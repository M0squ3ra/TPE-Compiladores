package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;

import com.company.Util.Error;

public class AccionSemanticaError implements AccionSemantica{

    /*
        Error de sintaxis. Tratamiento del error aplicando [TIPO DE TRATAMIENTO DE ERROR].
    */

    @Override
    public void aplicarAccionSemantica(char c) {
        
        Lexico lexico = Lexico.getInstance();

        Error error = new Error("Error de sintaxis.", false, lexico.getLinea());

        lexico.addError(error);
    }

}
