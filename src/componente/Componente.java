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
		 * Gera o objeto Posicao com valores padrões de x e y
		 * @param x
		 * @param y
		 */
		public Posicao() {
			this.x = 0;
			this.y = 0;
		}
		
		/**
		 * Gera o objeto Posicao com x e y
		 * @param x
		 * @param y
		 */
		public Posicao(int x, int y) {
			this.x = x;
			this.y = y;
		}

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
