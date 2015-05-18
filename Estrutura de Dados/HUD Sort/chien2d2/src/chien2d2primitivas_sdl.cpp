/*

Chien2D 2.0 Primitivas

Copyright 2008-2010, Paulo Vinicius Wolski Radtke (pvwradtke@gmail.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

#if	defined(linux)
	#include <SDL/SDL.h>
#else
	#include <SDL.h>
#endif

#include <stdlib.h>
#include <c2d2/chien2d2primitivas.h>
#include <c2d2/chien2d2primitivas_sdl.h>


// Variável que indica a tela do sistema
extern SDL_Surface *screen;

// Funções de uso exclusivo da biblioteca. Não use em seu programa!

// Desenha um ponto na surface.  O desenvolvedor deve, obrigatoriamente, travar e destravar a surface
// Código copiado honestamente do exemplo da SDL e modificado para usar apenas 32bpp e limites de desenho
//
// Data: 05/02/2008
//
inline void C2D2PSDL_PontoSDL(int x, int y, Uint32 cor)
{
	/* Here p is the address to the pixel we want to set */
    Uint8 *p = (Uint8 *)screen->pixels + y * screen->pitch + x * 4;
	// Testa se o ponto está na tela antes de desenhar (não tem no exemplo da SDL)
	if(x>=0 && x < screen->w && y >= 0 && y < screen->h)
		*(Uint32 *)p = cor;
}

// Desenha uma linha horizontal, a partir do ponto x,y, com comprimento larg e na cor indicada
// Usada para desenhar rapidamente elementos pintados
//
// Data: 06/02/2008
//
inline void C2D2PSDL_LinhaHorizontal(int x,int y,int larg, Uint32 cor)
{
	/* Here p is the address to the pixel we want to set */
    Uint8 *p = (Uint8 *)screen->pixels + y * screen->pitch + x * 4;
	if(y >= 0 && y < screen->h)
		// Para cada ponto
		for(int i=0;i<larg;i++)
		{
			// Testa se o ponto está na tela antes de desenhar (não tem no exemplo da SDL)
			if(x+i>=0 && x+i < screen->w)
				*(Uint32 *)p = cor;
			// Incrementa para o próximo ponto
			p += 4;
		}
}

// Função que inicia a biblioteca de primitivas no modo SDL.
//
// Data: 27/07/2008
//
bool C2D2PSDL_Inicia()
{
	// Indica os ponteiros de função corretor
	C2D2P_Pontos = &C2D2PSDL_Pontos;
	C2D2P_Linha =  &C2D2PSDL_Linha;
	C2D2P_Retangulo = &C2D2PSDL_Retangulo;
	C2D2P_RetanguloPintado = &C2D2PSDL_RetanguloPintado;
	C2D2P_RetanguloPintadoAlfa = &C2D2PSDL_RetanguloPintadoAlfa;
	C2D2P_Circulo = &C2D2PSDL_Circulo;
	C2D2P_CirculoPintado = &C2D2PSDL_CirculoPintado;
	C2D2P_Elipse = &C2D2PSDL_Elipse;
	C2D2P_ElipsePintada = &C2D2PSDL_ElipsePintada;
	return true;
}


// Algoritmo para desenhar um vetor de pontos na tela. O vetor pontos considera
// a posição 0 de cada linha como coordenada x, sendo a posição 1 a coordenada
// no eixo y. nPontos é o número de pontos no vetor. R,G, e B são os valores da cor.
//
// Data: 28/03/2008
//
void C2D2PSDL_Pontos(C2D2P_Ponto pontos[], unsigned int nPontos, unsigned char r, unsigned char g, unsigned char b)
{
	// TRava o display
	SDL_LockSurface(screen);
	Uint32 cor = SDL_MapRGB(screen->format, r, g, b);
	// Desenha os pontos
	for(unsigned int i=0;i<nPontos;i++)
		C2D2PSDL_PontoSDL(pontos[i][0], pontos[i][1], cor);
	// Libera o display
	SDL_UnlockSurface(screen);
}


// Desenha uma linha vertical, a partir do ponto x,y, com altura alt e na cor indicada
// Usada para desenhar rapidamente elementos pintados
//
// Data: 06/02/2008
//
inline void C2D2PSDL_LinhaVertical(int x,int y,int alt, Uint32 cor)
{
	/* Here p is the address to the pixel we want to set */
    Uint8 *p = (Uint8 *)screen->pixels + y * screen->pitch + x * 4;
	if(x >= 0 && x < screen->w)
		// Para cada ponto
		for(int i=0;i<alt;i++)
		{
			// Testa se o ponto está na tela antes de desenhar (não tem no exemplo da SDL)
			if(y+i>=0 && y+i < screen->h)
 				*(Uint32 *)p = cor;
			// Incrementa para o próximo ponto
			p += screen->pitch;
		}
}


