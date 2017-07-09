package sistema.controlador;

import java.util.List;

import sistema.utilitario.arquivo.Arquivo.ArquivoDoQuest;

/**
 * Classe que controla os dados do questionário
 * 
 * @author Emanuel
 */
public class ControladorDoQuestionario {
	// TODO Arrumar o arquivo de perguntas
	private Questionario questionario;

	/**
	 * Carregas as perguntas e opções do questionário
	 */
	public ControladorDoQuestionario() {
		questionario = new Questionario(ArquivoDoQuest.ler(true), ArquivoDoQuest.ler(false));
	}

	/**
	 * Obtem a quantidade de Perguntas do questionário
	 * 
	 * @return int
	 */
	public int obterQtdPerguntas() {
		return questionario.perguntas.size();
	}

	/**
	 * Obtem a quantidade de Respostas do questionário
	 * 
	 * @return int
	 */
	public int obterQtdRespostas() {
		return questionario.opcoes.size();
	}

	/**
	 * Obtem uma Pergunta do questionário
	 * 
	 * @param index
	 * @return String
	 */
	public String obterPergunta(int index) {
		return questionario.perguntas.get(index);
	}

	/**
	 * Obtem uma Resposta do questionário
	 * 
	 * @param index
	 * @return String
	 */
	public String obterResposta(int index) {
		return questionario.opcoes.get(index);
	}

	/**
	 * Classe com os dados do questionário
	 * 
	 * @author Emanuel
	 */
	public class Questionario {
		private List<String> perguntas;
		private List<String> opcoes;

		/**
		 * Cria o objeto questionário
		 * 
		 * @param perguntas
		 * @param opcoes
		 */
		public Questionario(List<String> perguntas, List<String> opcoes) {
			this.perguntas = perguntas;
			this.opcoes = opcoes;
		}
	}

	/**
	 * Testa o controlador do questionário
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ControladorDoQuestionario controladorDoQuest = new ControladorDoQuestionario();
		for (int i = 0; i < controladorDoQuest.obterQtdPerguntas(); i++) {
			System.out.println(controladorDoQuest.obterPergunta(i));
			System.out.print(controladorDoQuest.obterResposta(0));
			for (int j = 1; j < controladorDoQuest.obterQtdRespostas(); j++) {
				System.out.print(" - " + controladorDoQuest.obterResposta(j));
			}
			System.out.println("\n");
		}
	}
}
