package com.company.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParTokenLexema {
    private int token;
    private String lexema;

    @Override
    public String toString(){
        if(this.lexema != null)
            return "["+this.token+","+this.lexema+"]";
        return "["+this.token+"]";
    }
}
