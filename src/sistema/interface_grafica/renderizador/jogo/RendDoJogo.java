package sistema.interface_grafica.renderizador.jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import componente.Especime;
import sistema.controlador.ContDaEntidade;
import sistema.controlador.ContDoAmbiente;
import sistema.controlador.jogo.ContAuxDaEnt;
import sistema.controlador.jogo.ContDoMapa;
import sistema.controlador.jogo.movimento.ContDaIA;
import sistema.controlador.jogo.movimento.ContDoJogador;
import sistema.interface_grafica.Painel;
import sistema.interface_grafica.renderizador.Renderizador;
import sistema.interface_grafica.renderizador.jogo.base.Tela;
import sistema.interface_grafica.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe para renderizar o jogo
 * 
 * @author Emanuel
 */
public class RendDoJogo extends Renderizador {
	private final int ESCALA = 2;

	private Tela tela;

	private ContDaEntidade contDaEntidade;
	private ContDoAmbiente contDoAmbiente;

	private ContDoMapa contDoMapa;

	private ContAuxDaEnt contAuxDaEnt;
	private ContDoJogador contDoJogador;
	private ContDaIA contDaIA;

	/**
	 * Cria o objeto para renderização do jogo
	 * 
	 * @param painel
	 * @param contDaEntidade
	 * @param contDoAmbiente
	 * @param contDoMapa
	 * @param contAuxDaEnt
	 * @param contDoJogador
	 * @param contDaIA
	 */
	public RendDoJogo(Painel painel, ContDaEntidade contDaEntidade, ContDoAmbiente contDoAmbiente,
			ContDoMapa contDoMapa, ContAuxDaEnt contAuxDaEnt, ContDoJogador contDoJogador, ContDaIA contDaIA) {
		super(painel);
		this.contDaEntidade = contDaEntidade;
		this.contDoAmbiente = contDoAmbiente;
		this.contDoMapa = contDoMapa;
		this.contAuxDaEnt = contAuxDaEnt;
		this.contDoJogador = contDoJogador;
		this.contDaIA = contDaIA;

		tela = new Tela(painel.getWidth() / ESCALA, (painel.getHeight() - 30) / ESCALA);
	}

	/**
	 * Renderiza a tela do jogo
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		carregarGraficos();

		renderizarInfo();
		renderizarTelaDoJogo();

		// Ações conforme as teclas são pressionadas
		if (Teclado.sair) painel.acaoDoBotao('s');

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderizar a janela do informações
	 */
	private void renderizarInfo() {
		final int MIL = (int) Math.pow(10, 3);
		graficos.setColor(Color.black);
		graficos.fillRect(0, 0, painel.getWidth(), 30);

		renderizarRotulo(Color.lightGray, "Massa Celular: " + painel.massaCelular + "%", painel.getWidth() - 10);
		if (painel.pontuacao < MIL)
			renderizarRotulo(Color.gray, "Pontuacao: " + painel.pontuacao, (painel.getWidth() / 2) + 70);
		else renderizarRotulo(Color.gray, "Pontuacao: " + painel.pontuacao / MIL + "K", (painel.getWidth() / 2) + 70);
		if (painel.qtdCelulas < MIL) renderizarRotulo(Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas, 150);
		else renderizarRotulo(Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas / MIL + "K", 150);
	}

	/**
	 * Renderiza um rótulo com o texto em x e y
	 * 
	 * @param cor
	 * @param texto
	 * @param desvioX
	 */
	private void renderizarRotulo(Color cor, String texto, int desvioX) {
		int x = painel.getWidth() - desvioX, y = 5;

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
	 * Renderiza a tela do jogo
	 */
	private void renderizarTelaDoJogo() {
		BufferedImage imagem = new BufferedImage(tela.largura, tela.altura, BufferedImage.TYPE_INT_RGB);
		int pixeis[] = ((DataBufferInt) imagem.getRaster().getDataBuffer()).getData();

		tela.limpar();
		renderizarMapa();
		renderizarEntidades();

		for (int i = 0; i < tela.pixeis.length; i++) {
			pixeis[i] = tela.pixeis[i];
		}

		// Desenha a imagem da tela
		graficos.drawImage(imagem, 0, 30, painel.getWidth(), painel.getHeight(), null);

		// Mostra posição do mouse e do jogador
		graficos.setFont(new Font("Verdana", 0, 12));
		if (Mouse.obterBotao() > -1) {
			graficos.setColor(Color.green);
			graficos.fillRect(Mouse.obterDesvioX(), Mouse.obterDesvioY(), 16, 16);

			graficos.setColor(Color.red);
			graficos.drawString("Button: " + Mouse.obterBotao(), 20, 60);

			graficos.setColor(Color.white);
			graficos.drawString("X: " + Mouse.obterDesvioX() + ", Y: " + Mouse.obterDesvioY(), 20, 100);
			Posicao posicao = contDaEntidade.obterComponente(contDoJogador.obterID(), Posicao.class);
			graficos.drawString("X: " + posicao.x + ", Y: " + posicao.y, 20, 120);
		}
	}

	/**
	 * Renderiza o mapa em relação a posição do jogador
	 */
	private void renderizarMapa() {
		int id = contDoJogador.obterID();
		Posicao posicao = contDaEntidade.obterComponente(id, Posicao.class);
		int rolagemX = posicao.x - tela.largura / 2;
		int rolagemY = posicao.y - tela.altura / 2;
		Mapa mapa = contDoMapa.obterMapa();
		mapa.renderizar(rolagemX, rolagemY, tela);
	}

	/**
	 * Renderiza as sprites de cada entidade conforme a velocidade
	 */
	private void renderizarEntidades() {
		Posicao posicao;
		Velocidade velocidade;
		Sprites sprites;
		int invertido;
		for (int entidade : contDaEntidade.obterTodasEntidadesComOComponente(Especime.class)) {
			posicao = contDaEntidade.obterComponente(entidade, Posicao.class);
			velocidade = contDaEntidade.obterComponente(entidade, Velocidade.class);
			sprites = contDaEntidade.obterComponente(entidade, Sprites.class);
			invertido = 0;
			if (velocidade.direcao == 2) invertido = 2;
			if (velocidade.direcao == 3) invertido = 1;
			if (velocidade.direcao % 2 == 0) tela.renderizarEspecime(posicao.x, posicao.y,
					sprites.obterSpriteY(velocidade.valor), sprites.obterCor(), invertido);
			else tela.renderizarEspecime(posicao.x, posicao.y, sprites.obterSpriteX(velocidade.valor),
					sprites.obterCor(), invertido);
		}
	}
}
