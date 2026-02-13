package platzi.play.contenido;

public class Pelicula extends Contenido{

    public Pelicula(String titulo, int duracion, Genero genero, double calificacion) {
        super(titulo, duracion, genero, calificacion);
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo la película " + this.getTitulo());
    }

    @Override
    public String fichaTecnica() {
        return "Pelicula: " + "\n" +
                "Titulo: " + this.getTitulo() + "\n" +
                "Genero: " + this.getGenero() + "\n" +
                "Calificacion: " + this.getCalificacion() + "/5";
    }

    public Pelicula(String titulo, int duracion, Genero genero) {
        super(titulo, duracion, genero);
    }


}
