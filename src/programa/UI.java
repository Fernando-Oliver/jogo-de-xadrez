package programa;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cores;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

public class UI {

	public static void limparConsole() {
		System.out.println();
		System.out.flush();
	}

	public static XadrezPosicao lerPosicao(Scanner sc) {
		try {
			String s = sc.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new XadrezPosicao(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro ao ler os dados, valores validos de A1 até H8");
		}
	}

	public static void printPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturada) {
		printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		printPecaCapturada(capturada);
		System.out.println();
		System.out.println("Turno: " + partidaXadrez.getTurno());
		System.out.println("Jogador em espera: " + partidaXadrez.getJogadorAtual());
	}

	public static void printTabuleiro(PecaXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPecas(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}

	public static void printTabuleiro(PecaXadrez[][] pecas, boolean[][] movimentosPosssiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPecas(pecas[i][j], movimentosPosssiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}

	private static void printPecas(PecaXadrez peca, boolean cordefundo) {
		if (peca == null) {
			System.out.print("-");
		} else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}

	private static void printPecaCapturada(List<PecaXadrez> capturada) {
		List<PecaXadrez> branco = capturada.stream().filter(cap -> cap.getCores() == Cores.BRANCO)
				.collect(Collectors.toList());
		List<PecaXadrez> preto = capturada.stream().filter(cap -> cap.getCores() == Cores.PRETO)
				.collect(Collectors.toList());

		System.out.println("Peças capturadas");
		System.out.print(Cores.BRANCO);
		System.out.println(Arrays.toString(branco.toArray()));
		System.out.print(Cores.PRETO);
		System.out.println(Arrays.toString(preto.toArray()));
	}
}
