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
		
	}

	@Override
	public void execute(Bob b) {
		b.addFatigue(r.nextInt(10));
		b.addThirsty(r.nextInt(5));
		if(r.nextInt(2) == 1) {
			b.addNugets(1);
			this.flagGold = true;
		}
		
		if(b.isThirsty()){
			b.changeState(new QuenchThirst());
		} 
		
		if(b.isTired()) {
			b.changeState(new GoHomeAndSleepTillRested());
		} 
		
	}

	@Override
	public void exit(Bob b) {
		if(flagGold){
			System.out.println("This was a nice day. :D Many many gooooooollldssss");
		} else {
			System.out.println("Meh - Worth nothing.");
		}
		
		
	}

}
