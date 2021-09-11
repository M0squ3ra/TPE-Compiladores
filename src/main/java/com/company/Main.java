package com.company;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.company.Analizadores.Lexico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        FileInputStream inputStream = new FileInputStream(args[1]);
        DataInputStream dataInputStr = new DataInputStream(inputStream);
        Lexico.getInstance().setData(dataInputStr);

    }
}
