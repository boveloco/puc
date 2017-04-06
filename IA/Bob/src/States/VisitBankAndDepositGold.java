package States;

import java.util.Random;

import Omega.Bob;
import Omega.Player;

public class VisitBankAndDepositGold implements State<Bob> {

	Random r;

	public VisitBankAndDepositGold() {
		r = new Random();
	}

	@Override
	public void enter(Player<Bob> b) {
		System.out.println("---------------------------------");
		System.out.println("Meus bolsos deveriam ser maiores.");
	}

	@Override
	public void execute(Player<Bob> b) {
		if (((Bob) b).getNugets() >= 10) {
			this.changeGold(b, 10);
		} else {
			b.changeState(new EnterMineAndDigForNugget());
		}

	}

	@Override
	public void exit(Player<Bob> b) {
		System.out.println("E a bufunfa aumeeeeenta. :D");
		System.out.println("To com " + ((Bob) b).getGold() + " na busanfa!");

		if (r.nextInt(30) == 20) {
			int roubado = r.nextInt(((Bob) b).getGold());
			System.out.println("PUTA MERDA, ESSE FILHA DA MAE ME ROUBOU TUDO.. TAQUELAMERDA");
			System.out.println("Roubado: " + roubado);
		}

	}

	private void changeGold(Player<Bob> b, int n) {
		((Bob) b).addGold(n);
		if(r.nextInt(9) == 1) {
			((Bob) b).addNugets(-n);
			((Bob) b).addGold(2);
			System.out.println("Opa, gostei do banqueiro, ele me deu um gold a mais. Vou convidar ele pro meu vel�rio..");
		} else {
			((Bob) b).addNugets(-n);
			((Bob) b).addGold(1);
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
