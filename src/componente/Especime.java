package componente;

import java.util.Random;

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
	 * Verifica a igualdade de Especimes
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
		if (!(obj instanceof Especime)) {
			return false;
		}
		Especime other = (Especime) obj;
		if (especie == null) {
			if (other.especie != null) {
				return false;
			}
		} else if (!especie.equals(other.especie)) {
			return false;
		}
		return true;
	}

	/**
	 * Gera o texto da massa do Especime
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
		public String nome;
		public Tipo tipo;
		public int tempMaxSup, tempMinSup;

		/**
		 * Gera o objeto Especie com Tipo aleatorio e temperaturas suportadas
		 * 
		 * @param tempMaxSup
		 * @param tempMinSup
		 */
		public Especie(int tempMaxSup, int tempMinSup) {
			this(Forma.escolhaAleatoria(), tempMaxSup, tempMinSup);
		}

		/**
		 * Gera o objeto Especie com valores de Tipo e temperaturas suportadas
		 * 
		 * @param tipo
		 * @param tempMaxSup
		 * @param tempMinSup
		 */
		public Especie(Forma forma, int tempMaxSup, int tempMinSup) {
			this.nome = forma.toString() + " " + Integer.toString(tempMaxSup * tempMinSup);
			this.tipo = escolherTipo(forma);
			this.tempMaxSup = tempMaxSup;
			this.tempMinSup = tempMinSup;
		}

		/**
		 * Escolhe o Tipo da Especie dado a Forma
		 * 
		 * @param forma
		 * @return
		 */
		private static Tipo escolherTipo(Forma forma) {
			switch (forma) {
			case Coccus:
				return new Tipo(forma, Movimento.Deslizamento, true);
			case Bacillus:
				return new Tipo(forma, Movimento.Flagelo, false);
			case Spiral:
				return new Tipo(forma, Movimento.Contracao, false);
			}
			return null;
		}

		/**
		 * Gera o codigo com o nome da Especie
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int obterCodigo() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			return result;
		}

		/**
		 * Valida a igualdade de Especies
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
			if (!(obj instanceof Especie)) {
				return false;
			}
			Especie other = (Especie) obj;
			if (obterCodigo() != other.obterCodigo()) {
				return false;
			}
			if (tempMaxSup != other.tempMaxSup) {
				return false;
			}
			if (tempMinSup != other.tempMinSup) {
				return false;
			}
			if (tipo == null) {
				if (other.tipo != null) {
					return false;
				}
			} else if (!tipo.equals(other.tipo)) {
				return false;
			}
			return true;
		}

		/**
		 * Gera o texto do nome da Especie
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "[" + nome + "]";
		}

		/**
		 * Define o Tipo da Especie
		 * 
		 * @author Emanuel
		 */
		public static class Tipo {
			public Forma forma;
			public Movimento movimento;
			public boolean ehAutotrofa;

			/**
			 * Cria o objteto Tipo da Especie
			 * 
			 * @param forma
			 * @param movimento
			 * @param ehAutotrofa
			 */
			public Tipo(Forma forma, Movimento movimento, boolean ehAutotrofa) {
				this.forma = forma;
				this.movimento = movimento;
				this.ehAutotrofa = ehAutotrofa;
			}

			/**
			 * Valida a igualdade de Tipos
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
				if (!(obj instanceof Tipo)) {
					return false;
				}
				Tipo other = (Tipo) obj;
				if (forma != other.forma) {
					return false;
				}
				if (movimento != other.movimento) {
					return false;
				}
				if (ehAutotrofa != other.ehAutotrofa) {
					return false;
				}
				return true;
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
				return values()[new Random().nextInt(values().length)];
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