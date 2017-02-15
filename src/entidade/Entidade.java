package entidade;

import java.util.ArrayList;
import java.util.List;

import componente.Componente;

/**
 * Abstrai classes para entidades animadas e inanimadas
 * 
 * @author Emanuel
 */
public abstract class Entidade {
	public int id;
	private List<Componente> componentes = new ArrayList<Componente>();

	/**
	 * Adiciona um componente
	 * 
	 * @param componente
	 * @return
	 */
	public boolean adicionarComponente(Componente componente) {
		return componentes.add(componente);
	}

	/**
	 * Remove um componente
	 * 
	 * @param componente
	 * @return
	 */
	public boolean removerComponente(Componente componente) {
		return componentes.remove(componente);
	}
}
