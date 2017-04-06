package States;

import java.util.Random;

import Omega.Bob;
import Omega.Player;

public class QuenchThirst implements State<Bob> {

	Random r;

	public QuenchThirst() {
		this.r = new Random();
	}

	@Override
	public void enter(Player<Bob> b) {
		System.out.println("-----------------------------------");
		System.out.println("Acho que preciso de umas birita!");

	}

	@Override
	public void execute(Player<Bob> b) {
		if (((Bob) b).getThirsty() > 10) {
			int res = r.nextInt(7);
			if (res == 7)
				System.out.println("DESCE AQUELA VODKAZINHA COM RUM DO H�MALAIA!");
			((Bob) b).addThirsty(-res);
		}

		if (((Bob) b).getThirsty() <= 10)
			b.changeState(new EnterMineAndDigForNugget());

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
