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
   11,    5,    5,   17,    9,    9,   18,   18,   18,   21,
   21,   22,   22,   22,   24,   24,   19,   19,    3,    3,
   20,   20,   23,   23,   23,   23,   23,   23,   23,   25,
   29,   30,   31,   30,   32,   34,   26,   33,   27,   27,
   28,   28,   28,   16,   36,   36,   37,   37,   38,   38,
   39,   15,   15,   15,   40,   40,   40,   41,   41,   41,
   41,   41,   42,   35,   35,   35,   35,   35,   35,    8,
    8,
};
final static short yylen[] = {                            2,
    5,    4,    1,    2,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    7,    3,    1,    3,    3,    1,    3,    1,    1,    3,
    3,    3,    1,    1,    3,    3,    2,    1,    2,    1,
    2,    1,    4,    5,    1,    1,    1,    1,    2,    6,
    3,    1,    0,    4,    0,    0,   13,    3,    4,    4,
    4,    4,    4,    1,    3,    1,    3,    1,    3,    1,
    4,    3,    3,    1,    3,    3,    1,    1,    1,    2,
    1,    1,    4,    1,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    4,    0,  101,    0,  100,    0,
   12,   13,   14,    0,   16,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   50,   55,   56,   57,
   58,    0,    0,    0,    9,   10,   11,    0,   36,    0,
    0,    0,    0,   24,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,    2,   49,    0,    6,   34,    0,
    0,    0,    0,   32,    0,    0,    0,    0,    0,    0,
   23,    0,    0,    0,   89,    0,    0,    0,   91,    0,
   87,   92,    0,    0,    0,    0,    0,    0,   78,    0,
    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,
   35,    0,    0,    0,    0,    0,    0,    0,    8,    0,
    0,   90,   69,    0,    0,    0,    0,   70,   53,   95,
   94,   96,   97,   98,   99,    0,   61,    0,    0,    0,
    0,    0,   43,   44,    0,   71,   73,    0,   72,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   85,   86,    0,
    0,   77,    0,    0,   63,    0,   54,   65,    0,    0,
    0,    0,    0,    0,    0,   29,    0,    0,   26,    0,
    0,   17,   93,   81,   45,    0,   42,    0,   60,    0,
   18,   19,   31,   20,   21,   22,   15,    0,   25,    0,
   28,   64,    0,    0,   27,    0,   66,    0,    0,    0,
   30,    0,    0,    0,    0,    0,    0,    0,   67,   38,
   39,   52,    0,   48,    0,   51,   40,   47,    0,   37,
};
final static short yydgoto[] = {                          2,
    3,   10,   26,    4,   11,   12,   13,   14,   41,   15,
   16,   44,   69,   70,   85,   86,   17,  219,  223,  224,
  221,  132,   27,  134,   28,   29,   30,   31,   51,  135,
  188,  190,  207,  210,  126,   87,   88,   89,   79,   80,
   81,   82,
};
final static short yysindex[] = {                      -223,
  -21,    0,   53,    0,    0, -171,    0,   84,    0, -147,
    0,    0,    0,   94,    0,   83,    0, -216,   18,   98,
  -45, -178,   46,  -14,   82,  -98,    0,    0,    0,    0,
    0,   -3,   84,  -92,    0,    0,    0,    4,    0,  -33,
   62, -162,   83,    0, -145,   84,   31,    0,  -40,   31,
 -175,   73,  -25, -125,    0,    0,  -78,    0,    0, -145,
 -145,  104, -132,    0, -109,  121,  116,  -75,  128,  -75,
    0,  149,  -58,  157,    0,  164,  -49,   29,    0,  -10,
    0,    0,  158,   89,    6,  183,  -42,  -19,    0,  -55,
  203,   59,  212,   17,    0,  261,  264, -145,  272,    7,
    0,   31,  278,  260,   58,  -75,  265,   76,    0,   31,
   31,    0,    0,   31,   31,   31,   31,    0,    0,    0,
    0,    0,    0,    0,    0,   31,    0,   31,   31,   40,
   84,   78,    0,    0,   70,    0,    0,  285,    0,  106,
   76,   76,  109,   76,   76, -227,  316,   31,   93,  310,
  317,  107,   66,  329,  332,  -10,  -10,    0,    0,  141,
  -19,    0,   54,   64,    0,  320,    0,    0,  113,  118,
  339,  147,  151,  322,  163,    0,  341,  324,    0,  114,
  325,    0,    0,    0,    0,  -45,    0,  -55,    0,  327,
    0,    0,    0,    0,    0,    0,    0,  343,    0,  330,
    0,    0,  130,  133,    0,  -50,    0,  333,   31,  334,
    0,  141,  131,  353, -127,   34,   74,  336,    0,    0,
    0,    0, -121,    0, -104,    0,    0,    0,  -45,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  396,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  397,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -39,    0,    0,    0,    0,    0,  -32,
    0,    0,    0,    0,  -41,    0,  357,   -2,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  134,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -26,    2,    0,    0,  -16,
   12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -110,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  340,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
};
final static short yygindex[] = {                         0,
    0,   71,    8,    0,   47,   49,   55,   10,  -62,    0,
    0,  358,  -46,    0,   -7,  254,    0,    0,  186,  140,
    0,  216,  243,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  199,    0,  280,  281,    0,  112,
  178,    0,
};
final static int YYTABLESIZE=468;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         80,
   41,   88,   88,   88,   77,   88,   63,   88,   84,  120,
   84,  121,   84,   48,   82,   93,   82,   34,   82,   88,
   88,  104,   88,  107,   79,   53,   84,   84,  174,   84,
   39,  116,   82,   82,    1,   82,  117,    5,   76,   78,
   57,   84,   83,   60,   83,  153,   83,  146,  114,   68,
  115,   19,   75,   73,   72,   48,   35,   45,   36,  151,
   83,   83,   59,   83,   37,  120,   35,  121,   36,   96,
   97,  114,  100,  115,   37,   77,   20,  106,  169,  170,
   49,  172,  173,  175,   18,   50,   43,  113,   90,   35,
    7,   36,   48,   21,  147,   22,   19,   37,   48,  138,
   23,    9,  154,  155,   24,   65,   66,  143,   32,   65,
   22,   25,   67,   91,    7,   23,    7,  137,  160,   24,
   64,   54,   33,   99,  182,    9,   25,    9,  216,    7,
   22,  114,   94,  115,   21,   23,   22,  163,  164,   24,
    9,   23,  217,   98,  218,   24,   25,  119,  101,  227,
  218,  229,   25,   22,   46,   46,   65,   21,   23,   22,
  102,   65,   24,   21,   23,   22,  230,  218,   24,   25,
   23,  191,   55,  103,   24,   25,  192,   21,   58,   22,
   21,   25,   22,  114,   23,  115,  105,   23,   24,  108,
   65,   24,   95,   66,   65,   25,  110,   21,   25,   22,
  130,  212,   22,  111,   23,  194,   65,   23,   24,  195,
  112,   24,  109,   47,  131,   25,  118,   74,   25,   75,
   83,  197,   61,  127,   62,  156,  157,  122,  123,  124,
  125,   92,   76,  128,   80,   80,   88,   88,   88,   88,
   88,   88,   52,   84,   84,   84,   84,   84,   84,   82,
   82,   82,   82,   82,   82,   47,   41,  129,   41,   79,
   79,  136,  145,   41,   19,   41,   41,   41,   56,   41,
  139,   41,   41,   76,   41,  140,   56,   83,   83,   83,
   83,   83,   83,  122,  123,  124,  125,   75,   74,   21,
   75,   22,   47,  158,  159,   21,   23,   22,   47,   56,
   24,  141,   23,   76,  142,  218,   24,   25,    6,   21,
   56,   22,  144,   25,    7,   56,   23,  148,  149,  186,
   24,   22,    8,  152,  185,    9,   23,   25,  150,   21,
   24,   22,  133,   39,  187,  166,   23,   25,   18,   21,
   24,   22,  165,  167,    7,  218,   23,   25,   56,   38,
   24,   39,   42,   18,  220,    9,  176,   25,  114,    7,
  115,   40,  228,  178,  228,  168,  171,   46,  179,  183,
    9,  114,  184,  115,  114,  180,  115,  181,  189,  193,
  196,  198,  199,  201,  200,  203,  204,  206,  205,  208,
  214,  211,  213,  215,  226,    5,    7,   74,   68,   62,
   71,  177,  225,  202,  209,   56,   56,  161,    0,  162,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  133,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  222,  222,  222,
    0,    0,    0,    0,    0,  222,    0,  222,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   41,   42,   43,   45,   45,   40,   47,   41,   60,
   43,   62,   45,   59,   41,   41,   43,   10,   45,   59,
   60,   68,   62,   70,   41,   40,   59,   60,  256,   62,
  258,   42,   59,   60,  258,   62,   47,   59,   41,   47,
   33,   49,   41,   40,   43,  108,   45,   41,   43,   42,
   45,  268,   41,   46,   45,   59,   10,   40,   10,  106,
   59,   60,   59,   62,   10,   60,   20,   62,   20,   60,
   61,   43,   63,   45,   20,   45,    6,   70,  141,  142,
  259,  144,  145,  146,  256,   40,   16,   59,  264,   43,
  262,   43,   59,  256,  102,  258,  268,   43,   59,   41,
  263,  273,  110,  111,  267,   44,  269,   98,  256,   44,
  258,  274,  275,   41,  262,  263,  262,   59,  126,  267,
   59,   40,  270,  256,   59,  273,  274,  273,  256,  262,
  258,   43,  258,   45,  256,  263,  258,  130,  131,  267,
  273,  263,  270,   40,  272,  267,  274,   59,  258,  271,
  272,  256,  274,  258,  265,  266,   44,  256,  263,  258,
   40,   44,  267,  256,  263,  258,  271,  272,  267,  274,
  263,   59,  271,   58,  267,  274,   59,  256,  271,  258,
  256,  274,  258,   43,  263,   45,   59,  263,  267,   41,
   44,  267,  271,  269,   44,  274,   40,  256,  274,  258,
  256,  209,  258,   40,  263,   59,   44,  263,  267,   59,
  260,  267,  271,  259,  270,  274,   59,  258,  274,  260,
  261,   59,  256,   41,  258,  114,  115,  278,  279,  280,
  281,  257,  273,  276,  276,  277,  276,  277,  278,  279,
  280,  281,  257,  276,  277,  278,  279,  280,  281,  276,
  277,  278,  279,  280,  281,  259,  256,  277,  258,  276,
  277,   59,  256,  263,  268,  265,  266,  267,   26,  269,
   59,  271,  272,  276,  274,  259,   34,  276,  277,  278,
  279,  280,  281,  278,  279,  280,  281,  276,  258,  256,
  260,  258,  259,  116,  117,  256,  263,  258,  259,   57,
  267,   41,  263,  273,   41,  272,  267,  274,  256,  256,
   68,  258,   41,  274,  262,   73,  263,   40,   59,  256,
  267,  258,  270,   59,  271,  273,  263,  274,  271,  256,
  267,  258,   90,  258,  271,  266,  263,  274,  256,  256,
  267,  258,  265,   59,  262,  272,  263,  274,  106,  256,
  267,  258,  270,  256,  215,  273,   41,  274,   43,  262,
   45,  268,  223,  271,  225,  260,  258,  270,   59,   41,
  273,   43,   41,   45,   43,   59,   45,  271,   59,   41,
   59,   41,   59,   59,  271,   59,   44,  258,   59,  257,
  260,   59,   59,   41,   59,    0,    0,   41,   59,  266,
   43,  148,  217,  188,  206,  163,  164,  128,   -1,  129,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  188,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  215,  216,  217,
   -1,   -1,   -1,   -1,   -1,  223,   -1,  225,
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

