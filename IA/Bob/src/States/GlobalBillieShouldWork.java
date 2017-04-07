package States;

import Omega.Billie;
import Omega.Player;

public class GlobalBillieShouldWork<T> extends AbstractState<T> {
	
	@Override
	public void enter(Player<T> p){
		System.out.println("Vai tabalhar Billie");
	}
	
	@Override
	public void execute(Player<T> p){
		if(p instanceof Billie){
			p.getManager().changeState(new BillieWorking<>());
		} else {
			p.getManager().blipGlobalState();
		}
	}

}
