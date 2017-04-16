package States;

import java.util.Random;

import Omega.*;

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

		Player billie = Manager.getInstance().getPlayer("Billie");
		MessageDispatcher.getInstance().dispatchMessage(b, billie, Messages.BillieWork.toString());
	}

	@Override
	public void execute(Player b) {
		if (b.getThirsty() >= 0)
			b.addThirsty(-r.nextInt(10));
		if (((Bob)b).getFatigue() >= 0)
			((Bob)b).addFatigue(-r.nextInt(9));

		if (((Bob) b).isRested()) {
			if(((Bob) b).hasBillieWorked()){
				b.getManager().changeState(EnterMineAndDigForNugget.getInstance());
				return;
			}
			System.out.println("Esperando o billie trabalhar, esse vagabundo e folgado em!!");

		}
	}

}
