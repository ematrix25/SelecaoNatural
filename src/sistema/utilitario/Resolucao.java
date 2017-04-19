package sistema.utilitario;

/**
 * Calcula a resolução da tela relativa a resolucao do computador
 * 
 * @author Emanuel
 */
public class Resolucao {
	public static int calcLarguraRelativa(int altura) {
		return (Opcoes.larguraDaResolucao * altura) / Opcoes.alturaDaResolucao;
	}

	public static int calcAlturaRelativa(int largura) {
		return (Opcoes.alturaDaResolucao * largura) / Opcoes.larguraDaResolucao;
	}
}
