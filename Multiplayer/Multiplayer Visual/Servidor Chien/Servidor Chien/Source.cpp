#include <iostream>
#include <vector>
#include <WinSock2.h>
#include <WS2tcpip.h>
#include <ComandosRede.h>
#include <ConfiguraçõesServidor.h>

#define ESTADO_AGUARDANDO_JOGADORES 1
#define	ESTADO_JOGANDO 2

int main(){
	int r;

	WSAData wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	sockaddr_in meuEndereco;

	inet_pton(AF_INET, IP_SERVIDOR, &meuEndereco.sin_addr);
	meuEndereco.sin_family = AF_INET;
	meuEndereco.sin_port = htons(PORTA_SERVIDOR);

	SOCKET socketPrincipal = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);
	bind(socketPrincipal, (SOCKADDR*)&meuEndereco, sizeof(sockaddr_in));

	int estado = ESTADO_AGUARDANDO_JOGADORES;

	Comando comando;
	MoverMouseComando mouseComando;

	sockaddr_in enderecoCliente;

	int tamanho = sizeof(sockaddr_in);

	std::vector<sockaddr_in> jogadores;

	do
	{
		switch (estado){
		case ESTADO_AGUARDANDO_JOGADORES:
			std::cout << "Estado: Aguardando Jogadores" << std::endl;
			r = recvfrom(socketPrincipal, (char*)&comando, sizeof(comando), NULL, (SOCKADDR*)&enderecoCliente, &tamanho);

			switch (comando.comando)
			{
			case CMD_ENTRAR:
				std::cout << "Pushback add Clients.." << std::endl;
				jogadores.push_back(enderecoCliente);
				if (jogadores.size() == 2){
					Comando inicioComando;
					inicioComando.comando = CMD_INICIAR;
					for (int i = 0; i < jogadores.size(); i++)
					{
						std::cout << "Estado: Iniciando i == " << i << std::endl;
						sendto(socketPrincipal, (char*)&inicioComando, sizeof(inicioComando), NULL, (SOCKADDR*)&jogadores[i], sizeof(jogadores[i]));
						estado = ESTADO_JOGANDO;
						std::cout << "Size == "<< jogadores.size() << std::endl;
					}

				}
				break;
				//cabo aq
			}
			break;
		case ESTADO_JOGANDO:
			r = recvfrom(socketPrincipal, (char*)&mouseComando, sizeof(mouseComando), NULL, (SOCKADDR*)&enderecoCliente, &tamanho);
			if (enderecoCliente.sin_addr.s_addr == jogadores[0].sin_addr.s_addr && enderecoCliente.sin_port == jogadores[0].sin_port){
				sendto(socketPrincipal, (char*)&mouseComando, sizeof(mouseComando), NULL, (SOCKADDR*)&jogadores[1], sizeof(jogadores[1]));
				std::cout << "Estado: Jogando -- 1" << std::endl;
			}
			else {
				sendto(socketPrincipal, (char*)&mouseComando, sizeof(mouseComando), NULL, (SOCKADDR*)&jogadores[0], sizeof(jogadores[0]));
				std::cout << "Estado: Jogando -- 0" << std::endl;
			}
		break;
		//cabo aq
		}
	} while (true);

	closesocket(socketPrincipal);



}