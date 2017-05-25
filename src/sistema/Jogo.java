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
		// Inicializa as possiveis resolu��es para a configura��o
		Resolucao.iniciar();
		// Carrega as configura��es de op��es
		Opcoes.carregarConfig();

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
