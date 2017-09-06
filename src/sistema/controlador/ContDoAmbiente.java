package sistema.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import componente.Especime.Especie;
import componente.Especime.Especie.Forma;

/**
 * Gerencia o Ambiente e suas Esp�cies
 * 
 * @author Emanuel
 */
public class ContDoAmbiente {
	public Ambiente ambiente;
	public int dificuldade;
	public List<Integer> idsParaRemocao;

	/**
	 * Cria o objeto Controlador Do Ambiente
	 */
	public ContDoAmbiente() {
		ambiente = new Ambiente(400, 350);
		dificuldade = 0;
		idsParaRemocao = new ArrayList<Integer>();
	}

	/**
	 * Recria o Ambiente com temperatura menos quente
	 * 
	 * @param temp
	 */
	public void atualizarAmbiente(int temp) {
		ambiente.tempMax = gerarTempAleatoria(false, ambiente.tempMax - temp);
		ambiente.tempMin = gerarTempAleatoria(false, ambiente.tempMin - temp);
	}

	/**
	 * Gera temperatura aleatoria dada por uma temperatura base
	 * 
	 * @param ehSoma
	 * @param tempBase
	 * @return int
	 */
	private int gerarTempAleatoria(boolean ehSoma, int tempBase) {
		int aux = new Random().nextInt(tempBase / 10);
		if (ehSoma) return tempBase + aux;
		else return tempBase - aux;
	}

	/**
	 * Atualiza a temperatura do Ambiente aleatoriamente
	 */
	public void atualizarTemp() {
		int aux = new Random().nextInt(10);
		if (new Random().nextBoolean()) atualizarTemp(aux);
		else atualizarTemp(-aux);
	}

	/**
	 * Atualiza a temperatura do Ambiente
	 * 
	 * @param temp
	 */
	public void atualizarTemp(int temp) {
		int tempAux = ambiente.temp;
		tempAux += temp;
		if (tempAux > ambiente.tempMax) tempAux = ambiente.tempMax;
		if (tempAux < ambiente.tempMin) tempAux = ambiente.tempMin;
		ambiente.temp = tempAux;
	}

	/**
	 * Cria uma Esp�cie do Ambiente
	 * 
	 * @param ID
	 * @return Especie
	 */
	public Especie criarEspecie(int ID, Forma forma) {
		if (forma != null) return criarEspecie(ID, forma, ambiente.tempMax, ambiente.tempMin);
		else return criarEspecie(ID, ambiente.tempMax, ambiente.tempMin);
	}

	/**
	 * Cria as Esp�cies do Ambiente
	 * 
	 * @param IDs
	 * @return Especie[]
	 */
	public Especie[] criarEspecies(int[] IDs) {
		int tam = IDs.length, valor;
		Especie especies[] = new Especie[tam];
		if (tam > 2) {
			especies[0] = criarEspecie(IDs[0], Forma.Coccus, ambiente.tempMax, ambiente.tempMin);
			for (int i = 1; i <= tam / 2; i++) {
				especies[i * 2 - 1] = criarEspecie(IDs[i * 2 - 1], Forma.Bacillus, ambiente.tempMax, ambiente.tempMin);
				especies[i * 2] = criarEspecie(IDs[i * 2], ambiente.tempMax, ambiente.tempMin);
			}
		} else {
			valor = new Random().nextInt(10);
			especies[0] = criarEspecie(IDs[0], Forma.Bacillus, ambiente.tempMax - valor * dificuldade,
					ambiente.tempMin + valor * dificuldade / 2);
			especies[1] = criarEspecie(IDs[1], Forma.Bacillus, ambiente.tempMax - valor * dificuldade / 3,
					ambiente.tempMin + valor * dificuldade / 4);
		}
		return especies;
	}

	/**
	 * Cria uma Esp�cie com Forma
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
	 * Cria uma Esp�cie aleatoria
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
	 * Armazena os dados da Esp�cie
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
	 * Obt�m a Esp�cie do Especime do Ambiente
	 * 
	 * @param especime
	 * @return Integer
	 */
	public boolean temEspecie(int especime) {
		for (Integer especie : ambiente.especies.keySet()) {
			if (ambiente.especies.get(especie).contains(especime)) return true;
		}
		return false;
	}

	/**
	 * Obt�m a Esp�cie do Especime do Ambiente
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
	 * Obt�m os Especimes da Esp�cie do Ambiente por um Especime
	 * 
	 * @param especime
	 * @return Lista de Integer
	 */
	public List<Integer> obterEspecimesPorEspecime(int especime) {
		return ambiente.especies.get(ambiente.obterEspecieID(especime));
	}

	/**
	 * Obt�m os Especimes da Esp�cie do Ambiente por Esp�cie
	 * 
	 * @param especie
	 * @return Lista de Integer
	 */
	public List<Integer> obterEspecimesPorEspecie(int especie) {
		return ambiente.especies.get(especie);
	}

	/**
	 * Atualiza a Esp�cie com adi��o ou remo��o de Especimes
	 * 
	 * @param ID
	 * @param ehAdicao
	 * @param especie
	 */
	public void atualizarEspecie(int ID, boolean ehAdicao, int especie) {
		List<Integer> especimes = ambiente.especies.get(especie);
		if (especimes == null) {
			especimes = new ArrayList<Integer>();
			especimes.add(ID);
			ambiente.especies.put(especie, especimes);
		} else {
			if (ehAdicao) {
				especimes.add(ID);
				ambiente.especies.replace(especie, especimes);
			} else {
				especimes.remove(new Integer(ID));
				if (especimes.isEmpty()) ambiente.especies.remove(especie);
				else ambiente.especies.replace(especie, especimes);
			}
		}
	}

	/**
	 * Remove os esp�cimes para reconfigurar a esp�cie
	 *
	 * @param jogador
	 */
	public void removerEspecimesExtra(int jogador) {
		List<Integer> elementos;
		int id, qtd, i;
		for (int especie : ambiente.especies.keySet()) {
			elementos = ambiente.especies.get(especie);
			qtd = elementos.size() - 1;
			i = 0;
			while (qtd > 0) {
				id = elementos.get(i++);
				if (id != jogador) {
					idsParaRemocao.add(id);
					qtd--;
				}
			}
		}
	}

	/**
	 * Remove a Esp�cie e seus especimes
	 * 
	 * @param especie
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
		 * Obt�m a Temperatura M�xima do Ambiente
		 * 
		 * @return int
		 */
		public int obterTempMax() {
			return tempMax;
		}

		/**
		 * Obt�m a Temperatura M�nima do Ambiente
		 * 
		 * @return int
		 */
		public int obterTempMin() {
			return tempMin;
		}

		/**
		 * Obt�m a Temperatura do Ambiente
		 * 
		 * @return int
		 */
		public int obterTemp() {
			return temp;
		}

		/**
		 * Obt�m a IDs da Esp�cie
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
		 * Obt�m a QTD de Esp�cies
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
