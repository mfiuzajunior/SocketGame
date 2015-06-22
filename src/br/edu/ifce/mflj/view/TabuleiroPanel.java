package br.edu.ifce.mflj.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.CasaComposta;
import br.edu.ifce.mflj.dados.CasaSimples;
import br.edu.ifce.mflj.dados.DimensoesTabuleiro;
import br.edu.ifce.mflj.dados.Peca;
import br.edu.ifce.mflj.jogo.Regulamento;
import br.edu.ifce.mflj.jogo.excecoes.CasaCompostaException;
import br.edu.ifce.mflj.jogo.excecoes.MovimentoException;
import br.edu.ifce.mflj.jogo.excecoes.RepositorioDePecasException;
import br.edu.ifce.mflj.observer.CanalListener;
import br.edu.ifce.mflj.observer.MovimentoListener;

public class TabuleiroPanel extends JPanel implements MouseMotionListener, MouseListener, CanalListener {

	private static final long serialVersionUID = -8383511424909321696L;

	private	Casa[][]				tabuleiroBackground,
									tabuleiroDasPecas;
	private Graphics2D				graphics;
	private Integer					ultimoXMouse,
									ultimoYMouse;
	private Peca					pecaSelecionada;
	private Regulamento				regulamento;
	private List<MovimentoListener>	listenersDeMovimento;
	private Boolean					movimentoLocal = true;

	public TabuleiroPanel(){
		tabuleiroBackground		= new Casa[ DimensoesTabuleiro.LINHAS ][ DimensoesTabuleiro.COLUNAS ];
		tabuleiroDasPecas		= new Casa[ DimensoesTabuleiro.LINHAS ][ DimensoesTabuleiro.COLUNAS ];
		listenersDeMovimento	= new ArrayList<MovimentoListener>();

		limparTabuleiro();

		posicoesIniciaisDasPecas( tabuleiroBackground );
		posicoesIniciaisDasPecas( tabuleiroDasPecas );

		reporPecasDeJogo();

		addMouseMotionListener( this );
		addMouseListener( this );

	}

	public void addMovimentoListener( MovimentoListener movimentoListener ){
		this.listenersDeMovimento.add( movimentoListener );
	}

	public void removeMovimentoListener( MovimentoListener movimentoListener ){
		this.listenersDeMovimento.remove( movimentoListener );
	}

	public void notifyMovimento( TipoPacote tipoPacote, MouseEvent mouseEvent ){
		// Só irá transmitir o movimento se ele tiver sido gerado localmente
		if( movimentoLocal ){
			for( MovimentoListener movimentoListener : this.listenersDeMovimento ){
				movimentoListener.movimentoRealizado( tipoPacote, mouseEvent  );
			}
		}
	}

	public Regulamento getRegulamento() {
		return regulamento;
	}

	public void setRegulamento(Regulamento regulamento) {
		this.regulamento = regulamento;
	}

	/**
	 * Cria casas vazias, sem peça, em todas as posições do tabuleiro
	 */
	private void limparTabuleiro(){
		for( int linhaAtual = 0; linhaAtual < DimensoesTabuleiro.LINHAS; linhaAtual++ ){
			for( int colunaAtual = 0; colunaAtual < tabuleiroBackground[ linhaAtual ].length; colunaAtual++ ){
				tabuleiroBackground[ linhaAtual ][ colunaAtual ]	= new CasaSimples( linhaAtual, colunaAtual, null );
				tabuleiroDasPecas[ linhaAtual ][ colunaAtual ]		= new CasaSimples( linhaAtual, colunaAtual, null );
			}
		}
	}

