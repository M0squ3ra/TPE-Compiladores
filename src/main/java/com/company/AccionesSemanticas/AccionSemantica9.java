package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;

public class AccionSemantica9 implements AccionSemantica{

    /* 
        Limpiar Buffer.
    */

    @Override
    public Integer aplicarAccionSemantica(char c) {
        Lexico.getInstance().vaciarBuffer();
        return null;
    }
    
}
