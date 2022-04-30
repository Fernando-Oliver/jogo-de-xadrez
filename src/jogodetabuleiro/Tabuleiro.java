package jogodetabuleiro;

import xadrez.PartidaXadrez;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Peca[][] pecas;

	public Tabuleiro() {
	}

	public Tabuleiro(int linhas, int colunas) {
		if(linhas < 1 && colunas < 1) {
			throw new TabuleiroExcecao("Erro na criação do tabuleiro, é necessário que haja pelo menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public Peca peca(int linha, int coluna) {
		if(!posicaoExistente(linha,coluna)) {
			throw new TabuleiroExcecao("Não existe essa posição no tabuleiro.");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Não existe essa posição no tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public void colocarPeca(Peca peca, Posicao posicao) {
		if(temUmaPeca(posicao)) {
			throw new TabuleiroExcecao("Já existe uma peça nesta posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}

	private boolean posicaoExistente(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	public Peca removePeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Não existe essa posição no tabuleiro.");
		}
		if(peca(posicao) == null) {
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()]= null;
		return aux;
	}

	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
	}

	public boolean temUmaPeca(Posicao posicao) {
		if(!posicaoExistente(posicao)) {
			throw new TabuleiroExcecao("Não existe essa posição no tabuleiro.");
		}
	   return peca(posicao) != null;
	}
}
