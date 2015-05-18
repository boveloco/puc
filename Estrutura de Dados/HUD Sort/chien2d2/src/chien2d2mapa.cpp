/*

Chien 2D Mapa

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

	Data		Programador	Alteração
	=====================================================
	13/08/2010	Paulo VW Radtke	Funções para mudar uma camada do mapa e para pegar a camada de marcas.


**/


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#if	defined(linux)
	#include <SDL/SDL_endian.h>
#else
	#include <SDL_endian.h>
#endif

#include <c2d2/chien2d2.h>
#include <c2d2/chien2d2mapa.h>

char cabecalhos[6][5] ={ "FORM", "FMAP", "MPHD", "ANDT", "BODY", "LYR?"};

// indica se a Chien2D2 Mappy já foi inicializada. Por default, não foi ainda.
bool m_inicializado = false;

// O vetor com os mapas
C2D2M_Mapa mapas[C2D2M_MAX_MAPAS];
// Indica se o sistema está em shutdown ou não
bool m_shutdown = false;

// Funções exclusivas para desenvolvimento interno. Não use no seu programa!!

// Função para zerar os dados da Chien2D 2
void m_reset();


// Funções da C2D2 Mapa

// Função para zerar os dados da Chien2D 2
//
// Data: 29/03/2007

void m_reset()
{
	// Zera os mapas
	for(int i=0;i<C2D2M_MAX_MAPAS; i++)
	{
		// Apaga o apelido
		strcpy(mapas[i].apelido, "");
		// Zera o número de usuários
		mapas[i].usuarios = 0;
		// Zera o ponteiro das camadas
		for(int j=0;j<C2D2M_MAX_CAMADAS;j++)
			mapas[i].camadas[j] = 0;
		mapas[i].idSpriteset = 0;
		mapas[i].inicializado = false;
		mapas[i].camadaMarcas=-1;
	}
}

// Função para iniciar a Chien2D 2 Mapa
//
// Data: 27/07/2007

bool C2D2M_Inicia()
{
    if(m_inicializado)
        return false; 
	else
		m_reset();

	// Indica que foi iniciado e pula fora
    m_inicializado=true;
    return true;
}


// Função que encerra a Chien2D 2
//
// Data: 29/03/2007

void C2D2M_Encerra()
{
    // Testa por via das dúvidas para não ter problemas
    if(!m_inicializado)
        return;
    // Indica que está encerrando
    m_shutdown = true;
        
    printf("Apagando os mapas do sistema.\n");

    // Apaga os mapas
    for(int i=0;i<C2D2M_MAX_MAPAS;i++)
        C2D2M_RemoveMapa(i+1);

    m_inicializado=false;
}

