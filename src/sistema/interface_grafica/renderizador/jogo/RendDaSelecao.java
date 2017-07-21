package sistema.interface_grafica.renderizador.jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ContDaEntidade;
import sistema.controlador.ContDoAmbiente;
import sistema.controlador.ContDoAmbiente.Ambiente;
import sistema.interface_grafica.Painel;
import sistema.interface_grafica.renderizador.Renderizador;

/**
 * Classe para renderizar a tela de seleção das espécies
 * 
 * @author Emanuel
 */
public class RendDaSelecao extends Renderizador {
	private ContDaEntidade contDaEntidade;
	private ContDoAmbiente contDoAmbiente;

	private int selecao = -1;

	/**
	 * Cria o objeto de renderizador da seleção
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
	 * Obtem o resultado da selecao
	 * 
	 * @return int
	 */
	public int obterSelecao() {
		return selecao * 2;
	}

	/**
	 * Renderiza a tela de seleção
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		carregarGraficos("/imagens/selecao.jpg");

		Ambiente ambiente = contDoAmbiente.obterAmbiente();
		String dados[] = { "Temperatura Máx. = " + ambiente.obterTempMax(),
				"Temperatura Mínima = " + ambiente.obterTempMin() };
		renderizarDados("Ambiente", dados, 50);

		String opcoes[] = new String[3];
		Integer especime;
		Especie especie;
		for (int i = 1; i <= 3; i++) {
			especime = contDoAmbiente.obterEspecimesPorEspecie(ambiente.obterEspecieID(i * 2)).get(0);
			especie = contDaEntidade.obterComponente(especime, Especime.class).especie;
			opcoes[i - 1] = "Nome = " + especie.nome + ":Tipo = " + especie.tipo + ":Temperatura Máx. = "
					+ especie.tempMaxSup + ":Temperatura Mínima = " + especie.tempMinSup;
		}
		int selecao = renderizarSelecao("Selecione uma espécie: ", opcoes, 100);
		if (selecao != -1) this.selecao = selecao;

		int desvio = 20;

		renderizarBotao("Selecionar", desvio);

		descarregarGraficos();
		return imagem;
	}

	/**
	 * Renderiza os dados da seleção
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

		// Texto da opção
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x, y);
	}

	/**
	 * Renderiza a seleção com suas opções
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarSelecao(java.lang.String,
	 *      java.lang.String[], int)
	 */
	protected int renderizarSelecao(String texto, String[] opcoes, int desvioY) {
		int x = 20, y = desvioY;
		int selecao = -1;

		// Texto da Pergunta
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(opcoes[i], x, y + (50 * i + 20), this.selecao == i + 1)) selecao = i + 1;
		}
		return selecao;
	}

	/**
	 * Renderiza uma opção com o texto em x e y
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarOpcao(java.lang.String,
	 *      int, int, boolean)
	 */
	protected boolean renderizarOpcao(String texto, int desvioX, int desvioY, boolean selecionado) {
		int x = desvioX, y = desvioY;
		int tamanho = 8;

		// Botao circular da opção
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da opção
		graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		String dados[] = texto.split(":");
		for (int i = 0; i < (dados.length / 2); i++) {
			graficos.drawString(dados[i], x + 15 + 160 * i, y + tamanho);
			graficos.drawString(dados[i + 2], x + 15 + 160 * i, y + 20 + tamanho);
		}

		// Marcação de seleção da opção
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza um botão com o texto em x e y saindo do canto inferior direito
	 * 
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarBotao(java.lang.String,
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
