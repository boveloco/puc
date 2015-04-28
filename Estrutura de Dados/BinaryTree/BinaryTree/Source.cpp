#include "BinaryTree.h"
#include <iostream>
char main(){

	BinaryTree<char>* a;
	BinaryTree<char>* b;
	BinaryTree<char>* c;
	BinaryTree<char>* d;
	BinaryTree<char>* e;
	BinaryTree<char>* f;
	BinaryTree<char>* g;
	BinaryTree<char>* h;
	BinaryTree<char>* i;
	BinaryTree<char>* j;
	BinaryTree<char>* l;
	BinaryTree<char>* m;
	a = new BinaryTree < char >;
	b = new BinaryTree < char >;
	c = new BinaryTree < char >;
	d = new BinaryTree < char >;
	e = new BinaryTree < char >;
	f = new BinaryTree < char >;
	g = new BinaryTree < char >;
	h = new BinaryTree < char >;
	i = new BinaryTree < char >;
	j = new BinaryTree < char >;
	l = new BinaryTree < char >;
	m = new BinaryTree < char > ;

	a->m_left = b;
	a->m_right = c;
	b->m_left = d;
	d->m_left = m;
	c->m_left = f;
	c->m_right = g;
	f->m_left = i;
	f->m_right = h;
	g->m_left = j;
	j->m_left = l;

	///values
	a->m_data = 'a';
	b->m_data = 'b';
	c->m_data = 'c';
	d->m_data = 'd';
	e->m_data = 'e';
	f->m_data = 'f';
	g->m_data = 'g';
	h->m_data = 'h';
	i->m_data = 'i';
	j->m_data = 'j';
	l->m_data = 'l';
	m->m_data = 'm';

	//etcha

	std::cout << f->Count() << std::endl;
	a->Preorder();
		
	delete(a);
	

	system("PAUSE");
	return 0;
}