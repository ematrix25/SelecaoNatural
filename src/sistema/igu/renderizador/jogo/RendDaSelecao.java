package sistema.igu.renderizador.jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ContDaEntidade;
import sistema.controlador.ContDoAmbiente;
import sistema.controlador.ContDoAmbiente.Ambiente;
import sistema.igu.Painel;
import sistema.igu.renderizador.Renderizador;

/**
 * Classe para renderizar a tela de sele��o das esp�cies
 * 
 * @author Emanuel
 */
public class RendDaSelecao extends Renderizador {
	private ContDaEntidade contDaEntidade;
	private ContDoAmbiente contDoAmbiente;
	private int entidades[];

	private int selecao = -1;

	/**
	 * Cria o objeto de renderizador da sele��o
	 * 
	 * @param painel
	 * @param contDaEntidade
	 * @param contDoAmbiente
	 */
	public RendDaSelecao(Painel painel, ContDaEntidade contDaEntidade, ContDoAmbiente contDoAmbiente) {
		super(painel);
		this.contDaEntidade = contDaEntidade;
		this.contDoAmbiente = contDoAmbiente;
	}

	/**
	 * Obt�m o resultado da selecao
	 * 
	 * @return int
	 */
	public int obterSelecao() {
		return selecao * 2;
	}

	/**
	 * Configura as entidades
	 * 
	 * @param entidades
	 */
	public void configurarEntidades(int entidades[]) {
		this.entidades = entidades;
	}

	/**
	 * Renderiza a tela de sele��o
	 *
	 * @see sistema.igu.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		carregarGraficos("/imagens/selecao.jpg");

		Ambiente ambiente = contDoAmbiente.ambiente;
		String dados[] = { "Temperatura M�x. = " + ambiente.obterTempMax(),
				"Temperatura M�nima = " + ambiente.obterTempMin() };
		renderizarDados("Ambiente", dados, 50);

		String opcoes[] = new String[3];
		Integer especime;
		Especie especie;
		for (int i = 0; i < 3; i++) {
			especime = contDoAmbiente.obterEspecimesPorEspecie(ambiente.obterEspecieID(entidades[i])).get(0);
			especie = contDaEntidade.obterComponente(especime, Especime.class).especie;
			opcoes[i] = "Nome = " + especie.nome + ":Tipo = " + especie.tipo + ":Temperatura M�x. = "
					+ especie.tempMaxSup + ":Temperatura M�nima = " + especie.tempMinSup;
		}
		int selecao = renderizarSelecao("Selecione uma esp�cie: ", opcoes, 100);
		if (selecao != -1) this.selecao = selecao;

		int desvio = 20;

		renderizarBotao("Selecionar", desvio);

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderiza os dados da sele��o
	 * 
	 * @param texto
	 * @param dados
	 * @param desvioY
	 */
	private void renderizarDados(String texto, String[] dados, int desvioY) {
		int x = 20, y = desvioY;

		// Texto dos dados
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza os dados
		renderizarDado(dados[0], x + 0, y + 20);
		renderizarDado(dados[1], x + 175, y + 20);
	}

	/**
	 * Renderiza um dado em String
	 * 
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 */
	private void renderizarDado(String texto, int desvioX, int desvioY) {
		int x = desvioX, y = desvioY;

		// Texto da op��o
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x, y);
	}

	/**
	 * Renderiza a sele��o com suas op��es
	 *
	 * @see sistema.igu.renderizador.Renderizador#renderizarSelecao(java.lang.String,
	 *      java.lang.String[], int)
	 */
	protected int renderizarSelecao(String texto, String[] opcoes, int desvioY) {
		int x = 20, y = desvioY;
		int selecao = -1;

		// Texto da Pergunta
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Op��es de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(opcoes[i], x, y + (50 * i + 20), this.selecao == i + 1)) selecao = i + 1;
		}
		return selecao;
	}

	/**
	 * Renderiza uma op��o com o texto em x e y
	 *
	 * @see sistema.igu.renderizador.Renderizador#renderizarOpcao(java.lang.String,
	 *      int, int, boolean)
	 */
	protected boolean renderizarOpcao(String texto, int desvioX, int desvioY, boolean selecionado) {
		int x = desvioX, y = desvioY;
		int tamanho = 8;

		// Botao circular da op��o
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da op��o
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		String dados[] = texto.split(":");
		for (int i = 0; i < (dados.length / 2); i++) {
			graficos.drawString(dados[i], x + 15 + 160 * i, y + tamanho);
			graficos.drawString(dados[i + 2], x + 15 + 160 * i, y + 20 + tamanho);
		}

		// Marca��o de sele��o da op��o
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza um bot�o com o texto em x e y saindo do canto inferior direito
	 * 
	 * @see sistema.igu.renderizador.Renderizador#renderizarBotao(java.lang.String,
	 *      int)
	 */
	protected void renderizarBotao(String texto, int desvioY) {
		int largura = 90, altura = 30;
		int x = painel.getWidth() - (largura + 10), y = painel.getHeight() - (altura + desvioY);

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (painel.mouseEstaNoBotao(x, y, largura, altura) && selecao != -1) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (painel.mouseEstaNoBotao(x, y, largura, altura) && selecao != -1) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (painel.mouseClicouNoBotao(x, y, largura, altura) && selecao != -1) painel.acaoDoBotao(texto.charAt(0));
	}
}
