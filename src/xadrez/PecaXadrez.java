package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Tabuleiro;

public class PecaXadrez extends Peca {

	private Cores cores;

	public PecaXadrez() {
	}

	public PecaXadrez(Tabuleiro tabu, Cores cores) {
		super(tabu);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}
}
