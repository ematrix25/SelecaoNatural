package main;

import java.io.IOException;

import gui.TelaDoMenu;

/**
 * @author Emanuel
 *
 */
public class Principal {
	public static void main(String[] args) {
		try {
			new TelaDoMenu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
