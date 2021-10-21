package com.company.AccionesSemanticas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.company.Analizadores.Lexico;
import com.company.Util.Error;
import com.company.Util.TokensID;

public class AccionSemantica5 implements AccionSemantica {

    /* 
        Se verifica el rango del float.
    */
    
    private final BigDecimal limitePositivoMenor = new BigDecimal("1.17549435E-38");
    private final BigDecimal limitePositivoMayor = new BigDecimal("3.40282347E+38");    
  

    @Override
    public Integer aplicarAccionSemantica(char c) {

        Lexico lexico = Lexico.getInstance();
        String buffer = lexico.getBuffer();
        
        String convertedBuffer = buffer.replace('S', 'E');

        BigDecimal valor = new BigDecimal(convertedBuffer); 

        // devolver la entrada leida.
        lexico.addSimboloEntradaInicio(c);

        if ((valor.compareTo(new BigDecimal("0.0")) == 0) ||
            ((valor.compareTo(this.limitePositivoMenor) >= 0) && 
            (valor.compareTo(this.limitePositivoMayor) <= 0))) {

            // agregado de la constante a la tabla de símbolos
            Map<String, Object> atributos = new HashMap<String, Object>();
            atributos.put("TIPO", "SINGLE");
            lexico.addLexemaTablaSimbolos(atributos);

            return TokensID.CTE;

        } else {
            Error error = new Error("Flotante fuera de rango.", false, lexico.getLinea());
            lexico.addError(error);

            return TokensID.ERROR;
        }

    }
    
}
