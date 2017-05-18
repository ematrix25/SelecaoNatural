package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.visao.Tela;

/**
 * Cria o Painel do Menu
 * 
 * @author Emanuel
 */
public class PainelDoMenu extends Painel {

	private static final long serialVersionUID = 1L;

	/**
	 * Inicializa o painel do menu
	 *
	 * @param tela
	 */
	public PainelDoMenu(Tela tela) {
		super(tela, "Menu");
		try {
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicia a thread da painel do menu
	 */
	public synchronized void start() {
		super.start(1);
	}

	/**
	 * Executa a tread do painel do menu
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		requestFocus();
		while (rodando) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
			repaint();
		}
	}

	/**
	 * Renderiza o painel do menu
	 * 
	 * @see sistema.visao.painel.Painel#renderizar(java.awt.Graphics)
	 */
	public void renderizar(Graphics graficos) {
		renderizarBotao(graficos, "Sobre", 50);
		renderizarBotao(graficos, "Opcoes", 100);
		renderizarBotao(graficos, "Continuar", 150);
		renderizarBotao(graficos, "Jogar", 200);
	}

	/**
	 * Renderiza um botao com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioY
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioY) {
		int x = this.getWidth() - 100, y = this.getHeight() - desvioY;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, 90, 30);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y)) acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Realiza a ação do botao quando clicado
	 * 
	 * @see sistema.visao.painel.Painel#acaoDoBotao(char)
	 */
	protected void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 'J':
			tela.remove(tela.painelDoMenu);
			((PainelDoMenu) tela.painelDoMenu).stop();
			tela.add(tela.painelDoJogo);
			((PainelDoJogo) tela.painelDoJogo).start();
			break;
		case 'C':
			// TODO Criar uma acao para o botao continuar
			break;
		case 'O':
			// TODO Criar uma acao para o botao opcoes
			break;
		case 'S':
			// TODO Criar uma acao para o botão sobre
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}
}
