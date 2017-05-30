package sistema.interface_grafica.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import sistema.interface_grafica.Tela;
import sistema.utilitario.periferico.Teclado;

/**
 * Cria Painel do Jogo
 * 
 * @author Emanuel
 */
public class PainelDoJogo extends Painel {
	// TODO Implementar o Jogo nesse painel

	private static final long serialVersionUID = 1L;

	private volatile boolean gameOver = false;

	private int qtdCelulas = 1000, pontuacao = qtdCelulas * 10 + 110;
	private final float MASSA_CELULAR_MAX = 100.0f;
	private float massaCelular = 10.0f;

	private int x = 100, y = 100, cont = 0, contAntes = 0;
	private boolean xdir = true, ydir = false;

	/**
	 * Inicializa o painel do jogo
	 * 
	 * @param tela
	 */
	public PainelDoJogo(Tela tela) {
		super(tela, "Jogo");
	}

	/**
	 * Inicia a thread do painel do jogo
	 */
	public synchronized void iniciar() {
		super.iniciar(1.1f);
	}

	/**
	 * Retoma a thread do painel do jogo
	 */
	public synchronized void retomar() {
		super.retomar(1.1f);
	}

	/**
	 * Executa a thread do painel do jogo
	 * 
	 * @see sistema.interface_grafica.painel.Painel#run()
	 */
	@Override
	public void run() {
		long tempoAgora = System.nanoTime(), tempoAntes;
		long temporizador = System.currentTimeMillis();
		final double NANOSEGUNDOS = 1000000000.0;
		double deltaTempo = 0.0;
		int quadros = 0, atualizacoes = 0;
		while (executando) {
			requestFocusInWindow();
			// Espera 20 milissegundos para continuar a execução da thread
			try {
				Thread.sleep(20);
				synchronized (thread) {
					while (pausado) {
						thread.wait();
					}
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			// Passado 1 segundo o jogo faz 60 atualizações
			tempoAntes = tempoAgora;
			tempoAgora = System.nanoTime();
			deltaTempo += (tempoAgora - tempoAntes) / (NANOSEGUNDOS / 60.0);
			while (deltaTempo >= 1) {
				atualizar();
				atualizacoes++;
				deltaTempo--;
			}

			// Renderiza um quadro
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
				abrirQuest(60);
			}
			repaint();
		}
	}

	/**
	 * Abre o questionário quando os segundos do contator chegarem ao tempo
	 * 
	 * @param tempo
	 */
	private void abrirQuest(int tempo) {
		if (cont >= tempo) {
			// Para outros paineis
			tela.painelDoMenu.parar();
			tela.painelDeOpcoes.parar();

			// Para o painel do jogo
			tela.remove(tela.painelDoJogo);
			tela.painelDoJogo.parar();
			tela.setTitle(tela.TITULO);

			// Inicia o painel do questionário
			tela.add(tela.painelDoQuest);
			tela.painelDoQuest.iniciar();
		}
	}

	/**
	 * Atualiza todos os dados do jogo
	 */
	private void atualizar() {
		Teclado.atualizar();
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
	 * Renderizar a janela do informações
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
	 * Renderiza um rótulo com o texto em x e y
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

		// Ações conforme as teclas são pressionadas
		if (Teclado.sair) acaoDoBotao('S');
	}

	/**
	 * Faz a movimentação das posições de x e y do texto
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
	 * Realiza a ação do botão quando clicado
	 * 
	 * @see sistema.interface_grafica.painel.Painel#acaoDoBotao(char)
	 */
	@Override
	protected void acaoDoBotao(char inicial) {
		// TODO Implementar as ações dos botões aqui
		switch (inicial) {
		case 'S':
			voltarParaMenu();
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}

	/**
	 * Volta para o painel do menu
	 */
	private void voltarParaMenu() {
		// Pausa o painel do jogo
		tela.remove(tela.painelDoJogo);
		tela.painelDoJogo.pausar();
		// tela.setTitle(tela.TITULO);

		// Retoma o painel do menu
		tela.add(tela.painelDoMenu);
		tela.painelDoMenu.retomar();
	}
}
