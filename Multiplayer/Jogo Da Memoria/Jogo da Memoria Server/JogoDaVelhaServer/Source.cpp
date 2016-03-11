#define PORTA_SERVIDOR 9000

#define CMD_ENVIAR_JOGADA_REQ	1
#define CMD_ENVIAR_JOGADA_RES	2
#define CMD_INFOMA_JOGADA		3
#define CMD_ACABOU				4

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



	SOCKET clienteSocket;

	int cerquila[3][3];
	for (size_t i = 0; i < 3; i++)
		for (size_t j = 0; j < 3; j++)
			cerquila[i][j] = -1;

	int cerquilaResposta[3][3];
	cerquilaResposta[0][0] = 1;
	cerquilaResposta[0][1] = 2;
	cerquilaResposta[0][2] = 3;
	cerquilaResposta[1][0] = 1;
	cerquilaResposta[1][1] = 2;
	cerquilaResposta[1][2] = 3;
	cerquilaResposta[2][0] = 4;
	cerquilaResposta[2][1] = 4;
	cerquilaResposta[2][2] = 9;

	bool x1 = false;
	bool x2 = false;
	bool x3 = false;
	bool x4 = false;

	int lpx = -1;
	int lpy = -1;
	bool verificar = false;
	for (;;){
		clienteSocket = accept(principalSocket, (SOCKADDR*)&clienteEndereco, &clienteEnderecoTam); 

		int comando = CMD_ENVIAR_JOGADA_REQ;

		send(clienteSocket, (char*)&comando, sizeof(int), NULL);
		send(clienteSocket, (char*)cerquila, sizeof(int) * 9, NULL);

		for (;;){

			recv(clienteSocket,(char*)&comando, sizeof(int), NULL);
			switch (comando)
			{
			case CMD_ENVIAR_JOGADA_RES:
				int jogada[2];
					recv(clienteSocket, (char*)jogada, sizeof(int) * 2, NULL);
					
					//validacao
					int x = jogada[0];
					int y = jogada[1];
					cerquila[x][y] = cerquilaResposta[x][y];
					comando = CMD_ENVIAR_JOGADA_REQ;
					//ganhou?
					if (x1 && x2 && x3 && x4)
						comando = CMD_ACABOU;
					send(clienteSocket, (char*)&comando, sizeof(int), NULL);
					send(clienteSocket, (char*)cerquila, sizeof(int) * 9, NULL);
					
					//ve se rapaz acertou o jogo
					if (verificar){
						if (cerquila[x][y] != cerquila[lpx][lpy]){
							cerquila[x][y] = -1;
							cerquila[lpx][lpy] = -1;
						}
						else if (cerquila[x][y] == cerquila[lpx][lpy]){
							//qual ta certo
							switch (cerquila[x][y])
							{
							case 1:
								x1 = true;
								break;
							case 2:
								x2 = true;
								break;
							case 3:
								x3 = true;
								break;
							case 4:
								x4 = true;
								break;
							default:
								break;
							}
						}
						verificar = false;
						lpx = x;
						lpy = y;
						break;
					}
					lpx = x;
					lpy = y;
					verificar = true;
				break;
			}
		}
	}

	WSACleanup();
	return 0;
}
