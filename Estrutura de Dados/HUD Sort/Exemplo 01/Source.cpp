#include <c2d2/chien2d2.h>
#include <c2d2/chienaudio2.h>
#include <c2d2\chien2d2primitivas_gl.h>
#include <time.h>
#include <iostream>

#include "Quadradinho.h" 
#include "Array.h"
#include "BubbleSort.h"
#include "DLinkedList.h"

enum level{
	BLUE = 1,
	GREEN,
	RED
};

void setPos(Quadradinho *q, int pos){
	q->setPosX(pos * 65 + 2);
	q->setPosY(500);
}

int compareQuadradinhos(Quadradinho q1, Quadradinho q2){

	if (q1.getColor() > q2.getColor())
		return 1;
	else
		return 0;
}

int main(int ac, char **av)
{	

	srand(time(NULL));
	Array<Quadradinho> objcs2(10);
	Array<Quadradinho> objcs(10);
	for (size_t i = 0; i < objcs.Size(); i++){
		objcs.m_array[i].setPosX(objcs.m_array->random(800,0));
		objcs.m_array[i].setPosY(objcs.m_array->random(400, 0));
		objcs.m_array[i].setColor(objcs.m_array->random(3,1));
	}

	C2D2_Inicia(800, 600, C2D2_JANELA, C2D2_DESENHO_OPENGL, "Minha Janela Chien 2D");
	
	C2D2_Botao *teclado;
	teclado = C2D2_PegaTeclas();
	C2D2_Mouse *mouse;
	mouse = C2D2_PegaMouse();

	C2D2_TrocaCorLimpezaTela(0, 0, 0);
	
	
	//sprites
	unsigned int mouseSprite;
	unsigned int spriteRed;
	unsigned int spriteBlue;
	unsigned int spriteGreen;
	//carrega sprite
	mouseSprite = C2D2_CarregaSpriteSet("mouse.png", 110, 165);
	spriteRed	= C2D2_CarregaSpriteSet("red.png", 36, 66);
	spriteBlue	= C2D2_CarregaSpriteSet("blue.png", 36, 66);
	spriteGreen = C2D2_CarregaSpriteSet("green.png", 36, 66);

	//variaveis marcatorias
	int pontoInicialX = 0;
	int pontoInicialY = 0;


	unsigned long proximaAtualizacaoFrame = C2D2_TempoSistema() + 500;
	int frameAtual = 4;


	while (!teclado[C2D2_ESC].pressionado)
	{
		if (proximaAtualizacaoFrame < C2D2_TempoSistema())
		{
			frameAtual = (frameAtual == 4) ? 5 : 4;
			proximaAtualizacaoFrame = C2D2_TempoSistema() + 500;
		}
		C2D2_LimpaTela();

		//---------------------------------------------------------------------------------------------------
		//desenha as coisas
		C2D2_DesenhaSprite(mouseSprite,0,mouse->x,mouse->y);

		for (size_t i = 0; i < objcs.Size(); i++){
			switch (objcs[i].getColor())
			{
			case BLUE:
				C2D2_DesenhaSprite(spriteBlue, 0, objcs[i].getPosX(), objcs[i].getPosY());
				break;
			case RED:
				C2D2_DesenhaSprite(spriteRed, 0, objcs[i].getPosX(), objcs[i].getPosY());
				break;
			case GREEN:
				C2D2_DesenhaSprite(spriteGreen, 0, objcs[i].getPosX(), objcs[i].getPosY());
				break;
			default:
				break;
			}
		}
		for (size_t i = 0; i < objcs2.Size(); i++){
			switch (objcs2[i].getColor())
			{
			case BLUE:
				C2D2_DesenhaSprite(spriteBlue, 0, objcs2[i].getPosX(), objcs2[i].getPosY());
				break;
			case RED:
				C2D2_DesenhaSprite(spriteRed, 0, objcs2[i].getPosX(), objcs2[i].getPosY());
				break;
			case GREEN:
				C2D2_DesenhaSprite(spriteGreen, 0, objcs2[i].getPosX(), objcs2[i].getPosY());
				break;
			default:
				break;
			}
		}
		if (mouse->botoes->pressionado){
			pontoInicialX = mouse->x;
			pontoInicialY = mouse->y;
		}

		if (mouse->botoes->ativo)
			C2D2PGL_RetanguloPintadoAlfa(pontoInicialX, pontoInicialY,mouse->x, mouse->y, 0, 0, 140, 80);

		if (mouse->botoes->liberado){ 
			for (size_t i = 0; i < objcs2.Size(); i++){
				objcs2[i] = Quadradinho();
			}
			std::cout << "Mouse x: " << mouse->x << std::endl;
			std::cout << "Mouse y: " << mouse->y << std::endl;
			std::cout << "PontoInicialX: " << pontoInicialX << std::endl;
			std::cout << "PontoInicialY: " << pontoInicialY << std::endl;
			std::cout << std::endl;
			std::cout << "--------------------------------------------" << std::endl;
			for (size_t i = 0; i < objcs.Size(); i++){
				objcs2.Resize(10);
				if (pontoInicialX <= objcs[i].getPosX() && mouse->x >= objcs[i].getPosX()){
					if (pontoInicialY <= objcs[i].getPosY() && mouse->y >= objcs[i].getPosY()){
						std::cout << "Objeto :" << i << std::endl;
						std::cout << "Object Color :" << objcs[i].getColor() << std::endl;
						std::cout << "Pox x" << objcs[i].getPosX() << std::endl;
						std::cout << "Pox y" << objcs[i].getPosY() << std::endl;
						std::cout << std::endl;
						objcs2.Insert(objcs.m_array[i], i);
						//objcs.Remove(i);
					}
				}
			}
			std::cout << "--------------------------------------------" << std::endl;
			BubbleSort<Quadradinho>(objcs, compareQuadradinhos);
			for (size_t i = 0; i < objcs2.Size(); i++){
				setPos(&objcs2.m_array[i],i);
			}
		}
		
		//---------------------------------------------------------------------------------------------------
		C2D2_Sincroniza(C2D2_FPS_PADRAO);		
	}
	
	
	C2D2_Encerra();


	return 0;
}