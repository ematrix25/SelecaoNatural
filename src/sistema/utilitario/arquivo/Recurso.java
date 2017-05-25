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
	 * Obtem o arquivo do endereco dos recursos dado caminho do arquivo
	 * 
	 * @param CAMINHO
	 * @return
	 */
	public String getArquivoDoEndereco(final String CAMINHO) {
		URL endereco = getEndereco(CAMINHO);
		return (endereco != null) ? endereco.getFile() : "";
	}

	/**
	 * Obtem o endereço nos recursos dado caminho do arquivo
	 * 
	 * @param CAMINHO
	 * @return
	 */
	public URL getEndereco(String CAMINHO) {
		return getClass().getResource(CAMINHO);
	}

	/**
	 * Obtem o fluxo do endereco dos recursos dado caminho do arquivo
	 * 
	 * @param CAMINHO
	 * @return
	 */
	public InputStream getEnderecoEmFluxo(String CAMINHO) {
		return getClass().getResourceAsStream(CAMINHO);
	}
}
