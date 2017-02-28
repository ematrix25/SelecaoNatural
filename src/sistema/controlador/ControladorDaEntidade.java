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
 *
 */
public class ControladorDaEntidade {
	public int menorIDNaoAssociada = 1;
	public List<Integer> entidades;
	public HashMap<Class<?>, HashMap<Integer, ? extends Componente>> baseDeComponentes;

	/**
	 * Cria o objeto para controlar Entidades
	 */
	public ControladorDaEntidade() {
		entidades = new LinkedList<Integer>();
		baseDeComponentes = new HashMap<Class<?>, HashMap<Integer, ? extends Componente>>();
	}

	/**
	 * Cria uma Entidade de ID única
	 * 
	 * @return
	 */
	public int criarEntidade() {
		int novaID;
		// Previne a criação ao mesmo tempo de duas Entidades com a mesma ID
		synchronized (this) {
			if (menorIDNaoAssociada < Integer.MAX_VALUE) {
				novaID = menorIDNaoAssociada;
				int ID;
				for (ID = novaID; ID <= Integer.MAX_VALUE; ID++) {
					if (!entidades.contains(ID)) break;
				}
				menorIDNaoAssociada = ID;
			} else throw new Error("ERRO: Não é possivel criar Entidade porque não há mais IDs disponíveis");
		}
		entidades.add(novaID);
		return novaID;
	}

	/**
	 * Remove uma Entidade com parametro para remover a chave e não o índice
	 * 
	 * @param ID
	 * @return
	 */
	public boolean removerEntidade(Integer ID) {
		// Previne a remoção ao mesmo tempo da mesma Entidade
		synchronized (this) {
			entidades.remove(ID);
			for (HashMap<Integer, ? extends Componente> base : baseDeComponentes.values()) {
				base.remove(ID);
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
	 */
	@SuppressWarnings("unchecked")
	public <T extends Componente> boolean adicionarComponente(int ID, T componente) {
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(componente.getClass());
		if (base == null) {
			base = new HashMap<Integer, T>();
			baseDeComponentes.put(componente.getClass(), base);
		}
		return ((HashMap<Integer, T>) base).put(ID, componente) != null;
	}

	/**
	 * Obtem o Componente da Entidade da ID
	 * @param ID
	 * @param tipoDeComponente
	 * @return 
	 */
	public <T extends Componente> T obterComponente(int ID, Class<T> tipoDeComponente) {
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
		if (base == null) throw new IllegalArgumentException(
				"ERRO: Não existem Entidades com essa classe de Componente: " + tipoDeComponente);
		T resultado = tipoDeComponente.cast(base.get(ID));
		if (resultado == null)
			throw new IllegalArgumentException("ERRO: " + ID + " não possui Componente da classe: " + tipoDeComponente);
		return resultado;
	}
	
	/**
	 * Obtem todos os Componentes
	 * @param tipoDeComponente
	 * @return
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

	/**
	 * Obtem todas as Entidades de tipo de Componente
	 * @param tipoDeComponente
	 * @return
	 */
	public <T extends Componente> Set<Integer> obterTodasEntidadesComComponente(Class<T> tipoDeComponente) {
		HashMap<Integer, ? extends Componente> base = baseDeComponentes.get(tipoDeComponente);
		if (base == null) return new HashSet<Integer>();
		return base.keySet();
	}

	// TODO Terminar de implementar o Controlador
}
