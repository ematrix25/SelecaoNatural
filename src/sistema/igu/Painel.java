package sistema.igu;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import componente.Componente;
import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import componente.Especime;
import componente.Especime.Especie;
import sistema.Jogo.Janela;
import sistema.controlador.ContDaEntidade;
import sistema.controlador.ContDoAmbiente;
import sistema.controlador.ContDoQuestionario;
import sistema.controlador.jogo.ContAuxDaEnt;
import sistema.controlador.jogo.ContAuxDaEnt.Entidade;
import sistema.controlador.jogo.ContDoMapa;
import sistema.controlador.jogo.movimento.ContDaIA;
import sistema.controlador.jogo.movimento.ContDoJogador;
import sistema.igu.renderizador.RendDeOpcoes;
import sistema.igu.renderizador.RendDoMenu;
import sistema.igu.renderizador.RendDoQuest;
import sistema.igu.renderizador.jogo.RendDaSelecao;
import sistema.igu.renderizador.jogo.RendDoJogo;
import sistema.igu.renderizador.jogo.base.mapa.Coordenada;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;
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
	private static final long serialVersionUID = 1L;

	private Janela janela;
	private Image imagem;

	private Thread thread;
	private Teclado teclado;
	private Mouse mouse;

	private Mapa mapa;

	private ContDaEntidade contDaEntidade;
	private ContDoAmbiente contDoAmbiente;

	private ContDoMapa contDoMapa;

	private ContAuxDaEnt contAuxDaEnt;
	private ContDoJogador contDoJogador;
	private ContDaIA contDaIA;

	private ContDoQuestionario controladorDoQuest;

	private RendDoMenu rendDoMenu;
	private RendDeOpcoes rendDeOpcoes;
	private RendDaSelecao rendDaSelecao;
	private RendDoJogo rendDoJogo;
	private RendDoQuest rendDoQuest;

	private char telaAtiva = 'M';
	public boolean ehContinuavel = false;

	public int massaCelular = 0, qtdCelulas = 0, pontuacao = 0;
	public final float MASSA_CELULAR_MAX = 100.0f;

	public int contDeSegundos = 0;
	
	public static int tempo = 0;

	/**
	 * Inicializa o painel
	 *
	 * @param janela
	 */
	public Painel(Janela janela) {
		this.janela = janela;
		janela.redimensionar(1);
		setSize(janela.getWidth(), janela.getHeight());

		thread = new Thread(this, "Jogo");
		teclado = new Teclado();
		mouse = new Mouse(getWidth(), getHeight());

		// Adiciona os escutadores dos periféricos
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
		// Será executado até dar fim de jogo ou abrir o questionário
		while (true) {
			requestFocusInWindow();
			try {
				if (telaAtiva == 'J') Thread.sleep(20);
				else Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Atualiza os dados do jogo
			if (telaAtiva == 'J') {
				tempoAntes = tempoAgora;
				tempoAgora = System.nanoTime();
				deltaTempo += (tempoAgora - tempoAntes) / (NANOSEGUNDOS / 60.0);
				while (deltaTempo >= 1) {
					atualizar();
					atualizacoes++;
					deltaTempo--;
					tempo++;
				}
			}

			// Renderiza um quadro
			renderizar();
			quadros++;

			// Mostra APS e QPS a cada segundo
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				janela.setTitle(janela.TITULO + " | " + atualizacoes + " aps com " + quadros + " qps");
				atualizacoes = 0;
				quadros = 0;

				// Conta os segundos para abrir o painel do questionarios
				if (telaAtiva == 'S' || telaAtiva == 'J') {
					contDeSegundos++;
					if (contDeSegundos > 600) {
						janela.redimensionar(1.5f);
						controladorDoQuest = new ContDoQuestionario();
						rendDoQuest = new RendDoQuest(this, controladorDoQuest);
						telaAtiva = 'Q';
					}
				}
			}
		}
	}

	/**
	 * Atualiza todos os dados do jogo
	 */
	private void atualizar() {
		Teclado.atualizar();
		Mouse.atualizar(getWidth(), getHeight());
		int jogador = contDoJogador.obterID();
		massaCelular = contDaEntidade.obterComponente(jogador, Especime.class).massa;
		qtdCelulas = contDoAmbiente.obterEspecimesPorEspecime(jogador).size();
		pontuacao = contDoJogador.obterPontuacao();

		moverEntidades();
	}

	/**
	 * Move as entidades no mapa
	 */
	private void moverEntidades() {
		// TODO Melhorar os movimentos da inteligência artificial
		Entidade entidade;
		int velocidadeMax;
		for (int id : contDaEntidade.obterTodasEntidadesComOComponente(Especime.class)) {
			contAuxDaEnt.configurarEntidade(id, contDaEntidade.obterComponentes(id));
			entidade = contAuxDaEnt.obterEntidade();
			velocidadeMax = contDoMapa.obterVelocidadeMax(entidade.especime.especie.tipo.movimento);
			if (id == contDoJogador.obterID()) {
				contDoMapa.moverEntidade(contDoJogador.obterMovimentacao(velocidadeMax), contDoJogador.obterDirecao(),
						entidade);
			} else if (tempo % (new Random().nextInt(50) + 30) == 0) {
				contDoMapa.moverEntidade(contDaIA.obterMovimentacao(velocidadeMax), contDaIA.obterDirecao(), entidade);
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
		}
		graficos.drawImage(imagem, 0, 0, getWidth(), getHeight(), null);
		graficos.dispose();
		estrategiaDeBuffer.show();
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
		if (mouseEstaNoBotao(x, y, largura, altura) && Mouse.obterBotao() > -1) {
			Mouse.reconfigurarBotao();
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
		int mouseX = Mouse.obterX(), mouseY = Mouse.obterY();
		if (mouseX >= x && mouseX <= x + largura) if (mouseY >= y && mouseY <= y + altura) return true;
		return false;
	}

	/**
	 * Realiza a ação do botão quando clicado
	 * 
	 * @param inicial
	 */
	public void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 's':
			voltarParaMenu();
			break;
		case 'S':
			if (telaAtiva == 'S') {
				int selecao = rendDaSelecao.obterSelecao();
				contDoJogador.configurarID(selecao);
				mapearEntidades();
				voltarParaJogo();
			} else if (telaAtiva == 'O') {
				Opcoes.carregarConfig(true, rendDeOpcoes.obterConfiguracoes());
				voltarParaMenu();
			} else if (telaAtiva == 'Q') {
				if (!rendDoQuest.temPagina()) {
					ArquivoDoQuest.escrever(rendDoQuest.obterRespostas());
					janela.dispatchEvent(new WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
				} else rendDoQuest.proxPagina();
			}
			break;
		case 'N':
			janela.redimensionar(1.1f);
			telaAtiva = 'S';
			iniciarJogo();
			ehContinuavel = true;
			break;
		case 'C':
			if (telaAtiva == 'M') voltarParaJogo();
			else if (telaAtiva == 'O') voltarParaMenu();
			break;
		case 'O':
			janela.redimensionar(1.1f);
			telaAtiva = 'O';
			break;
		}
	}

	/**
	 * Volta para a tela do menu
	 */
	private void voltarParaMenu() {
		janela.redimensionar(1);
		telaAtiva = 'M';
	}

	/**
	 * Volta para a tela do jogo
	 */
	private void voltarParaJogo() {
		janela.redimensionar(1.1f);
		telaAtiva = 'J';
	}

	/**
	 * Inicia o jogo
	 */
	private void iniciarJogo() {
		contDaEntidade = new ContDaEntidade();
		contDoAmbiente = new ContDoAmbiente();
		gerarAmbiente();

		mapa = new Mapa("/mapas/caverna.png", 0);
		contDoMapa = new ContDoMapa(mapa);

		contAuxDaEnt = new ContAuxDaEnt();
		contDoJogador = new ContDoJogador();
		contDaIA = new ContDaIA();

		rendDaSelecao = new RendDaSelecao(this, contDaEntidade, contDoAmbiente);
		rendDoJogo = new RendDoJogo(this, contDaEntidade, contDoAmbiente, contDoMapa, contAuxDaEnt, contDoJogador,
				contDaIA);
	}

	/**
	 * Gera ambiente
	 */
	private void gerarAmbiente() {
		int entidades[] = new int[7];
		for (int i = 0; i < 7; i++) {
			entidades[i] = contDaEntidade.criarEntidade();
		}
		Especie[] especies = contDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < 7; i++) {
			contDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
		}
	}

	/**
	 * Coloca as entidades no mapa
	 */
	private void mapearEntidades() {
		Coordenada coordenada = new Coordenada(mapa);
		for (int entidade : contDaEntidade.obterTodasEntidadesComOComponente(Especime.class)) {
			if (entidade == contDoJogador.obterID())
				coordenada.configurarCoordenada(mapa.largura / 2, mapa.altura / 2 - 1);
			else {
				while (contDaEntidade.obterEntidadeComOComponente(new Posicao(coordenada)) != null)
					coordenada.configurarCoordenada();
			}
			System.out.println(coordenada);
			contDaEntidade.adicionarComponente(entidade, (Componente) new Posicao(coordenada));
			contDaEntidade.adicionarComponente(entidade, (Componente) new Velocidade());
			Especie especie = contDaEntidade.obterComponente(entidade, Especime.class).especie;
			contDaEntidade.adicionarComponente(entidade, (Componente) new Sprites(especie.tipo.forma, gerarCor()));
		}
	}

	/**
	 * Gera uma cor
	 */
	private int gerarCor() {
		int vermelho = new Random().nextInt(0x3f);
		int verde = new Random().nextInt(0x3f);
		int azul = new Random().nextInt(0x3f);
		return 0xff000000 + vermelho * 10000 + verde * 100 + azul;
	}
}
