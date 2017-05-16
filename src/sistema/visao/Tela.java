package sistema.visao;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sistema.utilitario.Resolucao;
import sistema.visao.painel.PainelDoJogo;
import sistema.visao.painel.PainelDoMenu;
import sistema.visao.painel.PainelDoQuest;

/**
 * Cria a Tela do jogo
 * 
 * @author Emanuel
 */
public class Tela extends JFrame {
	// TODO Implementar a tela aqui usando paineis renderizados

	private static final long serialVersionUID = 1L;
	private static final int ALTURA = 300;
	public final String TITULO = "Selecao Natural";
	public JPanel painelDoMenu, painelDoJogo, painelDoQuest;

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
		setBounds(100, 100, Resolucao.calcLarguraRelativa(ALTURA), ALTURA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		painelDoMenu = new PainelDoMenu(this);
		painelDoJogo = new PainelDoJogo(this);
		painelDoQuest = new PainelDoQuest(this);
		
		painelDoMenu.setSize(getWidth(), getHeight());
		painelDoJogo.setSize(getWidth(), getHeight());
		painelDoQuest.setSize(getWidth(), getHeight());

		getContentPane().add(painelDoMenu);
		((PainelDoMenu) painelDoMenu).start();
	}
}