// função para carregar um sprite set na memória
//
// Data: 13/04/2007
//
unsigned int C2D2M_CarregaMapaMappy(const char *arquivo_mapa, const char *arquivo_spriteset)
{
    // Verifica se o mapa não existe já
    unsigned int idx = C2D2M_ProcuraMapa(arquivo_mapa);
    // Se já existe um sprite com o nome do arquivo, retorna o índice associado
    if(idx != 0)
    {
		mapas[idx-1].usuarios=mapas[idx-1].usuarios+1;
        return idx;
    }
        
    // Se não existe, procura o primeiro índice vago (idx ainda é igual a 0)
    for(int i=0;i<C2D2M_MAX_MAPAS;i++)
        // O spriteset é vago (não tem imagem associada?
        if(!mapas[i].inicializado)
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
    strcpy(mapas[idx].apelido,arquivo_mapa);

	//////////////////////////////////////////////////

    FILE *mapa = fopen(arquivo_mapa, "rb");
	// Se falhou abrir o arquivo, deve retornar o erro
    if(mapa == 0)
	{
		C2D2M_RemoveMapa(idx+1);
        return 0;
	}
    // pega o tamanho do arquivo
	fseek(mapa, 0, SEEK_END);
    int tamanho = ftell(mapa);
    fseek(mapa, 0, SEEK_SET);
    // Aloca memória para o arquivo inteiro
    char *buffer = (char *)malloc(tamanho*sizeof(char));
    // Lê os dados
	fread(buffer, sizeof(char), tamanho, mapa);
    // Fecha o arquivo
      fclose(mapa);
    // Pega o "suposto" cabeçalho do arquivo
    CabecalhoArquivo *cabArq = (CabecalhoArquivo*)buffer;
    // Verifica se o cabecalho é certo
    if(C2D2M_VerificaCabecalhoArquivo(cabArq))
    {
        // Recupera o tamanho do arquivo (está em BIG ENDIAN)
        int tamanho = cabArq->tamanho;
        if(SDL_BYTEORDER == SDL_LIL_ENDIAN)
            tamanho = SDL_Swap32(tamanho);
        // O tamanho total soma 8
        tamanho+=8;
        // Índice dentro do buffer lido, já pulando o header de 12 bytes
        int indice=12;        
        // Indica se o arquivo é armazenado em little endian (recupera do MPHD)
        bool arquivoLilEndian=false;
        // Indica se o sistema é lil endian (usado com a variável em arquivo)
        bool sistemaLilEndian = false;
        if(SDL_BYTEORDER == SDL_LIL_ENDIAN)
            sistemaLilEndian=true;
        // Processa os chunks do arquivo enquanto o índice estiver dentro do arquivo
        while(indice < tamanho)
        {
            // pega o próximo chunk
            CabecalhoBloco *bloco = (CabecalhoBloco *)&(buffer[indice]);
            // Faz o indice apontar para os dados do bloco (pula 8 bytes)
            indice += 8;
            // Pega o tamanho do bloco (está no formato intel)
            int tamBloco = bloco->tamanho;
            if(SDL_BYTEORDER == SDL_LIL_ENDIAN)
                tamBloco = SDL_Swap32(tamBloco);
            //    Aqui extraímos as informações dos blocos. Nos interessam apenas
            // o header do arquivo (MPHD), o cenário (BODY), os layers (LYR"X")
            // e as animações (ANDT)
            switch(C2D2M_TipoBloco(bloco))
            {
                case CABECALHO_MPHD:
                {
                    // Acessa o header
                    MPHD *mphd = (MPHD *)&(buffer[indice]);
                    // O arquivo é codificado em Little Endian?
                    if(mphd->lsb)
                        arquivoLilEndian=true;
                    // Extra as informações relevantes
                    mapas[idx].altura = mphd->mapheight;
                    mapas[idx].largura = mphd->mapwidth;
                    mapas[idx].dimensaoBlocoH = mphd->blockwidth;
                    mapas[idx].dimensaoBlocoV = mphd->blockheight;
                    // Se o sistema e arquivo não baterem
                    if(arquivoLilEndian != sistemaLilEndian)
                    {
                        mapas[idx].altura = SDL_Swap16(mapas[idx].altura);
                        mapas[idx].largura = SDL_Swap16(mapas[idx].largura);
                        mapas[idx].dimensaoBlocoH = SDL_Swap16(mapas[idx].dimensaoBlocoH);
                        mapas[idx].dimensaoBlocoV = SDL_Swap16(mapas[idx].dimensaoBlocoV);
                    }
                    // Calcula a largura do mapa em pixels
                    mapas[idx].larguraPixel = mapas[idx].largura*mapas[idx].dimensaoBlocoH;
                    mapas[idx].alturaPixel = mapas[idx].altura*mapas[idx].dimensaoBlocoV;
                    break;
                }
                // Bloco dos dados da camada principal (0)
                case CABECALHO_BODY:
                {
                    // Aloca a memória do layer principal
					mapas[idx].camadas[0] = (short int*)malloc(mapas[idx].altura*mapas[idx].largura*sizeof(short int));
                    // Copia os dados do buffer para a camada
                    memcpy(mapas[idx].camadas[0], (short int*)&(buffer[indice]), 
                            mapas[idx].largura*mapas[idx].altura*sizeof(short int));
                    if(arquivoLilEndian != sistemaLilEndian)
                        for(int i=0;i<mapas[idx].altura*mapas[idx].largura;i++)
                           mapas[idx].camadas[0][i]=SDL_Swap16(mapas[idx].camadas[0][i]); 
                    break;
                }
                // Um dos layers associados
                case CABECALHO_LYR:
                {
                    // Determina a camada
                    int camada = bloco->tipo[3]-'0';
                    // Aloca a memória do layer principal
                    mapas[idx].camadas[camada] = (short int*)malloc(mapas[idx].altura*mapas[idx].largura*sizeof(short int));
                    // Copia os dados do buffer para a camada
                    memcpy(mapas[idx].camadas[camada], (short int*)&(buffer[indice]), 
                            mapas[idx].altura*mapas[idx].largura*sizeof(short int));
                    if(arquivoLilEndian != sistemaLilEndian)
                        for(int i=0;i<mapas[idx].altura*mapas[idx].largura;i++)
                           mapas[idx].camadas[camada][i]=SDL_Swap16(mapas[idx].camadas[camada][i]); 
                    break;
                }
                // Animações
                case CABECALHO_ANDT:
                {
                    // Procura as estruturas ANISTR (tem que ter pelo menos uma)
                    int numAniStr=0;
                    while(true)
                    {
                        // Pega a estrutura (a zero é vazia)
                        numAniStr++;
						memcpy(&(mapas[idx].estrutAnimacao[numAniStr]), 
                            (ANISTR*)&(buffer[indice+tamBloco-sizeof(ANISTR)*numAniStr]),
                            sizeof(ANISTR));
                        // Se a animação é do tipo AN_END, chegou ao fim
                        if(mapas[idx].estrutAnimacao[numAniStr].antype == AN_END)
                            break;
                    }
                    // Extrai as sequências de animação: tamanho do bloco- ANISTR lidas
                    int numAnimSeq=(tamBloco-sizeof(ANISTR)*numAniStr)/sizeof(long int);
                    memcpy(mapas[idx].seqAnimacao, (long int*)&(buffer[indice]),
                        sizeof(long int)*numAnimSeq);
                    // Ajusta os dados com mais de um byte
                    if(arquivoLilEndian != sistemaLilEndian)
                    {
                        for(int i=0;i<numAniStr;i++)
                        {
                           mapas[idx].estrutAnimacao[i].ancuroff = SDL_Swap32(mapas[idx].estrutAnimacao[i].ancuroff); 
                           mapas[idx].estrutAnimacao[i].anstartoff = SDL_Swap32(mapas[idx].estrutAnimacao[i].anstartoff); 
                           mapas[idx].estrutAnimacao[i].anendoff = SDL_Swap32(mapas[idx].estrutAnimacao[i].anendoff);                                                      
                        }
                        for(int i=0;i<numAnimSeq;i++)
                            mapas[idx].seqAnimacao[i] = SDL_Swap32(mapas[idx].seqAnimacao[i]);
                    }
                    break;                    
                }
                
                // Ignora o bloco e passa para o próximo
                default:
                    break;
            }
            // Atualiza a posição do índice
            indice += tamBloco;
        }
    }
    // Apaga o buffer
    free(buffer);

	//////////////////////////////////////////////////

	// Tenta carregar o spriteset
	mapas[idx].idSpriteset = C2D2_CarregaSpriteSet(arquivo_spriteset, mapas[idx].dimensaoBlocoH, mapas[idx].dimensaoBlocoV);
	// Se não conseguiu, apaga tudo
	if(	mapas[idx].idSpriteset == 0)
	{
		C2D2M_RemoveMapa(idx+1);
		return 0;
	}
	// Daqui pra frente é ladeira ...

	// Zera as velocidades do mapa
	memset(mapas[idx].vCamadas, 0, C2D2M_MAX_CAMADAS*sizeof(int));
	// Indica que tem um usuário
    mapas[idx].usuarios = 1;
	// Coordenadas iniciais do mapa
	mapas[idx].x=0;
	mapas[idx].y=0;
	// Camada de marcadores default: -1
	mapas[idx].camadaMarcas = -1;
	// Indica que não está procurando um mapa
	mapas[idx].buscaProximo = false;
	// Coloca a velocidade default como 0 para mapas topview
	mapas[idx].gravidade = 0;
	// Coloca a velocidade de queda máxima em 0
	mapas[idx].maxgravidade = 0;
	// Indica que inicializou
	mapas[idx].inicializado = true;
    // idx+1 indica o identificador. Retorna e encerra (ufa!)
    return idx+1;
}


// Remove um mapa da memória
//
// Data: 28/07/2007
//
void C2D2M_RemoveMapa(unsigned int id)
{
    // O identificador é válido?
    if(id >= C2D2M_MAX_MAPAS || id == 0)
        return;
    // Só apaga se o número de usuários for um ou se estiver em shutdown
    if(mapas[id-1].usuarios > 1 && !m_shutdown)
    {
        mapas[id-1].usuarios -= 1;
        return;
    }
    // Se cair aqui, é porquê não tem mais usuários
    mapas[id-1].usuarios = 0;
    // Tem uma surface SDL associada?
	if(mapas[id-1].idSpriteset != 0)
	{
		C2D2_RemoveSpriteSet(mapas[id-1].idSpriteset);
		mapas[id-1].idSpriteset = 0;
	}
    for(int i=0;i<C2D2M_MAX_CAMADAS;i++)
		if(mapas[id-1].camadas[i] != 0)
		{
            free(mapas[id-1].camadas[i]);
			mapas[id-1].camadas[i] = 0;
		}
	strcpy(mapas[id-1].apelido, "");
	// Enfim, indica que não está inicializado
	mapas[id-1].inicializado = false;
}


