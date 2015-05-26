#include "Graph.h"

int main(){


	Graph<int, int> grafo(5);
	grafo.addNode(1, 0);
	grafo.addNode(2, 1);
	grafo.addNode(3, 2);
	grafo.addNode(4, 3);
	grafo.addNode(5, 4);

	grafo.AddArc(0, 1, 0); //1 para o 2
	grafo.AddArc(1, 2, 0); //2 para o 3
	grafo.AddArc(2, 3, 0); //3 para o 4
	grafo.AddArc(3, 4, 0); //4 para o 5
	grafo.AddArc(4, 1, 0); //5 para o 2
	grafo.AddArc(4, 3, 0); //5 para o 4
	grafo.AddArc(4, 0, 0); //5 para o 1

	return 0;
}