package entidade;

import java.util.ArrayList;
import java.util.List;

import entidade.tipo.Tipo;

/**
 * @author Emanuel
 */
public class Especie {
	private Tipo tipo;
	//Temperaturas Suportadas
	private int tempMaxSup, tempMinSup;
	private List<Especime> especimes;

	public Especie(Tipo tipo, int tempMaxSup, int tempMinSup) {
		this.tipo = tipo;
		this.tempMaxSup = tempMaxSup;
		this.tempMinSup = tempMinSup;
		this.especimes = new ArrayList<Especime>();
	}

	public Tipo getTipo() {
		return tipo;
	}

	public int getTempMaxSup() {
		return tempMaxSup;
	}

	public void setTempMaxSup(int tempMaxSup) {
		this.tempMaxSup = tempMaxSup;
	}

	public int getTempMinSup() {
		return tempMinSup;
	}

	public void setTempMinSup(int tempMinSup) {
		this.tempMinSup = tempMinSup;
	}

	public List<Especime> getEspecimes() {
		return especimes;
	}

	public boolean addEspecime(Especime especime) {
		return especimes.add(especime);
	}
	
	public boolean remEspecime(Especime especime) {
		return especimes.remove(especime);
	}
}