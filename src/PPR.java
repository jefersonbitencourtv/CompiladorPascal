import java.io.IOException;

public class PPR extends Parser {
	
	String escopo;
	
	public PPR(String arquivo) throws IOException {
		super(arquivo);
		escopo = "Global";
	}
	
	@Override
	public void parse() throws IOException {
		analisaPrograma();	
	}	
	
	public boolean analisaPrograma() throws IOException {
		buscaToken();
		if(t.tipo == Tipo.SPROGRAMA) {
			System.out.println(t.tipo);
			buscaToken();
			if (t.tipo == Tipo.SIDENTIFICADOR) {
				System.out.println(t.tipo +": " + t.lexema);
				//Adiciona identficados a tabela de simbolos
				ts.ts.put(t.lexema, t);
				buscaToken();
				if(t.tipo == Tipo.SPONTO_E_VIRGULA) {
					System.out.println(t.tipo);
					if(analisaBloco()) {
						if(t.tipo == Tipo.SPONTO) {
							System.out.println(t.tipo);
							System.out.println("Compilação feita com sucesso");
							return true;
						} else {
							return erro("Ponto final esperado");
						}
					} else {
						return erro("Bloco principal nao encontrado");
					}
				} else {
					erro("Ponto e virgula esperado");
					return false;
				} 
			} else {
				return erro("Identificador esperado");
			}
		} else {
			return erro("Nao foi encontrado o programa principal.");
		}
	}
	
	public boolean analisaBloco() throws IOException {
		buscaToken();
		
		analisaEtapaDeclaracaoDeVariaveis();
		analisaComandos();
		
		if(t.tipo == Tipo.SFIM) {
			System.out.println(t.tipo);
			buscaToken();			
		}
		return true;
	}
	
	public boolean analisaEtapaDeclaracaoDeVariaveis() throws IOException {
		if(t.tipo == Tipo.SVAR) {
			System.out.println(t.tipo + " ");
			buscaToken();
			if (t.tipo == Tipo.SIDENTIFICADOR) {				
				while(t.tipo == Tipo.SIDENTIFICADOR) {
					if(analisaVariaveis()) {
						buscaToken();
						if(t.tipo == Tipo.SPONTO_E_VIRGULA) {
							System.out.println(t.tipo + " ");
							buscaToken();
							
							if (t.tipo == Tipo.SIDENTIFICADOR) {				
								while(t.tipo == Tipo.SIDENTIFICADOR) {
									if(analisaVariaveis()) {
										buscaToken();
										if(t.tipo == Tipo.SPONTO_E_VIRGULA) {
											System.out.println(t.tipo + " ");
											buscaToken();
											
											
											
											return true;
										} else {
											return erro("Ponto e virgula esperado");
										}
									} else {
										return erro("Erro na declaracao de variaveis");
									}
								}
							}
							
							
							
							return true;
						} else {
							return erro("Ponto e virgula esperado");
						}
					} else {
						return erro("Erro na declaracao de variaveis");
					}
				}
			} else {
				return erro("Identificador esperado");
			}
		}
		return true;
	}
	public boolean analisaVariaveis() throws IOException {
		do {
			if(t.tipo == Tipo.SIDENTIFICADOR) {
				verificaDuplicidade();
				System.out.println(t.tipo +": " +t.lexema +' ');
				ts.ts.put(t.lexema, t);				
				buscaToken();
				if(t.tipo == Tipo.SVIRGULA || t.tipo == Tipo.STIPO) {
					System.out.println(t.tipo + " ");
					if(t.tipo == Tipo.SVIRGULA) {
						//System.out.println(t.tipo + " ");
						buscaToken();
						if(t.tipo == Tipo.STIPO) {
							return erro("Token incorreto encontrado.");
						}
					}						
				} else {
					return erro("Declaracao de variavel incorreta");
				}
				//fecha if da duplicidade
				//else
				//retorna erro de duplicidade encontrada
			} else {
				return erro("Identificador esperado");
			}
		} while (t.tipo != Tipo.STIPO);
		buscaToken();
		return analisaTipo();
		//return true;
	}
	
	public boolean analisaTipo() throws IOException {
		if(t.tipo != Tipo.SINTEIRO && t.tipo != Tipo.SBOOLEANO) {
			return erro("Tipo não reconhecido");
		}
		else {
			System.out.println(t.tipo + " ");
			//Adiciona identficados a tabela de simbolos
			ts.ts.put(t.lexema, t);
		} 
		return true;
	}
	
