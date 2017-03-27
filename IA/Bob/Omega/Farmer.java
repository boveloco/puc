package Omega;

import java.util.Random;
import java.util.Scanner;

public class Farmer {
	Random r = new Random();;
	public int goldLimit = 100, thirstLimit = 450, tiredLimit = 1220;
	public int wealth = 0, gold = 0, thirst = 0, tired = 0;

	private void enterMineAndDigForNugget() {
		System.out.println("--------------------------------------------");
		System.out.println("Gold: " + gold + " Sede: " + thirst + " Cansaço: " + tired);
		System.out.println("Fazendo gold");
		while ((thirst < thirstLimit) || (tired < tiredLimit) || (gold < goldLimit)) {
			tired += r.nextInt(10);
			thirst += r.nextInt(5);
			gold += r.nextInt(2);
		}
	}

	private void visitBankAndDepositGold() {
		System.out.println("--------------------------------------------");
		wealth += gold;
		System.out.println("Depositado: " + gold + " Gold Total: " + wealth);
		gold = 0;
		System.out.println(" Sede: " + thirst + " Cansaço: " + tired);
	}

	private void goHomeAndSleepTillRested() {
		System.out.println("Descansando e recupenrando sede");
		System.out.println("Gold: " + gold + " Sede: " + thirst + " Cansaço: " + tired);
		tired = 0;
		thirst = 0;
		wealth -= r.nextInt(38);
		System.out.println("--------------------------------------------");
		System.out.println("Descansado: " + tired + " Sem sede: " + thirst);
		System.out.println("Wealth: " + wealth);
	}

	private void quenchThirst() {
		System.out.println("--------------------------------------------");
		System.out.println("Tomando umas bera!");
		System.out.println("Gold: " + gold + " Sede: " + thirst + " Cansaço: " + tired);
		thirst = 0;
		wealth -= r.nextInt(15);
		System.out.println("Sede: " + thirst);
		System.out.println("Wealth: " + wealth);
	}

	public static void main(String[] args) {
		Farmer f = new Farmer();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bora lá Bob!!!");
		while (true) {
			if (f.thirst < f.thirstLimit && f.gold < f.goldLimit && f.tired < f.tiredLimit) {
				f.enterMineAndDigForNugget();
			} else if (f.thirst >= f.thirstLimit) {
				f.quenchThirst();
			} else if (f.gold >= f.goldLimit) {
				f.visitBankAndDepositGold();
			} else {
				f.goHomeAndSleepTillRested();
			}
			System.out.println("Para sair entre com a");
			if (scanner.nextLine().equals("a")) {
				break;
			}
		}
		scanner.close();

	}

}