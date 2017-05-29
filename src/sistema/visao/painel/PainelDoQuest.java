package sistema.visao.painel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;

import sistema.utilitario.arquivo.Arquivo.ArquivoDoQuest;
import sistema.utilitario.arquivo.Recurso;
import sistema.visao.Tela;

/**
 * Cria o Painel do Questionario
 * 
 * @author Emanuel
 */
public class PainelDoQuest extends Painel {

	private static final long serialVersionUID = 1L;
	private int respostas[];

	/**
	 * Inicializa o painel do questionário
	 *
	 * @param tela
	 */
	public PainelDoQuest(Tela tela) {
		super(tela, "Quest");
		Recurso recurso = new Recurso();
		try {
			// TODO Criar alguma imagem diferente
			imagem = ImageIO.read(recurso.getEnderecoEmFluxo("/imagens/menu.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		respostas = new int[5];
	}

	/**
	 * Inicia a thread da painel do questionário
	 */
	public void iniciar() {
		super.iniciar(1.7f);
	}

	/**
	 * Executa a thread do painel do questionário
	 * 
	 * @see sistema.visao.painel.Painel#run()
	 */
	@Override
	public void run() {
		while (executando) {
			requestFocusInWindow();
			try {
				Thread.sleep(20);
				synchronized (this) {
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
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics graficos) {
		super.paintComponent(graficos);
		renderizar(graficos);
	}

	/**
	 * Renderiza o painel do questionário
	 *
	 * @param graficos
	 */
	protected void renderizar(Graphics graficos) {
		String opcoes[] = new String[5];
		int resposta;

		// TODO Escrever as perguntas e opções de respostas aqui
		for (int i = 0; i < respostas.length; i++) {
			for (int j = 0; j < opcoes.length; j++) {
				opcoes[j] = "Opção " + (i + 1) + "." + (j + 1);
			}
			resposta = renderizarPergunta(graficos, "Pergunta " + (i + 1), opcoes, 40 + 80 * i, i);
			if (resposta != -1) respostas[i] = resposta;
		}

		// Renderiza o botão para submeter as respostas
		renderizarBotao(graficos, "Submeter", 40);
	}

	/**
	 * Renderiza a pergunta com suas opções de respostas
	 *
	 * @param graficos
	 * @param texto
	 * @param opcoes
	 * @param desvioY
	 * @param pergunta
	 * @return
	 */
	private int renderizarPergunta(Graphics graficos, String texto, String[] opcoes, int desvioY, int pergunta) {
		int x = 20, y = desvioY;
		int resposta = -1;

		// Texto da Pergunta
		graficos.setColor(Color.lightGray);
		graficos.setFont(new Font("Verdana", 1, 18));
		graficos.drawString(texto, x, y);

		// Renderiza as Opções de respostas da Pergunta
		for (int i = 0; i < opcoes.length; i++) {
			if (respostas[pergunta] == i + 1) {
				if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, true)) resposta = i + 1;
			} else if (renderizarOpcao(graficos, opcoes[i], x + 150 * i, y + 20, false)) resposta = i + 1;
		}
		return resposta;
	}

	/**
	 * Renderiza uma opção de resposta com o texto em x e y
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
	 * Renderiza a marcação da opção da resposta em x e y
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
	 * Realiza a ação do botão quando clicado
	 * 
	 * @see sistema.visao.painel.Painel#acaoDoBotao(char)
	 */
	@Override
	protected void acaoDoBotao(char inicial) {
		switch (inicial) {
		case 'S':
			ArquivoDoQuest.escrever(respostas);
			tela.dispatchEvent(new WindowEvent(tela, WindowEvent.WINDOW_CLOSING));
			break;
		default:
			System.out.println("Botao clicado nao reconhecido");
			break;
		}
	}
}
