package com.company.AccionesSemanticas;

import com.company.Analizadores.Lexico;
import com.company.Util.TokensID;


public class AccionSemantica1 implements AccionSemantica {

    /*
        Reconocer literal y devolver token.
    */

    @Override
    public Integer aplicarAccionSemantica(char c) {
        
        int token = 0;

        Lexico lexico = Lexico.getInstance();
        String buffer = lexico.getBuffer();

        switch (c) {
            case '(':
                token = TokensID.PARENTESIS_ABRE;
                break;
            case ')':
                token = TokensID.PARENTESIS_CIERRA;
                break;
            case ',':
                token = TokensID.COMA;
                break;
            case ';':
                token = TokensID.PUNTO_Y_COMA;
                break;
            case '+':
                token = TokensID.MAS;
                break;
            case '*':
                token = TokensID.POR;
                break;
            case '-':
                token = TokensID.MENOS;
                break;
            case '&':
                if (buffer.equals("&"))
                    token = TokensID.AND;
                break;
            case '|':
                if (buffer.equals("|"))
                    token = TokensID.OR;
                break;
            case '>':
                if (buffer.equals("<"))
                    token = TokensID.DIFERENTE;
                break;
            case '=':
                if (buffer.equals(">")) {    
                    token = TokensID.MAYOR_IGUAL;
                } else if (buffer.equals("<")) {
                    token = TokensID.MENOR_IGUAL;
                } else if (buffer.equals("=")) {
                    token = TokensID.IGUALDAD;
                } else if (buffer.equals(":")) {
                    token = TokensID.ASIGNACION;
                }
                break;
            default:
                break;
        };

        
        return token;
    }
    
}
