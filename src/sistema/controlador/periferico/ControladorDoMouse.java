package sistema.controlador.periferico;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import componente.Componente;

/**
 * Classe responsável pelo controle de resposta do mouse
 * 
 * @author Emanuel
 *
 */
public class ControladorDoMouse extends Componente implements MouseListener, MouseMotionListener {

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseB = -1;
	public static int maximoX;
	public static int maximoY;

	/**
	 * Cria o objeto Mouse com maximos de metade dos limitesda tela
	 * @param width
	 * @param height
	 */
	public ControladorDoMouse(int width, int height) {
		ControladorDoMouse.maximoX = width / 2;
		ControladorDoMouse.maximoY = height / 2;
	}
	
	/**
	 * Retorna o valor de X
	 * @return X
	 */
	public static int getX() {
		return mouseX - maximoX;
	}

	/**
	 * Retorna o valor de Y
	 * @return Y
	 */
	public static int getY() {
		return mouseY - maximoY;
	}

	/**
	 * Retorna o valor do botão do mouse
	 * @return Mouse button
	 */
	public static int getButton() {
		return mouseB;
	}

	/**
	 * Obtem o evento quando o mouse é arrastado
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando o mouse é movido
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent evento) {
		mouseX = evento.getX();
		mouseY = evento.getY();
	}

	/**
	 * Obtem o evento quando um botão do mouse é clicado
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando o mouse entra num componente 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando o mouse sai dum componente 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent evento) {

	}

	/** 
	 * Obtem o evento quando um botão do mouse é pressionado
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent evento) {
		mouseB = evento.getButton();
	}

	/** 
	 * Obtem o evento quando um botão do mouse é soltado
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent evento) {
		mouseB = -1;
	}

}
