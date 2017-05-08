package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Cria o Painel do Menu
 * 
 * @author Emanuel
 */
public class PainelDoMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage imagem = null;

	/**
	 * Adiciona uma imagem ao Menu
	 */
	public PainelDoMenu() {
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modela a imagem no menu
	 */
	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
		renderizarBotao(graficos, "Sobre", 100, 50);
		renderizarBotao(graficos, "Opcoes", 100, 100);
		renderizarBotao(graficos, "Continuar", 100, 150);
		renderizarBotao(graficos, "Jogar", 100, 200);
		graficos.dispose();
	}

	/**
	 * Renderiza um botao com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param texto
	 * @param x
	 * @param y
	 */
	private void renderizarBotao(Graphics graficos, String texto, int x, int y) {
		graficos.setColor(Color.white);
		graficos.fillRoundRect(this.getWidth() - x, this.getHeight() - y, 90, 30, 5, 10);
		graficos.setColor(Color.black);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, this.getWidth() - (x - 10), this.getHeight() - (y - 17));
	}
}
