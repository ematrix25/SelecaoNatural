package sistema.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import componente.Componente;
import componente.Especime;
import componente.Especime.Especie;
import sistema.utilitario.Aleatorio;

/**
 * Gerencia o Ambiente e suas Especies
 * 
 * @author Emanuel
 */
public class ControladorDoAmbiente {
	private ControladorDaEntidade controladorDaEntidade;
	private Ambiente ambiente;

	public ControladorDoAmbiente(ControladorDaEntidade controladorDaEntidade) {
		this.controladorDaEntidade = controladorDaEntidade;
		criarAmbiente();
	}

	/**
	 * Cria o Ambiente inicial com temperatura de 400K até 350K
	 * 
	 * @return
	 */
	public void criarAmbiente() {
		ambiente = new Ambiente(400, 350);
		for (int i = 0; i < 3; i++) {
			criarEspecie(ambiente.tempMax + 50, ambiente.tempMin - 50);
		}
	}

	public Ambiente lerAmbiente() {
		return ambiente;
	}

	public boolean atualizarAmbiente(int tempMax, int tempMin) {
		ambiente.tempMax = tempMax;
		ambiente.tempMin = tempMin;
		return true;
	}

	public boolean criarEspecie(int maxTemp, int minTemp) {
		int[] temps = Aleatorio.escolherTemps(maxTemp, minTemp);
		int ID = controladorDaEntidade.criarEntidade();
		Especie especie = new Especie(temps[0], temps[1]);
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Especime(especie));
		List<Integer> especimes = new ArrayList<Integer>();
		especimes.add(ID);
		ambiente.especies.put(especie.obterCodigo(), especimes);
		return false;
	}

	public boolean lerEspecie() {
		// TODO Terminar de Implementar
		return false;
	}

	public boolean atualizarEspecie() {
		// TODO Terminar de Implementar
		return false;
	}

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
		public int tempMax, tempMin;
		public HashMap<Integer, List<Integer>> especies;

		/**
		 * Gera o objeto Ambiente com suas temperaturas
		 * 
		 * @param tempMax
		 * @param tempMin
		 */
		public Ambiente(int tempMax, int tempMin) {
			this.tempMax = tempMax;
			this.tempMin = tempMin;
			this.especies = new HashMap<Integer, List<Integer>>();
		}
	}
}
