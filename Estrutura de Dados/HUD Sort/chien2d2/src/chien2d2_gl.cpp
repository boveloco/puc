/*
Copyright 2008-2010, Paulo Vinicius Wolski Radtke (pvwradtke@gmail.com)

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
	09/12/2009 Fabio Binder     Permitir texturas maiores que 1024x1024
								variável maxtexsize alterada de local para global
								
	27/07/2010 Paulo Radkte     Corrigido bug para texturas que continuavam limitada a 1024x1024
	12/08/2010 Paulo Radtke	    Inclusão do callback do usuário durante a sincronização.

**/
// Includes padrão do C
#include <stdlib.h>
#include <string.h>
#include <math.h>

// Inclui o header do arquivo (sem comentários)
#include <c2d2/chien2d2.h>
#include <c2d2/chien2d2_interno.h>
#include <c2d2/chien2d2_gl.h>

// Variável externa para a função de callback do usuário durante a sincronização
extern void (*C2D2_SincronizaUsuario)();

// indica a cor para limpar a tela, incluindo o alpha
GLubyte limpaR, limpaG, limpaB, limpaA;        
// Variáveis relativas e efeitos da versão OpenGL
bool	faz_blend;
bool	texturiza;
GLuint	textura;
GLenum	blendOrigem, blendDestino;
GLenum  wrapTexturaS, wrapTexturaT;
GLenum  filtroMax, filtroMin;
GLint maxtexsize;

//Uint32 rmask, gmask, bmask, amask;
#if SDL_BYTEORDER == SDL_BIG_ENDIAN
	Uint32 rmask = 0xff000000;
    Uint32 gmask = 0x00ff0000;
    Uint32 bmask = 0x0000ff00;
    Uint32 amask = 0x000000ff;
#else
	Uint32 rmask = 0x000000ff;
    Uint32 gmask = 0x0000ff00;
    Uint32 bmask = 0x00ff0000;
    Uint32 amask = 0xff000000;
#endif


// Protótipos de funções internas da versão Open GL
/*inline void resetaGL();
inline void alteraBlend(bool estado);
inline void setaTexturizacao(bool estado);
inline void modoBlend(GLenum fatorOrigem, GLenum fatorDestino);
inline void setaTextura(GLuint textura);
inline void modoWrapTextura(GLenum wrapTexturaS, GLenum wraptTexturaT);
inline void modoFiltragem(GLenum filtroMax, GLenum filtroMin);*/
// Função para transformar um ponto magenta em transparente
void MagentaParaPontoTransparente(SDL_Surface *surface);

// Funções da Chien2D 2


