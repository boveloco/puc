package States;

import java.util.Random;

import Omega.Bob;

public class GoHomeAndSleepTillRested implements State {
	
	Random r;
	
	public GoHomeAndSleepTillRested() {
		this.r = new Random();
	}

	@Override
	public void enter(Bob b) {
		System.out.println("--------------------------------------------");
		System.out.println("I'm Home, sweet home alabama!");
		
	}

	@Override
	public void execute(Bob b) {
		b.addThirsty(-r.nextInt(10));
		b.addFatigue(-r.nextInt(9));
		
	}

	@Override
	public void exit(Bob b) {
		
	}

}
