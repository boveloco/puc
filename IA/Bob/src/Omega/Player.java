package Omega;

import java.util.Random;

public abstract class Player {

	protected Random r;
	private String name;
	int THIRSTLIMIT = 450;
	int thirst;
	protected StateManager manager;

	public Player(String name) {
		r = new Random();
		manager = new StateManager(this);

		this.name = name;
		Manager.getInstance().registerPlayer(this);

	}
	public StateManager getManager(){
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

	public String getName(){
		return this.name;
	}

	public boolean handleMessage(Message msg){
		return manager.handleMessage(msg);
	}
}
