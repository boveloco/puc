#include "ClienteJogo.h"
#include <sstream>
#include <exception>

void ClienteJogo::CarregarVersaoDoDisco(){

	FILE* arquivo;
	int r;
	r = fopen_s(&arquivo, "versao.txt", "r");
	if (r != 0){

		std::stringstream erro;

		erro << __FUNCTION__
			<< "não foi possivel abrir o arquivo de versao do cliente: "
			<< r << std::endl;

		throw std::runtime_error(erro.str());
	}
	
	fread(&m_versao, sizeof(int), 1, arquivo);
	fclose(arquivo);
}

void ClienteJogo::CarregarDadosDoDisco(){

	std::stringstream caminho;
	caminho << "wowv"
		<< m_versao
		<< ".exe";


	int e;
	FILE* arquivo;
	e = fopen_s(&arquivo, caminho.str().c_str(), "rb");

	if (e != 0){
		std::stringstream erro;
		erro << __FUNCTION__
			<< " - Ocorreu um erro ao abrir o - Codigo: "
			<< e
			<< std::endl;

		throw std::runtime_error(erro.str());
	} else {
		fseek(arquivo, 0, SEEK_END);
		m_tamanho = ftell(arquivo);
		rewind(arquivo);

		m_dados = new char[m_tamanho];

		fread(m_dados,
			sizeof(char),
			m_tamanho,
			arquivo);

		fclose(arquivo);
	}
}

char* ClienteJogo::Dados() const{
	return m_dados;
}

long ClienteJogo::Tamanho() const{
	return m_tamanho;
}
int ClienteJogo::Versao() const{
	return m_versao;
}

ClienteJogo::ClienteJogo(){
	delete m_dados;
}