import java.io.IOException;

public class Main_PPR {
	public static void main(String[] args) throws IOException {
		/**
		 * Cria o parser
		 */
		PPR ppr = new PPR("src/teste1.lpd");
		
		/**
		 * Executa o parser
		 */
		ppr.parse();
	}
}
