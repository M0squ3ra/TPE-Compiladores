//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
    package com.company.Analizadores;    
    import com.company.Analizadores.Lexico;
    import com.company.Analizadores.ParserVal;
    import com.company.Util.Error;
    import com.company.Util.Terceto;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.io.IOException;
    import java.math.BigDecimal;
    import java.util.Stack;
//#line 29 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short MAYOR_IGUAL=257;
public final static short MENOR_IGUAL=258;
public final static short IGUALDAD=259;
public final static short DIFERENTE=260;
public final static short CADENA=261;
public final static short AND=262;
public final static short OR=263;
public final static short IDENTIFICADOR=264;
public final static short ASIGNACION=265;
public final static short CTE=266;
public final static short ERROR=267;
public final static short INT=268;
public final static short IF=269;
public final static short THEN=270;
public final static short ELSE=271;
public final static short ENDIF=272;
public final static short PRINT=273;
public final static short FUNC=274;
public final static short RETURN=275;
public final static short BEGIN=276;
public final static short END=277;
public final static short BREAK=278;
public final static short SINGLE=279;
public final static short REPEAT=280;
public final static short PRE=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    4,    4,    4,    4,    2,    2,
    2,    2,    2,    2,    8,    8,   11,   11,   11,   11,
   11,   11,    7,    7,   13,   13,   13,   13,   14,   15,
   18,   12,   12,   20,   20,   20,    6,    6,   21,   10,
   10,    5,    5,    5,   23,   23,    3,    3,   22,   22,
   22,   22,   22,   22,   22,   22,   24,   28,   29,   30,
   29,   31,   33,   25,   32,   26,   26,   27,   27,   27,
   17,   17,   17,   36,   16,   16,   16,   37,   37,   37,
   19,   19,   39,   38,   38,   38,   38,   38,   34,   34,
   34,   34,   34,   34,   35,   35,    9,    9,
};
final static short yylen[] = {                            2,
    5,    4,    1,    2,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    0,    7,    1,    6,    6,    6,    3,    1,    3,    3,
    1,    3,    1,    1,    3,    3,    2,    1,    4,    5,
    2,    1,    1,    5,    1,    1,    6,    3,    1,    0,
    4,    0,    0,   13,    3,    4,    4,    4,    4,    4,
    3,    3,    1,    4,    3,    3,    1,    3,    3,    1,
    2,    1,    2,    1,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    4,    0,   98,    0,   97,    0,
   12,   13,   14,    0,   16,    0,   33,   38,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   48,   52,
   53,   55,   56,    0,    0,    0,    9,   10,   11,   43,
   44,    0,   41,    0,    0,    0,    0,   24,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,    0,
    2,   47,    0,    0,    0,    0,    7,    0,    6,    0,
   39,    0,    0,   31,    0,   37,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,   85,    0,    0,
    0,   87,    0,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   40,    0,    0,    0,    0,
    0,    0,    0,    0,   82,    0,    8,    0,    0,   86,
   66,    0,    0,    0,    0,   67,   49,    0,   91,   92,
   93,   94,   95,   96,   90,   89,   58,    0,    0,    0,
    0,   68,   70,    0,   69,    0,   42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   83,   81,   34,    0,    0,    0,    0,    0,   78,
   79,   54,    0,    0,   60,    0,   50,   62,   35,    0,
   36,    0,    0,    0,    0,    0,    0,   29,    0,    0,
   26,    0,    0,   17,   88,   74,    0,   57,    0,   18,
   19,   32,   20,   21,   22,   15,    0,   25,    0,   28,
   61,    0,    0,   27,    0,   63,    0,    0,    0,   30,
    0,    0,    0,    0,   64,
};
final static short yydgoto[] = {                          2,
    3,   10,   63,    4,   36,   11,   12,   13,   14,   45,
   15,   16,   48,   81,   82,   98,   99,  113,  124,   17,
   18,   29,   41,   30,   31,   32,   33,   56,  151,  207,
  209,  226,  229,  148,  149,   92,   93,   94,  125,
};
final static short yysindex[] = {                      -241,
  -16,    0, -223,    0,    0, -153,    0,  207,    0,   99,
    0,    0,    0, -216,    0, -184,    0,    0, -211,  -32,
 -157, -188,  -36,   40,  -27,   30,   83,  129,    0,    0,
    0,    0,    0,  117,  132, -129,    0,    0,    0,    0,
    0,  -33,    0,  -31,   -9,  -90, -184,    0,  111, -115,
  207,  -42,  -43,  -42,  -42, -105,  127,  -26,    0,  -95,
    0,    0,  147,  157,  207,  167,    0,    0,    0,  141,
    0, -115,  -40,    0, -214,    0,  -80,  152,  135,  177,
  140,  177,    0, -115,  160,  187,  171,    0,  172,  -61,
   28,    0,  103,    0,  148,  101,  153,  144,   52,  132,
  155,   -2,  156,  -45,    0,  197, -188,    0, -115,  185,
 -115,  199,  198,  200,  -29,    0,  -42,  203,  193,  -23,
  177,  201,  -84,  215,    0,   -6,    0,  -42,  -42,    0,
    0,  -42,  -42,  -42,  -42,    0,    0,  202,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -42,  -42,  -12,
  -10,    0,    0,  204,    0,    2,    0,  223,   -6,  225,
   -6, -115,   -6,   -6,  -78,  159,  -42,   -8,  212,  218,
    6,    0,    0,    0,   26,  165,  210,  103,  103,    0,
    0,    0,  144,  144,    0,  226,    0,    0,    0,   50,
    0,   57,  245,   74,   93,  228,   96,    0,   72,  231,
    0,   15,  234,    0,    0,    0,  132,    0,  235,    0,
    0,    0,    0,    0,    0,    0,  255,    0,  247,    0,
    0,   49,   55,    0,  -41,    0,  258,  -42,  259,    0,
  144,   53,  279,  132,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  321,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -13,    0,    0,    0,
    0,    0,   38,    0,    0,    0,    0,   79,    0,    0,
    0,    0,    0,    0,    0,    0,    1,    5,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,   65,    0,
    0,    0,   87,   94,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  274,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  151,    9,    0,  -25,    4,   17,   31,   86,   12,
    0,    0,  293,  -60,    0,   14,  176,    0,  -93,    0,
    0,   -4,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  123,    0,    0,  -96,   -5,    0,
};
final static int YYTABLESIZE=487;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        111,
   46,   90,   90,   54,    1,   40,   72,   50,   75,   67,
   43,  165,   58,   37,  103,  158,   28,  160,  145,  119,
  146,  122,    1,   62,   37,   71,   38,   84,   84,   84,
   68,   84,    6,   84,   77,  178,  179,   38,  154,   42,
   39,  114,    5,   66,    7,   84,   84,   43,   84,   76,
   37,   39,    8,    7,   80,    9,  153,   44,   62,   86,
  170,   62,   20,   38,    9,   91,   96,   97,  193,   77,
  132,   19,  133,  106,  150,   62,   52,   39,   77,   55,
   77,   62,   77,    7,  204,   75,  131,   75,   59,   75,
  121,   46,  147,   77,    9,   40,   77,   77,   19,   77,
   77,   62,   19,   75,   75,   76,   75,   76,  210,   76,
    7,  145,  217,  146,    7,  211,   62,   77,   51,   73,
   20,    9,   60,   76,   76,    9,   76,   72,  180,  181,
  166,  145,  213,  146,   71,   85,   77,  175,   73,   77,
   73,  176,  177,  132,  134,  133,   72,   69,   72,  135,
   84,  214,    7,   71,  216,   71,   21,  110,  112,  137,
  115,  183,  184,    9,  100,   22,   47,  101,  104,  123,
  190,  172,  192,   23,  194,  195,  197,  196,   24,  173,
  109,  221,   25,  116,   78,   43,  132,   26,  133,   27,
   79,  117,  118,  138,  123,  132,  123,  133,  120,  198,
  126,  132,   40,  133,  130,  205,  136,  132,  235,  133,
  128,  129,   95,  152,  155,  139,  140,  141,  142,  156,
   87,   87,   88,   88,   73,  159,  164,    7,   53,   40,
   70,   49,   74,   57,  102,   89,   89,  162,    9,  161,
  163,  231,  167,   84,   84,   84,   84,  123,   84,   84,
  206,  168,  132,  169,  133,  174,   46,   43,  185,  171,
  182,  186,  187,  189,   46,  191,   48,  188,  200,   46,
  201,   46,   46,   46,   48,   46,  202,   46,   46,   48,
   46,   42,  203,   48,  208,  212,  215,   48,   48,  218,
   48,  219,  220,  222,   77,   77,   77,   77,  223,   77,
   77,   75,   75,   75,   75,  224,   75,   75,  139,  140,
  141,  142,  225,  143,  144,  227,  230,  232,  233,  234,
    5,   76,   76,   76,   76,   59,   76,   76,  139,  140,
  141,  142,   65,  143,  144,   73,   73,   73,   73,   83,
   73,   73,  199,   72,   72,   72,   72,  228,   72,   72,
   71,   71,   71,   71,   34,   71,   71,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    7,   24,    0,    0,
    0,   25,   22,    0,   35,    0,   26,    9,   27,    0,
   23,   52,    0,    0,   22,   24,    0,   64,    0,   25,
   20,    0,   23,    0,   26,   23,   27,   24,    0,    0,
   24,   25,   22,    0,   25,   61,   26,   65,   27,   26,
   23,   27,   22,    0,    0,   24,    0,    0,    0,   25,
   23,   52,  107,  105,   26,   24,   27,    0,    0,   25,
   23,    0,   22,    0,   26,   24,   27,    0,    0,   25,
   23,    0,   22,  108,   26,   24,   27,    0,    0,   25,
   23,   78,  107,    0,   26,   24,   27,    0,    0,   25,
   23,    0,   22,  127,   26,   24,   27,    0,    0,   25,
   23,    0,    0,  157,   26,   24,   27,    0,    0,   25,
    0,    0,    0,    0,   26,    0,   27,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   45,   40,    0,   10,   40,   40,   40,   35,
    0,   41,   40,   10,   41,  109,    8,  111,   60,   80,
   62,   82,  264,   28,   21,   59,   10,   41,   42,   43,
   35,   45,  256,   47,   44,  132,  133,   21,   41,  256,
   10,  256,   59,   35,  268,   59,   60,  264,   62,   59,
   47,   21,  276,  268,   46,  279,   59,  274,   63,   51,
  121,   66,  274,   47,  279,   52,   53,   54,  162,   44,
   43,  256,   45,   65,  100,   80,  265,   47,   41,   40,
   43,   86,   45,  268,   59,   41,   59,   43,   59,   45,
   82,  276,   41,   44,  279,  100,   59,   60,  256,   62,
   44,  106,  256,   59,   60,   41,   62,   43,   59,   45,
  268,   60,   41,   62,  268,   59,  121,   44,  276,   41,
  274,  279,   40,   59,   60,  279,   62,   41,  134,  135,
  117,   60,   59,   62,   41,   50,   44,  126,   60,   44,
   62,  128,  129,   43,   42,   45,   60,  277,   62,   47,
   40,   59,  268,   60,   59,   62,    6,   72,   73,   59,
   75,  148,  149,  279,  270,  256,   16,   41,  264,   84,
  159,  256,  161,  264,  163,  164,  165,  256,  269,  264,
   40,  207,  273,  264,  275,  264,   43,  278,   45,  280,
  281,   40,   58,   41,  109,   43,  111,   45,   59,   41,
   41,   43,  207,   45,  266,   41,   59,   43,  234,   45,
   40,   40,  256,   59,   59,  257,  258,  259,  260,  265,
  264,  264,  266,  266,  256,   41,  256,  268,  265,  234,
  264,  264,  264,  261,  261,  279,  279,   40,  279,   41,
   41,  228,   40,  257,  258,  259,  260,  162,  262,  263,
   41,   59,   43,  277,   45,   41,  256,  264,  271,   59,
   59,  272,   59,   41,  264,   41,  256,  266,  277,  269,
   59,  271,  272,  273,  264,  275,   59,  277,  278,  269,
  280,  277,  277,  273,   59,   41,   59,  277,  278,   59,
  280,  277,   59,   59,  257,  258,  259,  260,   44,  262,
  263,  257,  258,  259,  260,   59,  262,  263,  257,  258,
  259,  260,  264,  262,  263,  261,   59,   59,  266,   41,
    0,  257,  258,  259,  260,  272,  262,  263,  257,  258,
  259,  260,   59,  262,  263,  257,  258,  259,  260,   47,
  262,  263,  167,  257,  258,  259,  260,  225,  262,  263,
  257,  258,  259,  260,  256,  262,  263,   -1,   -1,   -1,
   -1,   -1,  264,   -1,   -1,   -1,  268,  269,   -1,   -1,
   -1,  273,  256,   -1,  276,   -1,  278,  279,  280,   -1,
  264,  265,   -1,   -1,  256,  269,   -1,  256,   -1,  273,
  274,   -1,  264,   -1,  278,  264,  280,  269,   -1,   -1,
  269,  273,  256,   -1,  273,  277,  278,  276,  280,  278,
  264,  280,  256,   -1,   -1,  269,   -1,   -1,   -1,  273,
  264,  265,  256,  277,  278,  269,  280,   -1,   -1,  273,
  264,   -1,  256,   -1,  278,  269,  280,   -1,   -1,  273,
  264,   -1,  256,  277,  278,  269,  280,   -1,   -1,  273,
  264,  275,  256,   -1,  278,  269,  280,   -1,   -1,  273,
  264,   -1,  256,  277,  278,  269,  280,   -1,   -1,  273,
  264,   -1,   -1,  277,  278,  269,  280,   -1,   -1,  273,
   -1,   -1,   -1,   -1,  278,   -1,  280,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=283;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"MAYOR_IGUAL","MENOR_IGUAL","IGUALDAD",
