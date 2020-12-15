import java.io.IOException;

public abstract class Parser {
	/**
	 * Tabela de Simbolos
	 */
	TS ts;
	/**
	 * Analisador Lexico
	 */
	Lexico lexer;
	/**
	 * Token corrente que esta sendo analisado
	 */
	Token t;
	
	public Parser(String arquivo) throws IOException {
		ts = new TS();
		lexer = new Lexico(arquivo);
	}

	/**
	 * O metodo deve ser implementado pelo parser concreto
	 */
	public abstract void parse() throws IOException;
	
	/**
	 * Obtem o proximo token
	 * @throws IOException
	 */
	public void buscaToken() throws IOException {
		t = lexer.buscaToken();
	}
	
	public boolean erro(String erro) {
		System.out.println(erro + ": Linha " + t.linha + ", Coluna " + t.coluna);
		System.exit(0);
		return false;
	}
}
