package platzi.play.contenido;

public class Documental extends Contenido implements Promocionable{
    private String narrador;

    public Documental(String titulo, int duracion, Genero genero) {
        super(titulo, duracion, genero);
    }

    @Override
    public void reproducir() {
        System.out.println("Reproduciendo el documental " + this.getTitulo() + " narrado por " + this.getNarrador());
    }

    @Override
    public String fichaTecnica() {
        return "Documental: " + "\n" +
                "Titulo: " + this.getTitulo() + "\n" +
                "Genero: " + this.getGenero() + "\n" +
                "Calificacion: " + this.getCalificacion() + "/5" +
                "Narrador: " + this.getNarrador() + "\n";
    }

    @Override
    public String promocionar() {
        return "Descubre el documental " + this.getTitulo();
    }

    public Documental(String titulo, int duracion, Genero genero, double calificacion, String narrador) {
        super(titulo, duracion, genero, calificacion);

        this.narrador = narrador;
    }

    public String getNarrador() {
        return narrador;
    }
}