bool C2D2GL_Inicia(unsigned int largura, unsigned int altura, 
        int modoVideo, char *tituloJanela)
{
    if(inicializado)
        return false; 
	else
		reset();
	// Indica que não está em shutdown
	c_shutdown = false;
    // Testa se já foi inicializado o vídeo (se foi, cai fora!)
    if(SDL_WasInit(SDL_INIT_VIDEO))
        return false;
    // Inicia o vídeo
	if ( SDL_InitSubSystem(SDL_INIT_VIDEO | SDL_INIT_JOYSTICK) < 0 ) 
    {
		printf("A Chien2D 2 não conseguiu iniciar a SDL. Mensagem do sistema: %s\n", 
                SDL_GetError());
        return false;
    }

    // Imprime informação do modo de vídeo requisitado
    printf("Iniciando o modo de vídeo (OpenGL): %ix%ix32bpp\n", largura, altura);
    
    // Flags do modo de vídeo (se ficar assim, roda em janela)
    Uint32 flags = SDL_OPENGL; // | SDL_GL_DOUBLEBUFFER;
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
        // Indica os atributos da OpenGL
        SDL_GL_SetAttribute( SDL_GL_RED_SIZE, 8 );
        SDL_GL_SetAttribute( SDL_GL_GREEN_SIZE, 8 );
        SDL_GL_SetAttribute( SDL_GL_BLUE_SIZE, 8 );
        SDL_GL_SetAttribute( SDL_GL_ALPHA_SIZE, 8 );        
        SDL_GL_SetAttribute( SDL_GL_DEPTH_SIZE, 16 );
        SDL_GL_SetAttribute( SDL_GL_DOUBLEBUFFER, 1 );  
		SDL_GL_SetAttribute( SDL_GL_SWAP_CONTROL, 1 );
        // Inicia o modo de vídeo e recupera a surface (diferente de 0 se tudo ok)
        screen = SDL_SetVideoMode(largura, altura, 32,  flags);
        if ( screen == 0 ) 
        {
            printf("Não foi possível iniciar o modo de vídeo requisitado. Encerrando.");
            SDL_QuitSubSystem(SDL_INIT_VIDEO);
            return false;
        }
        // Testa se conseguiu colocar o modo de vídeo como double buffer
        // Não é um erro grave, mas para melhores resultados, queremos o double buffer
        int atributo;
        SDL_GL_GetAttribute(SDL_GL_DOUBLEBUFFER,&atributo);
        if(atributo != 0)
			printf("A aplicação está utilizando double buffer como esperado.\n");
        else
			printf("ATENÇÃO: a aplicação não está usando double buffer. Para melhores resultados, utilize um hardware que o suporte.\n");
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

    // Prepara a visão do sistema
	glDisable(GL_DEPTH_TEST);
	glDisable(GL_CULL_FACE);

	glViewport(0, 0, largura, altura);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0, largura, altura, 0, -1.0, 1.0);
	glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(0.0f, 0.0f, 0.0f);
	
	// Limpa a tela
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    resetaGL();
    glDisable(GL_BLEND);    
    glDisable(GL_TEXTURE_2D);  
    glLoadIdentity();			
    SDL_GL_SwapBuffers();
    // Testa o tamanho da textura
	glGetIntegerv(GL_MAX_TEXTURE_SIZE, &maxtexsize);
	
    printf("O tamanho máximo de textura suportado é de %ix%i pixels.\n", maxtexsize, maxtexsize);
    // Associa a cor pra limpar a tela padrão
    limpaR=0;
    limpaG=0;
    limpaB=0;
    limpaA=0;  

    // Desabilita o cursor do mouse
    SDL_ShowCursor(SDL_DISABLE);
  
	// Preenche os ponteiros de função
	C2D2_Encerra = &C2D2GL_Encerra ;
	C2D2_Sincroniza = &C2D2GL_Sincroniza;
	C2D2_LimpaTela = &C2D2GL_LimpaTela;
	C2D2_TrocaCorLimpezaTela=C2D2GL_TrocaCorLimpezaTela;
	C2D2_LimitaAreaDesenho=&C2D2GL_LimitaAreaDesenho;
	C2D2_LiberaAreaDesenho=&C2D2GL_LiberaAreaDesenho;
	C2D2_CarregaSpriteSet=&C2D2GL_CarregaSpriteSet;
	C2D2_RemoveSpriteSet=&C2D2GL_RemoveSpriteSet;
	C2D2_DesenhaSprite=&C2D2GL_DesenhaSprite;
	C2D2_DesenhaSpriteEfeito=&C2D2GL_DesenhaSpriteEfeito;
	C2D2_DesenhaSpriteCentro=&C2D2GL_DesenhaSpriteCentro;
	C2D2_DesenhaSpriteCentroAlfa=&C2D2GL_DesenhaSpriteCentroAlfa;

    // Indica que foi iniciado e pula fora
    inicializado=true;
    return true;
}


// Função que encerra a Chien2D 2
//
// Data: 29/03/2007

void C2D2GL_Encerra()
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
	SDL_FreeSurface(screen);
	SDL_QuitSubSystem(SDL_INIT_VIDEO);
	printf("Encerrou a SDL+OpenGL");

    inicializado=false;
}


// Função para sincronizar o vídeo a 60ppfps
//
// Data: 13/04/2007
//
// Alteração:
//	12/08/2010	Paulo Radtke	Chamada do callback do usuário.
//
void C2D2GL_Sincroniza(Uint8 fps)
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
    SDL_GL_SwapBuffers();
    // Atualiza as entradas
    C2D2_Entrada();
}

