/*
Copyright 2008-2010, Fabio Vinicius Binder (fbinder@gmail.com)

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
	Chien2D v2.0 Lua Integration
	Por Fabio Vinicius Binder - fabio.binder@pucpr.br

	Data: 20/05/2010


**/
#ifndef __CHIEN2D2LUA_H__
#define __CHIEN2D2LUA_H__

	#include <c2d2/chien2d2.h>
	#include <c2d2/chienlua2.h>

	bool C2D2_Inicia(const char *arquivo = "config.lua");

	unsigned int C2D2_CarregaSpriteSetLua(const char *arquivo_lua, char *chave = "sprite");

	void C2D2_RemoveSpriteSetLua(unsigned int id);

	bool C2D2_DesenhaSpriteLua(unsigned int id, unsigned int *indice, int *x, int *y, int *info = NULL);

	extern int volume_musica;

	extern int volume_efeitos;

	extern lua_State *estados_lua[C2D2_MAX_SPRITESET];
	
#endif
