package States;

import Omega.Player;

public interface State<Player> {

	public void enter(Player t);
	
	public void execute(Player t);
	
	public void exit(Player t);
}
