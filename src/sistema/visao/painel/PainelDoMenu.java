package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.periferico.Mouse;
import sistema.visao.Tela;

/**
 * Cria o Painel do Menu
 * 
 * @author Emanuel
 */
public class PainelDoMenu extends Painel implements Runnable {

	private static final long serialVersionUID = 1L;

	private Tela tela;
	private Thread thread;
	private Mouse mouse;
	private Image imagem;

	private volatile boolean rodando = false;

	/**
	 * Inicializa o painel do menu
	 *
	 * @param tela
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
	 * Modela a imagem na tela do menu
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);

		renderizarBotao(graficos, "Sobre", 50);
		renderizarBotao(graficos, "Opcoes", 100);
		renderizarBotao(graficos, "Continuar", 150);
		renderizarBotao(graficos, "Jogar", 200);

		graficos.dispose();
	}

	/**
	 * Renderiza um botao com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioY
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioY) {
		int x = this.getWidth() - 100, y = this.getHeight() - desvioY;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, 90, 30);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y)) acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Realiza a ação do botao quando clicado
	 * 
	 * @see sistema.visao.painel.Painel#acaoDoBotao(char)
	 */
	protected void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 'J':
			tela.remove(tela.painelDoMenu);
			((PainelDoMenu) tela.painelDoMenu).stop();
			tela.add(tela.painelDoJogo);
			((PainelDoJogo) tela.painelDoJogo).start();
			break;
		case 'C':
			// TODO Criar uma acao para o botao continuar
			break;
		case 'O':
			// TODO Criar uma acao para o botao opcoes
			break;
		case 'S':
			// TODO Criar uma acao para o botão sobre
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}
}
