package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import sistema.visao.Tela;

/**
 * Cria Painel do Jogo
 * 
 * @author Emanuel
 */
public class PainelDoJogo extends JPanel implements Runnable {
	// TODO Fazer o painel do jogo funcionar
	
	private static final long serialVersionUID = 1L;
	private Tela tela;
	private Thread thread;
	private volatile boolean rodando = false;
	private volatile boolean gameOver = false;
	private int x = 100, y = 100, cont = 0;
	private boolean xdir = true, ydir = false;
	private BufferedImage imagem;

	/**
	 * Inicializa o painel do jogo
	 */
	public PainelDoJogo(Tela tela) {
		this.tela = tela;
		thread = new Thread(this, "Jogo");
	}

	/**
	 * Inicia a thread do painel do jogo
	 * 
	 * @param telaDoJogo
	 */
	public synchronized void start() {
		rodando = true;
		thread.start();
	}

	/**
	 * Fecha a thread do painel do jogo
	 */
	public synchronized void stop() {
		rodando = false;
	}

	/**
	 * Executa a thread do painel do jogo
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long tempoAgora = System.nanoTime(), tempoAntes;
		long temporizador = System.currentTimeMillis();
		final double NANOSEGUNDOS = 1000000000.0 / 60.0;
		double deltaTempo = 0.0;
		int quadros = 0, atualizacoes = 0;
		requestFocus();
		while (rodando) {
			tempoAntes = tempoAgora;
			tempoAgora = System.nanoTime();
			deltaTempo += (tempoAgora - tempoAntes) / NANOSEGUNDOS;
			while (deltaTempo >= 1) {
				atualizar();
				atualizacoes++;
				deltaTempo--;
			}
			renderizar();
			quadros++;
			moveText();
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				tela.setTitle(tela.TITULO + " | " + atualizacoes + " aps e " + quadros + " qps");
				atualizacoes = 0;
				quadros = 0;				
				
				cont++;
				if (cont >= 300) {
					tela.remove(tela.painelDoJogo);
					((PainelDoJogo) tela.painelDoJogo).stop();
					// tela.add(tela.painelDoQuest);
					// ((PainelDoQuest) tela.painelDoQuest).start();
				}
			}
			repaint();

			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
		}
		// System.exit(0);
	}

	/**
	 * Atualiza todos os dados do jogo
	 */
	private void atualizar() {
		if (!gameOver) {
		}
	}

	/**
	 * Renderizar o painel na tela
	 */
	private void renderizar() {
		imagem = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();
		graficos.setColor(Color.white);
		graficos.fillRect(0, 0, getWidth(), getHeight());
		graficos.setColor(Color.black);
		graficos.setFont(new Font("Verdana", 0, 25));
		graficos.drawString("Janela do Jogo", x, y);
		graficos.dispose();
	}

	/**
	 * Faz a movimentacao das posicoes de x e y do texto
	 */
	private void moveText() {
		if (x == this.getWidth() - 183 || x == 0) xdir = !xdir;
		if (y == this.getHeight() - 5 || y == 19) ydir = !ydir;
		if (xdir) x++;
		else x--;
		if (ydir) y++;
		else y--;
	}

	/**
	 * Modela a imagem no menu
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		super.paintComponent(graficos);
		if (imagem != null) graficos.drawImage(imagem, 0, 0, null);
	}
}
