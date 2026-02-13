package platzi.play.util;

import platzi.play.contenido.Genero;

import java.util.Arrays;
import java.util.Scanner;

public class ScannerUtils {
    public static final Scanner SCANNER = new Scanner(System.in);

    public static String capturarTexto(String mensaje){
        System.out.print(mensaje + ": ");

        return SCANNER.nextLine();
    }

    public static int capturarEntero(String mensaje) {
        System.out.print(mensaje + ": ");

        while(!SCANNER.hasNextInt()){
            System.out.println("Tipo de dato invalido.");
            System.out.print(mensaje + ": ");
            SCANNER.next();
        }

        int dato = SCANNER.nextInt();
        SCANNER.nextLine(); // Para que salte a una nueva línea
        return dato;
    }

    public static double capturarDecimal(String mensaje) {
        System.out.print(mensaje + ": ");

        while(!SCANNER.hasNextDouble()){
            System.out.println("Tipo de dato invalido.");
            System.out.print(mensaje + ": ");
            SCANNER.next();
        }

        double dato = SCANNER.nextDouble();
        SCANNER.nextLine(); // Para que salte a una nueva línea
        return dato;
    }

    public static Genero capturarGenero(String mensaje){
        while (true){
            System.out.println("Las opciones existentes son:");
            Arrays.stream(Genero.values()).toList().forEach(genero -> System.out.println("-> " + genero.toString()));
            String capturarTexto = ScannerUtils.capturarTexto(mensaje);

            try {
                return Genero.valueOf(capturarTexto.toUpperCase());
            }catch (IllegalArgumentException e){
                System.out.println("Genero no aceptado!");
            }
        }
    }
}