	/**
	 * Coloca as peças no bullpen, área onde as peças ficam guardadas no início do jogo.
	 */
	private void posicoesIniciaisDasPecas( Casa[][] tabuleiro ) {
		tabuleiro[ 0 ][ 0 ] = new CasaSimples( 0, 0, Peca.CASA_VAZIA );
		tabuleiro[ 0 ][ 1 ] = new CasaSimples( 0, 1, Peca.CASA_VAZIA );
		tabuleiro[ 0 ][ 2 ] = new CasaSimples( 0, 2, Peca.CASA_VAZIA );
		tabuleiro[ 0 ][ 3 ] = new CasaSimples( 0, 3, Peca.CASA_VAZIA );
		tabuleiro[ 0 ][ 4 ] = new CasaComposta( 0, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 1 ][ 0 ] = new CasaSimples( 1, 0, Peca.CASA_VAZIA );
		tabuleiro[ 1 ][ 1 ] = new CasaSimples( 1, 1, Peca.CASA_VAZIA );
		tabuleiro[ 1 ][ 2 ] = new CasaSimples( 1, 2, Peca.CASA_VAZIA );
		tabuleiro[ 1 ][ 3 ] = new CasaSimples( 1, 3, Peca.CASA_VAZIA );
		tabuleiro[ 1 ][ 4 ] = new CasaComposta( 1, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 2 ][ 0 ] = new CasaSimples( 2, 0, Peca.CASA_VAZIA );
		tabuleiro[ 2 ][ 1 ] = new CasaSimples( 2, 1, Peca.CASA_VAZIA );
		tabuleiro[ 2 ][ 2 ] = new CasaSimples( 2, 2, Peca.CASA_VAZIA );
		tabuleiro[ 2 ][ 3 ] = new CasaSimples( 2, 3, Peca.CASA_VAZIA );
		tabuleiro[ 2 ][ 4 ] = new CasaComposta( 2, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 3 ][ 0 ] = new CasaSimples( 3, 0, Peca.CASA_VAZIA );
		tabuleiro[ 3 ][ 1 ] = new CasaSimples( 3, 1, Peca.CASA_VAZIA );
		tabuleiro[ 3 ][ 2 ] = new CasaSimples( 3, 2, Peca.CASA_VAZIA );
		tabuleiro[ 3 ][ 3 ] = new CasaSimples( 3, 3, Peca.CASA_VAZIA );
		tabuleiro[ 3 ][ 4 ] = new CasaComposta( 3, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 4 ][ 0 ] = new CasaSimples( 4, 0, Peca.CASA_VAZIA );
		tabuleiro[ 4 ][ 1 ] = new CasaSimples( 4, 1, Peca.CASA_VAZIA );
		tabuleiro[ 4 ][ 2 ] = new CasaSimples( 4, 2, Peca.CASA_VAZIA );
		tabuleiro[ 4 ][ 3 ] = new CasaSimples( 4, 3, Peca.CASA_VAZIA );
		tabuleiro[ 4 ][ 4 ] = new CasaComposta( 4, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 5 ][ 0 ] = new CasaSimples( 5, 0, Peca.CASA_VAZIA );
		tabuleiro[ 5 ][ 1 ] = new CasaSimples( 5, 1, Peca.CASA_VAZIA );
		tabuleiro[ 5 ][ 2 ] = new CasaSimples( 5, 2, Peca.CASA_VAZIA );
		tabuleiro[ 5 ][ 3 ] = new CasaSimples( 5, 3, Peca.CASA_VAZIA );
		tabuleiro[ 5 ][ 4 ] = new CasaComposta( 5, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 6 ][ 0 ] = new CasaSimples( 6, 0, Peca.CASA_VAZIA );
		tabuleiro[ 6 ][ 1 ] = new CasaSimples( 6, 1, Peca.CASA_VAZIA );
		tabuleiro[ 6 ][ 2 ] = new CasaSimples( 6, 2, Peca.CASA_VAZIA );
		tabuleiro[ 6 ][ 3 ] = new CasaSimples( 6, 3, Peca.CASA_VAZIA );
		tabuleiro[ 6 ][ 4 ] = new CasaComposta( 6, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 7 ][ 0 ] = new CasaSimples( 7, 0, Peca.CASA_VAZIA );
		tabuleiro[ 7 ][ 1 ] = new CasaSimples( 7, 1, Peca.CASA_VAZIA );
		tabuleiro[ 7 ][ 2 ] = new CasaSimples( 7, 2, Peca.CASA_VAZIA );
		tabuleiro[ 7 ][ 3 ] = new CasaSimples( 7, 3, Peca.CASA_VAZIA );
		tabuleiro[ 7 ][ 4 ] = new CasaComposta( 7, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 8 ][ 0 ] = new CasaSimples( 8, 0, Peca.CASA_VAZIA );
		tabuleiro[ 8 ][ 1 ] = new CasaSimples( 8, 1, Peca.CASA_VAZIA );
		tabuleiro[ 8 ][ 2 ] = new CasaSimples( 8, 2, Peca.CASA_VAZIA );
		tabuleiro[ 8 ][ 3 ] = new CasaSimples( 8, 3, Peca.CASA_VAZIA );
		tabuleiro[ 8 ][ 4 ] = new CasaComposta( 8, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 9 ][ 0 ] = new CasaSimples( 9, 0, Peca.CASA_VAZIA );
		tabuleiro[ 9 ][ 1 ] = new CasaSimples( 9, 1, Peca.CASA_VAZIA );
		tabuleiro[ 9 ][ 2 ] = new CasaSimples( 9, 2, Peca.CASA_VAZIA );
		tabuleiro[ 9 ][ 3 ] = new CasaSimples( 9, 3, Peca.CASA_VAZIA );
		tabuleiro[ 9 ][ 4 ] = new CasaComposta( 9, 4, new Peca[ 2 ][ 2 ] );

		tabuleiro[ 11 ][ 0 ] = new CasaSimples( 11, 0, Peca.CASA_VAZIA );
		tabuleiro[ 11 ][ 1 ] = new CasaSimples( 11, 1, Peca.CASA_VAZIA );
		tabuleiro[ 11 ][ 2 ] = new CasaSimples( 11, 2, Peca.CASA_VAZIA );
		tabuleiro[ 11 ][ 3 ] = new CasaSimples( 11, 3, Peca.CASA_VAZIA );
	}
	
