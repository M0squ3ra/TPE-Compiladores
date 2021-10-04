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
    1,    1,    1,    7,    7,   10,   10,   10,   10,   10,
   10,    6,    6,    6,    6,    6,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   12,   12,    5,    5,   16,
    9,    9,    4,    4,    4,   18,   18,    2,    2,   17,
   17,   17,   17,   17,   17,   17,   17,   17,   17,   17,
   20,   20,   21,   21,   21,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   14,   14,   14,   19,   26,
   13,   13,   13,   27,   27,   27,   11,   11,   29,   28,
   28,   28,   28,   28,   25,   25,   25,   25,   25,   25,
   24,   24,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    1,    1,    3,    3,    5,    2,    2,    2,
    1,    1,    1,    7,    1,    7,    7,    7,    7,    7,
    7,   16,   15,   24,   23,    1,   16,   16,   16,   16,
   16,   16,   16,   16,   15,    2,    1,    3,    1,    3,
    3,    1,    3,    1,    1,    3,    3,    2,    1,    4,
    5,    2,   10,    8,   11,    5,    1,    1,    1,    1,
    4,    4,    4,    4,    4,   10,   10,   10,   10,   10,
   10,    8,    8,    8,    8,    8,   11,   11,   11,   10,
   11,   10,   11,   11,   11,    3,    3,    1,    3,    4,
    3,    3,    1,    3,    3,    1,    2,    1,    2,    1,
    1,    2,    1,    4,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,  114,  113,    0,    0,    3,   11,   12,   13,
    0,   15,   26,   39,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    8,    9,   10,
   44,   45,   57,   58,   59,   60,    0,   42,    0,    0,
    0,    0,    0,    0,    0,   49,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    6,    0,
   52,    0,    0,    5,    0,    0,   40,    0,    0,    0,
   38,    0,    0,    0,    0,    2,   48,    0,    0,  101,
    0,    0,    0,  103,    0,   96,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   43,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   41,    0,    0,   98,    0,    7,    1,    0,  102,    0,
   61,    0,    0,    0,    0,   62,   50,    0,  107,  108,
  109,  110,  111,  112,  106,  105,    0,    0,    0,    0,
    0,    0,   63,    0,   65,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   99,   97,    0,    0,    0,    0,    0,    0,   94,   95,
   56,    0,    0,    0,    0,    0,    0,    0,   51,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   37,    0,    0,   16,
  104,   90,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   17,    0,   18,    0,    0,
    0,    0,    0,    0,   19,   20,   21,   14,    0,    0,
   36,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   72,    0,   73,    0,   74,    0,   75,   76,
    0,    0,   54,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   66,   67,   68,   69,   70,   71,   53,    0,
    0,    0,    0,    0,   82,    0,    0,    0,   80,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   84,   77,   78,   79,   81,   83,   85,   55,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   23,    0,    0,   35,    0,   27,   28,
   29,   30,   31,   32,   33,    0,   34,    0,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   25,    0,   24,
};
final static short yydgoto[] = {                          5,
    6,   49,    7,   27,  197,    9,   10,   82,   40,   12,
  113,  199,   91,   92,   13,   14,   31,   32,  214,   33,
   34,   35,   36,  138,  139,   84,   85,   86,  114,
};
final static short yysindex[] = {                      -232,
 -165, -175,    0,    0,    0, -174,    0,    0,    0,    0,
 -121,    0,    0,    0, -266,  -12, -156,  576, -140,  -78,
  -27,  -36,  -35,  185,  -16,    4, -257,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -14,    0,   -5,   52,
  -17,   23,  576, -182,  307,    0,  576,   47,  339,   94,
   47,   47,  109,   45,  -22,  255,  576,  402,    0,    0,
    0, -154, -202,    0,   89,   23,    0,  -24,    6, -235,
    0,  -83,   23,  162,  439,    0,    0,  457,  221,    0,
   50,  249,   79,    0,   42,    0,    0,  267,  115,  410,
  263,  427,  303,  214,  305,  -11,  309, -182,    0,  106,
  134, -138,   23,  354,   23,  373,   23, -204,  376,  -40,
    0, -108,  390,    0,  175,    0,    0,   47,    0,   47,
    0,   47,   47,   47,   47,    0,    0,  385,    0,    0,
    0,    0,    0,    0,    0,    0,  176,   47,   47,  180,
  192,   97,    0,  405,    0,    0,  201,  203,  212,  -57,
  461,  175,  471,  175,  475,  477,  -38,  175,  175,  -80,
    0,    0,   23,   87,  454,  493,   42,   42,    0,    0,
    0,  185,  263,  263,  185,  185,  185,  185,    0,  462,
  463,  464,  467,  -42,   23,   96,   23,  123,   23,   23,
   23, -135,  126,  135,  478,  139,    0,  -76,  -75,    0,
    0,    0,   77,  107,  150,  152, -122,  287,  287,  287,
  287,  283,   16,  515,   98,    0,  133,    0,  143,  184,
  216,   23, -193, -131,    0,    0,    0,    0,  519,  576,
    0,  185,  522,  185,  523,  185,  524,  185,  533,  237,
  185,  535,  538,  547,  551,  555,   47,  570,  -10,  299,
  576,  576,  576,  576,  576,  232,  549,  458,  576, -112,
  476,  343,    0,  355,    0,  358,    0,  364,    0,    0,
  367, -203,    0,  383,  386,  395,  396,  263,  398,  610,
  -52,  627,  486,  496,  506,  516,  526,  576,  630,  632,
  536,  615,  546,  634,  617,  618,  619,  622,  629,  639,
  640,  642,  659,  660,  661,  670,  185,  674,    1,  185,
  678,  680,  683,  684,  685,   83,   47,   47,  688,  689,
  699,   47,    0,    0,    0,    0,    0,    0,    0,  185,
  185,  185,  185,  185,    0,  185,  185,  185,    0,   47,
   47,   47,   47,   47,   47,  434,  513,   47,   47,   47,
  534,    0,    0,    0,    0,    0,    0,    0,    0,  545,
  548,  568,  577,  580,  583,  697,  687,  599,  447,  602,
  694,  698,  704,  708,  709,  714,  718,  482,  470,  719,
  700,  -25,  481,  510,  511,  520,  521,  530,  531,  724,
  734,  540,  487,  744,  541,  754,  764,  768,  769,  774,
  778,  779,  576,    0,  783,  784,    0,  788,    0,    0,
    0,    0,    0,    0,    0,  556,    0,  576,    0,  808,
  566,   47,  810,  605,   47,  792,  624,  575,  794,  796,
  581,    0,  798,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  859,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  130,    0,
    0,    0,    0,    0,  170,    0,    0,    0,    0,    0,
  223,    0,    0,    0,    0,    0,    0,   15,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  375,  397,    0,    0,
    0,    0,  310,  342,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -3,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  353,   -6,    0,  -13,  113,  191,  388,  340,  -93,    0,
  445, -117,  -41,  -39,    0,    0,  -18,    0,  336,    0,
    0,    0,    0,    0,  648,    0,  368,  380,    0,
};
final static int YYTABLESIZE=860;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
  160,   46,  192,   53,   55,   60,   83,   16,   89,   90,
   59,   45,   51,   94,   47,  105,  213,   58,   97,   64,
  109,  164,   73,    1,   46,   66,   77,   42,   46,  144,
   77,    2,    3,  395,   70,    3,   75,   46,   46,   77,
   78,  338,   61,   63,   67,  108,    4,  145,  281,    4,
   58,  156,  300,  101,   47,   89,   77,   44,  186,   77,
  188,  102,   44,    3,  193,  194,  196,  215,  301,  217,
   21,  219,  220,  221,  224,   22,  165,    4,  166,   23,
   15,   20,   48,  124,   25,   95,   26,  257,  125,   21,
   15,   81,    3,    3,   22,   72,  173,  174,   23,   15,
   18,   24,    3,   25,  256,   26,    4,    4,   16,  100,
   71,    3,    8,    8,    8,   15,    4,  149,   28,   43,
  222,  122,  345,  123,  259,    4,  150,    3,  103,   28,
   72,   28,    3,  240,   37,   47,    3,  121,   81,   72,
  223,    4,   38,   44,  260,  200,    4,  161,  241,  242,
    4,   21,   39,   81,  216,  162,   22,  122,  203,  123,
   23,  204,  205,  206,  207,   25,   72,   26,  292,   72,
  100,  100,  100,  127,  100,  195,  100,   44,   72,  229,
  111,  218,   72,   38,  225,   21,   48,   38,  100,  100,
   22,  100,    3,  226,   23,   16,   29,  228,  183,   25,
  230,   26,  115,  308,   46,  278,    4,   29,  184,   29,
   93,   46,   93,  309,   93,  159,  258,  191,  262,   52,
  264,  212,  266,  261,  268,   54,  271,  272,   93,   93,
  394,   93,   46,   46,   46,   46,   46,   50,   96,   77,
   46,   46,   77,    3,  283,  284,  285,  286,  287,   65,
   68,   41,  291,  293,  142,  280,  337,    4,   69,   62,
  118,  107,   89,   88,   77,   77,   77,   77,   77,   46,
   47,  248,   77,  135,   77,  136,  347,  346,   47,  212,
  351,  316,   88,   47,   88,   47,   47,   47,  120,   47,
    3,   47,   47,  335,   47,  270,  339,   77,  360,  361,
  362,  363,  364,  365,    4,  122,  368,  123,  370,  369,
   79,  231,   80,   49,    3,  119,  352,  353,  354,  355,
  356,   49,  357,  358,  359,  126,   49,  231,    4,  231,
   49,  231,  231,  231,   49,   49,  231,   49,   44,   11,
   11,   11,  135,  140,  136,   11,   21,  232,  233,   88,
   86,   22,  177,   17,   19,   23,   11,   79,   11,   80,
   25,    3,   26,  143,   93,    3,  178,  146,  231,   86,
  147,   86,   79,  251,   80,    4,    3,  234,  235,    4,
  424,   74,   87,  427,   46,  100,  100,  100,  100,  100,
    4,  100,  100,   30,  152,  100,  416,   77,  148,   46,
    3,   87,   77,   87,   30,  104,   30,  106,  252,  110,
    3,  421,  112,  154,    4,   91,  158,   91,  253,   91,
  236,  237,  238,  239,    4,   93,   93,   93,   93,   93,
  163,   93,   93,   91,   91,   93,   91,   92,   38,   92,
   56,   92,  112,  171,  112,  172,  112,  112,   21,  175,
  128,    3,  122,   22,  123,   92,   92,   23,   92,  254,
   57,  176,   25,  179,   26,    4,  180,  137,  181,  141,
  129,  130,  131,  132,  366,  133,  134,  182,   88,   88,
   88,   88,   88,    3,   88,   88,  135,  381,  136,  167,
  168,  255,   56,  135,  201,  136,  122,    4,  123,    3,
   21,  185,  198,  169,  170,   22,  135,  288,  136,   23,
   44,  187,   57,    4,   25,  189,   26,  190,   21,   48,
  208,  209,  210,   22,  198,  211,  198,   23,  198,  198,
  198,  198,   25,  202,   26,  122,  227,  123,  198,  129,
  130,  131,  132,  243,  244,  245,  246,  151,  249,  153,
  212,  155,  157,  367,  198,  122,  198,  123,  198,  198,
  198,  198,   44,  198,  282,   86,   86,   86,   86,   86,
   21,   86,   86,  250,  371,   22,  122,   67,  123,   23,
  263,  265,  267,   76,   25,  372,   26,  122,  373,  123,
  122,  269,  123,  273,   44,  198,  274,   87,   87,   87,
   87,   87,   21,   87,   87,  275,  289,   22,  374,  276,
  122,   23,  123,  277,  295,   87,   25,  375,   26,  122,
  376,  123,  122,  377,  123,  122,  296,  123,  279,  297,
   91,   91,   91,   91,   91,  298,   91,   91,  299,  380,
   91,  122,  382,  123,  122,  426,  123,  122,  302,  123,
  307,  303,   92,   92,   92,   92,   92,   98,   92,   92,
  304,  305,   92,  306,  429,   21,  122,  310,  123,  317,
   22,  318,  320,  322,   23,  323,  324,  325,   99,   25,
  326,   26,  330,  129,  130,  131,  132,  327,  133,  134,
  129,  130,  131,  132,   44,  133,  134,  328,  329,  331,
  332,  333,   21,  129,  130,  131,  132,   22,  133,  134,
  334,   23,   44,   44,  336,  116,   25,  340,   26,  341,
   21,   21,  342,  343,  344,   22,   22,  348,  349,   23,
   23,   44,  290,  117,   25,   25,   26,   26,  350,   21,
  378,   44,  390,  393,   22,  379,  391,  406,   23,   21,
  294,   44,  383,   25,   22,   26,  384,  396,   23,   21,
  311,   44,  385,   25,   22,   26,  386,  387,   23,   21,
  312,   44,  388,   25,   22,   26,  389,  392,   23,   21,
  313,   44,  403,   25,   22,   26,  397,  398,   23,   21,
  314,   44,  404,   25,   22,   26,  399,  400,   23,   21,
  315,   44,  407,   25,   22,   26,  401,  402,   23,   21,
  319,   44,  409,   25,   22,   26,  405,  408,   23,   21,
  321,   44,  410,   25,   22,   26,  411,  412,   23,   21,
  420,   44,  413,   25,   22,   26,  414,  415,   23,   21,
  423,  417,  418,   25,   22,   26,  419,  422,   23,  425,
  428,  430,  431,   25,  432,   26,  434,  433,    4,  247,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
   41,   20,   41,   40,   40,   24,   48,  274,   50,   51,
   24,   18,   40,   53,    0,   40,   59,   24,   41,  277,
  256,  115,   40,  256,   43,   40,   45,   40,   47,   41,
   49,  264,  268,   59,   40,  268,   43,   56,   57,   58,
   47,   41,   59,   40,   59,   40,  282,   59,   59,  282,
   57,  256,  256,  256,   40,   59,   75,    0,  152,   78,
  154,  264,  256,  268,  158,  159,  160,  185,  272,  187,
  264,  189,  190,  191,  192,  269,  118,  282,  120,  273,
  256,  256,  265,   42,  278,   41,  280,  281,   47,  264,
  256,   45,  268,  268,  269,   44,  138,  139,  273,  256,
  276,  276,  268,  278,  222,  280,  282,  282,  274,  264,
   59,  268,    0,    1,    2,  256,  282,  256,    6,  276,
  256,   43,   40,   45,  256,  282,  265,  268,   40,   17,
   44,   19,  268,  256,  256,  276,  268,   59,   45,   44,
  276,  282,  264,  256,  276,   59,  282,  256,  271,  272,
  282,  264,  274,   45,   59,  264,  269,   43,  172,   45,
  273,  175,  176,  177,  178,  278,   44,  280,  281,   44,
   41,   42,   43,   59,   45,  256,   47,  256,   44,  256,
  264,   59,   44,  264,   59,  264,  265,  264,   59,   60,
  269,   62,  268,   59,  273,  274,    6,   59,  256,  278,
  276,  280,   41,  256,  223,  247,  282,   17,  266,   19,
   41,  230,   43,  266,   45,  256,  223,  256,  232,  256,
  234,  264,  236,  230,  238,  261,  240,  241,   59,   60,
  256,   62,  251,  252,  253,  254,  255,  265,  261,  258,
  259,  260,  261,  268,  251,  252,  253,  254,  255,  264,
  256,  264,  259,  260,   41,  266,  256,  282,  264,  256,
   40,  256,  266,   41,  283,  284,  285,  286,  287,  288,
  256,  256,  291,   60,  293,   62,  318,  317,  264,  264,
  322,  288,   60,  269,   62,  271,  272,  273,   40,  275,
  268,  277,  278,  307,  280,   59,  310,  316,  340,  341,
  342,  343,  344,  345,  282,   43,  348,   45,  350,  349,
  264,  199,  266,  256,  268,  266,  330,  331,  332,  333,
  334,  264,  336,  337,  338,   59,  269,  215,  282,  217,
  273,  219,  220,  221,  277,  278,  224,  280,  256,    0,
    1,    2,   60,   41,   62,    6,  264,  271,  272,  256,
   41,  269,  256,    1,    2,  273,   17,  264,   19,  266,
  278,  268,  280,   59,  256,  268,  270,   59,  256,   60,
  265,   62,  264,  276,  266,  282,  268,  271,  272,  282,
  422,   42,   41,  425,  403,  256,  257,  258,  259,  260,
  282,  262,  263,    6,   41,  266,  403,  416,  265,  418,
  268,   60,  421,   62,   17,   66,   19,   68,  276,   70,
  268,  418,   73,   41,  282,   41,   41,   43,  276,   45,
  271,  272,  271,  272,  282,  256,  257,  258,  259,  260,
   41,  262,  263,   59,   60,  266,   62,   41,  264,   43,
  256,   45,  103,   59,  105,  270,  107,  108,  264,  270,
   41,  268,   43,  269,   45,   59,   60,  273,   62,  276,
  276,  270,  278,   59,  280,  282,  266,   41,  266,  256,
  257,  258,  259,  260,   41,  262,  263,  266,  256,  257,
  258,  259,  260,  268,  262,  263,   60,   41,   62,  122,
  123,  276,  256,   60,   41,   62,   43,  282,   45,  268,
  264,   41,  163,  124,  125,  269,   60,  276,   62,  273,
  256,   41,  276,  282,  278,   41,  280,   41,  264,  265,
   59,   59,   59,  269,  185,   59,  187,  273,  189,  190,
  191,  192,  278,   41,  280,   43,   59,   45,  199,  257,
  258,  259,  260,  208,  209,  210,  211,  103,  213,  105,
  264,  107,  108,   41,  215,   43,  217,   45,  219,  220,
  221,  222,  256,  224,  266,  256,  257,  258,  259,  260,
  264,  262,  263,   59,   41,  269,   43,   59,   45,  273,
   59,   59,   59,  277,  278,   41,  280,   43,   41,   45,
   43,   59,   45,   59,  256,  256,   59,  256,  257,  258,
  259,  260,  264,  262,  263,   59,   58,  269,   41,   59,
   43,  273,   45,   59,  272,  277,  278,   41,  280,   43,
   41,   45,   43,   41,   45,   43,  272,   45,   59,  272,
  256,  257,  258,  259,  260,  272,  262,  263,  272,   41,
  266,   43,   41,   45,   43,   41,   45,   43,  266,   45,
   41,  266,  256,  257,  258,  259,  260,  256,  262,  263,
  266,  266,  266,  266,   41,  264,   43,   41,   45,   40,
  269,   40,   58,   40,  273,   59,   59,   59,  277,  278,
   59,  280,   41,  257,  258,  259,  260,   59,  262,  263,
  257,  258,  259,  260,  256,  262,  263,   59,   59,   41,
   41,   41,  264,  257,  258,  259,  260,  269,  262,  263,
   41,  273,  256,  256,   41,  277,  278,   40,  280,   40,
  264,  264,   40,   40,   40,  269,  269,   40,   40,  273,
  273,  256,  275,  277,  278,  278,  280,  280,   40,  264,
   44,  256,  261,   44,  269,   59,  277,  261,  273,  264,
  275,  256,   59,  278,  269,  280,   59,  277,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,   59,  278,  269,  280,  277,  277,  273,  264,
  275,  256,   59,  278,  269,  280,  277,  277,  273,  264,
  275,  256,   59,  278,  269,  280,  277,  277,  273,  264,
  275,  256,   59,  278,  269,  280,  277,  277,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,   59,   59,  278,  269,  280,   59,   40,  273,   40,
   59,  277,   59,  278,   59,  280,   59,  277,    0,  212,
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
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN PRE ':' '(' CONDICION ')' ',' CADENA ';' CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' BEGIN PRE ':' '(' CONDICION ')' ',' CADENA ';' CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC : DECLARACION_FUNC_ERROR",
"DECLARACION_FUNC_ERROR : error FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO error IDENTIFICADOR '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC error '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR error PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' error ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO error DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' error DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC error CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' ';' END ';'",
"DECLARACION_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')' DECLARACIONES_VAR_FUNC BEGIN CONJUNTO_SENTENCIAS RETURN '(' EXPRESION ')' error ';'",
"DECLARACIONES_VAR_FUNC : DECLARACIONES_VAR_FUNC DECLARACION_VARIABLES",
"DECLARACIONES_VAR_FUNC : DECLARACION_VARIABLES",
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

