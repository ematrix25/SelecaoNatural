package sistema.visao.painel;

import javax.swing.JPanel;

import sistema.visao.Tela;

/**
 * Cria o Painel do Questionario
 * 
 * @author Emanuel
 */
public class PainelDoQuest extends JPanel {
	//TODO Terminar o painel baseado na TelaDoQuestionario

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Tela tela;
	
	/**
	 * Inicializa o painel do questionario
	 */
	public PainelDoQuest(Tela tela) {
		//Creio que nao precisa de referencia para a tela
		this.tela = tela;
	}
}
