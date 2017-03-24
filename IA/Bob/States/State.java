package States;

import Omega.Bob;

public interface State {
	
	public void enter(Bob b);
	
	public void execute(Bob b);
	
	public void exit(Bob b);
}