// Procura um mapa pelo nome do arquivo original
//
// Data: 13/04/2007
//
unsigned int C2D2M_ProcuraMapa(const char *apelido)
{
    // Índice de spriteset inválido, caso não encontre
    unsigned int idx=0;
    for(int i=0;i<C2D2M_MAX_MAPAS;i++)
        if(strcmp(mapas[i].apelido, apelido)==0)
        {
            // O índice é a posição atual + 1
            idx=i+1;
            // Encerra a busca
            break;
        }
    return idx;
}

// Função para ver as dimensões do mapa (retorna true se o mapa é válido, false caso contrário)
//
// Data: 26/01/2005
// Última atualização: 02/08/2007

bool C2D2M_DimensoesMapa(unsigned int idMapa, int *largura, int *altura)
{
	// É um índice válido?
	if(idMapa >= C2D2M_MAX_MAPAS)
		return false;
    // Se não foi inicializado, falha!
    if(!mapas[idMapa-1].inicializado)
        return false;
    // Preenche os valores
	*largura = mapas[idMapa-1].larguraPixel;
	*altura = mapas[idMapa-1].alturaPixel;
    return true;
}

// Posiciona o mapa nas coordenadas x,y indicadas
//
// Data: 16/08/2006
void C2D2M_PosicionaMapa(unsigned int idMapa, int x, int y)
{
    // O identificador é válido?
    if(idMapa >= C2D2M_MAX_MAPAS || idMapa == 0)
        return;
    // Se não foi inicializado, falha!
    if(!mapas[idMapa-1].inicializado)
        return;
	mapas[idMapa-1].x = x;
	mapas[idMapa-1].y = y;
}

// Recupera a posição do mapa
//
// Data: 16/08/2006
bool C2D2M_PosicaoXY(unsigned int idMapa, int *x, int *y)
{
    // O identificador é válido?
    if(idMapa >= C2D2M_MAX_MAPAS || idMapa == 0)
        return false;
    // Se não foi inicializado, falha!
    if(!mapas[idMapa-1].inicializado)
        return false;
	*x = mapas[idMapa-1].x;
	*y = mapas[idMapa-1].y;
	return true;
}

// Função para dar velocidade a uma camada do mapa
//
// Data: 02/08/2007

void C2D2M_VelocidadeCamadaMapa(unsigned int idMapa, int camada, int velocidade)
{
	// É um mapa válido?
	if(idMapa==0 || idMapa >= C2D2M_MAX_MAPAS)
		return;
	// É um índice válido?
	if(camada >= C2D2M_MAX_MAPAS || camada <0)
		return;
    // Se não foi inicializado, falha!
    if(!mapas[idMapa-1].inicializado)
        return;
	// A camada é válida?
	if(camada<0 || camada>=C2D2M_MAX_CAMADAS)
		return;
	mapas[idMapa-1].vCamadas[camada] = velocidade;
}

// Função para indicar a camada de marcas do mapa e o número do bloco inicial de marcas no cenário
//
// Data: 16/08/2007
//
bool C2D2M_CamadaMarcas(unsigned int idMapa, int camada, int offbloco)
{
	// É um mapa válido?
	if(idMapa==0 || idMapa >= C2D2M_MAX_MAPAS)
		return false;
	// É um índice válido?
	if(camada >= C2D2M_MAX_MAPAS)
		return false;
	// Enfim, marca a camada
	mapas[idMapa-1].camadaMarcas = camada;
	mapas[idMapa-1].offbloco = offbloco;
	// Reseta a busca de blocos, caso alguém use um dia a habilidade de mudar as camadas do mapa
	mapas[idMapa-1].buscaProximo = false;
	return true;
}


// Método para desenhar o mapa na tela a partir das coordenadas x,y do mapa
//
// Data: 26/01/2005
// Última atualização: 28/01/2005


// Função para desenhar uma camada do mapa na tela a partir das coordenadas do mapa (x,y na estrutura), dentro da janela especificada
// por xtela,ytela e as dimensões largura e altura.
//
// Data: 28/01/2005
// Última atualização: 16/08/2007

void C2D2M_DesenhaCamadaMapa(unsigned int idMapa, int camada, int xtela, int ytela, int largura, int altura)
{
	// É um índice válido?
	if(idMapa > C2D2M_MAX_MAPAS || idMapa ==0)
		return;
	if(!mapas[idMapa-1].inicializado)
        return;
    // TEsta se é um layer válido
	if(camada >= C2D2M_MAX_CAMADAS || camada <0)
        return;
	// Pega o ponteiro do mapa (mais rápido)
	C2D2M_Mapa *mapa = &mapas[idMapa-1];
    // Testa se o layer existe
	if(mapa->camadas[camada] == 0)
        return;
	
    // Só desenha o mapa se o ponto âncora for dentro do mapa e o mapa couber na tela

	if(mapa->x<0 || mapa->y<0 || mapa->x>(mapa->larguraPixel-largura) 
		|| mapa->y>( mapa->alturaPixel - altura))
        	return;
	int xmapa, ymapa;
	// Calcula o ponto de acordo com a velocidade do mapa
	if(mapa->vCamadas[camada])
	{
		xmapa=mapa->x/mapa->vCamadas[camada];
		ymapa=mapa->y/mapa->vCamadas[camada];
	}
	else
	{
		xmapa=0;
		ymapa=0;
	}
	// Pega a surface da tela
/*	SDL_Surface *tela = SDL_GetVideoSurface();
	// Prepara o retângulo de cliping
	SDL_Rect rect;
	rect.x=xtela;
	rect.y=ytela;
	rect.w=largura;
	rect.h=altura;
	// Clipa!
	SDL_SetClipRect(tela, &rect);*/
	//C2D2_LimitaAreaDesenho(xtela, ytela, largura, altura);
	// Determina as dimensões da tela em sprites (quanto vai desenhar)
	int larguraSprites = largura/mapa->dimensaoBlocoH;
	int alturaSprites = altura/mapa->dimensaoBlocoV;
	// Se as dimensões da janela não são múltiplos do tamanho do sprite, tem que desenhar um bloco a mais
	if(largura%mapa->dimensaoBlocoH)
		larguraSprites++;
	if(altura%mapa->dimensaoBlocoV)
		alturaSprites++;
    // Determina os blocos iniciais no mapa
    int blocoX = xmapa/mapa->dimensaoBlocoH;
    int blocoY = ymapa/mapa->dimensaoBlocoV;
    // DEtermina o offset em pixels DENTRO do sprite
    int offsetX = xmapa%mapa->dimensaoBlocoH;
    int offsetY = ymapa%mapa->dimensaoBlocoV;
    // Se tem offset, pode ser preciso desenhar um bloco a mais em cada direção
	// Para determinar se precisa ou não do offset, verificamos se o offset é maior que o resto da divisão pelo tamanho do bloco
    // Nota: não precisa verificar se vai ultrapassar o limite físico do mapa 
    // porque o teste da posição no mapa acima já faz isso
    if(offsetX)
        larguraSprites++;
    if(offsetY)
        alturaSprites++;
	// O sprite a ser desenhado em cada posição
	int sprite;
    // Desenha o mapa
    for(int linha=0;linha<alturaSprites;linha++)
        for(int coluna=0;coluna<larguraSprites;coluna++)
        {
            // Recupera o azulejo a desenhar
			sprite = mapa->camadas[camada][mapa->largura*(linha+blocoY)+coluna+blocoX];
            // Se o sprite for negativo, recupera o sprite na lista de animações
            if(sprite < 0)
                //    Como o valor é negativo, tem que negar antes, 
                // pegando na sequência de animação
                sprite = mapa->seqAnimacao[mapa->estrutAnimacao[-sprite].ancuroff];
            // Não desenha o sprite 0 (transparente)
            if(sprite!=0)
				C2D2_DesenhaSprite(mapa->idSpriteset, sprite-1, xtela+coluna*mapa->dimensaoBlocoH-offsetX,
					ytela+linha*mapa->dimensaoBlocoV-offsetY);
        }
	// Retira o Clip
	//SDL_SetClipRect(tela, 0);
	//C2D2_LiberaAreaDesenho();
}

