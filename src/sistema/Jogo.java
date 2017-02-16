package sistema;

import java.io.IOException;

import sistema.fronteira.TelaDoMenu;

/**
 * Inicia todo o processo
 * 
 * @author Emanuel
 *
 */
public class Jogo {
	public static void main(String[] args) {
		try {
			new TelaDoMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
