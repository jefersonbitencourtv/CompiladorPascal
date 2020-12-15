
public class Token {

	Tipo 	tipo;
	String  lexema;
	int 	linha;
	int 	coluna;
	
	public Token(Tipo tipo, String lexema, int linha, int coluna) {
		this.tipo = tipo;
		this.lexema = lexema;
		this.linha = linha;
		this.coluna = coluna;
	}

	public String toString() {
		return "Tipo: " + tipo + ",Lex: " + lexema + ", Linha: " + linha + ", Coluna: " + coluna;
	}
}