// função para buscar as coordenadas da primeira ocorrência de um bloco de marca dentro de um mapa
//
// Data: 17/08/2007
bool C2D2M_PrimeiroBlocoMarca(unsigned int idMapa, int bloco, int *x, int *y)
{
	// É um índice válido?
	if(idMapa >= C2D2M_MAX_MAPAS || idMapa ==0)
		return false;
	if(!mapas[idMapa-1].inicializado)
        return false;
	// Recupera o mapa
	C2D2M_Mapa *mapa = &mapas[idMapa-1];
    // TEsta se op layer de marcas existe
	if(mapa->camadaMarcas == -1)
        return false;
	int pos;
	// Procura em cada posição até achar
	for(pos=0;pos<mapa->largura*mapa->altura;pos++)
	{
		if(mapa->camadas[mapa->camadaMarcas][pos]==bloco+mapa->offbloco)
			break;
	}
	// Se achou, pos é menor que o número total de blocos na camada
	if(pos>=mapa->largura*mapa->altura)
		return false;
	// Se chegou aqui, achou o bloco!!
	// Calcula a posição
	*x = (pos % mapa->largura) * mapa->dimensaoBlocoH + mapa->dimensaoBlocoH/2;
	*y = (pos / mapa->largura) * mapa->dimensaoBlocoV + mapa->dimensaoBlocoV/2;
	// Guarda a informação para a chamada de C2D2M_ProximoBlocoMarca
	mapa->buscaProximo = true;
	mapa->posicaoUltimo = pos;
	mapa->blocoProcurado = bloco;
	// Retorna verdade
	return true;
}

// função para buscar as coordenadas da próxima ocorrência de um bloco de marca dentro de um mapa. Usar apenas APÓS C2D2M_PrimeiroBlocoMarca.
//
// Data: 23/08/2007
bool C2D2M_ProximoBlocoMarca(unsigned int idMapa, int *x, int *y)
{
	// É um índice válido?
	if(idMapa >= C2D2M_MAX_MAPAS || idMapa ==0)
		return false;
	if(!mapas[idMapa-1].inicializado)
        return false;
	// Recupera o mapa
	C2D2M_Mapa *mapa = &mapas[idMapa-1];

	// TEsta: está procurando uma seqüência de blocos??
	if(!mapa->buscaProximo)
		return false;
	// Se chegou aqui, a camada de marcas existe e é válida. Busca
	int pos;
	// Procura em cada posição até achar
	for(pos=mapa->posicaoUltimo+1;pos<mapa->largura*mapa->altura;pos++)
	{
		if(mapa->camadas[mapa->camadaMarcas][pos]==mapa->blocoProcurado+mapa->offbloco)
			break;
	}
	// Se achou, pos é menor que o número total de blocos na camada
	if(pos>=mapa->largura*mapa->altura)
	{
		// Indica que não está mais procurando o próximo
		mapa->buscaProximo = false;
		return false;
	}
	// Se chegou aqui, achou o bloco!!
	// Calcula a posição
	*x = (pos % mapa->largura) * mapa->dimensaoBlocoH + mapa->dimensaoBlocoH/2;
	*y = (pos / mapa->largura) * mapa->dimensaoBlocoV + mapa->dimensaoBlocoV/2;
	// Guarda a informação para a próxima chamada de C2D2M_ProximoBlocoMarca
	mapa->posicaoUltimo = pos;
	// Retorna verdade
	return true;
}

// Função que indica a aceleração de gravidade do mapa, em pixels por segundo, e a velocidade máxima de gravidade para simular atrito
bool C2D2M_GravidadeMapa(unsigned int idMapa, double gravidade, double maxgravidade)
{
	// É um índice válido?
	if(idMapa >= C2D2M_MAX_MAPAS || idMapa ==0)
		return false;
	if(!mapas[idMapa-1].inicializado)
        return false;
	// Recupera o mapa
	C2D2M_Mapa *mapa = &mapas[idMapa-1];
	// Muda a gravidade
	mapa->gravidade = gravidade;
	mapa->maxgravidade = maxgravidade;
	return true;
}

// Anima os blocos animados do mapa. Atualiza os quadros, reseta e etc e tal
//
// Data: 27/01/2005
// Última atualização: 02/08/2007

