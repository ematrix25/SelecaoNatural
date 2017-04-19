package sistema;

import java.io.IOException;

import sistema.visao.TelaDoMenu;

/**
 * Inicia todo o processo
 * 
 * @author Emanuel
 *
 */
public class Jogo {
	// TODO Trocar TelaDoMenu para Tela quando estiver pronta

	public static void main(String[] args) {
		try {
			new TelaDoMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
