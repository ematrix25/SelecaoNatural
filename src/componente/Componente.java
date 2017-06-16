package componente;

import sistema.interface_grafica.renderizador.base_do_jogo.Sprite;
import sistema.interface_grafica.renderizador.base_do_jogo.mapa.Coordenada;

/**
 * Abstrai classes de componentes que vão adicionar funcionalidades à entidade
 * 
 * @author Emanuel
 */
public abstract class Componente {

	/**
	 * Componente que indica a Posicao da Entidade
	 * 
	 * @author Emanuel
	 */
	public static class Posicao extends Componente {
		public int x, y;

		/**
		 * Gera o objeto Posicao
		 */
		public Posicao() {
			this(0, 0);
		}

		/**
		 * Gera o objeto Posicao com uma Coordenada
		 * 
		 * @param coordenada
		 */
		public Posicao(Coordenada coordenada) {
			this(coordenada.obterX(), coordenada.obterY());
		}

		/**
		 * Gera o objeto Posicao com x e y
		 * 
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
			return "Posicao = [" + x + ", " + y + "]";
		}
	}

	/**
	 * Componente que indica a Velocidade da Entidade
	 * 
	 * @author Emanuel
	 */
	public static class Velocidade extends Componente {
		public boolean movendo;
		public int valor, direcao;

		/**
		 * Gera o objeto Velocidade
		 */
		public Velocidade() {
			this(false, 0, -1);
		}

		/**
		 * Gera o objeto Velocidade com valores de movendo e direcao de
		 * movimento
		 * 
		 * @param movendo
		 * @param direcao
		 */
		public Velocidade(boolean movendo, int velocidade, int direcao) {
			this.movendo = movendo;
			this.valor = velocidade;
			this.direcao = direcao;
		}

		/**
		 * Gera o texto com os dados da Velocidade
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			if(movendo)return "Velocidade = " + valor + " e Direcao = " + direcao;
			else return "Velocidade = 0 e Direcao = " + direcao;
		}
	}

	/**
	 * Componente com os Sprites da Entidade
	 * 
	 * @author Emanuel
	 */
	public static class Sprites extends Componente {
		private Sprite sprites[];

		/**
		 * Cria o objeto Sprites com os sprites
		 * 
		 * @param sprites
		 */
		public Sprites(Sprite[] sprites) {
			this.sprites = sprites;
		}

		/**
		 * Obtem a sprite para o eixo X
		 * 
		 * @return Sprite
		 */
		public Sprite obterSpriteX(boolean movendo) {
			if (movendo) return sprites[1];
			else return sprites[3];
		}

		/**
		 * Obtem a sprite para o eixo Y
		 * 
		 * @return Sprite
		 */
		public Sprite obterSpriteY(boolean movendo) {
			if (movendo) return sprites[0];
			else return sprites[2];
		}
	}
}