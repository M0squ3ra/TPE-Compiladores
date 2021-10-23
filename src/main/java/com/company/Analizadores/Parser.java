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
   22,   22,   22,   22,   22,   22,   24,   29,   30,   31,
   30,   32,   34,   25,   33,   27,   27,   28,   28,   28,
   17,   17,   17,   37,   16,   16,   16,   38,   38,   38,
   19,   19,   40,   39,   39,   39,   39,   39,   26,   35,
   35,   35,   35,   35,   35,   36,   36,    9,    9,
};
final static short yylen[] = {                            2,
    5,    4,    1,    2,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    0,    7,    1,    6,    6,    6,    3,    1,    3,    3,
    1,    3,    1,    1,    3,    3,    2,    1,    4,    5,
    2,    1,    1,    2,    1,    1,    6,    3,    1,    0,
    4,    0,    0,   13,    3,    4,    4,    4,    4,    4,
    3,    3,    1,    4,    3,    3,    1,    3,    3,    1,
    2,    1,    2,    1,    1,    2,    1,    1,    4,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    4,    0,   99,    0,   98,    0,
   12,   13,   14,    0,   16,    0,   33,   38,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   48,   52,
   53,    0,   55,   56,    0,    0,    0,    9,   10,   11,
   43,   44,    0,   41,    0,    0,    0,    0,   24,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
    0,    2,   47,   54,    0,    0,    0,    0,    7,    0,
    6,    0,   39,    0,    0,   31,    0,   37,    0,    0,
    0,    0,    0,    0,   23,    0,    0,    0,    0,   85,
    0,    0,    0,   88,   87,    0,   80,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   45,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   40,    0,
    0,    0,    0,    0,    0,    0,    0,   82,    0,    8,
    0,   86,   66,    0,    0,    0,    0,   67,   49,   89,
   92,   93,   94,   95,   96,   97,   91,   90,   58,    0,
    0,    0,    0,   68,   70,    0,   69,    0,   42,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   83,   81,   34,    0,    0,    0,    0,
   78,   79,    0,    0,   60,    0,   50,   62,   35,    0,
   36,    0,    0,    0,    0,    0,    0,   29,    0,    0,
   26,    0,    0,   17,   74,    0,   57,    0,   18,   19,
   32,   20,   21,   22,   15,    0,   25,    0,   28,   61,
    0,    0,   27,    0,   63,    0,    0,    0,   30,    0,
    0,    0,    0,   64,
};
final static short yydgoto[] = {                          2,
    3,   10,   65,    4,   37,   11,   12,   13,   14,   46,
   15,   16,   49,   83,   84,  101,  102,  116,  127,   17,
   18,   29,   42,   30,   31,   32,   33,   34,   57,  153,
  206,  208,  225,  228,  150,  151,   95,   96,   97,  128,
};
final static short yysindex[] = {                      -254,
  -19,    0,  -16,    0,    0, -106,    0,  174,    0,  -99,
    0,    0,    0, -232,    0,  137,    0,    0, -238,  -33,
  166, -205,  -36,   23,  -27,   10,   32,   84,    0,    0,
    0,   34,    0,    0,  -81,   94, -177,    0,    0,    0,
    0,    0,  -34,    0,  -31,   -9, -120,  137,    0,   75,
 -200,  174,  -42,  -43,  -42,  -42, -149,  106,  -23,    0,
 -129,    0,    0,    0,  104,  122,  174,  119,    0,    0,
    0,  108,    0, -200,  -40,    0, -191,    0,  -92,  136,
  132,  -69,  135,  -69,    0, -200,  157,  134,  161,    0,
  175,  -46,   14,    0,    0,   95,    0,  183,  100,  169,
  121,   50,   94,  188,  -15,  191,    3,    0,  159, -205,
    0, -200,  210, -200,  220,  229,  230,  -24,    0,  -42,
  237,  227,   19,  -69,  231, -140,  265,    0,   47,    0,
  -42,    0,    0,  -42,  -42,  -42,  -42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -42,
  -42,   62,   46,    0,    0,  262,    0,   60,    0,  306,
   47,  308,   47, -200,   47,   47, -118,  221,  -42,   78,
  292,  293,   82,    0,    0,    0,   30,  242,   95,   95,
    0,    0,  121,  121,    0,  295,    0,    0,    0,   55,
    0,   63,  315,   74,  112,  307,  123,    0,   57,  310,
    0,   88,  312,    0,    0,   94,    0,  317,    0,    0,
    0,    0,    0,    0,    0,  335,    0,  321,    0,    0,
  125,  124,    0,  -41,    0,  342,  -42,  345,    0,  121,
  128,  365,   94,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  408,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   11,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -14,    0,
    0,    0,    0,    0,    0,   -4,    0,    0,    0,    0,
   65,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  145,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   35,   42,
    0,    0,   72,   79,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  350,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   80,   56,    0,  -28,   13,   33,   61,   77, -114,
    0,    0,  370,  -62,    0,   76,  250,    0,  -98,    0,
    0,    2,    0,    0,    0,  290,    0,    0,    0,    0,
    0,    0,    0,    0,  196,    0,    0,   51,   66,    0,
};
final static int YYTABLESIZE=517;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        114,
   46,   92,   92,   55,    1,   74,   51,   69,   77,    1,
   43,   41,   59,  160,  177,  162,  167,  106,  147,  122,
  148,  125,   38,   43,   73,  156,   84,   84,   84,   63,
   84,   44,   84,   38,   79,   20,   77,   70,   77,    5,
   77,   45,   39,  155,   84,   84,  190,   84,  192,   78,
  194,  195,  197,   39,   77,   77,  134,   77,  135,   53,
   38,  172,   56,   28,  117,  193,   63,    7,   60,   63,
   40,   61,  133,   79,  152,   75,    7,   75,    9,   75,
   39,   40,   76,   63,   76,   21,   76,    9,  204,   63,
  149,   68,   64,   75,   75,   48,   75,  216,   79,   71,
   76,   76,   82,   76,   41,   73,   79,   88,   40,  147,
   63,  148,   72,  209,   86,  174,  147,   79,  148,   71,
  103,  210,  109,  175,   73,   63,   73,   87,   93,   99,
  100,   72,  212,   72,  107,   22,  136,  196,   71,  124,
   71,  137,  134,   23,  135,   44,  104,  112,   24,   19,
  113,  115,   25,  118,   80,   79,   35,   26,  139,   27,
   81,    7,  126,  134,   23,  135,   79,   20,    7,   24,
  213,  119,    9,   25,   22,  120,   36,  220,   26,    9,
   27,  215,   23,   53,  179,  180,   22,   24,  126,  121,
  126,   25,   20,  123,   23,  168,   26,  129,   27,   24,
   55,  181,  182,   25,  234,   80,  178,   41,   26,  140,
   27,  134,   98,  135,  131,  141,  142,  143,  144,  132,
   89,   89,   90,   90,   75,  183,  184,    7,   54,   72,
   50,  166,   76,   58,   41,   91,   91,  105,    9,    6,
  126,  138,   84,   84,   84,   84,  154,   84,   84,  157,
  161,    7,   77,   77,   77,   77,   46,   77,   77,    8,
  163,  198,    9,  134,   46,  135,   48,  158,  164,   46,
  165,   46,   46,   46,   48,   46,  169,   46,   46,   48,
   46,   42,  205,   48,  134,  170,  135,   48,   48,  173,
   48,   75,   75,   75,   75,  171,   75,   75,   76,   76,
   76,   76,  230,   76,   76,  176,  141,  142,  143,  144,
   44,  145,  146,  141,  142,  143,  144,  186,  145,  146,
  187,   73,   73,   73,   73,  188,   73,   73,   72,   72,
   72,   72,  185,   72,   72,   71,   71,   71,   71,   22,
   71,   71,   94,   94,   94,   94,  189,   23,  191,   66,
  201,  202,   24,  207,  200,  211,   25,   23,  203,   22,
   62,   26,   24,   27,  218,  214,   25,   23,  217,   67,
  219,   26,   24,   27,  110,  221,   25,   22,  222,  223,
  108,   26,   23,   27,  226,   23,   53,   24,  224,   22,
   24,   25,   19,  232,   25,  111,   26,   23,   27,   26,
  229,   27,   24,  231,    7,  233,   25,    5,   65,   94,
  130,   26,   47,   27,  110,    9,   59,   85,  199,  227,
   94,   19,   23,   94,   94,   94,   94,   24,    0,   22,
    0,   25,    0,    7,    0,  159,   26,   23,   27,   94,
   94,   52,   24,    0,    9,    0,   25,    0,    0,    0,
    0,   26,    0,   27,    0,    0,    0,    0,   94,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   94,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   45,   40,    0,   40,   40,   36,   40,  264,
    0,   10,   40,  112,  129,  114,   41,   41,   60,   82,
   62,   84,   10,  256,   59,   41,   41,   42,   43,   28,
   45,  264,   47,   21,   44,  274,   41,   36,   43,   59,
   45,  274,   10,   59,   59,   60,  161,   62,  163,   59,
  165,  166,  167,   21,   59,   60,   43,   62,   45,  265,
   48,  124,   40,    8,  256,  164,   65,  268,   59,   68,
   10,   40,   59,   44,  103,   41,  268,   43,  279,   45,
   48,   21,   41,   82,   43,    6,   45,  279,   59,   88,
   41,   36,   59,   59,   60,   16,   62,   41,   44,  277,
   59,   60,   47,   62,  103,   41,   44,   52,   48,   60,
  109,   62,   41,   59,   40,  256,   60,   44,   62,   41,
  270,   59,   67,  264,   60,  124,   62,   51,   53,   54,
   55,   60,   59,   62,  264,  256,   42,  256,   60,   84,
   62,   47,   43,  264,   45,  264,   41,   40,  269,  256,
   74,   75,  273,   77,  275,   44,  256,  278,   59,  280,
  281,  268,   86,   43,  264,   45,   44,  274,  268,  269,
   59,  264,  279,  273,  256,   40,  276,  206,  278,  279,
  280,   59,  264,  265,  134,  135,  256,  269,  112,   58,
  114,  273,  274,   59,  264,  120,  278,   41,  280,  269,
   40,  136,  137,  273,  233,  275,  131,  206,  278,   41,
  280,   43,  256,   45,   40,  257,  258,  259,  260,  266,
  264,  264,  266,  266,  256,  150,  151,  268,  265,  264,
  264,  256,  264,  261,  233,  279,  279,  261,  279,  256,
  164,   59,  257,  258,  259,  260,   59,  262,  263,   59,
   41,  268,  257,  258,  259,  260,  256,  262,  263,  276,
   41,   41,  279,   43,  264,   45,  256,  265,   40,  269,
   41,  271,  272,  273,  264,  275,   40,  277,  278,  269,
  280,  277,   41,  273,   43,   59,   45,  277,  278,   59,
  280,  257,  258,  259,  260,  277,  262,  263,  257,  258,
  259,  260,  227,  262,  263,   41,  257,  258,  259,  260,
  264,  262,  263,  257,  258,  259,  260,  272,  262,  263,
   59,  257,  258,  259,  260,  266,  262,  263,  257,  258,
  259,  260,  271,  262,  263,  257,  258,  259,  260,  256,
  262,  263,   53,   54,   55,   56,   41,  264,   41,  256,
   59,   59,  269,   59,  277,   41,  273,  264,  277,  256,
  277,  278,  269,  280,  277,   59,  273,  264,   59,  276,
   59,  278,  269,  280,  256,   59,  273,  256,   44,   59,
  277,  278,  264,  280,  261,  264,  265,  269,  264,  256,
  269,  273,  256,  266,  273,  277,  278,  264,  280,  278,
   59,  280,  269,   59,  268,   41,  273,    0,   59,  120,
  277,  278,  276,  280,  256,  279,  272,   48,  169,  224,
  131,  256,  264,  134,  135,  136,  137,  269,   -1,  256,
   -1,  273,   -1,  268,   -1,  277,  278,  264,  280,  150,
  151,  276,  269,   -1,  279,   -1,  273,   -1,   -1,   -1,
   -1,  278,   -1,  280,   -1,   -1,   -1,   -1,  169,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  227,
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
"SENTENCIA_EJECUTABLE : LLAMADO_FUNCION ';'",
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
"FACTOR : LLAMADO_FUNCION",
"LLAMADO_FUNCION : IDENTIFICADOR '(' EXPRESION ')'",
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