//#line 332 "gramatica.y"

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
    

//#line 755 "Parser.java"
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
                                        bifurcacionTrue.setOperando2("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                        }
break;
case 31:
//#line 99 "gramatica.y"
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
//#line 114 "gramatica.y"
{addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
break;
case 34:
//#line 120 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 35:
//#line 124 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 36:
//#line 125 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 37:
//#line 128 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 40:
//#line 133 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 41:
//#line 134 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 42:
//#line 137 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 45:
//#line 142 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 46:
//#line 143 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 51:
//#line 154 "gramatica.y"
{addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
break;
case 53:
//#line 158 "gramatica.y"
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
case 54:
//#line 174 "gramatica.y"
{addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", val_peek(2).sval + "%")); addCadena(val_peek(2).sval + "%");}
break;
case 59:
//#line 179 "gramatica.y"
{yyerrorSemantico("Sentencia no reconocida");}
break;
case 60:
//#line 183 "gramatica.y"
{addEstructura("Sentencia IF");
                                    addTerceto(new Terceto("END_IF"));}
break;
case 61:
//#line 187 "gramatica.y"
{ Terceto terceto = new Terceto("BF", val_peek(1).sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto); }
break;
case 63:
//#line 194 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop(); 
                                    
                                    tercetoIncompleto.setOperando2("[" + (tercetos.get(getIdentificadorFuncionActual()).size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
break;
case 64:
//#line 201 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop();
                                    tercetoIncompleto.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");}
break;
case 65:
//#line 206 "gramatica.y"
{
                                            String id = getAmbitoIdentificador(val_peek(2).sval); 
                                            addTerceto(new Terceto(":=", id, val_peek(0).sval)); }
break;
case 66:
//#line 209 "gramatica.y"
{ 
                                            Terceto tercetoIncompleto = new Terceto("BF", val_peek(0).sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); }
break;
case 67:
//#line 212 "gramatica.y"
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
case 68:
//#line 227 "gramatica.y"
{
                                    addTerceto(new Terceto("REPEAT"));
                                    Terceto terceto = new Terceto(val_peek(1).sval, getAmbitoIdentificador(val_peek(2).sval), val_peek(0).sval); 
                                    addTerceto(terceto); 
                                    backpatching.push(terceto); 
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 69:
//#line 235 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 70:
//#line 236 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 71:
//#line 239 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 72:
//#line 240 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 73:
//#line 241 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 74:
//#line 244 "gramatica.y"
{yyval = val_peek(0);}
break;
case 75:
//#line 247 "gramatica.y"
{ 
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto("||", val_peek(2).sval, val_peek(0).sval, "INT")); 
                                            yyval = getReferenciaUltimaInstruccion();}
break;
case 76:
//#line 251 "gramatica.y"
{yyval = val_peek(0);}
break;
case 77:
//#line 254 "gramatica.y"
{ 
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto("&&", val_peek(2).sval, val_peek(0).sval, "INT")); 
                                            yyval = getReferenciaUltimaInstruccion();
                                        }
break;
case 78:
//#line 259 "gramatica.y"
{yyval = val_peek(0);}
break;
case 79:
//#line 262 "gramatica.y"
{
                                            if(checkTipos(val_peek(2).sval, val_peek(0).sval))
                                                addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval,"INT")); 
                                            yyval = getReferenciaUltimaInstruccion();
                                            }
break;
case 80:
//#line 267 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 81:
//#line 270 "gramatica.y"
{ addTerceto(new Terceto("CONV", val_peek(1).sval, null, "SINGLE")); yyval = getReferenciaUltimaInstruccion();}
break;
case 82:
//#line 273 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("+",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 83:
//#line 277 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("-",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 84:
//#line 281 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 85:
//#line 284 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("*", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("*",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 86:
//#line 288 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("/", $1.sval, $3.sval));*/
                                    addTercetoAritmetica("/",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 87:
//#line 292 "gramatica.y"
{yyval = val_peek(0);}
break;
case 88:
//#line 295 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 89:
//#line 296 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 90:
//#line 300 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 91:
//#line 303 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval);}
break;
case 92:
//#line 304 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = val_peek(0);}
break;
case 93:
//#line 307 "gramatica.y"
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
case 94:
//#line 318 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 95:
//#line 319 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 96:
//#line 320 "gramatica.y"
{yyval = new ParserVal(">=");}
break;
case 97:
//#line 321 "gramatica.y"
{yyval = new ParserVal("<=");}
break;
case 98:
//#line 322 "gramatica.y"
{yyval = new ParserVal("==");}
break;
case 99:
//#line 323 "gramatica.y"
{ yyval = new ParserVal("<>"); }
break;
case 100:
//#line 326 "gramatica.y"
{tipo = "SINGLE"; yyval = new ParserVal("SINGLE");}
break;
case 101:
//#line 327 "gramatica.y"
{tipo = "INT";  yyval = new ParserVal("INT");}
break;
//#line 1313 "Parser.java"
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
