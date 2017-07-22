package sistema.controlador;

import java.util.List;

import sistema.utilitario.arquivo.Arquivo.ArquivoDoQuest;

/**
 * Classe que controla os dados do question�rio
 * 
 * @author Emanuel
 */
public class ContDoQuestionario {
	// XXX Arrumar o arquivo de perguntas
	private Questionario questionario;

	/**
	 * Carregas as perguntas e op��es do question�rio
	 */
	public ContDoQuestionario() {
		questionario = new Questionario(ArquivoDoQuest.ler(true), ArquivoDoQuest.ler(false));
	}

	/**
	 * Obtem a quantidade de Perguntas do question�rio
	 * 
	 * @return int
	 */
	public int obterQtdPerguntas() {
		return questionario.perguntas.size();
	}

	/**
	 * Obtem a quantidade de Respostas do question�rio
	 * 
	 * @return int
	 */
	public int obterQtdRespostas() {
		return questionario.opcoes.size();
	}

	/**
	 * Obtem uma Pergunta do question�rio
	 * 
	 * @param index
	 * @return String
	 */
	public String obterPergunta(int index) {
		return questionario.perguntas.get(index);
	}

	/**
	 * Obtem uma Resposta do question�rio
	 * 
	 * @param index
	 * @return String
	 */
	public String obterResposta(int index) {
		return questionario.opcoes.get(index);
	}

	/**
	 * Classe com os dados do question�rio
	 * 
	 * @author Emanuel
	 */
	public class Questionario {
		private List<String> perguntas;
		private List<String> opcoes;

		/**
		 * Cria o objeto question�rio
		 * 
		 * @param perguntas
		 * @param opcoes
		 */
		public Questionario(List<String> perguntas, List<String> opcoes) {
			this.perguntas = perguntas;
			this.opcoes = opcoes;
		}
	}
}
