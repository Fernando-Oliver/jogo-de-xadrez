package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Cavalo extends PecaXadrez {

	
	
	public Cavalo() {}

	public Cavalo(Tabuleiro tabu, Cores cores) {
		super(tabu, cores);
	}

	@Override
	public String toString() {
		return "C";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabu().peca(posicao);
		return p == null || p.getCores() != getCores();
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabu().getLinhas()][getTabu().getColunas()];

		Posicao p = new Posicao(0, 0);

		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
	
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() + 2, posicao.getColuna() - 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		return mat;
	}

}
