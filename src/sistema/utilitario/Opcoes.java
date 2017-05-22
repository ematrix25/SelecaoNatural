package sistema.utilitario;

/**
 * Classe das opções do jogador
 * 
 * @author Emanuel
 */
public class Opcoes {
	// TODO Carregar as configurações de um arquivo

	public static int configuracoes[];
	public static boolean controlePorMouse;
	public static int larguraPadrao, alturaPadrao;

	/**
	 * Carrega as configurações
	 * 
	 * @return
	 */
	public static void carregarConfig(int[] config) {
		configuracoes = config;
		if (config[0] == 1) controlePorMouse = false;
		else controlePorMouse = true;
		switch (config[1]) {
		case 1:
			larguraPadrao = Resolucao.larguras[0];
			alturaPadrao = Resolucao.alturas[0];
			break;
		case 2:
			larguraPadrao = Resolucao.larguras[1];
			alturaPadrao = Resolucao.alturas[1];
			break;
		case 3:
			larguraPadrao = Resolucao.larguras[2];
			alturaPadrao = Resolucao.alturas[2];
			break;
		default:
			System.out.println("Opção de tamanho de tela não reconhecido");
			break;
		}
	}
}
