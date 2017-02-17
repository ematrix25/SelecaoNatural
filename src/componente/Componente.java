package componente;

/**
 * Abstrai classes de componentes que vão adicionar funcionalidades à entidade
 * 
 * @author Emanuel
 */
public abstract class Componente {

	/**
	 * Componente que indica a posicao da entidade
	 * 
	 * @author Emanuel
	 */
	public static class Posicao extends Componente {
		public int x, y;

		/**
		 * Gera texto dos dados da Posicao
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
}