// Função para limpar a tela
//
// Data: 13/04/2007
//
void C2D2GL_LimpaTela()
{
    // Limpa a tela e buffer de profundidade
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
    // Reseta o estado da OpenGL
    resetaGL();
    // Carrega a matriz identidade
    glLoadIdentity();

    // Indica que não quer usar textura
    setaTexturizacao(false);
    // Desenha um quad OpenGL
    glBegin(GL_QUADS);		       
	glColor4ub((GLubyte)limpaR, (GLubyte)limpaG, (GLubyte)limpaB, 255);
    // Coordenada do vértice na tela
    glVertex2i(0, 0);
    // E assim por diante
	glVertex2i( screen->w, 0);
    glVertex2i( screen->w, screen->h);
    glVertex2i( 0, screen->h);
    // Encerra o quad
    glEnd();
}

// Função para escolher a cor de limpeza da tela
//
// Data: 13/04/2007
//
void C2D2GL_TrocaCorLimpezaTela(Uint8 r, Uint8 g, Uint8 b)
{
	limpaR=r;	
	limpaG=g;
	limpaB=b;
}

// Função para limitar a área de desenho da tela
//
// Data: 16/08/2007
//
void C2D2GL_LimitaAreaDesenho(int x, int y, int largura, int altura)
{
	//????
	// Usa o stencil buffer para limitar a área de desenho
	// Habilita o stencil buffer
	glEnable(GL_STENCIL_TEST);
	// Limpa o stencil buffer
	glClear(GL_STENCIL_BUFFER_BIT);
	// Imdica a função, o número de bitplanes e o valor do bit
	glStencilFunc(GL_ALWAYS, 1, 1);	
	glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);				// We Set The Stencil Buffer To 1 Where We Draw Any Polygon
										// Keep If Test Fails, Keep If Test Passes But Buffer Test Fails
										// Replace If Test Passes
	// Desenha o quad na área desejada
    // Indica que não quer usar textura
    setaTexturizacao(false);
    // Prepara o blend de imagens
    alteraBlend(false);
    // Desenha o sprite num quad OpenGL
    glBegin(GL_QUADS);		       
    glColor4ub(255, 255, 255, 255);
    // Coordenada do vértice na tela
    glVertex2f((GLfloat)x, (GLfloat)y);
    // E assim por diante
	glVertex2f( (GLfloat) (x+largura), (GLfloat)y);
    glVertex2f( (GLfloat)(x+largura), (GLfloat)(y+altura));
    glVertex2f((GLfloat)x, (GLfloat)(y+altura));
    // Encerra o quad
    glEnd();
	glStencilFunc(GL_EQUAL, 1, 1);						// We Draw Only Where The Stencil Is 1
										// (I.E. Where The Floor Was Drawn)
	glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);					// Don't Change The Stencil Buffer


}

// Função para eliminar o limite da área de desenho
//
// Data: 16/08/2007
//
void C2D2GL_LiberaAreaDesenho()
{
	// Simplesmente desabilita o stencil buffer
	glDisable(GL_STENCIL_TEST);
}




//////////////////////////////////
// Funções para manipular sprites
//////////////////////////////////

