/*
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

#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#if	defined(linux)
	#include <SDL/SDL.h>
#else
	#include <SDL.h>
#endif

// Se está no Visual C++, inclui a lib
#if defined(_MSC_VER)
	#include <windows.h>
#endif
/*
Chien 2D 2.0 Primitivas OpenGL

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


// Includes OpenGL (multiplataforma)
#if defined(__APPLE__) && defined(__MACH__)
    #include <OpenGL/gl.h>	
    #include <OpenGL/glu.h>	
#else
    #include <GL/gl.h>	
    #include <GL/glu.h>	
#endif


#include <c2d2/chien2d2_gl.h>
#include <c2d2/chien2d2primitivas.h>
#include <c2d2/chien2d2primitivas_gl.h>


// Função que inicia a biblioteca de primitivas no modo OpenGL.
//
// Data: 27/07/2008
//
bool C2D2PGL_Inicia()
{
	// Indica os ponteiros de função corretor
	C2D2P_Pontos = &C2D2PGL_Pontos;
	C2D2P_Linha =  &C2D2PGL_Linha;
	C2D2P_Retangulo = &C2D2PGL_Retangulo;
	C2D2P_RetanguloPintado = &C2D2PGL_RetanguloPintado;
	C2D2P_RetanguloPintadoAlfa = &C2D2PGL_RetanguloPintadoAlfa;
	C2D2P_Circulo = &C2D2PGL_Circulo;
	C2D2P_CirculoPintado = &C2D2PGL_CirculoPintado;
	C2D2P_Elipse = &C2D2PGL_Elipse;
	C2D2P_ElipsePintada = &C2D2PGL_ElipsePintada;
	// Parâmetros padrão
	glPointSize(1.0);
	return true;
}


// Algoritmo para desenhar um vetor de pontos na tela. O vetor pontos considera
// a posição 0 de cada linha como coordenada x, sendo a posição 1 a coordenada
// no eixo y. nPontos é o número de pontos no vetor. R,G, e B são os valores da cor.
//
// Data: 28/03/2008
//
void C2D2PGL_Pontos(C2D2P_Ponto pontos[], unsigned int nPontos, unsigned char r, unsigned char g, unsigned char b)
{
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_POINTS);
	for(unsigned int i=0;i<nPontos;i++)
		glVertex2i((GLint)(pontos[i][0]),(GLint)(pontos[i][1]));
	glEnd();
}

// Algoritmo para desenhar uma linha reta, baseado em duas coordenadas no plano e uma cor em RGB
// Usa o algoritmo do ponto médio.
//
// Data: 05/02/2008
//
void C2D2PGL_Linha(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b)
{
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_LINES);
		glVertex2i((GLint)x1,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y2);
	glEnd();
}

// Algoritmo para desenhar um retângulo, baseado em duas coordenadas no plano e uma cor em RGB
//
// Data: 05/02/2008
//

void C2D2PGL_Retangulo(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b)
{
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_LINE_LOOP);
		glVertex2i((GLint)x1,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y2);
		glVertex2i((GLint)x1,(GLint)y2);
	glEnd();
}

// Algoritmo para desenhar um retângulo pintado, baseado em duas coordenadas no plano e uma cor em RGB
//
// Data: 05/02/2008
//
void C2D2PGL_RetanguloPintado(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b)
{
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_QUADS);
		glVertex2i((GLint)x1,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y2);
		glVertex2i((GLint)x1,(GLint)y2);
	glEnd();

}

// Algoritmo para desenhar um retângulo pintado, baseado em duas coordenadas no plano, uma cor em RGB e o valor de alfa
//
// Data: 29/01/2011
//
void C2D2PGL_RetanguloPintadoAlfa(int x1,int y1,int x2,int y2,unsigned char r, unsigned char g, unsigned char b, unsigned char alfa)
{
	setaTexturizacao(false);
	glColor4ub(r, g, b, alfa);
	glBegin(GL_QUADS);
		glVertex2i((GLint)x1,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y1);
		glVertex2i((GLint)x2,(GLint)y2);
		glVertex2i((GLint)x1,(GLint)y2);
	glEnd();

}


// Algoritmo para desenhar um círculo, baseado em uma coordenada no plano, o raio e uma cor em RGB
// Usa o algoritmo do ponto médio
//
// Data: 05/02/2008
//

void C2D2PGL_Circulo(int xc,int yc, int raio, unsigned char r, unsigned char g, unsigned char b)
{
	if(raio <= 0)
		return;
	double rad;
	int x,y, inc=0;
	inc = 500/raio;
	if(inc > 30)
		inc = 30;
	else if(inc < 3)
		inc = 3;
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_LINE_LOOP);
	for (int ang=0; ang<360; ang+=inc)
	{
		rad = ang * (double)3.1415926535897932384626433832795 / 180.0;
		x = xc + (int)(raio * cos(rad));
		y = yc - (int)(raio * sin(rad));
		glVertex2i(x,y);
	}
	glEnd();
}

// Algoritmo para desenhar um círculo pintado, baseado em uma coordenada no plano, o raio e uma cor em RGB
// Baseado no algoritmo do círculo pelo ponto médio
//
// Data: 05/02/2008
//
void C2D2PGL_CirculoPintado(int xc,int yc,int raio, unsigned char r, unsigned char g, unsigned char b)
{
	if(raio<=0)
		return;
	double rad;
	int x,y, inc=0;
	inc = 250/raio;
	if(inc > 90)
		inc = 90;
	else if(inc < 3)
		inc = 3;
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_POLYGON);
	for (int ang=0; ang<360; ang+=inc)
	{
		rad = ang * (double)3.1415926535897932384626433832795 / 180.0;
		x = xc + (int)(raio * cos(rad));
		y = yc - (int)(raio * sin(rad));
		glVertex2i(x,y);
	}
	glEnd();
}



// Algoritmo para desenhar uma elipse, baseado no centro da elipse, a relação "a" e "b", mais uma cor RGB
// Usa o algoritmo do ponto médio
//
// Data: 06/02/2008
//
void C2D2PGL_Elipse(int xc, int yc, int va, int vb, unsigned char r, unsigned char g, unsigned char b)
{
	if(va<=0 || vb <= 0)
		return;
	double rad;
	int x,y, inc=0;
	if(va > vb)
		inc = 250/va;
	else
		inc = 250/vb;
	if(inc > 90)
		inc = 90;
	else if(inc < 3)
		inc = 3;
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_LINE_LOOP);
	for(int ang = 0; ang < 360; ang += inc)
	{
		rad = ang * (double)3.1415926535897932384626433832795 / 180.0;
		x = xc + (int)(va * cos(rad));
		y = yc - (int)(vb * sin(rad));
		glVertex2i(x,y);
	}
	glEnd();
}

// Algoritmo para desenhar uma elipse pintada, baseado no centro da elipse, a relação "a" e "b", mais uma cor RGB
// Usa o algoritmo do ponto médio
//
// Data: 06/02/2008
//
void C2D2PGL_ElipsePintada(int xc, int yc, int va, int vb, unsigned char r, unsigned char g, unsigned char b)
{
	if(va<=0 || vb <= 0)
		return;
	double rad;
	int x,y, inc=0;
	if(va > vb)
		inc = 250/va;
	else
		inc = 250/vb;
	if(inc > 90)
		inc = 90;
	else if(inc < 3)
		inc = 3;
	setaTexturizacao(false);
	glColor3ub(r, g, b);
	glBegin(GL_POLYGON);
	for(int ang = 0; ang < 360; ang += inc)
	{
		rad = ang * (double)3.1415926535897932384626433832795 / 180.0;
		x = xc + (int)(va * cos(rad));
		y = yc - (int)(vb * sin(rad));
		glVertex2i(x,y);
	}
	glEnd();
}