"DIFERENTE","CADENA","AND","OR","IDENTIFICADOR","ASIGNACION","CTE","ERROR",
"INT","IF","THEN","ELSE","ENDIF","PRINT","FUNC","RETURN","BEGIN","END","BREAK",
"SINGLE","REPEAT","PRE","\"&&\"","\"||\"",
};
final static String yyrule[] = {
"$accept : PROGRAMA",
"PROGRAMA : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END",
"PROGRAMA : PROGRAMA_ENCABEZADO BEGIN CONJUNTO_SENTENCIAS END",
"PROGRAMA : PROGRAMA_ERROR",
"PROGRAMA_ENCABEZADO : IDENTIFICADOR ';'",
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA",
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BLOQUE_SENTENCIA END",
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN BLOQUE_SENTENCIA",
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO error SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA DECLARACION_VARIABLES",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA DECLARACION_FUNC",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA ASIGNACION_FUNC_VAR",
"SENTENCIA_DECLARATIVA : DECLARACION_VARIABLES",
"SENTENCIA_DECLARATIVA : DECLARACION_FUNC",
"SENTENCIA_DECLARATIVA : ASIGNACION_FUNC_VAR",
"ASIGNACION_FUNC_VAR : TIPO FUNC '(' TIPO ')' VARIABLES ';'",
"ASIGNACION_FUNC_VAR : ASIGNACION_FUNC_VAR_ERROR",
"ASIGNACION_FUNC_VAR_ERROR : error FUNC '(' TIPO ')' VARIABLES ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO error '(' TIPO ')' VARIABLES ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC error TIPO ')' VARIABLES ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC '(' error ')' VARIABLES ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC '(' TIPO error VARIABLES ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC '(' TIPO ')' error ';'",
"DECLARACION_FUNC : ENCABEZADO_FUNC SENTENCIA_DECLARATIVA CUERPO_FUNC",
"DECLARACION_FUNC : ENCABEZADO_FUNC CUERPO_FUNC",
"CUERPO_FUNC : BEGIN CONJUNTO_SENTENCIAS RETURN_FUNC ';' END ';'",
"CUERPO_FUNC : BEGIN RETURN_FUNC ';' END ';'",
"CUERPO_FUNC : BEGIN PRECONDICION_FUNC CONJUNTO_SENTENCIAS RETURN_FUNC ';' END ';'",
"CUERPO_FUNC : BEGIN PRECONDICION_FUNC RETURN_FUNC ';' END ';'",
"RETURN_FUNC : RETURN '(' EXPRESION ')'",
"PRECONDICION_FUNC : PRE ':' '(' CONDICION ')' ',' CADENA ';'",
"$$1 :",
"ENCABEZADO_FUNC : TIPO FUNC IDENTIFICADOR $$1 '(' PARAMETRO ')'",
"ENCABEZADO_FUNC : ENCABEZADO_FUNC_ERROR",
"ENCABEZADO_FUNC_ERROR : error FUNC IDENTIFICADOR '(' PARAMETRO ')'",
"ENCABEZADO_FUNC_ERROR : TIPO error IDENTIFICADOR '(' PARAMETRO ')'",
"ENCABEZADO_FUNC_ERROR : TIPO FUNC error '(' PARAMETRO ')'",
"DECLARACION_VARIABLES : TIPO VARIABLES ';'",
"DECLARACION_VARIABLES : DECLARACION_VARIABLES_ERROR",
"DECLARACION_VARIABLES_ERROR : TIPO error ';'",
"VARIABLES : VARIABLES ',' IDENTIFICADOR",
"VARIABLES : IDENTIFICADOR",
"BLOQUE_SENTENCIA : BEGIN CONJUNTO_SENTENCIAS END",
"BLOQUE_SENTENCIA : SENTENCIA_EJECUTABLE",
"BLOQUE_SENTENCIA : BLOQUE_SENTENCIA_ERROR",
"BLOQUE_SENTENCIA_ERROR : error CONJUNTO_SENTENCIAS END",
"BLOQUE_SENTENCIA_ERROR : BEGIN CONJUNTO_SENTENCIAS error",
"CONJUNTO_SENTENCIAS : CONJUNTO_SENTENCIAS SENTENCIA_EJECUTABLE",
"CONJUNTO_SENTENCIAS : SENTENCIA_EJECUTABLE",
"SENTENCIA_EJECUTABLE : IDENTIFICADOR ASIGNACION EXPRESION ';'",
"SENTENCIA_EJECUTABLE : PRINT '(' CADENA ')' ';'",
"SENTENCIA_EJECUTABLE : BREAK ';'",
"SENTENCIA_EJECUTABLE : SENTENCIA_IF",
"SENTENCIA_EJECUTABLE : SENTENCIA_REPEAT",
"SENTENCIA_EJECUTABLE : IDENTIFICADOR '(' EXPRESION ')' ';'",
"SENTENCIA_EJECUTABLE : ASIGNACION_ERROR",
"SENTENCIA_EJECUTABLE : PRINT_ERROR",
"SENTENCIA_IF : IF CONDICION_IF THEN CUERPO_IF ENDIF ';'",
"CONDICION_IF : '(' CONDICION ')'",
"CUERPO_IF : BLOQUE_SENTENCIA",
"$$2 :",
"CUERPO_IF : BLOQUE_SENTENCIA ELSE $$2 BLOQUE_SENTENCIA",
"$$3 :",
"$$4 :",
"SENTENCIA_REPEAT : REPEAT '(' IDENTIFICADOR ASIGNACION CTE $$3 ';' CONDICION_REPEAT $$4 ';' CTE ')' BLOQUE_SENTENCIA",
"CONDICION_REPEAT : IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION",
"ASIGNACION_ERROR : error ASIGNACION EXPRESION ';'",
"ASIGNACION_ERROR : IDENTIFICADOR ASIGNACION error ';'",
"PRINT_ERROR : PRINT CADENA ')' ';'",
"PRINT_ERROR : PRINT '(' ')' ';'",
"PRINT_ERROR : PRINT '(' CADENA ';'",
"CONDICION : CONDICION OPERADOR_LOGICO EXPRESION",
"CONDICION : CONDICION OPERADOR_COMPARADOR EXPRESION",
"CONDICION : EXPRESION",
"CONVERSION : SINGLE '(' EXPRESION ')'",
"EXPRESION : EXPRESION '+' TERMINO",
"EXPRESION : EXPRESION '-' TERMINO",
"EXPRESION : TERMINO",
"TERMINO : TERMINO '*' FACTOR",
"TERMINO : TERMINO '/' FACTOR",
"TERMINO : FACTOR",
"PARAMETRO : TIPO IDENTIFICADOR",
"PARAMETRO : PARAMETRO_ERROR",
"PARAMETRO_ERROR : TIPO error",
"FACTOR : IDENTIFICADOR",
"FACTOR : CTE",
"FACTOR : '-' CTE",
"FACTOR : CONVERSION",
"FACTOR : IDENTIFICADOR '(' EXPRESION ')'",
"OPERADOR_COMPARADOR : '>'",
"OPERADOR_COMPARADOR : '<'",
"OPERADOR_COMPARADOR : MAYOR_IGUAL",
"OPERADOR_COMPARADOR : MENOR_IGUAL",
"OPERADOR_COMPARADOR : IGUALDAD",
"OPERADOR_COMPARADOR : DIFERENTE",
"OPERADOR_LOGICO : AND",
"OPERADOR_LOGICO : OR",
"TIPO : SINGLE",
"TIPO : INT",
};

