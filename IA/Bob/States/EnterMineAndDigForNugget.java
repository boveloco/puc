package States;

import java.util.Random;

import Omega.Bob;

public class EnterMineAndDigForNugget implements State {

	Random r;
	private boolean flagGold;

	public EnterMineAndDigForNugget() {
		r = new Random();
		flagGold = false;
	}

	@Override
	public void enter(Bob b) {
		System.out.println("--------------------------------------------");
		System.out.println("Mining... Mining... Miningg...");
		flagGold = false;
		try {
			Thread.currentThread();
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute(Bob b) {
		b.addFatigue(r.nextInt(10));
		b.addThirsty(r.nextInt(5));
		int res = r.nextInt(4);
		if (res == 2) {
			b.addNugets(1);
			this.flagGold = true;
		}

		if (b.isThirsty()) {
			b.changeState(new QuenchThirst());
		}

		if (b.isTired()) {
			b.changeState(new GoHomeAndSleepTillRested());
		}

		if (b.getNugets() >= Bob.GOLDLIMIT) {
			b.changeState(new VisitBankAndDepositGold());
		}
		if (flagGold) {
			System.out.println("AHAAAAAAAAAAAAA. :D Many many gooooooollldssss");
		} else {
			System.out.println("Meh - Worth nothing.");
		}
		try {
			Thread.currentThread();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.flagGold = false;

	}

	@Override
	public void exit(Bob b) {
		System.out.println("-----------------------");
		System.out.println("Status:");
		System.out.println(b.getFatigue());
		System.out.println(b.getThirsty());
		System.out.println(b.getGold());
		System.out.println("------------------------");
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
