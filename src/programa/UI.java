package programa;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

public class UI {
	
	public static XadrezPosicao lerPosicao(Scanner sc){
		try {
		String s = sc.nextLine();
		char coluna = s.charAt(0);
		int linha = Integer.parseInt(s.substring(1));
		return new XadrezPosicao(coluna, linha);
		}catch(RuntimeException e){
			throw new InputMismatchException("Erro ao ler os dados, valores validos de A1 até H8");
		}
	}
	
	public static void printTabuleiro(PecaXadrez[][]pecas) {
		for(int i = 0; i < pecas.length; i++ ) {
			System.out.print((8 - i) + " ");
			for(int j = 0; j<pecas.length; j++) {
				printPecas(pecas[i][j]);
			}
			System.out.println();
		}
		System.out.println("  A B C D E F G H");
	}
	
	private static void printPecas(PecaXadrez peca) {
		if(peca == null) {
			System.out.print("-");
		}else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}
}
