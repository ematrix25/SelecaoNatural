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
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoAmbiente;
import sistema.controlador.jogo.ControladorDaEntidadeMovel;
import sistema.controlador.jogo.ControladorDoMapa;
import sistema.interface_grafica.renderizador.jogo.base.Sprite;
import sistema.interface_grafica.renderizador.jogo.base.mapa.Coordenada;
import sistema.interface_grafica.renderizador.jogo.base.mapa.Mapa;
import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Teclado;

/**
 * Testa o bom funcionamento do sistema controlador do Jogo
 * 
 * @author Emanuel
 */
public class TesteControladorDoJogo {
	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoAmbiente controladorDoAmbiente;
	private ControladorDoMapa controladorDoMapa;
	private ControladorDaEntidadeMovel controladorDaEntMovel;
	private Mapa mapa;

	/**
	 * Instância o controlador do Jogo para iniciar os testes
	 */
	@Before
	public void iniciar() {
		controladorDoAmbiente = new ControladorDoAmbiente();
		controladorDaEntidade = new ControladorDaEntidade();
		mapa = new Mapa("/mapas/caverna.png", 0);
		controladorDoMapa = new ControladorDoMapa(mapa);
		controladorDaEntMovel = new ControladorDaEntidadeMovel();
	}

	/**
	 * Testa se o sistema cria a Entidade com seus componentes
	 */
	@Test
	public void testarCriarEntidade() {
		assertTrue(controladorDaEntidade.entidades.isEmpty());
		int ID = criarEntidade();
		assertFalse(controladorDaEntidade.entidades.isEmpty());
		assertEquals(controladorDaEntidade.obterComponentes(ID).size(), 4);
	}

	/**
	 * Testa se os componentes da Entidade estão corretos
	 */
	@Test
	public void testarCompoentesDaEntidade() {
		int ID = criarEntidade();
		Posicao posicao = new Posicao(new Coordenada(mapa, 8, 7));
		assertEquals(controladorDaEntidade.obterComponente(ID, Posicao.class), posicao);
		Velocidade velocidade = new Velocidade();
		assertEquals(controladorDaEntidade.obterComponente(ID, Velocidade.class), velocidade);
		Sprites sprites = new Sprites(Sprite.coccus);
		assertEquals(controladorDaEntidade.obterComponente(ID, Sprites.class), sprites);
	}

	/**
	 * Testa se os componentes mudaram quando moveram
	 */
	@Test
	public void testarMovimentacaoDaEntidade() {
		int ID = criarEntidade();
		Posicao posicao = new Posicao(controladorDaEntidade.obterComponente(ID, Posicao.class));
		Velocidade velocidade = new Velocidade(controladorDaEntidade.obterComponente(ID, Velocidade.class));
		assertEquals(controladorDaEntidade.obterComponentes(ID).size(), 4);
		moverEntidade(ID);
		assertNotEquals(controladorDaEntidade.obterComponente(ID, Posicao.class), posicao);
		assertNotEquals(controladorDaEntidade.obterComponente(ID, Velocidade.class), velocidade);
	}

	/**
	 * Método auxiliar para testar a criação de Entidade
	 *
	 * @return int
	 */
	private int criarEntidade() {
		int ID = controladorDaEntidade.criarEntidade();
		Coordenada coordenada = new Coordenada(mapa, 8, 7);
		Especie especie = controladorDoAmbiente.criarEspecie(ID, Forma.Coccus);
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Especime(especie));
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Posicao(coordenada));
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Velocidade());
		controladorDaEntidade.adicionarComponente(ID, new Sprites(Sprite.coccus));
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

		controladorDaEntMovel.configurarEntidade(ID, controladorDaEntidade.obterComponentes(ID));
		controladorDoMapa.moverEntidade(true, controladorDaEntMovel.obterEntidade());
	}
}
