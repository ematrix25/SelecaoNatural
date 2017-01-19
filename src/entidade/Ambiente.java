package entidade;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Emanuel
 *
 */
public class Ambiente {
	private int tempMax, tempMin;
	private List<Especie> especies;

	public Ambiente(int tempMax, int tempMin) {
		this.tempMax = tempMax;
		this.tempMin = tempMin;
		this.especies = new ArrayList<Especie>();
	}

	public int getTempMax() {
		return tempMax;
	}

	public void setTempMax(int tempMax) {
		this.tempMax = tempMax;
	}

	public int getTempMin() {
		return tempMin;
	}

	public void setTempMin(int tempMin) {
		this.tempMin = tempMin;
	}

	public List<Especie> getEspecies() {
		return especies;
	}

	public boolean addEspecies(Especie especie) {
		return especies.add(especie);
	}

	public boolean remEspecie(Especie especie) {
		return especies.remove(especie);
	}
}
