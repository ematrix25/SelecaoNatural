package gui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author Emanuel
 *
 */
public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel painel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Menu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Menu() {
		setResizable(false);
		setTitle("Selecao Natural");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setVisible(true);
		painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painel);
		painel.setLayout(null);
		
		JButton btnJogar = new JButton("Jogar");
		btnJogar.setBounds(330, 50, 90, 30);
		painel.add(btnJogar);
		
		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(330, 150, 90, 30);
		painel.add(btnContinuar);
		
		JButton btnOpcoes = new JButton("Opcoes");
		btnOpcoes.setBounds(330, 100, 90, 30);
		painel.add(btnOpcoes);
		
		JButton btnSobre = new JButton("Sobre");
		btnSobre.setBounds(330, 200, 90, 30);
		painel.add(btnSobre);
	}
}
