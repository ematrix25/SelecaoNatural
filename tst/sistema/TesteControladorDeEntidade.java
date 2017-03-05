package sistema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import componente.Componente.Posicao;
import sistema.controlador.ControladorDaEntidade;

/**
 * Testa o bom funcionamento do sistema do Controlador Da Entidade
 * 
 * @author Emanuel
 */
public class TesteControladorDeEntidade {

	// TODO Terminar os Testes

	private ControladorDaEntidade controladorDaEntidade;
	private int ID;
	private Posicao posicao;

	/**
	 * Instancia o Controlador Da Entidade para iniciar os testes
	 * 
	 * @throws Exception
	 */
	@Before
	public void iniciar() throws Exception {
		controladorDaEntidade = new ControladorDaEntidade();
		ID = 0;
		posicao = new Posicao(1, 1);
	}

	/**
	 * Metodo auxiliar para testar a criação de Entidade
	 */
	private void criarEntidade() {
		ID = controladorDaEntidade.criarEntidade();
	}

	/**
	 * Testa se o sistema cria Entidade corretamente
	 */
	@Test
	public void testarCriarEntidade() {
		assertTrue(controladorDaEntidade.entidades.isEmpty());
		criarEntidade();
		assertFalse(controladorDaEntidade.entidades.isEmpty());
		assertEquals(new Integer(ID), controladorDaEntidade.entidades.get(0));
	}

	/**
	 * Testa se o sistema adiciona Componente à Entidade
	 */
	@Test
	public void testarAdicionarComponente() {
		criarEntidade();

		// Avaliação em si
		assertTrue(controladorDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(controladorDaEntidade.adicionarComponente(ID, posicao));
		assertFalse(controladorDaEntidade.baseDeComponentes.isEmpty());
	}

	/**
	 * Metodo auxiliar para adicionar Componente
	 */
	private void adicionarComponente() {
		criarEntidade();
		controladorDaEntidade.adicionarComponente(ID, posicao);
	}

	/**
	 * Testa se o sistema obtem Componente da Entidade
	 */
	@Test
	public void testarObterComponente() {
		adicionarComponente();

		// Avaliação em si
		assertFalse(controladorDaEntidade.baseDeComponentes.isEmpty());
		assertEquals(posicao, controladorDaEntidade.obterComponente(ID, Posicao.class));
		assertFalse(controladorDaEntidade.baseDeComponentes.isEmpty());
	}

	/**
	 * Testa se o sistema obtem Entidades com o Componente
	 */
	@Test
	public void testarObterTodasEntidadesComOComponente() {
		adicionarComponente();

		// Avaliação em si
		assertFalse(controladorDaEntidade.entidades.isEmpty());
		assertFalse(controladorDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(controladorDaEntidade.obterTodasEntidadesComOComponente(Posicao.class).contains(new Integer(ID)));
	}

	/**
	 * Testa se o sistema obtem Componentes do Tipo
	 */
	@Test
	public void testarObterTodosOsComponentesDoTipo() {
		adicionarComponente();

		// Avaliação em si
		assertFalse(controladorDaEntidade.entidades.isEmpty());
		assertFalse(controladorDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(controladorDaEntidade.obterTodosOsComponentesDoTipo(Posicao.class).contains(posicao));
	}

	/**
	 * Testa se o sistema remove Entidades corretamente
	 */
	@Test
	public void testarRemoverEntidade() {
		adicionarComponente();

		// Avaliação em si
		assertFalse(controladorDaEntidade.entidades.isEmpty());
		assertTrue(controladorDaEntidade.removerEntidade(ID));
		assertTrue(controladorDaEntidade.entidades.isEmpty());
	}

	/**
	 * Metodo auxiliar para remover Entidade
	 */
	private void removerEntidade() {
		adicionarComponente();
		controladorDaEntidade.removerEntidade(ID);
	}

	/**
	 * Testa se o sistema suporta obter Componente de Entidade removida
	 */
	@Test
	public void testarObterComponenteRemovido() {
		removerEntidade();

		// Avaliação em si
		assertTrue(controladorDaEntidade.entidades.isEmpty());
		assertTrue(controladorDaEntidade.baseDeComponentes.isEmpty());
		assertFalse(posicao.equals(controladorDaEntidade.obterComponente(ID, Posicao.class)));
	}

	/**
	 * Testa se o sistema suporta obter Componentes com Entidade removida
	 */
	@Test
	public void testarObterComponentesAposRemover() {
		removerEntidade();

		// Avaliação em si
		assertTrue(controladorDaEntidade.entidades.isEmpty());
		assertTrue(controladorDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(controladorDaEntidade.obterTodosOsComponentesDoTipo(Posicao.class).isEmpty());
	}
}
