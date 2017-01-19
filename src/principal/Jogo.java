package principal;

import java.io.IOException;

import fronteira.TelaDoMenu;

/**
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
