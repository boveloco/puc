package States;

import java.util.Random;

import Omega.Bob;
import Omega.Player;

public class GoHomeAndSleepTillRested implements State<Bob> {

	Random r;

	public GoHomeAndSleepTillRested() {
		this.r = new Random();
	}

	@Override
	public void enter(Player<Bob> b) {
		System.out.println("--------------------------------------------");
		System.out.println("I'm Home, sweet home alabama!");

	}

	@Override
	public void execute(Player<Bob> b) {
		if (((Bob) b).getThirsty() >= 0)
			((Bob) b).addThirsty(-r.nextInt(10));
		if (((Bob) b).getFatigue() >= 0)
			((Bob) b).addFatigue(-r.nextInt(9));

		if (((Bob) b).getThirsty() <= 0 && ((Bob) b).getFatigue() <= 0) {
			b.changeState(new EnterMineAndDigForNugget());
		}
	}

	@Override
	public void exit(Player<Bob> b) {
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
