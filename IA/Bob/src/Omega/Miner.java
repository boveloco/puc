package Omega;
public class Miner {

	Bob b;
	Billie bi;

	public Miner() {
		bi = new Billie();
		b = new Bob();
	}

	public Thread bili = new Thread(){
		@Override
		public void run(){
			while (true)
				b.getManager().update();
		}
	};

	public Thread bob = new Thread(){
		@Override
		public void run(){
			while(true)
				bi.getManager().update();
		}
	};

	public static void main(String[] args) {
		Miner m = new Miner();
		m.bili.setName("Billi");
		m.bob.setName("Bob");
		m.bili.start();
		m.bob.start();
	}
}