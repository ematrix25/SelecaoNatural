package controlador;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import componente.Componente;
import componente.Componente.Posicao;
import componente.Componente.Sprites;
import componente.Componente.Velocidade;
import sistema.controlador.ControladorDaEntidade;
import sistema.controlador.ControladorDoJogo;
import sistema.interface_grafica.renderizador.base_do_jogo.Sprite;
import sistema.interface_grafica.renderizador.base_do_jogo.mapa.Coordenada;
import sistema.interface_grafica.renderizador.base_do_jogo.mapa.Mapa;
import sistema.utilitario.Opcoes;
import sistema.utilitario.periferico.Teclado;

/**
 * Testa o bom funcionamento do sistema controlador do Jogo
 * 
 * @author Emanuel
 */
public class TesteControladorDoJogo {
	private ControladorDaEntidade controladorDaEntidade;
	private ControladorDoJogo controladorDoJogo;
	private Mapa mapa;

	/**
	 * Instância o controlador do Jogo para iniciar os testes
	 */
	@Before
	public void iniciar() {
		controladorDaEntidade = new ControladorDaEntidade();
		mapa = new Mapa("/mapas/caverna.png");
		controladorDoJogo = new ControladorDoJogo(mapa);
	}

	/**
	 * Método auxiliar para testar a criação de Entidade
	 *
	 * @return int
	 */
	private int criarEntidade() {
		int ID = controladorDaEntidade.criarEntidade();
		Coordenada coordenada = new Coordenada(mapa, 8, 7);
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Posicao(coordenada));
		controladorDaEntidade.adicionarComponente(ID, (Componente) new Velocidade());
		Sprite arrayDeSprites[] = { Sprite.jogadorMovendoY, Sprite.jogadorMovendoX, Sprite.jogadorParadoY,
				Sprite.jogadorParadoX };
		controladorDaEntidade.adicionarComponente(ID, new Sprites(arrayDeSprites));
		return ID;
	}

	/**
	 * Testa se o sistema cria a Entidade com seus componentes
	 */
	@Test
	public void testarCriarEntidade() {
		assertTrue(controladorDaEntidade.entidades.isEmpty());
		int ID = criarEntidade();
		assertFalse(controladorDaEntidade.entidades.isEmpty());
		assertEquals(controladorDaEntidade.obterComponentes(ID).size(), 3);
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
		Sprite arrayDeSprites[] = { Sprite.jogadorMovendoY, Sprite.jogadorMovendoX, Sprite.jogadorParadoY,
				Sprite.jogadorParadoX };
		Sprites sprites = new Sprites(arrayDeSprites);
		assertEquals(controladorDaEntidade.obterComponente(ID, Sprites.class), sprites);
	}

	/**
	 * Método auxiliar para testar a movimentação da Entidade
	 *
	 * @param ID
	 */
	private void moverEntidade(int ID) {
		Opcoes.controlePorMouse = false;
		Teclado.direita = true;
		Posicao posicao = controladorDaEntidade.obterComponente(ID, Posicao.class);
		Velocidade velocidade = controladorDaEntidade.obterComponente(ID, Velocidade.class);
		controladorDoJogo.moverJogador(posicao, velocidade);
	}

	/**
	 * Testa se os componentes mudaram quando moveram
	 */
	@Test
	public void testarMovimentacaoDaEntidade() {
		int ID = criarEntidade();
		Posicao posicao = controladorDaEntidade.obterComponente(ID, Posicao.class);
		posicao = new Posicao(posicao.x, posicao.y);
		Velocidade velocidade = controladorDaEntidade.obterComponente(ID, Velocidade.class);
		velocidade = new Velocidade(velocidade.movendo, velocidade.valor, velocidade.direcao);
		assertEquals(controladorDaEntidade.obterComponentes(ID).size(), 3);
		moverEntidade(ID);
		assertNotEquals(controladorDaEntidade.obterComponente(ID, Posicao.class), posicao);
		assertNotEquals(controladorDaEntidade.obterComponente(ID, Velocidade.class), velocidade);
	}
}
