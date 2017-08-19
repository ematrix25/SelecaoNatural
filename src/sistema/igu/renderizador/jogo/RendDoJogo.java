package sistema.igu.renderizador.jogo;

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
import sistema.igu.Painel;
import sistema.igu.renderizador.Renderizador;
import sistema.igu.renderizador.jogo.base.Tela;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.Opcoes;
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
	 * @see sistema.igu.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		carregarGraficos();

		rendInfo();
		rendTelaDoJogo();

		// Ações conforme as teclas são pressionadas
		if (Teclado.sair) painel.acaoDoBotao('s');

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderizar a janela do informações
	 */
	private void rendInfo() {
		final int MIL = (int) Math.pow(10, 3);
		graficos.setColor(Color.black);
		graficos.fillRect(0, 0, painel.getWidth(), 30);

		rendRotulo(Color.lightGray, "Massa Celular: " + painel.massaCelular + "%", painel.getWidth() - 10);
		if (painel.pontuacao < MIL)
			rendRotulo(Color.gray, "Pontuacao: " + painel.pontuacao, (painel.getWidth() / 2) + 70);
		else rendRotulo(Color.gray, "Pontuacao: " + painel.pontuacao / MIL + "K", (painel.getWidth() / 2) + 70);
		if (painel.qtdCelulas < MIL) rendRotulo(Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas, 150);
		else rendRotulo(Color.darkGray, "Qtd Celulas: " + painel.qtdCelulas / MIL + "K", 150);
	}

	/**
	 * Renderiza um rótulo com o texto em x e y
	 * 
	 * @param cor
	 * @param texto
	 * @param desvioX
	 */
	private void rendRotulo(Color cor, String texto, int desvioX) {
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
	private void rendTelaDoJogo() {
		BufferedImage imagem = new BufferedImage(tela.largura, tela.altura, BufferedImage.TYPE_INT_RGB);
		int pixeis[] = ((DataBufferInt) imagem.getRaster().getDataBuffer()).getData();
		String texto;

		// Mostra que o jogo terminou
		if (!painel.ehContinuavel) {
			graficos.setFont(new Font("Verdana", 0, 32));
			graficos.setColor(Color.red);
			texto = "Extinto! Você perdeu o Jogo";
			graficos.drawString(texto, painel.getWidth() / 6, painel.getHeight() / 2);
			return;
		}

		tela.limpar();
		rendMapa();
		rendEntidades();

		for (int i = 0; i < tela.pixeis.length; i++) {
			pixeis[i] = tela.pixeis[i];
		}

		// Desenha a imagem da tela
		graficos.drawImage(imagem, 0, 30, painel.getWidth(), painel.getHeight(), null);

		// Mostra posição do mouse e do jogador
		if (Opcoes.modoDesenvolvimento) {
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
	}

	/**
	 * Renderiza o mapa em relação a posição do jogador
	 */
	private void rendMapa() {
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
	private void rendEntidades() {
		Posicao posicao;
		Velocidade velocidade;
		Sprites sprites;
		for (int entidade : contDaEntidade.obterTodasEntidadesComOComponente(Especime.class)) {
			posicao = contDaEntidade.obterComponente(entidade, Posicao.class);
			velocidade = contDaEntidade.obterComponente(entidade, Velocidade.class);
			sprites = contDaEntidade.obterComponente(entidade, Sprites.class);
			tela.rendEspecime(posicao.x, posicao.y, sprites.obterSprite(velocidade.direcao, velocidade.valor),
					sprites.obterCor());
		}
	}
}
