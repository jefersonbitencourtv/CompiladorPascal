import java.io.IOException;
import java.util.ArrayList;


public class LexicoMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Lexico lexico = new Lexico("src/teste1.lpd");
		ArrayList<Token> lt = new ArrayList<Token>();
		
		lt = lexico.analisa("src/teste1.lpd");		
		//imprime o numero de token
		System.out.println("Numero de tokens: " + lt.size());
		
		//Percorre lista de tokens imprimindo-os
		lt.forEach(token -> System.out.println(token.toString()));	
	}
}