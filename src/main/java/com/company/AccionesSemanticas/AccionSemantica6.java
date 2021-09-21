package com.company.AccionesSemanticas;

import java.util.HashMap;
import java.util.Map;

import com.company.Analizadores.Lexico;
import com.company.Util.Error;
import com.company.Util.ParTokenLexema;
import com.company.Util.TokensID;

public class AccionSemantica6 implements AccionSemantica {

    /*
        Se verifica el rango del entero.
    */

    private final int limiteInferior = -32768;
    private final int limiteSuperior = 32767;


    @Override
    public void aplicarAccionSemantica(char c) {
        
        Lexico lexico = Lexico.getInstance();
        
        // devolver entrada.
        lexico.addSimboloEntradaInicio(c);

        int valor = Integer.parseInt(lexico.getBuffer());


        if (valor < this.limiteInferior || valor > this.limiteSuperior) {
            Lexico.getInstance().addError(new Error(
                "Entero fuera de rango", 
                false, 
                Lexico.getInstance().getLinea()));
        } else {
            
            ParTokenLexema tokenLexema = new ParTokenLexema(TokensID.CTE, lexico.getBuffer());
            lexico.addToken(tokenLexema);

            // agregar entero a la tabla de símbolos.
            Map<String, Object> propiedadesLexema = new HashMap<String, Object>();
            propiedadesLexema.put("TIPO", "INT");
            lexico.addLexemaTablaSimbolos(propiedadesLexema);
        }
    }
    
}
