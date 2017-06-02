package sistema.interface_grafica.painel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import componente.Componente;
import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.interface_grafica.Tela;
import sistema.interface_grafica.renderizador.RendDaSelecao;
import sistema.interface_grafica.renderizador.RendDoJogo;
import sistema.interface_grafica.renderizador.RendDoMenu;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe do Painel com thread
 * 
 * @author Emanuel
 */
public class PainelDeTeste extends JPanel implements Runnable {
	// TODO Integrar todos os paineis nesse painel
	private static final long serialVersionUID = 1L;

	private Tela tela;
	private Thread thread;
	private Teclado teclado;
	private Mouse mouse;
	private Image imagem;

	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;

	private RendDoMenu rendDoMenu;
	private RendDaSelecao rendDaSelecao;
	private RendDoJogo rendDoJogo;

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
	public PainelDeTeste(Tela tela) {
		this.tela = tela;
		thread = new Thread(this, "Jogo");

		teclado = new Teclado();
		mouse = new Mouse(this.getWidth(), this.getHeight());
		setSize(tela.getWidth(), tela.getHeight());

		// Adiciona os escutadores dos periféricos
		addKeyListener(teclado);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		rendDoMenu = new RendDoMenu(this);
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
				Thread.sleep(20);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
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
			switch (telaAtiva) {
			case 'M':
				imagem = rendDoMenu.renderizar();
				break;
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

			// Mostra APS e QPS a cada segundo
			if (System.currentTimeMillis() - temporizador > 1000) {
				temporizador += 1000;
				tela.setTitle(tela.TITULO + " | " + atualizacoes + " aps com " + quadros + " qps");
				atualizacoes = 0;
				quadros = 0;

				// Conta os segundos para abrir o painel do questionarios
				if (telaAtiva == 'S' || telaAtiva == 'J') {
					cont++;
					if (cont > 60) telaAtiva = 'Q';
				}
			}
			repaint();
		}
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
	 * @param inicial
	 */
	public void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 's':
			if (telaAtiva == 'J') telaAtiva = 'M';
			break;
		case 'S':
			int selecao = rendDaSelecao.getSelecao();
			Integer especime = controladorDoAmbiente
					.obterEspecie(controladorDoAmbiente.obterAmbiente().obterEspecieID(selecao)).get(0);
			Especie especie = controladorDaEntidade.obterComponente(especime, Especime.class).especie;
			System.out.println("Seleção do especime " + especime + " da especie " + especie);
			telaAtiva = 'J';
			break;
		case 'N':
			telaAtiva = 'S';
			iniciarJogo();
			ehContinuavel = true;
			break;
		case 'C':
			telaAtiva = 'J';
			break;
		case 'O':
			telaAtiva = 'O';
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
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
