package States;

import Omega.Player;

public class BillieWorking<Billie> extends AbstractState<Billie> {
	
	@Override
	public void enter(Player<Billie> t) {
		System.out.println(this.getClass() + " fingindo lavando a louça");
	}
	
	@Override
	public void execute(Player<Billie> t) {
		if(r.nextBoolean()){
			t.getManager().blipState();
			t.getManager().changeGlobalState(new GlobalPeeness<>());;
		}
	}
	
}
