package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;

import com.company.Util.Error;
import com.company.Util.TokensID;

public class AccionSemanticaError implements AccionSemantica{

    /*
        Error de sintaxis. Tratamiento del error aplicando [TIPO DE TRATAMIENTO DE ERROR].
    */

    @Override
    public Integer aplicarAccionSemantica(char c) {
        
        Lexico lexico = Lexico.getInstance();

        Error error = new Error("Error lexico.", false, lexico.getLinea());

        lexico.addError(error);

        lexico.addSimboloEntradaInicio(c);

        return TokensID.ERROR;
    }

}
