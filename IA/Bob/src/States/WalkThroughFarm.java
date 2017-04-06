package States;

import java.util.Random;

import Omega.Billie;
import Omega.Player;

public class WalkThroughFarm extends AbstractState<Billie> {

	Random r;

	public static State<Billie> instance;

	public static State<Billie> getInstance() {
		if (WalkThroughFarm.instance == null) {
			instance = new WalkThroughFarm();
		}
		return instance;
	}

	public WalkThroughFarm() {
		r = new Random();
	}

	@Override
	public void execute(Player<Billie> t) {
		System.out.println("EEE vortinha boosta, prefiro olhar para o tempo");
		if(r.nextInt(4)>0){
			t.getManager().changeState(LookAtWeather.getInstance());
		}

	}

}
