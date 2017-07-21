package componente;

import java.util.Arrays;
import java.util.Random;

import sistema.interface_grafica.renderizador.jogo.base.Sprite;
import sistema.interface_grafica.renderizador.jogo.base.mapa.Coordenada;

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
	 * Componente com os Sprites da Entidade
	 * 
	 * @author Emanuel
	 */
	public static class Sprites extends Componente {
		private final int LIMITE = 25;

		// TODO Criar classe de animação de sprites (GP Ep. 87-91)
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
		 * Obtem a sprite para o eixo X dado a velocidade
		 * 
		 * @param velocidade
		 * @return Sprite
		 */
		public Sprite obterSpriteX(int velocidade) {
			if (velocidade > 0) {
				int limite = LIMITE / velocidade;
				if (!(contador < limite)) contador = 0;
				if (contador++ > limite / 2) return sprites[3];
				else return sprites[1];
			} else {
				contador = 0;
				return sprites[3];
			}
		}

		/**
		 * Obtem a sprite para o eixo Y dado a velocidade
		 * 
		 * @return Sprite
		 */
		public Sprite obterSpriteY(int velocidade) {
			if (velocidade > 0) {
				int limite = LIMITE / velocidade;
				if (!(contador < limite)) contador = 0;
				if (contador++ > limite / 2) return sprites[2];
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