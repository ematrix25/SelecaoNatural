package utilitarios.controladores;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Emanuel
 *
 */
public class Teclado implements KeyListener {

	private boolean teclas[] = new boolean[120];
	public static boolean cima, baixo, esquerda, direita, shift;
	
	
	/**
	 * Atualiza o valor das variaveis das teclas
	 */
	public void update() {
		cima = teclas[KeyEvent.VK_UP] || teclas[KeyEvent.VK_W];
		baixo = teclas[KeyEvent.VK_DOWN] || teclas[KeyEvent.VK_S];
		esquerda = teclas[KeyEvent.VK_LEFT] || teclas[KeyEvent.VK_A];
		direita = teclas[KeyEvent.VK_RIGHT] || teclas[KeyEvent.VK_D];
		shift = teclas[KeyEvent.VK_SHIFT];
	}
	
	/**
	 * Obtem o evento do teclado quando uma tecla � pressionada 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent evento) {
		teclas[evento.getKeyCode()] = true;
	}

	/**
	 * Obtem o evento do teclado quando uma tecla � soltada
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent evento) {
		teclas[evento.getKeyCode()] = false;
	}

	/**
	 * Obtem o evento do teclado quando uma tecla � teclada
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent evento) {

	}
}
