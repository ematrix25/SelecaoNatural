package sistema.interface_grafica.painel;

import sistema.interface_grafica.Tela;
import sistema.interface_grafica.renderizador.RendDaSelecao;
import sistema.interface_grafica.renderizador.RendDoJogo;
import sistema.utilitario.periferico.Teclado;

/**
 * Cria Painel do Jogo
 * 
 * @author Emanuel
 */
public class PainelDoJogo extends Painel {
	// TODO Implementar o Jogo nesse painel
	private static final long serialVersionUID = 1L;
	
	private RendDaSelecao rendDaSelecao;
	private RendDoJogo rendDoJogo;

	private volatile boolean gameOver = false;

	public char telaAtiva = 'M';

	public int qtdCelulas = 1000, pontuacao = qtdCelulas * 10 + 110;
	public final float MASSA_CELULAR_MAX = 100.0f;
	public float massaCelular = 10.0f;

	private int cont = 0, contAntes = 0;

	/**
	 * Inicializa o painel do jogo
	 * 
	 * @param tela
	 */
	public PainelDoJogo(Tela tela) {
		super(tela, "Jogo");
		rendDaSelecao = new RendDaSelecao(this);
		rendDoJogo = new RendDoJogo(this);
		telaAtiva = 'S';
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
			switch (telaAtiva) {
			case 'S':
				imagem = rendDaSelecao.renderizar();
				break;
			case 'J':
				imagem = rendDoJogo.renderizar();
				break;
			default:
				System.out.println("Tela desconhecida");
				break;
			}
			quadros++;

			// TODO Remover depois
			// moveText();

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
	 * Realiza a ação do botão quando clicado
	 * 
	 * @see sistema.interface_grafica.painel.Painel#acaoDoBotao(char, char)
	 */
	public void acaoDoBotao(char telaDoBotao, char inicial) {
		// TODO Implementar as ações dos botões aqui
		switch (inicial) {
		case 'S':
			if (telaDoBotao == 'J') voltarParaMenu();
			else {
				System.out.println("Seleção de " + rendDaSelecao.getSelecao());
				telaAtiva = 'J';
			}
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
