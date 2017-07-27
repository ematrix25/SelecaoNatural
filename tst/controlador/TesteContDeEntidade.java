package controlador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import componente.Componente.Posicao;
import sistema.controlador.ContDaEntidade;

/**
 * Testa o bom funcionamento do sistema do Controlador Da Entidade
 * 
 * @author Emanuel
 */
public class TesteContDeEntidade {
	private ContDaEntidade contDaEntidade;
	private int ID;
	private Posicao posicao;

	/**
	 * Inst�ncia o Controlador Da Entidade para iniciar os testes
	 * 
	 * @throws Exception
	 */
	@Before
	public void iniciar() throws Exception {
		contDaEntidade = new ContDaEntidade();
		ID = 0;
		posicao = new Posicao(1, 1);
	}

	/**
	 * M�todo auxiliar para testar a cria��o de Entidade
	 */
	private void criarEntidade() {
		ID = contDaEntidade.criarEntidade();
	}

	/**
	 * Testa se o sistema cria Entidade corretamente
	 */
	@Test
	public void testarCriarEntidade() {
		assertTrue(contDaEntidade.entidades.isEmpty());
		criarEntidade();
		assertFalse(contDaEntidade.entidades.isEmpty());
		assertEquals(new Integer(ID), contDaEntidade.entidades.get(0));
	}

	/**
	 * Testa se o sistema adiciona Componente � Entidade
	 */
	@Test
	public void testarAdicionarComponente() {
		criarEntidade();

		// Avalia��o em si
		assertTrue(contDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(contDaEntidade.adicionarComponente(ID, posicao));
		assertFalse(contDaEntidade.baseDeComponentes.isEmpty());
	}

	/**
	 * M�todo auxiliar para adicionar Componente
	 */
	private void adicionarComponente() {
		criarEntidade();
		contDaEntidade.adicionarComponente(ID, posicao);
	}

	/**
	 * Testa se o sistema obtem Componente da Entidade
	 */
	@Test
	public void testarObterComponente() {
		adicionarComponente();

		// Avalia��o em si
		assertFalse(contDaEntidade.baseDeComponentes.isEmpty());
		assertEquals(posicao, contDaEntidade.obterComponente(ID, Posicao.class));
		assertFalse(contDaEntidade.baseDeComponentes.isEmpty());
	}

	/**
	 * Testa se o sistema obtem Entidades com o Componente
	 */
	@Test
	public void testarObterTodasEntidadesComOComponente() {
		adicionarComponente();

		// Avalia��o em si
		assertFalse(contDaEntidade.entidades.isEmpty());
		assertFalse(contDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(contDaEntidade.obterTodasEntidadesComOComponente(Posicao.class).contains(new Integer(ID)));
	}

	/**
	 * Testa se o sistema obtem Componentes do Tipo
	 */
	@Test
	public void testarObterTodosOsComponentesDoTipo() {
		adicionarComponente();

		// Avalia��o em si
		assertFalse(contDaEntidade.entidades.isEmpty());
		assertFalse(contDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(contDaEntidade.obterTodosOsComponentesDoTipo(Posicao.class).contains(posicao));
	}

	/**
	 * Testa se o sistema remove Entidades corretamente
	 */
	@Test
	public void testarRemoverEntidade() {
		adicionarComponente();

		// Avalia��o em si
		assertFalse(contDaEntidade.entidades.isEmpty());
		contDaEntidade.marcarEntidades(ID);
		assertTrue(contDaEntidade.removerEntidades());
		assertTrue(contDaEntidade.entidades.isEmpty());
	}

	/**
	 * Metodo auxiliar para remover Entidade
	 */
	private void removerEntidade() {
		adicionarComponente();
		contDaEntidade.marcarEntidades(ID);
		contDaEntidade.removerEntidades();
	}

	/**
	 * Testa se o sistema suporta obter Componente de Entidade removida
	 */
	@Test
	public void testarObterComponenteRemovido() {
		removerEntidade();

		// Avalia��o em si
		assertTrue(contDaEntidade.entidades.isEmpty());
		assertTrue(contDaEntidade.baseDeComponentes.isEmpty());
		assertFalse(posicao.equals(contDaEntidade.obterComponente(ID, Posicao.class)));
	}

	/**
	 * Testa se o sistema suporta obter Componentes com Entidade removida
	 */
	@Test
	public void testarObterComponentesAposRemover() {
		removerEntidade();

		// Avalia��o em si
		assertTrue(contDaEntidade.entidades.isEmpty());
		assertTrue(contDaEntidade.baseDeComponentes.isEmpty());
		assertTrue(contDaEntidade.obterTodosOsComponentesDoTipo(Posicao.class).isEmpty());
	}
}
