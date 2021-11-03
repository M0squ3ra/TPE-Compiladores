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
    import com.company.Util.Error;
    import com.company.Util.Terceto;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.HashMap;
    import java.io.IOException;
    import java.math.BigDecimal;
    import java.util.Stack;
//#line 30 "Parser.java"




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
public final static short CADENA=257;
public final static short IDENTIFICADOR=258;
public final static short ASIGNACION=259;
public final static short CTE=260;
public final static short ERROR=261;
public final static short INT=262;
public final static short IF=263;
public final static short THEN=264;
public final static short ELSE=265;
public final static short ENDIF=266;
public final static short PRINT=267;
public final static short FUNC=268;
public final static short RETURN=269;
public final static short BEGIN=270;
public final static short END=271;
public final static short BREAK=272;
public final static short SINGLE=273;
public final static short REPEAT=274;
public final static short PRE=275;
public final static short OR=276;
public final static short AND=277;
public final static short MAYOR_IGUAL=278;
public final static short MENOR_IGUAL=279;
public final static short IGUALDAD=280;
public final static short DIFERENTE=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    4,    4,    4,    4,    2,    2,
    2,    2,    2,    2,    8,    8,   11,   11,   11,   11,
   11,   11,    7,    7,   13,   13,   13,   13,   14,   15,
   12,    6,    6,   18,   10,   10,    5,    5,    5,   20,
   20,    3,    3,   19,   19,   19,   19,   19,   19,   19,
   21,   25,   26,   27,   26,   28,   30,   22,   29,   23,
   23,   24,   24,   24,   17,   32,   32,   33,   33,   34,
   34,   35,   16,   16,   16,   36,   36,   36,   37,   37,
   37,   37,   37,   38,   31,   31,   31,   31,   31,   31,
    9,    9,
};
final static short yylen[] = {                            2,
    5,    4,    1,    2,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    7,    3,    1,    3,    3,    1,    3,    1,    1,    3,
    3,    2,    1,    4,    5,    2,    1,    1,    1,    1,
    6,    3,    1,    0,    4,    0,    0,   13,    3,    4,
    4,    4,    4,    4,    1,    3,    1,    3,    1,    3,
    1,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    1,    1,    4,    1,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    4,    0,   92,    0,   91,    0,
   12,   13,   14,    0,   16,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   43,   47,   48,
   49,   50,    0,    0,    0,    9,   10,   11,   38,   39,
    0,   36,    0,    0,    0,    0,   24,    0,    0,    0,
    0,    0,    0,    0,    0,   46,    0,    2,   42,    0,
    0,    0,    0,    7,    0,    6,   34,    0,    0,    0,
    0,   32,    0,    0,    0,    0,    0,    0,   23,    0,
    0,    0,   80,    0,    0,    0,   82,    0,   78,   83,
    0,    0,    0,    0,    0,    0,   69,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,   35,    0,    0,    0,    0,    0,    0,    0,    8,
    0,    0,   81,   60,    0,    0,    0,    0,   61,   44,
   86,   85,   87,   88,   89,   90,    0,   52,    0,    0,
    0,    0,   62,   64,    0,   63,    0,   37,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   76,   77,    0,    0,   68,
   54,    0,   45,   56,    0,    0,    0,    0,    0,    0,
    0,   29,    0,    0,   26,    0,    0,   17,   84,   72,
    0,   51,    0,   18,   19,   31,   20,   21,   22,   15,
    0,   25,    0,   28,   55,    0,    0,   27,    0,   57,
    0,    0,    0,   30,    0,    0,    0,    0,   58,
};
final static short yydgoto[] = {                          2,
    3,   10,   60,    4,   35,   11,   12,   13,   14,   44,
   15,   16,   47,   77,   78,   93,   94,   17,   28,   40,
   29,   30,   31,   32,   53,  142,  191,  193,  210,  213,
  137,   95,   96,   97,   87,   88,   89,   90,
};
final static short yysindex[] = {                      -218,
  -13,    0,  -95,    0,    0,  -39,    0,   80,    0, -129,
    0,    0,    0, -116,    0,   93,    0, -219,   46,   94,
 -197, -169,   71,  -12,   34,   79, -101,    0,    0,    0,
    0,    0, -109,  -79, -147,    0,    0,    0,    0,    0,
   -8,    0,  -25,    8, -149,   93,    0, -179,   80,    5,
   44,    5, -128,   98,   24,    0, -102,    0,    0,  -73,
  -56,   80,   47,    0,    0,    0,    0, -179, -179,  120,
 -217,    0,  -78,  147,  139,   53,  130,   53,    0,  163,
   68,  173,    0,  175,  -54,   42,    0,   70,    0,    0,
  150,   87,  -43,  178,  -55,  -47,    0,  -79,  201,   -5,
  203,   54,    0,   74, -197,    0,  191,  236, -179,  245,
    2,    0,    5,  261,  253,   52,   53,  256,   75,    0,
    5,    5,    0,    0,    5,    5,    5,    5,    0,    0,
    0,    0,    0,    0,    0,    0,    5,    0,    5,    5,
   63,   78,    0,    0,  270,    0,   91,    0,   75,   75,
   76,   75,   75, -121,  169,    5,   82,  299,  301,   97,
   22,  179,  316,   70,   70,    0,    0,  131,  -47,    0,
    0,  303,    0,    0,   32,   51,  324,   69,   72,  310,
  109,    0,  329,  312,    0,  101,  314,    0,    0,    0,
  -79,    0,  315,    0,    0,    0,    0,    0,    0,    0,
  331,    0,  317,    0,    0,  119,  121,    0,  -53,    0,
  320,    5,  321,    0,  131,  122,  340,  -79,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  383,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   13,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -37,    0,    0,    0,    0,    0,  -29,    0,    0,
    0,    0,  -23,    0,  343,  -30,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    3,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  123,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   12,   18,    0,    0,  -21,  -15,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  326,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  166,   30,    0,  -10,   27,   38,   50,  -27,  -50,
    0,    0,  341,  -49,    0,  -16,  230,    0,   88,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  181,    0,  249,  251,    0,  157,  180,    0,
};
final static int YYTABLESIZE=391;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        125,
   41,  126,    1,   79,   79,   79,  131,   79,  132,   79,
   67,   75,   38,   75,   71,   75,  131,   71,  132,   70,
   80,   79,   79,   64,   79,   66,  115,   55,  118,   75,
   75,   68,   75,   86,   92,  145,   36,   27,  110,    1,
  107,  108,  154,  111,    7,    5,   36,   37,   19,   85,
   67,   73,   73,  144,   73,    9,   73,   37,   74,   38,
   74,   50,   74,   63,  101,   73,   72,  159,  161,   38,
   73,   73,   36,   73,   76,   73,   74,   74,   81,   74,
  188,  151,    7,   37,  125,   48,  126,  141,   85,   51,
  194,  104,   56,    9,   73,   38,  155,   39,  175,  176,
  124,  178,  179,  181,  162,  163,   21,  117,   22,  195,
   52,  127,   73,   23,   59,   73,  128,   24,   57,   74,
  168,   65,   25,   66,   26,   75,   33,  197,   22,  125,
  198,  126,    7,   23,  180,   98,   42,   24,   99,   41,
   34,   42,   25,    9,   26,  130,   21,   59,   22,   50,
   59,   43,   73,   23,   21,  102,   22,   24,   19,  109,
    6,   23,   25,   59,   26,   24,    7,  200,   59,   58,
   25,   20,   26,  125,    8,  126,   61,    9,   22,  112,
  205,   46,   21,   23,   22,   39,  113,   24,  116,   23,
   62,   59,   25,   24,   26,  215,  114,  103,   25,   21,
   26,   22,   50,  119,   59,  123,   23,  219,  129,  182,
   24,  125,  121,  126,  122,   25,   18,   26,  138,  189,
  139,  125,    7,  126,  133,  134,  135,  136,   19,  140,
   69,  149,   70,    9,  133,  134,  135,  136,   79,   79,
   79,   79,   79,   79,   54,   67,   75,   75,   75,   75,
   75,   75,   71,   71,   70,   70,   41,  153,   41,  143,
   66,  146,   82,   41,   83,   41,   41,   41,   43,   41,
   43,   41,   41,   37,   41,   43,  150,   84,   39,   43,
  100,  164,  165,   43,   43,  152,   43,   73,   73,   73,
   73,   73,   73,   74,   74,   74,   74,   74,   74,   91,
  156,   82,  105,   83,   22,   39,  166,  167,   21,   23,
   22,  157,  147,   24,  160,   23,   84,  106,   25,   24,
   26,   74,  158,   21,   25,   22,   26,  171,  173,  105,
   23,   22,   42,  177,   24,   21,   23,   22,  120,   25,
   24,   26,   23,  172,  148,   25,   24,   26,   18,   18,
  174,   25,  184,   26,    7,    7,  190,  185,  125,  186,
  126,  192,   45,   49,  196,    9,    9,  187,  199,  201,
  202,  203,  204,  206,  207,  208,  209,  211,  214,  216,
  218,  217,    5,   65,   59,  183,   79,  169,   53,  212,
  170,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         43,
    0,   45,    0,   41,   42,   43,   60,   45,   62,   47,
   41,   41,    0,   43,   40,   45,   60,   41,   62,   41,
   48,   59,   60,   34,   62,   41,   76,   40,   78,   59,
   60,   40,   62,   50,   51,   41,   10,    8,  256,  258,
   68,   69,   41,   71,  262,   59,   20,   10,  268,   45,
   59,   44,   41,   59,   43,  273,   45,   20,   41,   10,
   43,  259,   45,   34,   41,   44,   59,  117,  119,   20,
   59,   60,   46,   62,   45,   44,   59,   60,   49,   62,
   59,  109,  262,   46,   43,   40,   45,   98,   45,  259,
   59,   62,   59,  273,   44,   46,  113,   10,  149,  150,
   59,  152,  153,  154,  121,  122,  256,   78,  258,   59,
   40,   42,   44,  263,   27,   44,   47,  267,   40,  269,
  137,   34,  272,  271,  274,  275,  256,   59,  258,   43,
   59,   45,  262,  263,  256,  264,  258,  267,   41,  256,
  270,  258,  272,  273,  274,   59,  256,   60,  258,  259,
   63,  268,   44,  263,  256,  258,  258,  267,  268,   40,
  256,  263,  272,   76,  274,  267,  262,   59,   81,  271,
  272,    6,  274,   43,  270,   45,  256,  273,  258,  258,
  191,   16,  256,  263,  258,   98,   40,  267,   59,  263,
  270,  104,  272,  267,  274,  212,   58,  271,  272,  256,
  274,  258,  259,   41,  117,  260,  263,  218,   59,   41,
  267,   43,   40,   45,   40,  272,  256,  274,   41,   41,
  276,   43,  262,   45,  278,  279,  280,  281,  268,  277,
  256,   41,  258,  273,  278,  279,  280,  281,  276,  277,
  278,  279,  280,  281,  257,  276,  276,  277,  278,  279,
  280,  281,  276,  277,  276,  277,  256,  256,  258,   59,
  276,   59,  258,  263,  260,  265,  266,  267,  256,  269,
  258,  271,  272,  271,  274,  263,   41,  273,  191,  267,
  257,  125,  126,  271,  272,   41,  274,  276,  277,  278,
  279,  280,  281,  276,  277,  278,  279,  280,  281,  256,
   40,  258,  256,  260,  258,  218,  127,  128,  256,  263,
  258,   59,  259,  267,   59,  263,  273,  271,  272,  267,
  274,  269,  271,  256,  272,  258,  274,  265,   59,  256,
  263,  258,  258,  258,  267,  256,  263,  258,  271,  272,
  267,  274,  263,  266,  271,  272,  267,  274,  256,  256,
  260,  272,  271,  274,  262,  262,   41,   59,   43,   59,
   45,   59,  270,  270,   41,  273,  273,  271,   59,   41,
   59,  271,   59,   59,   44,   59,  258,  257,   59,   59,
   41,  260,    0,   41,   59,  156,   46,  139,  266,  209,
  140,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=281;
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
null,null,null,null,null,null,"CADENA","IDENTIFICADOR","ASIGNACION","CTE",
"ERROR","INT","IF","THEN","ELSE","ENDIF","PRINT","FUNC","RETURN","BEGIN","END",
"BREAK","SINGLE","REPEAT","PRE","OR","AND","MAYOR_IGUAL","MENOR_IGUAL",
"IGUALDAD","DIFERENTE",
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
"ENCABEZADO_FUNC : TIPO FUNC IDENTIFICADOR '(' TIPO IDENTIFICADOR ')'",
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
"SENTENCIA_EJECUTABLE : ASIGNACION_ERROR",
"SENTENCIA_EJECUTABLE : PRINT_ERROR",
"SENTENCIA_IF : IF CONDICION_IF THEN CUERPO_IF ENDIF ';'",
"CONDICION_IF : '(' CONDICION ')'",
"CUERPO_IF : BLOQUE_SENTENCIA",
"$$1 :",
"CUERPO_IF : BLOQUE_SENTENCIA ELSE $$1 BLOQUE_SENTENCIA",
"$$2 :",
"$$3 :",
"SENTENCIA_REPEAT : REPEAT '(' IDENTIFICADOR ASIGNACION CTE $$2 ';' CONDICION_REPEAT $$3 ';' CTE ')' BLOQUE_SENTENCIA",
"CONDICION_REPEAT : IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION",
"ASIGNACION_ERROR : error ASIGNACION EXPRESION ';'",
"ASIGNACION_ERROR : IDENTIFICADOR ASIGNACION error ';'",
"PRINT_ERROR : PRINT CADENA ')' ';'",
"PRINT_ERROR : PRINT '(' ')' ';'",
"PRINT_ERROR : PRINT '(' CADENA ';'",
"CONDICION : CONDICION_OR",
"CONDICION_OR : CONDICION_OR OR CONDICION_AND",
"CONDICION_OR : CONDICION_AND",
"CONDICION_AND : CONDICION_AND AND CONDICION_COMPARACION",
"CONDICION_AND : CONDICION_COMPARACION",
"CONDICION_COMPARACION : EXPRESION OPERADOR_COMPARADOR EXPRESION",
"CONDICION_COMPARACION : EXPRESION",
"CONVERSION : SINGLE '(' EXPRESION ')'",
"EXPRESION : EXPRESION '+' TERMINO",
"EXPRESION : EXPRESION '-' TERMINO",
"EXPRESION : TERMINO",
"TERMINO : TERMINO '*' FACTOR",
"TERMINO : TERMINO '/' FACTOR",
"TERMINO : FACTOR",
"FACTOR : IDENTIFICADOR",
"FACTOR : CTE",
"FACTOR : '-' CTE",
"FACTOR : CONVERSION",
"FACTOR : LLAMADO_FUNCION",
"LLAMADO_FUNCION : IDENTIFICADOR '(' EXPRESION ')'",
"OPERADOR_COMPARADOR : '>'",
"OPERADOR_COMPARADOR : '<'",
"OPERADOR_COMPARADOR : MAYOR_IGUAL",
"OPERADOR_COMPARADOR : MENOR_IGUAL",
"OPERADOR_COMPARADOR : IGUALDAD",
"OPERADOR_COMPARADOR : DIFERENTE",
"TIPO : SINGLE",
"TIPO : INT",
};

