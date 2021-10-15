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
//#line 28 "Parser.java"




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
    0,    0,    0,    1,    4,    4,    4,    4,    2,    2,
    2,    2,    2,    2,    8,    8,   11,   11,   11,   11,
   11,   11,    7,    7,   13,   13,   13,   13,   14,   15,
   12,   12,   19,   19,   19,   19,   19,   19,    6,    6,
   20,   10,   10,    5,    5,    5,   22,   22,    3,    3,
   21,   21,   21,   21,   21,   21,   21,   21,   23,   27,
   28,   28,   29,   24,   30,   25,   25,   26,   26,   26,
   17,   17,   17,   33,   16,   16,   16,   34,   34,   34,
   18,   18,   36,   35,   35,   35,   35,   35,   31,   31,
   31,   31,   31,   31,   32,   32,    9,    9,
};
final static short yylen[] = {                            2,
    5,    4,    1,    1,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    6,    1,    6,    6,    6,    6,    6,    6,    3,    1,
    3,    3,    1,    3,    1,    1,    3,    3,    2,    1,
    4,    5,    2,    1,    1,    5,    1,    1,    6,    3,
    1,    3,    0,   12,    3,    4,    4,    4,    4,    4,
    3,    3,    1,    4,    3,    3,    1,    3,    3,    1,
    2,    1,    2,    1,    1,    2,    1,    4,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    4,    0,    0,    3,    0,   98,    0,   97,    0,   12,
   13,   14,    0,   16,    0,   32,   40,    0,    0,    0,
    0,    0,    0,    0,    0,   63,    0,   50,   54,   55,
   57,   58,    0,    0,    0,    9,   10,   11,   45,   46,
    0,   43,    0,    0,    0,    0,   24,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   53,    0,    2,
   49,    0,    0,    0,    0,    7,    0,    6,    0,    0,
   41,    0,    0,    0,   39,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,    0,   85,    0,    0,    0,
   87,    0,   80,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   42,    0,    0,    0,    0,
    0,    0,    0,    0,   82,    0,    8,    0,    0,   86,
   66,    0,    0,    0,    0,   67,   51,    0,   91,   92,
   93,   94,   95,   96,   90,   89,   60,    0,    0,    0,
    0,   68,    0,   70,   69,    0,   44,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   83,   81,   33,    0,    0,    0,    0,
    0,   78,   79,   56,    0,    0,    0,    0,   52,    0,
   34,    0,   35,    0,   36,   37,   38,   31,    0,    0,
    0,    0,   29,    0,    0,   26,    0,    0,   17,   88,
   74,   62,   59,    0,   18,   19,   20,   21,   22,   15,
    0,   25,    0,   28,    0,    0,   27,    0,    0,    0,
    0,    0,   30,    0,    0,    0,   64,
};
final static short yydgoto[] = {                          2,
    3,    9,   62,    4,   35,   10,   11,   12,   13,   44,
   14,   15,   47,   80,   81,   97,   98,  124,   16,   17,
   28,   40,   29,   30,   31,   32,   55,  151,   59,  229,
  148,  149,   91,   92,   93,  125,
};
final static short yysindex[] = {                      -250,
    0,    0, -159,    0,  -87,    0,  166,    0, -119,    0,
    0,    0,   13,    0,  -85,    0,    0, -256,  -30,  -84,
 -225,  -33,    4,  -32,  -13,    0,   91,    0,    0,    0,
    0,    0,   85,   97, -212,    0,    0,    0,    0,    0,
  -34,    0,  -31,   48,  -79,  -85,    0,   68, -235,  166,
  -42,  -43,  -42,  -42, -140,   92,  -26,    0,   71,    0,
    0,  116,  122,  166,  128,    0,    0,    0,   99, -235,
    0,  -40,  -20, -206,    0, -109,  118,  168,  134,  121,
  134,    0, -235,  145,  154,  171,    0,  197,  -22,  130,
    0,  158,    0,  202,  161,   44,  164,   50,   97,  204,
  -24,  207,    7,    0,  160, -225,    0, -235,  227, -235,
  242, -235, -112,  249,  -29,    0,  -42,  252,  238,   27,
  134,  255, -211,  270,    0,   55,    0,  -42,  -42,    0,
    0,  -42,  -42,  -42,  -42,    0,    0,  267,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -42,  -42,   62,
   64,    0,  285,    0,    0,   81,    0,  304,   55,  307,
   55,  310,  311,  -18,   55,   55, -181,  169,  -42,   79,
  298,  303,   90,    0,    0,    0,   70,  200,  219,  158,
  158,    0,    0,    0,  164,  164,   97,  315,    0,  110,
    0,   84,    0,   87,    0,    0,    0,    0,   94,  144,
  320,  149,    0,   58,  322,    0,  105,  324,    0,    0,
    0,    0,    0,  329,    0,    0,    0,    0,    0,    0,
  355,    0,  345,    0,  147,  152,    0,  -41,  356,  358,
  -42,  155,    0,  164,  378,   97,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,  420,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   11,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -11,    0,    0,    0,    0,
    0,   -4,    0,    0,    0,    0,   65,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  156,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
   43,    0,    0,    0,   72,   80,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  366,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   37,    9,    0,  -21,   18,   73,   95,   52,  -98,
    0,    0,  380,  -55,    0,   19,  272,   66,    0,    0,
   -5,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  208,    0,    0,  153,   17,    0,
};
final static int YYTABLESIZE=446;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        110,
   48,   89,   89,   39,    1,   70,   53,   57,   74,   49,
   45,  167,   66,    1,  102,   27,  153,   19,  145,  113,
  146,   61,  198,  119,   71,  122,   36,  177,   67,   84,
   84,   84,    6,   84,  154,   84,   77,   36,   77,   51,
   77,   20,   65,   54,  174,   58,    8,   84,   84,  114,
   84,   46,  175,   79,   77,   77,   61,   77,   85,   61,
  192,    6,  194,   36,   68,  172,  199,  200,  202,   90,
   95,   96,  105,   61,  201,    8,   75,  150,   75,   61,
   75,   37,   42,   76,  138,   76,  132,   76,  133,  121,
  147,   76,   37,   39,   75,   75,    5,   75,  221,   61,
   84,   76,   76,   38,   76,   73,   75,   83,    6,  145,
  103,  146,   72,   76,   38,   61,    7,  145,   37,  146,
   71,  109,    8,  111,   73,  115,   73,   76,  209,   99,
   76,   72,  100,   72,  123,  168,   33,   76,  108,   71,
   38,   71,  215,  163,   22,  216,  178,  179,    6,   23,
  182,  183,  217,   24,  116,    6,   34,  117,   25,  123,
   26,  123,    8,  123,  123,  212,  185,  186,   18,    8,
   18,   18,  132,  158,  133,  160,   21,  162,  164,  120,
    6,   39,    6,    6,   22,  126,   19,   76,  131,   23,
   45,   50,   76,   24,    8,   77,    8,    8,   25,  134,
   26,   78,  218,  132,  135,  133,  132,  220,  133,  203,
  128,  132,   94,  133,  237,  139,  140,  141,  142,  137,
   86,   86,   87,   87,   72,  118,  166,    6,   56,   69,
   39,   52,   73,   48,  101,  112,  129,  197,   88,   88,
  210,    8,  132,  130,  133,   84,   84,   84,   84,  234,
   84,   84,   77,   77,   77,   77,   48,   77,   77,  211,
  136,  132,  152,  133,   48,  155,   50,  159,   41,   48,
  156,   48,   48,   48,   50,   48,   42,   48,   48,   50,
   48,   44,  161,   50,  180,  181,   43,   50,   50,  165,
   50,  169,   75,   75,   75,   75,  170,   75,   75,   76,
   76,   76,   76,  171,   76,   76,  139,  140,  141,  142,
  176,  143,  144,  173,  139,  140,  141,  142,   42,  143,
  144,   73,   73,   73,   73,  184,   73,   73,   72,   72,
   72,   72,  187,   72,   72,  188,   71,   71,   71,   71,
   21,   71,   71,  189,  191,  190,   21,  193,   22,   51,
  195,  196,   63,   23,   22,  205,  206,   24,   19,   23,
   22,  207,   25,   24,   26,   23,  208,   60,   25,   24,
   26,   21,   64,  213,   25,  214,   26,   21,  219,   22,
  222,  223,  224,  106,   23,   22,   51,  225,   24,   21,
   23,   22,  104,   25,   24,   26,   23,   22,  226,   25,
   24,   26,   23,  227,  107,   25,   24,   26,   77,   21,
  228,   25,  230,   26,  232,  106,  233,   22,  236,    5,
  235,   21,   23,   22,   65,   82,   24,   61,   23,   22,
  127,   25,   24,   26,   23,  231,  157,   25,   24,   26,
  204,    0,    0,   25,    0,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   45,   45,    9,    0,   40,   40,   40,   40,   40,
    0,   41,   34,  264,   41,    7,   41,  274,   60,   40,
   62,   27,   41,   79,   59,   81,    9,  126,   34,   41,
   42,   43,  268,   45,   59,   47,   41,   20,   43,  265,
   45,    5,   34,   40,  256,   59,  282,   59,   60,  256,
   62,   15,  264,   45,   59,   60,   62,   62,   50,   65,
  159,  268,  161,   46,  277,  121,  165,  166,  167,   51,
   52,   53,   64,   79,  256,  282,   41,   99,   43,   85,
   45,    9,  264,   41,   41,   43,   43,   45,   45,   81,
   41,   44,   20,   99,   59,   60,  256,   62,   41,  105,
   49,   59,   60,    9,   62,   41,   59,   40,  268,   60,
   40,   62,   41,   44,   20,  121,  276,   60,   46,   62,
   41,   70,  282,   72,   60,   74,   62,   44,   59,  270,
   44,   60,   41,   62,   83,  117,  256,   44,   40,   60,
   46,   62,   59,  256,  264,   59,  128,  129,  268,  269,
  134,  135,   59,  273,  264,  268,  276,   40,  278,  108,
  280,  110,  282,  112,  113,  187,  148,  149,  256,  282,
  256,  256,   43,  108,   45,  110,  256,  112,  113,   59,
  268,  187,  268,  268,  264,   41,  274,   44,   59,  269,
  276,  276,   44,  273,  282,  275,  282,  282,  278,   42,
  280,  281,   59,   43,   47,   45,   43,   59,   45,   41,
   40,   43,  256,   45,  236,  257,  258,  259,  260,   59,
  264,  264,  266,  266,  256,   58,  256,  268,  261,  264,
  236,  265,  264,  264,  261,  256,   40,  256,  282,  282,
   41,  282,   43,  266,   45,  257,  258,  259,  260,  231,
  262,  263,  257,  258,  259,  260,  256,  262,  263,   41,
   59,   43,   59,   45,  264,   59,  256,   41,  256,  269,
  264,  271,  272,  273,  264,  275,  264,  277,  278,  269,
  280,  277,   41,  273,  132,  133,  274,  277,  278,   41,
  280,   40,  257,  258,  259,  260,   59,  262,  263,  257,
  258,  259,  260,  277,  262,  263,  257,  258,  259,  260,
   41,  262,  263,   59,  257,  258,  259,  260,  264,  262,
  263,  257,  258,  259,  260,   59,  262,  263,  257,  258,
  259,  260,  271,  262,  263,  272,  257,  258,  259,  260,
  256,  262,  263,   59,   41,  265,  256,   41,  264,  265,
   41,   41,  256,  269,  264,  277,   59,  273,  274,  269,
  264,   59,  278,  273,  280,  269,  277,  277,  278,  273,
  280,  256,  276,   59,  278,  266,  280,  256,   59,  264,
   59,  277,   59,  256,  269,  264,  265,   59,  273,  256,
  269,  264,  277,  278,  273,  280,  269,  264,   44,  278,
  273,  280,  269,   59,  277,  278,  273,  280,  275,  256,
  264,  278,  261,  280,   59,  256,   59,  264,   41,    0,
  266,  256,  269,  264,   59,   46,  273,  272,  269,  264,
  277,  278,  273,  280,  269,  228,  277,  278,  273,  280,
  169,   -1,   -1,  278,   -1,  280,
};
}
final static short YYFINAL=2;
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
"PROGRAMA : PROGRAMA_ENCABEZADO SENTENCIA_DECLARATIVA BEGIN CONJUNTO_SENTENCIAS END",
"PROGRAMA : PROGRAMA_ENCABEZADO BEGIN CONJUNTO_SENTENCIAS END",
"PROGRAMA : PROGRAMA_ERROR",
"PROGRAMA_ENCABEZADO : IDENTIFICADOR",
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
"ENCABEZADO_FUNC : TIPO FUNC IDENTIFICADOR '(' PARAMETRO ')'",
"ENCABEZADO_FUNC : ENCABEZADO_FUNC_ERROR",
"ENCABEZADO_FUNC_ERROR : error FUNC IDENTIFICADOR '(' PARAMETRO ')'",
"ENCABEZADO_FUNC_ERROR : TIPO error IDENTIFICADOR '(' PARAMETRO ')'",
"ENCABEZADO_FUNC_ERROR : TIPO FUNC error '(' PARAMETRO ')'",
"ENCABEZADO_FUNC_ERROR : TIPO FUNC IDENTIFICADOR error PARAMETRO ')'",
"ENCABEZADO_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' error ')'",
"ENCABEZADO_FUNC_ERROR : TIPO FUNC IDENTIFICADOR '(' PARAMETRO error",
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
"CUERPO_IF : BLOQUE_SENTENCIA ELSE BLOQUE_SENTENCIA",
"$$1 :",
"SENTENCIA_REPEAT : REPEAT $$1 '(' IDENTIFICADOR ASIGNACION CTE ';' CONDICION_REPEAT ';' CTE ')' BLOQUE_SENTENCIA",
"CONDICION_REPEAT : IDENTIFICADOR OPERADOR_COMPARADOR EXPRESION",
"ASIGNACION_ERROR : error ASIGNACION EXPRESION ';'",
"ASIGNACION_ERROR : IDENTIFICADOR ASIGNACION error ';'",
"PRINT_ERROR : PRINT CADENA ')' ';'",
"PRINT_ERROR : PRINT '(' ')' ';'",
"PRINT_ERROR : PRINT '(' CADENA ';'",
"CONDICION : CONDICION OPERADOR_LOGICO EXPRESION",
"CONDICION : CONDICION OPERADOR_COMPARADOR EXPRESION",
"CONDICION : EXPRESION",
"CONVERSION : FLOAT '(' EXPRESION ')'",
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

