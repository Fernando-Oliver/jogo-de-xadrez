package xadrez;

import jogodetabuleiro.Posicao;

public class XadrezPosicao {

	private char coluna;
	private int linha;
	
	public XadrezPosicao(char coluna, int linha) {
		if(coluna < 'A' || coluna > 'H' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro instanciando a posi��o do jogo os valore v�lidos s�o de A1 at� H8.");
		}
			this.coluna = coluna;
		    this.linha = linha;
	}

	public char getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
	protected Posicao toPosicao() {
		return new Posicao(8 - linha, coluna - 'A');
	}
	
	protected static XadrezPosicao daPosicao(Posicao posicao) {
		return new XadrezPosicao((char)('A' + posicao.getColuna()), 8 - posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
}
