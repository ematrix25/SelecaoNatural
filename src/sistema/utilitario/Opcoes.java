package sistema.utilitario;

import sistema.utilitario.arquivo.Arquivo.ArquivoDeConfig;

/**
 * Classe das op��es do jogador
 * 
 * @author Emanuel
 */
public class Opcoes {
	public static int configuracoes[];
	public static boolean controlePorMouse;
	public static int larguraPadrao, alturaPadrao;

	/**
	 * Carrega as configura��es do arquivo
	 */
	public static void carregarConfig() {
		carregarConfig(ArquivoDeConfig.ler());
	}

	/**
	 * Carrega as configura��es
	 * 
	 * @param config
	 */
	public static void carregarConfig(int[] config) {
		configuracoes = config;
		ArquivoDeConfig.escrever(config);
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
			System.out.println("Op��o de tamanho de tela n�o reconhecido");
			break;
		}
	}
}
