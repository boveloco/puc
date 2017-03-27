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
		if (b.getThirsty() >= 0)
			b.addThirsty(-r.nextInt(10));
		if (b.getFatigue() >= 0)
			b.addFatigue(-r.nextInt(9));

		if (b.getThirsty() <= 0 && b.getFatigue() <= 0) {
			b.changeState(new EnterMineAndDigForNugget());
		}
	}

	@Override
	public void exit(Bob b) {
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
