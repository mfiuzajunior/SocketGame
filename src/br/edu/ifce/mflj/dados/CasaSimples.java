package br.edu.ifce.mflj.dados;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public class CasaSimples extends Casa {

	public CasaSimples(){}

	public CasaSimples( Integer linha, Integer coluna, Integer tipoPeca ){
		super( linha, coluna, tipoPeca );
	}

	@Override
	public void desenharBackground( Graphics2D graphics, ImageObserver imageObserver ){
		try {
			if( getPeca().getTipoPeca() == Peca.CASA_VAZIA ){
				graphics.drawImage(	getPeca().getImagem(),
									getPeca().getCoordenadaX(), 
									getPeca().getCoordenadaY(),
									DimensoesTabuleiro.LADO_CASA,
									DimensoesTabuleiro.LADO_CASA,
									imageObserver );
			}
		}
		catch( NullPointerException nullPointerException ){}
	}

	@Override
	public void desenharPecas( Graphics2D graphics, ImageObserver imageObserver ){
		if( getPeca().getTipoPeca() != Peca.CASA_VAZIA ){
			graphics.drawImage(	getPeca().getImagem(),
								getPeca().getCoordenadaX(), 
								getPeca().getCoordenadaY(),
								DimensoesTabuleiro.LADO_CASA,
								DimensoesTabuleiro.LADO_CASA,
								imageObserver );
		}
	}

	@Override
	public Peca getPecaSelecionada( Integer coordenadaX, Integer coordenadaY ){
		return getPeca();
	}

	@Override
	public void posicionarPeca( Casa[][] tabuleiro, Integer coordenadaYDestino, Integer coordenadaXDestino, Peca peca ){
		tabuleiro[ peca.getLinha() ][ peca.getColuna() ]	= new CasaSimples( peca.getLinha(), peca.getColuna(), Peca.CASA_VAZIA );
		tabuleiro[ getLinha() ][ getColuna() ]				= new CasaSimples( getLinha(), getColuna(), peca.getTipoPeca() );
	}
}