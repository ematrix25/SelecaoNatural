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
	 * Obtem os Especimes da Especie do Ambiente
	 * 
	 * @param especie
	 * @return
	 */
	public List<Integer> obterEspecie(int especie) {
		return ambiente.especies.get(especie);
	}

	/**
	 * Atualiza a Especie com adi��o ou remo��o de Especimes
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
			especimes.remove(new Integer(ID));
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
	 * Ambiente do jogo onde as Especies v�o habitar
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
			this.tempMin = tempMin;
			this.temp = (tempMax + tempMin) / 2;
			this.especies = new HashMap<Integer, List<Integer>>();
		}

		/**
		 * Obtem a Temperatura M�xima do Ambiente
		 * 
		 * @return int
		 */
		public int obterTempMax() {
			return tempMax;
		}

		/**
		 * Obtem a Temperatura M�nima do Ambiente
		 * 
		 * @return int
		 */
		public int obterTempMin() {
			return tempMin;
		}

		/**
		 * Obtem a Temperatura do Ambiente
		 * 
		 * @return int
		 */
		public int obterTemp() {
			return temp;
		}

		/**
		 * Obtem a IDs da Especie
		 * 
		 * @param especime
		 * @return int
		 */
		public int obterEspecieID(int especime) {
			for (Integer especie : especies.keySet()) {
				if (especies.get(especie).get(0) == especime) return especie;
			}
			return -1;
		}

		/**
		 * Obtem a QTD de Especies
		 * 
		 * @return int
		 */
		public int obterQTD() {
			return especies.size();
		}

		/**
		 * Transforma em texto os dados do Ambiente
		 * 
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return "[" + tempMax + ", " + tempMin + "]";
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
		System.out.println("Ambiente:\n" + controladorDoAmbiente.obterAmbiente());
		System.out.println();

		// Atualizar Ambiente
		controladorDoAmbiente.atualizarAmbiente(450, 400);
		System.out.println("Ambiente atualizado:\n" + controladorDoAmbiente.obterAmbiente());
		System.out.println();

		// Atualizar Temperatura
		if (controladorDoAmbiente.atualizarTemp(true, 20))
			System.out.println("Temperatura ambiente � " + controladorDoAmbiente.obterAmbiente().obterTemp() + "K");
		else System.out.println("Temperatura n�o foi atualizada");
		System.out.println();

		// Popular Ambiente
		int entidades[] = new int[7];
		for (int i = 0; i < 7; i++) {
			entidades[i] = controladorDaEntidade.criarEntidade();
		}
		Especie[] especies = controladorDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < 7; i++) {
			controladorDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
		}
		System.out.println("Ambiente populado:\n" + controladorDoAmbiente.obterAmbiente().especies);
		System.out.println();

		// Atualizar Popula��o - Adicionar Especime
		System.out.println("Adicionando o Especime 8");
		int entidade = controladorDaEntidade.criarEntidade();
		controladorDaEntidade.adicionarComponente(entidade, new Especime(especies[5]));
		controladorDoAmbiente.atualizarEspecie(entidade, true, especies[5].obterCodigo());
		System.out.println(controladorDoAmbiente.obterAmbiente().especies);
		System.out.println();

		// Atualizar Popula��o - Remover Especime
		System.out.println("Removendo o Especime 6");
		entidade = controladorDaEntidade.obterEntidadeComOComponente(new Especime(especies[5]));
		controladorDaEntidade.removerEntidade(entidade);
		controladorDoAmbiente.atualizarEspecie(entidade, false, especies[5].obterCodigo());
		System.out.println(controladorDoAmbiente.obterAmbiente().especies);
		System.out.println();

		// Remover Especie
		System.out.println("Removendo a Especie do Especime 6");
		for (Integer ID : controladorDoAmbiente.obterEspecie(especies[5].obterCodigo())) {
			controladorDaEntidade.removerEntidade(ID);
		}
		controladorDoAmbiente.removerEspecie(especies[5].obterCodigo());
		System.out.println(controladorDoAmbiente.obterAmbiente().especies);
		System.out.println();
	}
}