//#line 266 "gramatica.y"

    private Lexico lexico = Lexico.getInstance();
    private List<Error> erroresSintacticos = new ArrayList<Error>(); 
    private List<Error> erroresSemanticos = new ArrayList<Error>(); 
    private List<Integer> tokensReconocidos = new ArrayList<Integer>(); 
    private List<String> estructurasReconocidas = new ArrayList<String>();
    private List<String> variables = new ArrayList<String>();
    private String tipo;
    private String nombrePrograma;
    private List<String> ambitoActual = new ArrayList<String>();
    private List<Terceto> tercetos = new ArrayList<Terceto>();
    private Stack<Terceto> backpatching = new Stack<Terceto>();
    private boolean errorSemantico = false;

    
    public static void main(String args[]){
        
    }

    public int yylex(){ 
        
        int token = 0;

        try{
            token = lexico.yylex();
        } catch(IOException e){
            
        }
        
        // En el caso que sea IDENTIFICADOR,CTE o STRING obtiene 
        // la referencia a la tabla de simbolos
        if(token  == 261 || token == 264 || token == 266) 
            yylval = lexico.getyylval();
	
        tokensReconocidos.add(token);

        return token;
    }

    public List<Integer> getTokensReconocidos(){
        return tokensReconocidos;
    }

    public void yyerror(String error){
        erroresSintacticos.add(new Error(error,false,lexico.getLinea()));
    }

    public void yyerrorSemantico(String error){
        errorSemantico = true;
        erroresSemanticos.add(new Error(error,false,lexico.getLinea()));
    }
    
    public List<Error> getErroresSintacticos(){
        return erroresSintacticos;
    }

    public List<Error> getErroresSemanticos(){
        return erroresSemanticos;
    }

    public List<String> getEstructurasReconocidas(){
        return estructurasReconocidas;
    }
    
    public boolean checkRango(String lexema){
        if(lexico.getAtributosLexema(lexema).get("TIPO").equals("INT")){
            BigDecimal valor = new BigDecimal(lexema);
            BigDecimal limite = new BigDecimal("32767");
            if(valor.compareTo(limite) > 0)
                return false;
        }
        return true;
    }
    
    public void addEstructura(String s){
        estructurasReconocidas.add(String.format("%-15s", "[Linea "+String.valueOf(lexico.getLinea())+"]") + s);
    }
    
    public void addAtributoLexema(String lexema, String nombreAtributo, Object atributo){
        Map<String, Object> atributos;
        atributos = lexico.getAtributosLexema(lexema);
        atributos.put(nombreAtributo, atributo);
    }

    public void addTipoVariables(){
        Map<String, Object> atributos;
        for(String lexema: variables){
            atributos = lexico.getAtributosLexema(lexema);
            atributos.put("TIPO", tipo);
        }
        variables.clear();
    }
