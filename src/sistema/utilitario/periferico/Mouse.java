package sistema.utilitario.periferico;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Classe respons�vel pelo controle de resposta do mouse
 * 
 * @author Emanuel
 */
public class Mouse implements MouseListener, MouseMotionListener {

	private static int xMouse = -1;
	private static int yMouse = -1;
	private static int botaoDoMouse = -1;
	public static int xMaximo;
	public static int yMaximo;

	/**
	 * Cria o objeto Mouse com maximos de metade dos limites da tela
	 * 
	 * @param width
	 * @param height
	 */
	public Mouse(int width, int height) {
		Mouse.xMaximo = width / 2;
		Mouse.yMaximo = height / 2;
	}

	/**
	 * Obtem o valor de X
	 * 
	 * @return X
	 */
	public static int obterX() {
		return xMouse;// - xMaximo;
	}

	/**
	 * Obtem o valor de Y
	 * 
	 * @return Y
	 */
	public static int obterY() {
		return yMouse;// - yMaximo;
	}

	/**
	 * Obtem o valor do bot�o do mouse
	 * 
	 * @return Mouse button
	 */
	public static int obterBotao() {
		return botaoDoMouse;
	}

	/**
	 * Modifica o valor do bot�o do mouse
	 * 
	 * @param botao
	 */
	public static void configurarBotao(int botao) {
		botaoDoMouse = botao;
	}

	/**
	 * Obtem o evento quando o mouse � arrastado
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando o mouse � movido
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent evento) {
		xMouse = evento.getX();
		yMouse = evento.getY();
	}

	/**
	 * Obtem o evento quando um bot�o do mouse � clicado
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando o mouse entra num componente
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando o mouse sai dum componente
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent evento) {

	}

	/**
	 * Obtem o evento quando um bot�o do mouse � pressionado
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent evento) {
		botaoDoMouse = evento.getButton();
	}

	/**
	 * Obtem o evento quando um bot�o do mouse � soltado
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent evento) {
		botaoDoMouse = -1;
	}

}
