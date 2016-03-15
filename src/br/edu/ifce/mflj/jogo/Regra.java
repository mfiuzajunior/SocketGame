package br.edu.ifce.mflj.jogo;

import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.Peca;
import br.edu.ifce.mflj.jogo.excecoes.MovimentoException;

public interface Regra {
	Boolean movimentoValido( Casa[][] tabuleiro, Casa casaDestino, Peca pecaEmMovimento ) throws MovimentoException;
}