// Algoritmo para desenhar uma linha reta, baseado em duas coordenadas no plano e uma cor em RGB
// Usa o algoritmo do ponto médio.
//
// Data: 05/02/2008
//
void C2D2PSDL_Linha(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b)
{
	// Variáveis auxiliares
	int dx,dy,incra,incrb,d,x,y,soma,end;
	// Calcula a cor a desenhar
	Uint32 cor = SDL_MapRGB(screen->format, r, g, b);

	// Trava a surface
	SDL_LockSurface(screen);

	dx=abs(x2-x1);         // Calcula dx
	dy=abs(y2-y1);         // Calcula dy

	if(dx>=dy)             // Variacao no eixo x?
	{                      // Sim
	  d=2*dy-dx;           // Calcula d inicial

	  incra=2*(dy-dx);     // Calcula incremento a
	  incrb=2*dy;          // Calcula incremento b

	  if(x1<x2)            // Calcula pontos iniciais e incremento y (como Bresenham)
	  {
	    x=x1;
	    y=y1;
	    end=x2;
	    if(y1>y2)
	      soma=-1;
	    else
	      soma=1;
	  }
	  else
	  {
	    x=x2;
	    y=y2;
		end=x1;
	    if(y1>y2)
	      soma=1;
	    else
	      soma=-1;
	  }

	  C2D2PSDL_PontoSDL(x, y, cor);     // Desenha o primeiro ponto

	  while(x<end)        // Faz ate o ultimo ponto
	  {
	    if(d<=0)          // testa condica de teste
	    {
		 x++;         // Nao precisa incrementar y. Incrementa x
		 d+=incrb;    // e atualiza d
	    }
	    else
		{
		 x++;         // Precisa incrementar y. Incrementa x e atuali-
		 y+=soma;     // za d.
		 d+=incra;
	    }
	    C2D2PSDL_PontoSDL(x,y,cor);   // Desenha o ponto
	  }
	}
	else                 // Variacao em relacao a y
	{
	  d=2*dx-dy;         // Calcula d "virando" o eixo (em relacao a y)

	  incra=2*(dx-dy);   // Calcula incra em relacao a y
	  incrb=2*dx;        // Calcula incrb em relacao a y

	  if(y1<y2)          // Faz testes de ponto iniciais. Igual a Bresenham.
	  {
	    x=x1;
	    y=y1;
	    end=y2;
		if(x1>x2)
	      soma=-1;
	    else
		 soma=1;
	  }
	  else
	  {
	    x=x2;
	    y=y2;
	    end=y1;
	    if(x1>x2)
	      soma=1;
	    else
	      soma=-1;
	  }

	  while(y<end)          // Faz ate chegar o ultimo ponto :
	  {
	    if(d<=0)            // Testa se deve ou nao incrementar o x
	    {
		 y++;           //  Nao. Incrementa y e atualiza d.
		 d+=incrb;      //
	    }
		else
	    {
		 y++;           // Precisa. Incrementa x, y e atualiza d.
		 x+=soma;
		 d+=incra;
	    }
	    C2D2PSDL_PontoSDL(x,y,cor);     // Desenha o ponto
	  }
	}
	// Destrava a surface
	SDL_UnlockSurface(screen);

}

// Algoritmo para desenhar um retângulo, baseado em duas coordenadas no plano e uma cor em RGB
//
// Data: 05/02/2008
//

void C2D2PSDL_Retangulo(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b)
{
	// Calcula a cor
	Uint32 cor = SDL_MapRGB(screen->format,r,g,b);
	// TRava a surface
	SDL_LockSurface(screen);
	// desenha as linhas uma a uma
	if(x1<x2)
	{
		C2D2PSDL_LinhaHorizontal(x1, y1, x2-x1, cor);
		C2D2PSDL_LinhaHorizontal(x1, y2, x2-x1, cor);
	}
	else
	{
		C2D2PSDL_LinhaHorizontal(x2, y1, x1-x2, cor);
		C2D2PSDL_LinhaHorizontal(x2, y2, x1-x2, cor);
	}
	if(y1<y2)
	{
		C2D2PSDL_LinhaVertical(x1, y1, y2-y1, cor);
		C2D2PSDL_LinhaVertical(x2, y1, y2-y1, cor);
	}
	else
	{
		C2D2PSDL_LinhaVertical(x1, y2, y1-y2, cor);
		C2D2PSDL_LinhaVertical(x2, y2, y1-y2, cor);
	}

//	C2D2P_Linha(x2,y2, x1,y2, r, g, b);
//	C2D2P_Linha(x1,y2, x1,y1, r, g, b);
	// Destrava a surface
	SDL_UnlockSurface(screen);
}

