package Omega;

import States.State;

public class Billi extends Players<Billi> {
	
	public Billi(State<Billi> b) {
		this.state = b;
		b.enter(this);
	}


}
