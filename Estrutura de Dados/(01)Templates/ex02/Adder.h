template<class T>
class Adder{

public:
	Adder(){
		m_sum = 0;
	}
	void Add(T p_number){
		m_sum += p_number;
	}
	
	T Sum(){
		return m_sum;
	}
private:
	T m_sum;
};