package sistema.utilitario.periferico;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Classe responsável pelo controle de resposta do mouse
 * 
 * @author Emanuel
 */
public class Mouse implements MouseListener, MouseMotionListener {

	private static int xMouse = -1;
	private static int yMouse = -1;
	private static int botaoDoMouse = -1;
	public static int xMaximo;
	public static int yMaximo;
	public static int diferencaX;
	public static int diferencaY;

	/**
	 * Cria o objeto Mouse com maximos de metade dos limites da tela
	 * 
	 * @param largura
	 * @param altura
	 */
	public Mouse(int largura, int altura) {
		Mouse.xMaximo = largura / 2;
		Mouse.yMaximo = altura / 2;
	}

	/**
	 * Atualiza o objeto Mouse com maximos de metade dos limites da tela
	 * 
	 * @param largura
	 * @param altura
	 */
	public static void atualizar(int largura, int altura) {
		Mouse.xMaximo = largura / 2;
		Mouse.yMaximo = altura / 2;
	}

	/**
	 * Obtém o valor de X
	 * 
	 * @return int
	 */
	public static int obterX() {
		return xMouse;
	}

	/**
	 * Obtém o valor de X relativo ao xMaximo
	 * 
	 * @return int
	 */
	public static int obterDesvioX() {
		return xMouse - xMaximo;
	}

	/**
	 * Obtém o valor de Y
	 * 
	 * @return int
	 */
	public static int obterY() {
		return yMouse;
	}

	/**
	 * Obtém o valor de Y relativo ao yMaximo
	 * 
	 * @return desvioY
	 */
	public static int obterDesvioY() {
		return yMouse - yMaximo;
	}

	/**
	 * Obtém a diferença da posição do Mouse relativo a parte do Maximo
	 * 
	 * @return int
	 */
	public static int obterDiferenca(int fator) {
		diferencaX = Mouse.obterDiferencaX(fator);
		diferencaY = Mouse.obterDiferencaY(fator);
		return (diferencaY != 0) ? diferencaY : diferencaX;

	}

	/**
	 * Obtém a diferença de X relativo a parte do xMaximo
	 * 
	 * @return int
	 */
	private static int obterDiferencaX(int fator) {
		return obterDesvioX() / (xMaximo / fator);
	}

	/**
	 * Obtém a diferença de Y relativo a parte do yMaximo
	 * 
	 * @return int
	 */
	private static int obterDiferencaY(int fator) {
		return obterDesvioY() / (yMaximo / fator);
	}

	/**
	 * Obtém o valor do botão do mouse
	 * 
	 * @return int
	 */
	public static int obterBotao() {
		return botaoDoMouse;
	}

	/**
	 * Modifica o valor do botão do mouse
	 */
	public static void reconfigurarBotao() {
		botaoDoMouse = -1;
	}

	/**
	 * Obtém o evento quando o mouse é arrastado
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent evento) {

	}

	/**
	 * Obtém o evento quando o mouse é movido
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent evento) {
		xMouse = evento.getX();
		yMouse = evento.getY();
	}

	/**
	 * Obtém o evento quando um botão do mouse é clicado
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent evento) {

	}

	/**
	 * Obtém o evento quando o mouse entra num componente
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent evento) {

	}

	/**
	 * Obtém o evento quando o mouse sai dum componente
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent evento) {

	}

	/**
	 * Obtém o evento quando um botão do mouse é pressionado
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent evento) {
		botaoDoMouse = evento.getButton();
	}

	/**
	 * Obtém o evento quando um botão do mouse é soltado
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent evento) {
		botaoDoMouse = -1;
	}

}
