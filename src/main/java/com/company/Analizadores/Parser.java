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
   12,    6,    6,   18,   10,   10,    5,    5,    5,   20,
   20,    3,    3,   19,   19,   19,   19,   19,   19,   19,
   19,   21,   26,   27,   28,   27,   29,   31,   22,   30,
   24,   24,   25,   25,   25,   17,   17,   17,   34,   16,
   16,   16,   35,   35,   35,   36,   36,   36,   36,   36,
   23,   32,   32,   32,   32,   32,   32,   33,   33,    9,
    9,
};
final static short yylen[] = {                            2,
    5,    4,    1,    2,    2,    4,    4,    6,    2,    2,
    2,    1,    1,    1,    7,    1,    7,    7,    7,    7,
    7,    7,    3,    2,    6,    5,    7,    6,    4,    8,
    7,    3,    1,    3,    3,    1,    3,    1,    1,    3,
    3,    2,    1,    4,    5,    2,    1,    1,    2,    1,
    1,    6,    3,    1,    0,    4,    0,    0,   13,    3,
    4,    4,    4,    4,    4,    3,    3,    1,    4,    3,
    3,    1,    3,    3,    1,    1,    1,    2,    1,    1,
    4,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    4,    0,   91,    0,   90,    0,
   12,   13,   14,    0,   16,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   43,   47,   48,
    0,   50,   51,    0,    0,    0,    9,   10,   11,   38,
   39,    0,   36,    0,    0,    0,    0,   24,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    2,
   42,   49,    0,    0,    0,    0,    7,    0,    6,   34,
    0,    0,    0,    0,   32,    0,    0,    0,    0,    0,
    0,   23,    0,    0,    0,   77,    0,    0,    0,   80,
   79,    0,   75,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   40,    0,    0,    0,    0,    0,    0,
    0,    0,   35,    0,    0,    0,    0,    0,    0,    0,
    8,    0,   78,   61,    0,    0,    0,    0,   62,   44,
   81,   84,   85,   86,   87,   88,   89,   83,   82,   53,
    0,    0,    0,    0,   63,   65,    0,   64,    0,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   73,   74,    0,    0,
   55,    0,   45,   57,    0,    0,    0,    0,    0,    0,
    0,   29,    0,    0,   26,    0,    0,   17,   69,    0,
   52,    0,   18,   19,   31,   20,   21,   22,   15,    0,
   25,    0,   28,   56,    0,    0,   27,    0,   58,    0,
    0,    0,   30,    0,    0,    0,    0,   59,
};
final static short yydgoto[] = {                          2,
    3,   10,   63,    4,   36,   11,   12,   13,   14,   45,
   15,   16,   48,   80,   81,   97,   98,   17,   28,   41,
   29,   30,   31,   32,   33,   55,  144,  190,  192,  209,
  212,  141,  142,   91,   92,   93,
};
final static short yysindex[] = {                      -198,
   20,    0, -136,    0,    0,  -83,    0,  178,    0,   80,
    0,    0,    0, -235,    0,   -8,    0, -204,   56,   89,
 -155,  -38,   83,  -32,   88,   90,  110,    0,    0,    0,
   91,    0,    0,   98,  113, -133,    0,    0,    0,    0,
    0,  -28,    0,  -36,  -14,  -92,   -8,    0, -218,  178,
  -42,  -45,  -42,  -42, -117,  116,  -35,    0,  -98,    0,
    0,    0,  128, -205,  178,  138,    0,    0,    0,    0,
 -218, -218,  129, -143,    0,  -93,  134,  122,  148,  123,
  148,    0,  146,  158,  153,    0,  161,  -64,   -1,    0,
    0,   99,    0,  144,  106,  167,  -20,   42,  113,  150,
  -13,  154,  -51,    0,  168, -155,    0,  174,  175, -218,
  182,  -31,    0,  -42,  202,  187,  -30,  148,  190,  -10,
    0,  -42,    0,    0,  -42,  -42,  -42,  -42,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -42,  -42,    6,   13,    0,    0,  199,    0,   17,    0,
  -10,  -10,   22,  -10,  -10, -207,  218,  -42,   26,  231,
  251,   36,    4,  221,   99,   99,    0,    0,  -20,  -20,
    0,  259,    0,    0,   23,   48,  284,  108,  111,  273,
  135,    0,   49,  276,    0,   60,  279,    0,    0,  113,
    0,  280,    0,    0,    0,    0,    0,    0,    0,  296,
    0,  282,    0,    0,   78,   82,    0,  -53,    0,  287,
  -42,  288,    0,  -20,   84,  310,  113,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  352,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   11,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -27,    0,    0,    0,    0,    0,
    0,  -19,    0,    0,    0,    0,   57,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    5,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   92,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -7,   35,    0,    0,   64,   71,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  302,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   85,   19,    0,  -22,   27,   52,   61,   44,  -17,
    0,    0,  308,  -62,    0,   76,  212,    0,   79,    0,
    0,    0,   34,    0,    0,    0,    0,    0,    0,    0,
    0,  165,    0,    0,   -4,   72,
};
final static int YYTABLESIZE=458;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         88,
   41,   53,   88,   74,    1,  102,  138,   57,  139,  156,
   38,   71,   67,   76,   76,   76,  116,   76,  119,   76,
   42,   72,  125,   72,  126,   72,   27,  147,   43,   76,
   70,   76,   76,   70,   76,   70,   37,   70,   44,   72,
   72,  125,   72,  126,   75,  146,   37,   76,  180,    7,
   21,   70,   70,   66,   70,  161,   43,  124,   22,   51,
    9,   38,  188,   23,   79,    1,   76,   24,   84,   19,
   39,   38,   25,   37,   26,   71,  143,   71,    5,   71,
   39,  193,  140,  105,   90,   90,   90,   90,   40,  200,
   20,   76,   83,   71,   71,   49,   71,   68,   38,  118,
   47,  138,  163,  139,   67,   61,  194,   39,  138,   51,
  139,   66,  111,   68,  108,  109,   68,  112,   68,    6,
  165,  166,   54,   67,    7,   67,   89,   95,   96,   59,
   66,    7,   66,  175,  176,    9,  178,  179,  181,    8,
  127,   61,    9,   69,   61,  128,   58,   90,  125,   62,
  126,   76,   99,  153,   76,   90,  100,   61,   90,   90,
   90,   90,   61,   21,  130,  103,  196,  204,  110,  197,
  113,   22,   18,  114,   90,   90,   23,   40,   76,  115,
   24,  117,   77,   61,    7,   25,  120,   26,   78,  157,
   19,   90,   53,  199,  218,    9,   61,  164,  167,  168,
  122,  123,  129,  132,  133,  134,  135,  131,  145,  125,
   94,  126,  148,  149,  151,  152,  169,  170,   85,   72,
   86,   85,  154,   86,  155,  101,   52,   73,   56,   76,
   76,   76,   76,   87,   76,   76,   87,   72,   72,   72,
   72,  158,   72,   72,   90,  159,  160,   18,  162,   70,
   70,   70,   70,   43,   70,   70,   41,  173,  182,    7,
  125,  189,  126,  125,   41,  126,   43,   46,   40,   41,
    9,   41,   41,   41,   43,   41,  171,   41,   41,   43,
   41,   37,  174,   43,  172,  177,  214,   43,   43,  185,
   43,   71,   71,   71,   71,   40,   71,   71,  132,  133,
  134,  135,  184,  136,  137,  132,  133,  134,  135,  186,
  136,  137,  187,   68,   68,   68,   68,  191,   68,   68,
   67,   67,   67,   67,  195,   67,   67,   66,   66,   66,
   66,  198,   66,   66,  201,   34,  202,  203,  205,  206,
  207,  208,  210,   22,   18,  213,  215,    7,   23,  216,
  217,    5,   24,   21,   82,   35,    7,   25,    9,   26,
   60,   22,   51,   54,   50,   21,   23,    9,   64,  183,
   24,   19,  211,   22,    0,   25,   22,   26,   23,    0,
    0,   23,   24,   21,    0,   24,   60,   25,   65,   26,
   25,   22,   26,  106,    0,    0,   23,    0,    0,    0,
   24,   22,    0,   21,  104,   25,   23,   26,    0,    0,
   24,   22,    0,   21,  107,   25,   23,   26,    0,    0,
   24,   22,   77,  106,    0,   25,   23,   26,    0,    0,
   24,   22,    0,   21,  121,   25,   23,   26,    0,    0,
   24,   22,    0,    0,  150,   25,   23,   26,    0,    0,
   24,    0,    0,    0,    0,   25,    0,   26,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         45,
    0,   40,   45,   40,    0,   41,   60,   40,   62,   41,
    0,   40,   35,   41,   42,   43,   79,   45,   81,   47,
  256,   41,   43,   43,   45,   45,    8,   41,  264,   44,
   59,   59,   60,   41,   62,   43,   10,   45,  274,   59,
   60,   43,   62,   45,   59,   59,   20,   44,  256,  268,
  256,   59,   60,   35,   62,  118,  264,   59,  264,  265,
  279,   10,   59,  269,   46,  264,   44,  273,   50,  274,
   10,   20,  278,   47,  280,   41,   99,   43,   59,   45,
   20,   59,   41,   65,   51,   52,   53,   54,   10,   41,
    6,   44,   49,   59,   60,   40,   62,   41,   47,   81,
   16,   60,  120,   62,   41,   27,   59,   47,   60,  265,
   62,   41,  256,   35,   71,   72,   60,   74,   62,  256,
  125,  126,   40,   60,  268,   62,   51,   52,   53,   40,
   60,  268,   62,  151,  152,  279,  154,  155,  156,  276,
   42,   63,  279,  277,   66,   47,   59,  114,   43,   59,
   45,   44,  270,  110,   44,  122,   41,   79,  125,  126,
  127,  128,   84,  256,   59,  264,   59,  190,   40,   59,
  264,  264,  256,   40,  141,  142,  269,   99,   44,   58,
  273,   59,  275,  105,  268,  278,   41,  280,  281,  114,
  274,  158,   40,   59,  217,  279,  118,  122,  127,  128,
   40,  266,   59,  257,  258,  259,  260,   41,   59,   43,
  256,   45,   59,  265,   41,   41,  141,  142,  264,  256,
  266,  264,   41,  266,  256,  261,  265,  264,  261,  257,
  258,  259,  260,  279,  262,  263,  279,  257,  258,  259,
  260,   40,  262,  263,  211,   59,  277,  256,   59,  257,
  258,  259,  260,  264,  262,  263,  256,   59,   41,  268,
   43,   41,   45,   43,  264,   45,  256,  276,  190,  269,
  279,  271,  272,  273,  264,  275,  271,  277,  278,  269,
  280,  277,  266,  273,  272,  264,  211,  277,  278,   59,
  280,  257,  258,  259,  260,  217,  262,  263,  257,  258,
  259,  260,  277,  262,  263,  257,  258,  259,  260,   59,
  262,  263,  277,  257,  258,  259,  260,   59,  262,  263,
  257,  258,  259,  260,   41,  262,  263,  257,  258,  259,
  260,   59,  262,  263,   59,  256,  277,   59,   59,   44,
   59,  264,  261,  264,  256,   59,   59,  268,  269,  266,
   41,    0,  273,  256,   47,  276,  268,  278,  279,  280,
   59,  264,  265,  272,  276,  256,  269,  279,  256,  158,
  273,  274,  208,  264,   -1,  278,  264,  280,  269,   -1,
   -1,  269,  273,  256,   -1,  273,  277,  278,  276,  280,
  278,  264,  280,  256,   -1,   -1,  269,   -1,   -1,   -1,
  273,  264,   -1,  256,  277,  278,  269,  280,   -1,   -1,
  273,  264,   -1,  256,  277,  278,  269,  280,   -1,   -1,
  273,  264,  275,  256,   -1,  278,  269,  280,   -1,   -1,
  273,  264,   -1,  256,  277,  278,  269,  280,   -1,   -1,
  273,  264,   -1,   -1,  277,  278,  269,  280,   -1,   -1,
  273,   -1,   -1,   -1,   -1,  278,   -1,  280,
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
"SENTENCIA_EJECUTABLE : LLAMADO_FUNCION ';'",
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

