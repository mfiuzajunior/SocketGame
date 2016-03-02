package br.edu.ifce.mflj.dados;

import java.awt.Graphics2D;

public class Casa {

	private Boolean		ocupada;
	private Peca		peca;

	private Integer		coordenadaX,
						coordenadaY;

	public Casa(){}

	public Casa( Integer coordenadaX, Integer coordenadaY ){
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
	}

	public Casa( Integer coordenadaX, Integer coordenadaY, Peca peca ){
		this( coordenadaX, coordenadaY );
		adicionarPeca( peca );
	}

	public Peca getPeca() {
		return peca;
	}
	
	public void setPeca(Peca peca) {
		this.peca = peca;
	}

	public Integer getCoordenadaX() {
		return coordenadaX;
	}
	
	public void setCoordenadaX(Integer coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	
	public Integer getCoordenadaY() {
		return coordenadaY;
	}
	
	public void setCoordenadaY(Integer coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public Boolean isOcupada(){
		return ocupada;
	}

	public void setOcupada( Boolean ocupada ){
		this.ocupada = ocupada;
	}

	public void adicionarPeca( Peca peca ){
		ocupada		= true;
		this.peca	= peca;

		this.peca.setCoordenadaX( getCoordenadaX() );
		this.peca.setCoordenadaY( getCoordenadaY() );
	}

	public void removerPeca(){
		setPeca( null );
		setOcupada( false );
	}

	public void desenharPeca( Graphics2D graphics ){
		if( peca != null ){
			if( !peca.isSelecionada() ){
				graphics.drawImage(	peca.getImagem(),
									getCoordenadaX(),
									getCoordenadaY(),
									peca.getExtensao(),
									peca.getExtensao(),
									null );
			}
		}
	}

	private boolean cliqueDentroDoIntervaloVertical(Integer coordenadaY) {
		return ( getCoordenadaY() <= coordenadaY ) && ( coordenadaY <= ( getCoordenadaY() + 20 ) );
	}

	private boolean cliqueDentroDoIntervaloHorizontal(Integer coordenadaX) {
		return ( getCoordenadaX() <= coordenadaX ) && ( coordenadaX <= ( getCoordenadaX() + 20 ) );
	}

	public boolean temFocoDoMouse( int xDoClique, int yDoClique) {
		return	cliqueDentroDoIntervaloHorizontal( xDoClique ) &&
				cliqueDentroDoIntervaloVertical( yDoClique );
	}
}
