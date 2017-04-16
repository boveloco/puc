package Omega;

import States.Globals.GlobalBillie;
import States.LookAtWeather;

public class Billie extends Player {
	
	public Billie() {
		super("Billie");
		manager.changeState(LookAtWeather.getInstance());
		manager.changeGlobalState(GlobalBillie.getInstance());
	}


}
