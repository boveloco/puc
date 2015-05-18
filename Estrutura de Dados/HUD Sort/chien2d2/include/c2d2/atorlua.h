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

	Data: 27/07/2010


**/
#ifndef __ATORLUA_H__
#define __ATORLUA_H__


#include <c2d2/ator.h>
#include <c2d2/chienlua2.h>

#define ATOR_LUA_INI 1024

bool ATOR_CarregaAtorEstaticoLua(const char *arquivo_lua, 
								 bool (*atualiza)(Ator* a, unsigned int mapa), char *chave = "ator");

#endif
