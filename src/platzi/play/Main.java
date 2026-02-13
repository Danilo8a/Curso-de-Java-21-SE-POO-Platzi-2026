package platzi.play;

import platzi.play.contenido.*;
import platzi.play.excepcion.PeliculaExistenteException;
import platzi.play.plataforma.Plataforma;
import platzi.play.util.FileUtils;
import platzi.play.util.ScannerUtils;

import java.util.List;
import java.util.Map;

public class Main {
    public static final String NOMBRE_APP = "Platzi Play!";
    public static final String VERSION = "v1.0.0";

    public static void main(String[] args) {
        Plataforma plataforma = new Plataforma(NOMBRE_APP);
        System.out.println(NOMBRE_APP + " " + VERSION);
        int opcion = 0;

        // Creación de menú interactivo
        // 1. Agregar contenido
        // 2. Mostrar todo
        // 3. Buscar por título
        // 4. Eliminar
        // 5. Buscar por genero
        // 6. Ver populares
        // 7. Salir

        cargarPeliculas(plataforma);
        System.out.println("Más de " + plataforma.getDuracionTotal() + " minutos de contenido!");
        System.out.println("La pelicula más larga: " + plataforma.getMasLarga().getTitulo());
        plataforma.getPromocionables().forEach(contenido -> System.out.println(contenido.promocionar()));

        while (opcion != 7){
            opcion = ScannerUtils.capturarEntero("-----------------------\n" +
                    "Elige una de estas opciones: \n" +
                    "1. Agregar contenido \n" +
                    "2. Mostrar todo el contenido \n" +
                    "3. Buscar por título \n" +
                    "4. Eliminar por titulo \n" +
                    "5. Buscar por género \n" +
                    "6. Ranking de contenido \n" +
                    "7. Más populares \n" +
                    "8. Reproducir contenido \n" +
                    "9. Mostrar por visualizaciones \n" +
                    "10. Mostrar por tipo de contenido \n" +
                    "11. Salir");

            System.out.println("Opción elegida: " + opcion);

            switch (opcion) {
                case 1 -> {
                    int tipoContenido = ScannerUtils.capturarEntero("Qué tipo de contenido quieres agregar? Pelicula 1 y Documental 2");
                    String nombrePelicula = ScannerUtils.capturarTexto("Ingrese el nombre de la palícula");
                    Genero generoPelicula = ScannerUtils.capturarGenero("Ingrese el género");
                    int duracionPelicula = ScannerUtils.capturarEntero("Duración de la película");
                    double calificacionPelicula = ScannerUtils.capturarDecimal("Calificación de la película");

                    try {
                        Contenido aux;
                        if(tipoContenido == 1){
                            aux = new Pelicula(nombrePelicula, duracionPelicula, generoPelicula, calificacionPelicula);
                        }else{
                            String narrador = ScannerUtils.capturarTexto("Indique el nombre del narrador");
                            aux = new Documental(nombrePelicula, duracionPelicula, generoPelicula, calificacionPelicula, narrador);
                        }

                        plataforma.agregar(aux);
                        FileUtils.escribirContenido(aux);
                        System.out.println("Contenido agregado con éxito!");
                    }catch (PeliculaExistenteException e){
                        System.out.println(e.getMessage());
                    }
                }
                case 2 -> {
                    List<ResumenContenido> contenidoResumidos = plataforma.getResumen();
                    contenidoResumidos.forEach(resumen -> System.out.println(resumen.toString()));
                }

                case 3 -> {
                    String titulo = ScannerUtils.capturarTexto("Ingrese título a buscar");
                    Contenido aux = plataforma.buscarTitulo(titulo);
                    if (aux != null) {
                        System.out.println(aux.fichaTecnica());
                    }else{
                        System.out.println("Película no encontrada.");
                    }

                }
                case 4 -> {
                    String nombrePelicula = ScannerUtils.capturarTexto("Ingrese el nombre de la palícula a eliminar");
                    Contenido aux = plataforma.buscarTitulo(nombrePelicula);
                    if (aux != null) {
                        plataforma.eliminar(aux);
                        System.out.println(nombrePelicula + " ha sido eliminada");
                    }else {
                        System.out.println("Película no encontrada.");
                    }
                }
                case 5 -> {
                    Genero genero = ScannerUtils.capturarGenero("Ingrese el género");
                    plataforma.buscarGenero(genero).forEach(pelicula -> System.out.println(pelicula.getTitulo()));
                }
                case 6 -> {
                    System.out.println("Este es nuestro ranking:");
                    plataforma.getPopulares().forEach(pelicula -> System.out.println(pelicula.getTitulo() + "(" + pelicula.getCalificacion() + ")"));
                }
                case 7 ->{
                    System.out.println("Estas son nuestras películas más populares.");
                    List<Contenido> populares = plataforma.getMasPopulares();
                    populares.forEach(pelicula -> System.out.println(pelicula.getTitulo() + "(" + pelicula.getCalificacion() + ")"));
                }
                case 8 -> {
                    String titulo = ScannerUtils.capturarTexto("Indique un titulo");
                    Contenido contenido = plataforma.buscarTitulo(titulo);
                    if (contenido != null){
                        plataforma.reproducir(contenido);
                    }else {
                        System.out.println("El contenido no existe en la plataforma.");
                    }
                }
                case 9 -> {
                    Map<Contenido, Integer> peliculas = plataforma.getVisualizacionesMap();
                    peliculas.forEach((pelicula, vistas) -> System.out.println(pelicula.getTitulo() + ": " + vistas));
                }
                case 10 -> {
                    int tipoContenido = ScannerUtils.capturarEntero("1. para Peliculas y 2. para Documentales");
                    if (tipoContenido == 1){
                        System.out.println("Acá puedes ver todas nuestras películas:");
                        plataforma.getPeliculas().forEach(pelicula -> System.out.println(pelicula.getTitulo()));
                    } else if (tipoContenido ==2) {
                        System.out.println("Acá puedes ver todos los documentales:");
                        plataforma.getDocumentales().forEach(documental -> System.out.println(documental.getTitulo()));
                    }
                }
                case 11 -> {
                    System.out.println("Saliendo de la plataforma...");
                    System.exit(0);
                }
            }
        }


    }

    private static void cargarPeliculas(Plataforma plataforma) {
        plataforma.getContenido().addAll(FileUtils.leerContenido());
    }
}
