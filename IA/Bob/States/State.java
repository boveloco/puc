package States;

import Omega.Bob;

public interface State<T> {
	
	public void enter(T t);
	
	public void execute(T t);
	
	public void exit(T t);
}
