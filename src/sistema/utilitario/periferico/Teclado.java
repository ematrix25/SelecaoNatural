package sistema.utilitario.periferico;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Classe responsável pelo controle de resposta do teclado
 * 
 * @author Emanuel
 *
 */
public class Teclado implements KeyListener {
	// TODO Mapear quantidade de teclas
	private static boolean teclas[] = new boolean[120];
	public static boolean cima, baixo, esquerda, direita, correr, sair;

	/**
	 * Inicializa o teclado
	 */
	public Teclado() {
		super();
	}

	/**
	 * Atualiza o valor das variaveis das teclas
	 */
	public static void atualizar() {
		cima = teclas[KeyEvent.VK_UP] || teclas[KeyEvent.VK_W];
		baixo = teclas[KeyEvent.VK_DOWN] || teclas[KeyEvent.VK_S];
		esquerda = teclas[KeyEvent.VK_LEFT] || teclas[KeyEvent.VK_A];
		direita = teclas[KeyEvent.VK_RIGHT] || teclas[KeyEvent.VK_D];
		correr = teclas[KeyEvent.VK_SHIFT];
		sair = teclas[KeyEvent.VK_ESCAPE];
	}

	/**
	 * Obtem o evento do teclado quando uma tecla é pressionada
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */

	@Override
	public void keyPressed(KeyEvent evento) {
		teclas[evento.getKeyCode()] = true;
	}

	/**
	 * Obtem o evento do teclado quando uma tecla é soltada
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent evento) {
		teclas[evento.getKeyCode()] = false;
	}

	/**
	 * Obtem o evento do teclado quando uma tecla é teclada
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent evento) {
	}
}
