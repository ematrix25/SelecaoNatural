package sistema.visao;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * Cria a tela do Questionário
 * 
 * @author Emanuel
 *
 */
public class TelaDoQuestionario extends JFrame {
	// TODO Remover TelaDoQuestionario quando Tela estiver pronta

	private static final long serialVersionUID = 1L;
	private JPanel painel, painelDaP1, painelDaP2, painelDaP3;

	/**
	 * Roda a aplicação
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaDoQuestionario();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Cria a tela.
	 */
	public TelaDoQuestionario() {
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/icone.ico")));
		setTitle("Selecao Natural");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setVisible(true);

		painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		painel.setLayout(null);
		setContentPane(painel);

		painelDaP1 = new JPanel();
		painelDaP1.setBounds(10, 10, 310, 70);
		painelDaP1.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelDaP1.setLayout(null);
		painel.add(painelDaP1);

		JLabel lblPergunta = new JLabel("Pergunta1");
		lblPergunta.setBounds(0, 0, 90, 30);
		lblPergunta.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP1.add(lblPergunta);

		JRadioButton rdbtnOpcao = new JRadioButton("Opcao1.1");
		rdbtnOpcao.setBounds(0, 40, 90, 30);
		rdbtnOpcao.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP1.add(rdbtnOpcao);

		JRadioButton rdbtnOpcao_1 = new JRadioButton("Opcao1.2");
		rdbtnOpcao_1.setBounds(110, 40, 90, 30);
		rdbtnOpcao_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP1.add(rdbtnOpcao_1);

		JRadioButton rdbtnOpcao_2 = new JRadioButton("Opcao1.3");
		rdbtnOpcao_2.setBounds(220, 40, 90, 30);
		rdbtnOpcao_2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP1.add(rdbtnOpcao_2);

		painelDaP2 = new JPanel();
		painelDaP2.setSize(310, 70);
		painelDaP2.setLocation(10, 90);
		painelDaP2.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelDaP2.setLayout(null);
		painel.add(painelDaP2);

		JLabel lblPergunta_1 = new JLabel("Pergunta2");
		lblPergunta_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPergunta_1.setBounds(0, 0, 90, 30);
		painelDaP2.add(lblPergunta_1);

		JRadioButton rdbtnOpcao_3 = new JRadioButton("Opcao2.1");
		rdbtnOpcao_3.setBounds(0, 40, 90, 30);
		rdbtnOpcao_3.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP2.add(rdbtnOpcao_3);

		JRadioButton rdbtnOpcao_4 = new JRadioButton("Opcao2.2");
		rdbtnOpcao_4.setBounds(110, 40, 90, 30);
		rdbtnOpcao_4.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP2.add(rdbtnOpcao_4);

		JRadioButton rdbtnOpcao_5 = new JRadioButton("Opcao2.3");
		rdbtnOpcao_5.setBounds(220, 40, 90, 30);
		rdbtnOpcao_5.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP2.add(rdbtnOpcao_5);

		painelDaP3 = new JPanel();
		painelDaP3.setSize(310, 70);
		painelDaP3.setLocation(10, 160);
		painelDaP3.setBorder(new EmptyBorder(5, 5, 5, 5));
		painelDaP3.setLayout(null);
		painel.add(painelDaP3);

		JLabel lblPergunta_2 = new JLabel("Pergunta3");
		lblPergunta_2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPergunta_2.setBounds(0, 0, 90, 30);
		painelDaP3.add(lblPergunta_2);

		JRadioButton rdbtnOpcao_6 = new JRadioButton("Opcao3.1");
		rdbtnOpcao_6.setBounds(0, 40, 90, 30);
		rdbtnOpcao_6.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP3.add(rdbtnOpcao_6);

		JRadioButton rdbtnOpcao_7 = new JRadioButton("Opcao3.2");
		rdbtnOpcao_7.setBounds(110, 40, 90, 30);
		rdbtnOpcao_7.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP3.add(rdbtnOpcao_7);

		JRadioButton rdbtnOpcao_8 = new JRadioButton("Opcao3.3");
		rdbtnOpcao_8.setBounds(220, 40, 90, 30);
		rdbtnOpcao_8.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaP3.add(rdbtnOpcao_8);

		JButton btnSubmeter = new JButton("Submeter");
		btnSubmeter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		btnSubmeter.setBounds(345, 238, 89, 23);
		btnSubmeter.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painel.add(btnSubmeter);
	}
}
