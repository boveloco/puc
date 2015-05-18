/*

Chien 2D 2.0 SDL Renderer

Copyright 2007-2010, Paulo Vinicius Wolski Radtke (pvwradtke@gmail.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
	Data original: 12/06/20 08

	Histórico de alterações:

	Data       Programador      Alteração
	========== ================ ======================================================================
	12/08/2010 Paulo Radtke	    Inclusão do callback do usuário durante a sincronização.

**/


// Includes padrão do C
#include <stdlib.h>
#include <string.h>

// Inclui o header do arquivo (sem comentários)
#include <c2d2/chien2d2.h>
#include <c2d2/chien2d2_interno.h>
#include <c2d2/chien2d2_sdl.h>

// Variável externa para a função de callback do usuário durante a sincronização
extern void (*C2D2_SincronizaUsuario)();

// A cor que usa para limpar a tela
Uint32 clearColor = 0;

// Funções da Chien2D 2


bool C2D2SDL_Inicia(unsigned int largura, unsigned int altura, 
        int modoVideo, char *tituloJanela)
{
    if(inicializado)
        return false; 
	else
		reset();

    // Testa se já foi inicializado o vídeo (se foi, cai fora!)
    if(SDL_WasInit(SDL_INIT_VIDEO))
        return false;

	// Indica que não está em shutdown
	c_shutdown = false;

    // Inicia o vídeo
	if (SDL_InitSubSystem(SDL_INIT_VIDEO | SDL_INIT_JOYSTICK) < 0 ) 
    {
        printf("A Chien2D 2 não conseguiu iniciar a SDL. Mensagem do sistema: %s\n", 
                SDL_GetError());
        return false;
    }

    // Imprime informação do modo de vídeo requisitado
    printf("Iniciando o modo de vídeo: %ix%ix32bpp\n", largura, altura);
    
    // Flags do modo de vídeo (se ficar assim, roda em janela)
    Uint32 flags = SDL_HWSURFACE | SDL_DOUBLEBUF; // | SDL_SRCALPHA;
    // Se for rodar em tela cheia, tem que colocar um flag a mair
	switch(modoVideo)
	{
	case C2D2_JANELA:
		printf("A aplicação requisitou um modo de vídeo em janela.\n");
		break;
	case C2D2_TELA_CHEIA:
		printf("A aplicação requisitou um modo de vídeo em tela cheia.\n");
		flags |= SDL_FULLSCREEN;
		break;
	default:
		printf("A aplicação requisitou um modo de vídeo inválido. Por segurança, será utilizado um modo em janela.\n");
		break;
	}

	printf("A Chien2DLite vai verificar a disponibilidade do modo de vídeo requisitado.\n");
    
    if(SDL_VideoModeOK(largura, altura, 32, flags) ) 
    {
        printf("O sistema indica que o modo é suportado. Tenta iniciar.\n"); 
        
        // Inicia o modo de vídeo e recupera a surface (diferente de 0 se tudo ok)
        screen = SDL_SetVideoMode(largura, altura, 32,  flags);
        if ( screen == 0 ) 
        {
            printf("Não foi possível iniciar o modo de vídeo requisitado. Encerrando.");
            SDL_QuitSubSystem(SDL_INIT_VIDEO);
            return false;
        }
    }
    else
    {
        printf("O modo de vídeo não é suportado pelo hardware. Encerrando.");; 
		SDL_QuitSubSystem(SDL_INIT_VIDEO);
        return false;
    }
    printf("Modo de vídeo iniciado com sucesso!\n");

    // Coloca o nome na janela
    SDL_WM_SetCaption(tituloJanela, 0);
    // Desabilita o cursor do mouse
    SDL_ShowCursor(SDL_DISABLE);
  
	// Preenche os ponteiros de função
	C2D2_Encerra = &C2D2SDL_Encerra ;
	C2D2_Sincroniza = &C2D2SDL_Sincroniza;
	C2D2_LimpaTela = &C2D2SDL_LimpaTela;
	C2D2_TrocaCorLimpezaTela=C2D2SDL_TrocaCorLimpezaTela;
	C2D2_LimitaAreaDesenho=&C2D2SDL_LimitaAreaDesenho;
	C2D2_LiberaAreaDesenho=&C2D2SDL_LiberaAreaDesenho;
	C2D2_CarregaSpriteSet=&C2D2SDL_CarregaSpriteSet;
	C2D2_RemoveSpriteSet=&C2D2SDL_RemoveSpriteSet;
	C2D2_DesenhaSprite=&C2D2SDL_DesenhaSprite;
	C2D2_DesenhaSpriteEfeito = &C2D2SDL_DesenhaSpriteEfeito;

    // Indica que foi iniciado e pula fora
    inicializado=true;
    return true;
}


