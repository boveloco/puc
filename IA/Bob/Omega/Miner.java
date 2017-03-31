package Omega;

import States.EnterMineAndDigForNugget;
import States.LookAtWeather;

public class Miner {
	
	Bob b;
	Billi bi;
	
	public Miner() {
		b = new Bob(new EnterMineAndDigForNugget());
		bi = new Billi(LookAtWeather.getInstance());
	}
	
	public static void main(String[] args) {
		Miner m = new Miner();
		new Thread() {
			@Override
			public void run(){
				while(true){
						m.bi.update();	
				}			
				
			}
		}.start();
		
		new Thread() {
			@Override
			public void run(){
				while(true){
					m.b.update();
				}
			}
		}.start();
	}
}
