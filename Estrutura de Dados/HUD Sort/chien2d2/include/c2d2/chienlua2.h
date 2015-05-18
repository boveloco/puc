/*
Copyright 2010, Fabio Vinicius Binder (fbinder@gmail.com)

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

	Data: 12/05/2010


**/
#ifndef __CHIENLUA_H__
#define __CHIENLUA_H__


	extern "C" 
	{
		#if defined(linux)
			#include <lua5.1/lua.h>
			#include <lua5.1/lauxlib.h>
			#include <lua5.1/lualib.h>
		#else		
			#include <lua.h>
			#include <lauxlib.h>
			#include <lualib.h>
		#endif
	};

	extern lua_State *estado_lua_config;

	void CL2_Inicia(lua_State **estado_lua = &estado_lua_config);

	bool CL2_AbreArquivo(const char *arquivo, lua_State *estado_lua = estado_lua_config);

	void CL2_Encerra(lua_State *estado_lua = estado_lua_config);

	bool CL2_ChamaFuncaoLua(char *funcao, int *args, int n_args , int *res, int n_res, lua_State *estado_lua = estado_lua_config);

	double CL2_Double(char *nome, lua_State *estado_lua = estado_lua_config);

	bool CL2_Bool(char *nome, lua_State *estado_lua = estado_lua_config);

	const char *CL2_String(char *nome, lua_State *estado_lua = estado_lua_config);

	void CL2_IniciaEstrutura(char *nome, lua_State *estado_lua = estado_lua_config);

	//
	//Funções CL2_???Estrutura
	//Objetivo: pegam valores de dentro de uma estrutura composta
	//Parâmetros: nome do campo
	//
	void CL2_StringEstrutura(char *saida, const char *nome, lua_State *estado_lua = estado_lua_config);

	double CL2_DoubleEstrutura(char *nome, lua_State *estado_lua = estado_lua_config);

	bool CL2_BoolEstrutura(char *nome, lua_State *estado_lua = estado_lua_config);

	//
	//Função CL2_IniciaSubEstrutura
	//Objetivo: abre uma sub estrutura (estrutura dentro de estrutura)
	//Parâmetros: nome da sub estrutura
	//
	void CL2_IniciaSubEstrutura(char *nome, lua_State *estado_lua = estado_lua_config);

	//
	//Função CL2_EncerraSubEstrutura
	//Objetivo: finaliza sub estrutura
	//
	void CL2_EncerraSubEstrutura(lua_State *estado_lua = estado_lua_config);


#endif