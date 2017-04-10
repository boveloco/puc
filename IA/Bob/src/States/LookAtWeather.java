package States;

import java.util.Random;

import Omega.Billie;
import Omega.Player;

public class LookAtWeather extends AbstractState {

	Random r;
	public static State<Player> instance;

	public LookAtWeather() {
		r = new Random();
	}

	public static State<Player> getInstance(){
		if(instance == null){
			instance = new LookAtWeather();
		}
		return instance;
	}

	@Override
	public void enter(Player t) {
		System.out.println("Acho que vou dar uma olhada no tempo");

	}

	@Override
	public void execute(Player t) {
		if(r.nextBoolean()){
			System.out.println("Ceu ta limpo, eu ahco!");
		} else {
			System.out.println("Ceu ta uma BOSTA");
		}

		if(r.nextInt(4) == 3) {
			t.getManager().changeState(WalkThroughFarm.getInstance());
		}

	}

	@Override
	public void exit(Player t) {
		System.out.println("Vou dar umas vorta.");
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
