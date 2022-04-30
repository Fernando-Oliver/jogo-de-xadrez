package xadrez;


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
		for(int i=0; i < tabu.getLinhas(); i++) {
			for(int j=0; j < tabu.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabu.peca(i, j);
			}
		}
		return mat;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabu.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	private void ConfiguracaoInicial() {
		colocarNovaPeca('B', 6, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('E', 8, new Rei(tabu, Cores.PRETO));
		colocarNovaPeca('E', 1, new Rei(tabu, Cores.BRANCO));
	}
}
