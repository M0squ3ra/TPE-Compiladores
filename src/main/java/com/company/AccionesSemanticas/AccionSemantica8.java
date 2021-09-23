package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;


public class AccionSemantica8 implements AccionSemantica {

    /*
        Reconocer literal e ignorarlo. En caso de que sea salto de linea, 
        se incrementara el contador de linea
    */

    @Override
    public Integer aplicarAccionSemantica(char c) {

        if (c == '\n') {
            Lexico.getInstance().addLinea();
        }

        return null;
    }
    
}
