package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;
import com.company.Util.TokensID;

public class AccionSemantica7 implements AccionSemantica {

/*
    Devolver el caracter a la entrada y devolver token.
*/

    @Override
    public Integer aplicarAccionSemantica(char c) {

        

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
            case ":":
                token = TokensID.DOS_PUNTOS;
                break;
            default:
                break;
        };

        // devolver entrada.
        lexico.addSimboloEntradaInicio(c);

        return token;
    }

}