	private void reporPecasDeJogo() {
		tabuleiroDasPecas[ 0 ][ 5 ]		= new CasaSimples( 0, 5,	Peca.PRETA );
		tabuleiroDasPecas[ 1 ][ 5 ]		= new CasaSimples( 1, 5,	Peca.BRANCA );
		tabuleiroDasPecas[ 2 ][ 5 ]		= new CasaSimples( 2, 5,	Peca.AZUL );
		tabuleiroDasPecas[ 3 ][ 5 ]		= new CasaSimples( 3, 5,	Peca.AMARELA );
		tabuleiroDasPecas[ 4 ][ 5 ]		= new CasaSimples( 4, 5,	Peca.VERDE );
		tabuleiroDasPecas[ 5 ][ 5 ]		= new CasaSimples( 5, 5,	Peca.ROSA );
		tabuleiroDasPecas[ 6 ][ 5 ]		= new CasaSimples( 6, 5,	Peca.ROXA );
		tabuleiroDasPecas[ 7 ][ 5 ]		= new CasaSimples( 7, 5,	Peca.LARANJA );

		tabuleiroDasPecas[ 9 ][ 5 ]		= new CasaSimples( 9, 5,	Peca.CAIXA );
		tabuleiroDasPecas[ 11 ][ 5 ]	= new CasaSimples( 11, 5,	Peca.REPOR_PECAS );
	}

	@Override
	public void paintComponent( Graphics graphics ){
		super.paintComponent( graphics );
		this.graphics = ( Graphics2D )graphics.create();

		redesenharTabuleiro();
		desenharPecas();

		this.graphics.dispose();
	}

