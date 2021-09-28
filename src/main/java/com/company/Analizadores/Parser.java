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
   10,    6,    6,    6,    6,    6,   14,   14,   14,   14,
   14,   14,   14,   14,   14,    5,    5,   15,    9,    9,
    4,    4,    4,   17,   17,    2,    2,   16,   16,   16,
   16,   16,   16,   16,   16,   16,   16,   16,   19,   19,
   20,   20,   20,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   21,   21,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   13,   13,   13,   18,   25,   12,   12,
   12,   26,   26,   26,   11,   11,   28,   27,   27,   27,
   27,   27,   24,   24,   24,   24,   24,   24,   23,   23,
    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    1,    1,    3,    3,    5,    2,    2,    2,
    1,    1,    1,    7,    1,    7,    7,    7,    7,    7,
    7,   16,   15,   24,   23,    1,   16,   16,   16,   16,
   16,   16,   16,   16,   15,    3,    1,    3,    3,    1,
    3,    1,    1,    3,    3,    2,    1,    4,    5,    2,
   10,    8,   11,    5,    1,    1,    1,    1,    4,    4,
    4,    4,    4,   10,   10,   10,   10,   10,   10,    8,
    8,    8,    8,    8,   11,   11,   11,   10,   11,   10,
   11,   11,   11,    3,    3,    1,    3,    4,    3,    3,
    1,    3,    3,    1,    2,    1,    2,    1,    1,    2,
    1,    4,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,  112,  111,    0,    0,    3,   11,   12,   13,
    0,   15,   26,   37,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    8,    9,   10,
   42,   43,   55,   56,   57,   58,    0,   40,    0,    0,
    0,    0,    0,    0,    0,   47,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    6,    0,
   50,    0,    0,    5,    0,    0,   38,    0,    0,    0,
   36,    0,    0,    0,    0,    2,   46,    0,    0,   99,
    0,    0,    0,  101,    0,   94,   44,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   41,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,    0,    0,   96,    0,    7,    1,    0,  100,    0,
   59,    0,    0,    0,    0,   60,   48,    0,  105,  106,
  107,  108,  109,  110,  104,  103,    0,    0,    0,    0,
    0,    0,   61,    0,   63,   62,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   97,   95,    0,    0,    0,    0,    0,    0,   92,   93,
   54,    0,    0,    0,    0,    0,    0,    0,   49,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   16,  102,
   88,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   17,    0,   18,    0,    0,    0,
    0,    0,    0,   19,   20,   21,   14,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   70,    0,   71,    0,   72,    0,   73,   74,    0,    0,
   52,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   64,   65,   66,   67,   68,   69,   51,    0,    0,    0,
    0,    0,   80,    0,    0,    0,   78,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   82,
   75,   76,   77,   79,   81,   83,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   23,    0,    0,   35,    0,   27,   28,   29,   30,
   31,   32,   33,    0,   34,    0,   22,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   25,
    0,   24,
};
final static short yydgoto[] = {                          5,
    6,   49,    7,   27,    8,    9,   10,   82,   40,   12,
  113,   91,   92,   13,   14,   31,   32,  213,   33,   34,
   35,   36,  138,  139,   84,   85,   86,  114,
};
final static short yysindex[] = {                      -189,
 -202, -180,    0,    0,    0, -195,    0,    0,    0,    0,
 -222,    0,    0,    0, -221,  -27, -179,  548, -162,  -90,
  -37,  -36,  -31,  158,   12,    3, -186,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -35,    0,   -7,  -23,
  108, -141,  548,  -88,  177,    0,  548,  -10,  227,   88,
  -10,  -10,   94,  139,  -19,  233,  548,  395,    0,    0,
    0,  -64, -241,    0,  172, -141,    0,  -32,    5, -158,
    0,  -50, -141,  183,  423,    0,    0,  433,  187,    0,
    9,  213,  -15,    0,  171,    0,    0,  232,   87,  242,
  102,  356,  237,  219,  235,   -9,  240,  -88,    0,   46,
   48,  -80, -141,  279, -141,  286, -141, -157,  310,    6,
    0,  -83,  316,    0,  100,    0,    0,  -10,    0,  -10,
    0,  -10,  -10,  -10,  -10,    0,    0,  315,    0,    0,
    0,    0,    0,    0,    0,    0,  120,  -10,  -10,  121,
  129,  -78,    0,  320,    0,    0,  127,  134,  141, -174,
  376,  100,  378,  100,  379,  382,   14,  100,  100,  -63,
    0,    0, -141,   75,  285,  418,  171,  171,    0,    0,
    0,  158,  102,  102,  158,  158,  158,  158,    0,  365,
  366,  384,  385,  -49, -141,   78, -141,   79, -141, -141,
 -141, -161,  111,  113,  389,  123,  159,  -62,    0,    0,
    0, -159, -143,   31,   64, -155,  188,  188,  188,  188,
  228,  -61,  392,  184,    0,  196,    0,  197,  198,  204,
 -141, -138, -230,    0,    0,    0,    0,  548,  397,  158,
  405,  158,  425,  158,  431,  158,  442,  189,  158,  444,
  449,  450,  462,  464,  -10,  465,  -11,  226,  548,  548,
  548,  548,  548,  254,  475,  396,  548, -120,  443,  262,
    0,  276,    0,  282,    0,  292,    0,    0,  299, -207,
    0,  291,  313,  319,  321,  102,  327,  532, -166,  541,
  453,  463,  473,  483,  498,  548,  546,  557,  508,  545,
  518,  559,  549,  558,  572,  575,  576,  589,  595,  617,
  620,  621,  622,  625,  158,  626,   18,  158,  630,  637,
  641,  646,  648,   69,  -10,  -10,  650,  651,  653,  -10,
    0,    0,    0,    0,    0,    0,    0,  158,  158,  158,
  158,  158,    0,  158,  158,  158,    0,  -10,  -10,  -10,
  -10,  -10,  -10,  380,  477,  -10,  -10,  -10,  495,    0,
    0,    0,    0,    0,    0,    0,    0,  496,  501,  502,
  517,  524,  527,  654,  635,  561,  387,  579,  636,  645,
  649,  656,  661,  665,  666,  469,  428,  675,  691,  -58,
  467,  468,  472,  478,  480,  482,  488,  681,  701,  489,
  507,  710,  503,  711,  716,  720,  726,  730,  731,  736,
  548,    0,  740,  741,    0,  746,    0,    0,    0,    0,
    0,    0,    0,  528,    0,  548,    0,  769,  538,  -10,
  770,  580,  -10,  755,  612,  542,  756,  761,  547,    0,
  763,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  750,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   20,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  109,    0,
    0,    0,    0,    0,  146,    0,    0,    0,    0,    0,
  269,    0,    0,    0,    0,    0,    0,   68,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  318,  370,    0,    0,
    0,    0,  293,  332,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   11,    0,    0,    0,    0,
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
    0,    0,
};
final static short yygindex[] = {                         0,
  383,   -6,    0,  -13,  420,  180,  190,  493,  -96,    0,
  363,  -34,  -46,    0,    0,  -18,    0,  307,    0,    0,
    0,    0,    0,  614,    0,  259,  263,    0,
};
final static int YYTABLESIZE=828;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         46,
  393,   46,   51,   53,   66,   60,   94,  105,   55,  212,
   59,   45,   42,   83,  101,   89,   90,   58,  164,   42,
   72,   97,  102,   67,   46,  257,   77,  122,   46,  123,
   77,  144,   70,   37,   81,   71,   75,   46,   46,   77,
   78,   38,   63,  121,  108,  258,  160,  279,  298,  145,
   58,   39,   16,   15,  192,  186,   77,  188,  336,   77,
   20,  193,  194,  196,  299,    3,    1,   45,   21,   87,
   61,   16,    3,   22,    2,   15,   15,   23,    3,    4,
   24,  183,   25,  165,   26,  166,    4,    3,    3,  306,
   64,  184,    4,   15,  221,   18,   43,  109,  156,  307,
  238,    4,    4,  173,  174,    3,    3,   45,  343,    3,
    3,  230,  231,   47,  222,  239,  240,   44,   72,    4,
    4,   72,   72,    4,    4,   21,    3,  232,  233,  122,
   22,  123,   81,  199,   23,   44,  215,  217,   81,   25,
    4,   26,  255,   21,  122,  127,  123,   73,   22,   98,
   98,   98,   23,   98,   72,   98,   72,   25,  202,   26,
  290,  203,  204,  205,  206,   44,   72,   98,   98,  224,
   98,  225,  161,   21,   48,  149,   48,  177,   22,   95,
  162,  227,   23,   16,  150,   29,   91,   25,   91,   26,
   91,  178,  195,  229,  246,   30,   29,  392,   29,  100,
   38,   38,  211,   46,   91,   91,   30,   91,   30,   46,
  276,  103,  124,  111,  211,  256,  260,  125,  262,   52,
  264,  259,  266,  115,  269,  270,  118,   50,   65,   54,
   46,   46,   46,   46,   46,    3,   41,   77,   46,   46,
   77,   96,  281,  282,  283,  284,  285,  268,   68,    4,
  289,  291,  120,   79,  278,   80,   69,    3,   62,  142,
  107,  159,   77,   77,   77,   77,   77,   46,  344,  191,
   77,    4,   77,  335,  119,   47,   87,  140,  135,  314,
  136,  345,  128,   47,  122,  349,  123,  135,   47,  136,
  126,  333,   47,  143,  337,   77,   47,   47,  146,   47,
  367,  234,  235,  358,  359,  360,  361,  362,  363,   86,
  147,  366,  148,  368,  350,  351,  352,  353,  354,  152,
  355,  356,  357,   45,   44,  200,  154,  122,   86,  123,
   86,   45,   21,   84,  236,  237,   45,   22,   45,   45,
   45,   23,   45,   88,   45,   45,   25,   45,   26,   93,
  158,   79,   84,   80,   84,    3,  163,   79,   89,   80,
   89,    3,   89,   38,   98,   98,   98,   98,   98,    4,
   98,   98,   85,  171,   98,    4,   89,   89,  179,   89,
  167,  168,   46,   17,   19,  422,  169,  170,  425,  172,
  175,   85,  180,   85,  414,   77,  137,   46,  176,  181,
   77,   91,   91,   91,   91,   91,  182,   91,   91,  419,
   90,   91,   90,   56,   90,  135,  185,  136,  187,  189,
  364,   21,  190,  207,  208,   28,   22,  379,   90,   90,
   23,   90,   44,   57,  228,   25,   28,   26,   28,  135,
   21,  136,  209,  210,   56,   22,  135,  226,  136,   23,
  248,  211,   21,   76,   25,   67,   26,   22,  201,  249,
  122,   23,  123,  261,   57,  151,   25,  153,   26,  155,
  157,  250,  251,  252,  141,  129,  130,  131,  132,  253,
  133,  134,   44,  263,  129,  130,  131,  132,   44,  265,
   21,  280,   11,   11,   11,   22,   21,   48,   11,   23,
  267,   22,  271,   87,   25,   23,   26,  272,  273,   11,
   25,   11,   26,  241,  242,  243,  244,  365,  247,  122,
  274,  123,  275,  277,   86,   86,   86,   86,   86,  286,
   86,   86,  287,  293,   74,  369,  370,  122,  122,  123,
  123,  371,  372,  122,  122,  123,  123,  294,   84,   84,
   84,   84,   84,  295,   84,   84,  300,  373,  104,  122,
  106,  123,  110,  296,  374,  112,  122,  375,  123,  122,
  297,  123,  305,   89,   89,   89,   89,   89,  301,   89,
   89,  308,  197,   89,  302,  315,  303,   85,   85,   85,
   85,   85,  304,   85,   85,  112,  316,  112,  320,  112,
  112,  378,  318,  122,  214,  123,  216,  321,  218,  219,
  220,  223,  129,  130,  131,  132,  322,  133,  134,  380,
  424,  122,  122,  123,  123,   90,   90,   90,   90,   90,
  323,   90,   90,  324,  325,   90,  129,  130,  131,  132,
  254,  133,  134,  129,  130,  131,  132,  326,  133,  134,
   98,   44,  427,  327,  122,  198,  123,  328,   21,   21,
  329,  330,  331,   22,   22,  332,  334,   23,   23,  338,
  288,   99,   25,   25,   26,   26,  339,  198,   44,  198,
  340,  198,  198,  198,  198,  341,   21,  342,   44,  346,
  347,   22,  348,  377,  381,   23,   21,  376,   44,  116,
   25,   22,   26,  382,  389,   23,   21,  383,   44,  117,
   25,   22,   26,  198,  384,   23,   21,  292,   44,  385,
   25,   22,   26,  386,  387,   23,   21,  309,   44,  388,
   25,   22,   26,  390,  391,   23,   21,  310,   44,  401,
   25,   22,   26,  394,  395,   23,   21,  311,  396,    4,
   25,   22,   26,   44,  397,   23,  398,  312,  399,  402,
   25,   21,   26,   44,  400,  403,   22,  404,  405,  407,
   23,   21,  313,   44,  408,   25,   22,   26,  409,  406,
   23,   21,  317,   44,  410,   25,   22,   26,  411,  412,
   23,   21,  319,   44,  413,   25,   22,   26,  415,  416,
   23,   21,  418,   44,  417,   25,   22,   26,  420,  423,
   23,   21,  421,  426,  429,   25,   22,   26,  428,  430,
   23,  432,    0,  431,  245,   25,    0,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
   59,   20,   40,   40,   40,   24,   53,   40,   40,   59,
   24,   18,   40,   48,  256,   50,   51,   24,  115,    0,
   44,   41,  264,   59,   43,  256,   45,   43,   47,   45,
   49,   41,   40,  256,   45,   59,   43,   56,   57,   58,
   47,  264,   40,   59,   40,  276,   41,   59,  256,   59,
   57,  274,  274,  256,   41,  152,   75,  154,   41,   78,
  256,  158,  159,  160,  272,  268,  256,    0,  264,   59,
   59,  274,  268,  269,  264,  256,  256,  273,  268,  282,
  276,  256,  278,  118,  280,  120,  282,  268,  268,  256,
  277,  266,  282,  256,  256,  276,  276,  256,  256,  266,
  256,  282,  282,  138,  139,  268,  268,   40,   40,  268,
  268,  271,  272,  276,  276,  271,  272,  256,   44,  282,
  282,   44,   44,  282,  282,  264,  268,  271,  272,   43,
  269,   45,   45,   59,  273,  256,   59,   59,   45,  278,
  282,  280,  281,  264,   43,   59,   45,   40,  269,   41,
   42,   43,  273,   45,   44,   47,   44,  278,  172,  280,
  281,  175,  176,  177,  178,  256,   44,   59,   60,   59,
   62,   59,  256,  264,  265,  256,  265,  256,  269,   41,
  264,   59,  273,  274,  265,    6,   41,  278,   43,  280,
   45,  270,  256,  256,  256,    6,   17,  256,   19,  264,
  264,  264,  264,  222,   59,   60,   17,   62,   19,  228,
  245,   40,   42,  264,  264,  222,  230,   47,  232,  256,
  234,  228,  236,   41,  238,  239,   40,  265,  264,  261,
  249,  250,  251,  252,  253,  268,  264,  256,  257,  258,
  259,  261,  249,  250,  251,  252,  253,   59,  256,  282,
  257,  258,   40,  264,  266,  266,  264,  268,  256,   41,
  256,  256,  281,  282,  283,  284,  285,  286,  315,  256,
  289,  282,  291,  256,  266,  256,  266,   41,   60,  286,
   62,  316,   41,  264,   43,  320,   45,   60,  269,   62,
   59,  305,  273,   59,  308,  314,  277,  278,   59,  280,
  347,  271,  272,  338,  339,  340,  341,  342,  343,   41,
  265,  346,  265,  348,  328,  329,  330,  331,  332,   41,
  334,  335,  336,  256,  256,   41,   41,   43,   60,   45,
   62,  264,  264,   41,  271,  272,  269,  269,  271,  272,
  273,  273,  275,  256,  277,  278,  278,  280,  280,  256,
   41,  264,   60,  266,   62,  268,   41,  264,   41,  266,
   43,  268,   45,  264,  256,  257,  258,  259,  260,  282,
  262,  263,   41,   59,  266,  282,   59,   60,   59,   62,
  122,  123,  401,    1,    2,  420,  124,  125,  423,  270,
  270,   60,  266,   62,  401,  414,   41,  416,  270,  266,
  419,  256,  257,  258,  259,  260,  266,  262,  263,  416,
   41,  266,   43,  256,   45,   60,   41,   62,   41,   41,
   41,  264,   41,   59,   59,    6,  269,   41,   59,   60,
  273,   62,  256,  276,  276,  278,   17,  280,   19,   60,
  264,   62,   59,   59,  256,  269,   60,   59,   62,  273,
   59,  264,  264,  277,  278,   59,  280,  269,   41,  276,
   43,  273,   45,   59,  276,  103,  278,  105,  280,  107,
  108,  276,  276,  276,  256,  257,  258,  259,  260,  276,
  262,  263,  256,   59,  257,  258,  259,  260,  256,   59,
  264,  266,    0,    1,    2,  269,  264,  265,    6,  273,
   59,  269,   59,  277,  278,  273,  280,   59,   59,   17,
  278,   19,  280,  207,  208,  209,  210,   41,  212,   43,
   59,   45,   59,   59,  256,  257,  258,  259,  260,  276,
  262,  263,   58,  272,   42,   41,   41,   43,   43,   45,
   45,   41,   41,   43,   43,   45,   45,  272,  256,  257,
  258,  259,  260,  272,  262,  263,  266,   41,   66,   43,
   68,   45,   70,  272,   41,   73,   43,   41,   45,   43,
  272,   45,   41,  256,  257,  258,  259,  260,  266,  262,
  263,   41,  163,  266,  266,   40,  266,  256,  257,  258,
  259,  260,  266,  262,  263,  103,   40,  105,   40,  107,
  108,   41,   58,   43,  185,   45,  187,   59,  189,  190,
  191,  192,  257,  258,  259,  260,   59,  262,  263,   41,
   41,   43,   43,   45,   45,  256,  257,  258,  259,  260,
   59,  262,  263,   59,   59,  266,  257,  258,  259,  260,
  221,  262,  263,  257,  258,  259,  260,   59,  262,  263,
  256,  256,   41,   59,   43,  163,   45,   41,  264,  264,
   41,   41,   41,  269,  269,   41,   41,  273,  273,   40,
  275,  277,  278,  278,  280,  280,   40,  185,  256,  187,
   40,  189,  190,  191,  192,   40,  264,   40,  256,   40,
   40,  269,   40,   59,   59,  273,  264,   44,  256,  277,
  278,  269,  280,   59,  277,  273,  264,   59,  256,  277,
  278,  269,  280,  221,   59,  273,  264,  275,  256,   59,
  278,  269,  280,   59,   59,  273,  264,  275,  256,  261,
  278,  269,  280,   59,   44,  273,  264,  275,  256,   59,
  278,  269,  280,  277,  277,  273,  264,  275,  277,    0,
  278,  269,  280,  256,  277,  273,  277,  275,  277,   59,
  278,  264,  280,  256,  277,  277,  269,  261,   59,   59,
  273,  264,  275,  256,   59,  278,  269,  280,   59,  277,
  273,  264,  275,  256,   59,  278,  269,  280,   59,   59,
  273,  264,  275,  256,   59,  278,  269,  280,   59,   59,
  273,  264,  275,  256,   59,  278,  269,  280,   40,   40,
  273,  264,  275,   59,   59,  278,  269,  280,  277,   59,
  273,   59,   -1,  277,  211,  278,   -1,  280,
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