//#line 300 "gramatica.y"

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
        if(token  == 261 || token == 264 || token == 266) 
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
    

//#line 728 "Parser.java"
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
//#line 30 "gramatica.y"
{ambitoActual.add(0, val_peek(1).sval); addAtributoLexema(val_peek(1).sval,"USO","Nombre de Programa");}
break;
case 5:
//#line 33 "gramatica.y"
{yyerror("Bloque principal no especificado.");}
break;
case 6:
//#line 34 "gramatica.y"
{yyerror("BEGIN del bloque principal no especificado.");}
break;
case 7:
//#line 35 "gramatica.y"
{yyerror("END del bloque principal no especificado.");}
break;
case 8:
//#line 36 "gramatica.y"
{yyerror("Falta el nombre del programa.");}
break;
case 15:
//#line 47 "gramatica.y"
{addEstructura("Declaracion de varables");
                                                                        addTipoVariables();
                                                                        }
break;
case 17:
//#line 53 "gramatica.y"
{yyerror("Falta el tipo de la funcion.");}
break;
case 18:
//#line 54 "gramatica.y"
{yyerror("Falta la palabra reservada FUNC.");}
break;
case 19:
//#line 55 "gramatica.y"
{yyerror("Falta el primer parentesis en la asignacion de la funcion.");}
break;
case 20:
//#line 56 "gramatica.y"
{yyerror("Falta el tipo del parametro en la asignacion de la funcion.");}
break;
case 21:
//#line 57 "gramatica.y"
{yyerror("Falta el segundo parentesis en la asignacion de la funcion.");}
break;
case 22:
//#line 58 "gramatica.y"
{yyerror("Falta el listado de variables en la asignacion de la funcion.");}
break;
case 23:
//#line 62 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 24:
//#line 64 "gramatica.y"
{addEstructura("Declaracion de funcion"); ambitoActual.remove(0);}
break;
case 27:
//#line 69 "gramatica.y"
{
                                    Terceto bifurcacionIncondicional = backpatching.pop();
                                    /* bifurcacionIncondicional.setOperando1("[" + tercetos.size() + "]");*/
                                    bifurcacionIncondicional.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                    
                                }
