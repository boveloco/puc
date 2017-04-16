package States.Globals;

import Omega.Billie;
import Omega.Message;
import Omega.Messages;
import Omega.Player;
import States.AbstractState;
import States.BillieWorking;
import States.State;

public class GlobalBillie extends AbstractState {

	static State<Player> instance;

	public static State<Player> getInstance() {
		if (instance == null) {
			instance = new GlobalBillie();
		}
		return instance;
	}

	@Override
	public void enter(Player p) {
		System.out.println("Vai tabalhar Billie");
	}

	@Override
	public boolean onMessage(Player t, Message msg) {
		if(msg.getMsg().compareToIgnoreCase(Messages.BillieWork.toString()) == 0){
			t.getManager().changeState(BillieWorking.getInstance());
			return true;
		}
		return false;
	}
}