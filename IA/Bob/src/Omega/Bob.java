package Omega;

import States.EnterMineAndDigForNugget;
import States.Globals.GlobalBillie;
import States.Globals.GlobalBob;
import States.GoHomeAndSleepTillRested;

public class Bob extends Player{

	private int nugets = 0, gold = 0, fatigue = 0;
	public static final int GOLDLIMIT = 35, TIREDLIMIT = 200;
	private boolean billieWorked = false;

	public Bob() {
		super("Bob");
		manager.changeGlobalState(GlobalBob.getInstance());
		manager.changeState(GoHomeAndSleepTillRested.getInstance());
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

	public boolean hasBillieWorked() {
		return billieWorked;
	}

	public boolean isRested(){
		if(this.getThirsty() <= 0 && (this.getFatigue() <= 0))
			return true;
		return false;
	}

	public void setBillieWorked(boolean billieWorked) {
		this.billieWorked = billieWorked;
	}
}