// função para carregar um sprite set na memória
//
// Data: 13/04/2007
//
unsigned int C2D2GL_CarregaSpriteSet(const char *arquivo, int largura, int altura)
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
	// Indica o tamanho da imagem carregada
	sprites[idx].largura = sprites[idx].imagem->w;
	sprites[idx].altura = sprites[idx].imagem->h;
	// Verifica se pode usar como imagem dos sprites
	if(largura!=0 && altura!=0)
	{
		if(sprites[idx].largura%largura!=0 && sprites[idx].altura%altura!=0)
		{
			printf("A imagem %s não pode ser usada como fonte de sprites de tamanho %ix%i\n.",
				arquivo, largura, altura);
	        SDL_FreeSurface(sprites[idx].imagem);
			sprites[idx].imagem = 0;
			sprites[idx].apelido[0] = '\0';
			return 0;
		}
		else
		{
			sprites[idx].spLargura=largura;
			sprites[idx].spAltura=altura;
			sprites[idx].matrizX = sprites[idx].imagem->w/largura;
			sprites[idx].matrizY = sprites[idx].imagem->h/altura;
		}
	}
	else
	{
		sprites[idx].spLargura=sprites[idx].imagem->w;
		sprites[idx].spAltura=sprites[idx].imagem->h;
		sprites[idx].matrizX = 1;
		sprites[idx].matrizY = 1;
	}
	// Surface temporária. Será eliminada posteriormente se utilizada
	SDL_Surface *temp = 0;
	// Testa se uma das dimensões (qualquer uma) é potência de 2
    bool potencia=false;
    // Testamos de potência de 4 (16) até potência de 10 (1024);
    for(int i=4;i<=10;i++)
		if(pow(2.0,i)==sprites[idx].imagem->w)
        {
            potencia=true;
            break;
        }
	// Parte OpenGL. Testa se a imagem é quadrada e potência de 2. 
	// MUITO provável que não seja
	if(sprites[idx].imagem->w != sprites[idx].imagem->h || !potencia || sprites[idx].imagem->format->BytesPerPixel!=4)
	{
		// Guarda, temporariamente, a surface antiga em temp (para extrair as bitmasks)
		temp = sprites[idx].imagem;
		// DEtermina a dimensão da imagem a criar
		int maior;
		if(sprites[idx].imagem->w > sprites[idx].imagem->h)
			maior = sprites[idx].imagem->w;
		else
			maior = sprites[idx].imagem->h;
		// A imagem pode ser armazenada numa textura?
		if(maior > maxtexsize)
		{
	        SDL_FreeSurface(sprites[idx].imagem);
			sprites[idx].imagem = 0;
			sprites[idx].apelido[0] = '\0';
			printf("A imagem excede o limite de %ix%i para uma textura OpenGL no sistema.\n", maxtexsize, maxtexsize);
			return 0;
		}
		// Qual a potencia da imagem a criar?
		int pot=2;
		// Determina qual a melhor dimensão para a imagem
		while((int)pow(2.0,pot) < maior)
			pot++;
		// Indica o tamanho da textura
		sprites[idx].larguraTextura = sprites[idx].alturaTextura = (int)pow(2.0,pot);
		// Cria a nova imagem (a antiga está preservada em temp)
		sprites[idx].imagem = SDL_CreateRGBSurface(SDL_SWSURFACE, 
			sprites[idx].larguraTextura, sprites[idx].alturaTextura, 
            32, rmask, gmask, bmask, amask);
        // Testa se conseguiu criar a surface RGBA
        if(sprites[idx].imagem == 0) 
        {
			printf("Não conseguiu criar a imagem temporária ao carregar o sprite set. Arquivo: %s. Erro: %s.\n", 
				arquivo, SDL_GetError());
	        SDL_FreeSurface(sprites[idx].imagem);
			sprites[idx].imagem = 0;
			sprites[idx].apelido[0] = '\0';
            return 0;
        }
        // Copia a imagem original na nova imagem 32bpp
		if(temp->format->BytesPerPixel != 4)
			SDL_BlitSurface(temp, 0, sprites[idx].imagem, 0);
		else
		{
			for(int i=0;i<temp->h;i++)
			{
				memcpy(&((Uint8*)sprites[idx].imagem->pixels)[sprites[idx].imagem->pitch*i],
					&((Uint8*)temp->pixels)[temp->pitch*i], temp->pitch);
			}
		}
		// Apaga a surface temporária
		SDL_FreeSurface(temp);
	}
	
	// Recupera o tamanho da textura
	sprites[idx].larguraTextura=sprites[idx].imagem->w;
	sprites[idx].alturaTextura=sprites[idx].imagem->h;

	// Converte os pontos magenta da imagem em transparentes
	MagentaParaPontoTransparente(sprites[idx].imagem);
	

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

	C2D2_PreencheVetorBitMasks(sprites[idx].imagem, sprites[idx].spLargura, sprites[idx].spAltura, sprites[idx].largura, sprites[idx].altura, sprites[idx].bmask);	
	#ifdef DDEBUG
		// Faz o trace da máscara
		char saida[100];

		sprintf(saida, "%s.txt", arquivo);
		FILE *trace = fopen(saida, "wt");
		if(saida != 0)
		{
			for(int i=0;i<sprites[idx].matrizX*sprites[idx].matrizY;i++)
			{
				C2D2_BitMask *mascara = sprites[idx].bmask[i];
				for(int l=0;l<sprites[idx].spAltura;l++)
				{
					for(int c=0;c<sprites[idx].spLargura;c++)
					{
						if(C2D2_GetBit(mascara, c, l))
							fprintf(trace, "#");
						else
							fprintf(trace, " ");
					}
					fprintf(trace, "\n");
				}
			}
			fclose(trace);
		}
	#endif
	// Indica que tem um usuário
    sprites[idx].usuarios = 1;
	// Se temp foi usado, apaga
	// Liga a textura
    
	// Cria uma textura
	glGenTextures(1, &(sprites[idx].textura));
    // Associa a textura    
    glBindTexture(GL_TEXTURE_2D, sprites[idx].textura);   // 2d texture (x and y size)

    // Deve cobrir a textura até a borda??    
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
    // Faz escala linear
    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR); // scale linearly when image bigger than texture
    glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR); // scale linearly when image smalled than texture

    // Cria textura 2D, detalhe normal (0), 4 componentes (RGBA), a partir
    // da largura e altura da imagem,  sem borda, dados em RGBA, dados em
    // bytes não sianlizados e, finalmente, os dados em si
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, sprites[idx].imagem->w, 
		sprites[idx].imagem->h, 0, GL_RGBA, GL_UNSIGNED_BYTE, 
        sprites[idx].imagem->pixels);
	sprites[idx].texturaLigada=true;

    // idx+1 indica o identificador. Retorna e encerra (ufa!)
    return idx+1;
}


