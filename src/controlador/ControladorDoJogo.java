package controlador;

import entidade.Ambiente;
import entidade.Especie;
import utilitarios.Aleatorio;

/**
 * @author Emanuel
 *
 */
public class ControladorDoJogo {
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
		if (ambiente.getTempMax() != tempMax || ambiente.getTempMin() != tempMin) {
			ambiente.setTempMax(tempMax);
			ambiente.setTempMin(tempMin);
			return true;
		}
		return false;
	}

	public boolean criarEspecie(int maxTemp, int minTemp) {
		int[] temps = Aleatorio.escolherTemps(maxTemp, minTemp);
		ambiente.addEspecies(new Especie(Aleatorio.escolherTipo(), temps[0], temps[1]));
		return false;
	}

	public boolean lerEspecie() {
		return false;
	}

	public boolean atualizarEspecie() {
		return false;
	}

	public boolean removerEspecie() {
		return false;
	}
}
