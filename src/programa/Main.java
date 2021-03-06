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
		while (!PartidaXadrez.getXequeMate()) {
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
				if(PartidaXadrez.getPromocao() != null) {
					System.out.println("Escolha a pe?a promovida (B/C/T/A): ");
					String tipo = sc.nextLine().toUpperCase();
					while(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("A")) {
						System.out.println("Valor inv?lido! escolha uma dessas pe?as (B/C/T/A): ");
						 tipo = sc.nextLine().toUpperCase();
					}
					PartidaXadrez.substituirPecaPromovida(tipo);
				}
			} catch (XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}

		UI.limparConsole();
		UI.printPartida(PartidaXadrez, capturada);
	}

}
