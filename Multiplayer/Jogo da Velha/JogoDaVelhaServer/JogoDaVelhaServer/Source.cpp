#define PORTA_SERVIDOR 9000

#define CMD_ENVIAR_JOGADA_REQ	1
#define CMD_ENVIAR_JOGADA_RES	2
#define CMD_INFOMA_JOGADA		3

#include<iostream>
#include<WinSock2.h>
#include<string.h>
#include<stdio.h>

int main()
{
	WSAData wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	SOCKET principalSocket;
	principalSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

	sockaddr_in enderecoServidor;
	//enderecoServidor.sin_addr.s_addr = inet_addr("0.0.0.0");
	enderecoServidor.sin_addr.s_addr = ADDR_ANY;
	enderecoServidor.sin_family = AF_INET;
	enderecoServidor.sin_port = htons(PORTA_SERVIDOR);


	bind(principalSocket, (SOCKADDR*)&enderecoServidor, sizeof(enderecoServidor)); // liga socket e endereço

	listen(principalSocket, SOMAXCONN);

	sockaddr_in clienteEndereco;
	int clienteEnderecoTam = sizeof(clienteEndereco);



	SOCKET clienteSocket[2];

	int cerquila[3][3];
	for (size_t i = 0; i < 3; i++)
		for (size_t j = 0; j < 3; j++)
			cerquila[i][j] = -1;


	for (;;){

		for (int i = 0; i < 2; i++)
			clienteSocket[i] = accept(principalSocket, (SOCKADDR*)&clienteEndereco, &clienteEnderecoTam); //conecta os 2 clientes

		int jogador = 0;
		int comando = CMD_ENVIAR_JOGADA_REQ;

		send(clienteSocket[jogador], (char*)&comando, sizeof(int), NULL);

		for (;;){

			recv(clienteSocket[jogador],(char*)&comando, sizeof(int), NULL);
			switch (comando)
			{
			case CMD_ENVIAR_JOGADA_RES:
				int jogada[2];
					recv(clienteSocket[jogador], (char*)jogada, sizeof(int) * 2, NULL);
					
					//validacao
					int x = jogada[0];
					int y = jogada[1];
					cerquila[x][y] = jogador;
					comando = CMD_INFOMA_JOGADA;
					send(clienteSocket[jogador + 1 % 2], (char*)&comando, sizeof(int), NULL);
					send(clienteSocket[jogador + 1 % 2], (char*)jogada, sizeof(int)*2, NULL);
				break;
			}
		}
	}

	WSACleanup();
	return 0;
}
