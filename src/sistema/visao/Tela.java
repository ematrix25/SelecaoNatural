package sistema.visao;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sistema.utilitario.Resolucao;

/**
 * Cria a Tela do jogo
 * 
 * @author Emanuel
 */
public class Tela extends JFrame {
	// TODO Implementar a tela aqui usando paineis renderizados

	private static final long serialVersionUID = 2297499331609901168L;
	private static final int ALTURA = 300;
	private JPanel painelDoMenu, painelDoJogo, painelDoQuestionario;

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
		iniciar();
	}

	/**
	 * Inicia o conteudo do frame.
	 */
	private void iniciar() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaDoMenu.class.getResource("/imagens/icone.ico")));
		setTitle("Selecao Natural");
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBounds(100, 100, Resolucao.calcLarguraRelativa(ALTURA), ALTURA);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
