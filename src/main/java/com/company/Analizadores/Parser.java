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
    8,   11,    4,    1,    1,    1,    1,    4,    4,    4,
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
   59,   47,   53,  104,  105,  106,  107,  108,  109,  103,
  102,    0,    0,    0,    0,    0,    0,   60,    0,   62,
   61,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   96,   94,    0,    0,    0,
    0,    0,    0,   91,   92,    0,    0,    0,    0,    0,
    0,    0,   48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   15,  101,   87,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   16,    0,
   17,    0,    0,    0,    0,    0,    0,   18,   19,   20,
   13,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   69,    0,   70,    0,   71,    0,
   72,   73,    0,    0,   51,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,   64,   65,   66,   67,   68,
   50,    0,    0,    0,    0,    0,   79,    0,    0,    0,
   77,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   81,   74,   75,   76,   78,   80,   82,
   52,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   22,    0,    0,   34,    0,
   26,   27,   28,   29,   30,   31,   32,    0,   33,    0,
   21,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   24,    0,   23,
};
final static short yydgoto[] = {                          5,
    6,   51,    7,   27,    8,    9,   10,   86,   40,   12,
  118,   95,   96,   13,   14,   31,   32,  217,   33,   34,
   35,   36,  143,  144,   88,   89,   90,  119,
};
final static short yysindex[] = {                      -186,
 -175, -217,    0,    0,    0, -193,    0,    0,    0,    0,
 -219,    0,    0,    0, -182,  -31, -168,  566, -166,  -75,
  -37,  -33,  -21,  233,    3,  -22, -154,    0,    0, -218,
    0,    0,    0,    0,    0,    0,  -35,    0,  -15,   59,
  109, -215, -112,  566, -170,  256,    0,  566,   12,  -96,
  262,   46,   12,   12,   94,  136,  -14,  328,  566,  274,
    0,    0,    0,  -73,  -68,    0,  -48,  -55,  160, -215,
    0,  -36,   -8, -234,    0,  -58, -215,  170,  436,    0,
    0,  446,  173,    0,   -4,  177,   98,    0,  155,    0,
    0,  199,  102,  326,  105,  377,  223,  222,  214,  -10,
  239, -170,    0,   27,   36, -139,  269, -215,  280, -215,
  293, -215, -162,  298,  -26,    0,  -52,  304,    0,   51,
    0,    0,   12,    0,   12,    0,   12,   12,   12,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   93,   12,   12,  116,  131, -158,    0,  292,    0,
    0,  118,  137,  148, -212,  327,   51,  380,   51,  382,
  389,   -6,   51,   51,  -49,    0,    0, -215,   81,  332,
  367,  155,  155,    0,    0,  233,  105,  105,  233,  233,
  233,  233,    0,  373,  374,  376,  384,  -43, -215,  100,
 -215,  108, -215, -215, -215, -155,  111,  112,  388,  114,
  179,   31,    0,    0,    0, -251,   55,   65,   83, -141,
  185,  185,  185,  185,   72,   35,  398,  187,    0,  189,
    0,  197,  211,  217, -215, -145, -179,    0,    0,    0,
    0,  566,  416,  233,  439,  233,  440,  233,  442,  233,
  445,  227,  233,  451,  455,  457,  458,  463,   12,  464,
  -47,  229,  566,  566,  566,  566,  566,  248,  469,  456,
  566, -127,  466,  243,    0,  265,    0,  273,    0,  277,
    0,    0,  278, -134,    0,  275,  290,  301,  307,  105,
  315,  487,  -87,  544,  476,  486,  496,  506,  516,  566,
  547,  551,  526,  537,  536,  574,  540,  561,  568,  571,
  572,  579,  582,  577,  592,  606,  609,  610,  233,  612,
   -5,  233,  614,  620,  623,  624,  633,   10,   12,   12,
  634,  635,  636,   12,    0,    0,    0,    0,    0,    0,
    0,  233,  233,  233,  233,  233,    0,  233,  233,  233,
    0,   12,   12,   12,   12,   12,   12,  409,  379,   12,
   12,   12,  419,    0,    0,    0,    0,    0,    0,    0,
    0,  425,  449,  503,  520,  521,  545,  637,  618,  553,
  426,  559,  619,  621,  628,  631,  640,  642,  644,  418,
  405,  645,  650,    1,  429,  430,  431,  434,  441,  444,
  460,  658,  669,  461,  472,  684,  470,  689,  694,  698,
  699,  704,  708,  709,  566,    0,  714,  718,    0,  719,
    0,    0,    0,    0,    0,    0,    0,  546,    0,  566,
    0,  743,  556,   12,  747,  562,   12,  729,  576,  530,
  734,  738,  531,    0,  739,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  803,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   79,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  133,    0,    0,    0,    0,    0,  366,    0,
    0,    0,    0,    0,  312,    0,    0,    0,    0,    0,
    0,   69,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  386,  399,    0,    0,    0,  320,  353,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   -7,
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
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  396,   -1,    0,  -16,  502,  166,    0,  364,  -91,    0,
   74,  -39,  -54,    0,    0,  -18,    0,  240,    0,    0,
    0,    0,    0,  598,    0,  251,  287,    0,
};
final static int YYTABLESIZE=846;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   98,   47,   53,  110,   70,   62,   55,   61,   42,   87,
   71,  283,   93,   94,  165,  216,   46,   65,   57,  234,
  235,  114,   60,   71,   74,   47,  101,   81,  169,   47,
  149,  113,   81,    3,  196,  340,   37,   67,   15,   47,
   47,   81,   79,  187,   38,   38,   82,    4,  150,  347,
    3,   86,    3,  188,   39,   68,   85,   60,   18,  397,
   81,   63,   20,   81,    4,  190,    4,  192,   44,    1,
   21,  197,  198,  200,    3,   22,  261,    2,   41,   23,
   15,    3,   24,  170,   25,  171,   26,   43,    4,   43,
   85,   16,    3,  161,   49,    4,  262,  181,   16,    3,
  225,    3,   76,  177,  178,    3,    4,   44,   44,   48,
   45,  182,    3,    4,  242,    4,  154,   75,   21,    4,
  226,  302,   66,   22,   76,  155,    4,   23,   45,  243,
  244,  140,   25,  141,   26,  259,   21,  303,   85,  203,
  127,   22,  128,   76,  127,   23,  128,  127,   77,  128,
   25,   76,   26,  294,   76,   76,  126,   76,  219,  206,
  132,   50,  207,  208,  209,  210,  221,   41,  310,  228,
  229,   29,  231,   97,   97,   97,   99,   97,  311,   97,
   45,  156,   29,  158,   29,  160,  162,  105,   21,   49,
  104,   97,   97,   22,   97,  106,  129,   23,   50,  108,
  107,  130,   25,  166,   26,  116,  199,   47,   73,  280,
  120,  167,  123,   47,   38,   69,  125,  264,  282,  266,
  215,  268,   54,  270,  260,  273,  274,   52,   69,  164,
  263,    3,   41,   64,   47,   47,   47,   47,   47,   56,
   72,   81,   47,   47,   81,    4,  100,  112,   73,  195,
  339,  285,  286,  287,  288,  289,  396,  131,   86,  293,
  295,  124,  147,  145,  348,   45,   81,   81,   81,   81,
   81,   47,  148,   21,   81,   83,   81,   84,   22,    3,
  349,  140,   23,  141,  353,  272,  233,   25,  318,   26,
  250,  152,  337,    4,   38,  341,  371,  151,  215,   81,
  153,   92,  362,  363,  364,  365,  366,  367,  110,   83,
  370,   84,  372,    3,   38,  354,  355,  356,  357,  358,
  157,  359,  360,  361,   44,  236,  237,    4,  134,  135,
  136,  137,   44,  159,   46,  238,  239,   44,  163,   44,
   44,   44,   46,   44,  168,   44,   44,   46,   44,   97,
  183,   46,   85,  240,  241,   46,   46,   83,   46,   84,
   83,    3,  176,   11,   11,   11,  133,  189,  127,   30,
  128,   85,  204,   85,  127,    4,  128,  172,  173,   83,
   30,   83,   30,  184,  426,  179,   47,  429,   97,   97,
   97,   97,   97,   84,   97,   97,   17,   19,   97,   81,
  180,   47,  185,  418,   81,   78,   90,  205,   90,  127,
   90,  128,   84,  186,   84,  174,  175,  142,  423,  369,
  191,  127,  193,  128,   90,   90,   88,   90,   88,  194,
   88,  211,  212,  109,  213,  111,  140,  115,  141,   89,
  117,   89,  214,   89,   88,   88,  230,   88,  215,  368,
  245,  246,  247,  248,  232,  251,  252,   89,   89,  373,
   89,  127,  253,  128,  254,  374,  383,  127,  140,  128,
  141,  117,  255,  117,   71,  117,  117,  146,  134,  135,
  136,  137,   58,  138,  139,  140,  256,  141,   58,  375,
   21,  127,  257,  128,  284,   22,   21,  265,  267,   23,
  269,   22,   59,  271,   25,   23,   26,   28,   59,  275,
   25,   45,   26,  276,  297,  277,  278,   45,   28,   21,
   28,  279,  281,  290,   22,   21,  291,  309,   23,  102,
   22,  202,   80,   25,   23,   26,  298,   21,   91,   25,
  304,   26,   22,  376,  299,  127,   23,  128,  300,  301,
  103,   25,  202,   26,  202,  305,  202,  202,  202,  202,
  377,  378,  127,  127,  128,  128,  306,   85,   85,   85,
   85,   85,  307,   85,   85,   83,   83,   83,   83,   83,
  308,   83,   83,   45,  312,  379,  319,  127,  202,  128,
  320,   21,   49,  382,  322,  127,   22,  128,  325,  384,
   23,  127,  428,  128,  127,   25,  128,   26,   84,   84,
   84,   84,   84,  324,   84,   84,  431,  332,  127,  326,
  128,   90,   90,   90,   90,   90,  327,   90,   90,  328,
  329,   90,  333,  134,  135,  136,  137,  330,  138,  139,
  331,   88,   88,   88,   88,   88,  334,   88,   88,  335,
  336,   88,  338,  342,   89,   89,   89,   89,   89,  343,
   89,   89,  344,  345,   89,  134,  135,  136,  137,  201,
  138,  139,  346,  350,  351,  352,  381,  385,  392,  386,
  380,  393,  134,  135,  136,  137,  387,  138,  139,  388,
  218,   45,  220,  395,  222,  223,  224,  227,  389,   21,
  390,   45,  391,  394,   22,  398,  399,  400,   23,   21,
  401,   45,  121,   25,   22,   26,  405,  402,   23,   21,
  403,   45,  122,   25,   22,   26,  258,  406,   23,   21,
  292,   45,  408,   25,   22,   26,  404,  407,   23,   21,
  296,   45,  409,   25,   22,   26,  410,  411,   23,   21,
  313,   45,  412,   25,   22,   26,  413,  414,   23,   21,
  314,   45,  415,   25,   22,   26,  416,  417,   23,   21,
  315,   45,  419,   25,   22,   26,  420,  421,   23,   21,
  316,   45,  424,   25,   22,   26,  427,  430,   23,   21,
  317,   45,  433,   25,   22,   26,  434,  436,   23,   21,
  321,   45,    4,   25,   22,   26,  432,  435,   23,   21,
  323,   45,  249,   25,   22,   26,    0,    0,   23,   21,
  422,   45,    0,   25,   22,   26,    0,    0,   23,   21,
  425,    0,    0,   25,   22,   26,    0,    0,   23,    0,
    0,    0,    0,   25,    0,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
   55,   20,   40,   40,   40,   24,   40,   24,   40,   49,
   59,   59,   52,   53,   41,   59,   18,   40,   40,  271,
  272,  256,   24,   59,   40,   44,   41,   46,  120,   48,
   41,   40,   51,  268,   41,   41,  256,  256,  256,   58,
   59,   60,   44,  256,  264,  264,   48,  282,   59,   40,
  268,   59,  268,  266,  274,  274,   45,   59,  276,   59,
   79,   59,  256,   82,  282,  157,  282,  159,    0,  256,
  264,  163,  164,  165,  268,  269,  256,  264,    0,  273,
  256,  268,  276,  123,  278,  125,  280,  256,  282,  256,
   45,  274,  268,  256,  265,  282,  276,  256,  274,  268,
  256,  268,   44,  143,  144,  268,  282,  276,   40,  276,
  256,  270,  268,  282,  256,  282,  256,   59,  264,  282,
  276,  256,  277,  269,   44,  265,  282,  273,  256,  271,
  272,   60,  278,   62,  280,  281,  264,  272,   45,   59,
   43,  269,   45,   44,   43,  273,   45,   43,   40,   45,
  278,   44,  280,  281,   44,   44,   59,   44,   59,  176,
   59,  274,  179,  180,  181,  182,   59,  264,  256,   59,
   59,    6,   59,   41,   42,   43,   41,   45,  266,   47,
  256,  108,   17,  110,   19,  112,  113,  256,  264,  265,
  264,   59,   60,  269,   62,  264,   42,  273,  274,   40,
  256,   47,  278,  256,  280,  264,  256,  226,  264,  249,
   41,  264,   40,  232,  264,  264,   40,  234,  266,  236,
  264,  238,  256,  240,  226,  242,  243,  265,  264,  256,
  232,  268,  264,  256,  253,  254,  255,  256,  257,  261,
  256,  260,  261,  262,  263,  282,  261,  256,  264,  256,
  256,  253,  254,  255,  256,  257,  256,   59,  266,  261,
  262,  266,   41,   41,  319,  256,  285,  286,  287,  288,
  289,  290,   59,  264,  293,  264,  295,  266,  269,  268,
  320,   60,  273,   62,  324,   59,  256,  278,  290,  280,
  256,  265,  309,  282,  264,  312,  351,   59,  264,  318,
  265,  256,  342,  343,  344,  345,  346,  347,   40,  264,
  350,  266,  352,  268,  264,  332,  333,  334,  335,  336,
   41,  338,  339,  340,  256,  271,  272,  282,  257,  258,
  259,  260,  264,   41,  256,  271,  272,  269,   41,  271,
  272,  273,  264,  275,   41,  277,  278,  269,  280,  256,
   59,  273,   41,  271,  272,  277,  278,  264,  280,  266,
   41,  268,  270,    0,    1,    2,   41,   41,   43,    6,
   45,   60,   41,   62,   43,  282,   45,  127,  128,   60,
   17,   62,   19,  266,  424,  270,  405,  427,  256,  257,
  258,  259,  260,   41,  262,  263,    1,    2,  266,  418,
  270,  420,  266,  405,  423,   42,   41,   41,   43,   43,
   45,   45,   60,  266,   62,  129,  130,   41,  420,   41,
   41,   43,   41,   45,   59,   60,   41,   62,   43,   41,
   45,   59,   59,   70,   59,   72,   60,   74,   62,   41,
   77,   43,   59,   45,   59,   60,   59,   62,  264,   41,
  211,  212,  213,  214,  276,  216,   59,   59,   60,   41,
   62,   43,  276,   45,  276,   41,   41,   43,   60,   45,
   62,  108,  276,  110,   59,  112,  113,  256,  257,  258,
  259,  260,  256,  262,  263,   60,  276,   62,  256,   41,
  264,   43,  276,   45,  266,  269,  264,   59,   59,  273,
   59,  269,  276,   59,  278,  273,  280,    6,  276,   59,
  278,  256,  280,   59,  272,   59,   59,  256,   17,  264,
   19,   59,   59,  276,  269,  264,   58,   41,  273,  256,
  269,  168,  277,  278,  273,  280,  272,  264,  277,  278,
  266,  280,  269,   41,  272,   43,  273,   45,  272,  272,
  277,  278,  189,  280,  191,  266,  193,  194,  195,  196,
   41,   41,   43,   43,   45,   45,  266,  256,  257,  258,
  259,  260,  266,  262,  263,  256,  257,  258,  259,  260,
  266,  262,  263,  256,   41,   41,   40,   43,  225,   45,
   40,  264,  265,   41,   58,   43,  269,   45,   59,   41,
  273,   43,   41,   45,   43,  278,   45,  280,  256,  257,
  258,  259,  260,   40,  262,  263,   41,   41,   43,   59,
   45,  256,  257,  258,  259,  260,   59,  262,  263,   59,
   59,  266,   41,  257,  258,  259,  260,   59,  262,  263,
   59,  256,  257,  258,  259,  260,   41,  262,  263,   41,
   41,  266,   41,   40,  256,  257,  258,  259,  260,   40,
  262,  263,   40,   40,  266,  257,  258,  259,  260,  168,
  262,  263,   40,   40,   40,   40,   59,   59,  261,   59,
   44,  277,  257,  258,  259,  260,   59,  262,  263,   59,
  189,  256,  191,   44,  193,  194,  195,  196,   59,  264,
   59,  256,   59,   59,  269,  277,  277,  277,  273,  264,
  277,  256,  277,  278,  269,  280,   59,  277,  273,  264,
  277,  256,  277,  278,  269,  280,  225,   59,  273,  264,
  275,  256,  261,  278,  269,  280,  277,  277,  273,  264,
  275,  256,   59,  278,  269,  280,  277,   59,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,   40,  278,  269,  280,   40,   59,  273,  264,
  275,  256,   59,  278,  269,  280,   59,   59,  273,  264,
  275,  256,    0,  278,  269,  280,  277,  277,  273,  264,
  275,  256,  215,  278,  269,  280,   -1,   -1,  273,  264,
  275,  256,   -1,  278,  269,  280,   -1,   -1,  273,  264,
  275,   -1,   -1,  278,  269,  280,   -1,   -1,  273,   -1,
   -1,   -1,   -1,  278,   -1,  280,
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
"SENTENCIA_EJECUTABLE : IDENTIFICADOR '(' EXPRESION ')'",
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
//#line 676 "Parser.java"
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
//#line 1085 "Parser.java"
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
