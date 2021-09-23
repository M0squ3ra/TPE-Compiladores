package com.company.AccionesSemanticas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.company.Analizadores.Lexico;
import com.company.Util.Error;
import com.company.Util.TokensID;

public class AccionSemantica6 implements AccionSemantica {

    /*
        Se verifica el rango del entero.
    */
    private final BigDecimal limite = new BigDecimal("32767");

    @Override
    public Integer aplicarAccionSemantica(char c) {
        
        Lexico lexico = Lexico.getInstance();
        
        // devolver entrada.
        lexico.addSimboloEntradaInicio(c);

        BigDecimal valor = new BigDecimal(lexico.getBuffer());

        if (valor.compareTo(limite) > 0) {
            Lexico.getInstance().addError(new Error(
                "Entero fuera de rango", 
                false, 
                Lexico.getInstance().getLinea()));
            
            return TokensID.ERROR;
        } else {
            // agregar entero a la tabla de símbolos.
            Map<String, Object> propiedadesLexema = new HashMap<String, Object>();
            propiedadesLexema.put("TIPO", "INT");
            lexico.addLexemaTablaSimbolos(propiedadesLexema);

            return TokensID.CTE;
        }
    }
    
}
