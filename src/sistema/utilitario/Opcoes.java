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
		carregarConfig(false, ArquivoDeConfig.ler());
	}

	/**
	 * Carrega as configura��es
	 *
	 * @param arquivar
	 * @param config
	 */
	public static void carregarConfig(boolean arquivar, int[] config) {
		configuracoes = config;
		
		// Modifica a configura��o do controle do jogo
		if (config[0] == 1) controlePorMouse = false;
		else controlePorMouse = true;
		
		// Se for arquivar as configura��es a resolu��o n�o ser� alterada
		if (arquivar) {
			ArquivoDeConfig.escrever(config);
			return;
		}
		
		// Modifica a resolu��o da tela
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
