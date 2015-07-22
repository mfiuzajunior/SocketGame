package br.edu.ifce.mflj.dados;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Casa {

	private Boolean		ocupada;
	private Integer		coluna,
						linha;
	private List<Peca>	pecas;

	private Integer		restricaoDeMovimento;

	public Casa(){}

	public Casa( Integer linha, Integer coluna, Integer restricaoDeMovimento ){
		this.linha	= linha;
		this.coluna	= coluna;
		pecas		= new ArrayList<Peca>();
	}

	public List<Peca> getPecas() {
		return pecas;
	}
	
	public void setPecas(List<Peca> pecas) {
		this.pecas = pecas;
	}

	public Boolean isOcupada(){
		return ocupada;
	}

	public void setOcupada( Boolean ocupada ){
		this.ocupada = ocupada;
	}

	public Integer getColuna(){
		return coluna;
	}

	public void setColuna( Integer coluna ){
		this.coluna = coluna;
	}

	public Integer getLinha(){
		return linha;
	}

	public void setLinha( Integer linha ){
		this.linha = linha;
	}

	public Boolean adicionarPeca( Peca peca ){
		if( pecas.size() < 2 && !pecas.contains( peca ) ){
			peca.setContainer( this );
			pecas.add( peca );
			ocupada = true;

			/* Uma casa tem o seguinte desenho imaginário:
			 *  _______
			 * |00 |01 |
			 * |___|___|
			 * |10 |11 |
			 * |___|___|
			 * 
			 * Uma casa só poderá conter no máximo 2 peças (2 jogadores) e elas estarão posicionadas da seguinte maneira:
			 *		- uma peça sempre estará na posição 00, a peça no índice 0 do vetor.
			 *		- a segunda peça estará na posição 11, a peça no índice 1 do vetor.
			 * 
			 * Ao se adicionar uma peça no vetor da casa, as coordenadas X e Yy da peça devem estar de acordo
			 * com a posição dentro da casa.
			 * Na posição 00:
			 * 		- coordenadaX: coordenadaX da casa
			 *		- coordenadaY: coordenadaY da casa
			 * Na posição 11:
			 * 		- coordenadaX: (coordenadaX da casa + metade do lado da casa)
			 *		- coordenadaY: (coordenadaY da casa + metade do lado da casa)
			 */
			switch( pecas.indexOf( peca ) ){
				case 0:
					peca.setCoordenadaX( getCoordenadaX() );
					peca.setCoordenadaY( getCoordenadaY() );
					break;

				case 1:
					peca.setCoordenadaX( ( getCoordenadaX() + ( DimensoesTabuleiro.LADO_CASA / 2 ) ) );
					peca.setCoordenadaY( ( getCoordenadaY() + ( DimensoesTabuleiro.LADO_CASA / 2 ) ) );
					break;

				default:
					break;
			}

			return true;
		}
		return false;
	}

	public Boolean removerPeca( Peca peca ){
		if( pecas.contains( peca ) ){
			ocupada = pecas.size() == 1 ? true : false;
			peca.setContainer( null );
			peca.setSelecionada( false );
			return pecas.remove( peca );
		}
		return false;
	}

	public Integer getRestricaoDeMovimento() {
		return restricaoDeMovimento;
	}

	public void setRestricaoDeMovimento(Integer restricaoDeMovimento) {
		this.restricaoDeMovimento = restricaoDeMovimento;
	}

	protected int getCoordenadaX( Integer coluna ){
		return DimensoesTabuleiro.LIMITE_INICIAL_X + ( coluna * DimensoesTabuleiro.LADO_CASA );
	}

	protected int getCoordenadaY( Integer linha ){
		return DimensoesTabuleiro.LIMITE_INICIAL_Y + ( linha * DimensoesTabuleiro.LADO_CASA );
	}

	protected int getCoordenadaX(){
		return this.getCoordenadaX( this.getColuna() );
	}

	protected int getCoordenadaY(){
		return this.getCoordenadaY( this.getLinha() );
	}

	public void desenharPecas( Graphics2D graphics, ImageObserver imageObserver ){
		for( Iterator<Peca> iterator = pecas.iterator(); iterator.hasNext(); ){
			Peca pecaAtual = iterator.next();
			// Não desenha a peça selecionada. Isso já é feito pelo tabuleiro. Caso contrário, a peça selecionada,
			// sendo arrastada, estará sendo desenhada, bem como em sua posição original, duplicando o desenho da peça.
			if( !pecaAtual.isSelecionada() ){
				graphics.drawImage(	pecaAtual.getImagem(),
									pecaAtual.getCoordenadaX(),
									pecaAtual.getCoordenadaY(),
									pecaAtual.getExtensao(),
									pecaAtual.getExtensao(),
									null );
			}
		}
	}

	public Peca getPecaSelecionada( Integer coordenadaX, Integer coordenadaY ){
		for( Iterator<Peca> iterator = pecas.iterator(); iterator.hasNext(); ){
			Peca pecaAtual = iterator.next();
			if( cliqueDentroDoIntervaloHorizontal( coordenadaX, pecaAtual ) &&
				cliqueDentroDoIntervaloVertical( coordenadaY, pecaAtual ) ){
				pecaAtual.setSelecionada( true );
				return pecaAtual;
			}
		}
		return null;
	}

	private boolean cliqueDentroDoIntervaloVertical(Integer coordenadaY, Peca pecaAtual) {
		return ( pecaAtual.getCoordenadaY() <= coordenadaY ) && ( coordenadaY <= ( pecaAtual.getCoordenadaY() + pecaAtual.getExtensao() ) );
	}

	private boolean cliqueDentroDoIntervaloHorizontal(Integer coordenadaX, Peca pecaAtual) {
		return ( pecaAtual.getCoordenadaX() <= coordenadaX ) && ( coordenadaX <= ( pecaAtual.getCoordenadaX() + pecaAtual.getExtensao() ) );
	}
}
