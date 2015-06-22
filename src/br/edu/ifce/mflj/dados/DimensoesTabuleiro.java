package br.edu.ifce.mflj.dados;

public class DimensoesTabuleiro {
	public static final int FORA_DOS_LIMITES	= -1,
							COLUNAS				= 6,
							LINHAS				= 12,
							LADO_CASA			= 46,
							LIMITE_INICIAL_X	= 0,
							LIMITE_INICIAL_Y	= 0,
							LIMITE_FINAL_X		= LADO_CASA * COLUNAS,
							LIMITE_FINAL_Y		= LADO_CASA * LINHAS;

	public static boolean mouseDentroDoTabuleiro( int coordenadaX, int coordenadaY ){
		if(	coordenadaY < DimensoesTabuleiro.LIMITE_INICIAL_Y	||
			coordenadaY > DimensoesTabuleiro.LIMITE_FINAL_Y ){
			return false;
		}

		if( coordenadaX < DimensoesTabuleiro.LIMITE_INICIAL_X	||
			coordenadaX > DimensoesTabuleiro.LIMITE_FINAL_X ){
			return false;
		}

		return true;
	}
}
