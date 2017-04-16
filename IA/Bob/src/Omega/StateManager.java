package Omega;

import States.State;

public class StateManager {

	private Player p;
	private State<Player> currentState;
	private State<Player> lastState;
	private State<Player> lastGlobalState;
	private State<Player> globalState;


	public StateManager(Player p) {
		this.p = p;
		this.currentState = null;
		this.lastState = null;
		this.globalState = null;
	}
	
	public void changeState(State<Player> s){
		if(this.currentState == null){
			this.currentState = s;
			this.lastState = s;
			this.currentState.enter(p);
			return;
		}
		this.lastState = currentState;

		this.currentState.exit(p);

		this.currentState = s;

		this.currentState.enter(p);
	}

	public boolean handleMessage(Message msg){
		if (currentState.onMessage(p, msg)){
			return true;
		}
		if (globalState != null && globalState.onMessage(p, msg)){
			return true;
		}

		return false;
	}

	public void blipState(){
		State<Player> s = currentState;
		this.currentState.exit(p);
		this.currentState = lastState;
		lastState = s;
		currentState.enter(p);
	}

	public void blipGlobalState(){
		State<Player> s = globalState;
		this.globalState.exit(p);
		this.globalState = lastGlobalState;
		this.lastGlobalState = s;
		globalState.enter(p);
	}

	public void changeGlobalState(State<Player> s){
		if(this.globalState == null){
			this.globalState = s;
			this.lastGlobalState = s;
			this.globalState.enter(p);
			return;
		}
		this.lastGlobalState = globalState;

		this.globalState.exit(p);;

		this.globalState = s;

		this.globalState.enter(p);
	}

	public void update(){
		if(this.globalState != null)
			this.globalState.execute(p);
		if(this.currentState != null)
			this.currentState.execute(p);
		if(this.currentState == null && this.globalState == null){
			System.out.println("No State");
			try {
				//System.out.println("Thread: " + Thread.currentThread().getName());
				Thread.currentThread();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	public Player getP() {
		return p;
	}
	public State<Player> getLastState() {
		return lastState;
	}
	public State<Player> getGlobalState() {
		return globalState;
	}
	public State<Player> getCurrentState(){
		return this.currentState;
	}
}