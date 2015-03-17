#ifndef __SLINKEDLIST_H__
#define __SLINKEDLIST_H__

template <class T> class SListNode;
template <class T> class SLinkedList;
template <class T> class SListIterator;

template<class T>
class SListNode{

public:
	T m_data;
	SListNode<T> * m_next;
	
	SListNode(){
		m_next = 0;
	}

	void InsertAfter(T p_data){
		SListNode<T>* newNode;
		newNode = new SListNode<T>();
		newNode->m_data = p_data;
		newNode->m_next = m_next;
		m_next = newNode;
	}
};

template <class T>
class SLinkedList{
public:
	SListNode<T> * m_tail;
	SListNode<T> * m_head;
	int m_count;

	SLinkedList(){
		m_head = 0;
		m_tail = 0;
		m_count = 0;
	}
	~SLinkedList(){
		SListNode<T> * itr;
		itr = m_head;
		SListNode<T>* next;

		while (itr != 0){
			next = itr->m_next;
			delete itr;
			itr = next;
		}
	}
	void Append(T p_data){
		if (m_head == 0){
			m_head = m_tail = new SListNode<T>();
			m_head->m_data = p_data;
		} else {
			m_tail->InsertAfter(p_data);
			m_tail = m_tail->m_next;
		}
	}

	void Remove(SListIterator<T>& p_iterator){
		SListNode<T>* node = m_head;
		if (p_iterator.m_list != this)
			return;
		if (p_iterator.m_node == 0)
			return;
		if (p_iterator.m_node == m_head){
			p_iterator.Forth();
			RemoveHead();
		} else {
			while (node->m_next != p_iterator.m_node){
				node = node->m_next;

				p_iterator.Forth();
				
				if (node->m_next == m_tail)
					m_tail = node;

				delete node->m_next;
				node->m_next = p_iterator.m_node;
			}
			m_count--;
		}

		void RemoveHead(){
			SListNode<T> * node = 0;
			if (m_head != 0){
				node = m_head->m_next;
				delete m_head;
				m_head = node;

				if (m_head == 0)
					m_tail = 0;

				m_count--;
			}
		}
	}
};

template<class T>
class SListIterator{
public:
	SLinkedList<T>* m_list;
	SListNode<T>* m_node;
	
	SListIterator(SLinkedList<T>* p_list = 0, SListNode<T>* p_node = 0){
		m_list = p_list;
		n_node = p_node;
	}

	void Start(){
		if (m_list != 0)
			m_node = m_list->m_head;
	}
	void Forth(){
		if (m_node != 0)
			m_node = m_node->m_next;
	}
	T& Item(){
		return m_node->m_data;
	}
	bool Valid(){
		return m_node != 0;
	}
};
#endif