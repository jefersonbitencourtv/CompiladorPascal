import java.lang.reflect.Field;
import java.util.HashMap;

public class TS {

	HashMap<String, Token> ts;
	
	public TS() {
		ts = new HashMap<>();
	}
	
	//Operação Insert -> put()
	//Operação Lookup -> get()
	
	public void setAtributo(String chave, String atributo, String valor) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Token t = ts.get(chave);
		Field f = t.getClass().getDeclaredField(atributo);
		f.set(t, valor);
	}
	
	public String getAtributo(String chave, String atributo) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Token t = ts.get(chave);
		Field f = t.getClass().getDeclaredField(atributo);
		return (String)f.get(t);
	}
}
