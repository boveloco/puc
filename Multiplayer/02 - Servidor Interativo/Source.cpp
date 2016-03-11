#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define MAX_ERROR_MSG 200

#include <WinSock2.h>
#include <iostream>

void show_error (char* message) {
	std::cout << message << std::endl;
	system("pause");
}

int main() {
	int r = 0;
	char* error_message = new char[MAX_ERROR_MSG];

	WSADATA wsaData;

	r = WSAStartup(MAKEWORD(2, 2), &wsaData);

	if (r != 0) {
		sprintf_s(error_message, MAX_ERROR_MSG, "Ocorreu um erro na inicialização do WinSock: %d", WSAGetLastError());
		show_error(error_message);
		return 1;
	}

	sockaddr_in enderecoServidor;
	enderecoServidor.sin_addr.s_addr = inet_addr("0.0.0.0");		//IP do Servidor
	enderecoServidor.sin_family = AF_INET;							//IPV4
	enderecoServidor.sin_port = htons(9999);						//Porta do Servidor

	SOCKET principalSocket = socket(AF_INET,
									SOCK_STREAM,
									IPPROTO_TCP);

	if (principalSocket == INVALID_SOCKET) {
		sprintf_s(error_message, MAX_ERROR_MSG, "Ocorreu um erro ao criar o socket: %d", WSAGetLastError());
		show_error(error_message);
		return 1;
	}

	r = bind(principalSocket, (SOCKADDR *) &enderecoServidor, sizeof(enderecoServidor));

	if (r == SOCKET_ERROR)
	{
		sprintf_s(error_message, MAX_ERROR_MSG, "Ocorreu um erro ao ligar o socket e o endereco: %d", WSAGetLastError());
		show_error(error_message);
		return 1;
	}

	r = listen(principalSocket, SOMAXCONN);

	if (r == SOCKET_ERROR) {
		sprintf_s(error_message, MAX_ERROR_MSG, "Ocorreu um erro ao colocar o socket em estado de escutando: %d", WSAGetLastError());
		show_error(error_message);
		return 1;
	}

	SOCKET clienteSocket;
	sockaddr_in clienteEndereco;
	int clienteEnderecoTam = sizeof(clienteEndereco);

	while (true) {
		clienteSocket = accept(principalSocket, (SOCKADDR *) &clienteEndereco, &clienteEnderecoTam);

		std::cout << "Clente Conectado" << std::endl;
	}

	WSACleanup();

	return 0;
}