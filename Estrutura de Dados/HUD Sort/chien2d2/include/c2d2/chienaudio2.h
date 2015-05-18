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
#ifndef CA2_H
#define CA2_H

/**
	ChienAudio v2.0

	Data: 02/02/2008

**/

// Includes da SDL
#if	defined(linux)
	#include <SDL/SDL.h>
	#include <SDL/SDL_mixer.h>
#else
	#include <SDL.h>
	#include <SDL_mixer.h>
#endif

// Defines úteis

#define CA2_TAM_PATH		2048	// Tamanho do path de um arquivo completo em bytes
#define CA2_MAX_EFEITOS		512		// Número máximo de efeitos que podem ser carregados
#define CA2_MAX_MUSICAS		8		// Número máximo de músicas que podem ser carregadas
#define CA2_BUFFER_MIXER	2048	// Tamanho do buffer do mixer
#define CA2_DIREITA			100		// Som completamente à direita
#define CA2_ESQUERDA		-100	// Som completamente à esquerda
#define CA2_CENTRO			0		// Som no centro (ambos canais)


//     A estrutura CA2_Efeito possui campos relacionados aos efeitos no sistema.
//
// Data: 02/02/2008

typedef struct CA2_Efeito
{
    // Quantos elementos usam esse efeito. Elimina quando chega a zero
    int usuarios;
    // O apelido efeito. Único no sistema
    char apelido[CA2_TAM_PATH];
    // O tempo total do efeito
    long tempo;
	// Os dados do efeito lido do disco
    Mix_Chunk *efeito; 
} CA2_Efeito;    

//     A estrutura CA2_Efeito possui campos relacionados aos efeitos no sistema.
//
// Data: 02/02/2008

typedef struct CA2_Musica
{
    // Quantos elementos usam essa musica. Elimina quando chega a zero
    int usuarios;
    // O apelido efeito. Único no sistema
    char apelido[CA2_TAM_PATH];
    // O tempo total da música em milisegundos
    long tempo;
	// Os dados do efeito lido do disco
	Mix_Music *musica;
} CA2_Musica;    


// Função que inicia a Chien Audio 2
bool CA2_Inicia();
// Função que encerra a Chien2D 2
void CA2_Encerra();

// Função que ajusta o volume dos efeitos e músicas no sistema
bool CA2_AjustaVolume(unsigned int musica, unsigned int efeitos);

// Funções para manipular efeitos sonoros

// função para carregar um efeito na memória
unsigned int CA2_CarregaEfeito(const char *arquivo);
// Remove um efeitos da memória
void CA2_RemoveEfeito(unsigned int id);
// Procura um efeito pelo nome do arquivo original
unsigned int CA2_ProcuraEfeito(const char *apelido);
// Função para tocar um efeito
int CA2_TocaEfeito(unsigned int id, int posicao);
// Função para tocar um efeito em loop
int CA2_TocaEfeitoLoop(unsigned int id, int posicao, int loop);
// Função para tocar um efeito posicional baseado na coordenada x na tela
int CA2_TocaEfeitoTela(unsigned int id, int posicao);
// Função para cortar o efeito tocando em um canal
int CA2_CortaEfeito(int canal, int tempo);
// Função para pausar a reprodução de efeitos sonoros
void CA2_PausaEfeitos();
// Função para continuar a reprodução de efeitos sonoros (após pausa)
void CA2_ContinuaEfeitos();

// Funções para manipular músicas

// função para carregar um efeito na memória
unsigned int CA2_CarregaMusica(const char *arquivo);
// Remove uma música da memória
void CA2_RemoveMusica(unsigned int id);
// Procura uma música pelo nome do arquivo original
unsigned int CA2_ProcuraMusica(const char *apelido);
// Função para tocar uma música
bool CA2_TocaMusica(unsigned int id, int vezes);
// Função para pausar a reprodução da música
void CA2_PausaMusica();
// Função para continuar a reprodução da música (após pausa)
void CA2_ContinuaMusica();
// Função para encerrar a reprodução da música (fade)
void CA2_FadeMusica(unsigned int tempo);
#endif
