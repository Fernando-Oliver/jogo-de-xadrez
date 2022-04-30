package xadrez.pecas;

import jogodetabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez{

	public Torre() {}

	public Torre(Tabuleiro tabu, Cores cores) {
		super(tabu, cores);
	}

	@Override
	public String toString() {
		return "T";
	}
}
