#define WIN32_LEAN_AND_MEAN
#include <c2d2/chien2d2.h>
#include <c2d2/chienaudio2.h>
#include <vector>
#include <thread>
#include <numeric>
#include <WinSock2.h>
#include <WS2tcpip.h>
#include <time.h>

//myClasses
#include <ConfiguraçõesServidor.h>
#include <ComandosRede.h>

#define LARGURA_TELA 800
#define ALTURA_TELA 600

#define MEU_IP "0.0.0.0"

#define ESTADO_CONECTANDO 1
#define ESTADO_JOGANDO 2

struct MouseAdversario{
	int x, y;
};
MouseAdversario mouseAdversario;

void receberComandos(SOCKET socket){
	

	MoverMouseComando comando;
	sockaddr_in endereco;
	int tamanho = sizeof(endereco);
	int r;
	do{
		r = recvfrom(socket, (char*)&comando,
					sizeof(comando), NULL, 
					(SOCKADDR*)&endereco, &tamanho);

		mouseAdversario.x = comando.x;
		mouseAdversario.y = comando.y;

	} while (!(r==SOCKET_ERROR || r == 0));
}

int main(int ac, char **av)
{
	

	
	C2D2_Inicia(800, 600, C2D2_JANELA, C2D2_DESENHO_OPENGL, "Minha Janela Chien 2D");
	
	C2D2_Botao *teclado;
	teclado = C2D2_PegaTeclas();
	C2D2_Mouse *mouse;
	mouse = C2D2_PegaMouse();

	C2D2_TrocaCorLimpezaTela(0, 120, 0);

	mouseAdversario.x = 0;
	mouseAdversario.y = 0;

	unsigned int cursoresSpriteSet;
	cursoresSpriteSet = C2D2_CarregaSpriteSet("cursores.png", 26, 26);

	int estado = ESTADO_CONECTANDO;

	WSADATA wsaData;
	WSAStartup(MAKEWORD(2, 2), &wsaData);

	SOCKET meuSocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);

	sockaddr_in meuEndereco;
	inet_pton(AF_INET, MEU_IP, &meuEndereco.sin_addr.s_addr);
	meuEndereco.sin_family = AF_INET;
	
	int porta = 2000 + rand() % 2000;
	meuEndereco.sin_port = htons(porta);

	bind(meuSocket, (SOCKADDR*)&meuEndereco, sizeof(meuEndereco));
	
	sockaddr_in enderecoServidor;
	inet_pton(AF_INET, IP_SERVIDOR, &enderecoServidor.sin_addr.s_addr);
	enderecoServidor.sin_family = AF_INET;
	enderecoServidor.sin_port = htons(PORTA_SERVIDOR);

	Comando comand;
	comand.comando = CMD_ENTRAR;

	sendto(meuSocket, (char*)&comand, sizeof(comand), NULL, (SOCKADDR*)&enderecoServidor, sizeof(enderecoServidor));

	memset(&comand, 0, sizeof(comand));
	int tamanhoEndereco = sizeof(enderecoServidor);

	do{
		recvfrom(meuSocket, (char*)&comand, sizeof(comand), NULL, (SOCKADDR*)&enderecoServidor, &tamanhoEndereco);
	} while (comand.comando != CMD_INICIAR);
	
	MoverMouseComando mouseComando;
	mouseComando.comando = CMD_MOVER_MOUSE;

	std::thread th(receberComandos, meuSocket);

	unsigned long proximoEnvio = C2D2_TempoSistema() + 100;


	
	while (!teclado[C2D2_ESC].pressionado)
	{	
		if (proximoEnvio < C2D2_TempoSistema())
		{
			proximoEnvio = C2D2_TempoSistema() + 100;

			mouseComando.x = mouse->x;
			mouseComando.y = mouse->y;

			sendto(meuSocket, (char*)&mouseComando, sizeof(mouseComando), NULL, (SOCKADDR*)&enderecoServidor, sizeof(&tamanhoEndereco));

		}

		C2D2_LimpaTela();
		C2D2_DesenhaSprite(cursoresSpriteSet,
						   0, mouse->x, mouse->y);
		C2D2_DesenhaSprite(cursoresSpriteSet, 1, mouseAdversario.x, mouseAdversario.y);
		
		C2D2_Sincroniza(C2D2_FPS_PADRAO);		
	}

	C2D2_RemoveSpriteSet(cursoresSpriteSet);

	// AUDIO
	CA2_Encerra();
	C2D2_Encerra();


	return 0;
}