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
	// TODO Implementar o Jogo nesse painel

	private static final long serialVersionUID = 1L;

	private Tela tela;
	private Thread thread;
	private BufferedImage imagem;

	private volatile boolean rodando = false;
	private volatile boolean gameOver = false;

	private int qtdCelulas = 1000, pontuacao = qtdCelulas * 10 + 110;
	private float massaCelular = 10.0f;
	final float MASSA_CELULAR_MAX = 100.0f;

	private int x = 100, y = 100, cont = 0, contAntes = 0;
	private boolean xdir = true, ydir = false;

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
		tela.redimensionar((int) (tela.ALTURA_PADRAO * 1.1));
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
		final double NANOSEGUNDOS = 1000000000.0;
		double deltaTempo = 0.0;
		int quadros = 0, atualizacoes = 0;
		requestFocus();
		while (rodando) {
			tempoAntes = tempoAgora;
			tempoAgora = System.nanoTime();

			// Passado 1 segundo o jogo faz 60 atualizações
			deltaTempo += (tempoAgora - tempoAntes) / (NANOSEGUNDOS / 60.0);
			while (deltaTempo >= 1) {
				atualizar();
				atualizacoes++;
				deltaTempo--;
			}

			// Taxa de quadros varia conforme o desempenho da maquina
			renderizar();
			quadros++;

			// TODO Remover depois
			moveText();

			// Mostra APS e QPS a cada segundo
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				tela.setTitle(tela.TITULO + " | " + atualizacoes + " aps com " + quadros + " qps");
				atualizacoes = 0;
				quadros = 0;

				// Conta os segundos para abrir o painel do questionarios
				cont++;
				abrirQuest(30);
			}
			repaint();

			// Espera 20 milissegundos para continuar a execução da thread
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * Abre o questionario quando os segundos do contator chegarem ao tempo
	 * 
	 * @param tempo
	 */
	private void abrirQuest(int tempo) {
		if (cont >= tempo) {
			tela.remove(tela.painelDoJogo);
			((PainelDoJogo) tela.painelDoJogo).stop();
			tela.add(tela.painelDoQuest);
			((PainelDoQuest) tela.painelDoQuest).start();
		}
	}

	/**
	 * Atualiza todos os dados do jogo
	 */
	private void atualizar() {
		if (!gameOver) {
			// TODO Remover depois
			if (cont != contAntes) {
				contAntes = cont;
				massaCelular = 1 + (float) (Math.random() * 99);
				if (cont % 5 == 0) {
					qtdCelulas = 1 + (int) (Math.random() * 999999);
					pontuacao = qtdCelulas * 10 + (int) (Math.random() * 999999);
				}
			}
		}
	}

	/**
	 * Renderizar o painel na tela
	 */
	private void renderizar() {
		imagem = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics graficos = imagem.getGraphics();
		renderizarInfo(graficos);
		renderizarJogo(graficos);
		graficos.dispose();
	}

	/**
	 * Renderizar a janela do informacoes
	 * 
	 * @param graficos
	 */
	private void renderizarInfo(Graphics graficos) {
		int percentualMassa = (int) ((massaCelular / MASSA_CELULAR_MAX) * 100);
		final int MIL = (int) Math.pow(10, 3);
		graficos.setColor(Color.black);
		graficos.fillRect(0, 0, getWidth(), 30);

		renderizarRotulo(graficos, Color.lightGray, "Massa Celular: " + percentualMassa + "%", this.getWidth() - 10);
		if (pontuacao < MIL)
			renderizarRotulo(graficos, Color.gray, "Pontuacao: " + pontuacao, (this.getWidth() / 2) + 70);
		else renderizarRotulo(graficos, Color.gray, "Pontuacao: " + pontuacao / MIL + "K", (this.getWidth() / 2) + 70);
		if (qtdCelulas < MIL) renderizarRotulo(graficos, Color.darkGray, "Qtd Celulas: " + qtdCelulas, 150);
		else renderizarRotulo(graficos, Color.darkGray, "Qtd Celulas: " + qtdCelulas / MIL + "K", 150);
	}

	/**
	 * Renderiza um rotulo com o texto em x e y
	 * 
	 * @param graficos
	 * @param cor
	 * @param texto
	 * @param desvioX
	 */
	private void renderizarRotulo(Graphics graficos, Color cor, String texto, int desvioX) {
		int x = this.getWidth() - desvioX, y = 5;

		// Retangulo do rotulo
		graficos.setColor(cor);
		graficos.fillRect(x, y, 140, 20);

		// Texto do rotulo
		if (cor == Color.lightGray) graficos.setColor(Color.black);
		else graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 5, y + 15);
	}

	/**
	 * Renderizar a janela do jogo
	 * 
	 * @param graficos
	 */
	private void renderizarJogo(Graphics graficos) {
		graficos.setColor(Color.white);
		graficos.fillRect(0, 30, this.getWidth(), this.getHeight());
		graficos.setColor(Color.black);
		graficos.setFont(new Font("Verdana", 0, 25));
		graficos.drawString("Janela do Jogo", x, y);
	}

	/**
	 * Faz a movimentacao das posicoes de x e y do texto
	 */
	private void moveText() {
		if (x == this.getWidth() - 183 || x == 0) xdir = !xdir;
		if (y == this.getHeight() - 5 || y == 49) ydir = !ydir;
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
		if (imagem != null) graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
