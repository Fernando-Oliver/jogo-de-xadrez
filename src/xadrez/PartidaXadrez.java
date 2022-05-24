package xadrez;

import java.util.ArrayList;
import java.util.List;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabu;

	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabu = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cores.BRANCO;
		ConfiguracaoInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cores getJogadorAtual() {
		return jogadorAtual;
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabu.getLinhas()][tabu.getColunas()];
		for (int i = 0; i < tabu.getLinhas(); i++) {
			for (int j = 0; j < tabu.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabu.peca(i, j);
			}
		}
		return mat;
	}

	public boolean[][] movimentosPosssiveis(XadrezPosicao posicaoorigem) {
		Posicao posicao = posicaoorigem.toPosicao();
		validarPosicaoOrigem(posicao);
		return tabu.peca(posicao).movimentosPossiveis();

	}

	public PecaXadrez executarMovimento(XadrezPosicao posicaoOrigem, XadrezPosicao posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Peca pecaCapturada = realizarMovimento(origem, destino);
		proximoTurno();
		return (PecaXadrez) pecaCapturada;
	}

	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		Peca p = tabu.removePeca(origem);
		Peca pecaCapturada = tabu.removePeca(destino);
		tabu.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabu.posicaoExistente(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem.");
		}
		if (jogadorAtual != ((PecaXadrez) tabu.peca(posicao)).getCores()) {
			throw new XadrezExcecao("A peça escolhida não é a sua!");
		}
		if (!tabu.peca(posicao).umMovimentoPossivel()) {
			throw new XadrezExcecao("Não existe movimentos possiveis para a peça escolhida.");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabu.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode se mover para a posição de destino.");
		}
	}

	private void proximoTurno() {
		turno++;
		if (jogadorAtual == Cores.BRANCO) {
			jogadorAtual = Cores.PRETO;
		} else {
			jogadorAtual = Cores.BRANCO;
		}
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabu.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void ConfiguracaoInicial() {
		colocarNovaPeca('C', 8, new Torre(tabu, Cores.PRETO));
		colocarNovaPeca('C', 7, new Torre(tabu, Cores.PRETO));
		colocarNovaPeca('D', 8, new Rei(tabu, Cores.PRETO));
		colocarNovaPeca('D', 7, new Torre(tabu, Cores.PRETO));
		colocarNovaPeca('E', 8, new Torre(tabu, Cores.PRETO));
		colocarNovaPeca('E', 7, new Torre(tabu, Cores.PRETO));

		colocarNovaPeca('C', 1, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('C', 2, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('D', 1, new Rei(tabu, Cores.BRANCO));
		colocarNovaPeca('D', 2, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('E', 1, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('E', 2, new Torre(tabu, Cores.BRANCO));
	}
}
