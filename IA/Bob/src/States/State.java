package States;

import Omega.Message;
import Omega.Player;

public interface State<Player> {

	public void enter(Player t);
	
	public void execute(Player t);
	
	public void exit(Player t);

	public boolean onMessage(Player t, Message msg);
}
