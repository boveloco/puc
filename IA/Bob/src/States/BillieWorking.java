package States;

import Omega.*;

public class BillieWorking extends AbstractState {

	public static State<Player> instance;

	public static State<Player> getInstance(){
		if (instance == null){
			instance = new BillieWorking();
		}
		return instance;
	}
	
	@Override
	public void enter(Player t) {
		System.out.println(this.getClass() + " fingindo lavando a louca");
	}
	
	@Override
	public void execute(Player t) {
		if(r.nextBoolean()){
			t.getManager().blipState();
		}
	}

	@Override
	public void exit(Player t) {
		MessageDispatcher.getInstance().dispatchMessage(t, Manager.getInstance().getPlayer("Bob"), Messages.WorkDone.toString());
		super.exit(t);
	}
}
