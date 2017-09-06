package sistema.igu;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.Random;

import componente.Componente;
import componente.Componente.EstadoDaIA;
import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import componente.Componente.Vetor2i;
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
import sistema.igu.renderizador.jogo.base.mapa.Bloco;
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

	public HashMap<Integer, Posicao> posicoesDasEnt;

	public boolean ehContinuavel = false;
	public int massaCelular, qtdCelulas, pontuacao;

	public int contDeSegundos = 0, temperatura = 4;
	public static int tempo;

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

		posicoesDasEnt = new HashMap<Integer, Posicao>();

		thread.start();
	}

	/**
	 * Executa a thread do painel
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long nanoTempoAgora = System.nanoTime(), nanoTempoAntes;
		long miliTempoAgora = System.currentTimeMillis(), miliTempoAntes;
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
				nanoTempoAntes = nanoTempoAgora;
				nanoTempoAgora = System.nanoTime();
				deltaTempo += (nanoTempoAgora - nanoTempoAntes) / (NANOSEGUNDOS / 60.0);
				while (deltaTempo >= 1 && ehContinuavel) {
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
				if (Opcoes.modoDesenvolvimento)
					janela.setTitle(janela.TITULO + " | " + atualizacoes + " aps com " + quadros + " qps");
				atualizacoes = 0;
				quadros = 0;

				// Conta os segundos para abrir o painel do questionarios
				if (telaAtiva == 'S' || telaAtiva == 'J') {
					if (!ehContinuavel) {
						try {
							Thread.sleep(6000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						telaAtiva = 'M';
					}
					contDeSegundos++;
					if (contDeSegundos > 600) {
						janela.redimensionar(1.5f);
						controladorDoQuest = new ContDoQuestionario();
						rendDoQuest = new RendDoQuest(this, controladorDoQuest);
						telaAtiva = 'Q';
					}
				}
			}

			// FIXME Testar avanço para o próximo ambienteç
			// Avança para outro ambiente após tempo ou entidades criadas
			miliTempoAntes = miliTempoAgora;
			miliTempoAgora = System.currentTimeMillis();
			if (miliTempoAgora - miliTempoAntes > 1) tempo++;
			if (telaAtiva == 'J') {
				if (tempo > 20000 || contDaEntidade.entidades.size() > 20) {
					Bloco.associarTemperatura(--temperatura);
					contDoAmbiente.atualizarAmbiente(10);
					repovoarAmbiente();
					contDaEntidade.removerEntidades();
					telaAtiva = 'S';
					pontuacao += 1000;
				}
			}
		}
	}

	/**
	 * Atualiza todos os dados do jogo
	 */
	private void atualizar() {
		int jogador;
		Teclado.atualizar();
		Mouse.atualizar(getWidth(), getHeight());
		jogador = contDoJogador.obterID();
		if (contDoAmbiente.obterEspecie(jogador) != null) {
			massaCelular = contDaEntidade.obterComponente(jogador, Especime.class).massa;
			qtdCelulas = contDoAmbiente.obterEspecimesPorEspecime(jogador).size();
			pontuacao = contDoJogador.obterPontuacao();
		}
		if (tempo % 1000 == 0) {
			contDoMapa.atualizarBlocos();
			contDoAmbiente.atualizarTemp();
		}
		atualizarEntidades();

		// Cria as entidades marcadas no contDaEntidade
		criarEntidades();
		// Remove todas as entidades marcadas na movimentação
		contDaEntidade.removerEntidades();
	}

	/**
	 * Atualiza as entidades no mapa
	 */
	private void atualizarEntidades() {
		Entidade entidade;
		int velocidadeMax;
		int taxa = 7, taxaAux, temp;
		for (int id : contDaEntidade.obterTodasEntidadesComOComponente(Especime.class)) {
			contAuxDaEnt.configurarEntidade(id, contDaEntidade.obterComponentes(id));
			entidade = contAuxDaEnt.obterEntidade();
			velocidadeMax = contDoMapa.obterVelocidadeMax(entidade);
			if (id == contDoJogador.obterID()) {
				contDoMapa.moverEntidade(contDoJogador.obterMovimentacao(velocidadeMax), contDoJogador.obterDirecao(),
						entidade);
			} else {
				taxaAux = (entidade.posicao.proxPos != null) ? taxa : taxa + new Random().nextInt(59);
				if (tempo % taxaAux == 0) {
					if (!(posicoesDasEnt.size() < contDaEntidade.entidades.size())) contDaIA.configurarIA(entidade);
					contDoMapa.moverEntidade(contDaIA.obterMovimentacao(velocidadeMax), contDaIA.obterDirecao(),
							entidade);
				}
			}
			if (entidade.especime.massa == 100) {
				// Reproduz as bacterias
				System.out.println("ID " + id + " reproduziu");
				marcarEntidade(id, false);
				entidade.especime.massa = 50;
				if (id == contDoJogador.obterID()) contDoJogador.incrementarPontuacao(10);
			} else {
				// Consome a massa da entidade conforme o tempo passa
				if (tempo % 1000 == 0) {
					entidade.especime.massa--;
					temp = contDoAmbiente.ambiente.obterTemp();
					if (temp < entidade.especime.especie.tempMinSup || temp > entidade.especime.especie.tempMaxSup)
						entidade.especime.massa -= 4;
				}
				if (entidade.especime.massa == 0) {
					System.out.println("ID " + id + " morreu de fome ou não suportou a temperatura");
					marcarEntidade(id, true);
				}
			}
		}
	}

	/**
	 * Cria as entidades que foram guardadas no contDaEntidade
	 */
	private void criarEntidades() {
		Entidade entidade;
		for (int id : contDaEntidade.idsParaClonar) {
			contAuxDaEnt.configurarEntidade(id, contDaEntidade.obterComponentes(id));
			entidade = contAuxDaEnt.obterEntidade();
			criarEntidade(entidade);
		}
		contDaEntidade.idsParaClonar.clear();
	}

	/**
	 * Cria uma nova entidade dada uma entidade base
	 *
	 * @param entidade
	 */
	private void criarEntidade(Entidade entidade) {
		int id, x, y, sinal;
		Vetor2i vetor, vetorAux = null;
		Posicao posicao;
		Sprites sprites;
		id = contDaEntidade.criarEntidade();
		contDaEntidade.adicionarComponente(id, new Especime(entidade.especime.especie));
		contDoAmbiente.atualizarEspecie(id, true, entidade.especime.especie.obterCodigo());
		// Escolhe uma posição válida ao redor da entidade
		vetor = entidade.posicao.obterVetor();
		for (int i = 0; i < 2; i++) {
			sinal = (int) Math.pow((-1), (i + 1));
			for (int j = 0; j < 2; j++) {
				x = vetor.x + (j % 2) * sinal;
				y = vetor.y + (1 - (j % 2)) * sinal;
				vetorAux = obterVetorValido(x, y);
				if (vetorAux != null) break;
			}
			if (vetorAux != null) break;
		}
		posicao = new Posicao(vetorAux);
		posicoesDasEnt.put(entidade.id, posicao);
		contDaEntidade.adicionarComponente(id, posicao);
		contDaEntidade.adicionarComponente(id, new Velocidade());
		sprites = new Sprites(contDaEntidade.obterComponente(entidade.id, Sprites.class));
		contDaEntidade.adicionarComponente(id, sprites);
		contDaEntidade.adicionarComponente(id, new EstadoDaIA());
	}

	/**
	 * Obtém um vetor válido
	 * 
	 * @param x
	 * @param y
	 * @return Vetor2i
	 */
	private Vetor2i obterVetorValido(int x, int y) {
		Vetor2i vetorAux = null;
		if (!(x < 0 || x > mapa.largura - 1) && !(y < 0 || y > mapa.altura - 1))
			if (!mapa.obterBloco(x, y).solido) vetorAux = new Vetor2i(x, y);
		return vetorAux;
	}

	/**
	 * Marca a entidade para adição ou remoção
	 * 
	 * @param id
	 * @param remover
	 * @return boolean
	 */
	private boolean marcarEntidade(int id, boolean remover) {
		int especie = 0;
		if (remover) {
			if (contDoJogador.obterID() == id) {
				if (qtdCelulas > 1) {
					// Troca para outro espécime da espécie do jogador
					for (int idAux : contDoAmbiente.obterEspecimesPorEspecime(id)) {
						if (idAux != id) {
							posicoesDasEnt.get(idAux).proxPos = null;
							contDaEntidade.obterComponente(idAux, Posicao.class).proxPos = null;
							contDoJogador.configurarID(idAux);
							break;
						}
					}
				} else ehContinuavel = false;
			}
			if (posicoesDasEnt.containsKey(id)) {
				posicoesDasEnt.remove(id);
				if (contDoAmbiente.temEspecie(id)) {
					especie = contDoAmbiente.obterEspecie(id);
					contDoAmbiente.atualizarEspecie(id, false, especie);
				}
				return contDaEntidade.marcarEntidades(id, true);
			}
		} else {
			return contDaEntidade.marcarEntidades(id, false);
		}
		return false;
	}

	/**
	 * Resolve o conflito das entidades e se a entidadeAlvo for removida, true
	 * 
	 * @param entidade
	 * @param entidadeAlvo
	 * @return boolean
	 */
	public boolean resolverConflito(Entidade entidade, int entidadeAlvo) {
		Especime especimeAlvo = contDaEntidade.obterComponente(entidadeAlvo, Especime.class);
		Especime especime = entidade.especime;
		if (especimeAlvo == null) return true;
		if (contDoAmbiente.obterEspecie(entidade.id) == contDoAmbiente.obterEspecie(entidadeAlvo)) return true;
		if (especime.massa >= especimeAlvo.massa) {
			especime.massa = juntarMassa(especime.massa, especimeAlvo.massa);
			return marcarEntidade(entidadeAlvo, true);
		} else {
			especimeAlvo.massa = juntarMassa(especimeAlvo.massa, especime.massa);
			marcarEntidade(entidade.id, true);
		}
		return false;
	}

	/**
	 * Junta as massas dos especimes
	 * 
	 * @param massa
	 * @param acrescimo
	 * @return int
	 */
	private int juntarMassa(int massa, int acrescimo) {
		// 20% não são integrados a massa
		acrescimo = (int) (acrescimo * 0.8);
		if (massa + acrescimo > 100) return 100;
		return massa += acrescimo;
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
			if (telaAtiva == 'M' && ehContinuavel) voltarParaJogo();
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
		tempo = 0;
		janela.redimensionar(1.1f);
		telaAtiva = 'J';
	}

	/**
	 * Inicia o jogo
	 */
	private void iniciarJogo() {
		int entidades[] = { 2, 4, 6 };
		massaCelular = 0;
		qtdCelulas = 0;
		pontuacao = 0;
		contDaEntidade = new ContDaEntidade();
		contDoAmbiente = new ContDoAmbiente();
		gerarAmbiente(7);

		mapa = new Mapa("/mapas/caverna.png", temperatura);
		// mapa = new Mapa(128, 128, temperatura);
		contDoMapa = new ContDoMapa(this, mapa);

		contAuxDaEnt = new ContAuxDaEnt();
		contDoJogador = new ContDoJogador();
		contDaIA = new ContDaIA(this, mapa);

		rendDaSelecao = new RendDaSelecao(this, contDaEntidade, contDoAmbiente);
		rendDaSelecao.configurarEntidades(entidades);
		rendDoJogo = new RendDoJogo(this, contDaEntidade, contDoAmbiente, contDoMapa, contDoJogador);
	}

	/**
	 * Repovoa as entidades do ambiente
	 */
	private void repovoarAmbiente() {
		contDoAmbiente.removerEspecimesExtra(contDoJogador.obterID());
		contDoAmbiente.dificuldade++;
		for (int id : contDoAmbiente.idsParaRemocao)
			marcarEntidade(id, true);
		gerarAmbiente(2);
	}

	/**
	 * Gera as entidades no ambiente
	 * 
	 * @param qtd
	 */
	private void gerarAmbiente(int qtd) {
		int entidades[];
		Especie especies[];
		if (qtd < 3) entidades = new int[3];
		else entidades = new int[qtd];
		for (int i = 0; i < qtd; i++)
			entidades[i] = contDaEntidade.criarEntidade();
		especies = contDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < qtd; i++)
			contDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
		if (qtd < 3) {
			entidades[2] = contDoJogador.obterID();
			rendDaSelecao.configurarEntidades(entidades);
		}
	}

	/**
	 * Coloca as entidades no mapa
	 */
	private void mapearEntidades() {
		System.out.println("Mapeado as entidades:");
		Coordenada coordenada = new Coordenada(mapa);
		Posicao posicao;
		Especie especie;
		for (int entidade : contDaEntidade.obterTodasEntidadesComOComponente(Especime.class)) {
			if (entidade == contDoJogador.obterID())
				coordenada.configurarCoordenada(mapa.largura / 2, mapa.altura / 2 - 1);
			else {
				while (contDaEntidade.obterEntidadeComOComponente(new Posicao(coordenada)) != null)
					coordenada.configurarCoordenada(mapa);
			}
			System.out.print(coordenada + " ");
			posicao = new Posicao(coordenada);
			posicoesDasEnt.put(entidade, posicao);
			contDaEntidade.adicionarComponente(entidade, posicao);
			contDaEntidade.adicionarComponente(entidade, new Velocidade());
			especie = contDaEntidade.obterComponente(entidade, Especime.class).especie;
			if (contDaEntidade.obterComponente(entidade, Sprites.class) == null)
				contDaEntidade.adicionarComponente(entidade, new Sprites(especie.tipo.forma, gerarCor()));
			if (entidade != contDoJogador.obterID()) contDaEntidade.adicionarComponente(entidade, new EstadoDaIA());
		}
		System.out.println();
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

	/**
	 * Verifica se as ids são da mesma especie
	 * 
	 * @param id
	 * @param idAlvo
	 * @return boolean
	 */
	public boolean ehDaMesmaEspecie(int id, int idAlvo) {
		return contDoAmbiente.obterEspecie(id) == contDoAmbiente.obterEspecie(idAlvo);
	}
}
