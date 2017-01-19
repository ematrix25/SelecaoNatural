package controlador;

import entidade.Ambiente;

/**
 * @author Emanuel
 *
 */
public class ControladorDoJogo {
	private Ambiente ambiente;

	public boolean criarAmbiente(int tempMax, int tempMin) {
		ambiente = new Ambiente(tempMax, tempMin);
		for (int i = 0; i < 3; i++) {
			
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

	public boolean criarEspecie() {
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
