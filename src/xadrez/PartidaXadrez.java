package xadrez;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabu;

	public PartidaXadrez() {
		tabu = new Tabuleiro(8, 8);
		ConfiguracaoInicial();
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
	
	private void ConfiguracaoInicial() {
		tabu.colocarPeca(new Torre(tabu, Cores.BRANCO), new Posicao(2, 1));
		tabu.colocarPeca(new Rei(tabu, Cores.PRETO), new Posicao(4, 0));
		tabu.colocarPeca(new Rei(tabu, Cores.BRANCO), new Posicao(7, 4));
	}
}
