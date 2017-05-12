package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sistema.utilitario.periferico.Mouse;
import sistema.visao.Tela;

/**
 * Cria o Painel do Menu
 * 
 * @author Emanuel
 */
public class PainelDoMenu extends JPanel implements Runnable {
	// TODO Fazer o painel do menu funcionar

	private static final long serialVersionUID = 1L;
	private Tela tela;
	private Thread thread;
	private volatile boolean rodando = false;
	private Mouse mouse;
	private Image imagem;

	/**
	 * Inicializa o painel do menu
	 */
	public PainelDoMenu(Tela tela) {
		this.tela = tela;
		thread = new Thread(this, "Menu");
		mouse = new Mouse(this.getWidth(), this.getHeight());
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	/**
	 * Inicia a thread da painel do menu
	 */
	public synchronized void start() {
		rodando = true;
		thread.start();
	}

	/**
	 * Finaliza a thread da painel do menu
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
	 * Executa a tread do painel do menu
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		requestFocus();
		while (rodando) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
			repaint();
		}
	}

	/**
	 * Verifica se o mouse esta no botao
	 * 
	 * @return
	 */
	private boolean mouseEstaNoBotao(int x, int y) {
		int mouseX = Mouse.getX(), mouseY = Mouse.getY();
		if (mouseX >= x && mouseX <= x + 90) if (mouseY >= y && mouseY <= y + 60) return true;
		return false;
	}

	/**
	 * Verifica se o mouse clicou no botao em x e y
	 * 
	 * @return
	 */
	private boolean mouseClicouNoBotao(int x, int y) {
		if (mouseEstaNoBotao(x, y) && Mouse.getButton() > -1) return true;
		return false;
	}

	/**
	 * Renderiza um botao com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param texto
	 * @param x
	 * @param y
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioX, int desvioY) {
		int x = this.getWidth() - desvioX, y = this.getHeight() - desvioY;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, 90, 30);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y)) {
			graficos.setColor(Color.green);
			graficos.setFont(new Font("Verdana", 0, 25));
			graficos.drawString("Mouse clicou no botao", 50, 80);

		}
	}

	/**
	 * Modela a imagem na tela do menu
	 */
	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);

		renderizarBotao(graficos, "Sobre", 100, 50);
		renderizarBotao(graficos, "Opcoes", 100, 100);
		renderizarBotao(graficos, "Continuar", 100, 150);
		renderizarBotao(graficos, "Jogar", 100, 200);

		if (Mouse.getButton() > -1) {
			graficos.setColor(Color.green);
			graficos.setFont(new Font("Verdana", 0, 25));
			graficos.drawString("X: " + Mouse.getX() + ", Y: " + Mouse.getY(), 50, 40);
		}

		graficos.dispose();
	}
}
