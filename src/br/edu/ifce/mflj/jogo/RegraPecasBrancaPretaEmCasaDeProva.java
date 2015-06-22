package br.edu.ifce.mflj.jogo;

import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.CasaComposta;
import br.edu.ifce.mflj.dados.Peca;

public class RegraPecasBrancaPretaEmCasaDeProva implements Regra {

	@Override
	public Boolean movimentoValido( Casa[][] tabuleiro, Casa casaDestino, Peca pecaEmMovimento ){
		try {
			switch( pecaEmMovimento.getTipoPeca() ){
				case Peca.BRANCA:
				case Peca.PRETA:
					return casaDestino instanceof CasaComposta;

				default:
					return true;
			}
		}
		catch( NullPointerException nullPointerException ){
			return false;
		}
	}
}
