/*
Copyright 2006-2010, Fabio Vinicius Binder (fbinder@gmail.com)

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

#include <c2d2/chien2d2lua.h>


lua_State *estados_lua[C2D2_MAX_SPRITESET];

int volume_musica;

int volume_efeitos;

// Função que inicia a Chien2D 2 utilizando um arquivo de script lua.
//
// Data: 20/05/2010
//
bool C2D2_Inicia(const char *arquivo) {
	if (estado_lua_config == NULL)
		CL2_Inicia();

	int largura    = 640;
	int altura     = 480;
	int modo_video  = C2D2_JANELA;
	int tipo_render = C2D2_DESENHO_OPENGL;
	volume_musica = 50;
	volume_efeitos = 100;
	char titulo_janela[255] = "";

	if (CL2_AbreArquivo(arquivo)) {

		largura    = (int) CL2_Double("largura_janela");
		altura     = (int) CL2_Double("altura_janela");
		modo_video  = (int) CL2_Double("modo_video");
		tipo_render = (int) CL2_Double("tipo_render");
		strcpy(titulo_janela, CL2_String("titulo"));
		volume_musica  = (int) CL2_Double("volume_musica");
		volume_efeitos = (int) CL2_Double("volume_efeitos");
	}
	
	return C2D2_Inicia(largura, altura, modo_video, tipo_render, titulo_janela);
}

static int imprimir (lua_State *estado_lua)
{
   const char *msg = lua_tostring (estado_lua, 1);

   printf("Script: %s", msg);

   return 0;
}


// Função que carrega um spriteset a partir de um arquivo lua
//
// Data: 20/05/2010
//
unsigned int C2D2_CarregaSpriteSetLua(const char *arquivo_lua, char *chave) {

	int largura = 0;
	int altura = 0;
	char arquivo[1024] = "";

	lua_State *estado_lua;
	CL2_Inicia(&estado_lua);
	CL2_AbreArquivo(arquivo_lua, estado_lua);

	lua_pushcclosure (estado_lua, imprimir, 0);
	lua_setglobal (estado_lua, "imprimir");


	CL2_IniciaEstrutura(chave, estado_lua);
	largura    = (int) CL2_DoubleEstrutura("largura_quadro", estado_lua);
	altura     = (int) CL2_DoubleEstrutura("altura_quadro", estado_lua);
	CL2_StringEstrutura(arquivo, "arquivo", estado_lua);

	int aux = C2D2_CarregaSpriteSet(arquivo, largura, altura);

	estados_lua[aux] = estado_lua;

	return aux;
}

void C2D2_RemoveSpriteSetLua(unsigned int id) {

	CL2_Encerra(estados_lua[id]);

	C2D2_RemoveSpriteSet(id);
}

bool C2D2_DesenhaSpriteLua(unsigned int id, unsigned int *frame, int *x, int *y, int *info) {

	enum { SPRITE_FRAME, SPRITE_X, SPRITE_Y, SPRITE_INFO }; 
	int args[] = { id, *frame, *x, *y, (info!=NULL)?*info:0 };
	int res[4];

	if (CL2_ChamaFuncaoLua("atualizar", args, 5, res, 4, estados_lua[id])) {
		*frame = res[SPRITE_FRAME];
		*x = res[SPRITE_X];
		*y = res[SPRITE_Y];
		if (info != NULL)
			*info = res[SPRITE_INFO];
	}

	return C2D2_DesenhaSprite(id, *frame, *x, *y);
}

