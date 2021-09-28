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
   16,   16,   16,   16,   16,   16,   19,   19,   20,   20,
   20,   21,   21,   21,   21,   21,   21,   21,   21,   21,
   21,   21,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   13,   13,   13,   18,   25,   12,   12,   12,   26,
   26,   26,   11,   11,   28,   27,   27,   27,   27,   27,
   24,   24,   24,   24,   24,   24,   23,   23,    8,    8,
};
final static short yylen[] = {                            2,
    5,    4,    1,    1,    3,    3,    5,    2,    2,    1,
    1,    1,    7,    1,    7,    7,    7,    7,    7,    7,
   16,   15,   24,   23,    1,   16,   16,   16,   16,   16,
   16,   16,   16,   15,    3,    1,    3,    3,    1,    3,
    1,    1,    3,    3,    2,    1,    4,    5,    2,   10,
    8,   11,    1,    1,    1,    1,    4,    4,    4,    4,
    4,   10,   10,   10,   10,   10,   10,    8,    8,    8,
    8,    8,   11,   11,   11,   10,   11,   10,   11,   11,
   11,    3,    3,    1,    3,    4,    3,    3,    1,    3,
    3,    1,    2,    1,    2,    1,    1,    2,    1,    4,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,  110,  109,    0,    0,    3,   10,   11,   12,
    0,   14,   25,   36,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    8,    9,    0,
   41,   42,   53,   54,   55,   56,    0,   39,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    6,
    0,   49,    0,    0,    5,    0,    0,    0,    0,   37,
    0,    0,    0,   35,    0,    0,    0,    0,    2,   45,
    0,    0,   97,    0,    0,    0,   99,    0,   92,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   40,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   38,    0,    0,   94,    0,    7,    1,
    0,   98,    0,   57,    0,    0,    0,    0,   58,   47,
  103,  104,  105,  106,  107,  108,  102,  101,    0,    0,
    0,    0,    0,    0,   59,    0,   61,   60,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   95,   93,    0,    0,    0,    0,    0,    0,
   90,   91,    0,    0,    0,    0,    0,    0,    0,   48,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   15,
  100,   86,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   16,    0,   17,    0,    0,
    0,    0,    0,    0,   18,   19,   20,   13,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   68,    0,   69,    0,   70,    0,   71,   72,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   62,   63,   64,   65,   66,   67,   50,    0,    0,
    0,    0,    0,   78,    0,    0,    0,   76,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   80,   73,   74,   75,   77,   79,   81,   52,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   22,    0,    0,   34,    0,   26,   27,   28,
   29,   30,   31,   32,    0,   33,    0,   21,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   24,    0,   23,
};
final static short yydgoto[] = {                          5,
    6,   51,    7,   27,    8,    9,   10,   85,   40,   12,
  116,   93,   94,   13,   14,   31,   32,  214,   33,   34,
   35,   36,  140,  141,   87,   88,   89,  117,
};
final static short yysindex[] = {                      -220,
 -202, -171,    0,    0,    0, -182,    0,    0,    0,    0,
 -175,    0,    0,    0, -254,  -33, -153,  569, -152, -125,
 -241,  -37,  -11,  264,    5,  -35, -224,    0,    0, -142,
    0,    0,    0,    0,    0,    0,  -36,    0,  -26,   48,
   19, -122, -165,  569, -177,  396,    0,  569,   82, -129,
  402,   24,   82,   73,  101,  -10,  421,  569,  427,    0,
    0,    0, -126, -199,    0,   -9,  -78,  145, -122,    0,
  -25,    3, -231,    0,  -74, -122,  161,  433,    0,    0,
  439,  166,    0,  -57,  253,   74,    0,   94,    0,    0,
  250,  100,  244,  359,  271,  196,  283,   17,  304, -177,
    0,   84,  118,   69,  353, -122,  345, -122,  360, -122,
 -207,  366,   -7,    0,  -60,  376,    0,  154,    0,    0,
 -122,    0,   82,    0,   82,   82,   82,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  178,   82,
   82,  187,  203,  -73,    0,  416,    0,    0,  165,  212,
  218, -173,  445,  154,  450,  154,  456,  466,    4,  154,
  154,   43,    0,    0, -122,   62,  467,  369,   94,   94,
    0,    0,  264,  244,  244,  264,  264,  264,  264,    0,
  441,  453,  457,  462,    9, -122,  128, -122,  136, -122,
 -122, -122, -148,  144,  155,  463,  217,  249,   97,    0,
    0,    0, -159, -105,   80,  102, -146,  262,  262,  262,
  262,  223,  114,  470,  255,    0,  259,    0,  260,  270,
  272, -122,  -99, -186,    0,    0,    0,    0,  569,  491,
  264,  492,  264,  493,  264,  502,  264,  503,  254,  264,
  505,  506,  507,  508,  510,   82,  511,  -51,  292,  569,
  569,  569,  569,  569,  287,  480,  458,  569,  -80,  459,
  299,    0,  300,    0,  306,    0,  309,    0,    0,  310,
 -245,    0,  318,  319,  320,  321,  244,  322,  551, -119,
  556,  479,  489,  499,  509,  519,  569,  550,  558,  529,
  541,  539,  560,  542,  548,  552,  553,  554,  555,  561,
  574,  582,  583,  585,  586,  264,  587,   15,  264,  570,
  593,  597,  598,  599,    6,   82,   82,  600,  601,  602,
   82,    0,    0,    0,    0,    0,    0,    0,  264,  264,
  264,  264,  264,    0,  264,  264,  264,    0,   82,   82,
   82,   82,   82,   82,  372,  398,   82,   82,   82,  401,
    0,    0,    0,    0,    0,    0,    0,    0,  419,  420,
  425,  426,  444,  460,  604,  584,  461,  387,  474,  592,
  594,  595,  596,  608,  609,  611,  395,  404,  613,  634,
  -43,  407,  410,  411,  415,  432,  443,  447,  639,  659,
  448,  465,  662,  452,  671,  681,  682,  683,  685,  687,
  688,  569,    0,  690,  691,    0,  692,    0,    0,    0,
    0,    0,    0,    0,  549,    0,  569,    0,  716,  559,
   82,  720,  498,   82,  702,  504,  494,  707,  711,  513,
    0,  717,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  780,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   67,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  109,    0,    0,    0,    0,    0,  132,    0,    0,
    0,    0,  236,    0,    0,    0,    0,    0,    0,   55,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  167,  317,
    0,    0,    0,  297,  346,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -12,    0,    0,    0,
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
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  379,   -6,    0,  -15,  471,  337,    0,  403,  -83,    0,
  -89,  -39,  -53,    0,    0,  -18,    0,  227,    0,    0,
    0,    0,    0,  579,    0,  290,  323,    0,
};
final static int YYTABLESIZE=849;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         47,
   96,   47,   54,   69,   64,   61,   42,  280,   60,   86,
  299,   46,   92,   73,  108,  394,  153,   59,  155,   16,
  157,  159,   70,   52,  112,   47,  300,   80,   56,   47,
   99,  167,   80,  162,  166,    1,    3,   78,   47,   47,
   80,   81,  111,    2,  193,  344,   85,    3,  158,   70,
    4,   59,   65,   15,   44,  337,  103,  146,   76,   80,
    3,    4,   80,   62,  104,    3,   41,  213,   84,  258,
  187,   16,  189,   20,    4,  147,  194,  195,  197,    4,
   37,   21,  184,  168,   15,    3,   22,   49,   38,  259,
   23,   75,  185,   24,   44,   25,    3,   26,   39,    4,
  174,  175,   43,   43,   18,   75,   74,  222,   50,  239,
    4,  231,  232,   66,    3,    3,  125,   84,  126,    3,
  200,   38,   44,   48,  240,  241,   84,  223,    4,    4,
   45,   67,  124,    4,   41,  127,  307,  102,   21,   49,
  128,   97,  125,   22,  126,    3,  308,   23,   50,   96,
   96,   96,   25,   96,   26,   96,   45,  203,  130,    4,
  204,  205,  206,  207,   21,  233,  234,   96,   96,   22,
   96,   75,   89,   23,   89,   45,   89,  105,   25,   75,
   26,  256,  178,   21,  106,   72,  216,   75,   22,  114,
   89,   89,   23,   89,  218,  163,  179,   25,   75,   26,
  291,  118,  225,  164,   47,  121,  277,   87,  122,   87,
   47,   87,  393,  226,  279,  261,  257,  263,   53,  265,
   63,  267,  260,  270,  271,   87,   87,   68,   87,   71,
   41,   47,   47,   47,   47,   47,  144,   72,   80,   47,
   47,   80,    3,  282,  283,  284,  285,  286,  161,   55,
   98,  290,  292,   85,   68,  137,    4,  138,  110,  192,
   75,   45,  345,   80,   80,   80,   80,   80,   47,   21,
  336,   80,  212,   80,   22,  228,   84,  346,   23,   91,
  315,  350,  137,   25,  138,   26,  125,   82,  126,   83,
  334,    3,  123,  338,  368,   84,   80,   84,  196,  359,
  360,  361,  362,  363,  364,    4,   38,  367,  129,  369,
   44,  142,  269,  351,  352,  353,  354,  355,   44,  356,
  357,  358,   46,   44,  151,   44,   44,   44,   95,   44,
   46,   44,   44,  152,   44,   46,   82,   82,   83,   46,
    3,  145,   29,   46,   46,   82,   46,   83,  149,    3,
  235,  236,  230,   29,    4,   29,   82,   88,   82,   88,
   38,   88,  148,    4,   96,   96,   96,   96,   96,  247,
   96,   96,  237,  238,   96,   88,   88,  212,   88,   17,
   19,  423,  150,   47,  426,  154,   83,   89,   89,   89,
   89,   89,  108,   89,   89,  415,   80,   89,   47,  139,
  156,   80,   11,   11,   11,   83,  160,   83,   30,  202,
  420,  125,  365,  126,  169,  170,  165,   38,  137,   30,
  138,   30,   87,   87,   87,   87,   87,  380,   87,   87,
  181,  137,   87,  138,  242,  243,  244,  245,  366,  248,
  125,  370,  126,  125,   77,  126,  137,  173,  138,  171,
  172,  143,  131,  132,  133,  134,  176,  135,  136,  371,
  372,  125,  125,  126,  126,  373,  374,  125,  125,  126,
  126,  107,  177,  109,  180,  113,   28,  182,  115,  131,
  132,  133,  134,  183,  375,  186,  125,   28,  126,   28,
  188,   84,   84,   84,   84,   84,  190,   84,   84,  208,
  376,  379,  125,  125,  126,  126,  191,  201,  115,   57,
  115,  209,  115,  115,  381,  210,  125,   21,  126,   57,
  211,  227,   22,  115,  229,  212,   23,   21,  249,   58,
  250,   25,   22,   26,  251,  252,   23,  288,  425,   58,
  125,   25,  126,   26,  428,  253,  125,  254,  126,   70,
  262,  264,   82,   82,   82,   82,   82,  281,   82,   82,
  266,  268,  287,  272,  273,  274,  275,  199,  276,  278,
  294,  295,   88,   88,   88,   88,   88,  296,   88,   88,
  297,  298,   88,  301,  302,  303,  304,  305,  199,  316,
  199,  306,  199,  199,  199,  199,  309,  317,  319,  321,
  322,   83,   83,   83,   83,   83,  323,   83,   83,  339,
  324,  325,  326,  327,  329,  131,  132,  133,  134,  328,
  135,  136,  330,  331,  199,  332,  333,  335,  131,  132,
  133,  134,  340,  135,  136,  198,  341,  342,  343,  347,
  348,  349,  378,  131,  132,  133,  134,  377,  135,  136,
  382,   45,  383,  384,  385,  389,  215,   45,  217,   21,
  219,  220,  221,  224,   22,   21,  386,  387,   23,  388,
   22,  391,   79,   25,   23,   26,   45,  392,   90,   25,
  390,   26,  100,  395,   21,   49,  396,  397,   45,   22,
   21,  398,  255,   23,   45,   22,   21,  402,   25,   23,
   26,   22,   21,  101,   25,   23,   26,   22,  399,  119,
   25,   23,   26,   45,   45,  120,   25,  403,   26,  400,
  406,   21,   21,  401,  404,  405,   22,   22,  407,  408,
   23,   23,  289,  293,   45,   25,   25,   26,   26,  409,
  410,  411,   21,  412,   45,  413,  414,   22,  416,  417,
  418,   23,   21,  310,   45,  421,   25,   22,   26,  424,
  427,   23,   21,  311,   45,  430,   25,   22,   26,  431,
  429,   23,   21,  312,   45,  433,   25,   22,   26,    4,
    0,   23,   21,  313,   45,    0,   25,   22,   26,  432,
  246,   23,   21,  314,   45,    0,   25,   22,   26,    0,
    0,   23,   21,  318,   45,    0,   25,   22,   26,    0,
    0,   23,   21,  320,   45,    0,   25,   22,   26,    0,
    0,   23,   21,  419,   45,    0,   25,   22,   26,    0,
    0,   23,   21,  422,    0,    0,   25,   22,   26,    0,
    0,   23,    0,    0,    0,    0,   25,    0,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         18,
   54,   20,   40,   40,   40,   24,   40,   59,   24,   49,
  256,   18,   52,   40,   40,   59,  106,   24,  108,  274,
  110,  111,   59,  265,  256,   44,  272,   46,   40,   48,
   41,  121,   51,   41,  118,  256,  268,   44,   57,   58,
   59,   48,   40,  264,   41,   40,   59,  268,  256,   59,
  282,   58,  277,  256,    0,   41,  256,   41,   40,   78,
  268,  282,   81,   59,  264,  268,    0,   59,   45,  256,
  154,  274,  156,  256,  282,   59,  160,  161,  162,  282,
  256,  264,  256,  123,  256,  268,  269,  265,  264,  276,
  273,   44,  266,  276,   40,  278,  268,  280,  274,  282,
  140,  141,  256,  256,  276,   44,   59,  256,  274,  256,
  282,  271,  272,  256,  268,  268,   43,   45,   45,  268,
   59,  264,  276,  276,  271,  272,   45,  276,  282,  282,
  256,  274,   59,  282,  264,   42,  256,  264,  264,  265,
   47,   41,   43,  269,   45,  268,  266,  273,  274,   41,
   42,   43,  278,   45,  280,   47,  256,  173,   59,  282,
  176,  177,  178,  179,  264,  271,  272,   59,   60,  269,
   62,   44,   41,  273,   43,  256,   45,  256,  278,   44,
  280,  281,  256,  264,   40,  264,   59,   44,  269,  264,
   59,   60,  273,   62,   59,  256,  270,  278,   44,  280,
  281,   41,   59,  264,  223,   40,  246,   41,  266,   43,
  229,   45,  256,   59,  266,  231,  223,  233,  256,  235,
  256,  237,  229,  239,  240,   59,   60,  264,   62,  256,
  264,  250,  251,  252,  253,  254,   41,  264,  257,  258,
  259,  260,  268,  250,  251,  252,  253,  254,  256,  261,
  261,  258,  259,  266,  264,   60,  282,   62,  256,  256,
   44,  256,  316,  282,  283,  284,  285,  286,  287,  264,
  256,  290,  264,  292,  269,   59,   41,  317,  273,  256,
  287,  321,   60,  278,   62,  280,   43,  264,   45,  266,
  306,  268,   40,  309,  348,   60,  315,   62,  256,  339,
  340,  341,  342,  343,  344,  282,  264,  347,   59,  349,
  256,   41,   59,  329,  330,  331,  332,  333,  264,  335,
  336,  337,  256,  269,  256,  271,  272,  273,  256,  275,
  264,  277,  278,  265,  280,  269,  264,   41,  266,  273,
  268,   59,    6,  277,  278,  264,  280,  266,  265,  268,
  271,  272,  256,   17,  282,   19,   60,   41,   62,   43,
  264,   45,   59,  282,  256,  257,  258,  259,  260,  256,
  262,  263,  271,  272,  266,   59,   60,  264,   62,    1,
    2,  421,  265,  402,  424,   41,   41,  256,  257,  258,
  259,  260,   40,  262,  263,  402,  415,  266,  417,   41,
   41,  420,    0,    1,    2,   60,   41,   62,    6,   41,
  417,   43,   41,   45,  125,  126,   41,  264,   60,   17,
   62,   19,  256,  257,  258,  259,  260,   41,  262,  263,
  266,   60,  266,   62,  208,  209,  210,  211,   41,  213,
   43,   41,   45,   43,   42,   45,   60,  270,   62,  127,
  128,  256,  257,  258,  259,  260,  270,  262,  263,   41,
   41,   43,   43,   45,   45,   41,   41,   43,   43,   45,
   45,   69,  270,   71,   59,   73,    6,  266,   76,  257,
  258,  259,  260,  266,   41,   41,   43,   17,   45,   19,
   41,  256,  257,  258,  259,  260,   41,  262,  263,   59,
   41,   41,   43,   43,   45,   45,   41,   41,  106,  256,
  108,   59,  110,  111,   41,   59,   43,  264,   45,  256,
   59,   59,  269,  121,  276,  264,  273,  264,   59,  276,
  276,  278,  269,  280,  276,  276,  273,   58,   41,  276,
   43,  278,   45,  280,   41,  276,   43,  276,   45,   59,
   59,   59,  256,  257,  258,  259,  260,  266,  262,  263,
   59,   59,  276,   59,   59,   59,   59,  165,   59,   59,
  272,  272,  256,  257,  258,  259,  260,  272,  262,  263,
  272,  272,  266,  266,  266,  266,  266,  266,  186,   40,
  188,   41,  190,  191,  192,  193,   41,   40,   58,   40,
   59,  256,  257,  258,  259,  260,   59,  262,  263,   40,
   59,   59,   59,   59,   41,  257,  258,  259,  260,   59,
  262,  263,   41,   41,  222,   41,   41,   41,  257,  258,
  259,  260,   40,  262,  263,  165,   40,   40,   40,   40,
   40,   40,   59,  257,  258,  259,  260,   44,  262,  263,
   59,  256,   59,   59,   59,  261,  186,  256,  188,  264,
  190,  191,  192,  193,  269,  264,   59,   59,  273,   59,
  269,   59,  277,  278,  273,  280,  256,   44,  277,  278,
  277,  280,  256,  277,  264,  265,  277,  277,  256,  269,
  264,  277,  222,  273,  256,  269,  264,   59,  278,  273,
  280,  269,  264,  277,  278,  273,  280,  269,  277,  277,
  278,  273,  280,  256,  256,  277,  278,   59,  280,  277,
   59,  264,  264,  277,  277,  261,  269,  269,  277,   59,
  273,  273,  275,  275,  256,  278,  278,  280,  280,   59,
   59,   59,  264,   59,  256,   59,   59,  269,   59,   59,
   59,  273,  264,  275,  256,   40,  278,  269,  280,   40,
   59,  273,  264,  275,  256,   59,  278,  269,  280,   59,
  277,  273,  264,  275,  256,   59,  278,  269,  280,    0,
   -1,  273,  264,  275,  256,   -1,  278,  269,  280,  277,
  212,  273,  264,  275,  256,   -1,  278,  269,  280,   -1,
   -1,  273,  264,  275,  256,   -1,  278,  269,  280,   -1,
   -1,  273,  264,  275,  256,   -1,  278,  269,  280,   -1,
   -1,  273,  264,  275,  256,   -1,  278,  269,  280,   -1,
   -1,  273,  264,  275,   -1,   -1,  278,  269,  280,   -1,
   -1,  273,   -1,   -1,   -1,   -1,  278,   -1,  280,
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
"FACTOR : IDENTIFICADOR '(' PARAMETRO ')'",
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

