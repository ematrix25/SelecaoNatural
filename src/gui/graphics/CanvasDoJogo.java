package gui.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import gui.TelaDoJogo;
import gui.TelaDoQuestionario;

/**
 * @author Emanuel
 *
 */
public class CanvasDoJogo extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TelaDoJogo telaDoJogo;
	private Thread thread;
	private boolean rodando = false;
	private int qps, aps;
	private int x = 100, y = 100, cont = 0;
	private boolean xdir = true, ydir = false;

	public synchronized void start(TelaDoJogo telaDoJogo) {
		this.telaDoJogo = telaDoJogo;
		rodando = true;
		thread = new Thread(this, "TelaDoJogo");
		thread.start();
	}

	public synchronized void stop() {
		rodando = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long tempoAgora = System.nanoTime(), tempoAntes;
		long temporizador = System.currentTimeMillis();
		final double nanoSegundos = 1000000000.0 / 60.0;
		double deltaTempo = 0.0;
		int quadros = 0, atualizacoes = 0;
		int textCont = 0;
		requestFocus();
		while (rodando) {
			tempoAntes = tempoAgora;
			tempoAgora = System.nanoTime();
			deltaTempo += (tempoAgora - tempoAntes) / nanoSegundos;
			while (deltaTempo >= 1) {
				atualizar();
				atualizacoes++;
				deltaTempo--;
			}
			renderizar();
			quadros++;
			textCont++;
			if (textCont % 100 == 0) moveText();
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				qps = quadros;
				quadros = 0;
				aps = atualizacoes;
				atualizacoes = 0;

				// Sistema temporário para abrir o questionário
				cont++;
				if (cont >= 30) {
					telaDoJogo.setVisible(false);
					new TelaDoQuestionario();
					stop();
					telaDoJogo.dispose();
				}
			}
		}
	}

	private void atualizar() {

	}

	private void renderizar() {
		BufferStrategy buffer = getBufferStrategy();
		if (buffer == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics graficos = buffer.getDrawGraphics();
		graficos.setColor(Color.white);
		graficos.fillRect(0, 0, getWidth(), getHeight());
		graficos.setColor(Color.green);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString("QPS: " + qps, 10, 10);
		graficos.setColor(Color.red);
		graficos.drawString("APS: " + aps, 10, 30);
		graficos.setFont(new Font("Verdana", 0, 25));
		graficos.setColor(Color.black);
		graficos.drawString("Janela do Jogo", x, y);
		graficos.dispose();
		buffer.show();
	}

	private void moveText() {
		if (x == this.getWidth() - 183 || x == 0) xdir = !xdir;
		if (y == this.getHeight() - 5 || y == 19) ydir = !ydir;
		if (xdir) x++;
		else x--;
		if (ydir) y++;
		else y--;
	}
}
