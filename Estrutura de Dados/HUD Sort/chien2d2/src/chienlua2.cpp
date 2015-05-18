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
	Data: 18/08/2010

	Histórico de alterações:

	Data       Programador      Alteração
	========== ================ ======================================================================
	18/08/2010 Bruno Sanches    Corrigido bug de retornar endereço de variavel local em CL2_StringEstrutura

**/

#include <string.h>
#include <c2d2/chienlua2.h>

lua_State *estado_lua_config = NULL;

void CL2_Inicia(lua_State **estado_lua) {

	// Abre estado lua
	*estado_lua = lua_open (); 

	luaL_openlibs (*estado_lua);
}

bool CL2_AbreArquivo(const char *arquivo, lua_State *estado_lua) {
	int status = luaL_loadfile(estado_lua, arquivo);
	
	if (status == 0) {
		if (lua_pcall(estado_lua, 0, LUA_MULTRET, 0) == 0)
			return true;
	}

	printf("Lua erro: %s\n", lua_tostring(estado_lua, -1));
	return false;
}

void CL2_Encerra(lua_State *estado_lua) {
	lua_close (estado_lua);
}

bool CL2_ChamaFuncaoLua(char *funcao, int *args, int n_args , int *res, int n_res, lua_State *estado_lua) {

	// Procura função
	lua_getglobal(estado_lua, funcao);

	// Insere atributos
	for (int i = 0; i < n_args; i++)
		lua_pushnumber(estado_lua, args[i]);

	//lua_pushstring (estado_lua, funcao);
	//lua_gettable (estado_lua, LUA_GLOBALSINDEX);  

	// Chama função
	int cod_erro = 0;

	bool sem_erros = !lua_pcall(estado_lua, n_args, n_res, cod_erro);

	for (int i = 0; i < n_res; i++)
	  res[i] = (int)lua_tonumber(estado_lua, -(n_res - i));

	return sem_erros;
}

double CL2_Double(char *nome, lua_State *estado_lua)
{
	lua_getglobal(estado_lua, nome);
	return (double)lua_tonumber(estado_lua, -1);
}

bool CL2_Bool(char *nome, lua_State *estado_lua)
{
	lua_getglobal(estado_lua, nome);
	return lua_toboolean(estado_lua, -1) ? true : false;
}

const char *CL2_String(char *nome, lua_State *estado_lua)
{
	lua_getglobal(estado_lua, nome);
	return lua_tostring(estado_lua, -1);
}

void CL2_IniciaEstrutura(char *nome, lua_State *estado_lua)
{
	lua_getglobal(estado_lua, nome); 
}

//
//Funções CL2_???Estrutura
//Objetivo: pegam valores de dentro de uma estrutura composta
//Parâmetros: nome do campo
//
void CL2_StringEstrutura(char *out, const char *nome, lua_State *estado_lua)
{	
	lua_pushstring(estado_lua, nome);
	lua_gettable(estado_lua, -2);
	strcpy(out, lua_tostring(estado_lua,-1)); 
	lua_pop(estado_lua,1);
}

double CL2_DoubleEstrutura(char *nome, lua_State *estado_lua)
{
	double aux;
	lua_pushstring(estado_lua, nome);
	lua_gettable(estado_lua, -2);
	aux = (double)lua_tonumber(estado_lua,-1); 
	lua_pop(estado_lua,1);

	return aux;
}

bool CL2_BoolEstrutura(char *nome, lua_State *estado_lua)
{
	bool aux;
	lua_pushstring(estado_lua, nome);
	lua_gettable(estado_lua, -2);
	aux = lua_toboolean(estado_lua,-1) ? true : false; 
	lua_pop(estado_lua,1);

	return aux;
}

//
//Função CL2_IniciaSubEstrutura
//Objetivo: abre uma sub estrutura (estrutura dentro de estrutura)
//Parâmetros: nome da sub estrutura
//
void CL2_IniciaSubEstrutura(char *nome, lua_State *estado_lua)
{
	lua_pushstring(estado_lua, nome);
	lua_gettable(estado_lua, -2);
}

//
//Função CL2_EncerraSubEstrutura
//Objetivo: finaliza sub estrutura
//
void CL2_EncerraSubEstrutura(lua_State *estado_lua)
{
	lua_pop(estado_lua,1);
}

