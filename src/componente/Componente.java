package componente;

import sistema.interface_grafica.renderizador.base_do_jogo.Sprite;

/**
 * Abstrai classes de componentes que v�o adicionar funcionalidades � entidade
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
			return "(" + x + "," + y + ")";
		}
	}

	/**
	 * Componente que indica a Velocidade da Entidade
	 * 
	 * @author Emanuel
	 */
	public static class Velocidade extends Componente {
		public boolean movendo;
		// Dire��o = 0 (cima) | 1 (direita) | 2 (baixo) | 3 (esquerda)
		public int velocidade, direcao;

		/**
		 * Gera o objeto Velocidade com valores de movendo e direcao de
		 * movimento
		 * 
		 * @param movendo
		 * @param direcao
		 */
		public Velocidade(boolean movendo, int velocidade, int direcao) {
			this.movendo = movendo;
			this.velocidade = velocidade;
			this.direcao = direcao;
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
			if(movendo) return sprites[1];
			else return sprites[3];
		}
		
		/**
		 * Obtem a sprite para o eixo Y
		 * 
		 * @return Sprite
		 */
		public Sprite obterSpriteY(boolean movendo) {
			if(movendo) return sprites[0];
			else return sprites[2];
		}
	}
}