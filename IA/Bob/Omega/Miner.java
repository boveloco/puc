package Omega;

import States.EnterMineAndDigForNugget;

public class Miner {
	
	Bob b;
	
	public Miner() {
		b = new Bob(new EnterMineAndDigForNugget());
	}
	
	public static void main(String[] args) {
		Miner m = new Miner();
		while(true){
			m.b.update();			
		}
	}
}
