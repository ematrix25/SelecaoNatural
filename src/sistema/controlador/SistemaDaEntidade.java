package sistema.controlador;

import entidade.Entidade;

/**
 * Controla as Entidades do Jogo
 * 
 * @author Emanuel
 */
public interface SistemaDaEntidade {

	/**
	 * Obtem a proxima id
	 * 
	 * @return
	 */
	int obterProxID();

	/**
	 * Registra uma entidade que foi criada
	 * 
	 * @param entidade
	 * @return
	 */
	boolean registrarEntidade(Entidade entidade);

	/**
	 * Obtem a entidade da id especificada
	 * 
	 * @param id
	 * @return
	 */
	Entidade obterEntidade(int id);

	/**
	 * Remove uma entidade registrada
	 * 
	 * @param entidade
	 * @return
	 */
	boolean matarEntidade(Entidade entidade);
}
