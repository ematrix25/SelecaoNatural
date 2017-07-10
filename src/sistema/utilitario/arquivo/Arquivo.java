package sistema.utilitario.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Le e escreve em arquivos
 * 
 * @author Emanuel
 */
public abstract class Arquivo {
	private static Recurso recurso = new Recurso();

	/**
	 * Lê as linhas do arquivo
	 * 
	 * @param caminho
	 * @return Lista de String
	 */
	public static List<String> ler(File arquivo) {
		List<String> texto = new ArrayList<String>();
		BufferedReader leitor;
		String linha;
		try {
			leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
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
			escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo, !substitui), "UTF-8"));
			for (String linha : texto) {
				if (substitui) escritor.write(linha);
				else escritor.append(linha);
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
		private static final String NOME = "config.ini";

		/**
		 * Lê as configurações do arquivo
		 * 
		 * @return int[]
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
		private static final String RESULTADO = "questionario.dat";
		private static final String PERGUNTAS = "perguntas.dat";
		private static final String RESPOSTAS = "respostas.dat";

		/**
		 * Filtra do arquivo e obtem as perguntas ou respostas
		 * 
		 * @param saoPerguntas
		 * @return Lista de String
		 */
		public static List<String> ler(boolean saoPerguntas) {
			String nome = (saoPerguntas) ? PERGUNTAS : RESPOSTAS;
			File arquivo = new File(recurso.obterArquivoDoEndereco("/dados_base") + "/" + nome);
			if (!arquivo.exists()) criar(arquivo);
			List<String> texto =Arquivo.ler(arquivo);
			String linha = texto.get(0);
			texto.set(0, linha.substring(1, linha.length()));
			return texto;
		}

		/**
		 * Escreve as respostas do questionario no arquivo
		 * 
		 * @param config
		 */
		public static void escrever(int[] respostas) {
			String nome = RESULTADO;
			File arquivo = new File(recurso.obterArquivoDoEndereco("/dados") + "/" + nome);
			List<String> texto = new ArrayList<String>();
			if (!arquivo.exists()) criar(arquivo);
			texto.add(Arrays.toString(respostas));
			escrever(false, arquivo, texto);
		}
	}
}
