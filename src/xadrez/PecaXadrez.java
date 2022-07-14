package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

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

	public XadrezPosicao pegaPosicaoXadrez() {
		return XadrezPosicao.daPosicao(posicao);
	}

	protected boolean existePecaAdversaria(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabu().peca(posicao);
		return p != null && p.getCores() != cores;
	}
}
