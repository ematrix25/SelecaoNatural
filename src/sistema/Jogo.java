package sistema;

import java.awt.EventQueue;

import sistema.interface_grafica.Janela;
import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;

/**
 * Inicia todo o processo
 * 
 * @author Emanuel
 *
 */
public class Jogo {

	public static void main(String[] args) {		
		// Inicializa as possiveis resoluções para a configuração
		Resolucao.iniciar();
		// Carrega as configurações de opções
		Opcoes.carregarConfig();

		// Inicia a tela do jogo
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Janela();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
