package States;

import java.util.Random;

import Omega.Player;

public class GoToBathroom extends AbstractState{

	Random r;
	public GoToBathroom() {
		r = new Random();
	}

	@Override
	public void enter(Player t) {
		System.out.println("Fui mijar!");
		System.out.println(t.getClass() + " Entrou no banhiero");
	}

	@Override
	public void execute(Player t) {
		if (r.nextInt(4) > 0) {
			t.getManager().blipState();;
		}

	}

}
