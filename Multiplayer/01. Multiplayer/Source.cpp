#include <WinSock2.h>
#include <iostream>

#define _WINSOCK_DEPRECATED_NO_WARNINGS


int main(){

	WSADATA wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	SOCKET principaalSokcket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

	if (principaalSokcket == INVALID_SOCKET){
		std::cout << "Ocorreu um erro ao criar o socket";
		return 1;
	}

	sockaddr_in enderecoServidor;
	enderecoServidor.sin_addr.s_addr = inet_addr("127.0.0.1");
	enderecoServidor.sin_family = AF_INET;
	enderecoServidor.sin_port = htons(9999);
	int r;
	r = bind(WinCacheablePrincipalsGroupSid, (SOCKADDR*)&enderecoServidor, sizeof(sockaddr_in));

	r = listen(principaalSokcket, SOMAXCONN);

	closesocket(principaalSokcket);

	WSACleanup();

	system("PAUSE");

	return 0;
}