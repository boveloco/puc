package Omega;

import java.util.Random;

import States.GoToBathroom;
import States.State;

public abstract class Players<P> implements Player<P> {

	protected Random r;
	protected State<P> lastState;
	protected State<P> state;
	int THIRSTLIMIT = 450;
	int thirst;

	public Players() {
		r = new Random();
	}

	public State<P> getLastState() {
		return this.lastState;
	}

	public void goToPee() {

	}

	@Override
	public void update() {
		this.state.execute(this);
	}

	@Override
	public void changeState(State<P> s) {
		lastState = state;

		this.state.exit(this);

		int x = r.nextInt(100);
		if (x == 0) {
			this.state = new GoToBathroom<P>();
		} else {
			this.state = s;
		}

		this.state.enter(this);
	}

	public void addThirsty(int n) {
		this.thirst += n;
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
