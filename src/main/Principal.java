package main;

import java.io.IOException;

import gui.Menu;

/**
 * @author Emanuel
 *
 */
public class Principal {
	public static void main(String[] args) {
		try {
			new Menu();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
