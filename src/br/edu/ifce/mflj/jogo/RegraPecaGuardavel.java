package br.edu.ifce.mflj.jogo;

import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.Peca;
import br.edu.ifce.mflj.jogo.excecoes.MovimentoException;
import br.edu.ifce.mflj.jogo.excecoes.RepositorioDePecasException;

public class RegraPecaGuardavel implements Regra {

	@Override
	public Boolean movimentoValido( Casa[][] tabuleiro, Casa casaDestino, Peca pecaEmMovimento ) throws MovimentoException {
		try {
			if( casaDestino.isOcupada() && casaDestino.getPeca().getTipoPeca() == Peca.CAIXA ){
				throw new RepositorioDePecasException();
			}

		}
		catch( NullPointerException nullPointerException ){}

		return true;
	}
}
