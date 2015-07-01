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
		if( pecas.size() < 3 && !pecas.contains( peca ) ){
			pecas.add( peca );
			return true;
		}
		return false;
	}

	public Boolean removerPeca( Peca peca ){
		if( pecas.contains( peca ) ){
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
			graphics.drawImage(	pecaAtual.getImagem(),
								pecaAtual.getCoordenadaX(),
								pecaAtual.getCoordenadaY(),
								pecaAtual.getExtensao(),
								pecaAtual.getExtensao(),
								null );
		}
	}

	public void posicionarPeca( Casa[][] tabuleiro, Integer coordenadaYDestino, Integer coordenadaXDestino, Peca peca ){}
	public Peca getPecaSelecionada( Integer coordenadaX, Integer coordenadaY ){
		return null;
	}
}
