package br.edu.ifce.mflj.dados;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Peca {

	public static final int		CASA_DE_PROVA	= 0,
								CASA_VAZIA		= 1,
								AZUL			= 8,
								AMARELA			= 9,
								VERDE			= 10,
								ROSA			= 11,
								ROXA			= 12,
								LARANJA			= 13,
								PRETA			= 14,
								BRANCA			= 15,
								REPOR_PECAS		= 16,
								CAIXA			= 17;
	private Image	imagem;

	private Integer	tipoPeca,
					extensao,
					limiteInicialX,
					limiteInicialY,
					coordenadaX,
					coordenadaY,
					coluna,
					linha;


	/**
	 * @param coordenadaX Determina a posição X da imagem da peça em relação ao JPanel. O campo linha da classe determina a posição X na matrix do tabuleiro.
	 * @param coordenadaY Determina a posição Y da imagem da peça em relação ao JPanel. O campo coluna da classe determina a posição Y na matrix do tabuleiro.
	 */
	public Peca( Integer linha, Integer coluna, Integer tipoPeca ){
		String nomeImagem	= "";

		this.tipoPeca		= tipoPeca;
		this.limiteInicialX	= DimensoesTabuleiro.LIMITE_INICIAL_X;
		this.limiteInicialY	= DimensoesTabuleiro.LIMITE_INICIAL_Y;
		this.extensao		= DimensoesTabuleiro.LADO_CASA;
		this.coordenadaX	= this.limiteInicialX + ( coluna * this.extensao );
		this.coordenadaY	= this.limiteInicialY + ( linha * this.extensao );

		switch( this.tipoPeca ){
			case Peca.CASA_DE_PROVA:
				nomeImagem = "imagens/casa_de_prova.png";
				break;

			case Peca.CASA_VAZIA:
				nomeImagem = "imagens/casa_vazia.png";
				break;

			case Peca.PRETA:
				nomeImagem = "imagens/preta.png";
				break;

			case Peca.BRANCA:
				nomeImagem = "imagens/branca.png";
				break;

			case Peca.AZUL:
				nomeImagem = "imagens/azul.png";
				break;

			case Peca.AMARELA:
				nomeImagem = "imagens/amarela.png";
				break;

			case Peca.VERDE:
				nomeImagem = "imagens/verde.png";
				break;

			case Peca.ROSA:
				nomeImagem = "imagens/rosa.png";
				break;

			case Peca.ROXA:
				nomeImagem = "imagens/roxa.png";
				break;

			case Peca.LARANJA:
				nomeImagem = "imagens/laranja.png";
				break;

			case Peca.REPOR_PECAS:
				nomeImagem = "imagens/mais_pecas.png";
				break;

			case Peca.CAIXA:
				nomeImagem = "imagens/caixa.png";
				break;

			default:
				break;
		}

		this.imagem = new ImageIcon( nomeImagem ).getImage();

		this.setLinha( linha );
		this.setColuna( coluna );
	}

	public Integer getExtensao() {
		return this.extensao;
	}

	public void setExtensao(Integer extensao) {
		this.extensao = extensao;
	}

	public Integer getLimiteInicialX() {
		return this.limiteInicialX;
	}
	
	public void setLimiteInicialX(Integer limiteInicialX) {
		this.limiteInicialX = limiteInicialX;
	}
	
	public Integer getLimiteInicialY() {
		return this.limiteInicialY;
	}
	
	public void setLimiteInicialY(Integer limiteInicialY) {
		this.limiteInicialY = limiteInicialY;
	}

	public Image getImagem() {
		return this.imagem;
	}

	public void setImagem(Image imagem) {
		this.imagem = imagem;
	}

	public Integer getTipoPeca() {
		return this.tipoPeca;
	}

	public void setTipoPeca(Integer tipoPeca) {
		this.tipoPeca = tipoPeca;
	}

	public Integer getCoordenadaX() {
		return this.coordenadaX;
	}

	public void setCoordenadaX(Integer coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public Integer getCoordenadaY() {
		return this.coordenadaY;
	}

	public void setCoordenadaY(Integer coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public Integer getColuna() {
		return this.coluna;
	}

	public void setColuna(Integer coluna) {
		this.coluna = coluna;
	}

	public Integer getLinha() {
		return this.linha;
	}

	public void setLinha(Integer linha) {
		this.linha = linha;
	}

	public void resetarPosicaoInicial(){
		this.coordenadaX = this.limiteInicialX + ( this.getColuna() * this.extensao );
		this.coordenadaY = this.limiteInicialY + ( this.getLinha() * this.extensao );
	}
}