// Função que encerra a Chien2D 2
//
// Data: 29/03/2007

void C2D2SDL_Encerra()
{
    // Testa por via das dúvidas para não ter problemas
    if(!inicializado)
        return;
    // Indica que está encerrando
    c_shutdown = true;
        
    printf("Apagando os spritesets e fontes do sistema.\n");

    // Apaga as fontes
    for(int i=0;i<C2D2_MAX_FONTES;i++)
        C2D2_RemoveFonte(i+1);

    // Apaga os sprite sets
    for(int i=0;i<C2D2_MAX_SPRITESET;i++)
        C2D2_RemoveSpriteSet(i+1);

    printf("Encerrando a SDL.\n");
    // Encerra o vídeo
    if(screen != 0)
        SDL_FreeSurface(screen);        
    SDL_QuitSubSystem(SDL_INIT_VIDEO);
    printf("Encerrou a SDL.\n");
    
    inicializado=false;
}


// Função para sincronizar o vídeo a 60ppfps
//
// Data: 13/04/2007
//
// Alteração:
//	12/08/2010	Paulo Radtke	Chamada do callback do usuário.
//
void C2D2SDL_Sincroniza(Uint8 fps)
{
    // Inicializa e pega o tempo atual (só faz isso a primeira vez)
    static Uint32 tempoAnterior=0;

    // Chama a função de sincronização do usuário
    if(C2D2_SincronizaUsuario != 0)
        C2D2_SincronizaUsuario();

	// Verifica se o parâmetro é válido
	if (fps <= 0) fps = 1;

    // -- Espera 16ms entre cada quadro --	
    // Variável para o tempo atual
    int espera=(1000/fps)-SDL_GetTicks()+tempoAnterior;
    if(espera > 0)
       SDL_Delay(espera);
    tempoAnterior=SDL_GetTicks();

    // Troca os buffers
    SDL_Flip(screen);
    // Atualiza as entradas
    C2D2_Entrada();
}

// Função para limpar a tela
//
// Data: 13/04/2007
//
void C2D2SDL_LimpaTela()
{
	SDL_FillRect(screen, 0, clearColor);
}

// Função para escolher a cor de limpeza da tela
//
// Data: 13/04/2007
//
void C2D2SDL_TrocaCorLimpezaTela(Uint8 r, Uint8 g, Uint8 b)
{
	clearColor = SDL_MapRGB(screen->format, r, g, b);
}

// Função para limitar a área de desenho da tela
//
// Data: 16/08/2007
//
void C2D2SDL_LimitaAreaDesenho(int x, int y, int largura, int altura)
{
	// Pega a surface da tela
	SDL_Surface *tela = SDL_GetVideoSurface();	
	// Prepara o retângulo de cliping
	SDL_Rect rect;
	rect.x=x;
	rect.y=y;
	rect.w=largura;
	rect.h=altura;
	// Clipa!
	SDL_SetClipRect(tela, &rect);
}

// Função para eliminar o limite da área de desenho
//
// Data: 16/08/2007
//
void C2D2SDL_LiberaAreaDesenho()
{
	// Pega a surface da tela
	SDL_Surface *tela = SDL_GetVideoSurface();	
	// Remove o clipping
	SDL_SetClipRect(tela, 0);
}




//////////////////////////////////
// Funções para manipular sprites
//////////////////////////////////

