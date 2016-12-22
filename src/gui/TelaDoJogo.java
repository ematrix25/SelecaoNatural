package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import gui.graphics.CanvasDoJogo;

/**
 * @author Emanuel
 *
 */
public class TelaDoJogo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel painel, painelDaCelula, painelDaPontuacao, painelDaEspecie;
	private CanvasDoJogo canvasDoJogo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new TelaDoJogo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaDoJogo() {
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
		
		JPanel painelDeInfos = new JPanel();
		painelDeInfos.setBackground(Color.BLACK);
		painelDeInfos.setBorder(new LineBorder(new Color(0, 0, 0)));
		painelDeInfos.setBounds(0, 0, 444, 30);
		painel.add(painelDeInfos);
		painelDeInfos.setLayout(null);

		painelDaCelula = new JPanel();
		painelDaCelula.setBackground(Color.LIGHT_GRAY);
		painelDaCelula.setBounds(10, 5, 120, 20);
		painelDaCelula.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelDaCelula.setLayout(null);
		painelDeInfos.add(painelDaCelula);

		JLabel lblMassaCelular = new JLabel("Massa Celular:");
		lblMassaCelular.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblMassaCelular.setBounds(10, 0, 80, 20);
		painelDaCelula.add(lblMassaCelular);

		JLabel lblValorDaMassaCelular = new JLabel("10%");
		lblValorDaMassaCelular.setBounds(85, 0, 30, 20);
		lblValorDaMassaCelular.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaCelula.add(lblValorDaMassaCelular);

		painelDaPontuacao = new JPanel();
		painelDaPontuacao.setBackground(Color.GRAY);
		painelDaPontuacao.setBounds(165, 6, 120, 20);
		painelDaPontuacao.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelDaPontuacao.setLayout(null);
		painelDeInfos.add(painelDaPontuacao);
		
		JLabel lblPontuacao = new JLabel("Pontuacao:");
		lblPontuacao.setForeground(Color.LIGHT_GRAY);
		lblPontuacao.setBounds(10, 0, 60, 20);
		lblPontuacao.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaPontuacao.add(lblPontuacao);

		JLabel lblValorDaPontuacao = new JLabel("10110");
		lblValorDaPontuacao.setForeground(Color.LIGHT_GRAY);
		lblValorDaPontuacao.setBounds(70, 0, 40, 20);
		lblValorDaPontuacao.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaPontuacao.add(lblValorDaPontuacao);
		
		painelDaEspecie = new JPanel();
		painelDaEspecie.setBackground(Color.DARK_GRAY);
		painelDaEspecie.setBounds(315, 6, 120, 20);
		painelDaEspecie.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		painelDaEspecie.setLayout(null);
		painelDeInfos.add(painelDaEspecie);

		JLabel lblQtdCelulas = new JLabel("Qtd Celulas:");
		lblQtdCelulas.setForeground(Color.WHITE);
		lblQtdCelulas.setBounds(10, 0, 60, 20);
		lblQtdCelulas.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaEspecie.add(lblQtdCelulas);

		JLabel lblValorQtdCelulas = new JLabel("1000");
		lblValorQtdCelulas.setForeground(Color.WHITE);
		lblValorQtdCelulas.setBounds(75, 0, 35, 20);
		lblValorQtdCelulas.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		painelDaEspecie.add(lblValorQtdCelulas);

		canvasDoJogo = new CanvasDoJogo();
		canvasDoJogo.setBounds(0, 30, 444, 242);
		painel.add(canvasDoJogo);
		canvasDoJogo.start(this);
	}
}
