package xadrez.pecas;

import jogodetabuleiro.Posicao;
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
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean[][] mat = new boolean[getTabu().getLinhas()][getTabu().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		//Acima
		p.setValores(posicao.getLinha() -1, posicao.getColuna());
		while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() -1);
		}
		if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		 //Esquerda
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() -1);
		}
		if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		 //Direita
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() +1);
		}
		if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		       //Abaixo
				p.setValores(posicao.getLinha() +1, posicao.getColuna());
				while(getTabu().posicaoExistente(p) && !getTabu().temUmaPeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setLinha(p.getLinha() +1);
				}
				if(getTabu().posicaoExistente(p) && existePecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
		
		return mat;
	}
	
}
