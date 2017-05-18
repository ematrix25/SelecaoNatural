package sistema.visao.painel;

import javax.swing.JPanel;

import sistema.utilitario.periferico.Mouse;

/**
 * @author Emanuel
 *
 */
public abstract class Painel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Mouse mouse;

	/**
	 * Inicializa o painel
	 */
	public Painel() {
		mouse = new Mouse(this.getWidth(), this.getHeight());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	/**
	 * Verifica se o mouse clicou no botao em x e y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean mouseClicouNoBotao(int x, int y) {
		if (mouseEstaNoBotao(x, y) && Mouse.getButton() > -1) return true;
		return false;
	}

	/**
	 * Verifica se o mouse esta no botao
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean mouseEstaNoBotao(int x, int y) {
		int mouseX = Mouse.getX(), mouseY = Mouse.getY();
		if (mouseX >= x && mouseX <= x + 90) if (mouseY >= y && mouseY <= y + 60) return true;
		return false;
	}

	/**
	 * Realiza a ação do botao quando clicado
	 * 
	 * @param inicial
	 */
	abstract protected void acaoDoBotao(char inicial);
}
