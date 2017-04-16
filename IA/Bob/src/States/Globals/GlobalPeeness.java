package States.Globals;

import Omega.Message;
import Omega.Player;
import States.AbstractState;
import States.GoToBathroom;
import States.State;


public class GlobalPeeness extends AbstractState {

	static State<Player> instance;

	
	public static State<Player> getInstance() {
		if(instance == null){
			instance = new GlobalPeeness();
		}
		return instance;
	}

	@Override
		public void enter(Player t) {
			System.out.println("Global voltou para o peeness");
		}

   @Override
    public void execute(Player p){
        if(r.nextInt(100) == 0){
            p.getManager().changeState(new GoToBathroom());
        }
   }
}
