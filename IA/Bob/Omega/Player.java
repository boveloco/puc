package Omega;

import States.State;

public interface Player<P> {

	public void changeState(State<P> s);
	
	public void update();
}