void C2D2M_AnimaMapa(unsigned int id)
{
    if(id > C2D2M_MAX_MAPAS)
		return;
	// Pega o ponteiro do mapa
	C2D2M_Mapa *mapa = &mapas[id-1];
	// Testa se pode fazer a operação
    if(!mapa->inicializado)
        return;    
    // Procura as animações (0 não existe, lembrando)
    int indice = 1;
    while(mapa->estrutAnimacao[indice].antype != AN_END)
    {
        //     Verifica se não é uma animação (só Deus sabe porque isso existe)
        //     Se verdade, pula (acredite, isso faz tanto sentido pra mim como 
        // pra você, afinal, não é pra ser ANIMADO?!?
        if(mapa->estrutAnimacao[indice].antype == AN_NONE)
            continue;
        // Se a animação zera, tem que atualizar
        if((mapa->estrutAnimacao[indice].ancount-=1) == 0)
        {
            // Armazena o delay novo
            mapa->estrutAnimacao[indice].ancount = mapa->estrutAnimacao[indice].andelay;
            //    Atualiza o frame e, se estourou, volta para o início de acordo
            // com o tipo da animação
            switch(mapa->estrutAnimacao[indice].antype)
            {
                // Loop pra frente
                case AN_LOOPF:            
                {
                    if((mapa->estrutAnimacao[indice].ancuroff+=1) == mapa->estrutAnimacao[indice].anendoff)
                        mapa->estrutAnimacao[indice].ancuroff = mapa->estrutAnimacao[indice].anstartoff;
                    break;
                }
                // Loop reverso
                case AN_LOOPR:
                {
                    if((mapa->estrutAnimacao[indice].ancuroff-=1) < mapa->estrutAnimacao[indice].anstartoff)
                        mapa->estrutAnimacao[indice].ancuroff = mapa->estrutAnimacao[indice].anendoff-1;
                    break;
                }
                // Anima uma única vez (não muit oútil se não for na primeira tela)
                case AN_ONCE:            
                {
                    if(mapa->estrutAnimacao[indice].anuser)
                        break;
                    // Testa se chegou no final
                    if((mapa->estrutAnimacao[indice].ancuroff+=1) == mapa->estrutAnimacao[indice].anendoff-1)
                        mapa->estrutAnimacao[indice].anuser = true;
                    break;        
                }
                // Animação ping-pong pra frente
                case AN_PPFF:
                {
                    // Se verdade, vai de marcha-ré
                    if(mapa->estrutAnimacao[indice].anuser==0)
                    {
                        if((mapa->estrutAnimacao[indice].ancuroff-=1) < mapa->estrutAnimacao[indice].anstartoff)
                        {
                            mapa->estrutAnimacao[indice].ancuroff = mapa->estrutAnimacao[indice].anstartoff+1;
                            mapa->estrutAnimacao[indice].anuser = 1;                
                        }
                    }
                    else
                    {
                        if((mapa->estrutAnimacao[indice].ancuroff+=1) == mapa->estrutAnimacao[indice].anendoff)
                        {
                            mapa->estrutAnimacao[indice].ancuroff = mapa->estrutAnimacao[indice].anendoff-2;
                            mapa->estrutAnimacao[indice].anuser = 0;
                        }
                    }   
                    break;
                }
                // Animação ping-pong pra trás
                case AN_PPRR:
                {
                    // Se verdade, vai pra frente
                    if(mapa->estrutAnimacao[indice].anuser==0)
                    {
                        if((mapa->estrutAnimacao[indice].ancuroff+=1) == mapa->estrutAnimacao[indice].anendoff)
                        {
                            mapa->estrutAnimacao[indice].ancuroff = mapa->estrutAnimacao[indice].anendoff-2;
                            mapa->estrutAnimacao[indice].anuser = 1;
                        }
                    }
                    else
                    {
                        if((mapa->estrutAnimacao[indice].ancuroff-=1) < mapa->estrutAnimacao[indice].anstartoff)
                        {
                            mapa->estrutAnimacao[indice].ancuroff = mapa->estrutAnimacao[indice].anstartoff+1;
                            mapa->estrutAnimacao[indice].anuser = 0;                
                        }
                    }
                    break;
                }
                                   
                default:
                    break;
                
            }
        }
        // Próxima animação
        indice++;
    }
}

