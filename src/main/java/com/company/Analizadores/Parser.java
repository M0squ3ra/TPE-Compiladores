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
    import java.util.ArrayList;
    import java.util.List;
    import java.io.IOException;
    import java.math.BigDecimal;
//#line 25 "Parser.java"




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
public final static short FLOAT=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    7,    7,   10,   10,   10,   10,   10,   10,
    6,    6,    6,    6,    6,   14,   14,   14,   14,   14,
   14,   14,   14,   14,    5,    5,   15,    9,    9,    4,
    4,    4,   17,   17,    2,    2,   16,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   19,   19,   20,
   20,   20,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   13,   13,   13,   18,   25,   12,   12,   12,
   26,   26,   26,   11,   11,   28,   27,   27,   27,   27,
   27,   24,   24,   24,   24,   24,   24,   23,   23,    8,
    8,
};
final static short yylen[] = {                            2,
    5,    4,    1,    1,    3,    3,    5,    2,    2,    1,
    1,    1,    7,    1,    7,    7,    7,    7,    7,    7,
   16,   15,   24,   23,    1,   16,   16,   16,   16,   16,
   16,   16,   16,   15,    3,    1,    3,    3,    1,    3,
    1,    1,    3,    3,    2,    1,    4,    5,    2,   10,
    8,   11,    5,    1,    1,    1,    1,    4,    4,    4,
    4,    4,   10,   10,   10,   10,   10,   10,    8,    8,
    8,    8,    8,   11,   11,   11,   10,   11,   10,   11,
   11,   11,    3,    3,    1,    3,    4,    3,    3,    1,
    3,    3,    1,    2,    1,    2,    1,    1,    2,    1,
    4,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,  111,  110,    0,    0,    3,   10,   11,   12,
    0,   14,   25,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    8,    9,    0,
   41,   42,   54,   55,   56,   57,    0,   39,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    0,   49,    0,    0,    5,    0,    0,    0,    0,
   37,    0,    0,    0,   35,    0,    0,    0,    0,    2,
   45,    0,    0,   98,    0,    0,    0,  100,    0,   93,
   43,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   38,    0,    0,   95,    0,
    7,    1,    0,   99,    0,   58,    0,    0,    0,    0,
   59,   47,    0,  104,  105,  106,  107,  108,  109,  103,
  102,    0,    0,    0,    0,    0,    0,   60,    0,   62,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   96,   94,    0,    0,    0,
    0,    0,    0,   91,   92,   53,    0,    0,    0,    0,
    0,    0,    0,   48,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   15,  101,   87,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   16,
    0,   17,    0,    0,    0,    0,    0,    0,   18,   19,
   20,   13,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   69,    0,   70,    0,   71,
    0,   72,   73,    0,    0,   51,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   63,   64,   65,   66,   67,
   68,   50,    0,    0,    0,    0,    0,   79,    0,    0,
    0,   77,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   81,   74,   75,   76,   78,   80,
   82,   52,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   22,    0,    0,   34,
    0,   26,   27,   28,   29,   30,   31,   32,    0,   33,
    0,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   24,    0,   23,
};
final static short yydgoto[] = {                          5,
    6,   51,    7,   27,    8,    9,   10,   86,   40,   12,
  118,   95,   96,   13,   14,   31,   32,  218,   33,   34,
   35,   36,  143,  144,   88,   89,   90,  119,
};
final static short yysindex[] = {                      -244,
 -203, -168,    0,    0,    0, -179,    0,    0,    0,    0,
 -235,    0,    0,    0, -255,  -33, -161,  525, -158,  238,
  -36,   -8,  -31,  183,  -37,   -6, -227,    0,    0, -205,
    0,    0,    0,    0,    0,    0,  -22,    0,  -15,   19,
   12,  -99, -165,  525, -127,  258,    0,  525,   17,  -96,
  289,   47,   17,   17,   91,  157,  -26,  295,  525,  327,
    0,    0,    0,  -62, -210,    0,    1, -106,   80,  -99,
    0,  -35,   -5, -143,    0,  -52,  -99,  255,  333,    0,
    0,  339,  174,    0,   44,  262,   83,    0,  163,    0,
    0,  257,   87,  307,  158,  217,  286,  156,  272,    8,
  277, -127,    0,   77,   86,  -80,  313,  -99,  317,  -99,
  323,  -99, -139,  335,    3,    0,  -83,  354,    0,  116,
    0,    0,   17,    0,   17,    0,   17,   17,   17,   17,
    0,    0,  345,    0,    0,    0,    0,    0,    0,    0,
    0,  138,   17,   17,  140,  147,  -86,    0,  365,    0,
    0,  161,  172,  175, -183,  405,  116,  413,  116,  419,
  430,    4,  116,  116,  -78,    0,    0,  -99,   37,  342,
  390,  163,  163,    0,    0,    0,  183,  158,  158,  183,
  183,  183,  183,    0,  399,  407,  426,  429,   23,  -99,
   43,  -99,   75,  -99,  -99,  -99, -145,   89,   97,  446,
  101,  202,  -76,    0,    0,    0,   62,  127,  151,  173,
 -109,  265,  265,  265,  265,  266,   36,  462,  267,    0,
  273,    0,  300,  301,  311,  -99, -129, -240,    0,    0,
    0,    0,  525,  474,  183,  512,  183,  534,  183,  539,
  183,  540,  -56,  183,  542,  550,  555,  556,  559,   17,
  561,  -32,  282,  525,  525,  525,  525,  525,  351,  563,
  410,  525,  -74,  425,  358,    0,  362,    0,  369,    0,
  384,    0,    0,  391, -200,    0,  401,  403,  404,  406,
  158,  414,  627, -144,  641,  435,  445,  455,  465,  475,
  525,  644,  646,  485,  629,  495,  652,  634,  636,  637,
  638,  643,  647,  653,  675,  676,  681,  685,  686,  183,
  691,    7,  183,  696,  697,  702,  706,  707,   76,   17,
   17,  712,  716,  717,   17,    0,    0,    0,    0,    0,
    0,    0,  183,  183,  183,  183,  183,    0,  183,  183,
  183,    0,   17,   17,   17,   17,   17,   17,  395,  408,
   17,   17,   17,  424,    0,    0,    0,    0,    0,    0,
    0,    0,  456,  463,  472,  489,  496,  499,  718,  708,
  520,  402,  529,  713,  723,  727,  728,  732,  733,  737,
  516,  522,  738,  722,   -4,  523,  524,  527,  530,  531,
  532,  533,  743,  747,  535,  552,  752,  537,  756,  757,
  758,  759,  760,  761,  762,  525,    0,  763,  764,    0,
  765,    0,    0,    0,    0,    0,    0,    0,  505,    0,
  525,    0,  736,  515,   17,  785,  549,   17,  767,  590,
  551,  768,  770,  553,    0,  772,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  832,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   11,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,    0,    0,    0,    0,    0,  134,    0,
    0,    0,    0,    0,  233,    0,    0,    0,    0,    0,
    0,   66,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  322,  366,    0,    0,    0,    0,  380,  388,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   -2,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  508,   -1,    0,  -16,  481,   74,    0,  360,  -89,    0,
   79,  -39,  -54,    0,    0,  -18,    0,  269,    0,    0,
    0,    0,    0,  617,    0,  392,  417,    0,
};
final static int YYTABLESIZE=833;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   98,   47,  273,   53,  110,   62,   42,   61,   57,   87,
   41,    1,   93,   94,  101,  262,   46,   70,   16,    2,
   37,   63,   60,    3,   74,   47,  284,   81,   38,   47,
  169,   55,   81,   65,  113,  263,   71,    4,   39,   47,
   47,   81,   79,  165,  197,  105,   82,  341,  149,   66,
   67,   77,   15,  106,  398,  303,   86,   60,   38,   71,
   81,   85,   76,   81,    3,   44,  150,  191,   68,  193,
   16,  304,  188,  198,  199,  201,   20,   75,    4,   29,
   76,  217,  189,  170,   21,  171,   76,   15,    3,   22,
   29,   85,   29,   23,   43,  204,   24,   43,   25,    3,
   26,  220,    4,  178,  179,   44,    3,   18,   50,    3,
  226,  311,  114,    4,   44,  348,  161,   48,   76,  108,
    4,  312,    3,    4,    3,  127,   45,  128,    3,  127,
  227,  128,   76,  222,   21,   85,    4,   49,    4,   22,
   76,  126,    4,   23,   76,  132,  243,  229,   25,  107,
   26,  260,   97,   97,   97,  230,   97,   73,   97,  232,
  207,  244,  245,  208,  209,  210,  211,   41,    3,  182,
   97,   97,  166,   97,   90,  154,   90,  200,   90,  234,
  167,   45,    4,  183,  155,   38,  156,   38,  158,   21,
  160,  162,   90,   90,   22,   90,  147,   99,   23,   58,
  127,  104,  128,   25,  129,   26,  295,   21,   47,  130,
  281,  116,   22,  123,   47,  140,   23,  141,  265,   59,
  267,   25,  269,   26,  271,  261,  274,  275,   52,   56,
   41,  264,    3,  283,  100,   47,   47,   47,   47,   47,
   72,   69,   81,   47,   47,   81,    4,   54,   73,   64,
  112,  397,  286,  287,  288,  289,  290,  142,  164,  196,
  294,  296,  340,   86,   69,  349,   46,   81,   81,   81,
   81,   81,   47,   85,   46,   81,  140,   81,  141,   46,
   83,  350,   84,   46,    3,  354,  216,   46,   46,  319,
   46,  251,   85,  338,   85,  120,  342,  372,    4,  216,
   81,  125,   92,  363,  364,  365,  366,  367,  368,  124,
   83,  371,   84,  373,    3,  131,  355,  356,  357,  358,
  359,   44,  360,  361,  362,  140,  145,  141,    4,   44,
  148,   45,  235,  236,   44,  151,   44,   44,   44,   21,
   44,  152,   44,   44,   22,   44,   97,  133,   23,  127,
  153,  128,  110,   25,   83,   26,   84,  157,    3,   11,
   11,   11,   88,  159,   88,   30,   88,   97,   97,   97,
   97,   97,    4,   97,   97,  163,   30,   97,   30,   38,
   88,   88,  205,   88,  127,  427,  128,   47,  430,   90,
   90,   90,   90,   90,  168,   90,   90,  237,  238,   90,
   81,   78,   47,  176,  419,   81,   89,  177,   89,  180,
   89,  146,  134,  135,  136,  137,  181,  138,  139,  424,
   83,  239,  240,  184,   89,   89,  185,   89,   84,  109,
  206,  111,  127,  115,  128,  369,  117,  186,   58,   83,
  187,   83,  384,  241,  242,  190,   21,   84,  370,   84,
  127,   22,  128,  192,  140,   23,  141,  212,   59,  194,
   25,  140,   26,  141,  374,  213,  127,  117,  128,  117,
  195,  117,  117,  134,  135,  136,  137,  233,  138,  139,
  246,  247,  248,  249,  214,  252,   28,  215,   85,   85,
   85,   85,   85,   45,   85,   85,  375,   28,  127,   28,
  128,   21,   49,  376,  231,  127,   22,  128,   17,   19,
   23,   50,  377,   45,  127,   25,  128,   26,  172,  173,
  253,   21,  134,  135,  136,  137,   22,  203,  216,  378,
   23,  127,   71,  128,   80,   25,  379,   26,  127,  380,
  128,  127,  254,  128,   45,  174,  175,  285,  255,  203,
   45,  203,   21,  203,  203,  203,  203,   22,   21,   49,
  383,   23,  127,   22,  128,   91,   25,   23,   26,  385,
  266,  127,   25,  128,   26,  256,  257,   88,   88,   88,
   88,   88,  102,   88,   88,  203,  258,   88,   45,  429,
   21,  127,  268,  128,   45,   22,   21,  270,  272,   23,
  276,   22,   21,  103,   25,   23,   26,   22,  277,  121,
   25,   23,   26,  278,  279,  122,   25,  280,   26,  282,
  292,   89,   89,   89,   89,   89,  291,   89,   89,  298,
  432,   89,  127,  299,  128,   83,   83,   83,   83,   83,
  300,   83,   83,   84,   84,   84,   84,   84,  202,   84,
   84,  134,  135,  136,  137,  301,  138,  139,  134,  135,
  136,  137,  302,  138,  139,   45,  305,  310,  306,  307,
  219,  308,  221,   21,  223,  224,  225,  228,   22,  309,
   45,  313,   23,  320,  293,  321,  323,   25,   21,   26,
   45,  325,  326,   22,  327,  328,  329,   23,   21,  297,
   45,  330,   25,   22,   26,  331,  259,   23,   21,  314,
   45,  332,   25,   22,   26,  333,  334,   23,   21,  315,
   45,  335,   25,   22,   26,  336,  337,   23,   21,  316,
   45,  339,   25,   22,   26,  343,  344,   23,   21,  317,
   45,  345,   25,   22,   26,  346,  347,   23,   21,  318,
   45,  351,   25,   22,   26,  352,  353,   23,   21,  322,
   45,  381,   25,   22,   26,  396,  382,   23,   21,  324,
   45,  386,   25,   22,   26,  425,  393,   23,   21,  423,
   45,  387,   25,   22,   26,  388,  389,   23,   21,  426,
  390,  391,   25,   22,   26,  392,  395,   23,  394,  399,
  400,  406,   25,  401,   26,  407,  402,  403,  404,  405,
  410,  408,  409,  411,  412,  413,  414,  415,  416,  417,
  418,  420,  421,  422,  428,  431,  434,  433,  435,  436,
  437,    4,  250,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
   55,   20,   59,   40,   40,   24,   40,   24,   40,   49,
    0,  256,   52,   53,   41,  256,   18,   40,  274,  264,
  256,   59,   24,  268,   40,   44,   59,   46,  264,   48,
  120,   40,   51,   40,   40,  276,   59,  282,  274,   58,
   59,   60,   44,   41,   41,  256,   48,   41,   41,  277,
  256,   40,  256,  264,   59,  256,   59,   59,  264,   59,
   79,   45,   44,   82,  268,    0,   59,  157,  274,  159,
  274,  272,  256,  163,  164,  165,  256,   59,  282,    6,
   44,   59,  266,  123,  264,  125,   44,  256,  268,  269,
   17,   45,   19,  273,  256,   59,  276,  256,  278,  268,
  280,   59,  282,  143,  144,   40,  268,  276,  274,  268,
  256,  256,  256,  282,  276,   40,  256,  276,   44,   40,
  282,  266,  268,  282,  268,   43,  256,   45,  268,   43,
  276,   45,   44,   59,  264,   45,  282,  265,  282,  269,
   44,   59,  282,  273,   44,   59,  256,   59,  278,  256,
  280,  281,   41,   42,   43,   59,   45,  264,   47,   59,
  177,  271,  272,  180,  181,  182,  183,  264,  268,  256,
   59,   60,  256,   62,   41,  256,   43,  256,   45,  256,
  264,  256,  282,  270,  265,  264,  108,  264,  110,  264,
  112,  113,   59,   60,  269,   62,   41,   41,  273,  256,
   43,  264,   45,  278,   42,  280,  281,  264,  227,   47,
  250,  264,  269,   40,  233,   60,  273,   62,  235,  276,
  237,  278,  239,  280,  241,  227,  243,  244,  265,  261,
  264,  233,  268,  266,  261,  254,  255,  256,  257,  258,
  256,  264,  261,  262,  263,  264,  282,  256,  264,  256,
  256,  256,  254,  255,  256,  257,  258,   41,  256,  256,
  262,  263,  256,  266,  264,  320,  256,  286,  287,  288,
  289,  290,  291,   41,  264,  294,   60,  296,   62,  269,
  264,  321,  266,  273,  268,  325,  264,  277,  278,  291,
  280,  256,   60,  310,   62,   41,  313,  352,  282,  264,
  319,   40,  256,  343,  344,  345,  346,  347,  348,  266,
  264,  351,  266,  353,  268,   59,  333,  334,  335,  336,
  337,  256,  339,  340,  341,   60,   41,   62,  282,  264,
   59,  256,  271,  272,  269,   59,  271,  272,  273,  264,
  275,  265,  277,  278,  269,  280,  256,   41,  273,   43,
  265,   45,   40,  278,  264,  280,  266,   41,  268,    0,
    1,    2,   41,   41,   43,    6,   45,  256,  257,  258,
  259,  260,  282,  262,  263,   41,   17,  266,   19,  264,
   59,   60,   41,   62,   43,  425,   45,  406,  428,  256,
  257,  258,  259,  260,   41,  262,  263,  271,  272,  266,
  419,   42,  421,   59,  406,  424,   41,  270,   43,  270,
   45,  256,  257,  258,  259,  260,  270,  262,  263,  421,
   41,  271,  272,   59,   59,   60,  266,   62,   41,   70,
   41,   72,   43,   74,   45,   41,   77,  266,  256,   60,
  266,   62,   41,  271,  272,   41,  264,   60,   41,   62,
   43,  269,   45,   41,   60,  273,   62,   59,  276,   41,
  278,   60,  280,   62,   41,   59,   43,  108,   45,  110,
   41,  112,  113,  257,  258,  259,  260,  276,  262,  263,
  212,  213,  214,  215,   59,  217,    6,   59,  256,  257,
  258,  259,  260,  256,  262,  263,   41,   17,   43,   19,
   45,  264,  265,   41,   59,   43,  269,   45,    1,    2,
  273,  274,   41,  256,   43,  278,   45,  280,  127,  128,
   59,  264,  257,  258,  259,  260,  269,  168,  264,   41,
  273,   43,   59,   45,  277,  278,   41,  280,   43,   41,
   45,   43,  276,   45,  256,  129,  130,  266,  276,  190,
  256,  192,  264,  194,  195,  196,  197,  269,  264,  265,
   41,  273,   43,  269,   45,  277,  278,  273,  280,   41,
   59,   43,  278,   45,  280,  276,  276,  256,  257,  258,
  259,  260,  256,  262,  263,  226,  276,  266,  256,   41,
  264,   43,   59,   45,  256,  269,  264,   59,   59,  273,
   59,  269,  264,  277,  278,  273,  280,  269,   59,  277,
  278,  273,  280,   59,   59,  277,  278,   59,  280,   59,
   58,  256,  257,  258,  259,  260,  276,  262,  263,  272,
   41,  266,   43,  272,   45,  256,  257,  258,  259,  260,
  272,  262,  263,  256,  257,  258,  259,  260,  168,  262,
  263,  257,  258,  259,  260,  272,  262,  263,  257,  258,
  259,  260,  272,  262,  263,  256,  266,   41,  266,  266,
  190,  266,  192,  264,  194,  195,  196,  197,  269,  266,
  256,   41,  273,   40,  275,   40,   58,  278,  264,  280,
  256,   40,   59,  269,   59,   59,   59,  273,  264,  275,
  256,   59,  278,  269,  280,   59,  226,  273,  264,  275,
  256,   59,  278,  269,  280,   41,   41,  273,  264,  275,
  256,   41,  278,  269,  280,   41,   41,  273,  264,  275,
  256,   41,  278,  269,  280,   40,   40,  273,  264,  275,
  256,   40,  278,  269,  280,   40,   40,  273,  264,  275,
  256,   40,  278,  269,  280,   40,   40,  273,  264,  275,
  256,   44,  278,  269,  280,   44,   59,  273,  264,  275,
  256,   59,  278,  269,  280,   40,  261,  273,  264,  275,
  256,   59,  278,  269,  280,   59,   59,  273,  264,  275,
   59,   59,  278,  269,  280,   59,   59,  273,  277,  277,
  277,   59,  278,  277,  280,   59,  277,  277,  277,  277,
   59,  277,  261,  277,   59,   59,   59,   59,   59,   59,
   59,   59,   59,   59,   40,   59,   59,  277,   59,  277,
   59,    0,  216,
};
}
final static short YYFINAL=5;
final static short YYMAXTOKEN=284;
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
"SINGLE","REPEAT","PRE","FLOAT","\"&&\"","\"||\"",
};
final static String yyrule[] = {
"$accept : PROGRAMA",
"PROGRAMA : IDENTIFICADOR SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END",
"PROGRAMA : IDENTIFICADOR BEGIN CONJUNTO_SENTENCIAS END",
"PROGRAMA : PROGRAMA_ERROR",
"PROGRAMA_ERROR : SENTENCIA_DECLARATIVA",
"PROGRAMA_ERROR : SENTENCIA_DECLARATIVA BLOQUE_SENTENCIA END",
"PROGRAMA_ERROR : SENTENCIA_DECLARATIVA BEGIN BLOQUE_SENTENCIA",
"PROGRAMA_ERROR : error SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA DECLARACION_VARIABLES",
"SENTENCIA_DECLARATIVA : SENTENCIA_DECLARATIVA DECLARACION_FUNC",
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
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN PRE ':' '(' CONDICION ')' ',' CADENA ';' CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN PRE ':' '(' CONDICION ')' ',' CADENA ';' CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : DECLARACION_FUNC_ERROR",
"DECLARACION_FUNC_ERROR : error FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO error IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC error '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR error PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' error ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO error DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' error DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES error CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACION_VARIABLES BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' error ';'",
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
"SENTENCIA_EJECUTABLE : IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'",
"SENTENCIA_EJECUTABLE : IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ENDIF ';'",
"SENTENCIA_EJECUTABLE : REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"SENTENCIA_EJECUTABLE : IDENTIFICADOR '(' EXPRESION ')' ';'",
"SENTENCIA_EJECUTABLE : ASIGNACION_ERROR",
"SENTENCIA_EJECUTABLE : PRINT_ERROR",
"SENTENCIA_EJECUTABLE : IF_ERROR",
"SENTENCIA_EJECUTABLE : REPEAT_ERROR",
"ASIGNACION_ERROR : error ASIGNACION EXPRESION ';'",
"ASIGNACION_ERROR : IDENTIFICADOR ASIGNACION error ';'",
"PRINT_ERROR : PRINT CADENA ')' ';'",
"PRINT_ERROR : PRINT '(' ')' ';'",
"PRINT_ERROR : PRINT '(' CADENA ';'",
"IF_ERROR : IF error CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' error ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION error THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION ')' error BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA error BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA error ';'",
"IF_ERROR : IF error CONDICION ')' THEN BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' error ')' THEN BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION error THEN BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION ')' error BLOQUE_SENTENCIA ENDIF ';'",
"IF_ERROR : IF '(' CONDICION ')' THEN BLOQUE_SENTENCIA error ';'",
"REPEAT_ERROR : REPEAT '(' error ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR error CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR ASIGNACION error ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR ASIGNACION CTE CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' error ';' CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' error ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT error IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"REPEAT_ERROR : REPEAT '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE error BLOQUE_SENTENCIA",
"CONDICION : CONDICION OPERADOR_LOGICO EXPRESION",
"CONDICION : CONDICION OPERADOR_COMPARADOR EXPRESION",
"CONDICION : EXPRESION",
"CONDICION_REPEAT : IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION",
"CONVERSION : TIPO '(' EXPRESION ')'",
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
"TIPO : FLOAT",
"TIPO : INT",
};

