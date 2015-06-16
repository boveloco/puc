#include <iostream>
#include <stack>
#include <vector>

using namespace std;

template<class T1>
void printStack(stack<T1> p_stack);
void printStack(stack<T1> p_stack);


int main(){

	stack<int> myStack;

	myStack.push(5);
	myStack.push(2);
	myStack.push(3);
	myStack.push(1);
	myStack.push(8);
	myStack.push(0);

	printStack(myStack);

	stack<int, vector<int>> vStack;

	cout << endl;

	system("pause");
	return 0;
}

template <class T1>
void printStack(stack<T1> p_stack){

	while (!p_stack.empty()){

		cout << p_stack.top() << " - ";
		p_stack.pop();
	}

}