package States;

import Omega.Player;

import java.util.Random;

/**
 * Created by projecao on 06/04/17.
 */
public class GlobalPeeness<T> extends AbstractState<T> {

   @Override
    public void execute(Player<T> p){
        if(r.nextInt(100) == 0){
            p.getManager().changeState(new GoToBathroom<T>());
        }
   }
}
