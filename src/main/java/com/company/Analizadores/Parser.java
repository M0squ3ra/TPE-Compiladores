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
    2,    2,    2,    2,    7,    7,   10,   10,   10,   10,
   10,   10,    6,    6,   12,   12,   12,   12,   13,   14,
   11,    5,    5,   18,    9,    9,   17,   17,   19,   19,
   19,   22,   22,   23,   23,   23,   25,   25,   20,   20,
    3,    3,   21,   21,   24,   24,   24,   24,   24,   24,
   24,   26,   30,   31,   32,   31,   33,   35,   27,   34,
   28,   28,   29,   29,   29,   29,   16,   37,   37,   38,
   38,   39,   39,   40,   15,   15,   15,   41,   41,   41,
   42,   42,   42,   42,   42,   43,   36,   36,   36,   36,
   36,   36,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    1,    2,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    7,    3,    1,    3,    3,    1,    3,    1,    3,    1,
    1,    3,    3,    3,    1,    1,    3,    3,    2,    1,
    2,    1,    2,    1,    4,    5,    1,    1,    1,    1,
    2,    6,    3,    1,    0,    4,    0,    0,   13,    3,
    4,    4,    4,    4,    4,    3,    1,    3,    1,    3,
    1,    3,    1,    4,    3,    3,    1,    3,    3,    1,
    1,    1,    2,    1,    1,    4,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    4,    0,  104,    0,  103,    0,
   12,   13,   14,    0,   16,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   52,   57,   58,   59,
   60,    0,    0,    0,    9,   10,   11,    0,   38,    0,
    0,    0,    0,   24,    0,    0,    0,   61,    0,    0,
    0,    0,    0,    0,    0,    2,   51,    0,    6,   34,
    0,    0,    0,    0,   32,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,   92,    0,    0,    0,   94,
    0,   90,   95,    0,    0,    0,    0,    0,    0,   81,
    0,   76,    0,    0,    0,    0,    1,    0,    0,    0,
    0,    0,   37,    0,    0,    0,    0,    0,    0,    0,
    8,    0,    0,   93,   71,    0,    0,    0,    0,   72,
   55,   98,   97,   99,  100,  101,  102,    0,   63,    0,
    0,    0,    0,    0,   45,   46,    0,   73,   75,    0,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   36,    0,    0,    0,    0,    0,
   88,   89,    0,    0,   80,    0,    0,   65,    0,   56,
   67,    0,    0,    0,    0,    0,    0,    0,   29,    0,
    0,   26,    0,    0,   17,    0,   96,   84,   47,    0,
   44,    0,   62,    0,   18,   19,   31,   20,   21,   22,
   15,    0,   25,    0,   28,   35,   66,    0,    0,   27,
    0,   68,    0,    0,    0,   30,    0,    0,    0,    0,
    0,    0,    0,   69,   40,   41,   54,    0,   50,    0,
   53,   42,   49,    0,   39,
};
final static short yydgoto[] = {                          2,
    3,   10,   26,    4,   11,   12,   13,   14,  156,   15,
   16,   44,   70,   71,   86,   87,   41,   17,  224,  228,
  229,  226,  134,   27,  136,   28,   29,   30,   31,   51,
  137,  192,  194,  212,  215,  128,   88,   89,   90,   80,
   81,   82,   83,
};
final static short yysindex[] = {                      -214,
  -28,    0, -155,    0,    0,  -56,    0,   83,    0, -150,
    0,    0,    0,  -95,    0,  -46,    0, -188,   47,   74,
  -45, -123,   79,   20,  111, -109,    0,    0,    0,    0,
    0,   -3,   83,  -99,    0,    0,    0,   35,    0,  -33,
  -22, -172,  -46,    0, -140,   83,   31,    0,  -40,   31,
 -108,  101,  125,  -25,  -82,    0,    0,  -89,    0,    0,
 -140, -140,  140, -227,    0,  -72,  152,  138,  -79,  154,
  -79,    0,  178,  -69,  182,    0,  186,  -17,  306,    0,
   62,    0,    0,  175,  309,    6,  217,  -14,   -8,    0,
  -59,    0,  212,   11,  236,   46,    0,  259,  291, -140,
  294,    7,    0,   31,  269,  284,   77,  -79,  286,   95,
    0,   31,   31,    0,    0,   31,   31,   31,   31,    0,
    0,    0,    0,    0,    0,    0,    0,   31,    0,   31,
   31,   40,   83,   90,    0,    0,   96,    0,    0,  302,
    0,  103,   95,   95,  106,   95,   95,  -75,  270,   31,
  107,  313,  320,  109,    0,   23,  315,  332,   62,   62,
    0,    0,  164,   -8,    0,   54,   60,    0,  322,    0,
    0,   39,   41,  333,   67,   87,  323,   91,    0,  342,
  325,    0,  114,  327,    0,  129,    0,    0,    0,  -45,
    0,  -59,    0,  329,    0,    0,    0,    0,    0,    0,
    0,  345,    0,  331,    0,    0,    0,  133,  135,    0,
  -50,    0,  334,   31,  335,    0,  164,  136,  354, -142,
   34,   66,  338,    0,    0,    0,    0, -129,    0, -119,
    0,    0,    0,  -45,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  398,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  399,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -39,    0,    0,    0,    0,    0,
  -32,    0,    0,    0,    0,  -41,    0,  359,   -2,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  137,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -26,    2,
    0,    0,  -16,   12,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -208,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  343,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    1,    0,
};
final static short yygindex[] = {                         0,
    0,   72,    8,    0,   22,   49,   53,   10,  223,    0,
    0,  358,  -31,    0,  -23,  254,    0,    0,    0,  183,
  -27,    0,  214,  268,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  196,    0,  278,  279,    0,
  203,   52,    0,
};
final static int YYTABLESIZE=498;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
   43,   91,   91,   91,   78,   91,   64,   91,   87,  122,
   87,  123,   87,   48,   85,   95,   85,   34,   85,   91,
   91,   66,   91,   79,   82,   85,   87,   87,  101,   87,
    5,   35,   85,   85,    7,   85,   65,  106,   79,  109,
   58,   35,   86,    1,   86,    9,   86,  148,  116,   69,
  117,  140,   78,   74,   73,   48,   48,   48,   36,   54,
   86,   86,   37,   86,   35,  122,  186,  123,   36,  139,
   98,   99,   37,  102,   61,   78,  153,   20,  108,   19,
  149,  185,  186,   21,  186,   22,   45,   43,  157,  158,
   23,   36,   48,   60,   24,   37,   67,  195,   48,  196,
    6,   25,   68,  118,  163,   32,    7,   22,  119,  145,
  186,    7,   23,  221,    8,   22,   24,    9,   50,   33,
   23,    7,    9,   25,   24,  198,   21,  222,   22,  223,
  186,   25,    9,   23,  186,   49,  234,   24,   22,  166,
  167,  232,  223,   23,   25,  199,   21,   24,   22,  201,
   55,  235,  223,   23,   25,   91,   21,   24,   22,   92,
   38,   56,   39,   23,   25,   93,   21,   24,   22,  161,
  162,   59,   40,   23,   25,   96,   21,   24,   22,  100,
  177,   97,  155,   23,   25,  103,   21,   24,   22,   67,
  217,  104,  225,   23,   25,  105,  132,   24,   22,   18,
  233,  111,  233,   23,   25,    7,  116,   24,  117,   18,
  133,   19,  107,   47,   25,    7,    9,   75,  110,   76,
   84,  112,   62,   42,   63,  113,    9,  124,  125,  126,
  127,   94,   77,  120,   83,   83,   91,   91,   91,   91,
   91,   91,  114,   87,   87,   87,   87,   87,   87,   85,
   85,   85,   85,   85,   85,   47,   43,  129,   43,   82,
   82,  130,  147,   43,   19,   43,   43,   43,  131,   43,
  138,   43,   43,   79,   43,   52,   53,   86,   86,   86,
   86,   86,   86,  124,  125,  126,  127,   78,   75,   21,
   76,   22,   47,   57,  141,   21,   23,   22,   47,  143,
   24,   57,   23,   77,  142,  223,   24,   25,  150,   21,
  179,   22,  116,   25,  117,  190,   23,   22,  159,  160,
   24,   21,   23,   22,  189,   57,   24,   25,   23,   18,
  191,  144,   24,   25,  146,    7,   57,  223,   21,   25,
   22,   57,  151,   46,  154,   23,    9,  152,  116,   24,
  117,  116,  155,  117,  168,  187,   25,  116,  135,  117,
  170,  169,  171,  174,  115,  172,  173,  121,  175,  176,
  178,  182,  188,  197,  116,   57,  117,  181,  183,  184,
  193,  200,  202,  203,  204,  205,  206,  208,  209,  210,
  211,  213,  216,  218,  220,  219,  231,    5,    7,   77,
   72,   70,   64,  180,  230,  207,  214,  164,    0,  165,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  135,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  227,  227,  227,
    0,    0,    0,    0,    0,  227,    0,  227,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   41,   42,   43,   45,   45,   40,   47,   41,   60,
   43,   62,   45,   59,   41,   41,   43,   10,   45,   59,
   60,   44,   62,   47,   41,   49,   59,   60,  256,   62,
   59,   10,   59,   60,  262,   62,   59,   69,   41,   71,
   33,   20,   41,  258,   43,  273,   45,   41,   43,   42,
   45,   41,   41,   46,   45,   59,  265,  266,   10,   40,
   59,   60,   10,   62,   43,   60,   44,   62,   20,   59,
   61,   62,   20,   64,   40,   45,  108,    6,   71,  268,
  104,   59,   44,  256,   44,  258,   40,   16,  112,  113,
  263,   43,   59,   59,  267,   43,  269,   59,   59,   59,
  256,  274,  275,   42,  128,  256,  262,  258,   47,  100,
   44,  262,  263,  256,  270,  258,  267,  273,   40,  270,
  263,  262,  273,  274,  267,   59,  256,  270,  258,  272,
   44,  274,  273,  263,   44,  259,  256,  267,  258,  132,
  133,  271,  272,  263,  274,   59,  256,  267,  258,   59,
   40,  271,  272,  263,  274,  264,  256,  267,  258,   59,
  256,  271,  258,  263,  274,   41,  256,  267,  258,  118,
  119,  271,  268,  263,  274,  258,  256,  267,  258,   40,
  256,  271,  258,  263,  274,  258,  256,  267,  258,  269,
  214,   40,  220,  263,  274,   58,  256,  267,  258,  256,
  228,  271,  230,  263,  274,  262,   43,  267,   45,  256,
  270,  268,   59,  259,  274,  262,  273,  258,   41,  260,
  261,   40,  256,  270,  258,   40,  273,  278,  279,  280,
  281,  257,  273,   59,  276,  277,  276,  277,  278,  279,
  280,  281,  260,  276,  277,  278,  279,  280,  281,  276,
  277,  278,  279,  280,  281,  259,  256,   41,  258,  276,
  277,  276,  256,  263,  268,  265,  266,  267,  277,  269,
   59,  271,  272,  276,  274,  256,  257,  276,  277,  278,
  279,  280,  281,  278,  279,  280,  281,  276,  258,  256,
  260,  258,  259,   26,   59,  256,  263,  258,  259,   41,
  267,   34,  263,  273,  259,  272,  267,  274,   40,  256,
   41,  258,   43,  274,   45,  256,  263,  258,  116,  117,
  267,  256,  263,  258,  271,   58,  267,  274,  263,  256,
  271,   41,  267,  274,   41,  262,   69,  272,  256,  274,
  258,   74,   59,  270,   59,  263,  273,  271,   43,  267,
   45,   43,  258,   45,  265,   41,  274,   43,   91,   45,
   59,  266,  260,  258,   59,  143,  144,   59,  146,  147,
  148,   59,   41,   41,   43,  108,   45,  271,   59,  271,
   59,   59,   41,   59,  271,   59,  258,   59,   44,   59,
  258,  257,   59,   59,   41,  260,   59,    0,    0,   41,
   43,   59,  266,  150,  222,  192,  211,  130,   -1,  131,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  166,  167,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  192,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  220,  221,  222,
   -1,   -1,   -1,   -1,   -1,  228,   -1,  230,
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
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA CONJUNTO_SENTENCIAS END",
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS",
"PROGRAMA_ERROR : PROGRAMA_ENCABEZADO error SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA DECLARACION_VARIABLES",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA DECLARACION_FUNC",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA ASIGNACION_FUNC_VAR",
"SENTENCIA_DECLARATIVA : DECLARACION_VARIABLES",
"SENTENCIA_DECLARATIVA : DECLARACION_FUNC",
"SENTENCIA_DECLARATIVA : ASIGNACION_FUNC_VAR",
"ASIGNACION_FUNC_VAR : TIPO FUNC '(' TIPO ')' VARIABLES_FUNC_VAR ';'",
"ASIGNACION_FUNC_VAR : ASIGNACION_FUNC_VAR_ERROR",
"ASIGNACION_FUNC_VAR_ERROR : error FUNC '(' TIPO ')' VARIABLES_FUNC_VAR ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO error '(' TIPO ')' VARIABLES_FUNC_VAR ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC error TIPO ')' VARIABLES_FUNC_VAR ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC '(' error ')' VARIABLES_FUNC_VAR ';'",
"ASIGNACION_FUNC_VAR_ERROR : TIPO FUNC '(' TIPO error VARIABLES_FUNC_VAR ';'",
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
"VARIABLES_FUNC_VAR : VARIABLES_FUNC_VAR ',' IDENTIFICADOR",
"VARIABLES_FUNC_VAR : IDENTIFICADOR",
"VARIABLES : VARIABLES ',' IDENTIFICADOR",
"VARIABLES : IDENTIFICADOR",
"BLOQUE_SENTENCIA_REPEAT : BEGIN CONJUNTO_SENTENCIAS_REPEAT END",
"BLOQUE_SENTENCIA_REPEAT : SENTENCIA_EJECUTABLE_REPEAT",
"BLOQUE_SENTENCIA_REPEAT : BLOQUE_SENTENCIA_REPEAT_ERROR",
"BLOQUE_SENTENCIA_REPEAT_ERROR : error CONJUNTO_SENTENCIAS_REPEAT END",
"BLOQUE_SENTENCIA_REPEAT_ERROR : BEGIN CONJUNTO_SENTENCIAS_REPEAT error",
"BLOQUE_SENTENCIA : BEGIN CONJUNTO_SENTENCIAS END",
"BLOQUE_SENTENCIA : SENTENCIA_EJECUTABLE",
"BLOQUE_SENTENCIA : BLOQUE_SENTENCIA_ERROR",
"BLOQUE_SENTENCIA_ERROR : error CONJUNTO_SENTENCIAS END",
"BLOQUE_SENTENCIA_ERROR : BEGIN CONJUNTO_SENTENCIAS error",
"CONJUNTO_SENTENCIAS_REPEAT : CONJUNTO_SENTENCIAS_REPEAT SENTENCIA_EJECUTABLE_REPEAT",
"CONJUNTO_SENTENCIAS_REPEAT : SENTENCIA_EJECUTABLE_REPEAT",
"CONJUNTO_SENTENCIAS : CONJUNTO_SENTENCIAS SENTENCIA_EJECUTABLE",
"CONJUNTO_SENTENCIAS : SENTENCIA_EJECUTABLE",
"SENTENCIA_EJECUTABLE_REPEAT : BREAK ';'",
"SENTENCIA_EJECUTABLE_REPEAT : SENTENCIA_EJECUTABLE",
"SENTENCIA_EJECUTABLE : IDENTIFICADOR ASIGNACION EXPRESION ';'",
"SENTENCIA_EJECUTABLE : PRINT '(' CADENA ')' ';'",
"SENTENCIA_EJECUTABLE : SENTENCIA_IF",
"SENTENCIA_EJECUTABLE : SENTENCIA_REPEAT",
"SENTENCIA_EJECUTABLE : ASIGNACION_ERROR",
"SENTENCIA_EJECUTABLE : PRINT_ERROR",
"SENTENCIA_EJECUTABLE : error ';'",
"SENTENCIA_IF : IF CONDICION_IF THEN CUERPO_IF ENDIF ';'",
"CONDICION_IF : '(' CONDICION ')'",
"CUERPO_IF : BLOQUE_SENTENCIA",
"$$1 :",
"CUERPO_IF : BLOQUE_SENTENCIA ELSE $$1 BLOQUE_SENTENCIA",
"$$2 :",
"$$3 :",
"SENTENCIA_REPEAT : REPEAT '(' IDENTIFICADOR ASIGNACION CTE $$2 ';' CONDICION_REPEAT $$3 ';' CTE ')' BLOQUE_SENTENCIA_REPEAT",
"CONDICION_REPEAT : IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION",
"ASIGNACION_ERROR : error ASIGNACION EXPRESION ';'",
"ASIGNACION_ERROR : IDENTIFICADOR ASIGNACION ERROR ';'",
"PRINT_ERROR : PRINT CADENA ')' ';'",
"PRINT_ERROR : PRINT '(' ')' ';'",
"PRINT_ERROR : PRINT '(' CADENA ';'",
"PRINT_ERROR : PRINT error ';'",
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