// função para carregar um sprite set na memória
//
// Data: 13/04/2007
//
unsigned int C2D2SDL_CarregaSpriteSet(const char *arquivo, int largura, int altura)
{
    // Verifica se o spriteset não existe já
    unsigned int idx = C2D2_ProcuraSpriteSet(arquivo);
    // Se já existe um sprite com o nome do arquivo, retorna o índice associado
    if(idx != 0)
    {
		sprites[idx-1].usuarios=sprites[idx-1].usuarios+1;
        return idx;
    }
        
    // Se não existe, procura o primeiro índice vago (idx ainda é igual a 0)
    for(int i=0;i<C2D2_MAX_SPRITESET;i++)
        // O spriteset é vago (não tem imagem associada?
        if(sprites[i].imagem == 0)
        {
            idx=i+1;
            break;
        }
 
    // Testa se ainda tem espaço na lista
    //
    // ATENÇÃO: não ter espaço na memória não é relacionado a este teste. Apenas
    // testa-se aqui se existe um índice vago na lista. Se não existe, idx==0
    if(idx==0)
        // Retorna um índice inválido para indicar que a função falhou
        return 0;
    
    // Se chegou até aqui, idx contém o identificador correto 
    // Calcula a posição no array (sem incrementar 1)
    idx-=1;    
    // Indica o caminho do arquivo no apelido
    strcpy(sprites[idx].apelido,arquivo);

    // Carrega a imagem no spriteset
    sprites[idx].imagem = IMG_Load(arquivo);

    // Testa: conseguiu abrir a imagem? Se deu erro, retorna spriteset inválido
    if (sprites[idx].imagem == 0) 
    {
		printf("A função C2D2_CarregaSPriteset falhou ao carregar o arquivo %s. Erro: %s.\n",
                arquivo, SDL_GetError());
        sprites[idx].apelido[0] = '\0';
        return 0;
    }

    // Preenche as dimensões da imagem do spriteset
	sprites[idx].largura = sprites[idx].imagem->w;

	sprites[idx].altura = sprites[idx].imagem->h;
    // Preenche as dimensões dos sprites. Se for menor ou igual a 0, pega a da imagem em si
	if(largura > 0 && altura > 0)
	{
		sprites[idx].spLargura = largura;
		sprites[idx].spAltura = altura;
	}
	else
	{
		sprites[idx].spLargura = sprites[idx].imagem->w;
		sprites[idx].spAltura = sprites[idx].imagem->h;
	}

	// Testa se as dimensões dos sprites são válidas em relação a imagem.
	// Para isto, a imagem deve ter dimensões em valor múltiplo de inteiro com as do sprite.
	if( sprites[idx].altura%sprites[idx].spAltura != 0 || sprites[idx].largura%sprites[idx].spLargura != 0)
	{
            printf("Erro ao carregar a imagem %s. A altura e largura da imagem deve ser múltiplos do tamanho do sprite.\n", arquivo);
            SDL_FreeSurface(sprites[idx].imagem);
            sprites[idx].imagem=0;
            sprites[idx].apelido[0] = '\0';
            return 0;
    }
    // Preenche os tamanhos da matriz de sprites (inútil??)
    sprites[idx].matrizX =  sprites[idx].largura/sprites[idx].spLargura;
    sprites[idx].matrizY = sprites[idx].altura/sprites[idx].spAltura;

    // Define o magenta (R=255, B=255, G=0) para cor transparente
    SDL_SetColorKey(sprites[idx].imagem, SDL_RLEACCEL | SDL_SRCCOLORKEY | SDL_SRCALPHA,
        SDL_MapRGB(sprites[idx].imagem->format, 255, 0, 255));

        
    // Converte a imagem para o formato do vídeo do sistema
    SDL_Surface *temp=SDL_DisplayFormatAlpha(sprites[idx].imagem);
    //SDL_Surface *temp=SDL_DisplayFormat(sprites[idx].imagem);
    if(temp == 0) 
    {
        printf("Não conseguiu criar a imagem temporária ao carregar os sprites do arquivo %s. Erro: %s.\n",
            arquivo, SDL_GetError());
        SDL_FreeSurface(sprites[idx].imagem);
        sprites[idx].imagem = 0;
        sprites[idx].apelido[0] = '\0';
        return 0;
    }   
    // Apaga a surface antiga
    SDL_FreeSurface(sprites[idx].imagem);
    // Associa a nova
    sprites[idx].imagem = temp;

    // Cria a máscara de colisão 
	sprites[idx].bmask = C2D2_CriaVetorBitMasks(sprites[idx].imagem, sprites[idx].spLargura, sprites[idx].spAltura, sprites[idx].largura, sprites[idx].altura);
	// Se falhou, deve indicar isto
    if(sprites[idx].bmask == 0) 
    {
		printf("Não conseguiu criar o vetor de bitmasks ao carregar os sprites do arquivo %s.\n", arquivo);
        SDL_FreeSurface(sprites[idx].imagem);
		sprites[idx].imagem = 0;
		sprites[idx].apelido[0] = '\0';
        return 0;
    }   
	// Daqui pra frente é ladeira, não tem porquê falhar
	C2D2_PreencheVetorBitMasks(sprites[idx].imagem, sprites[idx].spLargura, sprites[idx].spAltura, sprites[idx].largura, sprites[idx].altura, sprites[idx].bmask);
	// Indica que tem um usuário
    sprites[idx].usuarios = 1;
    // idx+1 indica o identificador. Retorna e encerra (ufa!)
    return idx+1;
}


