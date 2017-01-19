package fronteira.painel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Emanuel
 *
 */
public class PainelDoMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage imagem = null;

	public PainelDoMenu() {
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics graficos) {
		graficos.drawImage(imagem, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
