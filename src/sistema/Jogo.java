package sistema;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import sistema.igu.Painel;
import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;
import sistema.utilitario.TocadorDeMusica;
import sistema.utilitario.arquivo.Recurso;

/**
 * Inicia todo o processo do jogo
 * 
 * @author Emanuel
 * @see GP Ep 102
 */
public class Jogo {

	/**
	 * Inicia o jogo
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Inicializa as possiveis resolu��es para a configura��o
		Resolucao.iniciar();
		// Carrega as configura��es de op��es
		Opcoes.carregarConfig();
		if (args.length > 0 && args[0].toUpperCase().equals("TESTE")) {
			Opcoes.modoDesenvolvimento = true;
		}

		// Inicia a tela do jogo
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Jogo().new Janela();
					new TocadorDeMusica();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a janela do jogo
	 * 
	 * @author Emanuel
	 */
	public class Janela extends JFrame {
		private static final long serialVersionUID = 1L;
		public String TITULO = "Selecao Natural";
		public final int LARGURA_PADRAO, ALTURA_PADRAO;
		public Painel painel;

		/**
		 * Cria a Tela
		 */
		public Janela() {
			// Obt�m o endereco da imagem do icone
			Recurso recurso = new Recurso();
			URL endereco = recurso.obterEndereco("/imagens/icones/icone.ico");

			setIconImage(Toolkit.getDefaultToolkit().getImage(endereco));
			setTitle(TITULO);
			setFont(new Font("Times New Roman", Font.PLAIN, 12));
			LARGURA_PADRAO = Opcoes.larguraPadrao;
			ALTURA_PADRAO = Opcoes.alturaPadrao;
			setBounds(100, 100, LARGURA_PADRAO, ALTURA_PADRAO);
			if (getHeight() == Resolucao.alturas[2]) setExtendedState(JFrame.MAXIMIZED_BOTH);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			painel = new Painel(this);
			add(painel);
		}

		/**
		 * Redimensiona o tamanho da tela trocando a altura
		 * 
		 * @param valor
		 */
		public void redimensionar(float valor) {
			if (getHeight() < Resolucao.alturas[1]) setSize((int) (getWidth() * valor), (int) (getHeight() * valor));
		}
	}
}
