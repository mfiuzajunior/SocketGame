package br.edu.ifce.mflj.jogo;

import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.Peca;

public class RegraCasaVazia implements Regra {

	@Override
	public Boolean movimentoValido( Casa[][] tabuleiro, Casa casaDestino, Peca pecaEmMovimento ){
		try {
			switch ( pecaEmMovimento.getTipoPeca() ) {
				case Peca.AMARELA:
				case Peca.AZUL:
				case Peca.LARANJA:
				case Peca.ROSA:
				case Peca.ROXA:
				case Peca.VERDE:
					return casaDestino.getPeca().getTipoPeca() == Peca.CASA_VAZIA;

				default:
					return true;
			}
		}
		catch( NullPointerException nullPointerException ){
			return false;

		}
	}
}
