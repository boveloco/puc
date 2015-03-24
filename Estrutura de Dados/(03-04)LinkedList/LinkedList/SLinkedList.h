#ifndef __SLINKEDLIST_H__
#define __SLINKEDLIST_H__

template <class T> class SListNode;
template <class T> class SLinkedList;
template <class T> class SListIterator;

template<class T>
class SListNode{
private:
	T m_data;
	SListNode<T> * m_next;
public:
	//getters&setters
	void setData(T p_data){
		this->m_data = p_data;
	}
	T getData(){
		return m_data;
	}
	void setNext(SListNode<T>* p_next){
		this->m_next = p_next;
	}
	SListNode<T>* getNext(){
		return this->m_next;
	}
	
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
private:
	SListNode<T> * m_tail;
	SListNode<T> * m_head;
	int m_count;
public:
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
			next = itr->getNext();
			delete itr;
			itr = next;
		}
	}
	void Prepend(T p_data){
		SListNode<T>* newNode = new SListNode < T > ;
		newNode->m_data = p_data;
		newNode->m_next = m_head;
		m_head = newNode;

		if (m_tail == 0)
			m_tail = m_head;
		m_count++;
	}

	void Append(T p_data){
		if (m_head == 0){
			m_head = m_tail = new SListNode<T>();
			m_head->setData(p_data);
		} else {
			m_tail->InsertAfter(p_data);
			m_tail->setNext(m_tail->getNext());
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
	void RemoveTail(){
		SListNode<T>* node = m_head;
		if (m_head != 0 && m_head == m_tail){
			delete m_head;
			m_head = m_tail = 0;
		}
		else {
			while (node->getNext() != m_tail){
				node->setNext(node->getNext());
			}
			m_tail = node;
			delete node->getNext();
			node->setNext(0);
		}
		m_count--;
	}
	//getters&setters
	void setHead(SListNode<T>* p_head){
		this->m_head = p_head;
	}
	void setTail(SListNode<T>* p_tail){
		this->m_tail = p_tail;
	}
	SListNode<T>* getHead(){
		return this->m_head;
	}
	SListNode<T>* getTail(){
		return this->m_tail;
	}
	SListIterator<T> GetIterator(){
		return SListIterator<T>(this, m_head);
	}
};

template<class T>
class SListIterator{
private:
	SLinkedList<T>* m_list;
	SListNode<T>* m_node;
public:
	
	
	SListIterator(SLinkedList<T>* p_list = 0, SListNode<T>* p_node = 0){
		m_list = p_list;
		m_node = p_node;
	}

	void Start(){
		if (m_list != 0)
			m_node = m_list->getHead();
	}
	void Forth(){
		if (m_node != 0)
			m_node = m_node->getNext();
	}
	T Item(){
		return m_node->getData();
	}
	bool Valid(){
		return m_node != 0;
	}
	//GSS
	SLinkedList<T>* getList(){
		return this->m_list;
	}
	SListNode<T>* getNode(){
		return this->m_node;
	}
	void setNode(SListNode<T>* p_node){
		this->m_node = p_node;
	}
	void setList(SLinkedList<T>* p_list){
		this->m_list = p_list;
	}
};
#endif