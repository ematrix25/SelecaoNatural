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
		// Inicializa as possiveis resolu��es para a configura��o
		Resolucao.iniciar();
		// Carrega as configura��es de op��es
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
