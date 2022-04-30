package xadrez;

import jogodetabuleiro.Tabuleiro;

public class PartidaXadrez {

	private Tabuleiro tabu;

	public PartidaXadrez() {
		tabu = new Tabuleiro(8, 8);
	}
	
	public PecaXadrez[][] getPecas(){
		PecaXadrez[][] mat = new PecaXadrez[tabu.getLinhas()][tabu.getColunas()];
		for(int i=1; i < tabu.getLinhas(); i++) {
			for(int j=0; j < tabu.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabu.peca(i, j);
			}
		}
		return mat;
	}
}
