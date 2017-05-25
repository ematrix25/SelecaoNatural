package sistema.utilitario;

import sistema.utilitario.arquivo.Arquivo.ArquivoDeConfig;

/**
 * Classe das opções do jogador
 * 
 * @author Emanuel
 */
public class Opcoes {
	public static int configuracoes[];
	public static boolean controlePorMouse;
	public static int larguraPadrao, alturaPadrao;

	/**
	 * Carrega as configurações do arquivo
	 */
	public static void carregarConfig() {
		carregarConfig(false, ArquivoDeConfig.ler());
	}

	/**
	 * Carrega as configurações
	 *
	 * @param arquivar
	 * @param config
	 */
	public static void carregarConfig(boolean arquivar, int[] config) {
		configuracoes = config;
		
		// Modifica a configuração do controle do jogo
		if (config[0] == 1) controlePorMouse = false;
		else controlePorMouse = true;
		
		// Se for arquivar as configurações a resolução não será alterada
		if (arquivar) {
			ArquivoDeConfig.escrever(config);
			return;
		}
		
		// Modifica a resolução da tela
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
