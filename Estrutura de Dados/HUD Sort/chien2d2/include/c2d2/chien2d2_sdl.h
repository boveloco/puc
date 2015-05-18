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
#ifndef CHIEN2D2_SDL_H
#define CHIEN2D2_SDL_H

/**
	Chien2D v2.0 Render SDL

	Data: 29/03/2007

	Histórico de alterações:

	Data       Programador      Alteração
	========== ================ ======================================================================
	21/05/2010 Fabio Binder		Inserido o parâmetro de quadros/s em C2D2_Sincroniza

**/
#include <c2d2/chien2d2.h>
#include <c2d2/chien2d2_interno.h>

// Ponteiros das funções da Chien2D2

// Função que inicia a Chien2D 2
bool C2D2SDL_Inicia(unsigned int largura, unsigned int altura, int modoVideo, char *tituloJanela);
// Função que encerra a Chien2D 2
void C2D2SDL_Encerra();
// Função para sincronizar o vídeo a n fps
void C2D2SDL_Sincroniza(Uint8 fps);
// Função para limpar a tela
void C2D2SDL_LimpaTela();
// Função para escolher a cor de limpeza da tela
void C2D2SDL_TrocaCorLimpezaTela(Uint8 r, Uint8 g, Uint8 b);
// Função para limitar a área de desenho da tela
void C2D2SDL_LimitaAreaDesenho(int x, int y, int largura, int altura);
// Função para eliminar o limite da área de desenho
void C2D2SDL_LiberaAreaDesenho();

// Funções para manipular sprites

// função para carregar um sprite set na memória
unsigned int C2D2SDL_CarregaSpriteSet(const char *arquivo, int largura, int altura);
// Remove um sprite set da memória
void C2D2SDL_RemoveSpriteSet(unsigned int id);
// Função para desenhar um sprite
bool C2D2SDL_DesenhaSprite(unsigned int identificador, unsigned int indice, int x, int y);
// Função para desenhar um sprite distorcido e com efeitos de cor/alpha
bool C2D2SDL_DesenhaSpriteEfeito(unsigned int identificador, unsigned int indice, int x[4], int y[4], Uint8 r, Uint8 g, Uint8 b, Uint8 a);


#endif
