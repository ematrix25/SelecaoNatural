package componente;

import sistema.utilitario.Aleatorio;

/**
 * Componente que caracteriza o Especime da Entidade
 * 
 * @author Emanuel
 */
public class Especime extends Componente {
	public Especie especie;
	public int massa;

	/**	
	 * Gera o objeto Especime com valor padrão de massa
	 */
	public Especime(Especie especie) {
		this(50, especie);
	}

	/**
	 * Gera o objeto Especime com valor de massa
	 * 
	 * @param massa
	 */
	public Especime(int massa, Especie especie) {
		this.massa = massa;
		this.especie = especie;
	}

	/**
	 * Gera o texto dos dados do Especime
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + massa + "]";
	}

	/**
	 * Caracteriza o Especime com a Especie
	 * 
	 * @author Emanuel
	 */
	public static class Especie {
		public Tipo tipo;
		public int tempMaxSup, tempMinSup;

		/**
		 * Gera o objeto Especie com Tipo aleatorio e temperaturas suportadas
		 * 
		 * @param tempMaxSup
		 * @param tempMinSup
		 */
		public Especie(int tempMaxSup, int tempMinSup) {
			this(escolherTipoAleatorio(), tempMaxSup, tempMinSup);
		}

		/**
		 * Gera o objeto Especie com valores de Tipo e temperaturas suportadas
		 * 
		 * @param tipo
		 * @param tempMaxSup
		 * @param tempMinSup
		 */
		public Especie(Tipo tipo, int tempMaxSup, int tempMinSup) {
			this.tipo = tipo;
			this.tempMaxSup = tempMaxSup;
			this.tempMinSup = tempMinSup;
		}

		/**
		 * Gera Tipo aleatorio para a Especie
		 * 
		 * @return
		 */
		private static Tipo escolherTipoAleatorio() {
			Forma formaAleatoria = Forma.escolhaAleatoria();
			switch (formaAleatoria) {
			case Coccus:
				return new Tipo(formaAleatoria, Movimento.Deslizamento, true);
			case Bacillus:
				return new Tipo(formaAleatoria, Movimento.Flagelo, false);
			case Spiral:
				return new Tipo(formaAleatoria, Movimento.Contracao, false);
			}
			return null;
		}

		/**
		 * Define o Tipo da Especie
		 * 
		 * @author Emanuel
		 */
		public static class Tipo {
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

		/**
		 * Define a Forma da Especie
		 * 
		 * @author Emanuel
		 */
		public enum Forma {
			Coccus, Bacillus, Spiral;

			public static Forma escolhaAleatoria() {
				return values()[Aleatorio.escolherNum(values().length)];
			}
		}

		/**
		 * Define o tipo de Movimento da Especie
		 * 
		 * @author Emanuel
		 */
		public enum Movimento {
			Deslizamento, Contracao, Flagelo;
		}
	}

}