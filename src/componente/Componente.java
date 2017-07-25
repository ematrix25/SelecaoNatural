package componente;

import java.util.Random;

import componente.Componente.Velocidade.Direcao;
import componente.Especime.Especie.Forma;
import sistema.igu.renderizador.jogo.base.Sprite;
import sistema.igu.renderizador.jogo.base.SpritesAnimados;
import sistema.igu.renderizador.jogo.base.mapa.Coordenada;

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
		 * Gera o objeto Posicao dada uma posicao
		 * 
		 * @param posicao
		 */
		public Posicao(Posicao posicao) {
			this(posicao.x, posicao.y);
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
		public int valor;
		public Direcao direcao;

		/**
		 * Gera o objeto Velocidade
		 */
		public Velocidade() {
			this(0, null);
		}

		/**
		 * Gera o objeto Velocidade dada uma velocidade
		 * 
		 * @param velocidade
		 */
		public Velocidade(Velocidade velocidade) {
			this(velocidade.valor, velocidade.direcao);
		}

		/**
		 * Gera o objeto Velocidade com valor e direcao de movimento
		 * 
		 * @param valor
		 * @param direcao
		 */
		public Velocidade(int valor, Direcao direcao) {
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
			resultado = primo * resultado + valor;
			resultado = primo * resultado + direcao.hashCode();
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
			return "Velocidade = " + valor + " e Direcao = " + direcao;
		}

		/**
		 * Define a direção da velocidade
		 * 
		 * @author Emanuel
		 */
		public enum Direcao {
			Cima, Baixo, Direita, Esquerda;

			/**
			 * Escolhe uma direção aleatória
			 * 
			 * @return Direcao
			 */
			public static Direcao escolhaAleatoria() {
				return values()[new Random().nextInt(values().length)];
			}
		}
	}

	/**
	 * Componente dos Sprites da Entidade
	 * 
	 * @author Emanuel
	 */
	public static class Sprites extends Componente {
		private SpritesAnimados spritesAnim;

		private int cor = 0xffffffff;

		/**
		 * Cria o objeto Sprites com os sprites da forma da Entidade
		 * 
		 * @param sprites
		 */
		public Sprites(Forma forma) {
			switch (forma) {
			case Bacillus:
				spritesAnim = SpritesAnimados.bacillus;
				break;
			case Spiral:
				spritesAnim = SpritesAnimados.spiral;
				break;
			case Coccus:
				spritesAnim = SpritesAnimados.coccus;
				break;
			}
		}

		/**
		 * Cria o objeto Sprites com os sprites da forma da Entidade e com a cor
		 * 
		 * @param sprites
		 */
		public Sprites(Forma forma, int cor) {
			this(forma);
			this.cor = cor;
		}

		/**
		 * Obtém a sprite dada a direção e a velocidade
		 * 
		 * @param direcao
		 * @param velocidade
		 * @return Sprite
		 */
		public Sprite obterSprite(Direcao direcao, int velocidade) {
			return spritesAnim.obterSprite(direcao, velocidade);
		}

		/**
		 * Obtém a cor
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
			result = prime * result + spritesAnim.hashCode();
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