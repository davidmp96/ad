package serpis.ad;

import java.util.Scanner;
import java.math.BigDecimal;
import java.util.List;

public class ArticuloView {
	private static Scanner scanner = new Scanner(System.in);
	public static long getId() {
		return getLong("Id: ");
	}
  
	public static void get(Articulo articulo) {
		String nombre = getString("Nombre: ");
		BigDecimal precio = getBigDecimal("Precio: ");
		long categoria = getLong("Categoría: ");
		articulo.setNombre(nombre);
		articulo.setPrecio(precio);
		articulo.setCategoria(categoria);
	}
  
	public static void show(Articulo articulo) {
		System.out.printf("%5s %-30s %10s %9s\n", "id", "nombre", "precio", "categoria");
		System.out.printf("%5s %-30s %10s %9s\n", 
				articulo.getId(), 
				articulo.getNombre(),
				articulo.getPrecio(),
				articulo.getCategoria());
	}
  
	public static void show(List<Articulo> articulos) {
		System.out.printf("%5s %-30s %10s %9s\n", "id", "nombre", "precio", "categoria");
		for (Articulo articulo : articulos) {
			System.out.printf("%5s %-30s %10s %9s\n", 
					articulo.getId(), 
					articulo.getNombre(),
					articulo.getPrecio(),
					articulo.getCategoria());
		}
	}
  
	private static long getLong(String label) {
		while (true) 
			try {
				System.out.print(label);
				String line = scanner.nextLine();
				return Long.parseLong(line);
			} catch (NumberFormatException ex) {
				System.out.println("Por favor, sólo números. Vuelve a introducir");
			}
	}

	private static BigDecimal getBigDecimal(String label) {
		while (true) 
			try {
				System.out.print(label);
				String line = scanner.nextLine();
				return new BigDecimal(line);
			} catch (NumberFormatException ex) {
				System.out.println("Por favor, sólo números. Vuelve a introducir");
			}
	}
  
	private static String getString(String label) {
		while (true) {
     	System.out.print(label);
     	String line = scanner.nextLine().trim();
     	if (line.length() > 0)
     		return line;
     	System.out.println("Dato obligatorio. Vuelve a introducir");
		}
	}
}