//#line 283 "gramatica.y"

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

    // En el caso de que se haya detectado un error semantico, no se genera el codigo
    // Pero se permite que el programa siga reconociendo errores y estructuras
    public void addTerceto(Terceto t){
        if(!errorSemantico) {
            tercetos.add(t);
        }
    }

    public void checkRetornoFuncion(String expresion){
        String ambito = getAmbito(ambitoActual);
        String ambitoSplit[] = ambito.split("\\."); 
        String funcion = ambitoSplit[ambitoSplit.length - 1];
        funcion += ambito.split(funcion)[0];
        funcion = funcion.substring(0,funcion.length()-1);

        checkTipos(expresion, funcion);
    }
    

//#line 699 "Parser.java"
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
case 27:
//#line 68 "gramatica.y"
{
                                    Terceto bifurcacionIncondicional = backpatching.pop();
                                    bifurcacionIncondicional.setOperando1("[" + tercetos.size() + "]");
                                }
break;
case 28:
//#line 72 "gramatica.y"
{
                                    Terceto bifurcacionIncondicional = backpatching.pop();
                                    bifurcacionIncondicional.setOperando1("[" + tercetos.size() + "]");
                                }
break;
case 29:
//#line 78 "gramatica.y"
{checkRetornoFuncion(val_peek(1).sval); addTerceto(new Terceto("RETURN_FUNC", val_peek(1).sval, null, getTipo(val_peek(1).sval)));}
break;
case 30:
//#line 81 "gramatica.y"
{
                                        Terceto bifurcacionTrue = new Terceto("BT", val_peek(4).sval, null);
                                        addTerceto(bifurcacionTrue);
                                        addTerceto(new Terceto("PRINT", val_peek(1).sval));
                                        Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                        addTerceto(bifurcacionIncondicional);
                                        backpatching.push(bifurcacionIncondicional);
                                        bifurcacionTrue.setOperando2("[" + tercetos.size() + "]");
                                        }
