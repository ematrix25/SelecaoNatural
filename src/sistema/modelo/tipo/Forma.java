package sistema.modelo.tipo;

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
