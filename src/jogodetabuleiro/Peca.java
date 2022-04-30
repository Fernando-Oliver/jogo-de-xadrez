package jogodetabuleiro;

public class Peca {

	protected Posicao posicao;
	private Tabuleiro tabu;
	
	public Peca() {}

	public Peca(Tabuleiro tabu) {
		this.tabu = tabu;
	}

	protected Tabuleiro getTabu() {
		return tabu;
	}
}