//#line 406 "gramatica.y"

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
    private boolean err = false;
    private List<String> variablesFunciones = new ArrayList<String>();
    private List<String> cadenas = new ArrayList<String>();
    private boolean errorAsignacion = false;
    private List<String> checkDeclaracionFunciones = new ArrayList<String>();


    

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
        
        if(token == 261) // token error
            err = true;
	
        tokensReconocidos.add(token);

        return token;
    }


    public List<Integer> getTokensReconocidos(){
        return tokensReconocidos;
    }

    public List<String> getCadenas(){
        return cadenas;
    }

    public String getNombrePrograma(){
        return nombrePrograma;
    }

    public void addCadena(String cadena){
        cadenas.add(cadena);
    }

    public void yyerror(String error){
        err = true;
        Error e = new Error(error,false,lexico.getLinea());
        if (!erroresSintacticos.contains(e))
            erroresSintacticos.add(e);
    }

    public void yyerrorSemantico(String error){
        err = true;
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
            if(!lexico.getInstance().getAtributosLexema(getAmbitoIdentificador(identificador)).get("USO").equals("ID_FUNC")){
                yyerrorSemantico("Identificador ya utilizado en el ámbito.");
            } else {
                if(checkDeclaracionFunciones.contains(getAmbitoIdentificador(identificador)))
                    yyerrorSemantico("Identificador ya utilizado en el ámbito.");
                else
                    checkDeclaracionFunciones.add(getAmbitoIdentificador(identificador));
            }
        } else {
            identificador = setAmbitoIdentificador(identificador);
            if (uso.equals("ID_VARIABLE") || uso.equals("ID_VAR_FUNC")){
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
        if(o == null)
            return "Error";
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
        return err;
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

    public boolean checkUso(String o1, String o2){
        if(o1 == null || o2 == null)
            return false;
        if((o2.startsWith("[") || Character.isDigit(o2.charAt(0)) || o2.startsWith(".") || o2.startsWith("-")) && (o1.startsWith("[") || Character.isDigit(o1.charAt(0)) || o1.startsWith(".") || o1.startsWith("-"))){
            return true;
        } else{
            Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos(); 
            if(o1.startsWith("[") || Character.isDigit(o1.charAt(0)) || o1.startsWith(".") || o1.startsWith("-")){
                if(tablaSimbolos.get(o2).get("USO").equals("ID_VAR_FUNC") || tablaSimbolos.get(o2).get("USO").equals("ID_FUNC")){
                    yyerrorSemantico("No se puede operar con funciones");
                    errorAsignacion = true;
                    return false;
                }
            } else if(o2.startsWith("[") || Character.isDigit(o2.charAt(0)) || o2.startsWith(".") || o2.startsWith("-")){
                if(tablaSimbolos.get(o1).get("USO").equals("ID_VAR_FUNC") || tablaSimbolos.get(o1).get("USO").equals("ID_FUNC")){
                    yyerrorSemantico("No se puede operar con funciones");
                    errorAsignacion = true;
                    return false;
                }
            } else {
                yyerrorSemantico("No se puede operar con funciones");
                errorAsignacion = true;
                return false;
            }
        }
        return true;
    }

//#line 807 "Parser.java"
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
{ambitoActual.add(0, val_peek(1).sval); addAtributoLexema(val_peek(1).sval,"USO","Nombre de Programa"); nombrePrograma = val_peek(1).sval;}
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
{
                                    addEstructura("Declaracion de variables de funciones");
                                    Map<String, Object> atributos;
                                    for(String lexema: variables){
                                        atributos = lexico.getAtributosLexema(lexema);
                                        atributos.put("TIPO", val_peek(6).sval);
                                        atributos.put("TIPO_PARAMETRO", val_peek(3).sval);
                                    }
                                    variables.clear();
                                    }
break;
case 17:
//#line 61 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 18:
//#line 62 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 19:
//#line 63 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 20:
//#line 64 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 21:
//#line 65 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 22:
//#line 66 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 23:
//#line 70 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 24:
//#line 72 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 29:
//#line 81 "gramatica.y"
{checkRetornoFuncion(val_peek(1).sval); addTerceto(new Terceto("RETURN_FUNC", val_peek(1).sval, null, getTipo(val_peek(1).sval)));}
break;
case 30:
//#line 84 "gramatica.y"
{
                                        Terceto bifurcacionTrue = new Terceto("BT", val_peek(4).sval, null);
                                        addTerceto(bifurcacionTrue);
                                        addTerceto(new Terceto("PRINT", val_peek(1).sval + "%"));
                                        addCadena(val_peek(1).sval + "%");
                                        /* Terceto bifurcacionIncondicional = new Terceto("END_PRE", null);*/
                                        addTerceto(new Terceto("END_PRE", null));
                                        /* addTerceto(bifurcacionIncondicional);*/
                                        /* backpatching.push(bifurcacionIncondicional);*/
                                        bifurcacionTrue.setOperando2("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                        }
break;
case 31:
//#line 98 "gramatica.y"
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
//#line 113 "gramatica.y"
{addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
break;
case 34:
//#line 119 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 35:
//#line 123 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VAR_FUNC");}
break;
case 36:
//#line 124 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VAR_FUNC");}
break;
case 37:
//#line 128 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 38:
//#line 129 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 39:
//#line 132 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 42:
//#line 137 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 43:
//#line 138 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 44:
//#line 141 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 47:
//#line 146 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 48:
//#line 147 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 53:
//#line 158 "gramatica.y"
{addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
break;
case 55:
//#line 162 "gramatica.y"
{
                                    addEstructura("Asignacion"); 
                                    String id = getAmbitoIdentificador(val_peek(3).sval);
                                    if(id == null){
                                        yyerrorSemantico("Variable \"" + val_peek(3).sval + "\" no declarada.");
                                        addTerceto(new Terceto(":=", val_peek(3).sval, val_peek(1).sval, "Error - no declarada"));
                                    } 
                                    else{
                                        if(val_peek(1).sval != null){
                                            if(!val_peek(1).sval.startsWith("[")){
                                                if(Character.isDigit(val_peek(1).sval.charAt(0)) || val_peek(1).sval.startsWith(".") || val_peek(1).sval.startsWith("-")){
                                                    if(checkTipos(id,val_peek(1).sval))
                                                        addTerceto(new Terceto(":=", id, val_peek(1).sval, getTipo(id)));
                                                } else{
                                                    Map<String, Map<String, Object>> tablaSimbolos = lexico.getTablaSimbolos();
                                                    if(!tablaSimbolos.get(val_peek(1).sval).get("USO").equals("ID_VAR_FUNC") && !tablaSimbolos.get(val_peek(1).sval).get("USO").equals("ID_FUNC")){
                                                        if(checkTipos(id,val_peek(1).sval))
                                                            addTerceto(new Terceto(":=", id, val_peek(1).sval, getTipo(id)));
                                                        else 
                                                            addTerceto(new Terceto(":=", id, val_peek(1).sval, "Error de tipo"));
                                                    } else{
                                                        if(tablaSimbolos.get(id).get("USO").equals("ID_VAR_FUNC"))
                                                            addTerceto(new Terceto("ASIG_FUNC",id,val_peek(1).sval));
                                                        else
                                                            yyerrorSemantico("Error de asignacion, no se puede asignar una funcion a la variable del lado izquierdo");
                                                    }
                                                }
                                            }else{
                                                if(!errorAsignacion){
                                                    if(checkTipos(id,val_peek(1).sval))
                                                        addTerceto(new Terceto(":=", id, val_peek(1).sval, getTipo(id)));
                                                    else 
                                                        addTerceto(new Terceto(":=", id, val_peek(1).sval, "Error de tipo"));
                                                } else{
                                                    errorAsignacion = false;
                                                }
                                            }
                                        }
                                    }
                                        
                                      
                                }
break;
case 56:
//#line 204 "gramatica.y"
{addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", val_peek(2).sval + "%")); addCadena(val_peek(2).sval + "%");}
break;
case 61:
//#line 209 "gramatica.y"
{yyerrorSemantico("Sentencia no reconocida");}
break;
case 62:
//#line 213 "gramatica.y"
{addEstructura("Sentencia IF");
                                    addTerceto(new Terceto("END_IF"));}
break;
case 63:
//#line 217 "gramatica.y"
{ 
                                                    Terceto terceto = new Terceto("BF", val_peek(1).sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto);
                                                    }
break;
case 65:
//#line 226 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop(); 
                                    
                                    tercetoIncompleto.setOperando2("[" + (tercetos.get(getIdentificadorFuncionActual()).size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
break;
case 66:
//#line 233 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop();
                                    tercetoIncompleto.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");}
break;
case 67:
//#line 238 "gramatica.y"
{
                                            String id = getAmbitoIdentificador(val_peek(2).sval); 
                                            if(!getTipo(id).equals("INT"))
                                                yyerrorSemantico("La variable debe ser de tipo entero");
                                            addTerceto(new Terceto(":=", id, val_peek(0).sval)); 
                                            addTerceto(new Terceto("REPEAT"));
                                            }
break;
case 68:
//#line 246 "gramatica.y"
{ 
                                            Terceto tercetoIncompleto = new Terceto("BF", val_peek(0).sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); }
break;
case 69:
//#line 249 "gramatica.y"
{ 
                                        addEstructura("Sentencia REPEAT");
                                        Terceto bifurcacionFalse = backpatching.pop();
                                        /* Terceto destinoBifurcacionIncondicional = backpatching.pop();*/
                                        String id = getAmbitoIdentificador(val_peek(10).sval);
                                        addTercetoAritmetica("+",id,val_peek(2).sval);
                                        addTerceto(new Terceto(":=", id, getReferenciaUltimaInstruccion().sval));
                                        /* String referenciaBI = "[" + tercetos.get(getIdentificadorFuncionActual()).indexOf(destinoBifurcacionIncondicional) + "]";*/
                                        /* addTerceto(new Terceto("BI", referenciaBI));*/
                                        String referenciaBF = "[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]";
                                        bifurcacionFalse.setOperando2(referenciaBF);
                                        addTerceto(new Terceto("END_REPEAT"));
                                        }
break;
case 70:
//#line 264 "gramatica.y"
{
                                    if(!getTipo(val_peek(0).sval).equals("INT"))
                                        yyerrorSemantico("La expresion debe ser de tipo entero");
                                    
                                    Terceto terceto = new Terceto(val_peek(1).sval, getAmbitoIdentificador(val_peek(2).sval), val_peek(0).sval); 
                                    addTerceto(terceto); 
                                    backpatching.push(terceto); 
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 71:
//#line 274 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 72:
//#line 275 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 73:
//#line 278 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 74:
//#line 279 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 75:
//#line 280 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 76:
//#line 281 "gramatica.y"
{yyerror("Error en la sentencia PRINT");}
break;
case 77:
//#line 284 "gramatica.y"
{yyval = val_peek(0);}
break;
case 78:
//#line 287 "gramatica.y"
{ 
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto("||", val_peek(2).sval, val_peek(0).sval, "INT")); 
                                            yyval = getReferenciaUltimaInstruccion();}
break;
case 79:
//#line 291 "gramatica.y"
{yyval = val_peek(0);}
break;
case 80:
//#line 294 "gramatica.y"
{ 
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto("&&", val_peek(2).sval, val_peek(0).sval, "INT")); 
                                            yyval = getReferenciaUltimaInstruccion();
                                        }
break;
case 81:
//#line 299 "gramatica.y"
{yyval = val_peek(0);}
break;
case 82:
//#line 302 "gramatica.y"
{
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval,"INT")); 
                                            yyval = getReferenciaUltimaInstruccion();
                                            }
break;
case 83:
//#line 307 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 84:
//#line 310 "gramatica.y"
{ addTerceto(new Terceto("CONV", val_peek(1).sval, null, "SINGLE")); yyval = getReferenciaUltimaInstruccion();}
break;
case 85:
//#line 313 "gramatica.y"
{ 
                                    if(checkUso(val_peek(2).sval,val_peek(0).sval)){
                                        addTercetoAritmetica("+",val_peek(2).sval,val_peek(0).sval);
                                        yyval = getReferenciaUltimaInstruccion();
                                    }
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    /* addTercetoAritmetica("+",$1.sval,$3.sval);*/
                                    /* $$ = getReferenciaUltimaInstruccion(); }*/
                                    }
break;
case 86:
//#line 322 "gramatica.y"
{ 
                                    if(checkUso(val_peek(2).sval,val_peek(0).sval)){
                                        addTercetoAritmetica("-",val_peek(2).sval,val_peek(0).sval);
                                        yyval = getReferenciaUltimaInstruccion();
                                    }
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    /* addTercetoAritmetica("-",$1.sval,$3.sval);*/
                                    /* $$ = getReferenciaUltimaInstruccion(); }*/
                                    }
break;
case 87:
//#line 331 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 88:
//#line 334 "gramatica.y"
{ 
                                    if(checkUso(val_peek(2).sval,val_peek(0).sval)){
                                        addTercetoAritmetica("*",val_peek(2).sval,val_peek(0).sval);
                                        yyval = getReferenciaUltimaInstruccion();
                                    }
                                    /* addTercetoAritmetica("*",$1.sval,$3.sval);*/
                                    /* $$ = getReferenciaUltimaInstruccion(); }*/
                                    }
break;
case 89:
//#line 343 "gramatica.y"
{ 
                                    if(checkUso(val_peek(2).sval,val_peek(0).sval)){
                                        addTercetoAritmetica("/",val_peek(2).sval,val_peek(0).sval);
                                        yyval = getReferenciaUltimaInstruccion();
                                    }
                                    /* addTercetoAritmetica("/",$1.sval,$3.sval);*/
                                    /* $$ = getReferenciaUltimaInstruccion();}*/
                                }
break;
case 90:
//#line 351 "gramatica.y"
{yyval = val_peek(0);}
break;
case 91:
//#line 354 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 92:
//#line 355 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 93:
//#line 359 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 94:
//#line 362 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval);}
break;
case 95:
//#line 363 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = val_peek(0);}
break;
case 96:
//#line 366 "gramatica.y"
{String id = getAmbitoIdentificador(val_peek(3).sval);
                                                                
                                                                if (id != null) {
                                                                    if(lexico.getAtributosLexema(id).get("USO").equals("ID_FUNC") || lexico.getAtributosLexema(id).get("USO").equals("ID_VAR_FUNC")){
                                                                        if(lexico.getAtributosLexema(id).get("TIPO_PARAMETRO").equals(getTipo(val_peek(1).sval))){
                                                                            if(lexico.getAtributosLexema(id).get("USO").equals("ID_FUNC"))
                                                                                addTerceto(new Terceto("CALL_FUNC", id, val_peek(1).sval,getTipo(id))); 
                                                                            else
                                                                                addTerceto(new Terceto("CALL_FUNC_VAR", id, val_peek(1).sval,getTipo(id))); 
                                                                            yyval = getReferenciaUltimaInstruccion();
                                                                        } else {
                                                                            yyerrorSemantico("Tipo de la expresion incompatible con el parametro de la funcion");
                                                                            yyval = getReferenciaUltimaInstruccion();
                                                                        }
                                                                    } else{
                                                                        yyerrorSemantico("\"" + val_peek(3).sval + "\" es una variable, no una funcion");
                                                                        yyval = new ParserVal(id);
                                                                    }
                                                                } else {
                                                                    yyerrorSemantico("Funcion no declarada");
                                                                    yyval = new ParserVal(id);
                                                                }
                                                        }
break;
case 97:
//#line 392 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 98:
//#line 393 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 99:
//#line 394 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 100:
//#line 395 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 101:
//#line 396 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 102:
//#line 397 "gramatica.y"
{ yyval = new ParserVal("<>"); }
break;
case 103:
//#line 400 "gramatica.y"
{tipo = "SINGLE"; yyval = new ParserVal("SINGLE");}
break;
case 104:
//#line 401 "gramatica.y"
{tipo = "INT";  yyval = new ParserVal("INT");}
break;
//#line 1434 "Parser.java"
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