// Remove um sprite set da memória
//
// Data: 13/04/2007
//
void C2D2SDL_RemoveSpriteSet(unsigned int id)
{
    // O identificador é válido?
    if(id > C2D2_MAX_SPRITESET || id == 0)
        return;
    // Só apaga se o número de usuários for um ou se estiver em shutdown
    if(sprites[id-1].usuarios > 1 && !c_shutdown)
    {
        sprites[id-1].usuarios -= 1;
        return;
    }
    // Se cair aqui, é porquê não tem mais usuários
    sprites[id-1].usuarios = 0;
    // Tem uma surface SDL associada?
    if(sprites[id-1].imagem != 0)
    {
        // Apaga a surface
        SDL_FreeSurface(sprites[id-1].imagem);
        // Associa um valor nulo para reutilizar depois
        sprites[id-1].imagem = 0;
        // Tem que tirar o nome
        sprites[id-1].apelido[0] = '\0';
    }
    // Se tem máscara de colisão, apaga
	if(sprites[id-1].bmask != 0)
	{
		// Apaga as máscaras do vetor
		for(int i=0;i<sprites[id-1].matrizX*sprites[id-1].matrizY;i++)
			C2D2_RemoveBitMask(sprites[id-1].bmask[i]);
		// Apaga o vetor das máscaras     
		free(sprites[id-1].bmask);
        // Prepara para o próximo uso
        sprites[id-1].bmask=0;
    }

	/*	if(sprites[id-1].colisao != 0)
	{
		// Apaga a máscara     
		free(sprites[id-1].colisao);
        // Prepara para o próximo uso
        sprites[id-1].colisao=0;
    }*/
}


// Função para desenhar um sprite
//
// Data: 13/04/2007
//
bool C2D2SDL_DesenhaSprite(unsigned int identificador, unsigned int indice, int x, int y)
{
	// O índice é válido?
	if(identificador<=0 || identificador > C2D2_MAX_SPRITESET)
	{
		printf("Erro ao tentar desenhar o spriteset %i. Este índice é inválido.\n", identificador);
		return false;
	}
    // Tem que existir a imagem
    if(sprites[identificador-1].imagem == 0)
        return false;
    
    // Seleciona o spriteset que vai desenhar
    C2D2_SpriteSet *sprite = &(sprites[identificador-1]);
    
    // Tamanho em pixels do spriteset
	int tamX = sprite->spLargura;
	int tamY = sprite->spAltura;
    // Define os rects para o blit
	SDL_Rect orig, dest;
 	dest.x = (Sint16)x;
	dest.y = (Sint16)y;
	dest.w = tamX;
	dest.h = tamY;
	orig.x = (indice%sprite->matrizX)*tamX;
	orig.y = (indice/sprite->matrizX)*tamY;
	orig.w = tamX;
	orig.h = tamY;
	SDL_BlitSurface(sprite->imagem, &orig, screen, &dest);
	// Faz o blit
    return true;
}

// Função para desenhar um sprite distorcido e com efeitos de cor/alpha
bool C2D2SDL_DesenhaSpriteEfeito(unsigned int identificador, unsigned int indice, int x[4], int y[4], Uint8 r, Uint8 g, Uint8 b, Uint8 a)
{
	// Por default, não consegue desenhar neste modo e retorna falso (dããã!)
	return false;
}
