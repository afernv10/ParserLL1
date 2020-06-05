package lexLL1;

public class Yytoken{
	public int m_index; // position among other similar tokens in file
	public String m_text; // lexeme
	public String m_tktype; // token type
	public int m_line;
	public int m_column;
	
	Yytoken (int index, String text, String tktype, int line, int column){
		m_index=index;
		m_text=text;
		m_tktype=tktype;
		m_line=line;
		m_column=column;
	}

	public String toString() {
		return ("Text: " + m_text + 
				"\n Type: " + m_tktype+
				"\n index: " + m_index +
				"\n line: " + m_line +
				"\n column: " + m_column);
	}

	// Error message when token does not match a reference (refStrToken)
	public void errorMatch(String refStrToken) {
		System.out.println("\t[Parser] Encontrado token de tipo "+m_tktype+"(line: "+m_line+", column: "+m_column+")\n\tSe esperaba "+ refStrToken);
	}
	
	// Error message without saying anything about the expected token
	public void error(String msg) {
		System.out.println("\t[Parser] Error: "+msg+" de tipo: "+m_tktype+" (line: "+m_line+", column: "+m_column+")\n\tSe esperaba otro.");
	}
}
