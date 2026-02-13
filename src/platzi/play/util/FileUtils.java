package platzi.play.util;

import platzi.play.contenido.Documental;
import platzi.play.contenido.Genero;
import platzi.play.contenido.Contenido;
import platzi.play.contenido.Pelicula;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static final String CONTENIDO_TXT = "contenido.txt";
    public static final String SEPARADOR = "\\|";

    public static void escribirContenido(Contenido contenido){
        String linea = String.join("|",
                contenido.getTitulo(),
                String.valueOf(contenido.getDuracion()),
                contenido.getGenero().name(),
                String.valueOf(contenido.getCalificacion()),
                contenido.getFechaEstreno().toString());

        if (contenido instanceof Documental documental){
            linea = String.join("|", "DOCUMENTAL", linea, documental.getNarrador());
        }else {
            linea = String.join("|", "PELICULA", linea);
        }

        try {
            Files.writeString(Paths.get(CONTENIDO_TXT), linea + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        }catch (IOException e){
            System.out.println("Error al escribir el archivo");
            System.out.println(e.getMessage());
        }

    }

    public static List<Contenido> leerContenido(){
        List<Contenido> lista = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(CONTENIDO_TXT));
            lineas.forEach(linea -> {
                String[] datos = linea.split(SEPARADOR);
                if ((datos.length == 6 && "PELICULA".equalsIgnoreCase(datos[0])) || (datos.length == 7 && "DOCUMENTAL".equalsIgnoreCase(datos[0]))){
                    String titulo = datos[1];
                    int duracion = Integer.parseInt(datos[2]);
                    Genero genero = Genero.valueOf(datos[3].toUpperCase());
                    double calificacion = datos[4].isBlank() ? 0 : Double.parseDouble(datos[4]);
                    LocalDate estreno = LocalDate.parse(datos[5]);
                    Contenido contenido = null;
                    if("PELICULA".equalsIgnoreCase(datos[0])){
                        contenido = new Pelicula(titulo, duracion, genero, calificacion);
                    } else {
                        String narrador = datos[6];
                        contenido = new Documental(titulo, duracion, genero, calificacion, narrador);

                    }
                    contenido.setFechaEstreno(estreno);
                    lista.add(contenido);
                }
            });
        }catch (IOException e){
            System.out.println("Hubo un problema al leer el archivo de presistencia.");
            System.out.println(e.getMessage());
        }
        return lista;
    }
}
