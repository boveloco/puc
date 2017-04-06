package States;

import java.util.Random;

import Omega.Player;
import Omega.Player;

public class GoToBathroom<P> extends AbstractState<P>{

	Random r;
	public GoToBathroom() {
		r = new Random();
	}

	@Override
	public void enter(Player<P> t) {
		System.out.println("Fui mijar!");
		System.out.println(t.getClass() + " Entrou no banhiero");
	}

	@Override
	public void execute(Player<P> t) {
		if (r.nextInt(4) > 0) {
			t.getManager().blipState();;
		}

	}

}
