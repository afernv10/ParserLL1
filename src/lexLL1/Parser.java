package lexLL1;

import java.io.*;

public class Parser{
	// Properties: current token and lexical analyser that will check the token plus a counter of statements
	private Yytoken currentToken=null;
	private static Lexer myLexer=null;  
	int stCounter=0;
	int recoveringBefore = 0;
	
	// Constructor: set my_lexer value
	Parser (Lexer lex){
		myLexer=lex;
	}
	
	// Get type of current token
	private String getCurrentTkType(){
		return currentToken.m_tktype;
	}
	
	private String getCurrentLexeme() {
		return currentToken.m_text;
	}
	
	// Get next token
	private void getNextToken() {
		Yytoken prevToken=currentToken;
		try {
			currentToken=myLexer.yylex(); 
			if (currentToken==null){ //Found end of file
				System.out.println("\t[Parser] End of file");
				currentToken= new Yytoken(0,"End of File","EOF",prevToken.m_line,prevToken.m_column);
			}	
		}
		catch (IOException e){
			currentToken.error("Error getting token next to ");
		}
		prevToken=null;
	}
	
	// BEGINNING OF RECURSIVE-DESCENDENT PARSING
	
	//Inicio Producciones
	/*
	S-->T;
	T-->FT'
	T'-->opFT'| epsilon
	F-->(T)|num
	
	E --> E+T;| E-T; | T;
	T --> T*F | T/F | F
	F --> (E) | int
	==============================
	Quitamos recursividad a izq y factorizamos:
	E --> TE';
	E'--> +TE'| -TE' | epsilon
	T --> FT'
	T'--> *FT' | /FT' | epsilon
	F --> (E) | int
	
	Esto es LL1
	*/
	
	// Añado S como entrada para el manejo de ; (final statement)
	public void S() { // S -> E;
		E();
		match("SEMICOLON");  // ';': end of statement 
	}
	
	public void E() { // TE';
		stCounter++;
		//System.out.println("\t=================================");
		//System.out.println("\t[Parser] NEW STATEMENT [#"+stCounter+"]");
		T();
		Edot(); 
	}
	
	private void Edot() { //E'--> +TE' | -TE' | epsilon
		if (getCurrentTkType().equals("ADD")) {
			match("ADD"); 
			T();
			Edot();
		} else if(getCurrentTkType().equals("SUB")) {
			match("SUB"); 
			T();
			Edot();
		} else{ // epsilon
		}
	}

	public void T() { // T --> FT'
		F();
		Tdot();		
	}
	
	public void Tdot() { // T'--> *FT' | /FT' | epsilon
		if (getCurrentTkType().equals("TIMES")) {
			match("TIMES"); 
			F();
			Tdot();
		} else if(getCurrentTkType().equals("DIV")) {
			match("DIV"); 
			F();
			Tdot();
		} else { // epsilon
		}
	}
	
	public void F() {//F --> (E) | int
		if (getCurrentTkType().equals("LEFT_BRACKET")) {
			match("LEFT_BRACKET");
			E();	// No acabará en ;
			//System.out.println("holis");
			match("RIGHT_BRACKET");
		}
		else if (getCurrentTkType().contentEquals("INTEGER")) {
			match("INTEGER");
		} else {
			match("OTROTOKEN");
			//System.out.println("HOLAAAAAAAAAAAa");
		}
	}
	// If current token matches the token type in the grammar, get next token. Otherwise, error
	public void match(String refTkType) {
		//System.out.println("refTkType: " + refTkType);
		if (getCurrentTkType().equals(refTkType)) {
			if(refTkType.equals("SEMICOLON") && recoveringBefore ==1) {
				System.out.println("\t\t[Parser] Exit recovering: with "+getCurrentTkType());
			} else {
				System.out.println("\t[Parser] ("+getCurrentTkType()+ ", "+ getCurrentLexeme() +"): Ok");
			}
			recoveringBefore = 0;
			getNextToken();
		}
		else if (getCurrentTkType().equals("EOF")) {
			// Do nothing in case of end of file
		}else if( getCurrentTkType().equals("SEMICOLON")&& recoveringBefore==1) {
			// Do nothing in case of Semicolon in recovery
		}
		else {
			if(refTkType=="OTROTOKEN" || refTkType=="SEMICOLON") {
				// Puede esperar varias opciones
				currentToken.error("Encontrado token");
				// Caso de varios SEMICOLON seguidos:
				if(refTkType.equals("OTROTOKEN") && getCurrentTkType().equals("SEMICOLON")) recoveringBefore=1;
			} else {
				currentToken.errorMatch(refTkType); // Current token does not match production in grammar
			}
			
			// 1. Ignore following tokens until semicolon (i.e. end of statement) or EOF is found
			while ((getCurrentTkType().contentEquals("SEMICOLON")==false)&&(getCurrentTkType().contentEquals("EOF")==false)) {
					System.out.println("\t\t[Parser] Recovering from error: skipping "+getCurrentTkType());
					getNextToken();
					recoveringBefore = 1;
			}
		}
	}
		

	
	public static void main(String[] argv) {
		  if (argv.length == 0) {
		      System.out.println("Syntax : java Parser  <inputfile)>");
		    }
		  else {
		 
		 try {
			java.io.FileInputStream stream = new java.io.FileInputStream(argv[0]);
		    java.io.Reader reader = new java.io.InputStreamReader(stream);
		    
			Lexer lex= new Lexer(reader);
			
			Parser recursive_descendent_parser=new Parser(lex);
			
			recursive_descendent_parser.getNextToken();
			do { 
			
				recursive_descendent_parser.S();
			}
			while (recursive_descendent_parser.getCurrentTkType().equals("EOF")==false);
		}
		catch(IOException x) {
			System.out.println("\t[Parser] Error while reading "+x.toString()+(myLexer.yytext()));
		}
		  }
	}
	
}

