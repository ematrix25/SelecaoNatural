package sistema;

import java.awt.EventQueue;

import sistema.visao.Tela;

/**
 * Inicia todo o processo
 * 
 * @author Emanuel
 *
 */
public class Jogo {

	public static void main(String[] args) {
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
