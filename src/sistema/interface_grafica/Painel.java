package sistema.interface_grafica;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import componente.Componente;
import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.interface_grafica.renderizador.RendDaSelecao;
import sistema.interface_grafica.renderizador.RendDeOpcoes;
import sistema.interface_grafica.renderizador.RendDoJogo;
import sistema.interface_grafica.renderizador.RendDoMenu;
import sistema.interface_grafica.renderizador.RendDoQuest;
import sistema.utilitario.Opcoes;
import sistema.utilitario.arquivo.Arquivo.ArquivoDoQuest;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe do Painel com thread
 * 
 * @author Emanuel
 */
public class Painel extends Canvas implements Runnable {
	// TODO Implementar o Jogo aqui e renderizar no RendDoJogo
	private static final long serialVersionUID = 1L;

	private Janela tela;
	private Image imagem;

	private Thread thread;
	private Teclado teclado;
	private Mouse mouse;

	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;

	private RendDoMenu rendDoMenu;
	private RendDeOpcoes rendDeOpcoes;
	private RendDaSelecao rendDaSelecao;
	private RendDoJogo rendDoJogo;
	private RendDoQuest rendDoQuest;

	private char telaAtiva = 'M';
	public boolean ehContinuavel = false;

	public int qtdCelulas = 1000, pontuacao = qtdCelulas * 10 + 110;
	public final float MASSA_CELULAR_MAX = 100.0f;
	public float massaCelular = 10.0f;

	private int cont = 0, contAntes = 0;

	/**
	 * Inicializa o painel
	 *
	 * @param tela
	 */
	public Painel(Janela tela) {
		this.tela = tela;
		tela.redimensionar(1);
		setSize(tela.getWidth(), tela.getHeight());

		thread = new Thread(this, "Jogo");
		teclado = new Teclado();
		mouse = new Mouse(this.getWidth(), this.getHeight());

		// Adiciona os escutadores dos perif�ricos
		addKeyListener(teclado);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		rendDoMenu = new RendDoMenu(this);
		rendDeOpcoes = new RendDeOpcoes(this);
		thread.start();
	}

	/**
	 * Executa a thread do painel
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
		while (true) {
			requestFocusInWindow();
			try {
				if(telaAtiva=='J')
					Thread.sleep(20);
				else
					Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Atualiza os dados do jogo
			if (telaAtiva == 'S' || telaAtiva == 'J') {
				tempoAntes = tempoAgora;
				tempoAgora = System.nanoTime();
				deltaTempo += (tempoAgora - tempoAntes) / (NANOSEGUNDOS / 60.0);
				while (deltaTempo >= 1) {
					atualizar();
					atualizacoes++;
					deltaTempo--;
				}
			}

			// Renderiza um quadro
			renderizar();
			quadros++;

			// Mostra APS e QPS a cada segundo
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				tela.setTitle(tela.TITULO + " | " + atualizacoes + " aps com " + quadros + " qps");
				atualizacoes = 0;
				quadros = 0;

				// Conta os segundos para abrir o painel do questionarios
				if (telaAtiva == 'S' || telaAtiva == 'J') {
					cont++;
					if (cont > 60) {
						tela.redimensionar(1.7f);
						rendDoQuest = new RendDoQuest(this);
						telaAtiva = 'Q';
					}
				}
			}
		}
	}

	/**
	 * Renderiza a tela do painel
	 */
	private void renderizar() {
		BufferStrategy estrategiaDeBuffer = getBufferStrategy();
		if (estrategiaDeBuffer == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics graficos = estrategiaDeBuffer.getDrawGraphics();
		switch (telaAtiva) {
		case 'M':
			imagem = rendDoMenu.renderizar();
			break;
		case 'O':
			imagem = rendDeOpcoes.renderizar();
			break;
		case 'S':
			imagem = rendDaSelecao.renderizar();
			break;
		case 'J':
			imagem = rendDoJogo.renderizar();
			break;
		case 'Q':
			imagem = rendDoQuest.renderizar();
			break;
		default:
			System.out.println("Tela desconhecida");
			break;
		}
		graficos.drawImage(imagem, 0, 0, getWidth(), getHeight(), null);
		graficos.dispose();
		estrategiaDeBuffer.show();		
	}

	/**
	 * Atualiza todos os dados do jogo
	 */
	private void atualizar() {
		Teclado.atualizar();
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

	/**
	 * Verifica se o mouse clicou no bot�o em x e y
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
	 * Verifica se o mouse est� no bot�o
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
	 * Realiza a a��o do bot�o quando clicado
	 * 
	 * @param inicial
	 */
	public void acaoDoBotao(char inicial) {
		// TODO Implementar as a��es das teclas do jogo aqui
		switch (inicial) {
		case 's':
			voltarParaMenu();
			break;
		case 'S':
			if (telaAtiva == 'S') {
				int selecao = rendDaSelecao.obtemSelecao();
				Integer especime = controladorDoAmbiente
						.obterEspecie(controladorDoAmbiente.obterAmbiente().obterEspecieID(selecao)).get(0);
				Especie especie = controladorDaEntidade.obterComponente(especime, Especime.class).especie;
				System.out.println("Sele��o do especime " + especime + " da especie " + especie);

				voltarParaJogo();
			} else if (telaAtiva == 'O') {
				Opcoes.carregarConfig(true, rendDeOpcoes.obterConfiguracoes());
				voltarParaMenu();
			} else if (telaAtiva == 'Q') {
				ArquivoDoQuest.escrever(rendDoQuest.obterRespostas());
				tela.dispatchEvent(new WindowEvent(tela, WindowEvent.WINDOW_CLOSING));
			}
			break;
		case 'N':
			tela.redimensionar(1.1f);
			telaAtiva = 'S';
			iniciarJogo();
			ehContinuavel = true;
			break;
		case 'C':
			if (telaAtiva == 'M') voltarParaJogo();
			else if (telaAtiva == 'O') voltarParaMenu();
			break;
		case 'O':
			tela.redimensionar(1.1f);
			telaAtiva = 'O';
			break;
		default:
			System.out.println("Botao clicado n�o reconhecido");
			break;
		}
	}

	/**
	 * Volta para a tela do menu
	 */
	private void voltarParaMenu() {
		tela.redimensionar(1);
		telaAtiva = 'M';
	}

	/**
	 * Volta para a tela do jogo
	 */
	private void voltarParaJogo() {
		tela.redimensionar(1.1f);
		telaAtiva = 'J';
	}

	/**
	 * Inicia o jogo
	 */
	private void iniciarJogo() {
		controladorDaEntidade = new ControladorDaEntidade();
		controladorDoAmbiente = new ControladorDoAmbiente();
		gerarAmbiente();

		rendDaSelecao = new RendDaSelecao(this, controladorDaEntidade, controladorDoAmbiente);
		rendDoJogo = new RendDoJogo(this, controladorDaEntidade, controladorDoAmbiente);
	}

	/**
	 * Gera ambiente
	 */
	private void gerarAmbiente() {
		int entidades[] = new int[7];
		for (int i = 0; i < 7; i++) {
			entidades[i] = controladorDaEntidade.criarEntidade();
		}
		Especie[] especies = controladorDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < 7; i++) {
			controladorDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
		}
	}
}
