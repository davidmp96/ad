package serpis.ad;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Scanner;

public class ArticuloMain {
	
	public enum Option{Salir, Nuevo, Editar, Eliminar, Consultar, Listar};
	public enum State{Vacio, Medio, Lleno};
	
	private static Scanner scanner = new Scanner(System.in);
	
	public static class Articulo {
		private long id;
		private String nombre;
		private BigDecimal precio;
		private long categoria;
	}
	
	public static void main(String[] args) {
		
		showFields(Articulo.class);
		
//		new Menu.add("Salir", null)
//			.add("Nuevo", () -> nuevo())
//			.add("Editar",() -> editar())
//			.run();
//		
//	}
	
	}
	
	private static void showFields(Class<?> type) {
		for(Field field : type.getDeclaredFields() )
			System.out.printf("%s %s\n", field.getName(), field.getType().getName());
	}
	
	public static void nuevo() {
		///TODO implementar
	}
		
	public static <T extends Enum<T>> T scan(Class<T> enumType) {
		T[] constants = enumType.getEnumConstants();
		for(int index  = 0; index < constants.length; index++)
			System.out.printf("%s - %s\n", index, constants[index]);
		String options = String.format("^[0-%s]$", constants.length - 1);
		while (true) {
			System.out.println("Elige una opción: ");
			String line = scanner.nextLine();
			if (line.matches(options))
				return constants[Integer.parseInt(line)];
			System.out.println("Opcion invalida. Vuelve a introducir");
			
		}
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
