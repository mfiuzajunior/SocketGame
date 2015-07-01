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
import br.edu.ifce.mflj.dados.DimensoesTabuleiro;
import br.edu.ifce.mflj.dados.Peca;
import br.edu.ifce.mflj.jogo.Regulamento;
import br.edu.ifce.mflj.jogo.excecoes.CasaCompostaException;
import br.edu.ifce.mflj.jogo.excecoes.MovimentoException;
import br.edu.ifce.mflj.observer.CanalListener;
import br.edu.ifce.mflj.observer.MovimentoListener;

public class TabuleiroPanel extends JPanel implements MouseMotionListener, MouseListener, CanalListener {

	private static final long serialVersionUID = -8383511424909321696L;

	private static final int VOLTE_DUAS_CASAS	= -2;
	private static final int AVANCE_DUAS_CASAS	= 2;
	private static final int UM_TURNO_PARADO	= 0;
	private static final int ROLE_O_DADO		= -1;

	private	Casa[][]				tabuleiroDasPecas;
	private Graphics2D				graphics;
	private Integer					ultimoXMouse,
									ultimoYMouse;
	private Peca					pecaSelecionada;
	private Regulamento				regulamento;
	private List<MovimentoListener>	listenersDeMovimento;
	private Boolean					movimentoLocal = true;

	public TabuleiroPanel(){
		tabuleiroDasPecas		= new Casa[ DimensoesTabuleiro.LINHAS ][ DimensoesTabuleiro.COLUNAS ];
		listenersDeMovimento	= new ArrayList<MovimentoListener>();

		posicoesIniciaisDasPecas( tabuleiroDasPecas );

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
	 * Coloca as peças no bullpen, área onde as peças ficam guardadas no início do jogo.
	 */
	private void posicoesIniciaisDasPecas( Casa[][] tabuleiro ) {
		tabuleiro[ 0 ][ 0 ] = new Casa( 0, 0, null );
		tabuleiro[ 0 ][ 1 ] = new Casa( 0, 1, null );
		tabuleiro[ 0 ][ 2 ] = new Casa( 0, 2, null );
		tabuleiro[ 0 ][ 3 ] = new Casa( 0, 3, null );
		tabuleiro[ 0 ][ 4 ] = new Casa( 0, 4, VOLTE_DUAS_CASAS );
		tabuleiro[ 0 ][ 5 ] = new Casa( 0, 5, null );
		tabuleiro[ 0 ][ 6 ] = new Casa( 0, 6, null );

		tabuleiro[ 1 ][ 0 ] = new Casa( 1, 0, null );
		tabuleiro[ 1 ][ 1 ] = new Casa( 1, 1, null );
		tabuleiro[ 1 ][ 2 ] = new Casa( 1, 2, null );
		tabuleiro[ 1 ][ 3 ] = new Casa( 1, 3, ROLE_O_DADO );
		tabuleiro[ 1 ][ 4 ] = new Casa( 1, 4, null );
		tabuleiro[ 1 ][ 5 ] = new Casa( 1, 5, null );
		tabuleiro[ 1 ][ 6 ] = new Casa( 1, 6, UM_TURNO_PARADO );

		tabuleiro[ 2 ][ 0 ] = new Casa( 2, 0, null );
		tabuleiro[ 2 ][ 1 ] = new Casa( 2, 1, AVANCE_DUAS_CASAS );
		tabuleiro[ 2 ][ 2 ] = new Casa( 2, 2, null );
		tabuleiro[ 2 ][ 3 ] = new Casa( 2, 3, null );
		tabuleiro[ 2 ][ 4 ] = new Casa( 2, 4, null );
		tabuleiro[ 2 ][ 5 ] = new Casa( 2, 5, null );
		tabuleiro[ 2 ][ 6 ] = new Casa( 2, 6, null );

		tabuleiro[ 3 ][ 0 ] = new Casa( 3, 0, null );
		tabuleiro[ 3 ][ 1 ] = new Casa( 3, 1, ROLE_O_DADO );
		tabuleiro[ 3 ][ 2 ] = new Casa( 3, 2, null );
		tabuleiro[ 3 ][ 3 ] = new Casa( 3, 3, null );
		tabuleiro[ 3 ][ 4 ] = new Casa( 3, 4, UM_TURNO_PARADO );
		tabuleiro[ 3 ][ 5 ] = new Casa( 3, 5, null );
		tabuleiro[ 3 ][ 6 ] = new Casa( 3, 6, null );

		tabuleiro[ 4 ][ 0 ] = new Casa( 4, 0, null );
		tabuleiro[ 4 ][ 1 ] = new Casa( 4, 1, null );
		tabuleiro[ 4 ][ 2 ] = new Casa( 4, 2, null );
		tabuleiro[ 4 ][ 3 ] = new Casa( 4, 3, null );
		tabuleiro[ 4 ][ 4 ] = new Casa( 4, 4, VOLTE_DUAS_CASAS );
		tabuleiro[ 4 ][ 5 ] = new Casa( 4, 5, null );
		tabuleiro[ 4 ][ 6 ] = new Casa( 4, 6, null );

		tabuleiro[ 0 ][ 0 ].adicionarPeca( new Peca( 0, 0, Peca.AMARELA ) );
	}

	@Override
	public void paintComponent( Graphics graphics ){
		super.paintComponent( graphics );
		this.graphics = ( Graphics2D )graphics.create();

		redesenharTabuleiro();
		desenharGrade();
		desenharPecas();

		this.graphics.dispose();
	}

	private void desenharGrade() {
		// Linhas verticais
		for( int linha = 0; linha < DimensoesTabuleiro.LINHAS; linha++ ){
			this.graphics.drawLine( 0, linha * DimensoesTabuleiro.LADO_CASA, DimensoesTabuleiro.LIMITE_FINAL_X, linha * DimensoesTabuleiro.LADO_CASA);
		}

		// Linhas horizontais
		for( int linha = 0; linha < DimensoesTabuleiro.COLUNAS; linha++ ){
			this.graphics.drawLine( linha * DimensoesTabuleiro.LADO_CASA, 0, linha * DimensoesTabuleiro.LADO_CASA, DimensoesTabuleiro.LIMITE_FINAL_Y );
		}
	}

	private void redesenharTabuleiro() {
		graphics.drawImage( new ImageIcon( "imagens/tabuleiro.png" ).getImage(), 0, 0, DimensoesTabuleiro.LIMITE_FINAL_X, DimensoesTabuleiro.LIMITE_FINAL_Y, null );
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