//#line 200 "gramatica.y"

    private Lexico lexico = Lexico.getInstance();
    private List<Error> erroresSintacticos = new ArrayList<Error>(); 
    private List<Integer> tokensReconocidos = new ArrayList<Integer>(); 
    private List<String> estructurasReconocidas = new ArrayList<String>();

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
case 14:
//#line 39 "gramatica.y"
{addEstructura("Declaracion de varables");}
break;
case 16:
//#line 43 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 17:
//#line 44 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 18:
//#line 45 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 19:
//#line 46 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 20:
//#line 47 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 21:
//#line 48 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 22:
//#line 51 "gramatica.y"
{addEstructura("Declaracion de funcion");}
break;
case 23:
//#line 52 "gramatica.y"
{addEstructura("Declaracion de funcion sin declaración de variables ni PRE");}
break;
case 24:
//#line 53 "gramatica.y"
{addEstructura("Declaracion de función con PRE.");}
break;
case 25:
//#line 54 "gramatica.y"
{addEstructura("Declaracion de función con PRE y sin declaración de variables.");}
break;
case 27:
//#line 58 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 28:
//#line 59 "gramatica.y"
{yyerror("Falta la palabra clave FUNC.");}
break;
case 29:
//#line 60 "gramatica.y"
{yyerror("Falta el identificador de la funcion.");}
break;
case 30:
//#line 61 "gramatica.y"
{yyerror("Falta el primer parentesis de la funcion.");}
break;
case 31:
//#line 62 "gramatica.y"
{yyerror("Falta el parametro de la funcion.");}
break;
case 32:
//#line 63 "gramatica.y"
{yyerror("Falta el segundo parentesis de la funcion.");}
break;
case 33:
//#line 64 "gramatica.y"
{yyerror("Falta el return de la funcion.");}
break;
case 34:
//#line 65 "gramatica.y"
{yyerror("Falta el BEGIN de la funcion.");}
break;
case 35:
//#line 66 "gramatica.y"
{yyerror("Falta el END de la funcion.");}
break;
case 38:
//#line 73 "gramatica.y"
{addEstructura("Declaracion de varables");}
break;
case 40:
//#line 77 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 43:
//#line 84 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 46:
//#line 89 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 47:
//#line 90 "gramatica.y"
{yyerror("Falta el END del bloque de setnencia");}
break;
case 50:
//#line 97 "gramatica.y"
{addEstructura("Asignacion");}
break;
case 51:
//#line 98 "gramatica.y"
{addEstructura("Sentencia PRINT");}
break;
case 52:
//#line 99 "gramatica.y"
{addEstructura("BREAK");}
break;
case 53:
//#line 100 "gramatica.y"
{addEstructura("Sentencia IF/ELSE");}
break;
case 54:
//#line 101 "gramatica.y"
{addEstructura("Sentencia IF");}
break;
case 55:
//#line 102 "gramatica.y"
{addEstructura("Sentencia REPEAT");}
break;
case 56:
//#line 103 "gramatica.y"
{addEstructura("Llamado a funcion");}
break;
case 61:
//#line 110 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 62:
//#line 111 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 63:
//#line 114 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 64:
//#line 115 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 65:
//#line 116 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 66:
//#line 119 "gramatica.y"
{yyerror("Falta el primer paréntesis de la condición del IF.");}
break;
case 67:
//#line 120 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 68:
//#line 121 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 69:
//#line 122 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 70:
//#line 123 "gramatica.y"
{yyerror("Falta el ELSE del IF.");}
break;
case 71:
//#line 124 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 72:
//#line 125 "gramatica.y"
{yyerror("Falta el primer paréntesiis de la condición del IF.");}
break;
case 73:
//#line 126 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 74:
//#line 127 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 75:
//#line 128 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 76:
//#line 129 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 77:
//#line 132 "gramatica.y"
{yyerror("Falta el identificador del REPEAT.");}
break;
case 78:
//#line 133 "gramatica.y"
{yyerror("Falta el asignador al identificador del REPEAT.");}
break;
case 79:
//#line 134 "gramatica.y"
{yyerror("El identificador no tiene constante a asignar del REPEAT.");}
break;
case 80:
//#line 135 "gramatica.y"
{yyerror("Falta ';' luego de la asignacion del REPEAT.");}
break;
case 81:
//#line 136 "gramatica.y"
{yyerror("Falta la condicion del ciclo del REPEAT.");}
break;
case 82:
//#line 137 "gramatica.y"
{yyerror("Falta ';' luego de la condicion del REPEAT.");}
break;
case 83:
//#line 138 "gramatica.y"
{yyerror("Falta la constante de iteracion del REPEAT.");}
break;
case 84:
//#line 139 "gramatica.y"
{yyerror("Falta el primer paréntesis del REPEAT.");}
break;
case 85:
//#line 140 "gramatica.y"
{yyerror("Falta el segundo paréntesis del REPEAT.");}
break;
case 99:
//#line 168 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 101:
//#line 172 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                        yyerror("Constante fuera de rango");
                                            }}
break;
case 102:
//#line 175 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 104:
//#line 179 "gramatica.y"
{addEstructura("Llamado a funcion como operando");}
break;
//#line 1097 "Parser.java"
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