//#line 195 "gramatica.y"

    private Lexico lexico = Lexico.getInstance();
    private List<Error> erroresSintacticos = new ArrayList<Error>(); 
    private List<Integer> tokensReconocidos = new ArrayList<Integer>(); 

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
    
    public List<Error> getErroresSintacticos(){
        return erroresSintacticos;
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
    
    public void printEstructura(String s){
        System.out.print(String.format("%-15s", "[Linea "+String.valueOf(lexico.getLinea())+"]"));
        System.out.println(s);
    }
//#line 674 "Parser.java"
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
//#line 25 "gramatica.y"
{yyerror("Bloque principal no especificado.");}
break;
case 5:
//#line 26 "gramatica.y"
{yyerror("BEGIN del bloque principal no especificado.");}
break;
case 6:
//#line 27 "gramatica.y"
{yyerror("END del bloque principal no especificado.");}
break;
case 7:
//#line 28 "gramatica.y"
{yyerror("Falta el nombre del programa.");}
break;
case 15:
//#line 42 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 16:
//#line 43 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 17:
//#line 44 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 18:
//#line 45 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 19:
//#line 46 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 20:
//#line 47 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 21:
//#line 50 "gramatica.y"
{printEstructura("Declaracion de funcion");}
break;
case 22:
//#line 51 "gramatica.y"
{printEstructura("Declaracion de funcion");}
break;
case 23:
//#line 52 "gramatica.y"
{printEstructura("Declaracion de funcion");}
break;
case 24:
//#line 53 "gramatica.y"
{printEstructura("Declaracion de funcion");}
break;
case 26:
//#line 57 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 27:
//#line 58 "gramatica.y"
{yyerror("Falta la palabra clave FUNC.");}
break;
case 28:
//#line 59 "gramatica.y"
{yyerror("Falta el identificador de la funcion.");}
break;
case 29:
//#line 60 "gramatica.y"
{yyerror("Falta el primer parentesis de la funcion.");}
break;
case 30:
//#line 61 "gramatica.y"
{yyerror("Falta el parametro de la funcion.");}
break;
case 31:
//#line 62 "gramatica.y"
{yyerror("Falta el segundo parentesis de la funcion.");}
break;
case 32:
//#line 63 "gramatica.y"
{yyerror("Falta el return de la funcion.");}
break;
case 33:
//#line 64 "gramatica.y"
{yyerror("Falta el BEGIN de la funcion.");}
break;
case 34:
//#line 65 "gramatica.y"
{yyerror("Falta el END de la funcion.");}
break;
case 35:
//#line 68 "gramatica.y"
{printEstructura("Declaracion de varables");}
break;
case 37:
//#line 72 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 40:
//#line 79 "gramatica.y"
{printEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 43:
//#line 84 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 44:
//#line 85 "gramatica.y"
{yyerror("Falta el END del bloque de setnencia");}
break;
case 47:
//#line 92 "gramatica.y"
{printEstructura("Asignacion");}
break;
case 48:
//#line 93 "gramatica.y"
{printEstructura("PRINT");}
break;
case 49:
//#line 94 "gramatica.y"
{printEstructura("BREAK");}
break;
case 50:
//#line 95 "gramatica.y"
{printEstructura("Sentencia IF/ELSE");}
break;
case 51:
//#line 96 "gramatica.y"
{printEstructura("Sentencia IF");}
break;
case 52:
//#line 97 "gramatica.y"
{printEstructura("Sentencia REPEAT");}
break;
case 53:
//#line 98 "gramatica.y"
{printEstructura("Llamado a funcion");}
break;
case 58:
//#line 105 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 59:
//#line 106 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 60:
//#line 109 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 61:
//#line 110 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 62:
//#line 111 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 63:
//#line 114 "gramatica.y"
{yyerror("Falta el primer paréntesis de la condición del IF.");}
break;
case 64:
//#line 115 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 65:
//#line 116 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 66:
//#line 117 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 67:
//#line 118 "gramatica.y"
{yyerror("Falta el ELSE del IF.");}
break;
case 68:
//#line 119 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 69:
//#line 120 "gramatica.y"
{yyerror("Falta el primer paréntesiis de la condición del IF.");}
break;
case 70:
//#line 121 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 71:
//#line 122 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 72:
//#line 123 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 73:
//#line 124 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 74:
//#line 127 "gramatica.y"
{yyerror("Falta el identificador del REPEAT.");}
break;
case 75:
//#line 128 "gramatica.y"
{yyerror("Falta el asignador al identificador del REPEAT.");}
break;
case 76:
//#line 129 "gramatica.y"
{yyerror("El identificador no tiene constante a asignar del REPEAT.");}
break;
case 77:
//#line 130 "gramatica.y"
{yyerror("Falta ';' luego de la asignacion del REPEAT.");}
break;
case 78:
//#line 131 "gramatica.y"
{yyerror("Falta la condicion del ciclo del REPEAT.");}
break;
case 79:
//#line 132 "gramatica.y"
{yyerror("Falta ';' luego de la condicion del REPEAT.");}
break;
case 80:
//#line 133 "gramatica.y"
{yyerror("Falta la constante de iteracion del REPEAT.");}
break;
case 81:
//#line 134 "gramatica.y"
{yyerror("Falta el primer paréntesis del REPEAT.");}
break;
case 82:
//#line 135 "gramatica.y"
{yyerror("Falta el segundo paréntesis del REPEAT.");}
break;
case 96:
//#line 163 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 98:
//#line 167 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                        yyerror("Constante fuera de rango");
                                            }}
break;
case 99:
//#line 170 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 101:
//#line 174 "gramatica.y"
{printEstructura("Llamado a funcion como operando");}
break;
//#line 1083 "Parser.java"
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
