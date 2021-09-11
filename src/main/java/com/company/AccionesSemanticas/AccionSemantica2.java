package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;

public class AccionSemantica2 implements AccionSemantica{

    @Override
    public void aplicarAccionSemantica(char c) {
        Lexico.getInstance().vaciarBuffer();
        Lexico.getInstance().concatenarBuffer(c);      
    }

    
}