break;
case 31:
//#line 92 "gramatica.y"
{addAtributoLexema(val_peek(0).sval,"TIPO",val_peek(2).sval); verificarRedeclaracion(val_peek(0).sval, "ID_FUNC"); ambitoActual.add(0, val_peek(0).sval);}
break;
case 34:
//#line 96 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 35:
//#line 97 "gramatica.y"
{yyerror("Falta la palabra clave FUNC.");}
break;
case 36:
//#line 98 "gramatica.y"
{yyerror("Falta el identificador de la funcion.");}
break;
case 37:
//#line 101 "gramatica.y"
{addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
break;
case 39:
//#line 107 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 40:
//#line 111 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 41:
//#line 112 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 42:
//#line 115 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 45:
//#line 120 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 46:
//#line 121 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 49:
//#line 128 "gramatica.y"
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
//#line 144 "gramatica.y"
{addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", val_peek(2).sval)); }
break;
case 51:
//#line 145 "gramatica.y"
{addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
break;
case 54:
//#line 148 "gramatica.y"
{addEstructura("Llamado a funcion");}
break;
case 57:
//#line 154 "gramatica.y"
{addEstructura("Sentencia IF");}
break;
case 58:
//#line 157 "gramatica.y"
{ Terceto terceto = new Terceto("BF", val_peek(1).sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto); }
break;
case 60:
//#line 164 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop(); 
                                    tercetoIncompleto.setOperando2("[" + (tercetos.size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
break;
case 61:
//#line 170 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop();
                                    tercetoIncompleto.setOperando1("[" + tercetos.size() + "]");}
break;
case 62:
//#line 175 "gramatica.y"
{
                                            String id = getAmbitoIdentificador(val_peek(2).sval); 
                                            addTerceto(new Terceto(":=", id, val_peek(0).sval)); }
break;
case 63:
//#line 178 "gramatica.y"
{ 
                                            Terceto tercetoIncompleto = new Terceto("BF", val_peek(0).sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); }
break;
case 64:
//#line 181 "gramatica.y"
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
//#line 197 "gramatica.y"
{Terceto terceto = new Terceto(val_peek(1).sval, getAmbitoIdentificador(val_peek(2).sval), val_peek(0).sval); addTerceto(terceto); backpatching.push(terceto); yyval = getReferenciaUltimaInstruccion();}
break;
case 66:
//#line 200 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 67:
//#line 201 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 68:
//#line 204 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 69:
//#line 205 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 70:
//#line 206 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 71:
//#line 209 "gramatica.y"
{ addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion();}
break;
case 72:
//#line 210 "gramatica.y"
{addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion();}
break;
case 73:
//#line 211 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 74:
//#line 214 "gramatica.y"
{ addTerceto(new Terceto("CONV", val_peek(1).sval, null, "SINGLE")); yyval = getReferenciaUltimaInstruccion();}
break;
case 75:
//#line 217 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("+",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 76:
//#line 221 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("-",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 77:
//#line 225 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 78:
//#line 228 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("*", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("*",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 79:
//#line 232 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("/", $1.sval, $3.sval));*/
                                    addTercetoAritmetica("/",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 80:
//#line 236 "gramatica.y"
{yyval = val_peek(0);}
break;
case 81:
//#line 239 "gramatica.y"
{
                                    String identificador = setAmbitoIdentificador(val_peek(0).sval);
                                    addAtributoLexema(identificador,"USO","ID_PARAMETRO"); 
                                    addAtributoLexema(identificador,"TIPO",val_peek(1).sval);
                                    }
break;
case 83:
//#line 247 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 84:
//#line 250 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 85:
//#line 251 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 86:
//#line 255 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 87:
//#line 258 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval);}
break;
case 88:
//#line 259 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = val_peek(0);}
break;
case 89:
//#line 262 "gramatica.y"
{String id = getAmbitoIdentificador(val_peek(3).sval); addTerceto(new Terceto("CALL_FUNC", id, val_peek(1).sval,getTipo(id))); yyval = getReferenciaUltimaInstruccion();}
break;
case 90:
//#line 265 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 91:
//#line 266 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 92:
//#line 267 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 93:
//#line 268 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 94:
//#line 269 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 95:
//#line 270 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 96:
//#line 273 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 97:
//#line 274 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 98:
//#line 277 "gramatica.y"
{tipo = "SINGLE"; yyval = new ParserVal("SINGLE");}
break;
case 99:
//#line 278 "gramatica.y"
{tipo = "INT";  yyval = new ParserVal("INT");}
break;
//#line 1220 "Parser.java"
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
