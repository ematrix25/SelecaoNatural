package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.controlador.ControladorDoAmbiente.Ambiente;
import sistema.interface_grafica.Painel;

/**
 * Classe para renderizar a tela de seleção das espécies
 * 
 * @author Emanuel
 */
public class RendDaSelecao extends Renderizador {
	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;

	private int selecao = -1;

	/**
	 * Cria o objeto de renderizador da seleção
	 * 
	 * @param painel
	 * @param contDaEntidade
	 * @param contDoAmbiente
	 */
	public RendDaSelecao(Painel painel, ControladorDaEntidade contDaEntidade, ControladorDoAmbiente contDoAmbiente) {
		super(painel);
		controladorDaEntidade = contDaEntidade;
		controladorDoAmbiente = contDoAmbiente;

	}

	/**
	 * Obtem o resultado da selecao
	 * 
	 * @return int
	 */
	public int obtemSelecao() {
		return selecao;
	}

	/**
	 * Renderiza a tela de seleção
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizar()
	 */
	public BufferedImage renderizar() {
		// TODO Criar alguma imagem diferente
		carregarImagem("/imagens/menu.jpg");
		Graphics graficos = imagem.getGraphics();

		Ambiente ambiente = controladorDoAmbiente.obterAmbiente();
		String dados[] = { "TempMax = " + ambiente.obterTempMax(), "TempMin = " + ambiente.obterTempMin() };
		renderizarDados(graficos, "Ambiente", dados, 50);

		String opcoes[] = new String[3];
		Integer especime;
		Especie especie;
		for (int i = 1; i <= 3; i++) {
			especime = controladorDoAmbiente.obterEspecie(ambiente.obterEspecieID(i)).get(0);
			especie = controladorDaEntidade.obterComponente(especime, Especime.class).especie;
			opcoes[i - 1] = "Nome = " + especie.nome + ":Tipo = " + especie.tipo + ":TempMax = " + especie.tempMaxSup
					+ ":TempMin = " + especie.tempMinSup;
		}
		int selecao = renderizarSelecao(graficos, "Selecione uma espécie: ", opcoes, 100);
		if (selecao != -1) this.selecao = selecao;

		int desvio = 20;

		renderizarBotao(graficos, "Selecionar", desvio);

		graficos.dispose();
		return imagem;
	}

	/**
	 * Renderiza os dados da seleção
	 * 
	 * @param graficos
	 * @param texto
	 * @param dados
	 * @param desvioY
	 */
	private void renderizarDados(Graphics graficos, String texto, String[] dados, int desvioY) {
		int x = 20, y = desvioY;

		// Texto dos dados
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza os dados
		renderizarDado(graficos, dados[0], x + 0, y + 20);
		renderizarDado(graficos, dados[1], x + 150, y + 20);
	}

	/**
	 * Renderiza um dado em String
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 */
	private void renderizarDado(Graphics graficos, String texto, int desvioX, int desvioY) {
		int x = desvioX, y = desvioY;

		// Texto da opção
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x, y);
	}

	/**
	 * Renderiza a seleção com suas opções
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarSelecao(java.awt.Graphics,
	 *      java.lang.String, java.lang.String[], int)
	 */
	protected int renderizarSelecao(Graphics graficos, String texto, String[] opcoes, int desvioY) {
		int x = 20, y = desvioY;
		int selecao = -1;

		// Texto da Pergunta
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (renderizarOpcao(graficos, opcoes[i], x, y + (50 * i + 20), this.selecao == i + 1)) selecao = i + 1;
		}
		return selecao;
	}

	/**
	 * Renderiza uma opção com o texto em x e y
	 *
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarOpcao(java.awt.Graphics,
	 *      java.lang.String, int, int, boolean)
	 */
	protected boolean renderizarOpcao(Graphics graficos, String texto, int desvioX, int desvioY, boolean selecionado) {
		int x = desvioX, y = desvioY;
		int tamanho = 8;

		// Botao circular da opção
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da opção
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		String dados[] = texto.split(":");
		for (int i = 0; i < (dados.length / 2); i++) {
			graficos.drawString(dados[i], x + 15 + 160 * i, y + tamanho);
			graficos.drawString(dados[i + 2], x + 15 + 160 * i, y + 20 + tamanho);
		}

		// Marcação de seleção da opção
		if (painel.mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(graficos, x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(graficos, x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza um botão com o texto em x e y saindo do canto inferior direito
	 * 
	 * @see sistema.interface_grafica.renderizador.Renderizador#renderizarBotao(java.awt.Graphics,
	 *      java.lang.String, int)
	 */
	protected void renderizarBotao(Graphics graficos, String texto, int desvioY) {
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
