package sistema.visao;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;

import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;
import sistema.visao.painel.Painel;
import sistema.visao.painel.PainelDeOpcoes;
import sistema.visao.painel.PainelDoJogo;
import sistema.visao.painel.PainelDoMenu;
import sistema.visao.painel.PainelDoQuest;

/**
 * Cria a Tela do jogo
 * 
 * @author Emanuel
 */
public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	public final int ALTURA_PADRAO = 300;
	public String TITULO = "Selecao Natural";
	public Painel painelDoMenu, painelDoJogo, painelDoQuest, painelDeOpcoes;

	/**
	 * Roda a Aplicação.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Tela();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a Tela
	 */
	public Tela() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/icone.ico")));
		setTitle(TITULO);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBounds(100, 100, Opcoes.larguraPadrao, Opcoes.alturaPadrao);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		painelDoMenu = new PainelDoMenu(this);
		painelDoJogo = new PainelDoJogo(this);
		painelDoQuest = new PainelDoQuest(this);
		painelDeOpcoes = new PainelDeOpcoes(this);

		painelDoMenu.setSize(getWidth(), getHeight());
		painelDoJogo.setSize(getWidth(), getHeight());
		painelDoQuest.setSize(getWidth(), getHeight());
		painelDeOpcoes.setSize(getWidth(), getHeight());

		getContentPane().add(painelDoMenu);
		((PainelDoMenu) painelDoMenu).start();
	}

	/**
	 * Redimensiona o tamanho da tela trocando a altura
	 * 
	 * @param altura
	 */
	public void redimensionar(int altura) {
		setSize(Resolucao.calcLarguraRelativa(altura), altura);
	}
}