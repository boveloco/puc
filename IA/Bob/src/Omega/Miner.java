package Omega;

import java.util.Random;

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
		b = new Bob(GoHomeAndSleepTillRested.getInstance());
		b.getManager().changeGlobalState(new GlobalPeeness<Bob>());
		bi = new Billie(LookAtWeather.getInstance());
		bi.getManager().changeGlobalState(new GlobalPeeness<Billie>());
	}
	
	public static void main(String[] args) {
		Miner m = new Miner();
		while(true){
		if(m.b.getManager().getCurrentState() instanceof GoHomeAndSleepTillRested){
				m.bi.getManager().changeGlobalState(new GlobalBillieShouldWork<Billie>());
		}
			m.b.getManager().update();
			m.bi.getManager().update();
		}
	
	}
}
