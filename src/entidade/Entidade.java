package entidade;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import sistema.controlador.SistemaDaEntidade;

/**
 * Abstrai classes para entidades animadas e inanimadas
 * 
 * @author Emanuel
 */
public abstract class Entidade {

	public int id;
	public static SistemaDaEntidade sistemaDaEntidade;

	/**
	 * Cria uma entidade com a id
	 * 
	 * @param id
	 */
	public Entidade() {
		this.id = UUID.randomUUID().hashCode();
		if (sistemaDaEntidade == null) throw new IllegalArgumentException(
				"Nao há um sistemaDaEntidade global, então crie um antes de criar uma Entidade");
		sistemaDaEntidade.registrarEntidade(this);
	}

	public class SistemaBaseDaEntidade implements SistemaDaEntidade {

		public List<Entidade> entidades = new LinkedList<Entidade>();

		/**
		 *  Cria um sistemaBaseDaEntidade e associa com a entidade se for nulo
		 */
		public SistemaBaseDaEntidade() {
			if (Entidade.sistemaDaEntidade == null) {
				Entidade.sistemaDaEntidade = this;
			}
		}

		/**
		 * Obtem a proxima id
		 * 
		 * @see sistema.controlador.SistemaDaEntidade#obterProxID()
		 */
		@Override
		public int obterProxID() {
			return entidades.iterator().next().id;
		}

		/**
		 * Registra uma entidade que foi criada
		 * @return 
		 * 
		 * @see sistema.controlador.SistemaDaEntidade#registrarEntidade(entidade.
		 *      Entidade)
		 */
		@Override
		public boolean registrarEntidade(Entidade entidade) {
			return entidades.add(entidade);
		}

		/**
		 * Obtem a entidade da id especificada
		 * 
		 * @see sistema.controlador.SistemaDaEntidade#obterEntidade(int)
		 */
		@Override
		public Entidade obterEntidade(int id) {
			return entidades.get(id);
		}

		/**
		 * Remove uma entidade registrada
		 * @return 
		 * 
		 * @see sistema.controlador.SistemaDaEntidade#matarEntidade(entidade.
		 *      Entidade)
		 */
		@Override
		public boolean matarEntidade(Entidade entidade) {
			return entidades.remove(entidade);
		}
	}
}
