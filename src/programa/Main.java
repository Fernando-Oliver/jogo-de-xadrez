package programa;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezExcecao;
import xadrez.XadrezPosicao;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		PartidaXadrez PartidaXadrez = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();
		while (true) {
			try {
				UI.limparConsole();
				UI.printPartida(PartidaXadrez, capturada);
				System.out.println();
				System.out.print("origem: ");
				XadrezPosicao origem = UI.lerPosicao(sc);

				boolean[][] movimentosPossiveis = PartidaXadrez.movimentosPosssiveis(origem);
				UI.limparConsole();
				UI.printTabuleiro(PartidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino: ");
				XadrezPosicao destino = UI.lerPosicao(sc);

				PecaXadrez pecaCapturada = PartidaXadrez.executarMovimento(origem, destino);

				if (pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
			} catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

	}

}
