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
    private final BigDecimal limiteNegativoMenor = new BigDecimal("-3.40282347E+38");
    private final BigDecimal limiteNegativoMayor = new BigDecimal("-1.17549435E-38"); 
    

    @Override
    public void aplicarAccionSemantica(char c) {

        Lexico lexico = Lexico.getInstance();
        String buffer = lexico.getBuffer();
        
        lexico.addSimboloEntradaInicio(c);
        BigDecimal valor = new BigDecimal(buffer.replace('S', 'E')); 

        
       if ((valor.compareTo(new BigDecimal("0.0")) == 0) ||
            (valor.compareTo(this.limitePositivoMenor) == 1) || 
            (valor.compareTo(this.limitePositivoMayor) == -1) ||
            (valor.compareTo(this.limiteNegativoMenor) == 1) ||
            (valor.compareTo(this.limiteNegativoMayor) == -1)) {

            // agregado de la constante a la tabla de símbolos
            Map<String, Object> atributos = new HashMap<String, Object>();
            atributos.put("TIPO", "FLOAT");
            lexico.addLexemaTablaSimbolos(atributos);

            ParTokenLexema tokenLexema = new ParTokenLexema(TokensID.FLOAT, lexico.getBuffer());

            lexico.addToken(tokenLexema);
        } else {
            Error error = new Error("Flotante fuera de rango.", false, lexico.getLinea());
            lexico.addError(error);
        }

        // devolver la entrada leida.
        lexico.addSimboloEntradaInicio(c);
    }
    
}
