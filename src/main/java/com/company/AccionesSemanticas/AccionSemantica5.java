package com.company.AccionesSemanticas;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.company.Analizadores.Lexico;
import com.company.Util.Error;
import com.company.Util.ParTokenLexema;
import com.company.Util.TokensID;

public class AccionSemantica5 implements AccionSemantica {

    /* 
        Se verifica el rango del float.
    */
    
    private final BigDecimal limitePositivoMenor = new BigDecimal("1.17549435E-38");
    private final BigDecimal limitePositivoMayor = new BigDecimal("3.40282347E+38");    
  

    @Override
    public void aplicarAccionSemantica(char c) {

        Lexico lexico = Lexico.getInstance();
        String buffer = lexico.getBuffer();
        
        String convertedBuffer = buffer.replace('S', 'E');

        BigDecimal valor = new BigDecimal(convertedBuffer); 

        if ((valor.compareTo(new BigDecimal("0.0")) == 0) ||
            ((valor.compareTo(this.limitePositivoMenor) >= 0) && 
            (valor.compareTo(this.limitePositivoMayor) <= 0))) {

            // agregado de la constante a la tabla de símbolos
            Map<String, Object> atributos = new HashMap<String, Object>();
            atributos.put("TIPO", "FLOAT");
            lexico.addLexemaTablaSimbolos(atributos);

            ParTokenLexema tokenLexema = new ParTokenLexema(TokensID.CTE, lexico.getBuffer());

            lexico.addToken(tokenLexema);
        } else {
            Error error = new Error("Flotante fuera de rango.", false, lexico.getLinea());
            lexico.addError(error);
        }

        // devolver la entrada leida.
        lexico.addSimboloEntradaInicio(c);
    }
    
}
