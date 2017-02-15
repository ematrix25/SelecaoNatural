package sistema.utilitario;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Calcula a resolução da tela relativa a resolucao do computador
 * 
 * @author Emanuel
 */
public class Resolucao {
	private static int larguraDaComp;
	private static int alturaDaComp;

	public Resolucao() {
		Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
		larguraDaComp = dimensao.width;
		alturaDaComp = dimensao.height;
	}

	public Resolucao(int largura, int altura) {
		larguraDaComp = largura;
		alturaDaComp = altura;
	}

	public static int calcLarguraRelativa(int altura) {
		return (larguraDaComp * altura) / alturaDaComp;
	}

	public static int calcAlturaRelativa(int largura) {
		return (alturaDaComp * largura) / larguraDaComp;
	}
}