//#line 196 "gramatica.y"

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
//#line 673 "Parser.java"
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
{printEstructura("Declaracion de varables");}
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
{printEstructura("Declaracion de funcion");}
break;
case 23:
//#line 52 "gramatica.y"
{printEstructura("Declaracion de funcion sin declaración de variables ni PRE.");}
break;
case 24:
//#line 53 "gramatica.y"
{printEstructura("Declaracion de función con PRE.");}
break;
case 25:
//#line 54 "gramatica.y"
{printEstructura("Declaracion de función con PRE y sin declaración de variables.");}
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
case 36:
//#line 69 "gramatica.y"
{printEstructura("Declaracion de varables");}
break;
case 38:
//#line 73 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 41:
//#line 80 "gramatica.y"
{printEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 44:
//#line 85 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 45:
//#line 86 "gramatica.y"
{yyerror("Falta el END del bloque de setnencia");}
break;
case 48:
//#line 93 "gramatica.y"
{printEstructura("Asignacion");}
break;
case 49:
//#line 94 "gramatica.y"
{printEstructura("PRINT");}
break;
case 50:
//#line 95 "gramatica.y"
{printEstructura("BREAK");}
break;
case 51:
//#line 96 "gramatica.y"
{printEstructura("Sentencia IF/ELSE");}
break;
case 52:
//#line 97 "gramatica.y"
{printEstructura("Sentencia IF");}
break;
case 53:
//#line 98 "gramatica.y"
{printEstructura("Sentencia REPEAT");}
break;
case 54:
//#line 99 "gramatica.y"
{printEstructura("Llamado a funcion");}
break;
case 59:
//#line 106 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 60:
//#line 107 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 61:
//#line 110 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 62:
//#line 111 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 63:
//#line 112 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 64:
//#line 115 "gramatica.y"
{yyerror("Falta el primer paréntesis de la condición del IF.");}
break;
case 65:
//#line 116 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 66:
//#line 117 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 67:
//#line 118 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 68:
//#line 119 "gramatica.y"
{yyerror("Falta el ELSE del IF.");}
break;
case 69:
//#line 120 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 70:
//#line 121 "gramatica.y"
{yyerror("Falta el primer paréntesiis de la condición del IF.");}
break;
case 71:
//#line 122 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 72:
//#line 123 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 73:
//#line 124 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 74:
//#line 125 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 75:
//#line 128 "gramatica.y"
{yyerror("Falta el identificador del REPEAT.");}
break;
case 76:
//#line 129 "gramatica.y"
{yyerror("Falta el asignador al identificador del REPEAT.");}
break;
case 77:
//#line 130 "gramatica.y"
{yyerror("El identificador no tiene constante a asignar del REPEAT.");}
break;
case 78:
//#line 131 "gramatica.y"
{yyerror("Falta ';' luego de la asignacion del REPEAT.");}
break;
case 79:
//#line 132 "gramatica.y"
{yyerror("Falta la condicion del ciclo del REPEAT.");}
break;
case 80:
//#line 133 "gramatica.y"
{yyerror("Falta ';' luego de la condicion del REPEAT.");}
break;
case 81:
//#line 134 "gramatica.y"
{yyerror("Falta la constante de iteracion del REPEAT.");}
break;
case 82:
//#line 135 "gramatica.y"
{yyerror("Falta el primer paréntesis del REPEAT.");}
break;
case 83:
//#line 136 "gramatica.y"
{yyerror("Falta el segundo paréntesis del REPEAT.");}
break;
case 97:
//#line 164 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 99:
//#line 168 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                        yyerror("Constante fuera de rango");
                                            }}
break;
case 100:
//#line 171 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 102:
//#line 175 "gramatica.y"
{printEstructura("Llamado a funcion como operando");}
break;
//#line 1086 "Parser.java"
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
