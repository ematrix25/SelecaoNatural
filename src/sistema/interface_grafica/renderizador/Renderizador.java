package sistema.interface_grafica.renderizador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
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
	protected Image imagem;

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
	 * @return Image
	 */
	public abstract Image renderizar();

	/**
	 * Carrega a imagem do arquivo no endereco dos recursos
	 * 
	 * @param endereco
	 */
	protected void carregarImagem(String endereco) {
		try {
			imagem = ImageIO.read(new Recurso().getEnderecoEmFluxo(endereco));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Renderiza a sele��o com suas op��es
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @param selecao
	 * @return int
	 */
	protected int renderizarSelecao(Graphics graficos, String texto, String[] opcoes, int desvioY, int selecao) {
		return -1;
	}

	/**
	 * Renderiza a sele��o com suas op��es
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @return int
	 */
	protected int renderizarSelecao(Graphics graficos, String texto, String[] opcoes, int desvioY) {
		return -1;
	}

	/**
	 * Renderiza uma op��o com o texto em x e y
	 *
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 * @param selecionado
	 * @return boolean
	 */
	protected boolean renderizarOpcao(Graphics graficos, String texto, int desvioX, int desvioY, boolean selecionado) {
		return false;
	}

	/**
	 * Renderiza a marca��o da op��o em x e y
	 * 
	 * @param graficos
	 * @param x
	 * @param y
	 * @param tamanho
	 */
	protected void renderizarMarcacao(Graphics graficos, int x, int y, int tamanho) {
		graficos.setColor(Color.black);
		graficos.fillOval(x + (tamanho / 2), y + (tamanho / 2), tamanho, tamanho);
	}

	/**
	 * Renderiza um bot�o com texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioY
	 */
	protected void renderizarBotao(Graphics graficos, String texto, int desvio) {
	}
}
