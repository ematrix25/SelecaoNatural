package sistema.entidade.tipo;

/**
 * @author Emanuel
 *
 */
public class Tipo {
	private Forma forma;
	private Movimento movimento;
	private boolean ehAutotrofa;

	public Tipo(Forma forma, Movimento movimento, boolean ehAutotrofa) {
		this.forma = forma;
		this.movimento = movimento;
		this.ehAutotrofa = ehAutotrofa;
	}

	public Forma getForma() {
		return forma;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public boolean isEhAutotrofa() {
		return ehAutotrofa;
	}
}
