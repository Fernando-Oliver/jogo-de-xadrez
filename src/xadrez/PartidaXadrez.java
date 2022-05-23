package xadrez;


import jogodetabuleiro.Peca;
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
		for(int i=0; i < tabu.getLinhas(); i++) {
			for(int j=0; j < tabu.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabu.peca(i, j);
			}
		}
		return mat;
	}
	
	public PecaXadrez executarMovimento(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino ) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicao(origem);
		Peca pecaCapturada = realizarMovimento(origem, destino);
		return (PecaXadrez)pecaCapturada;
	}
	private  Peca realizarMovimento(Posicao origem, Posicao destino) {
		Peca p = tabu.removePeca(origem);
		Peca pecaCapturada = tabu.removePeca(destino);
		tabu.colocarPeca(p, destino);
		return pecaCapturada;
	}
	private void validarPosicao(Posicao posicao) {
		if(!tabu.posicaoExistente(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem");
		}
		if(!tabu.peca(posicao).umMovimentoPossivel()) {
			throw new XadrezExcecao("Não existe movimentos possiveis para a peça escolhida");
		}
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
