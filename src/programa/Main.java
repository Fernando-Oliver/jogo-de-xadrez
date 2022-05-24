package programa;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezExcecao;
import xadrez.XadrezPosicao;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		PartidaXadrez PartidaXadrez = new PartidaXadrez();
		while (true) {
			try {
				UI.limparConsole();
				UI.printPartida(PartidaXadrez);
				System.out.println();
				System.out.print("origem ");
				XadrezPosicao origem = UI.lerPosicao(sc);

				boolean[][] movimentosPossiveis = PartidaXadrez.movimentosPosssiveis(origem);
				UI.limparConsole();
				UI.printTabuleiro(PartidaXadrez.getPecas(), movimentosPossiveis);

				System.out.println();
				System.out.print("Destino ");
				XadrezPosicao destino = UI.lerPosicao(sc);

				PecaXadrez pecaCapturada = PartidaXadrez.executarMovimento(origem, destino);
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
