package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.controlador.ControladorDoJogo;
import sistema.interface_grafica.Painel;
import sistema.interface_grafica.renderizador.base_do_jogo.Tela;
import sistema.interface_grafica.renderizador.base_do_jogo.mapa.Mapa;
import sistema.utilitario.periferico.Mouse;
import sistema.utilitario.periferico.Teclado;

/**
 * Classe para renderizar o jogo
 * 
 * @author Emanuel
 */
@SuppressWarnings("unused")
public class RendDoJogo extends Renderizador {
	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;
	private ControladorDoJogo controladorDoJogo;

	private Tela tela;

	private int x = 100, y = 100;
	private boolean xdir = true, ydir = false;

	/**
	 * Cria o objeto para renderização do jogo
	 * 
	 * @param painel
	 * @param contDaEntidade
	 * @param contDoAmbiente
	 */
	public RendDoJogo(Painel painel, ControladorDaEntidade contDaEntidade, ControladorDoAmbiente contDoAmbiente,
			ControladorDoJogo contDoJogo) {
		super(painel);
		controladorDaEntidade = contDaEntidade;
		controladorDoAmbiente = contDoAmbiente;
		controladorDoJogo = contDoJogo;

		tela = new Tela(painel.getWidth(), painel.getHeight() - 30);
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

		// TODO Implementar a renderização para todas as entidades
		tela.limpar();
		Posicao posicao = controladorDaEntidade.obterComponente(controladorDoJogo.obterJogador(), Posicao.class);
		int rolagemX = posicao.x - tela.largura / 2;
		int rolagemY = posicao.y - tela.altura / 2;
		Mapa mapa = controladorDoJogo.obterMapa();
		mapa.renderizar(rolagemX, rolagemY, tela);
		Velocidade velocidade = controladorDaEntidade.obterComponente(controladorDoJogo.obterJogador(),
				Velocidade.class);
		Sprites sprites = controladorDaEntidade.obterComponente(controladorDoJogo.obterJogador(), Sprites.class);
		int invertido = 0;
		if (velocidade.direcao == 2) invertido = 2;
		if (velocidade.direcao == 3) invertido = 1;
		// TODO Implementar animação de movimento alternando entre sprites
		// movendo e parado
		if (velocidade.direcao % 2 == 0)
			tela.renderizarEspecime(rolagemX, rolagemY, sprites.obterSpriteY(velocidade.movendo), invertido);
		else tela.renderizarEspecime(rolagemX, rolagemY, sprites.obterSpriteX(velocidade.movendo), invertido);

		for (int i = 0; i < tela.pixeis.length; i++) {
			pixeis[i] = tela.pixeis[i];
		}

		// TODO Descobrir porque renderiza o mapa todo ao inves da tela do mapa
		graficos.drawImage(imagem, 0, 30, painel.getWidth(), painel.getHeight(), null);

		// Mostra posição do mouse e do jogador
		graficos.setFont(new Font("Verdana", 0, 12));
		if (Mouse.obterBotao() > -1) {
			graficos.setColor(Color.green);
			graficos.fillRect(Mouse.obterX(), Mouse.obterY(), 16, 16);

			graficos.setColor(Color.red);
			graficos.drawString("Button: " + Mouse.obterBotao(), 20, 60);

			graficos.setColor(Color.white);
			graficos.drawString("X: " + Mouse.obterX() + ", Y: " + Mouse.obterY(), 20, 100);
			graficos.drawString("X: " + posicao.x + ", Y: " + posicao.y, 20, 120);
		}
	}

	/**
	 * Faz a movimentação das posições de x e y do texto
	 */
	private void moveText() {
		if (x == painel.getWidth() - 183 || x == 0) xdir = !xdir;
		if (y == painel.getHeight() - 5 || y == 49) ydir = !ydir;
		if (xdir) x++;
		else x--;
		if (ydir) y++;
		else y--;
	}
}
