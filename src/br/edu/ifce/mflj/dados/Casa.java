package br.edu.ifce.mflj.dados;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

public abstract class Casa {

	private Boolean	ocupada;
	private Integer	coluna,
					linha;
	private Peca	peca;

	public Casa(){}

	public Casa( Integer linha, Integer coluna, Integer tipoPeca ){
		this.linha	= linha;
		this.coluna	= coluna;

		if( tipoPeca != null ){
			this.ocupada	= true;
			this.peca		= new Peca(	linha, coluna, tipoPeca );

		} else {
			this.ocupada = false;
		}
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

	public Peca getPeca(){
		return peca;
	}

	public void setPeca( Peca peca ){
		this.peca = peca;
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

	public abstract void desenharPecas( Graphics2D graphics, ImageObserver imageObserver );
	public abstract void desenharBackground( Graphics2D graphics, ImageObserver imageObserver );
	public abstract void posicionarPeca( Casa[][] tabuleiro, Integer coordenadaYDestino, Integer coordenadaXDestino, Peca peca );
	public abstract Peca getPecaSelecionada( Integer coordenadaX, Integer coordenadaY );
}
