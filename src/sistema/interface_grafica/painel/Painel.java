package sistema.interface_grafica.painel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import sistema.interface_grafica.Tela;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe generica do Painel com thread
 * 
 * @author Emanuel
 */
public abstract class Painel extends JPanel implements Runnable {
	/*
	 * TODO Dica: Renderização de tudo no run desse Painel com 4 classes de
	 * renderização ao invés de outros 4 paineis. Renderização das classes é
	 * chamada em alternância no run de uma só thread.
	 */
	private static final long serialVersionUID = 1L;

	protected Tela tela;
	protected Thread thread;
	protected Teclado teclado;
	protected Mouse mouse;
	protected Image imagem;

	protected volatile boolean executando = false;
	protected volatile boolean pausado = false;

	/**
	 * Inicializa o painel
	 *
	 * @param tela
	 * @param nome
	 */
	public Painel(Tela tela, String nome) {
		this.tela = tela;
		thread = new Thread(this, nome);
		teclado = new Teclado();
		mouse = new Mouse(this.getWidth(), this.getHeight());
		setSize(tela.getWidth(), tela.getHeight());

		// Adiciona os escutadores dos perifericos
		addKeyListener(teclado);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	/**
	 * Inicia a thread do painel
	 *
	 * @param valor
	 */
	public synchronized void iniciar(float valor) {
		tela.redimensionar(valor);
		executando = true;
		thread.start();
	}

	/**
	 * Pausa a thread do painel
	 */
	public synchronized void pausar() {
		pausado = true;
	}

	/**
	 * Retoma a thread do painel
	 *
	 * @param valor
	 */
	public synchronized void retomar(float valor) {
		tela.redimensionar(valor);
		pausado = false;
		synchronized (thread) {
			thread.notify();
		}
	}

	/**
	 * Para a thread do painel
	 */
	public synchronized void parar() {
		if (pausado) {
			pausado = false;
			synchronized (thread) {
				thread.notify();
			}
		}
		executando = false;
		thread.interrupt();
	}

	/**
	 * Executa a thread do painel do questionário
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
		super.paintComponent(graficos);

		// Desenha a imagem
		if (imagem != null) {
			graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	/**
	 * Verifica se o mouse clicou no botão em x e y
	 *
	 * @param x
	 * @param y
	 * @param largura
	 * @param altura
	 * @return boolean
	 */
	public boolean mouseClicouNoBotao(int x, int y, int largura, int altura) {
		if (mouseEstaNoBotao(x, y, largura, altura) && Mouse.getBotao() > -1) {
			Mouse.setBotao(-1);
			return true;
		}
		return false;
	}

	/**
	 * Verifica se o mouse está no botão
	 *
	 * @param x
	 * @param y
	 * @param largura
	 * @param altura
	 * @return boolean
	 */
	public boolean mouseEstaNoBotao(int x, int y, int largura, int altura) {
		int mouseX = Mouse.getX(), mouseY = Mouse.getY();
		if (mouseX >= x && mouseX <= x + largura) if (mouseY >= y && mouseY <= y + altura) return true;
		return false;
	}

	/**
	 * Realiza a ação do botão quando clicado
	 * 
	 * @param telaDobotao
	 * @param inicial
	 */
	protected abstract void acaoDoBotao(char telaDobotao, char inicial);
}