/*
    public void addUsoIdentificador(String lexema, String uso) {
        Map<String, Object> atributos = lexico.getAtributosLexema(lexema);
        atributos.put("USO", uso);        
    }
*/
    public String getAmbito(List<String> ambitoActual) {
        // a partir de una lista de strings, se devuelve el ambito con el formato: '.ambito1.ambito2'.
        String ambito = "";
        for (String amb: ambitoActual) {
            ambito = "." + amb + ambito;
        }  
        return ambito; 
    }

    public String setAmbitoIdentificador(String lexema) {
        String ambito = getAmbito(ambitoActual);
        lexico.addAmbitoIdentificador(lexema,ambito);
        return lexema + ambito;
    }

    public boolean estaEnTablaSimbolos(String lexema) {
        // dado un identificador, se le agrega el ambito y se verifica si ya existe en la tabla de simbolos.
        return lexico.getTablaSimbolos().containsKey(lexema + getAmbito(ambitoActual));
    }

    public String getAmbitoIdentificador(String identificador) {
        // devuelde el identificador junto al ámbito más cercano al actual. Si no existe, devuelve null;
        Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos();
        int cantidadAmbitos = ambitoActual.size();
        for (int i = 0; i < cantidadAmbitos; i++) {
            List<String> ambitoAux = ambitoActual.subList(i, cantidadAmbitos);
            String identificadorAux = identificador + getAmbito(ambitoAux);
            if (tablaSimbolos.containsKey(identificadorAux)) {
                tablaSimbolos.remove(identificador);
                return identificadorAux;
            }
        }
        //yyerrorSemantico("Identificador no accesible en el ámbito actual.");
        return null;
    }

    public ParserVal getReferenciaUltimaInstruccion() {
        return new ParserVal("["+ (tercetos.size() - 1) + "]");
    }

    public List<Terceto> getTercetos(){
        return tercetos;
    }

    public void verificarRedeclaracion(String identificador, String uso) {
        if (getAmbitoIdentificador(identificador) != null) {
            yyerrorSemantico("Identificador ya utilizado en el ámbito.");
        } else {
            identificador = setAmbitoIdentificador(identificador);
            variables.add(identificador);
            addAtributoLexema(identificador,"USO",uso);
        }
    }

    public String getTipo(String o){
        if(!o.startsWith("[")){
            Lexico lexico = Lexico.getInstance();
            return (String) lexico.getAtributosLexema(o).get("TIPO");
        } 
        else {
            
            String tipo = null;
            if(tercetos.size() > 0){ //Para cuando el error semantico se da antes de agregar cualquier terceto
                Terceto t = tercetos.get((Integer.parseInt(o.substring(1, o.length() - 1 ))));
                tipo = t.getTipo();
            }
            return tipo;
        }
    }

    // Como la conversion es explicita no es necesario armar una tabla de tipos
    public boolean checkTipos(String o1, String o2){
        if(!getTipo(o1).equals(getTipo(o2))){
            yyerrorSemantico("Tipos incompatibles");
            return false;
        }
        return  true;
    }

    public boolean checkDeclarado(String o1, String o2){
        if(o1 == null || o2 == null){
            return false;
        }
        return true;
    }

    public boolean checkDeclarado(String o){
        if(o == null){
            return false;
        }
        return true;
    }

    public void addTercetoAritmetica(String o, String o1, String o2){
        if(!checkDeclarado(o1,o2)){
            yyerrorSemantico("Variable no declarada.");
        } else{
            if(checkTipos(o1, o2))
                addTerceto(new Terceto(o, o1 , o2, getTipo(o1)));
            else
                addTerceto(new Terceto(o, o1 , o2, "Error de tipo"));
        }         
    }

    public void addTerceto(Terceto t){
        if(!errorSemantico)
            tercetos.add(t);
    }
    

