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
	private ControladorDaEntidade controladorDaEntidade;
	private Ambiente ambiente;

	/**
	 * Cria o objeto Controlador Do Ambiente
	 * 
	 * @param controladorDaEntidade
	 */
	public ControladorDoAmbiente(ControladorDaEntidade controladorDaEntidade) {
		this.controladorDaEntidade = controladorDaEntidade;
		ambiente = new Ambiente(400, 350);
		criarEspecies();
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
	 * Cria as Especies do Ambiente
	 */
	private void criarEspecies() {
		criarEspecie(Forma.Coccus, gerarTempAleatoria(true, ambiente.tempMax),
				gerarTempAleatoria(false, ambiente.tempMin));
		for (int i = 0; i < 3; i++) {
			criarEspecie(Forma.Bacillus, gerarTempAleatoria(true, ambiente.tempMax),
					gerarTempAleatoria(false, ambiente.tempMin));
			criarEspecie(gerarTempAleatoria(true, ambiente.tempMax), gerarTempAleatoria(false, ambiente.tempMin));
		}
	}

	/**
	 * Recria o Ambiente com temperatura menos quente
	 * 
	 * @param tempMax
	 * @param tempMin
	 */
	public void recriarAmbiente(int tempMax, int tempMin) {
		ambiente.tempMax = gerarTempAleatoria(false, ambiente.tempMax);
		ambiente.tempMin = gerarTempAleatoria(false, ambiente.tempMin);
		criarEspecies();
	}

	/**
	 * Le o Ambiente
	 * 
	 * @return
	 */
	public Ambiente lerAmbiente() {
		return ambiente;
	}

	/**
	 * Atualiza a temperatura do Ambiente
	 * 
	 * @param temp
	 * @return
	 */
	public void atualizarAmbiente(int temp) {
		ambiente.temp = temp;
	}

	/**
	 * Armazena os dados da Especie
	 * 
	 * @param ID
	 * @param especie
	 */
	private void guardarEspecie(int ID, Especie especie) {
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Especime(especie));
		List<Integer> especimes = new ArrayList<Integer>();
		especimes.add(ID);
		ambiente.especies.put(especie.obterCodigo(), especimes);
	}

	/**
	 * Cria uma Especie aleatoria
	 * 
	 * @param maxTemp
	 * @param minTemp
	 * @return
	 */
	public void criarEspecie(int maxTemp, int minTemp) {
		guardarEspecie(controladorDaEntidade.criarEntidade(), new Especie(maxTemp, minTemp));
	}

	/**
	 * Cria uma Especie com Forma
	 * 
	 * @param forma
	 * @param maxTemp
	 * @param minTemp
	 * @return
	 */
	public void criarEspecie(Forma forma, int maxTemp, int minTemp) {
		guardarEspecie(controladorDaEntidade.criarEntidade(), new Especie(forma, maxTemp, minTemp));
	}

	/**
	 * Le a Especie
	 * 
	 * @return
	 */
	public boolean lerEspecie() {
		// TODO Terminar de Implementar
		return false;
	}

	/**
	 * Atualiza a Especie
	 * 
	 * @return
	 */
	public boolean atualizarEspecie() {
		// TODO Terminar de Implementar
		return false;
	}

	/**
	 * Remove a Especie
	 * 
	 * @return
	 */
	public boolean removerEspecie() {
		// TODO Terminar de Implementar
		return false;
	}

	/**
	 * Ambiente do jogo onde as Especies vão habitar
	 * 
	 * @author Emanuel
	 */
	public class Ambiente {
		public int tempMax, temp, tempMin;
		public HashMap<Integer, List<Integer>> especies;

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
	}
}
