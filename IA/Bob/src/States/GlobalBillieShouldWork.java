package States;

import Omega.Billie;
import Omega.Player;

public class GlobalBillieShouldWork extends AbstractState {
	
	static State<Player> instance;
	
	public static State<Player> getInstance(){
		if(instance == null){
			instance = new GlobalBillieShouldWork();
		}
		return instance;
	}
	
	@Override
	public void enter(Player p){
		System.out.println("Vai tabalhar Billie");
	}
	
	@Override
	public void execute(Player p){
		if(p instanceof Billie){
			p.getManager().changeState(new BillieWorking());
		} else {
			p.getManager().blipGlobalState();
		}
	}

}
