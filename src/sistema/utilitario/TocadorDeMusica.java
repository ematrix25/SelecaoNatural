package sistema.utilitario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import jaco.mp3.player.MP3Player;
import sistema.utilitario.arquivo.Recurso;

/**
 * Executa as m�sicas em plano de fundo
 * 
 * @author Emanuel
 */
public class TocadorDeMusica implements Runnable {
	private Thread thread;
	private static List<File> musicas;
	private static int faixa;

	/**
	 * Cria o objeto inicializando a execu��o das m�sicas
	 */
	public TocadorDeMusica() {
		File pasta = new File(new Recurso().obterArquivoDoEndereco("/musicas"));
		thread = new Thread(this, "Tocador");

		musicas = new ArrayList<File>(Arrays.asList(pasta.listFiles()));
		Collections.shuffle(musicas);
		faixa = 0;

		thread.start();
	}

	/**
	 * Obtem o nome da m�sica
	 * 
	 * @return String
	 */
	public static String obterNomeDaMusica() {
		MP3File arquivo = obterMusica();
		if (arquivo != null) return arquivo.getID3v2Tag().getSongTitle();
		return "N�o h�";
	}

	/**
	 * Obtem o artista da m�sica
	 * 
	 * @return String
	 */
	public static String obterArtistaDaMusica() {
		MP3File arquivo = obterMusica();
		if (arquivo != null) return arquivo.getID3v2Tag().getLeadArtist();
		return "N�o h�";
	}

	/**
	 * Obtem o compositor da m�sica
	 * 
	 * @return String
	 */
	public static String obterCompositorDaMusica() {
		MP3File arquivo = obterMusica();
		if (arquivo != null) return arquivo.getID3v2Tag().getAuthorComposer();
		return "N�o h�";
	}

	/**
	 * Obtem o arquivo mp3 da m�sica sendo tocada
	 * 
	 * @return MP3File
	 */
	private static MP3File obterMusica() {
		try {
			return new MP3File(musicas.get(faixa));
		} catch (IOException | TagException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Executa o tocador de m�sicas repetindo ao chegar na �ltima faixa
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			tocar();

			if (faixa < musicas.size()) faixa++;
			else faixa = 0;
		}
	}

	/**
	 * Toca uma m�sica
	 */
	private void tocar() {
		MP3Player tocador = new MP3Player(musicas.get(faixa));
		tocador.play();
		while (!tocador.isStopped()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}

	/**
	 * Testa o tocador
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new TocadorDeMusica();
	}
}