//#line 207 "gramatica.y"

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

    public void addTipoVariables(){
        Map<String, Object> atributos;
        for(String lexema: variables){
            atributos = lexico.getAtributosLexema(lexema);
            atributos.put("TIPO", tipo);
        }
        variables.clear();
    }

    public void addUsoIdentificador(String lexema, String uso) {
        Map<String, Object> atributos = lexico.getAtributosLexema(lexema);
        atributos.put("USO", uso);        
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
        return new ParserVal("["+ tercetos.size() + "]");
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
            addUsoIdentificador(identificador, uso);
        }
    }

//#line 611 "Parser.java"
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
//#line 28 "gramatica.y"
{ambitoActual.add(0, val_peek(0).sval);}
break;
case 5:
//#line 31 "gramatica.y"
{yyerror("Bloque principal no especificado.");}
break;
case 6:
//#line 32 "gramatica.y"
{yyerror("BEGIN del bloque principal no especificado.");}
break;
case 7:
//#line 33 "gramatica.y"
{yyerror("END del bloque principal no especificado.");}
break;
case 8:
//#line 34 "gramatica.y"
{yyerror("Falta el nombre del programa.");}
break;
case 15:
//#line 45 "gramatica.y"
{addEstructura("Declaracion de varables");
                                                                        addTipoVariables();
                                                                        }
