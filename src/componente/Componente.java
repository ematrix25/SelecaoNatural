package componente;

import java.util.Arrays;

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
		 * Gera o código com os valores de x e y
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int primo = 31;
			int resultado = 1;
			resultado = primo * resultado + x;
			resultado = primo * resultado + y;
			return resultado;
		}

		/**
		 * Verifica a igualdade de duas Posições
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Posicao)) {
				return false;
			}
			Posicao other = (Posicao) obj;
			if (hashCode() != other.hashCode()) {
				return false;
			}
			return true;
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
		 * @param valor
		 * @param direcao
		 */
		public Velocidade(boolean movendo, int valor, int direcao) {
			this.movendo = movendo;
			this.valor = valor;
			this.direcao = direcao;
		}

		/**
		 * Gera o código com os valores da velocidade
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int primo = 31;
			int resultado = 1;
			resultado = primo * resultado + direcao;
			resultado = primo * resultado + (movendo ? 1231 : 1237);
			resultado = primo * resultado + valor;
			return resultado;
		}

		/**
		 * Verifica a igualdade de duas Velocidades
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Velocidade)) {
				return false;
			}
			Velocidade other = (Velocidade) obj;
			if (hashCode() != other.hashCode()) {
				return false;
			}
			return true;
		}

		/**
		 * Gera o texto com os dados da Velocidade
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			if (movendo) return "Velocidade = " + valor + " e Direcao = " + direcao;
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
		private int cor = 0xffffffff;
		private int contador = 0;

		/**
		 * Cria o objeto Sprites com os sprites
		 * 
		 * @param sprites
		 */
		public Sprites(Sprite[] sprites) {
			this.sprites = sprites;
		}

		/**
		 * Cria o objeto Sprites com os sprites e cor
		 * 
		 * @param sprites
		 */
		public Sprites(Sprite[] sprites, int cor) {
			this.sprites = sprites;
			this.cor = cor;
		}

		/**
		 * Obtem a sprite para o eixo X
		 * 
		 * @return Sprite
		 */
		public Sprite obterSpriteX(boolean movendo) {
			if (movendo) {
				if (!(contador < 99)) contador = 0;
				if (contador++ > 55) return sprites[3];
				else return sprites[1];
			} else {
				contador = 0;
				return sprites[3];
			}
		}

		/**
		 * Obtem a sprite para o eixo Y
		 * 
		 * @return Sprite
		 */
		public Sprite obterSpriteY(boolean movendo) {
			if (movendo) {
				if (!(contador < 99)) contador = 0;
				if (contador++ > 55) return sprites[2];
				return sprites[0];
			} else {
				contador = 0;
				return sprites[2];
			}
		}

		/**
		 * Obtem a cor
		 * 
		 * @return int
		 */
		public int obterCor() {
			return cor;
		}

		/**
		 * Gera o código dos Sprites
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(sprites);
			return result;
		}

		/**
		 * Verifica a igualdade dos objetos Sprites
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Sprites)) {
				return false;
			}
			Sprites other = (Sprites) obj;
			if (hashCode() != other.hashCode()) {
				return false;
			}
			return true;
		}

	}
}