package Omega;

import States.State;

public class Bob{

	private int nugets = 0, gold = 0, fatigue = 0, thirsty = 0, dailyGold = 0;
	public static final int GOLDLIMIT = 35, THIRSTLIMIT = 450, TIREDLIMIT = 1220;

	private State<Bob> state;

	public Bob(State<Bob> s) {
		this.state = s;

		this.state.enter(this);
	}

	public void update() {
		this.state.execute(this);
	}

	public void addNugets(int n) {
		this.nugets += n;
	}

	public void addGold(int n) {
		this.gold += n;
	}

	public void addFatigue(int n) {
		this.fatigue += n;
	}

	public void addThirsty(int n) {
		this.thirsty += n;
	}

	public void addDailyGold(int n) {
		this.dailyGold += n;
	}

	public boolean isTired() {
		if (this.fatigue >= TIREDLIMIT)
			return true;
		return false;
	}

	public boolean isThirsty() {
		if (this.thirsty >= THIRSTLIMIT)
			return true;
		return false;
	}

	public void changeState(State<Bob> state) {
		this.state.exit(this);

		this.state = state;

		this.state.enter(this);
	}

	public int getNugets() {
		return nugets;
	}

	public int getGold() {
		return gold;
	}

	public int getFatigue() {
		return fatigue;
	}

	public int getThirsty() {
		return thirsty;
	}

	public int getDailyGold() {
		return dailyGold;
	}

	public State<Bob> getState() {
		return state;
	}

}
