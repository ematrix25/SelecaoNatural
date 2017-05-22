package sistema;

import java.awt.EventQueue;

import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;
import sistema.visao.Tela;

/**
 * Inicia todo o processo
 * 
 * @author Emanuel
 *
 */
public class Jogo {

	public static void main(String[] args) {
		// Prepara antes de iniciar o jogo
		Resolucao.iniciar(3);
		int[] config = { 2, 1 };
		Opcoes.carregarConfig(config);

		// Inicia a tela do jogo
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Tela();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
