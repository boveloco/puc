package States;

import java.util.Random;

import Omega.Billi;

public class WalkThroughFarm implements State<Billi> {

	Random r;

	public static State<Billi> instance;

	public static State<Billi> getInstance() {
		if (WalkThroughFarm.instance == null) {
			instance = new WalkThroughFarm();
		}
		return instance;
	}

	public WalkThroughFarm() {
		r = new Random();
	}


	@Override
	public void enter(Billi t) {
		
	}

	@Override
	public void execute(Billi t) {
		System.out.println("EEE vortinha boosta, prefiro olhar para o tempo");
		if(r.nextInt(4)>0){
			t.changState(LookAtWeather.getInstance());
		}

	}

	@Override
	public void exit(Billi t) {
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
