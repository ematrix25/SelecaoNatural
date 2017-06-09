package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.interface_grafica.Painel;
import sistema.utilitario.arquivo.Recurso;

/**
 * Classe base dos renderizadores
 * 
 * @author Emanuel
 */
public abstract class Renderizador {
	protected Painel painel;
	protected BufferedImage imagem;
	protected Graphics graficos;

	/**
	 * Cria o objeto de renderizador
	 * 
	 * @param painel
	 */
	public Renderizador(Painel painel) {
		this.painel = painel;
	}

	/**
	 * Renderiza a tela
	 * 
	 * @return BufferedImage
	 */
	public abstract BufferedImage renderizar();

	/**
	 * Carrega a classe gráfica de uma imagem generica
	 */
	protected void carregarGraficos() {
		imagem = new BufferedImage(painel.getWidth(), painel.getHeight(), BufferedImage.TYPE_INT_RGB);
		graficos = imagem.getGraphics();
	}

	/**
	 * Carrega a classe gráfica do arquivo no endereco dos recursos
	 * 
	 * @param endereco
	 */
	protected void carregarGraficos(String endereco) {
		carregarGraficos();
		BufferedImage imagem;
		try {
			imagem = ImageIO.read(new Recurso().getEnderecoEmFluxo(endereco));
			graficos.drawImage(imagem, 0, 0, painel.getWidth(),
					painel.getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Descarrega a classe gráfica
	 */
	protected void descarregarGraficos() {
		graficos.dispose();
	}

	/**
	 * Renderiza a seleção com suas opções
	 *
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @param selecao
	 * @return int
	 */
	protected int renderizarSelecao(String texto, String[] opcoes, int desvioY, int selecao) {
		return -1;
	}

	/**
	 * Renderiza a seleção com suas opções
	 *
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @return int
	 */
	protected int renderizarSelecao(String texto, String[] opcoes, int desvioY) {
		return -1;
	}

	/**
	 * Renderiza uma opção com o texto em x e y
	 *
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 * @param selecionado
	 * @return boolean
	 */
	protected boolean renderizarOpcao(String texto, int desvioX, int desvioY, boolean selecionado) {
		return false;
	}

	/**
	 * Renderiza a marcação da opção em x e y
	 * 
	 * @param x
	 * @param y
	 * @param tamanho
	 */
	protected void renderizarMarcacao(int x, int y, int tamanho) {
		graficos.setColor(Color.black);
		graficos.fillOval(x + (tamanho / 2), y + (tamanho / 2), tamanho, tamanho);
	}

	/**
	 * Renderiza um botão com texto em x e y saindo do canto inferior direito
	 * 
	 * @param texto
	 * @param desvioY
	 */
	protected void renderizarBotao(String texto, int desvio) {
	}
}
