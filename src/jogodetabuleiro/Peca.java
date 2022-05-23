package jogodetabuleiro;

public abstract class Peca {

	protected Posicao posicao;
	private Tabuleiro tabu;
	
	public Peca() {}

	public Peca(Tabuleiro tabu) {
		this.tabu = tabu;
	}

	protected Tabuleiro getTabu() {
		return tabu;
	}
	
	public abstract boolean[][] movimentosPossiveis();
	
	public  boolean possiveisPosica(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
		
	}
	
	public boolean umMovimentoPossivel() {
		boolean[][] mat = movimentosPossiveis();
		for(int i = 0; i<mat.length; i++) {
			for(int j = 0; j< mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