break;
case 17:
//#line 51 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 18:
//#line 52 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 19:
//#line 53 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 20:
//#line 54 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 21:
//#line 55 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 22:
//#line 56 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 23:
//#line 60 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 24:
//#line 62 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 31:
//#line 77 "gramatica.y"
{verificarRedeclaracion(val_peek(3).sval, "ID_FUNC"); ambitoActual.add(0, val_peek(3).sval);}
break;
case 33:
//#line 81 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 34:
//#line 82 "gramatica.y"
{yyerror("Falta la palabra clave FUNC.");}
break;
case 35:
//#line 83 "gramatica.y"
{yyerror("Falta el identificador de la funcion.");}
break;
case 36:
//#line 84 "gramatica.y"
{yyerror("Falta el primer parentesis de la funcion.");}
break;
case 37:
//#line 85 "gramatica.y"
{yyerror("Falta el parametro de la funcion.");}
break;
case 38:
//#line 86 "gramatica.y"
{yyerror("Falta el segundo parentesis de la funcion.");}
break;
case 39:
//#line 90 "gramatica.y"
{addEstructura("Declaracion de variables");
                                                    addTipoVariables();
                                                    }
break;
case 41:
//#line 96 "gramatica.y"
{yyerror("Variables mal declaradas.");}
break;
case 42:
//#line 100 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 43:
//#line 101 "gramatica.y"
{verificarRedeclaracion(val_peek(0).sval, "ID_VARIABLE");}
break;
case 44:
//#line 104 "gramatica.y"
{addEstructura("Bloque de sentencias con BEGIN/END");}
break;
case 47:
//#line 109 "gramatica.y"
{yyerror("Falta el BEGIN del bloque de sentencia.");}
break;
case 48:
//#line 110 "gramatica.y"
{yyerror("Falta el END del bloque de sentencia");}
break;
case 51:
//#line 117 "gramatica.y"
{addEstructura("Asignacion"); tercetos.add(new Terceto(":=", getAmbitoIdentificador(val_peek(3).sval), val_peek(1).sval)); }
break;
case 52:
//#line 118 "gramatica.y"
{addEstructura("Sentencia PRINT"); tercetos.add(new Terceto("PRINT", val_peek(2).sval)); }
break;
case 53:
//#line 119 "gramatica.y"
{addEstructura("BREAK"); tercetos.add(new Terceto("BREAK"));}
break;
case 56:
//#line 122 "gramatica.y"
{addEstructura("Llamado a funcion");}
break;
case 59:
//#line 127 "gramatica.y"
{addEstructura("Sentencia IF");}
break;
case 60:
//#line 130 "gramatica.y"
{}
break;
case 63:
//#line 137 "gramatica.y"
{}
break;
case 64:
//#line 137 "gramatica.y"
{addEstructura("Sentencia REPEAT");/*Agregar CTE como último terceto*/}
break;
case 65:
//#line 140 "gramatica.y"
{}
break;
case 66:
//#line 143 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 67:
//#line 144 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 68:
//#line 147 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 69:
//#line 148 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 70:
//#line 149 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 74:
//#line 157 "gramatica.y"
{ tercetos.add(new Terceto("CONV", val_peek(1).sval, null, val_peek(3).sval)); }
break;
case 75:
//#line 160 "gramatica.y"
{ tercetos.add(new Terceto("+", val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion(); }
break;
case 76:
//#line 161 "gramatica.y"
{ tercetos.add(new Terceto("-", val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion(); }
break;
case 77:
//#line 162 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 78:
//#line 165 "gramatica.y"
{ tercetos.add(new Terceto("*", val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion(); }
break;
case 79:
//#line 166 "gramatica.y"
{ tercetos.add(new Terceto("/", val_peek(2).sval, val_peek(0).sval));yyval = getReferenciaUltimaInstruccion();}
break;
case 80:
//#line 167 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 81:
//#line 170 "gramatica.y"
{addUsoIdentificador(val_peek(0).sval, "ID_PARAMETRO");}
break;
case 83:
//#line 174 "gramatica.y"
{yyerror("Falta el identificador");}
break;
case 84:
//#line 177 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 85:
//#line 178 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 86:
//#line 182 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 88:
//#line 186 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = new ParserVal(getAmbitoIdentificador(val_peek(3).sval));}
break;
case 89:
//#line 189 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 90:
//#line 190 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 91:
//#line 191 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 92:
//#line 192 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 93:
//#line 193 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 94:
//#line 194 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 95:
//#line 197 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 96:
//#line 198 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 97:
//#line 201 "gramatica.y"
{tipo = "FLOAT";}
break;
case 98:
//#line 202 "gramatica.y"
{tipo = "INT";}
break;
//#line 1029 "Parser.java"
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
