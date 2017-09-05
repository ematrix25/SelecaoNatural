package sistema.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import componente.Componente;

/**
 * Gerencia as Entidades e seus Componentes
 * 
 * @author Emanuel
 */
public class ContDaEntidade {
	public int menorIDNaoAssociada = 1;
	public List<Integer> entidades, idsParaClonar, idsParaRemocao;
	public HashMap<Class<?>, HashMap<Integer, ? extends Componente>> baseDeComponentes;

	/**
	 * Cria o objeto para controlar Entidades
	 */
	public ContDaEntidade() {
		entidades = new LinkedList<Integer>();
		idsParaClonar = new ArrayList<Integer>();
		idsParaRemocao = new ArrayList<Integer>();
		baseDeComponentes = new HashMap<Class<?>, HashMap<Integer, ? extends Componente>>();
	}

	/**
	 * Cria uma Entidade de ID única
	 * 
	 * @return int
	 */
	public int criarEntidade() {
		int novaID;
		// Previne a criação ao mesmo tempo de duas Entidades com a mesma ID
		synchronized (this) {
			if (menorIDNaoAssociada < Integer.MAX_VALUE) {
				novaID = menorIDNaoAssociada;
				int ID = novaID;
				for (ID += 1; ID <= Integer.MAX_VALUE; ID++) {
					if (!entidades.contains(ID)) break;
				}
				menorIDNaoAssociada = ID;
			} else throw new Error("ERRO: Não é possivel criar Entidade porque não há mais IDs disponíveis");
		}
		entidades.add(novaID);
		return novaID;
	}

	/**
	 * Marca uma entidade para posterior criação ou remoção
	 * 
	 * @param ID
	 * @param remover
	 * @return boolean
	 */
	public boolean marcarEntidades(Integer ID, boolean remover) {
		if (remover) return idsParaRemocao.add(ID);
		else return idsParaClonar.add(ID);
	}

	/**
	 * Remove as entidades que foram marcadas
	 * 
	 * @return boolean
	 */
	public boolean removerEntidades() {
		boolean resultado = false;
		if (!idsParaRemocao.isEmpty()) {
			for (Integer id : idsParaRemocao) {
				resultado |= removerEntidade(id);
			}
			idsParaRemocao.clear();
		}
		return resultado;
	}

	/**
	 * Remove uma Entidade com parametro para remover a chave e não o índice
	 * 
	 * @param ID
	 * @return boolean
	 */
	private boolean removerEntidade(Integer ID) {
		// Previne a remoção ao mesmo tempo da mesma Entidade
		Componente componente;
		synchronized (this) {
			entidades.remove(ID);
			for (HashMap<Integer, ? extends Componente> base : baseDeComponentes.values()) {
				componente = base.remove(ID);
				if (componente != null) {
					if (baseDeComponentes.get(componente.getClass()) == null)
						baseDeComponentes.remove(componente.getClass());
				}
			}
			menorIDNaoAssociada = ID;
		}
		return menorIDNaoAssociada == ID;
	}

	/**
	 * Adiciona um Componente a uma Entidade
	 * 
	 * @param ID
	 * @param componente
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public <T extends Componente> boolean adicionarComponente(int ID, T componente) {
		if (!entidades.contains(ID)) return false;
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(componente.getClass());
		if (base == null) {
			base = new HashMap<Integer, T>();
			baseDeComponentes.put(componente.getClass(), base);
		}
		((HashMap<Integer, T>) base).put(ID, componente);
		return ((HashMap<Integer, T>) base).put(ID, componente) != null;
	}

	/**
	 * Obtém a Entidade dado um Componente
	 * 
	 * @param componente
	 * @return Integer
	 */
	public <T extends Componente> Integer obterEntidadeComOComponente(T componente) {
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(componente.getClass());
		if (base == null) return null;
		for (Integer chave : base.keySet()) {
			if (base.get(chave).equals(componente)) return chave;
		}
		return null;
	}

	/**
	 * Obtém todas as Entidades do tipo de Componente
	 * 
	 * @param tipoDeComponente
	 * @return Conjunto de Integer
	 */
	public <T extends Componente> Set<Integer> obterTodasEntidadesComOComponente(Class<T> tipoDeComponente) {
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
		if (base == null) return new HashSet<Integer>();
		return base.keySet();
	}

	/**
	 * Obtém o Componente da Entidade da ID
	 * 
	 * @param ID
	 * @param tipoDeComponente
	 * @return T
	 */
	public <T extends Componente> T obterComponente(int ID, Class<T> tipoDeComponente) {
		if (!entidades.contains(ID)) return null;
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
		if (base == null) return null;
		T componente = tipoDeComponente.cast(base.get(ID));
		return componente;
	}

	/**
	 * Obtém todos os Componentes da Entidade da ID
	 * 
	 * @param ID
	 * @return Lista de Componentes
	 */
	@SuppressWarnings("unchecked")
	public <T extends Componente> List<T> obterComponentes(int ID) {
		if (!entidades.contains(ID)) return null;
		List<T> componentes = new ArrayList<T>();
		for (Class<?> tipoDeComponente : baseDeComponentes.keySet()) {
			HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
			if (base == null) continue;
			T componente = (T) tipoDeComponente.cast(base.get(ID));
			componentes.add(componente);
		}
		return componentes;
	}

	/**
	 * Obtém todos os Componentes do tipo de Componente
	 * 
	 * @param tipoDeComponente
	 * @return Lista de Componentes
	 */
	@SuppressWarnings("unchecked")
	public <T extends Componente> List<T> obterTodosOsComponentesDoTipo(Class<T> tipoDeComponente) {
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
		if (base == null) {
			return new LinkedList<T>();
		} else {
			List<T> result = new ArrayList<T>((java.util.Collection<T>) base.values());
			return result;
		}
	}
}
