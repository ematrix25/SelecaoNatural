package sistema.visao.painel;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import sistema.utilitario.periferico.Mouse;
import sistema.visao.Tela;

/**
 * Cria o Painel do Questionario
 * 
 * @author Emanuel
 */
public class PainelDoQuest extends JPanel implements Runnable {
	// TODO Terminar o painel baseado na TelaDoQuestionario

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private Tela tela;
	private Thread thread;
	private Mouse mouse;
	private Image imagem;

	private volatile boolean rodando = false;

	/**
	 * Inicializa o painel do questionario
	 *
	 * @param tela
	 */
	public PainelDoQuest(Tela tela) {
		// Creio que nao precisa de referencia para a tela
		this.tela = tela;
		thread = new Thread("Quest");
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
	 * Inicia a thread da painel do questionario
	 */
	public void start() {
		rodando = true;
		thread.start();
	}

	/**
	 * Finaliza a thread da painel do questionario
	 */
	public void stop() {
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
	 * Modela a imagem no questionario
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);

		graficos.dispose();
	}
}
