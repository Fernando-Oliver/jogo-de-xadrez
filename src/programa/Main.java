package programa;

import xadrez.PartidaXadrez;

public class Main {

	public static void main(String[] args) {
		
		PartidaXadrez PartidaXadrez = new PartidaXadrez();
		UI.printTabuleiro(PartidaXadrez.getPecas());
	}

}
