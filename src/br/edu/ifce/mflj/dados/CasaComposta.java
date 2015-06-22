package br.edu.ifce.mflj.dados;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class CasaComposta extends Casa {

	private Peca[][] pecas;

	public CasaComposta( Integer linha, Integer coluna, Peca[][] pecas ){
		super();

		setLinha( linha );
		setColuna( coluna );
		setOcupada( true );

		if( pecas != null ){
			pecas[ 0 ][ 0 ]	= new Peca(	0, 0, Peca.CASA_DE_PROVA );
			pecas[ 0 ][ 1 ]	= new Peca(	0, 1, Peca.CASA_DE_PROVA );
			pecas[ 1 ][ 0 ]	= new Peca(	1, 0, Peca.CASA_DE_PROVA );
			pecas[ 1 ][ 1 ]	= new Peca(	1, 1, Peca.CASA_DE_PROVA );

			this.pecas = pecas;
		}
	}

	public Peca[][] getPecas(){
		return this.pecas;
	}

	@Override
	public Peca getPecaSelecionada( Integer coordenadaX, Integer coordenadaY ){
		try {
			return pecas[ getColunaInterna( coordenadaX ) ][ getLinhaInterna( coordenadaY ) ];
		}
		catch( ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException ){
			return null;
		}
	}

	@Override
	public void desenharBackground( Graphics2D graphics, ImageObserver imageObserver ){
		graphics.drawRoundRect(	getCoordenadaX( getColuna() ), getCoordenadaY( getLinha() ),
								DimensoesTabuleiro.LADO_CASA, DimensoesTabuleiro.LADO_CASA,
								15, 15 );
	}

	@Override
	public void desenharPecas( Graphics2D graphics, ImageObserver imageObserver ){
		for( int linhaDentroDaCasaComposta = 0; linhaDentroDaCasaComposta < pecas.length; linhaDentroDaCasaComposta++ ){
			for( int colunaDentroDaCasaComposta = 0; colunaDentroDaCasaComposta < pecas[ linhaDentroDaCasaComposta ].length; colunaDentroDaCasaComposta++ ){
				centralizar( pecas[ linhaDentroDaCasaComposta ][ colunaDentroDaCasaComposta ], linhaDentroDaCasaComposta, colunaDentroDaCasaComposta, graphics, imageObserver );
			}
		}
	}

	@Override
	public void posicionarPeca( Casa[][] tabuleiro, Integer coordenadaYDestino, Integer coordenadaXDestino, Peca peca ){
		pecas[ getLinhaInterna( coordenadaYDestino ) ][ getColunaInterna( coordenadaXDestino ) ] = peca;
		peca.setLinha( getLinhaInterna( coordenadaYDestino ) );
		peca.setColuna( getColunaInterna( coordenadaXDestino ) );
		peca.setCoordenadaX( obterXSuperiorEsquerdo( getColunaInterna( coordenadaXDestino ) ) );
		peca.setCoordenadaY( obterYSuperiorEsquerdo( getLinhaInterna( coordenadaYDestino ) ) );
	}

	private int getLinhaInterna( Integer coordenadaY ){
		return ( coordenadaY - this.getCoordenadaY() ) / ( DimensoesTabuleiro.LADO_CASA / 2 );
	}

	private int getColunaInterna( Integer coordenadaX ){
		return ( coordenadaX - this.getCoordenadaX() ) / ( DimensoesTabuleiro.LADO_CASA / 2 );
	}

	private void centralizar( Peca peca, int linhaDentroDaCasaComposta, int colunaDentroDaCasaComposta, Graphics2D graphics, ImageObserver imageObserver ){
		graphics.drawImage(	peca == null ? new Peca( 0, 0, Peca.CASA_DE_PROVA ).getImagem() : peca.getImagem(),
							obterXSuperiorEsquerdo( colunaDentroDaCasaComposta ),
							obterYSuperiorEsquerdo( linhaDentroDaCasaComposta ),
							DimensoesTabuleiro.LADO_CASA,
							DimensoesTabuleiro.LADO_CASA,
							imageObserver );
	}

	private int obterYSuperiorEsquerdo( int linhaDentroDaCasaComposta ){
		return ( this.getCoordenadaY( this.getLinha() ) - ( DimensoesTabuleiro.LADO_CASA / 4 ) ) + ( linhaDentroDaCasaComposta * DimensoesTabuleiro.LADO_CASA / 2 );
	}

	private int obterXSuperiorEsquerdo( int colunaDentroDaCasaComposta ){
		return ( this.getCoordenadaX( this.getColuna() ) - ( DimensoesTabuleiro.LADO_CASA / 4 ) ) + ( colunaDentroDaCasaComposta * DimensoesTabuleiro.LADO_CASA / 2 );
	}
}
