package States;

import java.util.Random;

import Omega.Player;
import Omega.Players;

public class GoToBathroom<P> implements State<P> {

	//nao posso fazer singleton em templates?
	//da erro

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
			t.changeState(((Players<P>) t).getLastState());
		}

	}

	@Override
	public void exit(Player<P> t) {
		System.out.println("Sai do banhiero");
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
