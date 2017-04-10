package States;

import java.util.Random;

import Omega.Bob;
import Omega.Player;

public class EnterMineAndDigForNugget extends AbstractState {

	public static State<Player> instance;

	Random r;
	private boolean flagGold;


	public EnterMineAndDigForNugget() {
		r = new Random();
		flagGold = false;
	}

	@Override
	public void enter(Player b) {
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

	public static State<Player> getInstance() {
		if (instance == null) {
			instance = new EnterMineAndDigForNugget();
		}
		return instance;
	}

	@Override
	public void execute(Player b) {
		if(!(b instanceof Bob)){
		    System.out.println("Erro Verificar bob");
		    return;
		}
		((Bob)b).addFatigue(r.nextInt(10));
		((Bob)b).addThirsty(r.nextInt(5));
		int res = r.nextInt(4);
		if (res == 2) {
			((Bob) b).addNugets(1);
			this.flagGold = true;
		}

		if (((Bob)b).isThirsty()) {
			b.getManager().changeState(QuenchThirst.getInstance());
		}

		if ( ((Bob)b).isTired()) {
			b.getManager().changeState(GoHomeAndSleepTillRested.getInstance());
		}

		if (((Bob)b).getNugets() >= Bob.GOLDLIMIT) {
			b.getManager().changeState(VisitBankAndDepositGold.getInstance());
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
	public void exit(Player b) {
		System.out.println("-----------------------");
		System.out.println("Status:");
		System.out.println(((Bob) b).getFatigue());
		System.out.println(((Bob) b).getThirsty());
		System.out.println(((Bob) b).getGold());
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
