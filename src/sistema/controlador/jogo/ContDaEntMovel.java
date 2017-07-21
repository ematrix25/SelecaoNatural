package sistema.controlador.jogo;

import java.util.List;

import componente.Componente;
import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Especime;

/**
 * Classe que controla os dados da Entidade Móvel
 * 
 * @author Emanuel
 */
public class ContDaEntMovel {

	private Entidade entidade;

	/**
	 * Obtem a entidade
	 * 
	 * @return Entidade
	 */
	public Entidade obterEntidade() {
		return entidade;
	}

	/**
	 * Configura a entidade dada uma lista de componentes
	 */
	public void configurarEntidade(int id, List<Componente> componentes) {
		Especime especime = null;
		Posicao posicao = null;
		Velocidade velocidade = null;
		for (Componente componente : componentes) {
			if (componente.getClass() == Especime.class) especime = (Especime) componente;
			if (componente.getClass() == Posicao.class) posicao = (Posicao) componente;
			if (componente.getClass() == Velocidade.class) velocidade = (Velocidade) componente;
		}
		this.entidade = new Entidade(id, especime, posicao, velocidade);
	}

	/**
	 * Classe que guarda os dados da Entidade
	 * 
	 * @author Emanuel
	 */
	public class Entidade {
		public int id;
		public Especime especime;
		public Posicao posicao;
		public Velocidade velocidade;

		/**
		 * Cria o objeto da entidade
		 * 
		 * @param id
		 * @param especime
		 * @param posicao
		 * @param velocidade
		 */
		public Entidade(int id, Especime especime, Posicao posicao, Velocidade velocidade) {
			this.id = id;
			this.especime = especime;
			this.posicao = posicao;
			this.velocidade = velocidade;
		}
	}
}
