package utilitarios;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Calcula a resolução da tela relativa a resolucao do computador
 * 
 * @author Emanuel
 */
public class Resolucao {
	private int larguraDaComp, alturaDaComp;

	public Resolucao() {
		Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
		larguraDaComp = dimensao.width;
		alturaDaComp = dimensao.height;
	}
	
	public int calcLarguraRelativa(int altura) {
		return (altura*larguraDaComp)/alturaDaComp;
	}
	
	public int calcAlturaRelativa(int largura) {
		return (largura*alturaDaComp)/larguraDaComp;
	}

	public static void main(String[] args) {
		Resolucao res = new Resolucao();
		System.out.println("Altura = " + 450);
		System.out.println("Largura = " + res.calcLarguraRelativa(450));
	}
}