	private void redesenharTabuleiro() {
		graphics.drawImage( new ImageIcon( "imagens/plano_de_fundo.jpg" ).getImage(), 0, 0, DimensoesTabuleiro.LIMITE_FINAL_X, DimensoesTabuleiro.LIMITE_FINAL_Y, null );

		// Desenha apenas as casas vazias
		for( int linha = 0; linha < DimensoesTabuleiro.LINHAS; linha++ ){
			for( int coluna = 0; coluna < this.tabuleiroBackground[ linha ].length; coluna++ ){
				try {
					tabuleiroBackground[ linha ][ coluna ].desenharBackground( graphics, this );
				}
				catch( NullPointerException nullPointerException ){}
			}
		}
	}

	private void desenharPecas(){
		// Desenha todas as peças, menos a peça que estiver selecionada para movimento.
		for( int linha = 0; linha < DimensoesTabuleiro.LINHAS; linha++ ){
			for( int coluna = 0; coluna < this.tabuleiroDasPecas[ linha ].length; coluna++ ){
				try {
					tabuleiroDasPecas[ linha ][ coluna ].desenharPecas( graphics, this );
				}
				catch( NullPointerException nullPointerException ){}
			}
		}
		// Desenhar peça selecionada por último, para que fique por cima das demais.
		if( pecaSelecionada != null ){
			graphics.drawImage(	pecaSelecionada.getImagem(),
								pecaSelecionada.getCoordenadaX(),
								pecaSelecionada.getCoordenadaY(),
								DimensoesTabuleiro.LADO_CASA,
								DimensoesTabuleiro.LADO_CASA,
								this );
		}
	}

	private int getLinha( int coordenadaX, int coordenadaY ){
		int valorYAbsoluto = coordenadaY - DimensoesTabuleiro.LIMITE_INICIAL_Y;

		if( !DimensoesTabuleiro.mouseDentroDoTabuleiro( coordenadaX, coordenadaY ) ){
			return DimensoesTabuleiro.FORA_DOS_LIMITES;
		}

		return ( valorYAbsoluto / DimensoesTabuleiro.LADO_CASA );
	}

	private int getColuna( int coordenadaX, int coordenadaY ){
		int valorXAbsoluto = coordenadaX - DimensoesTabuleiro.LIMITE_INICIAL_X;

		if( !DimensoesTabuleiro.mouseDentroDoTabuleiro( coordenadaX, coordenadaY ) ){
			return DimensoesTabuleiro.FORA_DOS_LIMITES;
		}

		return valorXAbsoluto / DimensoesTabuleiro.LADO_CASA;
	}

	@Override
	public void mousePressed( MouseEvent mouseEvent ){
		int	coluna	= getColuna( mouseEvent.getX(), mouseEvent.getY() ),
			linha	= getLinha( mouseEvent.getX(), mouseEvent.getY() );

		ultimoXMouse = mouseEvent.getX();
		ultimoYMouse = mouseEvent.getY();

		try {
			if(	pecaSelecionavel( mouseEvent, coluna, linha ) ){
				pecaSelecionada = tabuleiroDasPecas[ linha ][ coluna ].getPecaSelecionada( ultimoXMouse, ultimoYMouse );

			} else if( tabuleiroDasPecas[ linha ][ coluna ].getPeca().getTipoPeca() == Peca.REPOR_PECAS ){
				reporPecasDeJogo();
			}

		}
		catch( NullPointerException nullPointerException ){
			System.err.println( "mousePressed" );
		}
		catch( CasaCompostaException casaCompostaException ){}

		notifyMovimento( TipoPacote.MOUSE_PRESSIONADO, mouseEvent );
	}

