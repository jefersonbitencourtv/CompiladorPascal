import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Lexico {
	
	//Stream de leitura do arquivo fonte
	PushbackReader r;
	//Lista de Tokens
	ArrayList<Token> lt = new ArrayList<Token>();
	//Armazena o codigo do caractere lido
	int intch;	
	char ch;
	int linha = 1;
	int coluna = 1;
	
	public Lexico(String arquivo) throws UnsupportedEncodingException, FileNotFoundException {
		//PushbackReader serÃ¡ usado para devolver caracter ao stream
				r = new PushbackReader(
						new BufferedReader(
								new InputStreamReader(
										new FileInputStream(arquivo), "US-ASCII")));
	}
	
	public ArrayList<Token> analisa(String arquivo) throws IOException{
		//Enquanto nao e o fim do arquivo 
		while(ch != '@'){
			lt.add(buscaToken());
		}
		return lt;
	}
	
	public char leCh() throws IOException{
		intch = r.read(); 
		ch = (char) intch;
		if(ch == '\n'){
			linha++; 
			coluna = 0; 
		}else{ 
			coluna++; 
		}
		if(intch != -1){ 
			return ch; 
		}else{ 
			return '@'; 
		} 
	} 

	public void devolve() throws IOException{ 
		r.unread(ch); 
		if(ch == '\n'){ 
			linha--; 
		}else{ 
			coluna--; 
		} 
	} 
	
	public Token buscaToken() throws IOException{
		String lexema = "";
		int estado = 0;	
		
		while(ch != '@') {
			ch = leCh();
			switch(estado){ 
				case 0: 
					if(ch==':'){ 
						estado = 1; 
					}else if(ch=='+'){ 
						return new Token(Tipo.SMAIS, "+", linha, coluna); 
					}else if (ch =='-') {
						return new Token(Tipo.SMENOS,"-", linha, coluna);
					}else if (ch =='*') {
						return new Token(Tipo.SMULTIPLICACAO,"*", linha, coluna);
					}else if (ch =='/') {
						return new Token(Tipo.SDIVISAO,"/", linha, coluna);
					}else if (ch ==';') {
						return new Token(Tipo.SPONTO_E_VIRGULA,";", linha, coluna);
					}else if (ch ==',') {
						return new Token(Tipo.SVIRGULA,",", linha, coluna);
					}else if (ch =='.') {
						return new Token(Tipo.SPONTO,".", linha, coluna);
					}else if (ch =='(') {
						return new Token(Tipo.SABRE_PARENTESIS,"(", linha, coluna);
					}else if (ch ==')') {
						return new Token(Tipo.SFECHA_PARENTESIS,")", linha, coluna);
					}else if (Character.isDigit(ch)) {
						lexema +=ch;
						estado = 12;
					}else if(Character.isLetter(ch)){
						lexema +=ch;
						estado = 14;
					}
					break;
				case 1:
					estado = 0;
					if(ch == '='){
						return new Token(Tipo.SATRIBUICAO, ":=", linha, coluna);
					}else{
						 return new Token(Tipo.STIPO, ":", linha, coluna); 
					}					
				case 12:
					if(Character.isDigit(ch)){
						lexema += ch;
					}else{
						devolve();
						return new Token(Tipo.SNUMERO, lexema, linha, coluna);	 
					}
					break;
				case 14:
					if(Character.isLetter(ch)){
						lexema += ch;
					}else{
						devolve();
						switch(lexema){
							case "programa": return new Token(Tipo.SPROGRAMA, lexema, linha, coluna);
							case "inteiro": return new Token(Tipo.SINTEIRO, lexema, linha, coluna);
							case "booleano": return new Token(Tipo.SBOOLEANO, lexema, linha, coluna);
							case "inicio": return new Token(Tipo.SINICIO, lexema, linha, coluna);
							case "fim": return new Token(Tipo.SFIM, lexema, linha, coluna);
							case "var": return new Token(Tipo.SVAR, lexema, linha, coluna);
							case "escreva": return new Token(Tipo.SESCREVA, lexema, linha, coluna);
							default: return new Token(Tipo.SIDENTIFICADOR, lexema, linha, coluna);
						}
					}
					break;				
				}
			}
		return new Token(Tipo.SERRO, "", linha, coluna);
	}
}
