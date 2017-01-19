package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.graphics.PainelDoMenu;

/**
 * @author Emanuel
 *
 */
public class TelaDoMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel painel, painelDosBotoes;
	private PainelDoMenu painelDoMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaDoMenu();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public TelaDoMenu() throws IOException {		
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaDoMenu.class.getResource("/imagens/icone.ico")));
		setTitle("Selecao Natural");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setVisible(true);

		painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		painel.setLayout(null);
		setContentPane(painel);

		painelDosBotoes = new JPanel();
		painelDosBotoes.setBounds(330, 30, 90, 210);
		painelDosBotoes.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelDosBotoes.setLayout(null);
		painelDosBotoes.setOpaque(false);
		painel.add(painelDosBotoes);

		JButton btnJogar = new JButton("Jogar");
		btnJogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new TelaDoJogo();
				dispose();
			}
		});
		btnJogar.setBounds(0, 0, 90, 30);
		btnJogar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDosBotoes.add(btnJogar);

		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(0, 60, 90, 30);
		btnContinuar.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDosBotoes.add(btnContinuar);

		JButton btnOpcoes = new JButton("Opcoes");
		btnOpcoes.setBounds(0, 120, 90, 30);
		btnOpcoes.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDosBotoes.add(btnOpcoes);

		JButton btnSobre = new JButton("Sobre");
		btnSobre.setBounds(0, 180, 90, 30);
		btnSobre.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDosBotoes.add(btnSobre);

		painelDoMenu = new PainelDoMenu();
		painelDoMenu.setBounds(0, 0, 444, 272);
		painel.add(painelDoMenu);
	}
}