	@Override
	public void mouseReleased( MouseEvent mouseEvent ){
		int		linha		= getLinha( mouseEvent.getX(), mouseEvent.getY() ),
				coluna		= getColuna( mouseEvent.getX(), mouseEvent.getY() );

		try {
			Casa casaDestino  = tabuleiroDasPecas[ linha ][ coluna ];

			if( pecaSelecionada != null && regulamento.movimentoPermitido( tabuleiroDasPecas, casaDestino, pecaSelecionada ) ){
				casaDestino.posicionarPeca( tabuleiroDasPecas, mouseEvent.getY(), mouseEvent.getX(), pecaSelecionada );

			} else {
				pecaSelecionada.resetarPosicaoInicial();
			}
		}
		catch( NullPointerException | ArrayIndexOutOfBoundsException expection ){
			try {
				pecaSelecionada.resetarPosicaoInicial();
			} catch( NullPointerException nullPointerException ){
				System.err.println( "mouseReleased" );
			}
		}
		catch( MovimentoException movimentoException ){
			if( movimentoException instanceof RepositorioDePecasException ){
				tabuleiroDasPecas[ pecaSelecionada.getLinha() ][ pecaSelecionada.getColuna() ]	= new CasaSimples( linha, coluna, Peca.CASA_VAZIA );
			}
		}

		pecaSelecionada = null;

		notifyMovimento( TipoPacote.MOUSE_LIBERADO, mouseEvent );
		super.repaint();
	}

	@Override
	public void mouseDragged( MouseEvent mouseEvent ){
		if( pecaSelecionada != null && DimensoesTabuleiro.mouseDentroDoTabuleiro( mouseEvent.getX(), mouseEvent.getY() ) ){
			int		deslocamentoEmX = mouseEvent.getX() - ultimoXMouse,
					deslocamentoEmY	= mouseEvent.getY() - ultimoYMouse;

			pecaSelecionada.setCoordenadaX( pecaSelecionada.getCoordenadaX() + deslocamentoEmX );
			pecaSelecionada.setCoordenadaY( pecaSelecionada.getCoordenadaY() + deslocamentoEmY );

			ultimoXMouse = mouseEvent.getX();
			ultimoYMouse = mouseEvent.getY();

			notifyMovimento( TipoPacote.MOUSE_ARRASTADO, mouseEvent );
			super.repaint();
		}
	}

	private Boolean pecaSelecionavel( MouseEvent mouseEvent, int coluna, int linha ) throws CasaCompostaException {
		return	( DimensoesTabuleiro.mouseDentroDoTabuleiro( mouseEvent.getX(), mouseEvent.getY() ) )	&&
				( tabuleiroDasPecas[ linha ][ coluna ].getPecaSelecionada( mouseEvent.getX(), mouseEvent.getY() ).getTipoPeca() != Peca.CASA_DE_PROVA )	&&
				( tabuleiroDasPecas[ linha ][ coluna ].getPecaSelecionada( mouseEvent.getX(), mouseEvent.getY() ).getTipoPeca() != Peca.REPOR_PECAS )	&&
				( tabuleiroDasPecas[ linha ][ coluna ].getPecaSelecionada( mouseEvent.getX(), mouseEvent.getY() ).getTipoPeca() != Peca.CASA_VAZIA )	&&
				( tabuleiroDasPecas[ linha ][ coluna ].getPecaSelecionada( mouseEvent.getX(), mouseEvent.getY() ).getTipoPeca() != Peca.CAIXA )			&&
				( tabuleiroDasPecas[ linha ][ coluna ].isOcupada() );
	}

	public void mouseMoved( MouseEvent mouseEvent ){}
	public void mouseClicked( MouseEvent mouseEvent ){}
	public void mouseEntered( MouseEvent mouseEvent ){}
	public void mouseExited( MouseEvent mouseEvent ){}

	@Override
	public void tratarPacoteRecebido( Pacote pacote ){
		movimentoLocal = false;
		switch( pacote.getTipoPacote().getDescricao() ){
			case "MOUSE_PRESSIONADO":
				this.mousePressed( (MouseEvent)pacote.getPayload() );
				break;

			case "MOUSE_ARRASTADO":
				this.mouseDragged( (MouseEvent)pacote.getPayload() );
				break;

			case "MOUSE_LIBERADO":
				this.mouseReleased( (MouseEvent)pacote.getPayload() );
				break;

			default:
				break;
		}
		movimentoLocal = true;
	}
}