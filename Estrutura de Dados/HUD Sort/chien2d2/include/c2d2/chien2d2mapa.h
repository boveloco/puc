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

	Data		Programador	Alteração
	=====================================================
	13/08/2010	Paulo VW Radtke	Funções para mudar uma camada do mapa e para pegar a camada de marcas.


**/


#ifndef C2D2MAPA_H
#define C2D2MAPA_H

// Defines específicos para mapas do Mappy

// O número máximo de camadas do mapa (0 é mais inferior, 8 é a mais em cima)
#define C2D2M_MAX_CAMADAS	8
// O número máximo de azulejos animados (estamos sendo conservadores)
#define C2D2M_MAX_ANISTR	256
// O número máximo de sequências de animação (conservador)
#define C2D2M_MAX_ANIMSEQ	2048
// O número máximo de mapas utilizados
#define C2D2M_MAX_MAPAS		32
#define C2D2M_TAM_PATH		2048	// Tamanho do path de um arquivo completo em bytes


// Constantes para blocos marcadores no mapa
enum {C2D2M_INICIO, C2D2M_FIM, C2D2M_CHECK, 
// Os tipos de blocos de parede
C2D2M_SOLIDO, 
// blocos com inclinação de 45º e 22,5º
C2D2M_SOLIDO45, C2D2M_SOLIDO22A, C2D2M_SOLIDO22B,
// blocos com inclinação de 135º e 157,5º 
C2D2M_SOLIDO135, C2D2M_SOLIDO157A, C2D2M_SOLIDO157B,
// blocos com inclinação de 225º e 202,5º
C2D2M_SOLIDO225, C2D2M_SOLIDO202A, C2D2M_SOLIDO202B,
// blocos com inclinação de 315º e 337,5º
C2D2M_SOLIDO315, C2D2M_SOLIDO337A, C2D2M_SOLIDO337B,

// Os tipos de escada
C2D2M_ESCADAV, C2D2M_ESCADA_SD, C2D2M_ESCADA_SE, 
C2D2M_MORTE,  C2D2M_PROG};

// These structs and defines are from the original MAPPY source files
// © Robin Burrows <rburrows@bigfoot.com>
// http://www.tilemap.co.uk

// DEfines dos tipos de animação
#define AN_END -1
#define AN_NONE 0
#define AN_LOOPF 1
#define AN_LOOPR 2
#define AN_ONCE 3
#define AN_ONCEH 4
#define AN_PPFF 5
#define AN_PPRR 6
#define AN_PPRF 7
#define AN_PPFR 8
#define AN_ONCES 9


// Defines para os tipos de header
enum {CABECALHO_IGNORADO, CABECALHO_FORM, CABECALHO_FMAP, CABECALHO_MPHD, 
        CABECALHO_ANDT, CABECALHO_BODY, CABECALHO_LYR };

// Tipos para o mapa

// Header do arquivo do MAPPY
typedef struct {	/* Map header structure */
/* char M,P,H,D;	4 byte chunk identification. */
/* long int mphdsize;	size of map header. */
char mapverhigh;	/* map version number to left of . (ie X.0). */
char mapverlow;		/* map version number to right of . (ie 0.X). */
char lsb;		/* if 1, data stored LSB first, otherwise MSB first. */
char maptype;	/* 0 for 32 offset still, -16 offset anim shorts in BODY added FMP0.5*/
short int mapwidth;	/* width in blocks. */
short int mapheight;	/* height in blocks. */
short int reserved1;
short int reserved2;
short int blockwidth;	/* width of a block (tile) in pixels. */
short int blockheight;	/* height of a block (tile) in pixels. */
short int blockdepth;	/* depth of a block (tile) in planes (ie. 256 colours is 8) */
short int blockstrsize;	/* size of a block data structure */
short int numblockstr;	/* Number of block structures in BKDT */
short int numblockgfx;	/* Number of 'blocks' in graphics (BODY) */
unsigned char ckey8bit, ckeyred, ckeygreen, ckeyblue; /* colour key values added FMP0.4*/
/* info for non rectangular block maps added FMP0.5*/
short int blockgapx, blockgapy, blockstaggerx, blockstaggery;
short int clickmask, pillars;
} MPHD;

// Estrutura da animação
typedef struct { /* Animation control structure */
signed char antype;	/* Type of anim, AN_? */
signed char andelay;	/* Frames to go before next frame */
signed char ancount;	/* Counter, decs each frame, till 0, then resets to andelay */
signed char anuser;	/* User info */
//long 
int ancuroff;	/* Points to current offset in list */
//long 
int anstartoff;	/* Points to start of blkstr offsets list, AFTER ref. blkstr offset */
//long 
int anendoff;	/* Points to end of blkstr offsets list */
} ANISTR;

typedef struct
{
    char tipo[4];
    int tamanho;
}CabecalhoBloco;

typedef struct
{
    char tipo[4];
    int tamanho;
    char subtipo[4];
}CabecalhoArquivo;