// Ajusta o deslocamento de um boundingbox no mapa. Recebe o mapa aonde o deslocamento
// se realiza, a posição do bounding box e o quanto deve mover no eixo x e y. O booleano
// gravidade indica se há gravidade, situação na qual deslocamentos verticais para baixo
// e cima não implicam em deslocamento no eixo x. 
//
//  Os parâmetros são o identificador do mapa (idMapa), as coordenadas do bounding box
// (x,y), as dimensões do bounding box (largura), os ponteiros para o deslocamento 
// (dx e dy) e se a gravidade está em ação.
//
// Data: XX/XX/2007
// Última atualização: 28/08/2008
//
void C2D2M_AjustaDeslocamento(unsigned int idMapa, int xorig, int yorig, 
							  int larg, int alt, int *dx, int *dy, bool gravidade)
{
	if(idMapa == 0 || idMapa>=C2D2M_MAX_MAPAS)
		return;
	// SE o mapa não foi inicializado, ou se a camada de marcas está na default (-1)
	if(!mapas[idMapa-1].inicializado || mapas[idMapa-1].camadaMarcas==-1)
		return;
	// REcupera o valor de x e y originais
	int x=xorig,y=yorig;
	// Recupera o mapa
	C2D2M_Mapa *mapa = &mapas[idMapa-1];
	// Bounding box
	int topo, baixo, esquerda, direita;
	// A referência do ator para inclinações, o quanto entra no bloco e a diferença de pontos
	int meio, entrou, dif;
	// Qual a maior distância percorrida (mais tarde, passa para o personagem)
	int maior;
	// Qual o bloco
	int bloco;
	if(abs(*dx) > abs(*dy))
		maior = abs(*dx);
	else
		maior = abs(*dy);
	// O deslocamento no eixo a cada passo (mais tarde, passa para o personagem)
	int dxl=0, dyl=0;
	if(*dx>0)
		dxl=1;
	else if(*dx < 0)
		dxl=-1;
	if(*dy>0)
		dyl=1;
	else if(*dy < 0)
		dyl=-1;
	// As variáveis para indicar se deve deslocar ou não
	int xant=0, yant=0;
	// Percorre os pixels no maior eixo
	for(int i = 1; i <= maior ; i++)
	{
		// Aplica o deslocamento no eixo x?
		if(dxl !=0 && (i * *dx)/maior != xant)
		{
			xant = (i * *dx)/maior;
			// Desloca
			x += dxl;
			// Calcula o bounding box
			topo = (y)/mapa->dimensaoBlocoV;
			baixo = (y+alt-1)/mapa->dimensaoBlocoV;
			esquerda = (x)/mapa->dimensaoBlocoH;
			direita = (x+larg-1)/mapa->dimensaoBlocoH;
			// Calcula o bloco em que se encontra o meio
			meio = (x+larg/2)/mapa->dimensaoBlocoH;
			// Testa a colisão com blocos inclinados
			if(meio >=0 && meio <= mapa->largura && mapa->dimensaoBlocoH == mapa->dimensaoBlocoV)
			{
				// ajusta pela parte de baixo
				if(baixo >= 0 && baixo<= mapa->altura)
				{
					bloco =mapa->camadas[mapa->camadaMarcas][mapa->largura*baixo+meio]-mapa->offbloco; 
					switch(bloco)
					{
					case C2D2M_SOLIDO45:
					case C2D2M_SOLIDO135:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO45)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = (baixo+1)*mapa->dimensaoBlocoV-1-(y+alt);
						// Aqui colide e ajusta
						if(dif < entrou)
							y -= entrou-dif;
							//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_BAIXO);
						// Não verifica a colisão na horizontal nesta linha
						baixo--;
						break;
					case C2D2M_SOLIDO22A:
					case C2D2M_SOLIDO157B:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO22A)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = (baixo+1)*mapa->dimensaoBlocoV-1-(y+alt);
						// Aqui colide e ajusta
						if(dif < entrou/2)
							y -= entrou/2-dif;
						// Não verifica a colisão na horizontal nesta linha
						baixo--;
						break;
					case C2D2M_SOLIDO22B:
					case C2D2M_SOLIDO157A:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO22B)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = (baixo+1)*mapa->dimensaoBlocoV-1-(y+alt);
						// Aqui colide e ajusta
						if(dif < (entrou+mapa->dimensaoBlocoV)/2)
						{
							y -= (entrou+mapa->dimensaoBlocoV)/2-dif;
							//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_BAIXO);
						}
						// Não verifica a colisão na horizontal nesta linha
						baixo--;
						break;
					}
				}

				// ajusta pela parte de cima
				if(topo >= 0 && topo<= mapa->altura)
				{
					bloco =mapa->camadas[mapa->camadaMarcas][mapa->largura*topo+meio]-mapa->offbloco; 
					switch(bloco)
					{
					case C2D2M_SOLIDO225:
					case C2D2M_SOLIDO315:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO315)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = y - (topo)*mapa->dimensaoBlocoV;
						// Aqui colide e ajusta
						if(dif < entrou)
							y += entrou-dif;
						// Não verifica a colisão na horizontal nesta linha
						topo++;
						break;
					case C2D2M_SOLIDO202B:
					case C2D2M_SOLIDO337A:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO337A)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = y - topo*mapa->dimensaoBlocoV;
						// Aqui colide e ajusta
						if(dif < entrou/2)
						{
							y += entrou/2-dif;
							//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_CIMA);
						}
						// Não verifica a colisão na horizontal nesta linha
						topo++;
						break;
					case C2D2M_SOLIDO202A:
					case C2D2M_SOLIDO337B:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO337B)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = y - topo*mapa->dimensaoBlocoV;
						// Aqui colide e ajusta
						if(dif < (entrou+mapa->dimensaoBlocoV)/2)
						{
							y += (entrou+mapa->dimensaoBlocoV)/2-dif;
							//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_CIMA);
						}
						// Não verifica a colisão na horizontal nesta linha
						topo++;
						break;
					}
				}
			}

			// Testa a colisão com blocos completamente sólidos
			for(int lin=topo;lin<=baixo;lin++)
			{
				// Testa se está dentro das coordenadas válidas
				if(lin < 0 || lin >= mapa->altura)
					continue;
				// Testa se ainda está dentro do mapa à esquerda 
				if(esquerda >= 0 && esquerda <= mapa->largura && dxl<0)
					// Testa se colidiu
					if(mapa->camadas[mapa->camadaMarcas][mapa->largura*lin+esquerda] == mapa->offbloco + C2D2M_SOLIDO)
					{
						// Se colidiu, aumenta um ponto e encerra
						x++; 
						break;
					}
				// Testa se ainda está dentro do mapa à direita 
				if(direita >= 0 && direita <= mapa->largura && dxl>0)
					// Testa se colidiu
					if(mapa->camadas[mapa->camadaMarcas][mapa->largura*lin+direita] == mapa->offbloco + C2D2M_SOLIDO)
					{
						// Se colidiu, diminui um ponto e encerra
						x--; 
						break;
					}
			}
		}
		// Teste de ajuste na vertical para quando não há gravidade
		if(dyl !=0 && (i * *dy)/maior != yant && !gravidade)
		{
			yant = (i * *dy)/maior;
			// Desloca
			y += dyl;
			// Calcula o bounding box
			topo = (y)/mapa->dimensaoBlocoV;
			baixo = (y+alt-1)/mapa->dimensaoBlocoV;
			esquerda = (x)/mapa->dimensaoBlocoH;
			direita = (x+larg-1)/mapa->dimensaoBlocoH;
			// Calcula o bloco em que se encontra o meio. Agora, o meio é na horizontal!
			meio = (y+alt/2)/mapa->dimensaoBlocoV;
			// Testa a colisão com blocos inclinados
			if(meio >=0 && meio <= mapa->altura && mapa->dimensaoBlocoH == mapa->dimensaoBlocoV)
			{
				// ajusta pela parte da esquerda
				if(esquerda >= 0 && esquerda<= mapa->largura)
				{
					bloco =mapa->camadas[mapa->camadaMarcas][mapa->largura*meio+esquerda]-mapa->offbloco; 
					switch(bloco)
					{
					case C2D2M_SOLIDO135:
					case C2D2M_SOLIDO225:
						entrou=y+alt/2 - meio*mapa->dimensaoBlocoV;
						if(bloco == C2D2M_SOLIDO135)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoV-entrou;						
						dif = x-esquerda*mapa->dimensaoBlocoH;
						// Aqui colide e ajusta
						if(dif < entrou)
							x += entrou-dif;
						// Encerra a verificação
						esquerda++;
						break;
					case C2D2M_SOLIDO157A:
					case C2D2M_SOLIDO202A:
						entrou=y+alt/2 - meio*mapa->dimensaoBlocoV;
						if(bloco == C2D2M_SOLIDO157A)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoV-entrou;						
						dif = x-esquerda*mapa->dimensaoBlocoH;
						// Aqui colide e ajusta
						if(dif < entrou*2)
						{
							x += entrou*2-dif;

						}
						// Encerra a verificação
						esquerda++;
						break;
					case C2D2M_SOLIDO157B:
					case C2D2M_SOLIDO202B:
						entrou=y+alt/2 - meio*mapa->dimensaoBlocoV;
						if(bloco == C2D2M_SOLIDO157B)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoV-entrou;						
						dif = x-esquerda*mapa->dimensaoBlocoH;
						// Aqui colide e ajusta
						if(dif < entrou*2 - mapa->dimensaoBlocoV)
						{
							x += entrou*2 - mapa->dimensaoBlocoV-dif;
						}
						// Encerra a verificação
						esquerda++;
						break;

					}
				}
				// ajusta pela parte da direita
				if(direita >= 0 && direita<= mapa->largura)
				{
					bloco =mapa->camadas[mapa->camadaMarcas][mapa->largura*meio+direita]-mapa->offbloco; 
					switch(bloco)
					{
					case C2D2M_SOLIDO45:
					case C2D2M_SOLIDO315:
						entrou=y +alt/2- meio*mapa->dimensaoBlocoV;
						if(bloco == C2D2M_SOLIDO45)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoV-entrou;						
						dif = (direita+1)*mapa->dimensaoBlocoH-1-(x+larg); 
						// Aqui colide e ajusta
						if(dif < entrou)
							x -= entrou-dif;
						// Encerra a verificação
						direita--;
						break;
					case C2D2M_SOLIDO22B:
					case C2D2M_SOLIDO337B:
						entrou=y +alt/2- meio*mapa->dimensaoBlocoV;
						if(bloco == C2D2M_SOLIDO22B)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoV-entrou;						
						dif = (direita+1)*mapa->dimensaoBlocoH-1-(x+larg); 
						// Aqui colide e ajusta
						if(dif < entrou*2)
						{
							x -= entrou*2-dif;
							//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_BAIXO);
						}
						// Encerra a verificação
						direita--;
						break;
					case C2D2M_SOLIDO22A:
					case C2D2M_SOLIDO337A:
						entrou=y +alt/2- meio*mapa->dimensaoBlocoV;
						if(bloco == C2D2M_SOLIDO22A)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoV-entrou;						
						dif = (direita+1)*mapa->dimensaoBlocoH-1-(x+larg); 
						// Aqui colide e ajusta
						if(dif < entrou*2-mapa->dimensaoBlocoV)
						{
							//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_BAIXO);
							x -= entrou*2-mapa->dimensaoBlocoV-dif;
						}
						// Encerra a verificação
						direita--;
						break;

					}

				}
			}
			// Testa a colisão com bloco completamente sólido na vertical
			for(int col=esquerda;col<=direita;col++)
			{
				// Testa se está dentro das coordenadas válidas
				if(col < 0 || col >= mapa->largura)
					continue;
				// Testa se ainda está dentro do mapa acima
				if(topo >= 0 && topo <= mapa->altura && dyl<0)
					// Testa se colidiu
					if(mapa->camadas[mapa->camadaMarcas][mapa->largura*topo+col] == mapa->offbloco + C2D2M_SOLIDO)
					{
						// Se colidiu, aumenta um ponto e encerra
						y++; //dxl;
						//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_CIMA);
						break;
					}
				// Testa se ainda está dentro do mapa abaixo 
				if(baixo >= 0 && baixo <= mapa->altura && dyl>0)
					// Testa se colidiu
					if(mapa->camadas[mapa->camadaMarcas][mapa->largura*baixo+col] == mapa->offbloco + C2D2M_SOLIDO)
					{
						// Se colidiu, diminui um ponto e encerra
						y--; //dxl;
						//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_BAIXO);
						break;
					}
			}


		}
		// Teste de ajuste na vertical para quando há gravidade e velocidade no y
		if(dyl != 0 && (i * *dy)/maior != yant && gravidade)
		{
			yant = (i * *dy)/maior;
			// Desloca
			y += dyl;
			// Calcula o bounding box
			topo = (y)/mapa->dimensaoBlocoV;
			baixo = (y+alt-1)/mapa->dimensaoBlocoV;
			esquerda = (x)/mapa->dimensaoBlocoH;
			direita = (x+larg-1)/mapa->dimensaoBlocoH;
			// Calcula o bloco em que se encontra o meio. Agora, o meio é na horizontal!
			meio = (x+larg/2)/mapa->dimensaoBlocoH;
			// Testa a colisão com blocos inclinados
			if(meio >=0 && meio <= mapa->largura && mapa->dimensaoBlocoH == mapa->dimensaoBlocoV)
			{
				// ajusta pela parte de baixo
				if(baixo >= 0 && baixo<= mapa->altura)
				{
					bloco =mapa->camadas[mapa->camadaMarcas][mapa->largura*baixo+meio]-mapa->offbloco; 
					switch(bloco)
					{
					case C2D2M_SOLIDO45:
					case C2D2M_SOLIDO135:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO45)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = (baixo+1)*mapa->dimensaoBlocoV-1-(y+alt);
						// Aqui colide e ajusta
						if(dif < entrou)
							y -= 1;//entrou-dif;
						// Não verifica a colisão na horizontal nesta linha
						//baixo--;
						esquerda++;
						break;
					case C2D2M_SOLIDO22A:
					case C2D2M_SOLIDO157B:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO22A)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = (baixo+1)*mapa->dimensaoBlocoV-1-(y+alt);
						// Aqui colide e ajusta
						if(dif < entrou/2)
							y --;//= entrou/2-dif;
						//baixo--;
						esquerda++;
						break;
					case C2D2M_SOLIDO22B:
					case C2D2M_SOLIDO157A:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO22B)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = (baixo+1)*mapa->dimensaoBlocoV-1-(y+alt);
						// Aqui colide e ajusta
						if(dif < (entrou+mapa->dimensaoBlocoV)/2)
						{
							y --;//= (entrou+mapa->dimensaoBlocoV)/2-dif;
						}
						// Não verifica a colisão na horizontal nesta linha
						//baixo--;
						esquerda++;
						break;
					}
				}

				// ajusta pela parte de cima
				if(topo >= 0 && topo<= mapa->altura)
				{
					bloco =mapa->camadas[mapa->camadaMarcas][mapa->largura*topo+meio]-mapa->offbloco; 
					switch(bloco)
					{
					case C2D2M_SOLIDO225:
					case C2D2M_SOLIDO315:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO315)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = y - (topo)*mapa->dimensaoBlocoV;
						// Aqui colide e ajusta
						if(dif < entrou)
							y += entrou-dif;
						//topo++;
						direita--;
						break;
					case C2D2M_SOLIDO202B:
					case C2D2M_SOLIDO337A:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO337A)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = y - topo*mapa->dimensaoBlocoV;
						// Aqui colide e ajusta
						if(dif < entrou/2)
							y += entrou/2-dif;
						// Não verifica a colisão na horizontal nesta linha
						//topo++;
						direita--;
						break;
					case C2D2M_SOLIDO202A:
					case C2D2M_SOLIDO337B:
						entrou=x+larg/2 - meio*mapa->dimensaoBlocoH;
						if(bloco == C2D2M_SOLIDO337B)
							entrou++;
						else
							entrou = mapa->dimensaoBlocoH-entrou;
						dif = y - topo*mapa->dimensaoBlocoV;
						// Aqui colide e ajusta
						if(dif < (entrou+mapa->dimensaoBlocoV)/2)
							y += (entrou+mapa->dimensaoBlocoV)/2-dif;

						// Não verifica a colisão na horizontal nesta linha
						//topo++;
						direita--;
						break;
					}
				}

			}
			// Testa a colisão com bloco completamente sólido na vertical
			for(int col=esquerda;col<=direita;col++)
			{
				// Testa se está dentro das coordenadas válidas
				if(col < 0 || col >= mapa->largura)
					continue;
				// Testa se ainda está dentro do mapa acima
				if(topo >= 0 && topo <= mapa->altura && dyl<0)
					// Testa se colidiu
					if(mapa->camadas[mapa->camadaMarcas][mapa->largura*topo+col] == mapa->offbloco + C2D2M_SOLIDO)
					{
						// Se colidiu, aumenta um ponto e encerra
						y++; //dxl;
						//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_CIMA);
						break;
					}
				// Testa se ainda está dentro do mapa abaixo 
				if(baixo >= 0 && baixo <= mapa->altura && dyl>0)
					// Testa se colidiu
					if(mapa->camadas[mapa->camadaMarcas][mapa->largura*baixo+col] == mapa->offbloco + C2D2M_SOLIDO)
					{
						// Se colidiu, diminui um ponto e encerra
						y--; //dxl;
						//ATOR_InsereEvento(a, EVT_COLIDIU_PAREDE_BAIXO);
						break;
					}
			}


		}



		// Para cada passo, verifica as colisões
		//ATOR_ColideBoundingBox(a, mapa);

	}
	// Atualiza o dx e dy
	*dx = x-xorig;
	*dy = y-yorig;
	return;// (cx || cy);
}

