package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabu;
	private boolean xeque;
	private boolean xequeMate;
	private PecaXadrez enPassantVulneravel;
	private PecaXadrez promocao;

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

	public boolean getXeque() {
		return xeque;
	}

	public boolean getXequeMate() {
		return xequeMate;
	}

	public PecaXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}
	
	public PecaXadrez getPromocao() {
		return promocao;
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

		if (testarXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Você nõa pode se colocar em xeque!");
		}

		PecaXadrez pecaMoveu = (PecaXadrez) tabu.peca(destino);
		
		// #movimento especial promocao
		promocao = null;
		if(pecaMoveu instanceof Peao) {
			if(pecaMoveu.getCores() == Cores.BRANCO && destino.getColuna() == 0 || pecaMoveu.getCores() == Cores.PRETO && destino.getColuna() == 7 ) {
				promocao = (PecaXadrez)tabu.peca(destino);
				promocao = substituirPecaPromovida("A");
			}
		}

		xeque = (testarXeque(oponente(jogadorAtual))) ? true : false;
		if (testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		} else {
			proximoTurno();
		}

		// #movimento especial en passant
		if (pecaMoveu instanceof Peao && (destino.getLinha() == origem.getColuna() - 2)
				|| destino.getLinha() == origem.getColuna() + 2) {
			enPassantVulneravel = pecaMoveu;
		} else {
			enPassantVulneravel = null;
		}
		return (PecaXadrez) pecaCapturada;
	}
	
	public PecaXadrez substituirPecaPromovida(String tipo) {
		if(promocao == null) {
			throw new IllegalStateException("Não há peça para ser promovida!");
		}
		if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("A")) {
			throw new InvalidParameterException("Promoção inválida!");
		}
		
		Posicao pos = promocao.pegaPosicaoXadrez().toPosicao();
		Peca p = tabu.removePeca(pos);
		pecasNoTabuleiro.remove(p);
		
		PecaXadrez novaPeca = novaPeca(tipo, promocao.getCores());
		tabu.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}

	private PecaXadrez novaPeca(String tipo, Cores cor) {
		if(tipo.equals("B")) return new Bispo(tabu, cor);
		if(tipo.equals("C")) return new Cavalo(tabu, cor);
		if(tipo.equals("A")) return new Rainha(tabu, cor);
	    return new Torre(tabu, cor);
	}
	
	private Peca realizarMovimento(Posicao origem, Posicao destino) {
		PecaXadrez p = (PecaXadrez) tabu.removePeca(origem);
		p.incrementarMovimentos();
		Peca pecaCapturada = tabu.removePeca(destino);
		tabu.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// #movimento especial roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabu.removePeca(origemT);
			tabu.colocarPeca(torre, destinoT);
			torre.incrementarMovimentos();
		}

		// #movimento especial roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabu.removePeca(origemT);
			tabu.colocarPeca(torre, destinoT);
			torre.incrementarMovimentos();
		}

		// #movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoDoPeao;
				if (p.getCores() == Cores.BRANCO) {
					posicaoDoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					posicaoDoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabu.removePeca(posicaoDoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}
		return pecaCapturada;
	}

	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {

		PecaXadrez p = (PecaXadrez) tabu.removePeca(destino);
		p.reducaoMovimentos();
		tabu.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabu.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);

		}

		// #movimento especial roque pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaXadrez torre = (PecaXadrez) tabu.removePeca(destinoT);
			tabu.colocarPeca(torre, origemT);
			torre.reducaoMovimentos();
		}

		// #movimento especial roque grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaXadrez torre = (PecaXadrez) tabu.removePeca(destinoT);
			tabu.colocarPeca(torre, origemT);
			torre.reducaoMovimentos();
		}
		// #movimento especial en passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
				PecaXadrez peao = (PecaXadrez)tabu.removePeca(destino);
				Posicao posicaoDoPeao;
				if (p.getCores() == Cores.BRANCO) {
					posicaoDoPeao = new Posicao(3, destino.getColuna());
				} else {
					posicaoDoPeao = new Posicao(4, destino.getColuna());
				}
				tabu.colocarPeca(peao, posicaoDoPeao);
			}
		}
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

	private Cores oponente(Cores cor) {
		return (cor == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}

	private PecaXadrez rei(Cores cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCores() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe o rei " + cor + " nesta posição!");
	}

	private boolean testarXeque(Cores cor) {
		Posicao posicaoRei = rei(cor).pegaPosicaoXadrez().toPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCores() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	public boolean testeXequeMate(Cores cor) {
		if (!testarXeque(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCores() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabu.getLinhas(); i++) {
				for (int j = 0; j < tabu.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).pegaPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = realizarMovimento(origem, destino);
						boolean testarXeque = testarXeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testarXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void colocarNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabu.colocarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void ConfiguracaoInicial() {

		colocarNovaPeca('A', 1, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('B', 1, new Cavalo(tabu, Cores.BRANCO));
		colocarNovaPeca('C', 1, new Bispo(tabu, Cores.BRANCO));
		colocarNovaPeca('D', 1, new Rainha(tabu, Cores.BRANCO));
		colocarNovaPeca('E', 1, new Rei(tabu, Cores.BRANCO, this));
		colocarNovaPeca('F', 1, new Bispo(tabu, Cores.BRANCO));
		colocarNovaPeca('G', 1, new Cavalo(tabu, Cores.BRANCO));
		colocarNovaPeca('H', 1, new Torre(tabu, Cores.BRANCO));
		colocarNovaPeca('A', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('B', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('C', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('D', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('E', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('F', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('G', 2, new Peao(tabu, Cores.BRANCO, this));
		colocarNovaPeca('H', 2, new Peao(tabu, Cores.BRANCO, this));

		colocarNovaPeca('A', 8, new Torre(tabu, Cores.PRETO));
		colocarNovaPeca('B', 8, new Cavalo(tabu, Cores.PRETO));
		colocarNovaPeca('C', 8, new Bispo(tabu, Cores.PRETO));
		colocarNovaPeca('D', 8, new Rainha(tabu, Cores.PRETO));
		colocarNovaPeca('E', 8, new Rei(tabu, Cores.PRETO, this));
		colocarNovaPeca('F', 8, new Bispo(tabu, Cores.PRETO));
		colocarNovaPeca('G', 8, new Cavalo(tabu, Cores.PRETO));
		colocarNovaPeca('H', 8, new Torre(tabu, Cores.PRETO));
		colocarNovaPeca('A', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('B', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('C', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('D', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('E', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('F', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('G', 7, new Peao(tabu, Cores.PRETO, this));
		colocarNovaPeca('H', 7, new Peao(tabu, Cores.PRETO, this));

	}
}
