/* Generated By:JavaCC: Do not edit this line. SPARQLParser11Constants.java */
/*
 * (c) Copyright 2004, 2005, 2006, 2007, 2008, 2009 Hewlett-Packard Development Company, LP
 * (c) Copyright 2010 Talis Systems Ltd
 * All rights reserved.
 */

package com.hp.hpl.jena.sparql.lang.sparql_11 ;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface SPARQLParser11Constants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int WS = 6;
  /** RegularExpression Id. */
  int SINGLE_LINE_COMMENT = 7;
  /** RegularExpression Id. */
  int IRIref = 8;
  /** RegularExpression Id. */
  int PNAME_NS = 9;
  /** RegularExpression Id. */
  int PNAME_LN = 10;
  /** RegularExpression Id. */
  int BLANK_NODE_LABEL = 11;
  /** RegularExpression Id. */
  int VAR1 = 12;
  /** RegularExpression Id. */
  int VAR2 = 13;
  /** RegularExpression Id. */
  int LANGTAG = 14;
  /** RegularExpression Id. */
  int A2Z = 15;
  /** RegularExpression Id. */
  int A2ZN = 16;
  /** RegularExpression Id. */
  int KW_A = 17;
  /** RegularExpression Id. */
  int BASE = 18;
  /** RegularExpression Id. */
  int PREFIX = 19;
  /** RegularExpression Id. */
  int SELECT = 20;
  /** RegularExpression Id. */
  int DISTINCT = 21;
  /** RegularExpression Id. */
  int REDUCED = 22;
  /** RegularExpression Id. */
  int DESCRIBE = 23;
  /** RegularExpression Id. */
  int CONSTRUCT = 24;
  /** RegularExpression Id. */
  int ASK = 25;
  /** RegularExpression Id. */
  int LIMIT = 26;
  /** RegularExpression Id. */
  int OFFSET = 27;
  /** RegularExpression Id. */
  int ORDER = 28;
  /** RegularExpression Id. */
  int BY = 29;
  /** RegularExpression Id. */
  int BINDINGS = 30;
  /** RegularExpression Id. */
  int UNDEF = 31;
  /** RegularExpression Id. */
  int ASC = 32;
  /** RegularExpression Id. */
  int DESC = 33;
  /** RegularExpression Id. */
  int NAMED = 34;
  /** RegularExpression Id. */
  int FROM = 35;
  /** RegularExpression Id. */
  int WHERE = 36;
  /** RegularExpression Id. */
  int AND = 37;
  /** RegularExpression Id. */
  int GRAPH = 38;
  /** RegularExpression Id. */
  int OPTIONAL = 39;
  /** RegularExpression Id. */
  int UNION = 40;
  /** RegularExpression Id. */
  int SERVICE = 41;
  /** RegularExpression Id. */
  int EXISTS = 42;
  /** RegularExpression Id. */
  int NOT = 43;
  /** RegularExpression Id. */
  int NOTEXISTS = 44;
  /** RegularExpression Id. */
  int MINUS_P = 45;
  /** RegularExpression Id. */
  int AS = 46;
  /** RegularExpression Id. */
  int GROUP = 47;
  /** RegularExpression Id. */
  int HAVING = 48;
  /** RegularExpression Id. */
  int SEPARATOR = 49;
  /** RegularExpression Id. */
  int AGG = 50;
  /** RegularExpression Id. */
  int COUNT = 51;
  /** RegularExpression Id. */
  int MIN = 52;
  /** RegularExpression Id. */
  int MAX = 53;
  /** RegularExpression Id. */
  int SUM = 54;
  /** RegularExpression Id. */
  int AVG = 55;
  /** RegularExpression Id. */
  int STDDEV = 56;
  /** RegularExpression Id. */
  int SAMPLE = 57;
  /** RegularExpression Id. */
  int GROUP_CONCAT = 58;
  /** RegularExpression Id. */
  int FILTER = 59;
  /** RegularExpression Id. */
  int BOUND = 60;
  /** RegularExpression Id. */
  int COALESCE = 61;
  /** RegularExpression Id. */
  int IN = 62;
  /** RegularExpression Id. */
  int NOT_IN = 63;
  /** RegularExpression Id. */
  int IF = 64;
  /** RegularExpression Id. */
  int BNODE = 65;
  /** RegularExpression Id. */
  int IRI = 66;
  /** RegularExpression Id. */
  int URI = 67;
  /** RegularExpression Id. */
  int CAST = 68;
  /** RegularExpression Id. */
  int STR = 69;
  /** RegularExpression Id. */
  int STRLANG = 70;
  /** RegularExpression Id. */
  int STRDT = 71;
  /** RegularExpression Id. */
  int DTYPE = 72;
  /** RegularExpression Id. */
  int LANG = 73;
  /** RegularExpression Id. */
  int LANGMATCHES = 74;
  /** RegularExpression Id. */
  int IS_URI = 75;
  /** RegularExpression Id. */
  int IS_IRI = 76;
  /** RegularExpression Id. */
  int IS_BLANK = 77;
  /** RegularExpression Id. */
  int IS_LITERAL = 78;
  /** RegularExpression Id. */
  int REGEX = 79;
  /** RegularExpression Id. */
  int SAME_TERM = 80;
  /** RegularExpression Id. */
  int TRUE = 81;
  /** RegularExpression Id. */
  int FALSE = 82;
  /** RegularExpression Id. */
  int INSERT = 83;
  /** RegularExpression Id. */
  int DELETE = 84;
  /** RegularExpression Id. */
  int DATA = 85;
  /** RegularExpression Id. */
  int LOAD = 86;
  /** RegularExpression Id. */
  int CLEAR = 87;
  /** RegularExpression Id. */
  int CREATE = 88;
  /** RegularExpression Id. */
  int SILENT = 89;
  /** RegularExpression Id. */
  int DROP = 90;
  /** RegularExpression Id. */
  int INTO = 91;
  /** RegularExpression Id. */
  int DFT = 92;
  /** RegularExpression Id. */
  int WITH = 93;
  /** RegularExpression Id. */
  int DIGITS = 94;
  /** RegularExpression Id. */
  int INTEGER = 95;
  /** RegularExpression Id. */
  int DECIMAL = 96;
  /** RegularExpression Id. */
  int DOUBLE = 97;
  /** RegularExpression Id. */
  int INTEGER_POSITIVE = 98;
  /** RegularExpression Id. */
  int DECIMAL_POSITIVE = 99;
  /** RegularExpression Id. */
  int DOUBLE_POSITIVE = 100;
  /** RegularExpression Id. */
  int INTEGER_NEGATIVE = 101;
  /** RegularExpression Id. */
  int DECIMAL_NEGATIVE = 102;
  /** RegularExpression Id. */
  int DOUBLE_NEGATIVE = 103;
  /** RegularExpression Id. */
  int EXPONENT = 104;
  /** RegularExpression Id. */
  int QUOTE_3D = 105;
  /** RegularExpression Id. */
  int QUOTE_3S = 106;
  /** RegularExpression Id. */
  int ECHAR = 107;
  /** RegularExpression Id. */
  int STRING_LITERAL1 = 108;
  /** RegularExpression Id. */
  int STRING_LITERAL2 = 109;
  /** RegularExpression Id. */
  int STRING_LITERAL_LONG1 = 110;
  /** RegularExpression Id. */
  int STRING_LITERAL_LONG2 = 111;
  /** RegularExpression Id. */
  int LPAREN = 112;
  /** RegularExpression Id. */
  int RPAREN = 113;
  /** RegularExpression Id. */
  int NIL = 114;
  /** RegularExpression Id. */
  int LBRACE = 115;
  /** RegularExpression Id. */
  int RBRACE = 116;
  /** RegularExpression Id. */
  int LBRACKET = 117;
  /** RegularExpression Id. */
  int RBRACKET = 118;
  /** RegularExpression Id. */
  int ANON = 119;
  /** RegularExpression Id. */
  int SEMICOLON = 120;
  /** RegularExpression Id. */
  int COMMA = 121;
  /** RegularExpression Id. */
  int DOT = 122;
  /** RegularExpression Id. */
  int EQ = 123;
  /** RegularExpression Id. */
  int NE = 124;
  /** RegularExpression Id. */
  int GT = 125;
  /** RegularExpression Id. */
  int LT = 126;
  /** RegularExpression Id. */
  int LE = 127;
  /** RegularExpression Id. */
  int GE = 128;
  /** RegularExpression Id. */
  int BANG = 129;
  /** RegularExpression Id. */
  int TILDE = 130;
  /** RegularExpression Id. */
  int COLON = 131;
  /** RegularExpression Id. */
  int SC_OR = 132;
  /** RegularExpression Id. */
  int SC_AND = 133;
  /** RegularExpression Id. */
  int PLUS = 134;
  /** RegularExpression Id. */
  int MINUS = 135;
  /** RegularExpression Id. */
  int STAR = 136;
  /** RegularExpression Id. */
  int SLASH = 137;
  /** RegularExpression Id. */
  int DATATYPE = 138;
  /** RegularExpression Id. */
  int AT = 139;
  /** RegularExpression Id. */
  int VBAR = 140;
  /** RegularExpression Id. */
  int CARAT = 141;
  /** RegularExpression Id. */
  int FPATH = 142;
  /** RegularExpression Id. */
  int RPATH = 143;
  /** RegularExpression Id. */
  int QMARK = 144;
  /** RegularExpression Id. */
  int PN_CHARS_BASE = 145;
  /** RegularExpression Id. */
  int PN_CHARS_U = 146;
  /** RegularExpression Id. */
  int PN_CHARS = 147;
  /** RegularExpression Id. */
  int PN_PREFIX = 148;
  /** RegularExpression Id. */
  int PN_LOCAL = 149;
  /** RegularExpression Id. */
  int VARNAME = 150;
  /** RegularExpression Id. */
  int UNKNOWN = 151;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "<WS>",
    "<SINGLE_LINE_COMMENT>",
    "<IRIref>",
    "<PNAME_NS>",
    "<PNAME_LN>",
    "<BLANK_NODE_LABEL>",
    "<VAR1>",
    "<VAR2>",
    "<LANGTAG>",
    "<A2Z>",
    "<A2ZN>",
    "\"a\"",
    "\"base\"",
    "\"prefix\"",
    "\"select\"",
    "\"distinct\"",
    "\"reduced\"",
    "\"describe\"",
    "\"construct\"",
    "\"ask\"",
    "\"limit\"",
    "\"offset\"",
    "\"order\"",
    "\"by\"",
    "\"bindings\"",
    "\"undef\"",
    "\"asc\"",
    "\"desc\"",
    "\"named\"",
    "\"from\"",
    "\"where\"",
    "\"and\"",
    "\"graph\"",
    "\"optional\"",
    "\"union\"",
    "\"service\"",
    "\"exists\"",
    "\"not\"",
    "<NOTEXISTS>",
    "\"minus\"",
    "\"as\"",
    "\"group\"",
    "\"having\"",
    "\"separator\"",
    "\"agg\"",
    "\"count\"",
    "\"min\"",
    "\"max\"",
    "\"sum\"",
    "\"avg\"",
    "\"stdev\"",
    "\"sample\"",
    "\"group_concat\"",
    "\"filter\"",
    "\"bound\"",
    "\"coalesce\"",
    "\"in\"",
    "<NOT_IN>",
    "\"if\"",
    "\"bnode\"",
    "\"iri\"",
    "\"uri\"",
    "\"cast\"",
    "\"str\"",
    "\"strlang\"",
    "\"strdt\"",
    "\"datatype\"",
    "\"lang\"",
    "\"langmatches\"",
    "\"isURI\"",
    "\"isIRI\"",
    "\"isBlank\"",
    "\"isLiteral\"",
    "\"regex\"",
    "\"sameTerm\"",
    "\"true\"",
    "\"false\"",
    "\"insert\"",
    "\"delete\"",
    "\"data\"",
    "\"load\"",
    "\"clear\"",
    "\"create\"",
    "\"silent\"",
    "\"drop\"",
    "\"into\"",
    "\"default\"",
    "\"with\"",
    "<DIGITS>",
    "<INTEGER>",
    "<DECIMAL>",
    "<DOUBLE>",
    "<INTEGER_POSITIVE>",
    "<DECIMAL_POSITIVE>",
    "<DOUBLE_POSITIVE>",
    "<INTEGER_NEGATIVE>",
    "<DECIMAL_NEGATIVE>",
    "<DOUBLE_NEGATIVE>",
    "<EXPONENT>",
    "\"\\\"\\\"\\\"\"",
    "\"\\\'\\\'\\\'\"",
    "<ECHAR>",
    "<STRING_LITERAL1>",
    "<STRING_LITERAL2>",
    "<STRING_LITERAL_LONG1>",
    "<STRING_LITERAL_LONG2>",
    "\"(\"",
    "\")\"",
    "<NIL>",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "<ANON>",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\"!=\"",
    "\">\"",
    "\"<\"",
    "\"<=\"",
    "\">=\"",
    "\"!\"",
    "\"~\"",
    "\":\"",
    "\"||\"",
    "\"&&\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"^^\"",
    "\"@\"",
    "\"|\"",
    "\"^\"",
    "\"->\"",
    "\"<-\"",
    "\"?\"",
    "<PN_CHARS_BASE>",
    "<PN_CHARS_U>",
    "<PN_CHARS>",
    "<PN_PREFIX>",
    "<PN_LOCAL>",
    "<VARNAME>",
    "<UNKNOWN>",
  };

}
