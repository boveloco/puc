package Omega;

import States.State;

public class Bob extends Players<Bob>{

	private int nugets = 0, gold = 0, fatigue = 0, dailyGold = 0;
	public static final int GOLDLIMIT = 35, TIREDLIMIT = 1220;

	public Bob(State<Bob> s) {
		this.state = s;

		this.state.enter(this);
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

	public void addDailyGold(int n) {
		this.dailyGold += n;
	}

	public boolean isTired() {
		if (this.fatigue >= TIREDLIMIT)
			return true;
		return false;
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

	public int getDailyGold() {
		return dailyGold;
	}

	public State<Bob> getState() {
		return state;
	}


}
