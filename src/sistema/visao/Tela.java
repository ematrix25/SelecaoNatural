package sistema.visao;

import java.awt.Font;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

import sistema.utilitario.Opcoes;
import sistema.utilitario.Resolucao;
import sistema.utilitario.arquivo.Recurso;
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
	 * Cria a Tela
	 */
	public Tela() {
		// Obtem o endereco da imagem do icone
		Recurso recurso = new Recurso();
		URL endereco = recurso.getEndereco("/imagens/icone.ico");

		setIconImage(Toolkit.getDefaultToolkit().getImage(endereco));
		setTitle(TITULO);
		setFont(new Font("Times New Roman", Font.PLAIN, 12));
		setBounds(100, 100, Opcoes.larguraPadrao, Opcoes.alturaPadrao);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		// Inicia os paineis
		painelDoMenu = new PainelDoMenu(this);
		painelDoJogo = new PainelDoJogo(this);
		painelDoQuest = new PainelDoQuest(this);
		painelDeOpcoes = new PainelDeOpcoes(this);

		// Configura o tamanho dos paineis
		painelDoMenu.setSize(getWidth(), getHeight());
		painelDoJogo.setSize(getWidth(), getHeight());
		painelDoQuest.setSize(getWidth(), getHeight());
		painelDeOpcoes.setSize(getWidth(), getHeight());

		// Inicia o painel do menu
		getContentPane().add(painelDoMenu);
		((PainelDoMenu) painelDoMenu).iniciar();
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