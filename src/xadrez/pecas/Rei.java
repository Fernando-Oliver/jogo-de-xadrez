package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {

	private PartidaXadrez partidaXadrez;

	public Rei() {
	}

	public Rei(Tabuleiro tabu, Cores cores, PartidaXadrez partidaXadrez) {
		super(tabu, cores);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabu().peca(posicao);
		return p == null || p.getCores() != getCores();
	}

	private boolean testarTorreRoque(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabu().peca(posicao);
		return p != null && p instanceof Torre && p.getCores() == getCores() && p.getContarMovimentos() == 0;
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabu().getLinhas()][getTabu().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Acima
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Abaixo
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		// Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabu().posicaoExistente(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// #movimento especial roque
		if (getContarMovimentos() == 0 && !partidaXadrez.getXeque()) {
			// #movimento especial roque pequeno
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testarTorreRoque(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabu().peca(p1) == null && getTabu().peca(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			// #movimento especial roque grande
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testarTorreRoque(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabu().peca(p1) == null && getTabu().peca(p2) == null && getTabu().peca(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
		}
		return mat;
	}
}
