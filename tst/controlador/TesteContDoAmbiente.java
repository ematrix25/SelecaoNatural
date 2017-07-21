package controlador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import componente.Componente;
import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ContDaEntidade;
import sistema.controlador.ContDoAmbiente;

/**
 * Testa o bom funcionamento do sistema do Controlador Do Ambiente
 * 
 * @author Emanuel
 */
public class TesteContDoAmbiente {
	private ContDaEntidade contDaEntidade;
	private ContDoAmbiente contDoAmbiente;
	private Especie[] especies;

	/**
	 * Inicia os Controladores para iniciar os testes
	 * 
	 * @throws Exception
	 */
	@Before
	public void iniciar() throws Exception {
		contDaEntidade = new ContDaEntidade();
		contDoAmbiente = new ContDoAmbiente();
	}

	/**
	 * Testa se o sistema criou o Ambiente corretamente
	 */
	@Test
	public void testarCriarAmbiente() {
		assertTrue(contDoAmbiente.obterAmbiente() != null);
	}

	/**
	 * Testa se o sistema atualiza o Ambiente corretamente
	 */
	@Test
	public void testarAtualizarAmbiente() {
		assertTrue(contDoAmbiente.obterAmbiente() != null);
		int tempMax = contDoAmbiente.obterAmbiente().obterTempMax();
		int tempMin = contDoAmbiente.obterAmbiente().obterTempMin();
		assertTrue(tempMax == 400 && tempMin == 350);
		contDoAmbiente.atualizarAmbiente(450, 400);
		tempMax = contDoAmbiente.obterAmbiente().obterTempMax();
		tempMin = contDoAmbiente.obterAmbiente().obterTempMin();
		assertFalse(tempMax == 400 && tempMin == 350);
	}

	/**
	 * Testa se o sistema atualiza a temperatura do Ambiente corretamente
	 */
	@Test
	public void testarAtualizarTemp() {
		assertTrue(contDoAmbiente.obterAmbiente() != null);
		int temp = contDoAmbiente.obterAmbiente().obterTemp();
		assertEquals(temp, 375);
		contDoAmbiente.atualizarTemp(false, 25);
		temp = contDoAmbiente.obterAmbiente().obterTemp();
		assertEquals(temp, 350);
	}

	/**
	 * Método auxiliar para criar as Especies
	 */
	private void criarEspecies() {
		int entidades[] = new int[7];
		for (int i = 0; i < 7; i++) {
			entidades[i] = contDaEntidade.criarEntidade();
		}
		especies = contDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < 7; i++) {
			contDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
		}
	}

	/**
	 * Testa se o sistema cria Especies corretamente
	 */
	@Test
	public void testarCriarEspecies() {
		assertEquals(contDoAmbiente.obterAmbiente().obterQTD(), 0);
		criarEspecies();
		assertFalse(contDaEntidade.obterTodasEntidadesComOComponente(Especime.class).isEmpty());
		assertEquals(contDoAmbiente.obterAmbiente().obterQTD(), 7);
	}

	/**
	 * Testa se o sistema atualiza a Especie corretamente
	 */
	@Test
	public void testarAtualizarEspecie() {
		criarEspecies();
		assertEquals(contDoAmbiente.obterEspecimesPorEspecie(especies[5].obterCodigo()).size(), 1);
		int entidade = contDaEntidade.criarEntidade();
		contDaEntidade.adicionarComponente(entidade, new Especime(especies[5]));
		contDoAmbiente.atualizarEspecie(entidade, true, especies[5].obterCodigo());
		assertEquals(contDoAmbiente.obterEspecimesPorEspecie(especies[5].obterCodigo()).size(), 2);
		contDaEntidade.removerEntidade(entidade);
		contDoAmbiente.atualizarEspecie(entidade, false, especies[5].obterCodigo());
		assertEquals(contDoAmbiente.obterEspecimesPorEspecie(especies[5].obterCodigo()).size(), 1);
	}

	/**
	 * Testa se o sistema remove a Especie corretamente
	 */
	@Test
	public void testarRemoverEspecie() {
		criarEspecies();
		assertEquals(contDoAmbiente.obterAmbiente().obterQTD(), 7);
		for (Integer ID : contDoAmbiente.obterEspecimesPorEspecie(especies[5].obterCodigo())) {
			contDaEntidade.removerEntidade(ID);
		}
		contDoAmbiente.removerEspecie(especies[5].obterCodigo());
		assertFalse(contDoAmbiente.obterEspecimesPorEspecie(especies[5].obterCodigo()) != null);
		assertEquals(contDoAmbiente.obterAmbiente().obterQTD(), 6);
	}
}
