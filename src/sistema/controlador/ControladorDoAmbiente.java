package sistema.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
	 * @return Ambiente
	 */
	public Ambiente obterAmbiente() {
		return ambiente;
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
	 * Gera temperatura aleatoria dada por uma temperatura base
	 * 
	 * @param ehSoma
	 * @param tempBase
	 * @return int
	 */
	private int gerarTempAleatoria(boolean ehSoma, int tempBase) {
		final int aux = new Random().nextInt(tempBase / 10);
		if (ehSoma) return tempBase + aux;
		else return tempBase - aux;
	}

	/**
	 * Atualiza a temperatura do Ambiente
	 * 
	 * @param ehSoma
	 * @param temp
	 * @return boolean
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
	 * Cria uma Especie do Ambiente
	 * 
	 * @param ID
	 * @return Especie
	 */
	public Especie criarEspecie(int ID, Forma forma) {
		if (forma != null)
			return criarEspecie(ID, forma, ambiente.tempMax, ambiente.tempMin);
		else
			return criarEspecie(ID, ambiente.tempMax, ambiente.tempMin);
	}

	/**
	 * Cria as Especies do Ambiente
	 * 
	 * @param IDs
	 * @return Especie[]
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
	 * Cria uma Especie aleatoria
	 * 
	 * @param ID
	 * @param tempMax
	 * @param tempMin
	 * @return Especie
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
	 * @return Especie
	 */
	private Especie criarEspecie(int ID, Forma forma, int tempMax, int tempMin) {
		Especie especie = new Especie(forma, gerarTempAleatoria(true, tempMax), gerarTempAleatoria(false, tempMin));
		guardarEspecie(especie.obterCodigo(), ID);
		return especie;
	}

	/**
	 * Armazena os dados da Especie
	 * 
	 * @param especie
	 * @param ID
	 * @return boolean
	 */
	private boolean guardarEspecie(int especie, int ID) {
		List<Integer> especimes = new ArrayList<Integer>();
		especimes.add(ID);
		if (ambiente.especies.containsKey(especie)) return false;
		ambiente.especies.put(especie, especimes);
		return true;
	}

	/**
	 * Obte a Especie do Especime do Ambiente
	 * 
	 * @param especime
	 * @return Integer
	 */
	public Integer obterEspecie(int especime) {
		for (Integer especie : ambiente.especies.keySet()) {
			if (ambiente.especies.get(especie).contains(especime)) return especie;
		}
		return null;
	}

	/**
	 * Obtem os Especimes da Especie do Ambiente por um Especime
	 * 
	 * @param especime
	 * @return Lista de Integer
	 */
	public List<Integer> obterEspecimesPorEspecime(int especime) {
		return ambiente.especies.get(ambiente.obterEspecieID(especime));
	}

	/**
	 * Obtem os Especimes da Especie do Ambiente por Especie
	 * 
	 * @param especie
	 * @return Lista de Integer
	 */
	public List<Integer> obterEspecimesPorEspecie(int especie) {
		return ambiente.especies.get(especie);
	}

	/**
	 * Atualiza a Especie com adição ou remoção de Especimes
	 * 
	 * @param ID
	 * @param ehAdicao
	 * @param especie
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
			this.tempMin = tempMin;
			this.temp = (tempMax + tempMin) / 2;
			this.especies = new HashMap<Integer, List<Integer>>();
		}

		/**
		 * Obtem a Temperatura Máxima do Ambiente
		 * 
		 * @return int
		 */
		public int obterTempMax() {
			return tempMax;
		}

		/**
		 * Obtem a Temperatura Mínima do Ambiente
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
		 * @return Integer
		 */
		public Integer obterEspecieID(int especime) {
			for (Integer especie : especies.keySet()) {
				if (especies.get(especie).get(0) == especime) return especie;
			}
			return null;
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
}