//#line 684 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
public int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 4:
//#line 29 "gramatica.y"
{ambitoActual.add(0, val_peek(1).sval); addAtributoLexema(val_peek(1).sval,"USO","Nombre de Programa");}
break;
case 5:
//#line 32 "gramatica.y"
{yyerror("Bloque principal no especificado.");}
break;
case 6:
//#line 33 "gramatica.y"
{yyerror("BEGIN del bloque principal no especificado.");}
break;
case 7:
//#line 34 "gramatica.y"
{yyerror("END del bloque principal no especificado.");}
break;
case 8:
//#line 35 "gramatica.y"
{yyerror("Falta el nombre del programa.");}
break;
case 15:
//#line 46 "gramatica.y"
{addEstructura("Declaracion de varables");
                                                                        addTipoVariables();
                                                                        }
break;
case 17:
//#line 52 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 18:
//#line 53 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 19:
//#line 54 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 20:
//#line 55 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 21:
//#line 56 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 22:
//#line 57 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 23:
//#line 61 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 24:
//#line 63 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 31:
//#line 78 "gramatica.y"
{addAtributoLexema(val_peek(0).sval,"TIPO",val_peek(2).sval); verificarRedeclaracion(val_peek(0).sval, "ID_FUNC"); ambitoActual.add(0, val_peek(0).sval);}
break;
case 34:
//#line 82 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 35:
//#line 83 "gramatica.y"
{yyerror("Falta la palabra clave FUNC.");}
break;
case 36:
//#line 84 "gramatica.y"
{yyerror("Falta el identificador de la funcion.");}
break;
case 37:
//#line 87 "gramatica.y"
{addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
break;
case 39:
//#line 93 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 40:
//#line 97 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 41:
//#line 98 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 42:
//#line 101 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 45:
//#line 106 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 46:
//#line 107 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 49:
//#line 114 "gramatica.y"
{
                                    addEstructura("Asignacion"); 
                                    String id = getAmbitoIdentificador(val_peek(3).sval);
                                    if(id == null){
                                        yyerrorSemantico("Variable \"" + val_peek(3).sval + "\" no declarada.");
                                        addTerceto(new Terceto(":=", val_peek(3).sval, val_peek(1).sval, "Error - no declarada"));
                                    } 
                                    else{
                                        if(checkTipos(id,val_peek(1).sval))
                                            addTerceto(new Terceto(":=", id, val_peek(1).sval, getTipo(id)));
                                        else 
                                            addTerceto(new Terceto(":=", id, val_peek(1).sval, "Error de tipo"));
                                    }
                                        
                                      
                                }
break;
case 50:
//#line 130 "gramatica.y"
{addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", val_peek(2).sval)); }
break;
case 51:
//#line 131 "gramatica.y"
{addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
break;
case 54:
//#line 134 "gramatica.y"
{addEstructura("Llamado a funcion");}
break;
case 57:
//#line 140 "gramatica.y"
{addEstructura("Sentencia IF");}
break;
case 58:
//#line 143 "gramatica.y"
{ Terceto terceto = new Terceto("BF", val_peek(1).sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto); }
break;
case 60:
//#line 150 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop(); 
                                    tercetoIncompleto.setOperando2("[" + (tercetos.size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
break;
case 61:
//#line 156 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop();
                                    tercetoIncompleto.setOperando1("[" + tercetos.size() + "]");}
break;
case 62:
//#line 161 "gramatica.y"
{
                                            String id = getAmbitoIdentificador(val_peek(2).sval); 
                                            addTerceto(new Terceto(":=", id, val_peek(0).sval)); }
break;
case 63:
//#line 164 "gramatica.y"
{ 
                                            Terceto tercetoIncompleto = new Terceto("BF", val_peek(0).sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); }
break;
case 64:
//#line 167 "gramatica.y"
{ 
                                        addEstructura("Sentencia REPEAT");
                                        Terceto bifurcacionFalse = backpatching.pop();
                                        Terceto destinoBifurcacionIncondicional = backpatching.pop();
                                        String id = getAmbitoIdentificador(val_peek(10).sval);
                                        addTerceto(new Terceto("+", id, val_peek(2).sval));
                                        addTerceto(new Terceto(":=", id, getReferenciaUltimaInstruccion().sval));
                                        String referenciaBI = "[" + tercetos.indexOf(destinoBifurcacionIncondicional) + "]";
                                        addTerceto(new Terceto("BI", referenciaBI));
                                        String referenciaBF = "[" + tercetos.size() + "]";
                                        bifurcacionFalse.setOperando2(referenciaBF);
                                        }
break;
case 65:
//#line 183 "gramatica.y"
{Terceto terceto = new Terceto(val_peek(1).sval, getAmbitoIdentificador(val_peek(2).sval), val_peek(0).sval); addTerceto(terceto); backpatching.push(terceto); yyval = getReferenciaUltimaInstruccion();}
break;
case 66:
//#line 186 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 67:
//#line 187 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 68:
//#line 190 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 69:
//#line 191 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 70:
//#line 192 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 71:
//#line 195 "gramatica.y"
{ addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion();}
break;
case 72:
//#line 196 "gramatica.y"
{addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion();}
break;
case 73:
//#line 197 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 74:
//#line 200 "gramatica.y"
{ addTerceto(new Terceto("CONV", val_peek(1).sval, null, "SINGLE")); yyval = getReferenciaUltimaInstruccion();}
break;
case 75:
//#line 203 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("+",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 76:
//#line 207 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("-",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 77:
//#line 211 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 78:
//#line 214 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("*", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("*",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 79:
//#line 218 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("/", $1.sval, $3.sval));*/
                                    addTercetoAritmetica("/",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 80:
//#line 222 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 81:
//#line 225 "gramatica.y"
{
                                    String identificador = setAmbitoIdentificador(val_peek(0).sval);
                                    addAtributoLexema(identificador,"USO","ID_PARAMETRO"); 
                                    addAtributoLexema(identificador,"TIPO",val_peek(1).sval);
                                    }
break;
case 83:
//#line 233 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 84:
//#line 236 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 85:
//#line 237 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 86:
//#line 241 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 87:
//#line 244 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval);}
break;
case 88:
//#line 245 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = new ParserVal(getAmbitoIdentificador(val_peek(3).sval));}
break;
case 89:
//#line 248 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 90:
//#line 249 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 91:
//#line 250 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 92:
//#line 251 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 93:
//#line 252 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 94:
//#line 253 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 95:
//#line 256 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 96:
//#line 257 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 97:
//#line 260 "gramatica.y"
{tipo = "SINGLE"; yyval = new ParserVal("SINGLE");}
break;
case 98:
//#line 261 "gramatica.y"
{tipo = "INT";  yyval = new ParserVal("INT");}
break;
//#line 1171 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
