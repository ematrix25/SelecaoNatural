package sistema.controlador.jogo;

import java.util.List;

import componente.Componente;
import componente.Componente.EstadoDaIA;
import componente.Componente.Posicao;
import componente.Componente.Velocidade;
import componente.Especime;

/**
 * Classe que controla os dados da Entidade Móvel
 * 
 * @author Emanuel
 */
public class ContAuxDaEnt {

	private Entidade entidade;

	/**
	 * Obtém a entidade
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
		EstadoDaIA estadoDaIA = null;
		for (Componente componente : componentes) {
			if (componente == null) continue;
			if (componente.getClass() == Especime.class) especime = (Especime) componente;
			if (componente.getClass() == Posicao.class) posicao = (Posicao) componente;
			if (componente.getClass() == Velocidade.class) velocidade = (Velocidade) componente;
			if (componente.getClass() == EstadoDaIA.class) estadoDaIA = (EstadoDaIA) componente;
		}
		this.entidade = new Entidade(id, especime, posicao, velocidade, estadoDaIA);
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
		public EstadoDaIA estadoDaIA;

		/**
		 * Cria o objeto da entidade
		 * 
		 * @param id
		 * @param especime
		 * @param posicao
		 * @param velocidade
		 */
		public Entidade(int id, Especime especime, Posicao posicao, Velocidade velocidade, EstadoDaIA estadoDaIA) {
			this.id = id;
			this.especime = especime;
			this.posicao = posicao;
			this.velocidade = velocidade;
			this.estadoDaIA = estadoDaIA;
		}
	}
}
