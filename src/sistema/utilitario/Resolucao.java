package sistema.utilitario;

import java.awt.Toolkit;

/**
 * Calcula a resolução da tela relativa a resolucao do computador
 * 
 * @author Emanuel
 */
public class Resolucao {

	public static final int LARGURA_DA_RESOLUCAO = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int ALTURA_DA_RESOLUCAO = Toolkit.getDefaultToolkit().getScreenSize().height;

	public static int[] larguras;
	public static int[] alturas;
	
	/**
	 * Inicializa as possíveis medidas
	 * 
	 * @return
	 */
	public static void iniciar(int qtd) {
		larguras = new int[qtd];
		alturas = new int[qtd];
		
		for (int i = 0; i < qtd; i++) {
			alturas[i] = Resolucao.ALTURA_DA_RESOLUCAO / (qtd - i);
			larguras[i] = calcLarguraRelativa(alturas[i]);
		}
	}

	/**
	 * Calcula a largura relativa dada uma altura
	 * 
	 * @param altura
	 * @return
	 */
	public static int calcLarguraRelativa(int altura) {
		return (LARGURA_DA_RESOLUCAO * altura) / ALTURA_DA_RESOLUCAO;
	}

	/**
	 * Calcula a altura relativa dada uma largura
	 * 
	 * @param largura
	 * @return
	 */
	public static int calcAlturaRelativa(int largura) {
		return (ALTURA_DA_RESOLUCAO * largura) / LARGURA_DA_RESOLUCAO;
	}
}
