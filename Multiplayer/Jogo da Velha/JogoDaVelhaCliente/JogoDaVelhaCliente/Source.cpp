#define _CRT_SECURE_NO_WARNINGS
#define _WINSOCK_DEPRECATED_NO_WARNINGS
#define PORTA_SERVIDOR 9000

#define CMD_ENVIAR_JOGADA_REQ 1
#define CMD_ENVIAR_JOGADA_RES 2

#include<iostream>
#include<WinSock2.h>
#include<string.h>

int main()
{
	WSAData wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	SOCKET meuSocket;
	meuSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

	sockaddr_in enderecoServidor;
	enderecoServidor.sin_addr.s_addr = inet_addr("127.0.0.1");
	//enderecoServidor.sin_addr.s_addr = ADDR_ANY;
	enderecoServidor.sin_family = AF_INET;
	enderecoServidor.sin_port = htons(PORTA_SERVIDOR);

	int flag = connect(meuSocket, (SOCKADDR*)&enderecoServidor, sizeof(enderecoServidor));

	for (;;){
		int comando;
		recv(meuSocket, (char*)&comando, sizeof(int), NULL);
		int jogada[2];
		switch (comando)
		{
		case CMD_ENVIAR_JOGADA_REQ:
			std::cout << "informe a coordenada x: " << std::endl;
			std::cin >> jogada[0];
			std::cout << "informe a coordenada y: " << std::endl;
			std::cin >> jogada[1];
			comando = CMD_ENVIAR_JOGADA_RES;
			send(meuSocket, (char*)&comando, sizeof(int), NULL);
			send(meuSocket, (char*)jogada, sizeof(int)*2, NULL);
			break;
		default:
			break;
		}
	}

	WSACleanup();
	return 0;
}