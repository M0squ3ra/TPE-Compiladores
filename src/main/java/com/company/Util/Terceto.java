package com.company.Util;

public class Terceto {
    public String operador;
    public String operando1;
    public String operando2;
    public String tipo; // Para conversion explicita

    // Para Break
    public Terceto(String op){
        this.operador = op;
        this.operando1 = null;
        this.operando2 = null;
        this.tipo = null;
    }

    public Terceto(String op,String o1){
        this.operador = op;
        this.operando1 = o1;
        this.operando2 = null;
        this.tipo = null;
    }

    public Terceto(String o, String o1, String o2){
        this.operador = o;
        this.operando1 = o1;
        this.operando2 = o2;
        this.tipo = null;
    }

    public Terceto(String o, String o1, String o2, String tipo){
        this.operador = o;
        this.operando1 = o1;
        this.operando2 = o2;
        this.tipo = tipo;
    }

    public String getOperador(){
        return this.operador;
    }

    public String getOperando1(){
        return this.operando1;
    }

    public String getOperando2(){
        return this.operando2;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setOperador(String o){
        this.operador = o;
    }

    public void setOperando1(String o){
        this.operando1 = o;
    }

    public void setOperando2(String o){
        this.operando2 = o;
    }

    public void setTipo(String t){
        this.tipo = t;
    }

    public String toString(){
        return  "[" + this.tipo + "] (" + this.operador + ", " + this.operando1 + ", " + this.operando2 + ")";
    }
}
