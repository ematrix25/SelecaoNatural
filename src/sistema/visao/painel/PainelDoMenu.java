package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.arquivo.Recurso;
import sistema.visao.Tela;

/**
 * Cria o Painel do Menu
 * 
 * @author Emanuel
 */
public class PainelDoMenu extends Painel {

	private static final long serialVersionUID = 1L;
	private boolean sobreAtivado = false;

	/**
	 * Inicializa o painel do menu
	 *
	 * @param tela
	 */
	public PainelDoMenu(Tela tela) {
		super(tela, "Menu");
		Recurso recurso = new Recurso();
		try {
			imagem = ImageIO.read(recurso.getEnderecoEmFluxo("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicia a thread da painel do menu
	 */
	public synchronized void iniciar() {
		super.iniciar(1);
	}

	/**
	 * Retoma a thread do painel do jogo
	 */
	public synchronized void retomar() {
		super.retomar(1);
	}

	/**
	 * Executa a thread do painel do menu
	 * 
	 * @see sistema.visao.painel.Painel#run()
	 */
	@Override
	public void run() {
		while (executando) {
			requestFocusInWindow();
			try {
				Thread.sleep(20);
				synchronized (thread) {
					while (pausado) {
						thread.wait();
					}
				}
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			repaint();
		}
	}

	/**
	 * Pinta o painel que é usado no repaint
	 *
	 * @see sistema.visao.painel.Painel#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		super.paintComponent(graficos);
		renderizar(graficos);
	}

	/**
	 * Renderiza o painel do menu
	 *
	 * @param graficos
	 */
	public void renderizar(Graphics graficos) {
		// Renderizar texto do botão sobre
		if (sobreAtivado) {
			graficos.setColor(Color.white);
			graficos.setFont(new Font("Verdana", 0, 12));
			graficos.drawString("Jogo desenvolvido por Emanuel Torres", 10, this.getHeight() - 20);
		}

		// Renderizar Botões
		renderizarBotao(graficos, "Sobre", 50);
		renderizarBotao(graficos, "Opções", 100);
		renderizarBotao(graficos, "Continuar", 150);
		renderizarBotao(graficos, "Jogar", 200);
	}

	/**
	 * Renderiza um botão com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioY
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioY) {
		int x = this.getWidth() - 100, y = this.getHeight() - desvioY;
		int largura = 90, altura = 30;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y, largura, altura) && continuavel(texto.charAt(0))) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y, largura, altura) && continuavel(texto.charAt(0))) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y, largura, altura)) acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Realiza a ação do botão quando clicado
	 * 
	 * @see sistema.visao.painel.Painel#acaoDoBotao(char)
	 */
	@Override
	protected void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 'J':
			tela.remove(tela.painelDoMenu);
			tela.painelDoMenu.pausar();
			if (tela.painelDoJogo.pausado) {
				tela.painelDoJogo = new PainelDoJogo(tela);
			}
			tela.add(tela.painelDoJogo);			
			tela.painelDoJogo.iniciar();
			break;
		case 'C':
			if (tela.painelDoJogo.pausado) {
				tela.remove(tela.painelDoMenu);
				tela.painelDoMenu.pausar();
				tela.add(tela.painelDoJogo);
				tela.painelDoJogo.retomar();
			}
			break;
		case 'O':
			tela.remove(tela.painelDoMenu);
			tela.painelDoMenu.pausar();
			tela.add(tela.painelDeOpcoes);
			if (tela.painelDeOpcoes.pausado) tela.painelDeOpcoes.retomar();
			else tela.painelDeOpcoes.iniciar();
			break;
		case 'S':
			sobreAtivado = true;
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}

	/**
	 * Verifica se é possível retomar o painel do jogo
	 * 
	 * @param inicial
	 * @return
	 */
	private boolean continuavel(char inicial) {
		if (inicial == 'C') return tela.painelDoJogo.pausado;
		return true;
	}
}