break;
case 28:
//#line 75 "gramatica.y"
{
                                    Terceto bifurcacionIncondicional = backpatching.pop();
                                    /* bifurcacionIncondicional.setOperando1("[" + tercetos.size() + "]");*/
                                    bifurcacionIncondicional.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");
                                }
break;
case 29:
//#line 82 "gramatica.y"
{checkRetornoFuncion(val_peek(1).sval); addTerceto(new Terceto("RETURN_FUNC", val_peek(1).sval, null, getTipo(val_peek(1).sval)));}
break;
case 30:
//#line 85 "gramatica.y"
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
case 44:
//#line 141 "gramatica.y"
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
//#line 157 "gramatica.y"
{addEstructura("Sentencia PRINT"); addTerceto(new Terceto("PRINT", val_peek(2).sval + "%")); addCadena(val_peek(2).sval + "%");}
break;
case 46:
//#line 158 "gramatica.y"
{addEstructura("BREAK"); addTerceto(new Terceto("BREAK"));}
break;
case 49:
//#line 161 "gramatica.y"
{addEstructura("Llamado a funcion");}
break;
case 52:
//#line 167 "gramatica.y"
{addEstructura("Sentencia IF");
                                    addTerceto(new Terceto("END_IF"));}
break;
case 53:
//#line 171 "gramatica.y"
{ Terceto terceto = new Terceto("BF", val_peek(1).sval, null);
                                                    addTerceto(terceto);
                                                    backpatching.push(terceto); }
