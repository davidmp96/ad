package serpis.ad;

import java.util.Scanner;

public class ArticuloMain {
	
	public enum Option{Salir, Nuevo, Editar, Eliminar, Consultar, Listar};
	
	private static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) {
		scanOption("Opcion (0-5): ", "^[012345]$");
	}
		
	public static int scanInt(String label) {
		while(true) {
			try {
				System.out.println(label);
				String line = scanner.nextLine();
				return Integer.parseInt(line);
			}catch (NumberFormatException ex){
				System.out.println("Introduce solo numeros");
			}
		}
	}
	
	public static Option scanOption(String label, String options) {
		while(true) {
				System.out.println(label);
				String line = scanner.nextLine();
				if (line.matches(options))
						return line;
				System.out.println("Opcion invalida. Vuelve a introducir");
		}	
	}
}
