package States;

import java.util.Random;

import Omega.Bob;
import Omega.Player;

public class VisitBankAndDepositGold extends AbstractState {
	public static State<Player> instance;

	Random r;

	public VisitBankAndDepositGold() {
		r = new Random();
	}

	public static State<Player> getInstance(){
		if(instance == null){
			instance = new VisitBankAndDepositGold();
		}
		return instance;
	}

	@Override
	public void enter(Player b) {
		System.out.println("---------------------------------");
		System.out.println("Meus bolsos deveriam ser maiores.");
	}

	@Override
	public void execute(Player b) {
		if (((Bob) b).getNugets() >= 10) {
			this.changeGold((Bob)b, 10);
		} else {
			b.getManager().changeState(EnterMineAndDigForNugget.getInstance());
		}

	}

	@Override
	public void exit(Player b) {
		System.out.println("E a bufunfa aumeeeeenta. :D");
		System.out.println("To com " + ((Bob) b).getGold() + " na busanfa!");

		if (r.nextInt(30) == 20) {
			int roubado = r.nextInt(((Bob) b).getGold());
			System.out.println("PUTA MERDA, ESSE FILHA DA MAE ME ROUBOU TUDO.. TAQUELAMERDA");
			System.out.println("Roubado: " + roubado);
		}

		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void changeGold(Bob b, int n) {
		b.addGold(n);
		if(r.nextInt(9) == 1) {
			b.addNugets(-n);
			b.addGold(2);
			System.out.println("Opa, gostei do banqueiro, ele me deu um gold a mais. Vou convidar ele pro meu vel�rio..");
		} else {
			b.addNugets(-n);
			b.addGold(1);
			System.out.println("A cada dia que passa acho que esse banqueiro est� me roubando mais e mais.");			
		}
			
		try {
			Thread.currentThread();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