break;
case 55:
//#line 178 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop(); 
                                    
                                    /* tercetoIncompleto.setOperando2("[" + (tercetos.size() + 1) + "]");*/
                                    tercetoIncompleto.setOperando2("[" + (tercetos.get(getIdentificadorFuncionActual()).size() + 1) + "]");
                                    Terceto bifurcacionIncondicional = new Terceto("BI", null);
                                    addTerceto(bifurcacionIncondicional);
                                    backpatching.push(bifurcacionIncondicional);}
break;
case 56:
//#line 186 "gramatica.y"
{Terceto tercetoIncompleto = backpatching.pop();
                                    /* tercetoIncompleto.setOperando1("[" + tercetos.size() + "]");}*/
                                    tercetoIncompleto.setOperando1("[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]");}
break;
case 57:
//#line 192 "gramatica.y"
{
                                            String id = getAmbitoIdentificador(val_peek(2).sval); 
                                            addTerceto(new Terceto(":=", id, val_peek(0).sval)); }
break;
case 58:
//#line 195 "gramatica.y"
{ 
                                            Terceto tercetoIncompleto = new Terceto("BF", val_peek(0).sval, null); 
                                            addTerceto(tercetoIncompleto); backpatching.push(tercetoIncompleto); }
break;
case 59:
//#line 198 "gramatica.y"
{ 
                                        addEstructura("Sentencia REPEAT");
                                        Terceto bifurcacionFalse = backpatching.pop();
                                        Terceto destinoBifurcacionIncondicional = backpatching.pop();
                                        String id = getAmbitoIdentificador(val_peek(10).sval);
                                        /* addTerceto(new Terceto("+", id, $11.sval));*/
                                        addTercetoAritmetica("+",id,val_peek(2).sval);
                                        addTerceto(new Terceto(":=", id, getReferenciaUltimaInstruccion().sval));
                                        String referenciaBI = "[" + tercetos.get(getIdentificadorFuncionActual()).indexOf(destinoBifurcacionIncondicional) + "]";
                                        addTerceto(new Terceto("BI", referenciaBI));
                                        /* String referenciaBF = "[" + tercetos.size() + "]";*/
                                        String referenciaBF = "[" + tercetos.get(getIdentificadorFuncionActual()).size() + "]";
                                        bifurcacionFalse.setOperando2(referenciaBF);
                                        addTerceto(new Terceto("END_REPEAT"));
                                        }