//#line 194 "gramatica.y"

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
case 57:
//#line 104 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 58:
//#line 105 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 59:
//#line 108 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 60:
//#line 109 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 61:
//#line 110 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 62:
//#line 113 "gramatica.y"
{yyerror("Falta el primer paréntesis de la condición del IF.");}
break;
case 63:
//#line 114 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 64:
//#line 115 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 65:
//#line 116 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 66:
//#line 117 "gramatica.y"
{yyerror("Falta el ELSE del IF.");}
break;
case 67:
//#line 118 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 68:
//#line 119 "gramatica.y"
{yyerror("Falta el primer paréntesiis de la condición del IF.");}
break;
case 69:
//#line 120 "gramatica.y"
{yyerror("Falta la condición del IF.");}
break;
case 70:
//#line 121 "gramatica.y"
{yyerror("Falta el último paréntesis de la condición del IF.");}
break;
case 71:
//#line 122 "gramatica.y"
{yyerror("Falta el THEN del IF.");}
break;
case 72:
//#line 123 "gramatica.y"
{yyerror("Falta el ENDIF del IF.");}
break;
case 73:
//#line 126 "gramatica.y"
{yyerror("Falta el identificador del REPEAT.");}
break;
case 74:
//#line 127 "gramatica.y"
{yyerror("Falta el asignador al identificador del REPEAT.");}
break;
case 75:
//#line 128 "gramatica.y"
{yyerror("El identificador no tiene constante a asignar del REPEAT.");}
break;
case 76:
//#line 129 "gramatica.y"
{yyerror("Falta ';' luego de la asignacion del REPEAT.");}
break;
case 77:
//#line 130 "gramatica.y"
{yyerror("Falta la condicion del ciclo del REPEAT.");}
break;
case 78:
//#line 131 "gramatica.y"
{yyerror("Falta ';' luego de la condicion del REPEAT.");}
break;
case 79:
//#line 132 "gramatica.y"
{yyerror("Falta la constante de iteracion del REPEAT.");}
break;
case 80:
//#line 133 "gramatica.y"
{yyerror("Falta el primer paréntesis del REPEAT.");}
break;
case 81:
//#line 134 "gramatica.y"
{yyerror("Falta el segundo paréntesis del REPEAT.");}
break;
case 95:
//#line 162 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 97:
//#line 166 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                        yyerror("Constante fuera de rango");
                                            }}
break;
case 98:
//#line 169 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
//#line 1074 "Parser.java"
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
