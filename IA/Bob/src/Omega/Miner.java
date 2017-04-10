package Omega;

import java.util.Random;

import States.EnterMineAndDigForNugget;
import States.GlobalBillieShouldWork;
import States.GlobalPeeness;
import States.GoHomeAndSleepTillRested;
import States.LookAtWeather;

public class Miner {

	Random r;
	Bob b;
	Billie bi;

	public Miner() {
		r = new Random();
		b = new Bob();
		bi = new Billie();
		b.manager.changeState(EnterMineAndDigForNugget.getInstance());
		bi.manager.changeGlobalState(LookAtWeather.getInstance());

	}
	
	public static void main(String[] args) {
		Miner m = new Miner();
		while(true){
		if(m.b.getManager().getCurrentState() instanceof GoHomeAndSleepTillRested){
				m.bi.getManager().changeGlobalState(new GlobalBillieShouldWork());
		}
			m.b.getManager().update();
			m.bi.getManager().update();
		}
	
	}
}
