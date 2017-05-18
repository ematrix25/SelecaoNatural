package sistema.visao.painel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import sistema.utilitario.periferico.Mouse;
import sistema.visao.Tela;

/**
 * @author Emanuel
 *
 */
public abstract class Painel extends JPanel implements Runnable {
	//TODO Abstrair o PainelDoJogo para o Painel

	private static final long serialVersionUID = 1L;

	protected Tela tela;
	protected Thread thread;
	protected Mouse mouse;
	protected Image imagem;

	protected volatile boolean rodando = false;

	/**
	 * Inicializa o painel
	 *
	 * @param tela
	 */
	public Painel(Tela tela, String nome) {
		this.tela = tela;
		thread = new Thread(this, nome);
		mouse = new Mouse(this.getWidth(), this.getHeight());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	/**
	 * Inicia a thread do painel
	 */
	public synchronized void start(float valor) {
		tela.redimensionar((int) (tela.ALTURA_PADRAO * valor));
		rodando = true;
		thread.start();
	}

	/**
	 * Finaliza a thread do painel
	 */
	public synchronized void stop() {
		rodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Executa a thread do painel do questionario
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public abstract void run();

	/**
	 * Pinta o painel que é usado no repaint
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);

		renderizar(graficos);

		graficos.dispose();
	}

	/**
	 * Renderiza o painel
	 * 
	 * @param graficos
	 */
	protected abstract void renderizar(Graphics graficos);

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
	protected abstract void acaoDoBotao(char inicial);
}
