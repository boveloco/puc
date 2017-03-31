package Omega;

import States.State;

public class Billi {
	
	private State<Billi> state;
	
	public void changState(State<Billi> b){
		this.state.exit(this);
		
		this.state = b;
		
		this.state.enter(this);
		
	}
	
	public void update(){
		this.state.execute(this);
	}

}
