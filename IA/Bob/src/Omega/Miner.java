package Omega;

import States.EnterMineAndDigForNugget;
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
		b = new Bob(new EnterMineAndDigForNugget());
		bi = new Billie(LookAtWeather.getInstance());
	}
	
	public static void main(String[] args) {
		Miner m = new Miner();
		m.bili.setName("Billi");
		m.bob.setName("Bob");
		m.bili.start();
		m.bob.start();
	}
}