// Para um mapa, calcula a velocidade de um corpo de acordo com o tempo total da queda
// expresso em quadros (60 quadros por segundo)
bool C2D2M_CalculaQueda(unsigned int idMapa, int quadrosQueda, double *vqueda)
{
	if(idMapa == 0 || idMapa>=C2D2M_MAX_MAPAS)
		return false;
	// SE o mapa não foi inicializado, ou se a camada de marcas está na default (-1)
	if(!mapas[idMapa-1].inicializado || mapas[idMapa-1].camadaMarcas==-1)
		return false;
	
	// Recupera o mapa
	C2D2M_Mapa *mapa = &mapas[idMapa-1];
	// Se a gravidade do mapa for zerada ...
	if(mapa->gravidade == 0)
		return false;
	
    // Calcula o tempo, considerando 60 quadros por segundo
    double tempo = quadrosQueda*1.0/60.0;
    // Calcula a velocidade. 
	*vqueda = mapa->gravidade*tempo*tempo;
    // Limita a velocidade ("atrito com o ar")
	if(*vqueda > mapa->maxgravidade/60.0)	
        *vqueda = mapa->maxgravidade/60.0;
    // Retorna o resultado
    return true;
}

// Testa se um bounding box colide com um bloco qualquer de cenário.
//
// Data: 28/08/2008
//
bool C2D2M_ColidiuBlocoCenario(unsigned int idMapa, int x, int y, int larg, int alt, int bloco)
{
	if(idMapa == 0 || idMapa>=C2D2M_MAX_MAPAS)
		return false;
	// SE o mapa não foi inicializado, ou se a camada de marcas está na default (-1)
	if(!mapas[idMapa-1].inicializado || mapas[idMapa-1].camadaMarcas==-1)
		return false;
	
	// Recupera o mapa
	C2D2M_Mapa *mapa = &mapas[idMapa-1];
	// Intervalo de blocos em que verificamos a colisão
	int topo, baixo, esquerda, direita;
	// Calcula o extremo esquerdo
	topo = y/mapa->dimensaoBlocoV;
	esquerda = x/mapa->dimensaoBlocoH;
	// Calcula o extremo direito
	baixo = (y+alt-1)/mapa->dimensaoBlocoV;
	direita = (x+larg-1)/mapa->dimensaoBlocoH;

	// Indica se houve colisao
	bool cxy=false;

	// Para cada bloco no intervalo
	for(int lin=topo;lin<=baixo && ! cxy;lin++)
		for(int col=esquerda;col<=direita && !cxy ;col++)
		{
			// O bloco é válido para colisão?
			// Ele é SE:
			//			- Ele está dentro do mapa
			//		- Se ele é bloco (ele pode ser colidido)
			// Testa então se está dentro do mapa
			if(lin< 0 || lin >= mapa->altura || col < 0 || col >= mapa->largura)
				continue;
			// Testa se ele é válido
			if(mapa->camadas[mapa->camadaMarcas][mapa->largura*lin+col]==mapa->offbloco+bloco)
				cxy = true;
		}
	return cxy;
}



