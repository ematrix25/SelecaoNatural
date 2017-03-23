package sistema.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import componente.Componente;
import componente.Especime;
import componente.Especime.Especie;
import componente.Especime.Especie.Forma;

/**
 * Gerencia o Ambiente e suas Especies
 * 
 * @author Emanuel
 */
public class ControladorDoAmbiente {
	private Ambiente ambiente;

	/**
	 * Cria o objeto Controlador Do Ambiente
	 */
	public ControladorDoAmbiente() {
		ambiente = new Ambiente(400, 350);
	}

	/**
	 * Obtem o Ambiente
	 * 
	 * @return
	 */
	public Ambiente obterAmbiente() {
		return ambiente;
	}

	/**
	 * Gera temperatura aleatoria dada por uma temperatura base
	 * 
	 * @param ehSoma
	 * @param tempBase
	 * @return
	 */
	private int gerarTempAleatoria(boolean ehSoma, int tempBase) {
		final int aux = new Random().nextInt(tempBase / 10);
		if (ehSoma) return tempBase + aux;
		else return tempBase - aux;
	}

	/**
	 * Recria o Ambiente com temperatura menos quente
	 * 
	 * @param tempMax
	 * @param tempMin
	 */
	public void atualizarAmbiente(int tempMax, int tempMin) {
		ambiente.tempMax = gerarTempAleatoria(false, ambiente.tempMax);
		ambiente.tempMin = gerarTempAleatoria(false, ambiente.tempMin);
	}

	/**
	 * Atualiza a temperatura do Ambiente
	 * 
	 * @param ehSoma
	 * @param temp
	 * @return
	 */
	public boolean atualizarTemp(boolean ehSoma, int temp) {
		int tempAux = ambiente.temp;
		if (ehSoma) tempAux += temp;
		else tempAux -= temp;
		if (tempAux > ambiente.tempMax || tempAux < ambiente.tempMin) return false;
		ambiente.temp = tempAux;
		return true;
	}

	/**
	 * Armazena os dados da Especie
	 * 
	 * @param especie
	 * @param ID
	 */
	private boolean guardarEspecie(int especie, int ID) {
		List<Integer> especimes = new ArrayList<Integer>();
		especimes.add(ID);
		if (ambiente.especies.containsKey(especie)) return false;
		ambiente.especies.put(especie, especimes);
		return true;
	}

	/**
	 * Cria uma Especie aleatoria
	 * 
	 * @param ID
	 * @param tempMax
	 * @param tempMin
	 * @return
	 */
	private Especie criarEspecie(int ID, int tempMax, int tempMin) {
		Especie especie = null;
		while (true) {
			especie = new Especie(gerarTempAleatoria(true, tempMax), gerarTempAleatoria(false, tempMin));
			if (guardarEspecie(especie.obterCodigo(), ID)) break;
		}
		return especie;
	}

	/**
	 * Cria uma Especie com Forma
	 * 
	 * @param ID
	 * @param forma
	 * @param tempMax
	 * @param tempMin
	 * @return
	 */
	private Especie criarEspecie(int ID, Forma forma, int tempMax, int tempMin) {
		Especie especie = new Especie(forma, gerarTempAleatoria(true, tempMax), gerarTempAleatoria(false, tempMin));
		guardarEspecie(especie.obterCodigo(), ID);
		return especie;
	}

	/**
	 * Cria as Especies do Ambiente
	 * 
	 * @param IDs
	 * @return
	 */
	public Especie[] criarEspecies(int[] IDs) {
		Especie especies[] = new Especie[7];
		especies[0] = criarEspecie(IDs[0], Forma.Coccus, ambiente.tempMax, ambiente.tempMin);
		for (int i = 1; i <= 3; i++) {
			especies[i * 2 - 1] = criarEspecie(IDs[i * 2 - 1], Forma.Bacillus, ambiente.tempMax, ambiente.tempMin);
			especies[i * 2] = criarEspecie(IDs[i * 2], ambiente.tempMax, ambiente.tempMin);
		}
		return especies;
	}

	/**
	 * Obtem a Especie pela ID de um Especime
	 * 
	 * @param ID
	 * @return
	 */
	public int obterEspecie(int ID) {
		for (int especie : ambiente.especies.keySet()) {
			for (int especime : ambiente.especies.get(especie)) {
				if (ID == especime) return especie;
			}
		}
		return -1;
	}

	/**
	 * Atualiza a Especie com adição ou remoção de Especimes
	 * 
	 * @param ID
	 * @param ehAdicao
	 * @param especie
	 * @return
	 */
	public void atualizarEspecie(int ID, boolean ehAdicao, int especie) {
		List<Integer> especimes = ambiente.especies.get(especie);
		if (ehAdicao) {
			especimes.add(ID);
			ambiente.especies.replace(especie, especimes);
		} else {
			especimes.remove(ID);
		}
		ambiente.especies.replace(especie, especimes);
	}

	/**
	 * Remove a Especie e seus especimes
	 * 
	 * @param especie
	 * @return
	 */
	public void removerEspecie(int especie) {
		ambiente.especies.remove(especie);
	}

	/**
	 * Ambiente do jogo onde as Especies vão habitar
	 * 
	 * @author Emanuel
	 */
	public class Ambiente {
		private int tempMax, temp, tempMin;
		private HashMap<Integer, List<Integer>> especies;

		/**
		 * Gera o objeto Ambiente com suas temperaturas
		 * 
		 * @param tempMax
		 * @param tempMin
		 */
		public Ambiente(int tempMax, int tempMin) {
			this.tempMax = tempMax;
			this.temp = (tempMax + tempMin) / 2;
			this.tempMin = tempMin;
			this.especies = new HashMap<Integer, List<Integer>>();
		}

		/**
		 * Obtem a Temperatura do Ambiente
		 */
		public int obterTemp() {
			return temp;
		}
	}

	/**
	 * Metodo principal para testar o Controlador do Ambiente
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ControladorDaEntidade controladorDaEntidade = new ControladorDaEntidade();
		ControladorDoAmbiente controladorDoAmbiente = new ControladorDoAmbiente();
		System.out.println("Temperatura ambiente é " + controladorDoAmbiente.obterAmbiente().obterTemp() + "K");

		// Popular Ambiente
		int entidades[] = new int[7];
		for (int i = 0; i < 7; i++) {
			entidades[i] = controladorDaEntidade.criarEntidade();
		}
		Especie[] especies = controladorDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < 7; i++) {
			controladorDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
			System.out.println("Especie " + (i + 1) + ": " + especies[i]);
		}

		// Atualizar Temperatura
		if (controladorDoAmbiente.atualizarTemp(true, 20))
			System.out.println("Temperatura ambiente é " + controladorDoAmbiente.obterAmbiente().obterTemp() + "K");

		// Atualizar População - Adicionar Especime
		int entidade = controladorDaEntidade.criarEntidade();
		controladorDaEntidade.adicionarComponente(entidade, new Especime(especies[5]));
		controladorDoAmbiente.atualizarEspecie(entidade, true, especies[5].obterCodigo());

		// Atualizar População - Remover Especime
		entidade = controladorDaEntidade.obterEntidadeComOComponente(new Especime(especies[5]));
		controladorDaEntidade.removerEntidade(entidade);
		controladorDoAmbiente.atualizarEspecie(entidade, false, especies[5].obterCodigo());
		
		// Remover Especie
		for (Integer ID : controladorDoAmbiente.obterAmbiente().especies.get(especies[5])) {
			controladorDaEntidade.removerEntidade(ID);
		}
		controladorDoAmbiente.removerEspecie(especies[5].obterCodigo());
	}
}
