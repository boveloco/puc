package States.Globals;

import Omega.Bob;
import Omega.Message;
import Omega.Messages;
import Omega.Player;
import States.AbstractState;

public class GlobalBob extends AbstractState {
    private static GlobalBob ourInstance = new GlobalBob();

    public static GlobalBob getInstance() {
        return ourInstance;
    }

    private GlobalBob() {
    }

    @Override
    public boolean onMessage(Player t, Message msg) {
        if(msg.getMsg().compareToIgnoreCase(Messages.WorkDone.toString()) == 0){
            ((Bob)t).setBillieWorked(true);
            return true;
        }
        return false;
    }
}
