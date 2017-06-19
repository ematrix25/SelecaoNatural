package controlador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import componente.Componente;
import componente.Especime;
import componente.Especime.Especie;
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;

/**
 * Testa o bom funcionamento do sistema do Controlador Do Ambiente
 * 
 * @author Emanuel
 */
public class TesteControladorDoAmbiente {
	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;
	private Especie[] especies;

	/**
	 * Inicia os Controladores para iniciar os testes
	 * 
	 * @throws Exception
	 */
	@Before
	public void iniciar() throws Exception {
		controladorDaEntidade = new ControladorDaEntidade();
		controladorDoAmbiente = new ControladorDoAmbiente();
	}

	/**
	 * Testa se o sistema criou o Ambiente corretamente
	 */
	@Test
	public void testarCriarAmbiente() {
		assertTrue(controladorDoAmbiente.obterAmbiente() != null);
	}

	/**
	 * Testa se o sistema atualiza o Ambiente corretamente
	 */
	@Test
	public void testarAtualizarAmbiente() {
		assertTrue(controladorDoAmbiente.obterAmbiente() != null);
		int tempMax = controladorDoAmbiente.obterAmbiente().obterTempMax();
		int tempMin = controladorDoAmbiente.obterAmbiente().obterTempMin();
		assertTrue(tempMax == 400 && tempMin == 350);
		controladorDoAmbiente.atualizarAmbiente(450, 400);
		tempMax = controladorDoAmbiente.obterAmbiente().obterTempMax();
		tempMin = controladorDoAmbiente.obterAmbiente().obterTempMin();
		System.out.println(tempMax + " e " + tempMin);
		assertFalse(tempMax == 400 && tempMin == 350);
	}

	/**
	 * Testa se o sistema atualiza a temperatura do Ambiente corretamente
	 */
	@Test
	public void testarAtualizarTemp() {
		assertTrue(controladorDoAmbiente.obterAmbiente() != null);
		int temp = controladorDoAmbiente.obterAmbiente().obterTemp();
		assertEquals(temp, 375);
		controladorDoAmbiente.atualizarTemp(false, 25);
		temp = controladorDoAmbiente.obterAmbiente().obterTemp();
		assertEquals(temp, 350);
	}

	/**
	 * Método auxiliar para criar as Especies
	 */
	private void criarEspecies() {
		int entidades[] = new int[7];
		for (int i = 0; i < 7; i++) {
			entidades[i] = controladorDaEntidade.criarEntidade();
		}
		especies = controladorDoAmbiente.criarEspecies(entidades);
		for (int i = 0; i < 7; i++) {
			controladorDaEntidade.adicionarComponente(entidades[i], (Componente) new Especime(especies[i]));
		}
	}

	/**
	 * Testa se o sistema cria Especies corretamente
	 */
	@Test
	public void testarCriarEspecies() {
		assertEquals(controladorDoAmbiente.obterAmbiente().obterQTD(), 0);
		criarEspecies();
		assertFalse(controladorDaEntidade.obterTodasEntidadesComOComponente(Especime.class).isEmpty());
		assertEquals(controladorDoAmbiente.obterAmbiente().obterQTD(), 7);
	}

	/**
	 * Testa se o sistema atualiza a Especie corretamente
	 */
	@Test
	public void testarAtualizarEspecie() {
		criarEspecies();
		assertEquals(controladorDoAmbiente.obterEspecie(especies[5].obterCodigo()).size(), 1);
		int entidade = controladorDaEntidade.criarEntidade();
		controladorDaEntidade.adicionarComponente(entidade, new Especime(especies[5]));
		controladorDoAmbiente.atualizarEspecie(entidade, true, especies[5].obterCodigo());
		assertEquals(controladorDoAmbiente.obterEspecie(especies[5].obterCodigo()).size(), 2);
		controladorDaEntidade.removerEntidade(entidade);
		controladorDoAmbiente.atualizarEspecie(entidade, false, especies[5].obterCodigo());
		assertEquals(controladorDoAmbiente.obterEspecie(especies[5].obterCodigo()).size(), 1);
	}

	/**
	 * Testa se o sistema remove a Especie corretamente
	 */
	@Test
	public void testarRemoverEspecie() {
		criarEspecies();
		assertEquals(controladorDoAmbiente.obterAmbiente().obterQTD(), 7);
		for (Integer ID : controladorDoAmbiente.obterEspecie(especies[5].obterCodigo())) {
			controladorDaEntidade.removerEntidade(ID);
		}
		controladorDoAmbiente.removerEspecie(especies[5].obterCodigo());
		assertFalse(controladorDoAmbiente.obterEspecie(especies[5].obterCodigo()) != null);
		assertEquals(controladorDoAmbiente.obterAmbiente().obterQTD(), 6);
	}
}