	public boolean verificaDuplicidade() throws IOException {
		if(ts.ts.containsKey(t.lexema))
		{
			return erro("Identificador já declarado no escopo.");
		}
		return true;
	}
	
//após inicio, testa se o token foi mais ou menos, e fica em loop enquanto for mais ou menos
private boolean analisaComandos()  throws IOException {
		if (t.tipo == Tipo.SINICIO) {
			System.out.println(t.tipo +"");
			buscaToken();
			analisa_comando_simples();
			buscaToken();
			while(t.tipo == Tipo.SMAIS ||t.tipo == Tipo.SMENOS || t.tipo == Tipo.SMULTIPLICACAO ||t.tipo == Tipo.SDIVISAO) {
				System.out.println(""+ t.tipo);
				buscaToken();
				analisa_comando_simples();
				
			}
			do {
			if (t.tipo == Tipo.SPONTO_E_VIRGULA) {
				System.out.println(t.tipo+"");
				buscaToken();
			//enquanto não chegar ao fim fica analisando comando simples
			if (t.tipo != Tipo.SFIM && t.tipo != Tipo.SINICIO) {
				analisa_comando_simples();
			}
			
			
			}else {
				erro("Falta ponto e virgula");
			}
			} while (t.tipo != Tipo.SFIM);


		return true;
	}
		return false;
}
//função para comandos de atribuição e de escreva
private void analisa_comando_simples() throws IOException {
		
		if (t.tipo == Tipo.SIDENTIFICADOR || t.tipo == Tipo.SNUMERO) {
				analisa_atrib_chprocedimento();
			
	}
		if(t.tipo == Tipo.SESCREVA) {
			System.out.println(t.tipo+"");
			analisa_escreva();
		}

}
//função escreve entre parentesis o identificador
private boolean analisa_escreva() throws IOException {
	
	buscaToken();
	if (t.tipo == Tipo.SABRE_PARENTESIS) {
		System.out.println(t.tipo+"");
		buscaToken();
		
		if (t.tipo == Tipo.SIDENTIFICADOR) {
			System.out.println(t.tipo+":  "+t.lexema);
				buscaToken();
				
				if (t.tipo == Tipo.SFECHA_PARENTESIS) {
					System.out.println(t.tipo+"");
					buscaToken();
					
				}
				else {
					return erro("Falta fecha parentesis");
				}
			}
			else {
				return erro("Erro declaração token");
			}
		}
		else {
			return erro("Erro abre parentesis");
		}
	return false;
	}

//testa se o token e do tipo atribuição
private void analisa_atrib_chprocedimento() throws IOException {
	System.out.println(t.tipo +": "+ t.lexema);
	buscaToken();
	
	if (t.tipo == Tipo.SATRIBUICAO) {
		System.out.println(""+ t.tipo);
		analisa_atribuicao();
	}

}
//Analise se o token é do tipo = e chama o metodo e expressao simples
private void analisa_atribuicao() throws IOException {
	buscaToken();
	System.out.println(t.tipo +":  "+ t.lexema);
	analisa_expressao_simples();
	
}

//Testa enquanto token for mais ou menose chama termo
private void analisa_expressao_simples() throws IOException {
	
	
	buscaToken();
	if (t.tipo == Tipo.SMAIS || t.tipo == Tipo.SMENOS) {
		System.out.println(""+ t.tipo);
		buscaToken();
	}
	analisa_termo();
		
	while ((t.tipo == Tipo.SMAIS) || (t.tipo == Tipo.SMENOS)) {
		System.out.println(""+ t.tipo);
		buscaToken();
		analisa_termo();
	}
}
//Testa enquanto token for multiplicação ou divisao e chama fator
private void analisa_termo() throws IOException {
	analisa_fator();
	while (t.tipo == Tipo.SMULTIPLICACAO || t.tipo == Tipo.SDIVISAO) {
		System.out.println(""+ t.tipo);
		buscaToken();
		analisa_fator();
	}
}
//testa se os identificadores, numeros devem estar entre parenteses
private void analisa_fator() throws IOException {
	System.out.println(t.tipo +":  "+ t.lexema);
	if(t.tipo == Tipo.SIDENTIFICADOR || t.tipo == Tipo.SNUMERO);
	else if(t.tipo == Tipo.SABRE_PARENTESIS) {
		analisa_expressao_simples();
		buscaToken();
		if(t.tipo == Tipo.SFECHA_PARENTESIS);
		else {
			System.out.println(") esperado");
		}
	}
}

}

