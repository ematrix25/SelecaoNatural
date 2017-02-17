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
	public int obterProxID();

	/**
	 * Registra uma entidade que foi criada
	 * 
	 * @param entidade
	 * @return
	 */
	public boolean registrarEntidade(Entidade entidade);

	/**
	 * Obtem a entidade da id especificada
	 * 
	 * @param id
	 * @return
	 */
	public Entidade obterEntidade(int id);

	/**
	 * Remove uma entidade registrada
	 * 
	 * @param entidade
	 * @return
	 */
	public boolean matarEntidade(Entidade entidade);
}