//#line 313 "gramatica.y"

    private Lexico lexico = Lexico.getInstance();
    private List<Error> erroresSintacticos = new ArrayList<Error>(); 
    private List<Error> erroresSemanticos = new ArrayList<Error>(); 
    private List<Integer> tokensReconocidos = new ArrayList<Integer>(); 
    private List<String> estructurasReconocidas = new ArrayList<String>();
    private List<String> variables = new ArrayList<String>();
    private String tipo;
    private String nombrePrograma;
    private List<String> ambitoActual = new ArrayList<String>();
    private Map<String,List<Terceto>> tercetos = new HashMap<String,List<Terceto>>();
    private Stack<Terceto> backpatching = new Stack<Terceto>();
    private boolean errorSemantico = false;
    private List<String> variablesFunciones = new ArrayList<String>();
    private List<String> cadenas = new ArrayList<String>();

    

    public int yylex(){ 
        
        int token = 0;

        try{
            token = lexico.yylex();
        } catch(IOException e){
            
        }
        
        // En el caso que sea IDENTIFICADOR,CTE o STRING obtiene 
        // la referencia a la tabla de simbolos
        if(token  == 258 || token == 260 || token == 257) 
            yylval = lexico.getyylval();
	
        tokensReconocidos.add(token);

        return token;
    }

    public List<Integer> getTokensReconocidos(){
        return tokensReconocidos;
    }

    public List<String> getCadenas(){
        return cadenas;
    }

    public void addCadena(String cadena){
        cadenas.add(cadena);
    }

    public void yyerror(String error){
        Error e = new Error(error,false,lexico.getLinea());
        if (!erroresSintacticos.contains(e))
            erroresSintacticos.add(e);
    }

    public void yyerrorSemantico(String error){
        errorSemantico = true;
        Error e = new Error(error,false,lexico.getLinea());
        if(!erroresSemanticos.contains(e))
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
        List<Terceto> listaTercetos = tercetos.get(getIdentificadorFuncionActual());

        if(listaTercetos == null){
            tercetos.put(getIdentificadorFuncionActual(), new ArrayList<Terceto>());
            yyerrorSemantico("Error");
            return  new ParserVal("[-1]"); // Si pongo null a generar un error, pero si hago new ParserVal
                         // mas adelante me va a decir que hay error de compatibilidad de tipos
                         // cuando no es asi
        }

        return new ParserVal("["+ (listaTercetos.size() - 1) + "]");
    }

    public Map<String,List<Terceto>> getTercetos(){
        return tercetos;
    }

    public void verificarRedeclaracion(String identificador, String uso) {
        if (getAmbitoIdentificador(identificador) != null) {
            yyerrorSemantico("Identificador ya utilizado en el ámbito.");
        } else {
            identificador = setAmbitoIdentificador(identificador);
            if (uso.equals("ID_VARIABLE")){
                variables.add(identificador);
                String funcion = getIdentificadorFuncionActual();
                // if(variablesFunciones.get(funcion) == null)
                //     variablesFunciones.put(funcion, new ArrayList<String>());
                variablesFunciones.add(identificador);
            }
                
            addAtributoLexema(identificador,"USO",uso);
        }
    }

    public List<String> getVariablesFunciones(){
        return variablesFunciones;
    }

    public String getTipo(String o){
        if(!o.startsWith("[")){
            Lexico lexico = Lexico.getInstance();
            return (String) lexico.getAtributosLexema(o).get("TIPO");
        } 
        else {
            List<Terceto> listaTercetos = tercetos.get(getIdentificadorFuncionActual());
            String tipo = null;
            if(listaTercetos.size() > 0){ //Para cuando el error semantico se da antes de agregar cualquier terceto
                Terceto t = listaTercetos.get((Integer.parseInt(o.substring(1, o.length() - 1 ))));
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

    public String getIdentificadorFuncionActual(){
        String ambito = getAmbito(ambitoActual);
        String ambitoSplit[] = ambito.split("\\."); 
        String funcion = ambitoSplit[ambitoSplit.length - 1];
        funcion += ambito.split(funcion)[0];
        funcion = funcion.substring(0,funcion.length()-1);

        return funcion;
    }

    // En el caso de que se haya detectado un error semantico, no se genera el codigo
    // Pero se permite que el programa siga reconociendo errores y estructuras
    public void addTerceto(Terceto t){
        
        String funcion = getIdentificadorFuncionActual();
        List<Terceto> listaTercetos = tercetos.get(funcion);
        
        if(listaTercetos == null){
            listaTercetos = new ArrayList<Terceto>();
            tercetos.put(funcion,listaTercetos);
        }
        
        listaTercetos.add(t);
    }

    public boolean getError(){
        return errorSemantico;
    }

    public void checkRetornoFuncion(String expresion){
        // Obtiene el lexema de la funcion
        String ambito = getAmbito(ambitoActual);
        String ambitoSplit[] = ambito.split("\\."); 
        String funcion = ambitoSplit[ambitoSplit.length - 1];
        funcion += ambito.split(funcion)[0];
        funcion = funcion.substring(0,funcion.length()-1);

        checkTipos(expresion, funcion);
    }
    

//#line 717 "Parser.java"
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
//#line 31 "gramatica.y"
{ambitoActual.add(0, val_peek(1).sval); addAtributoLexema(val_peek(1).sval,"USO","Nombre de Programa");}
break;
case 5:
//#line 34 "gramatica.y"
{yyerror("Bloque principal no especificado.");}
break;
case 6:
//#line 35 "gramatica.y"
{yyerror("BEGIN del bloque principal no especificado.");}
break;
case 7:
//#line 36 "gramatica.y"
{yyerror("END del bloque principal no especificado.");}
break;
case 8:
//#line 37 "gramatica.y"
{yyerror("Falta el nombre del programa.");}
break;
case 15:
//#line 48 "gramatica.y"
{addEstructura("Declaracion de varables");
                                                                        addTipoVariables();
                                                                        }
break;
case 17:
//#line 54 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 18:
//#line 55 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 19:
//#line 56 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 20:
//#line 57 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 21:
//#line 58 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 22:
//#line 59 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 23:
//#line 63 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 24:
//#line 65 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 27:
//#line 70 "gramatica.y"
{
                                    Terceto bifurcacionIncondicional = backpatching.pop();
                                    /* bifurcacionIncondicional.setOperando1("[" + tercetos.size() + "]");*/
                                    bifurcacionIncondicional.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                    
                                }
break;
case 28:
//#line 76 "gramatica.y"
{
                                    Terceto bifurcacionIncondicional = backpatching.pop();
                                    /* bifurcacionIncondicional.setOperando1("[" + tercetos.size() + "]");*/
                                    bifurcacionIncondicional.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                }
break;
case 29:
//#line 83 "gramatica.y"
{checkRetornoFuncion(val_peek(1).sval); addTerceto(new Terceto("RETURN_FUNC", val_peek(1).sval, null, getTipo(val_peek(1).sval)));}
break;
case 30:
//#line 86 "gramatica.y"
{
                                        Terceto bifurcacionTrue = new Terceto("BT", val_peek(4).sval, null);
                                        addTerceto(bifurcacionTrue);
                                        addTerceto(new Terceto("PRINT", val_peek(1).sval + "%"));
                                        addCadena(val_peek(1).sval + "%");
                                        Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                        addTerceto(bifurcacionIncondicional);
                                        backpatching.push(bifurcacionIncondicional);
                                        /* bifurcacionTrue.setOperando2("[" + tercetos.size() + "]");*/
                                        bifurcacionTrue.setOperando2("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                        }
break;
case 31:
//#line 100 "gramatica.y"
{addAtributoLexema(val_peek(4).sval,"TIPO",val_peek(6).sval);
                                    verificarRedeclaracion(val_peek(4).sval, "ID_FUNC");
                                    addAtributoLexema(getAmbitoIdentificador(val_peek(4).sval),"TIPO_PARAMETRO", val_peek(2).sval);

                                    ambitoActual.add(0, val_peek(4).sval);
                                    
                                    String identificador = setAmbitoIdentificador(val_peek(1).sval);
                                    addAtributoLexema(identificador,"USO","ID_PARAMETRO"); 
                                    addAtributoLexema(identificador,"TIPO",val_peek(2).sval);
                                    addAtributoLexema(getAmbitoIdentificador(val_peek(4).sval),"NOMBRE_PARAMETRO", identificador);
                                    }
break;
case 32:
//#line 115 "gramatica.y"
{addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
break;
case 34:
//#line 121 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 35:
//#line 125 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 36:
//#line 126 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 37:
//#line 129 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 40:
//#line 134 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 41:
//#line 135 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 44:
//#line 142 "gramatica.y"
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
case 45:
//#line 158 "gramatica.y"
{addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", val_peek(2).sval + "%")); addCadena(val_peek(2).sval + "%");}
break;
case 46:
//#line 159 "gramatica.y"
{addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
break;
case 51:
//#line 167 "gramatica.y"
{addEstructura("Sentencia IF");
                                    addTerceto(new Terceto("END_IF"));}
break;
case 52:
//#line 171 "gramatica.y"
{ Terceto terceto = new Terceto("BF", val_peek(1).sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto); }
break;
case 54:
//#line 178 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop(); 
                                    
                                    /* tercetoIncompleto.setOperando2("[" + (tercetos.size() + 1) + "]");*/
                                    tercetoIncompleto.setOperando2("[" + (tercetos.get(getIdentificadorFuncionActual()).size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
break;
case 55:
//#line 186 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop();
                                    /* tercetoIncompleto.setOperando1("[" + tercetos.size() + "]");}*/
                                    tercetoIncompleto.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");}
break;
case 56:
//#line 192 "gramatica.y"
{
                                            String id = getAmbitoIdentificador(val_peek(2).sval); 
                                            addTerceto(new Terceto(":=", id, val_peek(0).sval)); }
break;
case 57:
//#line 195 "gramatica.y"
{ 
                                            Terceto tercetoIncompleto = new Terceto("BF", val_peek(0).sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); }
break;
case 58:
//#line 198 "gramatica.y"
{ 
                                        addEstructura("Sentencia REPEAT");
                                        Terceto bifurcacionFalse = backpatching.pop();
                                        Terceto destinoBifurcacionIncondicional = backpatching.pop();
                                        String id = getAmbitoIdentificador(val_peek(10).sval);
                                        addTercetoAritmetica("+",id,val_peek(2).sval);
                                        addTerceto(new Terceto(":=", id, getReferenciaUltimaInstruccion().sval));
                                        String referenciaBI = "[" + tercetos.get(getIdentificadorFuncionActual()).indexOf(destinoBifurcacionIncondicional) + "]";
                                        addTerceto(new Terceto("BI", referenciaBI));
                                        String referenciaBF = "[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]";
                                        bifurcacionFalse.setOperando2(referenciaBF);
                                        addTerceto(new Terceto("END_REPEAT"));
                                        }
break;
case 59:
//#line 213 "gramatica.y"
{Terceto terceto = new Terceto(val_peek(1).sval, getAmbitoIdentificador(val_peek(2).sval), val_peek(0).sval); addTerceto(terceto); backpatching.push(terceto); yyval = getReferenciaUltimaInstruccion();}
break;
case 60:
//#line 216 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 61:
//#line 217 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 62:
//#line 220 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 63:
//#line 221 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 64:
//#line 222 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 65:
//#line 225 "gramatica.y"
{yyval = val_peek(0);}
break;
case 66:
//#line 228 "gramatica.y"
{ 
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto("||", val_peek(2).sval, val_peek(0).sval, "INT")); 
                                            yyval = getReferenciaUltimaInstruccion();}
break;
case 67:
//#line 232 "gramatica.y"
{yyval = val_peek(0);}
break;
case 68:
//#line 235 "gramatica.y"
{ 
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto("&&", val_peek(2).sval, val_peek(0).sval, "INT")); 
                                            yyval = getReferenciaUltimaInstruccion();
                                        }
break;
case 69:
//#line 240 "gramatica.y"
{yyval = val_peek(0);}
break;
case 70:
//#line 243 "gramatica.y"
{
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval,"INT")); 
                                            yyval = getReferenciaUltimaInstruccion();
                                            }
break;
case 71:
//#line 248 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 72:
//#line 251 "gramatica.y"
{ addTerceto(new Terceto("CONV", val_peek(1).sval, null, "SINGLE")); yyval = getReferenciaUltimaInstruccion();}
break;
case 73:
//#line 254 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("+",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 74:
//#line 258 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("-",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 75:
//#line 262 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 76:
//#line 265 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("*", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("*",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 77:
//#line 269 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("/", $1.sval, $3.sval));*/
                                    addTercetoAritmetica("/",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 78:
//#line 273 "gramatica.y"
{yyval = val_peek(0);}
break;
case 79:
//#line 276 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 80:
//#line 277 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 81:
//#line 281 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 82:
//#line 284 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval);}
break;
case 83:
//#line 285 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = val_peek(0);}
break;
case 84:
//#line 288 "gramatica.y"
{String id = getAmbitoIdentificador(val_peek(3).sval);
                                                                if(lexico.getAtributosLexema(id).get("TIPO_PARAMETRO").equals(getTipo(val_peek(1).sval))){
                                                                    addTerceto(new Terceto("CALL_FUNC", id, val_peek(1).sval,getTipo(id))); 
                                                                    yyval = getReferenciaUltimaInstruccion();
                                                                } else {
                                                                    yyerrorSemantico("Tipo de la expresion incompatible con el parametro de la funcion");
                                                                    yyval = new ParserVal(id);
                                                                }
                                                                }
break;
case 85:
//#line 299 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 86:
//#line 300 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 87:
//#line 301 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 88:
//#line 302 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 89:
//#line 303 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 90:
//#line 304 "gramatica.y"
{ yyval = new ParserVal("<>"); }
break;
case 91:
//#line 307 "gramatica.y"
{tipo = "SINGLE"; yyval = new ParserVal("SINGLE");}
break;
case 92:
//#line 308 "gramatica.y"
{tipo = "INT";  yyval = new ParserVal("INT");}
break;
//#line 1257 "Parser.java"
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
