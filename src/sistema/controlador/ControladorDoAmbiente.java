package sistema.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sistema.modelo.Ambiente;
import sistema.utilitario.Aleatorio;

/**
 * @author Emanuel
 *
 */
public class ControladorDoAmbiente {
	private Ambiente ambiente;

	// Cria ambiente com temperatura de 400K até 350K
	public boolean criarAmbiente() {
		ambiente = new Ambiente(400, 350);
		for (int i = 0; i < 3; i++) {
			criarEspecie(450, 300);
		}
		return ambiente != null;
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
		// TODO Terminar de Implementar
		int[] temps = Aleatorio.escolherTemps(maxTemp, minTemp);
		ambiente.addEspecies(new ControladorDaEspecie(Aleatorio.escolherTipo(), temps[0], temps[1]));
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
	 * @author Emanuel
	 *
	 */
	public class Ambiente {
		public int tempMax, tempMin;
		public List<HashMap<Integer, List<Integer>>> especies;

		public Ambiente(int tempMax, int tempMin) {
			this.tempMax = tempMax;
			this.tempMin = tempMin;
			this.especies = new ArrayList<HashMap<Integer, List<Integer>>>();
		}
	}
}