typedef struct C2D2M_Mapa
{
	// Indica se o mapa foi inicializado ou não
	bool inicializado;
    // O apelido da fonte. Único no sistema
    char apelido[C2D2M_TAM_PATH];
    // Quantos elementos usam esse mapa. Elimina quando chega a zero
    int usuarios;
    // A largura e altura do mapa em BLOCOS
    int largura, altura;
	// A largura e altura do mapa em pixels (útil pra evitar multiplicações excessivas)
	int larguraPixel, alturaPixel;
    // O tamanho do spriteset usado (deve bater com o arquivo utilizado)
    int dimensaoBlocoH, dimensaoBlocoV;
    // AS camadas do mapa. 0 é a camada de base e 7 é a mais em cima
	short int *camadas[C2D2M_MAX_CAMADAS];
	// A velocidade de cada camada. Se for zero, a camada está desabilitada
	int vCamadas[C2D2M_MAX_CAMADAS];
    //   As estruturas de animação, são referenciadas como números negativos
    // nas camadas
    ANISTR estrutAnimacao[C2D2M_MAX_ANISTR];
    //   As sequências de animação, referenciadas na estrutAnimacao
    //long 
	int seqAnimacao[C2D2M_MAX_ANIMSEQ];
	// O spriteset utilizado pelo mapa
	unsigned int idSpriteset;
	// As coordenadas x,y do ponto de referência do mapa
	int x,y;
	// A camada que serve como marcador de cenário
	int camadaMarcas;
	// O índice do bloco inicial das marcas
	int offbloco;
	
	// Campos para a busca do próximo bloco
	
	// Indica que está buscando o proximo
	bool buscaProximo;
	// Indica qual o bloco que está buscando
	int blocoProcurado;
	// Indica em qual posição achou o último bloco
	int posicaoUltimo;

    // A gravidade do jogo. Default é zero para que o personagem não caia em mapas top view
    double gravidade;
    // O limite da velocidade de queda em pixels por segundo (simula atrito)
    double maxgravidade;

}C2D2M_Mapa;


// Funções para manipular os mapas

// Função que inicia a Chien2D 2 Mapa
bool C2D2M_Inicia();
// Função que encerra a Chien2D 2 Mapa
void C2D2M_Encerra();

//Função que carrega uma mapa no formato Mappy
unsigned int C2D2M_CarregaMapaMappy(const char *arquivo_mapa, const char *arquivo_spriteset);
// Remove os dados alocados do mapa e marca como não inicializado
void C2D2M_RemoveMapa(unsigned int id);
// Função para procurar um mapa pelo nome na memória
unsigned int C2D2M_ProcuraMapa(const char *apelido);
// Função para ver as dimensões do mapa (retorna true se o mapa é válido, false caso contrário)
bool C2D2M_DimensoesMapa(unsigned int idMapa, int *largura, int *altura);
// Posiciona o mapa nas coordenadas x,y indicadas
void C2D2M_PosicionaMapa(unsigned int idMapa, int x, int y);
// Recupera a posição do mapa
bool C2D2M_PosicaoXY(unsigned int idMapa, int *x, int *y);
// Função para dar velocidade a uma camada do mapa
void C2D2M_VelocidadeCamadaMapa(unsigned int idMapa, int camada, int velocidade);
// Função para indicar a camada de marcas do mapa e o número do bloco inicial de marcas no cenário
bool C2D2M_CamadaMarcas(unsigned int idMapa, int camada, int offbloco);
// Anima os blocos animados do mapa. Atualiza os quadros, reseta e etc e tal
void C2D2M_AnimaMapa(unsigned int id);
// Ajusta o deslocamento de um boundingbox no mapa. Recebe o mapa aonde o deslocamento
// se realiza, a posição do bounding box e o quanto deve mover no eixo x e y. O booleano
// gravidade indica se há gravidade, situação na qual deslocamentos verticais para baixo
// e cima não implicam em deslocamento no eixo x. 
//
//  Os parâmetros são o identificador do mapa (idMapa), as coordenadas do bounding box
// (x,y), as dimensões do bounding box (largura), os ponteiros para o deslocamento 
// (dx e dy) e se a gravidade está em ação.
void C2D2M_AjustaDeslocamento(unsigned int idMapa, int xorig, int yorig, 
							  int larg, int alt, int *dx, int *dy, bool gravidade);

// Testa se um bounding box colide com um bloco qualquer de cenário.
bool C2D2M_ColidiuBlocoCenario(unsigned int idMapa, int x, int y, int larg, int alt, int bloco);

// Para um mapa, calcula a velocidade de queda de um corpo de acordo com o tempo total da queda
// expresso em quadros (60 quadros por segundo)
bool C2D2M_CalculaQueda(unsigned int idMapa, int quadrosQueda, double *vqueda);

// Função para desenhar uma camada do mapa na tela a partir das coordenadas do mapa (x,y na estrutura), dentro da janela especificada
// por xtela,ytela e as dimensões largura e altura.
void C2D2M_DesenhaCamadaMapa(unsigned int idMapa, int camada, int xtela, int ytela, int largura, int altura);        
// função para buscar as coordenadas da primeira ocorrência de um bloco de marca dentro de um mapa
bool C2D2M_PrimeiroBlocoMarca(unsigned int idMapa, int bloco, int *x, int *y);
// função para buscar as coordenadas da próxima ocorrência de um bloco de marca dentro de um mapa. Usar apenas APÓS C2D2M_PrimeiroBlocoMarca.
bool C2D2M_ProximoBlocoMarca(unsigned int idMapa, int *x, int *y);

// Função que indica a aceleração de gravidade do mapa, em pixels por segundo, e a velocidade máxima de gravidade para simular atrito
bool C2D2M_GravidadeMapa(unsigned int idMapa, double gravidade, double maxgravidade);

// Método para verificar se o cabeçalho do FMP/FMA é correto
bool C2D2M_VerificaCabecalhoArquivo(CabecalhoArquivo *cabecalho);
// Método para verificar o tipo do cabeçalho. Retorna um dos tipos enumerados
int C2D2M_TipoBloco(CabecalhoBloco *cabecalho);

// Função que recupera a camazda de marcas
unsigned int C2D2M_PegaCamadaMarcas(unsigned int idMapa);
// Função para mudar o valor de um bloco em uma camada específica
void C2D2M_AlteraBloco(unsigned int idMapa, int camada, int x, int y, unsigned int valor);


#endif
