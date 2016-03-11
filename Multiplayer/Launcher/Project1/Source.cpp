#include <WinSock2.h>
#include <WS2tcpip.h>
#include <exception>
#include <iostream>
#include <sstream>
//classess
#include "ClienteJogo.h"


#define PORTA_SERVIDOR 9000

enum Comandos
{
	NUMERO_ULTIMAA_VERSAO_CLIENTE_REQ,
	EXECUTAVEL_ULTIMA_VERSAO_CLIENTE_REQ
};

void receberTcp(const SOCKET& socket, void* buffer, const long& tamanhoBuffer);

int main(){
	try{
		ClienteJogo* ultimoCliente = new ClienteJogo();

		WSAData wsaData;
		WSAStartup(MAKEWORD(2, 2), &wsaData);

		sockaddr_in enderecoServidor;

		inet_pton(AF_INET, "0.0.0.0", &enderecoServidor.sin_addr);
		enderecoServidor.sin_family = AF_INET;
		enderecoServidor.sin_port = htons(PORTA_SERVIDOR);

		SOCKET principalSocket;
		principalSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

		bind(principalSocket, (SOCKADDR*)&enderecoServidor, sizeof(sockaddr_in);

		listen(principalSocket, SOMAXCONN);

		SOCKET clienteSocket;
		sockaddr_in clienteEndereco;
		int clienteEnderecoTam = sizeof(sockaddr_in);
		
		for (;;){
			clienteSocket = accept(principalSocket, (SOCKADDR*)&clienteEndereco, &clienteEnderecoTam);

			for (;;){
				try{
					int comando;
					int ultimaVersaoCliente;

					receberTcp(clienteSocket, &comando, sizeof(int));

					switch (comando){
					case Comandos::NUMERO_ULTIMAA_VERSAO_CLIENTE_REQ:
					}
				}
				catch (std::exception& ex){
					std::cout << ex.what();
					break;
				}
			}
		}
	}
	catch (std::exception& ex){
		std::cout << ex.what();
		system("PAUSE");
		return 1;
	}
	return 0;

}

void receberTcp(const SOCKET& socket, void* buffer, const long& tamanhoBuffer){

	long bytesRecebidos			= 0;
	long totalBytesRecebidos	= 0;

	do{

		bytesRecebidos = recv(socket, (char*)buffer + totalBytesRecebidos, tamanhoBuffer - totalBytesRecebidos, NULL);

		if (bytesRecebidos == SOCKET_ERROR || bytesRecebidos == 0){

			std::stringstream erro;

			erro << __FUNCTION__
				<< " - A conexao foi encerrada durante a transmissao: "
				<< WSAGetLastError()
				<< std::endl;

			throw std::runtime_error(erro.str());
		}

		totalBytesRecebidos += bytesRecebidos;
	} while (totalBytesRecebidos != tamanhoBuffer);

}