// Algoritmo para desenhar um retângulo pintado, baseado em duas coordenadas no plano e uma cor em RGB
//
// Data: 05/02/2008
//
void C2D2PSDL_RetanguloPintado(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b)
{
	// Cria a área a pintar
	SDL_Rect area;
	if(x1 < x2)
		area.x = x1;
	else
		area.x = x2;
	if(y1 < y2)
		area.y = y1;
	else
		area.y = y2;
	area.w = abs(x1-x2);
	area.h = abs(y1-y2);
	// Cria a cor
	Uint32 cor = SDL_MapRGB(screen->format, r, g, b);
	// Pinta
	SDL_FillRect(screen, &area, cor);
}

// Algoritmo dummy para desenhar um retângulo pintado, baseado em duas coordenadas no plano e uma cor em RGB e canal de alfa
//
// Data: 29/01/2011
//
void C2D2PSDL_RetanguloPintadoAlfa(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b, unsigned char alfa)
{
}

// Algoritmo para desenhar um círculo, baseado em uma coordenada no plano, o raio e uma cor em RGB
// Usa o algoritmo do ponto médio
//
// Data: 05/02/2008
//

void C2D2PSDL_Circulo(int xc,int yc, int raio, unsigned char r, unsigned char g, unsigned char b)
{
	if(raio <= 0)
		return;
	// Trava a surface
	SDL_LockSurface(screen);
	// Calcula a cor
	Uint32 cor = SDL_MapRGB(screen->format, r, g, b);
	int x,y,d,da,db;

	if(raio <=0)
	  return;

	x=0;
	y=raio;
	d=1-raio;
	da=3;
	db=-2*raio + 5;

	C2D2PSDL_PontoSDL(x+xc,y+yc,cor);
	C2D2PSDL_PontoSDL(y+xc,x+yc,cor);
	C2D2PSDL_PontoSDL(y+xc,-x+yc,cor);
	C2D2PSDL_PontoSDL(x+xc,-y+yc,cor);
	C2D2PSDL_PontoSDL(-x+xc,-y+yc,cor);
	C2D2PSDL_PontoSDL(-y+xc,-x+yc,cor);
	C2D2PSDL_PontoSDL(-y+xc,x+yc,cor);
	C2D2PSDL_PontoSDL(-x+xc,y+yc,cor);

	while(y>x)
	{
	  if(d<0)
	  {
	    d=d+da;
	    da+=2;
	    db+=2;
	  }
	  else
	  {
	    d=d+db;
	    da+=2;
	    db+=4;
	    y--;
	  }
	  x++;
		C2D2PSDL_PontoSDL(x+xc,y+yc,cor);
		C2D2PSDL_PontoSDL(y+xc,x+yc,cor);
		C2D2PSDL_PontoSDL(y+xc,-x+yc,cor);
		C2D2PSDL_PontoSDL(x+xc,-y+yc,cor);
		C2D2PSDL_PontoSDL(-x+xc,-y+yc,cor);
		C2D2PSDL_PontoSDL(-y+xc,-x+yc,cor);
		C2D2PSDL_PontoSDL(-y+xc,x+yc,cor);
		C2D2PSDL_PontoSDL(-x+xc,y+yc,cor);
	}


	// Destrava a surface
	SDL_UnlockSurface(screen);
}

// Algoritmo para desenhar um círculo pintado, baseado em uma coordenada no plano, o raio e uma cor em RGB
// Baseado no algoritmo do círculo pelo ponto médio
//
// Data: 05/02/2008
//
void C2D2PSDL_CirculoPintado(int xc,int yc,int raio, unsigned char r, unsigned char g, unsigned char b)
{
	if(raio <=0)
		return;
	int x,y,d,da,db;
	Uint32 cor = SDL_MapRGB(screen->format, r,g,b);
	if(raio <=0)
	  return;
	// Trava a surface do display
	SDL_LockSurface(screen);
	x=0;
	y=raio;
	d=1-raio;
	da=3;
	db=-2*raio + 5;

	C2D2PSDL_LinhaHorizontal(-x+xc, y+yc, 2*x, cor);
	C2D2PSDL_LinhaHorizontal(-y+xc, x+yc, 2*y, cor);
	C2D2PSDL_LinhaHorizontal(-y+xc, -x+yc, 2*y, cor);
	C2D2PSDL_LinhaHorizontal(-x+xc, -y+yc, 2*x, cor);

	while(y>x)
	{
	  if(d<0)
	  {
	    d=d+da;
	    da+=2;
	    db+=2;
	  }
	  else
	  {
	    d=d+db;
	    da+=2;
	    db+=4;
	    y--;
	  }
	  x++;
	C2D2PSDL_LinhaHorizontal(-x+xc, y+yc, 2*x, cor);
	C2D2PSDL_LinhaHorizontal(-y+xc, x+yc, 2*y, cor);
	C2D2PSDL_LinhaHorizontal(-y+xc, -x+yc, 2*y, cor);
	C2D2PSDL_LinhaHorizontal(-x+xc, -y+yc, 2*x, cor);
	}
	// Destrava a surface do display
	SDL_UnlockSurface(screen);

}

