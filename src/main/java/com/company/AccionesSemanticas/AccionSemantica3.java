package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;

public class AccionSemantica3 implements AccionSemantica{

    @Override
    public Integer aplicarAccionSemantica(char c) {
        Lexico.getInstance().concatenarBuffer(c);     
        return null;   
    }
    
}
