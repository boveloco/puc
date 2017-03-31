package States;

import Omega.Player;

public interface State<T> {
	
	public void enter(Player<T> t);
	
	public void execute(Player<T> t);
	
	public void exit(Player<T> t);
}
