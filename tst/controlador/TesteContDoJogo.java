package controlador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import componente.Componente;
import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import componente.Especime;
import componente.Especime.Especie;
import componente.Especime.Especie.Forma;
import sistema.controlador.ContDaEntidade;
import sistema.controlador.ContDoAmbiente;
import sistema.controlador.jogo.ContAuxDaEnt;
import sistema.controlador.jogo.ContDoMapa;
import sistema.controlador.jogo.movimento.ContDoJogador;
import sistema.igu.renderizador.jogo.base.mapa.Coordenada;
import sistema.igu.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Teclado;

/**
 * Testa o bom funcionamento do sistema controlador do Jogo
 * 
 * @author Emanuel
 */
public class TesteContDoJogo {
	private ContDaEntidade contDaEntidade;
	private ContDoAmbiente contDoAmbiente;
	private ContDoMapa contDoMapa;
	private ContAuxDaEnt contAuxDaEnt;
	private ContDoJogador contDoJogador;
	private Mapa mapa;

	/**
	 * Instância o controlador do Jogo para iniciar os testes
	 */
	@Before
	public void iniciar() {
		contDoAmbiente = new ContDoAmbiente();
		contDaEntidade = new ContDaEntidade();
		mapa = new Mapa("/mapas/caverna.png", 0);
		contDoMapa = new ContDoMapa(mapa);
		contAuxDaEnt = new ContAuxDaEnt();
		contDoJogador = new ContDoJogador();
	}

	/**
	 * Testa se o sistema cria a Entidade com seus componentes
	 */
	@Test
	public void testarCriarEntidade() {
		assertTrue(contDaEntidade.entidades.isEmpty());
		int ID = criarEntidade();
		assertFalse(contDaEntidade.entidades.isEmpty());
		assertEquals(contDaEntidade.obterComponentes(ID).size(), 4);
	}

	/**
	 * Testa se os componentes da Entidade estão corretos
	 */
	@Test
	public void testarCompoentesDaEntidade() {
		int ID = criarEntidade();
		Posicao posicao = new Posicao(new Coordenada(mapa, 8, 7));
		assertEquals(contDaEntidade.obterComponente(ID, Posicao.class), posicao);
		Velocidade velocidade = new Velocidade();
		assertEquals(contDaEntidade.obterComponente(ID, Velocidade.class), velocidade);
		Sprites sprites = new Sprites(Forma.Coccus);
		assertEquals(contDaEntidade.obterComponente(ID, Sprites.class), sprites);
	}

	/**
	 * Testa se os componentes mudaram quando moveram
	 */
	@Test
	public void testarMovimentacaoDaEntidade() {
		int ID = criarEntidade();
		Posicao posicao = new Posicao(contDaEntidade.obterComponente(ID, Posicao.class));
		Velocidade velocidade = new Velocidade(contDaEntidade.obterComponente(ID, Velocidade.class));
		assertEquals(contDaEntidade.obterComponentes(ID).size(), 4);
		moverEntidade(ID);
		assertNotEquals(contDaEntidade.obterComponente(ID, Posicao.class), posicao);
		assertNotEquals(contDaEntidade.obterComponente(ID, Velocidade.class), velocidade);
	}

	/**
	 * Método auxiliar para testar a criação de Entidade
	 *
	 * @return int
	 */
	private int criarEntidade() {
		int ID = contDaEntidade.criarEntidade();
		Coordenada coordenada = new Coordenada(mapa, 8, 7);
		Especie especie = contDoAmbiente.criarEspecie(ID, Forma.Coccus);
		contDaEntidade.adicionarComponente(ID, (Componente) new Especime(especie));
		contDaEntidade.adicionarComponente(ID, (Componente) new Posicao(coordenada));
		contDaEntidade.adicionarComponente(ID, (Componente) new Velocidade());
		contDaEntidade.adicionarComponente(ID, new Sprites(Forma.Coccus));
		return ID;
	}

	/**
	 * Método auxiliar para testar a movimentação da Entidade
	 *
	 * @param ID
	 */
	private void moverEntidade(int ID) {
		Opcoes.controlePorMouse = false;
		Teclado.direita = true;
		Teclado.correr = true;

		contAuxDaEnt.configurarEntidade(ID, contDaEntidade.obterComponentes(ID));
		int velocidadeMax = contDoMapa.obterVelocidadeMax(contAuxDaEnt.obterEntidade().especime.especie.tipo.movimento);
		contDoMapa.moverEntidade(contDoJogador.obterMovimentacao(velocidadeMax), contDoJogador.obterDirecao(),
				contAuxDaEnt.obterEntidade());
	}
}
