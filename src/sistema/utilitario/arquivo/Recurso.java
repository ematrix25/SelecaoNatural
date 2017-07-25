package sistema.utilitario.arquivo;

import java.io.InputStream;
import java.net.URL;

/**
 * Classe para auxiliar com os arquivos dos recursos
 * 
 * @author Emanuel
 */
public class Recurso {
	/**
	 * Obtém o arquivo do endereco dos recursos dado caminho do arquivo
	 * 
	 * @param CAMINHO
	 * @return
	 */
	public String obterArquivoDoEndereco(final String CAMINHO) {
		URL endereco = obterEndereco(CAMINHO);
		String caminho;
		if (endereco == null) {
			endereco = obterEndereco("/imagens");
			caminho = endereco.getFile().replace("imagens", "dados");
		} else caminho = endereco.getFile();
		return caminho;
	}

	/**
	 * Obtém o endereço nos recursos dado caminho do arquivo
	 * 
	 * @param CAMINHO
	 * @return
	 */
	public URL obterEndereco(String CAMINHO) {
		return getClass().getResource(CAMINHO);
	}

	/**
	 * Obtém o fluxo do endereco dos recursos dado caminho do arquivo
	 * 
	 * @param CAMINHO
	 * @return
	 */
	public InputStream obterEnderecoEmFluxo(String CAMINHO) {
		return getClass().getResourceAsStream(CAMINHO);
	}
}
