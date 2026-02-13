package platzi.play.plataforma;

import platzi.play.contenido.*;
import platzi.play.excepcion.PeliculaExistenteException;

import java.util.*;

public class Plataforma {
    private String nombre;
    private List<Contenido> contenido;
    private Map<Contenido, Integer> visualizaciones;

    public Plataforma(String nombre) {
        this.nombre = nombre;
        this.contenido = new ArrayList<>();
        this.visualizaciones = new HashMap<>();
    }

    public void agregar(Contenido elemento){
        Contenido contenido = this.buscarTitulo(elemento.getTitulo());
        if (contenido != null){
            throw new  PeliculaExistenteException(elemento.getTitulo());
        }
        this.contenido.add(elemento);
    }

    public void reproducir(Contenido contenido){
        int visualizaciones = this.visualizaciones.getOrDefault(contenido, 0);
        System.out.println(contenido.getTitulo() + " ha sido reproducido por " + visualizaciones + " veces.");
        this.contarVisualizacion(contenido);
        contenido.reproducir();
    }

    private void contarVisualizacion(Contenido contenido){
        int conteoActual = this.visualizaciones.getOrDefault(contenido, 0);
        this.visualizaciones.put(contenido, conteoActual + 1);
    }

    public int len(){
        return this.contenido.size();
    }

    public List<Contenido> getContenido() {
        return contenido;
    }

    public void eliminar(Contenido contenido){
        this.contenido.remove(contenido);
    }

    public List<String> mostrarTitulos(){
        // Stream con expresión lambda
        return this.contenido.stream().map(Contenido::getTitulo).toList();
    }

    public List<ResumenContenido> getResumen(){
        return contenido.stream().map(c -> new ResumenContenido(c.getTitulo(), c.getDuracion(), c.getGenero())).toList();
    }

    public Contenido buscarTitulo(String titulo){
        return this.contenido.stream().filter(contenido -> contenido.getTitulo().equalsIgnoreCase(titulo)).findFirst().orElse(null);
    }

    public List<Contenido> buscarGenero(Genero genero){
        return this.contenido.stream().filter(pelicula -> pelicula.getGenero().equals(genero)).toList();
    }

    public int getDuracionTotal(){
        return this.contenido.stream().mapToInt(Contenido::getDuracion).sum();
    }

    public List<Contenido> getPopulares(){
        return this.contenido.stream().sorted(Comparator.comparingDouble(Contenido::getCalificacion).reversed()).toList();
    }

    public List<Contenido> getMasPopulares(){
        return this.contenido.stream().filter(Contenido::esPopular).toList();
    }

    public Contenido getMasLarga(){
        return this.contenido.stream().max(Comparator.comparingInt(Contenido::getDuracion)).orElse(null);
    }

    public List<Contenido> getPorVisualizaciones(){
        return this.contenido.stream().sorted(Comparator.comparingInt(pelicula -> this.visualizaciones.getOrDefault(pelicula, 0))).toList().reversed();
    }

    public Map<Contenido, Integer> getVisualizacionesMap(){
        List<Contenido> contenidos = getPorVisualizaciones();
        Map<Contenido, Integer> pares = new LinkedHashMap<>();

        for (Contenido contenido : contenidos){
            pares.put(contenido, this.visualizaciones.getOrDefault(contenido, 0));
        }

        return pares;
    }

    public List<Pelicula> getPeliculas() {
        return this.contenido.stream()
                .filter(c -> c instanceof Pelicula)
                .map(c -> (Pelicula) c)
                .toList();
    }

    public List<Documental> getDocumentales() {
        return this.contenido.stream()
                .filter(c -> c instanceof Documental)
                .map(c -> (Documental) c)
                .toList();
    }

    public List<Promocionable> getPromocionables(){
        return this.contenido.stream()
                .filter(contenido1 -> contenido1 instanceof Promocionable)
                .map(contenido1 -> (Promocionable) contenido1)
                .toList();
    }
}
