package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;

public class AccionSemantica9 implements AccionSemantica{

    /* 
        Limpiar Buffer.
    */

    @Override
    public void aplicarAccionSemantica(char c) {
        Lexico.getInstance().vaciarBuffer();
    }
    
}
