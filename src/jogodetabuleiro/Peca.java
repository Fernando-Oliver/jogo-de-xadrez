package jogodetabuleiro;

public class Peca {

	protected Posicao posicao;
	private Tabuleiro borda;
	
	public Peca() {}

	public Peca(Tabuleiro borda) {
		this.borda = borda;
	}

	protected Tabuleiro getBorda() {
		return borda;
	}
	
	
}
