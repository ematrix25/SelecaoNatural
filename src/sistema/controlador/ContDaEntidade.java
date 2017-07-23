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
	public List<Integer> entidades;
	public HashMap<Class<?>, HashMap<Integer, ? extends Componente>> baseDeComponentes;

	/**
	 * Cria o objeto para controlar Entidades
	 */
	public ContDaEntidade() {
		entidades = new LinkedList<Integer>();
		baseDeComponentes = new HashMap<Class<?>, HashMap<Integer, ? extends Componente>>();
	}

	/**
	 * Cria uma Entidade de ID �nica
	 * 
	 * @return int
	 */
	public int criarEntidade() {
		int novaID;
		// Previne a cria��o ao mesmo tempo de duas Entidades com a mesma ID
		synchronized (this) {
			if (menorIDNaoAssociada < Integer.MAX_VALUE) {
				novaID = menorIDNaoAssociada;
				int ID = novaID;
				for (ID += 1; ID <= Integer.MAX_VALUE; ID++) {
					if (!entidades.contains(ID)) break;
				}
				menorIDNaoAssociada = ID;
			} else throw new Error("ERRO: N�o � possivel criar Entidade porque n�o h� mais IDs dispon�veis");
		}
		entidades.add(novaID);
		return novaID;
	}

	/**
	 * Remove os registros de tipos de Componentes vazios da Base de Componentes
	 */
	private void limparBaseDeComponentes() {
		for (Class<?> Componente : baseDeComponentes.keySet()) {
			baseDeComponentes.remove(Componente);
		}
	}

	/**
	 * Remove uma Entidade com parametro para remover a chave e n�o o �ndice
	 * 
	 * @param ID
	 * @return boolean
	 */
	public boolean removerEntidade(Integer ID) {
		// Previne a remo��o ao mesmo tempo da mesma Entidade
		synchronized (this) {
			entidades.remove(ID);
			for (HashMap<Integer, ? extends Componente> base : baseDeComponentes.values()) {
				base.remove(ID);
				limparBaseDeComponentes();
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
	 * Obtem a Entidade dado um Componente
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
	 * Obtem todas as Entidades do tipo de Componente
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
	 * Obtem o Componente da Entidade da ID
	 * 
	 * @param ID
	 * @param tipoDeComponente
	 * @return T
	 */
	public <T extends Componente> T obterComponente(int ID, Class<T> tipoDeComponente) {
		if (!entidades.contains(ID)) return null;
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
		if (base == null) throw new IllegalArgumentException(
				"ERRO: N�o existem Entidades com essa classe de Componente: " + tipoDeComponente);
		T componente = tipoDeComponente.cast(base.get(ID));
		if (componente == null)
			throw new IllegalArgumentException("ERRO: " + ID + " n�o possui Componente da classe: " + tipoDeComponente);
		return componente;
	}

	/**
	 * Obtem todos os Componentes da Entidade da ID
	 * 
	 * @param ID
	 * @return List<T>
	 */
	@SuppressWarnings("unchecked")
	public <T extends Componente> List<T> obterComponentes(int ID) {
		if (!entidades.contains(ID)) return null;
		List<T> componentes = new ArrayList<T>();
		for (Class<?> tipoDeComponente : baseDeComponentes.keySet()) {
			HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
			if (base == null) continue;
			T componente = (T) tipoDeComponente.cast(base.get(ID));
			if (componente == null) throw new IllegalArgumentException(
					"ERRO: " + ID + " n�o possui Componente da classe: " + tipoDeComponente);
			componentes.add(componente);
		}
		return componentes;
	}

	/**
	 * Obtem todos os Componentes do tipo de Componente
	 * 
	 * @param tipoDeComponente
	 * @return List<T>
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