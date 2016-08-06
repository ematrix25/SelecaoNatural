package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import gui.graphics.TelaDoJogo;

/**
 * @author Emanuel
 *
 */
public class Jogo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel painel, painelDaCelula, painelDaPontuacao, painelDaEspecie;
	private TelaDoJogo telaDoJogo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Jogo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Jogo() {
		setResizable(false);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Menu.class.getResource("/imagens/icone.ico")));
		setTitle("Selecao Natural");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setVisible(true);

		painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 5, 5));
		painel.setLayout(null);
		setContentPane(painel);

		painelDaCelula = new JPanel();
		painelDaCelula.setBackground(Color.WHITE);
		painelDaCelula.setBounds(20, 0, 120, 30);
		painelDaCelula.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelDaCelula.setLayout(null);
		painel.add(painelDaCelula);

		JLabel lblMassaCelular = new JLabel("Massa Celular:");
		lblMassaCelular.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMassaCelular.setBounds(10, 5, 80, 20);
		painelDaCelula.add(lblMassaCelular);

		JLabel lblValorDaMassaCelular = new JLabel("10%");
		lblValorDaMassaCelular.setBounds(85, 5, 30, 20);
		lblValorDaMassaCelular.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaCelula.add(lblValorDaMassaCelular);

		painelDaPontuacao = new JPanel();
		painelDaPontuacao.setBackground(Color.GRAY);
		painelDaPontuacao.setBounds(160, 0, 120, 30);
		painelDaPontuacao.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelDaPontuacao.setLayout(null);
		painel.add(painelDaPontuacao);
		
		JLabel lblPontuacao = new JLabel("Pontuacao:");
		lblPontuacao.setForeground(Color.LIGHT_GRAY);
		lblPontuacao.setBounds(10, 5, 60, 20);
		lblPontuacao.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaPontuacao.add(lblPontuacao);

		JLabel lblValorDaPontuacao = new JLabel("10110");
		lblValorDaPontuacao.setForeground(Color.LIGHT_GRAY);
		lblValorDaPontuacao.setBounds(70, 5, 40, 20);
		lblValorDaPontuacao.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaPontuacao.add(lblValorDaPontuacao);
		
		painelDaEspecie = new JPanel();
		painelDaEspecie.setBackground(Color.DARK_GRAY);
		painelDaEspecie.setBounds(300, 0, 120, 30);
		painelDaEspecie.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelDaEspecie.setLayout(null);
		painel.add(painelDaEspecie);

		JLabel lblQtdCelulas = new JLabel("Qtd Celulas:");
		lblQtdCelulas.setForeground(Color.WHITE);
		lblQtdCelulas.setBounds(5, 5, 60, 20);
		lblQtdCelulas.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaEspecie.add(lblQtdCelulas);

		JLabel lblValorQtdCelulas = new JLabel("1000");
		lblValorQtdCelulas.setForeground(Color.WHITE);
		lblValorQtdCelulas.setBounds(70, 5, 40, 20);
		lblValorQtdCelulas.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaEspecie.add(lblValorQtdCelulas);

		telaDoJogo = new TelaDoJogo();
		telaDoJogo.setToolTipText("");
		telaDoJogo.setBounds(0, 30, 444, 242);
		telaDoJogo.setLayout(null);
		painel.add(telaDoJogo);
	}
}