// Método para verificar se o cabeçalho do FMP/FMA é correto
//
// Data: 26/01/2005
// Última atualização: 29/07/2007

bool C2D2M_VerificaCabecalhoArquivo(CabecalhoArquivo *cabecalho)
{
    // Está certo por definição
    bool ok=true;
    // Verifica se tem o FORM no começo
    for(int i=0;i<4;i++)
        if(cabecalho->tipo[i] != cabecalhos[0][i])
        {
            ok=false;
            break;
        }
    if(!ok)
        return ok;
    // Verifica se tem o FMAP no subtipo
    for(int i=0;i<4;i++)
        if(cabecalho->subtipo[i] != cabecalhos[1][i])
        {
            ok=false;
            break;
        }
    return ok;
}

// Função para verificar o tipo do cabeçalho. Retorna um dos tipos enumerados
//
// Data: 26/01/2005
// Última atualização: 29/07/2007

int C2D2M_TipoBloco(CabecalhoBloco *cabecalho)
{    
    // Verifica se é o bloco do MPHD no começo
    bool ok=true;
    for(int i=0;i<4;i++)
        if(cabecalho->tipo[i] != cabecalhos[2][i])
        {
            ok=false;
            break;
        }
    if(ok)
        return CABECALHO_MPHD;
    // Verifica se é o bloco do ANDT no começo
    ok=true;
    for(int i=0;i<4;i++)
        if(cabecalho->tipo[i] != cabecalhos[3][i])
        {
            ok=false;
            break;
        }
    if(ok)
        return CABECALHO_ANDT;
    // Verifica se é o bloco do BODY no começo
    ok=true;
    for(int i=0;i<4;i++)
        if(cabecalho->tipo[i] != cabecalhos[4][i])
        {
            ok=false;
            break;
        }
    if(ok)
        return CABECALHO_BODY;
    // Verifica se é uma camada (o quarto caracter é o número da camada)
    ok=true;
    for(int i=0;i<3;i++)
        if(cabecalho->tipo[i] != cabecalhos[5][i])
        {
            ok=false;
            break;
        }
    if(ok)
        return CABECALHO_LYR;  
    
    // Por default
    return CABECALHO_IGNORADO;
}

// Função que recupera a camazda de marcas
//
// Data: 30/01/2010
//
//
unsigned int C2D2M_PegaCamadaMarcas(unsigned int idMapa)
{
	// � um mapa v�lido?
	if(idMapa==0 || idMapa >= C2D2M_MAX_MAPAS)
		return -1;
        return(mapas[idMapa-1].camadaMarcas);
}

// Função para mudar o valor de um bloco em uma camada específica
//
// Data: 30/01/2010
// �ltima atualiza��o: 30/01/2010
//
void C2D2M_AlteraBloco(unsigned int idMapa, int camada, int x, int y, unsigned int valor)
{
    // � um mapa v�lido?
    if(idMapa==0 || idMapa >= C2D2M_MAX_MAPAS)
        return;
    // Se n�o foi inicializado, falha!
    if(!mapas[idMapa-1].inicializado)
        return;
    // A camada � v�lida?
    if(camada<0 || camada>=C2D2M_MAX_CAMADAS)
        return;
    // Pega o ponteiro do mapa (mais r�pido)
    C2D2M_Mapa *mapa = &mapas[idMapa-1];
    // Testa se o layer existe
    if(mapa->camadas[camada] == 0)
        return;

    // So muda o bloco se ele estiver dentro do mapa
    if(x<0 || y<0 || x>=mapa->largura || y>=mapa->altura)
        return;
    mapa->camadas[camada][mapa->largura*y+x]=valor+mapa->offbloco;

}
