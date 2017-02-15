package sistema.entidade.tipo;

/**
 * @author Emanuel
 *
 */
public enum Forma {
	Coccus, Bacillus, Spiral;

	public static Forma escolhaAleatoria() {
		return values()[(int) (Math.random() * values().length)];
	}
}
