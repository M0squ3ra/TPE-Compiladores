package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;
import com.company.Util.ParTokenLexema;
import com.company.Util.TokensID;

public class AccionSemantica7 implements AccionSemantica {

/*
    Devolver la entrada y devolver token.
*/

    @Override
    public void aplicarAccionSemantica(char c) {

        

        String buffer = Lexico.getInstance().getBuffer();
        Lexico lexico = Lexico.getInstance();
        int token = 0;

        switch (buffer) {
            case "/":
                token = TokensID.DIVIDIR;
                break;
            case "<":
                token = TokensID.MENOR;
                break;
            case ">":
                token = TokensID.MAYOR;
                break;
            default:
                break;
        };

        ParTokenLexema parTokenLexema = new ParTokenLexema(token, null);
        lexico.addToken(parTokenLexema);

        // devolver entrada.
        lexico.addSimboloEntradaInicio(c);
    }

}