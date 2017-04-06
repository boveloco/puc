package Omega;

import States.EnterMineAndDigForNugget;
import States.GlobalPeeness;
import States.LookAtWeather;

public class Miner {

	Bob b;
	Billie bi;
	public Thread bili = new Thread(){
		@Override
		public void run(){
			while (true)
				b.getManager().update();
		}
	};
	public Thread bob = new Thread(){
		@Override
		public void run(){
			while(true)
				bi.getManager().update();
		}
	};


	public Miner() {
		b = new Bob(EnterMineAndDigForNugget.getInstance());
		b.getManager().changeGlobalState(new GlobalPeeness<Bob>());
		bi = new Billie(LookAtWeather.getInstance());
		bi.getManager().changeGlobalState(new GlobalPeeness<Billie>());
	}
	
	public static void main(String[] args) {
		Miner m = new Miner();
		m.bili.setName("Billi");
		m.bob.setName("Bob");
		m.bili.start();
		m.bob.start();
	}
}
