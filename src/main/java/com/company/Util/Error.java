package com.company.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    private String message;
    private boolean warning;
    private int line;

    @Override
    public String toString() {
        String errorType = ((this.warning) ? "WARNING" : "ERROR");
        return "[" + errorType + "] - linea " + this.line + " - " + this.message;
    }

}