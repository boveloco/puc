#pragma once
#include "DLinkedList.h"
#include "Array.h"

template<class TNode, class TArc>
class GraphNode;

template<class TNode, class TArc>
class GraphArc{

public:
	GraphNode<TNode, TArc> * m_node;
	TArc m_weight;
};

template<class TNode, class TArc>
class GraphNode{
public:
	typedef	GraphNode<TNode, TArc> Node;
	typedef GraphArc<TNode, TArc> Arc;
	TNode m_data;
	DLinkedList<TNode> m_arcList;
	bool m_marked;
	
	void AddArc(Node* p_node, TArc p_weight){
		Arc a;
		a.m_node = p_node;
		a.m_weight = p_weight;	
		m_arcList.Append(a);
	}

	Arc* GetArc(Node* p_node){
		DListIterator<Arc> itr;
		itr = m_arcList.GetIterator();
		for (itr.Start(); itr.Valid(); itr.Forth()){
			if (itr.Item().m_node == p_node)
				return &(itr.Item());
		}
	}

	void RemoveArc(Node* p_node){
		DListIterator<Arc> itr;
		itr = m_arcList.GetIterator();
		for (itr.Start(); itr.Valid(); itr.Forth()){
			if (itr.Item().m_node == p_node)
				m_arcList.Remove(itr);
		}
	}
};


template<class TNode, class TArc>
class Graph{
public:
	typedef	GraphNode<TNode, TArc> Node;
	typedef GraphArc<TNode, TArc> Arc;
	Array<Node*> m_nodes;
	int m_count;

	Graph(int p_size) : m_nodes(p_size){
		for (int i = 0; i < p_size; i++)
			m_nodes[i] = nullptr;
		
		m_count = 0;
	}

	~Graph(){
		for (int i = 0; i < m_nodes.m_size; i++){
			if (m_nodes[i] != nullptr)
				delete m_nodes[i];
		}
	}

	bool addNode(TNode p_data, int p_index){
		if (m_nodes[p_index] != nullptr)
			return false;

		m_nodes[p_index] = new Node;
		m_nodes[p_index]->m_data = p_data;
		m_nodes[p_index]->m_marked = false;
		m_count++;

		return true;
	}
	
	void RemoveNode(int p_index){
		if (m_nodes[p_index] == nullptr)
			delete m_nodes[p_index];

		Arc* arc;
		for (size_t i = 0; i < m_nodes.Size(); i++){
			if (m_nodes[i] != nullptr){
				arc = m_nodes[i]->GetArc(m_nodes[p_index]);
				if (arc != nullptr)
					RemoveArc(i, p_index);
			}
		}
		delete m_nodes[p_index];
		m_nodes[p_index] = nullptr;
		m_count--;
	}

	bool AddArc(int p_from, int p_to, TArc p_weight){
		if (m_nodes[p_from] == nullptr || m_nodes[p_to] == nullptr)
			return false;
		// se existe um arco retorne false
		if (m_nodes[p_from]->GetArc(m_nodes[p_to]) != 0)
			return false;

		m_nodes[p_from]->AddArc(m_nodes[p_to], p_weight);
		return true;
	}

	void RemoveArc(int p_from, int p_to){
		if (m_node[p_from] == 0 || m_nodes[p_to] == 0)
			return nullptr;

		return m_nodes[p_from]->GetArc(m_nodes[p_to]);
	}

	void ClearMarks(){
		for (size_t i = 0; i < m_nodes.Size(); i++)
		{
			if (m_nodes[i] != nullptr)
				m_nodes[i]->m_marked = false;
		}
	}

	void DepthFirst(Node* p_node, void(*p_process)(Node*)){
		if (p_node == nullptr)
			return;

		p_process(p_node);

		DListIterator<Arc> itr;
		itr = p_node->m_arcList.GetIterator();
		for (itr.Start(); itr.Valid(); itr.Forth()){
			if (itr.Item().m_node->m_marked == false){
				itr.Item()m_node->m_marked = true;
				DepthFirst(itr.Item().m_node, p_process)
			}
		}
	}
};