package Omega;

import java.util.Random;

import States.GoToBathroom;
import States.State;

public abstract class Player<T> {

	protected Random r;
	int THIRSTLIMIT = 450;
	int thirst;
	protected StateManager<T> manager;

	public Player(State<T> def) {
		r = new Random();
		manager = new StateManager<T>(this);
		this.manager.changeState(def);

	}
	public StateManager<T> getManager(){
		return this.manager;
	}
	public void addThirsty(int n) {
		if((this.thirst += n) < 0){
			this.thirst = 0;
		}
	}
	public int getThirsty() {
		return thirst;
	}

	public boolean isThirsty() {
		if (this.thirst >= THIRSTLIMIT)
			return true;
		return false;
	}
}
