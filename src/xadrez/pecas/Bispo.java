package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Bispo extends PecaXadrez {

	
	public Bispo() {}

	public Bispo(Tabuleiro tabu, Cores cores) {
		super(tabu, cores);
	}

	@Override
	public String toString() {
		return "B";
	}
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabu().getLinhas()][getTabu().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//Noroeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() - 1);
		while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		 //Nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		 //Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		       //Sudoeste
				p.setValores(posicao.getLinha() +1, posicao.getColuna() - 1);
				while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValores(p.getLinha() + 1, p.getColuna() - 1);
				}
				if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		return mat;
	}

}
