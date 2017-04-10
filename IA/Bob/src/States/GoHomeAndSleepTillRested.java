package States;

import java.util.Random;

import Omega.Bob;
import Omega.Player;

public class GoHomeAndSleepTillRested extends AbstractState {

	public static State<Player> instance;
	Random r;

	public static State<Player> getInstance(){
		if(instance == null){
			instance = new GoHomeAndSleepTillRested();
		}
		return instance;
	}

	public GoHomeAndSleepTillRested() {
		this.r = new Random();
	}
	@Override
	public void enter(Player b) {
		System.out.println("--------------------------------------------");
		System.out.println("I'm Home, sweet home alabama!");

	}

	@Override
	public void execute(Player b) {
		if (b.getThirsty() >= 0)
			b.addThirsty(-r.nextInt(10));
		if (((Bob)b).getFatigue() >= 0)
			((Bob)b).addFatigue(-r.nextInt(9));

		if (b.getThirsty() <= 0 && (((Bob)b).getFatigue() <= 0)) {
			b.getManager().changeState(EnterMineAndDigForNugget.getInstance());
		}
	}

}
