package sistema.modelo;

/**
 * @author Emanuel
 *
 */
public class Especime {
	private int massa, x, y;

	public Especime(int massa, int x, int y) {
		this.massa = massa;
		this.x = x;
		this.y = y;
	}

	public int getMassa() {
		return massa;
	}

	public void setMassa(int massa) {
		this.massa = massa;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}	
}