// Remove um sprite set da memória
//
// Data: 13/04/2007
//
void C2D2GL_RemoveSpriteSet(unsigned int id)
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
	// tem a textura devidamente associada?
	if(sprites[id-1].texturaLigada)
	{
		if(sprites[id-1].textura != 0)
		{
			// Remove the texture
			glDeleteTextures(1, &(sprites[id-1].textura));
			sprites[id-1].textura = 0;
		}
		sprites[id-1].texturaLigada=false;
	}
    // Tem uma surface SDL associada?
    if(sprites[id-1].imagem != 0)
    {
        // Apaga a surface
        SDL_FreeSurface(sprites[id-1].imagem);
        // Associa um valor nulo para reutilizar depois
        sprites[id-1].imagem = 0;
        // Tem que tirar o nome
        sprites[id-1].apelido[0] = '\0';
		// Remove a textura
		// Indica que a textura não existe mais
		sprites[id-1].textura=0;
		sprites[id-1].texturaLigada=false;

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

}


// Função para desenhar um sprite
//
// Data: 13/04/2007
//
bool C2D2GL_DesenhaSprite(unsigned int id, unsigned int indice, int x, int y)
{
	// O id é válido?
	if(id == 0)
		return false;
	int idx = id-1;
	// O índice é válido?
	if(indice >= (unsigned int)(sprites[idx].matrizX * sprites[idx].matrizY) || sprites[idx].textura==0)
		return false;
	// Tudo certo, calcula as coordenadas iniciais a desenhar na textura:
	int xImg = (indice%sprites[idx].matrizX)*sprites[idx].spLargura;
	int yImg = (indice/sprites[idx].matrizX)*sprites[idx].spAltura;
	// Calcula o ponto inicial da textura baseado no tamanho total desta
	double posx = (double)xImg/(double)(sprites[idx].larguraTextura);
	double posy = (double)yImg/(double)(sprites[idx].alturaTextura);
	// qual a largura (em porcentagem) na imagem
	double largura = (double)(sprites[idx].spLargura)/(double)(sprites[idx].larguraTextura);
	double altura = (double)(sprites[idx].spAltura)/(double)(sprites[idx].alturaTextura);
	// Faz o desenho em um quad OpenGL
    // Indica que quer usar textura
    setaTexturizacao(true);
    // Seleciona a textura a utilizar
	setaTextura(sprites[idx].textura);
    // Prepara o blend de imagens
    alteraBlend(true);
    modoBlend(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
    // Indica como pega a subtextura e como faz a escala
    modoWrapTextura(GL_CLAMP, GL_CLAMP);
    modoFiltragem(GL_LINEAR, GL_LINEAR);
    // Desenha o sprite num quad OpenGL
    glBegin(GL_QUADS);		       
    glColor4ub(255, 255, 255, 255);
    // DEfine o primeiro vértice na textura
    glTexCoord2f((GLfloat)posx, (GLfloat)posy); 
    // Coordenada do vértice na tela
    glVertex2f((GLfloat)x, (GLfloat)y);
    // E assim por diante
    glTexCoord2f((GLfloat)(posx+largura), (GLfloat)(posy)); 
	glVertex2f( (GLfloat)(x+sprites[idx].spLargura), (GLfloat)y);
    glTexCoord2f((GLfloat)(posx+largura), (GLfloat)(posy+altura)); 
    glVertex2f( (GLfloat)(x+sprites[idx].spLargura), (GLfloat)(y+sprites[idx].spAltura));
    glTexCoord2f((GLfloat)posx, (GLfloat)(posy+altura)); 
    glVertex2f((GLfloat)x, (GLfloat)(y+sprites[idx].spAltura));
    // Encerra o quad
    glEnd();

	return true;
}

static int ValidaSprite(unsigned int id, unsigned int indice)
{
	// O id é válido?
	if(id == 0)
		return -1;
	int idx = id-1;
	// O índice é válido?
	if(indice >= (unsigned int)(sprites[idx].matrizX * sprites[idx].matrizY) || sprites[idx].textura==0)
		return -1;

	return idx;
}

// Função para desenhar um sprite
//
// Data: 13/04/2007
//
bool C2D2GL_DesenhaSpriteEfeito(unsigned int id, unsigned int indice, int x[4], int y[4], Uint8 r, Uint8 g, Uint8 b, Uint8 a)
{
	// O id é válido?
	int idx = ValidaSprite(id, indice);
	if(idx < 0)
		return false;
	
	// Tudo certo, calcula as coordenadas iniciais a desenhar na textura:
	int xImg = (indice%sprites[idx].matrizX)*sprites[idx].spLargura;
	int yImg = (indice/sprites[idx].matrizX)*sprites[idx].spAltura;
	// Calcula o ponto inicial da textura baseado no tamanho total desta
	double posx = (double)xImg/(double)(sprites[idx].larguraTextura);
	double posy = (double)yImg/(double)(sprites[idx].alturaTextura);
	// qual a largura (em porcentagem) na imagem
	double largura = (double)(sprites[idx].spLargura)/(double)(sprites[idx].larguraTextura);
	double altura = (double)(sprites[idx].spAltura)/(double)(sprites[idx].alturaTextura);
	// Faz o desenho em um quad OpenGL
    // Indica que quer usar textura
    setaTexturizacao(true);
    // Seleciona a textura a utilizar
	setaTextura(sprites[idx].textura);
    // Prepara o blend de imagens
    alteraBlend(true);
    modoBlend(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
    // Indica como pega a subtextura e como faz a escala
    modoWrapTextura(GL_CLAMP, GL_CLAMP);
    modoFiltragem(GL_LINEAR, GL_LINEAR);
    // Desenha o sprite num quad OpenGL
    glBegin(GL_QUADS);		       
    glColor4ub(r, g, b, a);
    // DEfine o primeiro vértice na textura
    glTexCoord2f((GLfloat)posx, (GLfloat)posy); 
    // Coordenada do vértice na tela
    glVertex2f((GLfloat)x[0], (GLfloat)y[0]);
    // E assim por diante
    glTexCoord2f((GLfloat)(posx+largura), (GLfloat)posy); 

	glVertex2f( (GLfloat)x[1], (GLfloat)y[1]);
    glTexCoord2f((GLfloat)(posx+largura), (GLfloat)(posy+altura)); 
    glVertex2f( (GLfloat)x[2], (GLfloat)y[2]);
    glTexCoord2f((GLfloat)posx, (GLfloat)(posy+altura));
    glVertex2f((GLfloat)x[3], (GLfloat)y[3]);
    // Encerra o quad
    glEnd();

	return true;
}

bool C2D2GL_DesenhaSpriteCentro(unsigned int id, unsigned int indice, double xcentro, double ycentro, int largura, int altura)
{
	return C2D2GL_DesenhaSpriteCentroAlfa(id, indice, xcentro, ycentro, largura, altura, 255, 255, 255, 255);	
}

bool C2D2GL_DesenhaSpriteCentroAlfa(unsigned int id, unsigned int indice, double xcentro, double ycentro, int largura, int altura, Uint8 r, Uint8 g, Uint8 b, Uint8 a)
{
	int x[4];
	int y[4];

	double offsetx = xcentro - (largura  / 2);
	double offsety = ycentro - (altura / 2);

	x[0] = x[3] = (int)offsetx;
	x[1] = x[2] = (int)(offsetx + largura);

	y[0] = y[1] = (int)offsety;
	y[2] = y[3] = (int)(offsety + altura);

	return C2D2GL_DesenhaSpriteEfeito(id, indice, x, y, r, g, b, a);
}


// Métodos específicos para a versão OpenGL

inline void resetaGL()

{
    faz_blend = false;
    glDisable(GL_BLEND);
    texturiza = false;
    glDisable(GL_TEXTURE_2D);
    textura = 0;
    blendOrigem = 0xffffffff;
    blendDestino = 0xffffffff; 
    wrapTexturaS = 0xffffffff; 
    wrapTexturaT = 0xffffffff; 
    filtroMax = 0xffffffff; 
    filtroMin = 0xffffffff; 
}

inline void alteraBlend(bool estado)
{
	if(faz_blend == estado)
		return;

	if(estado)
		glEnable(GL_BLEND);
	else
		glDisable(GL_BLEND);
	faz_blend = estado;
}

void setaTexturizacao(bool estado)
{
	if(texturiza == estado)
		return;

	if(estado)
		glEnable(GL_TEXTURE_2D);
	else
		glDisable(GL_TEXTURE_2D);
	texturiza = estado;
}

inline void modoBlend(GLenum origem, GLenum destino)
{
	if((blendOrigem == origem) && (blendDestino == destino))
		return;

	glBlendFunc(origem, destino);

	blendOrigem = origem;
	blendDestino = destino;
}

inline void setaTextura(GLuint text)
{
	if(text == textura)
		return;

	glBindTexture(GL_TEXTURE_2D, text);
	textura = text;
}

inline void modoWrapTextura(GLenum wTexturaS, GLenum wTexturaT)
{
	if((wrapTexturaS == wTexturaS) && (wrapTexturaT == wTexturaT))
		return;

	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wTexturaS);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wTexturaT);

	wrapTexturaS = wTexturaS;
	wrapTexturaT = wTexturaT;
}
inline void modoFiltragem(GLenum fMax, GLenum fMin)
{
	if((filtroMin == fMin) && (filtroMax == fMax))
		return;

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, fMin);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, fMax);

	filtroMin = fMin;
	filtroMax = fMax;
}

// Função auziliar para criar pontos transparentes a partir de cor chave
//
// Data: 12/06/2008
//

void MagentaParaPontoTransparente(SDL_Surface *surface)
{
	if(surface == 0)
		return;
	SDL_LockSurface(surface);
	// Quantos bytes tem por pixel a imagem
    int bpp = surface->format->BytesPerPixel;
    // Endereço do ponto a recuperar
    Uint8 *p;
	Uint32 cor;
	Uint32 cor2 = SDL_MapRGBA(surface->format, 255, 0, 255,255);
	for(int y=0;y<surface->h;y++)
		for(int x=0;x<surface->w;x++)
		{
			p= (Uint8 *)surface->pixels + y * surface->pitch + x * bpp;
			// Só funciona em imagens com 32 bits e canal de alfa
			cor = *(Uint32 *)p;
			// Se for magenta o ponto, coloca 0 (transparente)
			if(cor == cor2)// && (cor&surface->format->Amask) != 0)
				*(Uint32 *)p=0;
		}
	SDL_UnlockSurface(surface);
}
