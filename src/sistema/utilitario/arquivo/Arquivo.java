package sistema.utilitario.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Le e escreve em arquivos
 * 
 * @author Emanuel
 */
public abstract class Arquivo {
	/**
	 * Lê as linhas do arquivo
	 * 
	 * @param caminho
	 * @return
	 */
	public static List<String> ler(File arquivo) {
		List<String> texto = new ArrayList<String>();
		BufferedReader leitor;
		String linha;
		try {
			leitor = new BufferedReader(new FileReader(arquivo));
			while ((linha = leitor.readLine()) != null) {
				texto.add(linha);
			}
			leitor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texto;
	}

	/**
	 * Escreve linhas no arquivo do dado caminho
	 * 
	 * @param substitui
	 * @param caminho
	 * @param texto
	 */
	public static void escrever(boolean substitui, File arquivo, List<String> texto) {
		BufferedWriter escritor;
		try {
			escritor = new BufferedWriter(new FileWriter(arquivo, !substitui));
			for (String linha : texto) {
				escritor.write(linha);
				escritor.newLine();
			}
			escritor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria as pastas do arquivo e o arquivo
	 * 
	 * @param arquivo
	 * @throws IOException
	 */
	public static void criar(File arquivo) {
		try {
			arquivo.getParentFile().mkdirs();
			arquivo.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Le e escreve o arquivo de configuração
	 * 
	 * @author Emanuel
	 */
	public static class ArquivoDeConfig extends Arquivo {
		private static Recurso recurso = new Recurso();
		private static final String NOME = "config.ini";

		/**
		 * Lê as configurações do arquivo
		 * 
		 * @return
		 */
		public static int[] ler() {
			File arquivo = new File(recurso.obterArquivoDoEndereco("/dados") + "/" + NOME);
			if (!arquivo.exists()) criar(arquivo);
			List<String> texto = Arquivo.ler(arquivo);
			int[] config = new int[texto.size()];
			int indice = 0;
			for (String linha : texto) {
				config[indice++] = Integer.parseInt(linha.split(" = ")[1]);
			}
			return config;
		}

		/**
		 * Escreve as configurações no arquivo
		 * 
		 * @param config
		 */
		public static void escrever(int[] config) {
			File arquivo = new File(recurso.obterArquivoDoEndereco("/dados") + "/" + NOME);
			List<String> texto = Arquivo.ler(arquivo);
			if (!arquivo.exists()) criar(arquivo);
			for (int i = 0; i < config.length; i++) {
				texto.set(i, texto.get(i).split(" = ")[0] + " = " + config[i]);
			}
			escrever(true, arquivo, texto);
		}

		/**
		 * Cria as pastas do arquivo e o arquivo
		 * 
		 * @param arquivo
		 * @throws IOException
		 */
		public static void criar(File arquivo) {
			Arquivo.criar(arquivo);
			int[] config = { 2, 1 };
			List<String> texto = new ArrayList<String>();
			texto.add("Controle = " + config[0]);
			texto.add("Resolução = " + config[1]);
			escrever(true, arquivo, texto);
		}
	}

	/**
	 * Escreve o arquivo do questionário
	 *
	 * @author Emanuel
	 */
	public static class ArquivoDoQuest extends Arquivo {
		private static Recurso recurso = new Recurso();
		private static final String NOME = "questionario.dat";

		/**
		 * Escreve as respostas do questionario no arquivo
		 * 
		 * @param config
		 */
		public static void escrever(int[] respostas) {
			File arquivo = new File(recurso.obterArquivoDoEndereco("/dados") + "/" + NOME);
			List<String> texto = new ArrayList<String>();
			if (!arquivo.exists()) criar(arquivo);
			texto.add(Arrays.toString(respostas));
			escrever(false, arquivo, texto);
		}
	}
}
