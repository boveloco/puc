package States;

import Omega.Player;

public class BillieWorking extends AbstractState {
	
	@Override
	public void enter(Player t) {
		System.out.println(this.getClass() + " fingindo lavando a louça");
	}
	
	@Override
	public void execute(Player t) {
		if(r.nextBoolean()){
			t.getManager().blipState();
			t.getManager().changeGlobalState(new GlobalPeeness());;
		}
	}
	
}
