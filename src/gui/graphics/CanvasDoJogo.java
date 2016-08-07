package gui.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * @author Emanuel
 *
 */
public class CanvasDoJogo extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean rodando = false;
	private int qps, aps;

	public synchronized void start() {
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
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				qps = quadros;
				quadros = 0;
				aps = atualizacoes;
				atualizacoes = 0;
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
		graficos.drawString("Janela do Jogo", 100, 100);
		graficos.dispose();
		buffer.show();
	}
}
