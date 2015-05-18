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

#include <c2d2/atorlua.h>
#include <c2d2/chien2d2lua.h>

#include <string.h>


// Carrega dados do ator a partir de um arquivo de script lua
bool ATOR_CarregaAtorEstaticoLua(const char *arquivo_lua, 
								 bool (*atualiza)(Ator* a, unsigned int mapa), char *chave) {

	// Nome do arquivo
	char arquivo[1024] = "";

	// Inicia lua
	lua_State *estado_lua;
	CL2_Inicia(&estado_lua);
	CL2_AbreArquivo(arquivo_lua, estado_lua);

	// Carrega dados simples
	CL2_IniciaEstrutura(chave, estado_lua);
	int id         = (int) CL2_DoubleEstrutura("id", estado_lua);
	int largura    = (int) CL2_DoubleEstrutura("largura_quadro", estado_lua);
	int altura     = (int) CL2_DoubleEstrutura("altura_quadro", estado_lua);
	int xbb        = (int) CL2_DoubleEstrutura("x_bounding_box", estado_lua);
	int ybb        = (int) CL2_DoubleEstrutura("y_bounding_box", estado_lua);
	int lbb        = (int) CL2_DoubleEstrutura("largura_bounding_box", estado_lua);
	int abb        = (int) CL2_DoubleEstrutura("altura_bounding_box", estado_lua);
	bool rotacionar = CL2_BoolEstrutura("rotacionar", estado_lua);
	CL2_StringEstrutura(arquivo, "arquivo", estado_lua);

	// Inicia animações
	CL2_IniciaSubEstrutura("animacoes", estado_lua);

	int qtde = (int)lua_objlen(estado_lua, -1) / 2;

	Animacao *animacoes = new Animacao[qtde];

	// Carrega animações
	for (int i = 0; i < qtde; i++) {

		// Tempo
		lua_pushinteger(estado_lua, i*2+1);
		lua_gettable(estado_lua, -2);
		animacoes[i].tempo = lua_tointeger(estado_lua, -1);
		lua_pop(estado_lua,1);

		// Sequência de animação
		lua_pushinteger(estado_lua, i*2+2);
		lua_gettable(estado_lua, -2);

		animacoes[i].numQuadros = (int)lua_objlen(estado_lua, -1);
		int *quadros = new int[animacoes[i].numQuadros];

		for (unsigned int j = 1; j <= animacoes[i].numQuadros; j++) {
			lua_pushinteger(estado_lua, j);
			lua_gettable(estado_lua, -2);
			quadros[j-1] = lua_tointeger(estado_lua, -1);
			lua_pop(estado_lua, 1);
		}

		memcpy(animacoes[i].quadros, quadros, animacoes[i].numQuadros * sizeof(int));

		
		lua_pop(estado_lua,1);
	}	

	CL2_EncerraSubEstrutura(estado_lua);


	bool aux = ATOR_CarregaAtorEstatico(id, arquivo, largura, altura, 
		                                xbb, ybb, lbb, abb, animacoes, rotacionar, 0, 0, atualiza, qtde);
 
	estados_lua[id+ATOR_LUA_INI] = estado_lua;

	return aux;

}
