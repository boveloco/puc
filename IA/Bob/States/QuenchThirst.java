package States;

import java.util.Random;

import Omega.Bob;

public class QuenchThirst implements State<Bob> {

	Random r;

	public QuenchThirst() {
		this.r = new Random();
	}

	@Override
	public void enter(Bob b) {
		System.out.println("-----------------------------------");
		System.out.println("Acho que preciso de umas birita!");

	}

	@Override
	public void execute(Bob b) {
		if (b.getThirsty() > 10) {
			int res = r.nextInt(7);
			if (res == 7)
				System.out.println("DESCE AQUELA VODKAZINHA COM RUM DO HÍMALAIA!");
			b.addThirsty(-res);
		}

		if (b.getThirsty() <= 10)
			b.changeState(new EnterMineAndDigForNugget());

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
