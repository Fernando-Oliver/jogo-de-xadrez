package xadrez.pecas;

import jogodetabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	public Rei() {}

	public Rei(Tabuleiro tabu, Cores cores) {
		super(tabu, cores);
	}

	@Override
	public String toString() {
		return "R";
	}
}