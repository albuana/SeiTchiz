import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyServer {
	String nomeFicheiro;
	BufferedReader br;
	public MyServer(String fich) {
		nomeFicheiro = fich;
	}
	public static void main(String[] args) {
		MyServer serv = new MyServer("users.txt");
		try { 
			serv.Abrir();
			serv.Ler();
		} catch (SecurityException se) {
			System.err.println("Erro de permissoes");
			se.printStackTrace(); 
		}
	}
	void Abrir() {
		try { br = new BufferedReader (new FileReader (nomeFicheiro));
		} catch (FileNotFoundException e) {
			System.err.println("Erro no acesso ao ficheiro ...");
			System.exit(-1); 
		}
	}
	void Ler() {
		String thisLine;
		try {
			while ((thisLine = br.readLine()) != null) System.out.println(thisLine);
		} catch (IOException e) {
			System.err.println("Erro a ler do ficheiro ...");
			System.exit(-1); 
		} 
	}
}