break;
case 60:
//#line 217 "gramatica.y"
{Terceto terceto = new Terceto(val_peek(1).sval, getAmbitoIdentificador(val_peek(2).sval), val_peek(0).sval); addTerceto(terceto); backpatching.push(terceto); yyval = getReferenciaUltimaInstruccion();}
break;
case 61:
//#line 220 "gramatica.y"
{yyerror("Falta el identificador de la asignación.");}
break;
case 62:
//#line 221 "gramatica.y"
{yyerror("Falta la expresión en la asignación.");}
break;
case 63:
//#line 224 "gramatica.y"
{yyerror("Falta el primer paréntesis del PRINT.");}
break;
case 64:
//#line 225 "gramatica.y"
{yyerror("Falta la cadena del PRINT.");}
break;
case 65:
//#line 226 "gramatica.y"
{yyerror("Falta el último paréntesis del PRINT.");}
break;
case 66:
//#line 229 "gramatica.y"
{ addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion();}
break;
case 67:
//#line 230 "gramatica.y"
{addTerceto(new Terceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval)); yyval = getReferenciaUltimaInstruccion();}
break;
case 68:
//#line 231 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 69:
//#line 234 "gramatica.y"
{ addTerceto(new Terceto("CONV", val_peek(1).sval, null, "SINGLE")); yyval = getReferenciaUltimaInstruccion();}
break;
case 70:
//#line 237 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("+", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("+",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 71:
//#line 241 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("-", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("-",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 72:
//#line 245 "gramatica.y"
{ yyval = val_peek(0); }
break;
case 73:
//#line 248 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("*", $1.sval, $3.sval)); */
                                    addTercetoAritmetica("*",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion(); }
break;
case 74:
//#line 252 "gramatica.y"
{ 
                                    /*addTerceto(new Terceto("/", $1.sval, $3.sval));*/
                                    addTercetoAritmetica("/",val_peek(2).sval,val_peek(0).sval);
                                    yyval = getReferenciaUltimaInstruccion();}
break;
case 75:
//#line 256 "gramatica.y"
{yyval = val_peek(0);}
break;
case 76:
//#line 259 "gramatica.y"
{ yyval = new ParserVal(getAmbitoIdentificador(val_peek(0).sval));}
break;
case 77:
//#line 260 "gramatica.y"
{if (!checkRango(val_peek(0).sval)){
                                            yyerror("Constante fuera de rango");
                                        }
                                        yyval = new ParserVal(val_peek(0).sval); }
break;
case 78:
//#line 264 "gramatica.y"
{lexico.cambiarSimboloConstante(val_peek(0).sval);
				                            yyval = new ParserVal("-" + val_peek(0).sval);
                                        }
break;
case 79:
//#line 267 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval);}
break;
case 80:
//#line 268 "gramatica.y"
{addEstructura("Llamado a funcion como operando"); yyval = val_peek(0);}
break;
case 81:
//#line 271 "gramatica.y"
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
case 82:
//#line 282 "gramatica.y"
{yyval = new ParserVal(">");}
break;
case 83:
//#line 283 "gramatica.y"
{yyval = new ParserVal("<");}
break;
case 84:
//#line 284 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 85:
//#line 285 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 86:
//#line 286 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval);}
break;
case 87:
//#line 287 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 88:
//#line 290 "gramatica.y"
{ yyval = new ParserVal(val_peek(0).sval); }
break;
case 89:
//#line 291 "gramatica.y"
{yyval = new ParserVal(val_peek(0).sval); }
break;
case 90:
//#line 294 "gramatica.y"
{tipo = "SINGLE"; yyval = new ParserVal("SINGLE");}
break;
case 91:
//#line 295 "gramatica.y"
{tipo = "INT";  yyval = new ParserVal("INT");}
break;
//#line 1255 "Parser.java"
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
