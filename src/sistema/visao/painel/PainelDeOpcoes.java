package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;
import sistema.visao.Tela;

/**
 * @author Emanuel
 *
 */
public class PainelDeOpcoes extends Painel {
	// TODO Salvar as configurações em arquivo

	private static final long serialVersionUID = 1L;
	private int configuracoes[];

	/**
	 * Inicializa o painel de opções
	 * 
	 * @param tela
	 * @param nome
	 */
	public PainelDeOpcoes(Tela tela) {
		super(tela, "Opcoes");
		try {
			// TODO Criar alguma imagem para as opções
			imagem = ImageIO.read(getClass().getResourceAsStream("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configuracoes = Opcoes.configuracoes;
	}

	/**
	 * Inicia a thread da painel de opções
	 */
	public synchronized void iniciar() {
		super.iniciar(1.1f);
	}
	
	/**
	 * Retoma a thread do painel do jogo
	 */
	public synchronized void retomar() {
		super.retomar(1.1f);
	}

	/**
	 * Executa a thread do painel de opções
	 * 
	 * @see sistema.visao.painel.Painel#run()
	 */
	@Override
	public void run() {
		requestFocus();
		while (executando) {
			try {
				Thread.sleep(20);
				synchronized (thread) {
					while (pausado) {
						thread.wait();
					}
				}
			} catch (InterruptedException ex) {
			}

			repaint();
		}
	}

	/**
	 * Pinta o painel que é usado no repaint
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
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
		String opcoes[] = { "Teclado", "Mouse" };
		int configuracao;

		// Renderiza a configuração do controle de jogo
		configuracao = renderizarConfig(graficos, "Controlar o Jogo por:", opcoes, 40, 0);
		if (configuracao != -1) configuracoes[0] = configuracao;

		// Renderiza a configuração de tamanho da janela
		opcoes = new String[3];
		for (int i = 0; i < opcoes.length; i++) {
			opcoes[i] = Resolucao.larguras[i] + "x" + Resolucao.alturas[i];
		}
		configuracao = renderizarConfig(graficos, "Tamanho da janela:", opcoes, 120, 1);
		if (configuracao != -1) configuracoes[1] = configuracao;

		// Renderiza os botões
		renderizarBotao(graficos, "Cancelar", 100);
		renderizarBotao(graficos, "Salvar", 200);
	}

	/**
	 * Renderiza a configuração com suas opções
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @param configuracao
	 * @return
	 */
	private int renderizarConfig(Graphics graficos, String texto, String[] opcoes, int desvioY, int configuracao) {
		int x = 20, y = desvioY;
		int opcao = -1;

		// Texto da Configuração
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de configuração
		for (int i = 0; i < opcoes.length; i++) {
			if (configuracoes[configuracao] == i + 1) {
				if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, true)) opcao = i + 1;
			} else if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, false)) opcao = i + 1;
		}
		return opcao;
	}

	/**
	 * Renderiza uma opção de configuração com o texto em x e y
	 *
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 * @param desvioY
	 * @param selecionado
	 * @return
	 */
	private boolean renderizarOpcao(Graphics graficos, String texto, int desvioX, int desvioY, boolean selecionado) {
		int x = desvioX, y = desvioY;
		int tamanho = 8;

		// Botao circular da opção
		graficos.setColor(Color.white);
		graficos.fillOval(x, y, tamanho, tamanho);

		// Texto da opção
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 15, y + tamanho);

		// Marcação de seleção da opção
		if (mouseClicouNoBotao(x, y, tamanho, tamanho)) {
			renderizarMarcacao(graficos, x, y, tamanho / 2);
			return true;
		} else if (selecionado) renderizarMarcacao(graficos, x, y, tamanho / 2);
		return false;
	}

	/**
	 * Renderiza a marcação da opção da configuração em x e y
	 * 
	 * @param graficos
	 * @param x
	 * @param y
	 * @param tamanho
	 */
	private void renderizarMarcacao(Graphics graficos, int x, int y, int tamanho) {
		graficos.setColor(Color.black);
		graficos.fillOval(x + (tamanho / 2), y + (tamanho / 2), tamanho, tamanho);
	}

	/**
	 * Renderiza um botao com o texto em x e y saindo do canto inferior direito
	 * 
	 * @param graficos
	 * @param texto
	 * @param desvioX
	 */
	private void renderizarBotao(Graphics graficos, String texto, int desvioX) {
		int x = this.getWidth() - desvioX, y = this.getHeight() - 40;
		int largura = 90, altura = 30;

		// Retangulo do botao
		graficos.setColor(Color.white);
		if (mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.gray);
		graficos.fillRect(x, y, largura, altura);

		// Texto do botao
		graficos.setColor(Color.black);
		if (mouseEstaNoBotao(x, y, largura, altura)) graficos.setColor(Color.white);
		graficos.setFont(new Font("Verdana", 0, 12));
		graficos.drawString(texto, x + 10, y + 20);

		if (mouseClicouNoBotao(x, y, largura, altura)) acaoDoBotao(texto.charAt(0));
	}

	/**
	 * Realiza a ação do botao quando clicado
	 * 
	 * @see sistema.visao.painel.Painel#acaoDoBotao(char)
	 */
	@Override
	protected void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 'S':
			Opcoes.carregarConfig(configuracoes);
			voltarParaMenu();
			break;
		case 'C':
			voltarParaMenu();
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}

	/**
	 * Volta para o painel do menu
	 */
	private void voltarParaMenu() {
		tela.remove(tela.painelDeOpcoes);
		tela.painelDeOpcoes.pausar();
		tela.add(tela.painelDoMenu);
		((PainelDoMenu) tela.painelDoMenu).retomar();
	}
}