// Algoritmo para desenhar uma elipse, baseado no centro da elipse, a relação "a" e "b", mais uma cor RGB
// Usa o algoritmo do ponto médio
//
// Data: 06/02/2008
//
void C2D2PSDL_Elipse(int xc, int yc, int va, int vb, unsigned char r, unsigned char g, unsigned char b)
{
	if(va<=0 || vb<=0)
		return;
	int x,y;
	double d1,d2;
	// Calcula a cor
	Uint32 cor = SDL_MapRGB(screen->format,r,g,b);
	// TRava a surface
	SDL_LockSurface(screen);
	x=0;
	y=vb;
	d1=(double)(vb*vb)-(double)(va*va*vb)+0.25*(va*va);
	C2D2PSDL_PontoSDL(xc+x,yc+y,cor);
	C2D2PSDL_PontoSDL(xc-x,yc+y,cor);
	C2D2PSDL_PontoSDL(xc-x,yc-y,cor);
	C2D2PSDL_PontoSDL(xc+x,yc-y,cor);

	while( (double)(va*va)*((double)y-0.5) > (double)(vb*vb*(x+1)))
	{
	  if(d1<0)
		d1+=(double)(vb*vb*(2*x + 3));
	  else
	  {
		d1+=(double)(vb*vb*(2*x + 3) + va*va*(-2*y + 2));
		y--;
	  }
	  x++;
	C2D2PSDL_PontoSDL(xc+x,yc+y,cor);
	C2D2PSDL_PontoSDL(xc-x,yc+y,cor);
	C2D2PSDL_PontoSDL(xc-x,yc-y,cor);
	C2D2PSDL_PontoSDL(xc+x,yc-y,cor);
	}
	d2=(double)(vb*vb*(x+0.5)*(x+0.5) + va*va*(y-1)*(y-1) - va*va*vb*vb);
	while(y>0)
	{
	  if(d2>=0)
		d2+=(double)(va*va*(-2*y + 3));
	  else
	  {
		d2+=(float)(2*vb*vb*(x+1) + va*va*(-2*y + 3));
		x++;
	  }
	  y--;
	C2D2PSDL_PontoSDL(xc+x,yc+y,cor);
	C2D2PSDL_PontoSDL(xc-x,yc+y,cor);
	C2D2PSDL_PontoSDL(xc-x,yc-y,cor);
	C2D2PSDL_PontoSDL(xc+x,yc-y,cor);
	}
	// Destrava a surface
	SDL_UnlockSurface(screen);
}

// Algoritmo para desenhar uma elipse pintada, baseado no centro da elipse, a relação "a" e "b", mais uma cor RGB
// Usa o algoritmo do ponto médio
//
// Data: 06/02/2008
//
void C2D2PSDL_ElipsePintada(int xc, int yc, int va, int vb, unsigned char r, unsigned char g, unsigned char b)
{
	if(va<=0 || vb <= 0)
		return;
	int x,y;
	double d1,d2;
	x=0;
	y=vb;
	// TRava o display
	SDL_LockSurface(screen);
	d1=(double)(vb*vb)-(double)(va*va*vb)+0.25*(va*va);
	Uint32 cor = SDL_MapRGB(screen->format, r,g,b);
	C2D2PSDL_LinhaHorizontal(xc-x, yc+y, 2*x, cor);
	C2D2PSDL_LinhaHorizontal(xc-x, yc-y, 2*x, cor);

	while( (double)(va*va)*((double)y-0.5) > (double)(vb*vb*(x+1)))
	{
	  if(d1<0)
		d1+=(double)(vb*vb*(2*x + 3));
	  else
	  {
		d1+=(double)(vb*vb*(2*x + 3) + va*va*(-2*y + 2));
		y--;
	  }
	  x++;
	C2D2PSDL_LinhaHorizontal(xc-x, yc+y, 2*x, cor);
	C2D2PSDL_LinhaHorizontal(xc-x, yc-y, 2*x, cor);
	}
	d2=(double)(vb*vb*(x+0.5)*(x+0.5) + va*va*(y-1)*(y-1) - va*va*vb*vb);
	while(y>0)
	{
	  if(d2>=0)
		d2+=(double)(va*va*(-2*y + 3));
	  else
	  {
		d2+=(float)(2*vb*vb*(x+1) + va*va*(-2*y + 3));
		x++;
	  }
	  y--;
	C2D2PSDL_LinhaHorizontal(xc-x, yc+y, 2*x, cor);
	C2D2PSDL_LinhaHorizontal(xc-x, yc-y, 2*x, cor);
	}
	// Destrava o display
	SDL_UnlockSurface(screen);
}
