#pragma once

#define CMD_ENTRAR 1
#define CMD_INICIAR 2
#define CMD_MOVER_MOUSE 3

struct mouseAdversario{
	int x;
	int y;
};

struct Comando{
	int comando;
};

struct MoverMouseComando : Comando{
	int x, y;
};
