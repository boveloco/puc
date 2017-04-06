package Omega;

import States.State;

public class StateManager<T> {

	private Player<T> p;
	private State<T> currentState;
	private State<T> lastState;
	private State<T> lastGlobalState;
	private State<T> globalState;


	public StateManager(Player<T> p) {
		this.p = p;
		this.currentState = null;
		this.lastState = null;
		this.globalState = null;
	}
	public void changeState(State<T> s){
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

	public void blipState(){
		State<T> s = currentState;
		this.currentState.exit(p);
		this.currentState = lastState;
		lastState = s;
		currentState.enter(p);
	}

	public void blipGlobalState(){
		State<T> s = globalState;
		this.globalState.exit(p);
		this.globalState = lastGlobalState;
		this.lastGlobalState = s;
		globalState.enter(p);
	}

	public void changeGlobalState(State<T> s){
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
	};
	public Player<T> getP() {
		return p;
	}
	public State<T> getLastState() {
		return lastState;
	}
	public State<T> getGlobalState() {
		return globalState;
	}
	public State<T> getCurrentState(){
		return this.currentState;
	}
}