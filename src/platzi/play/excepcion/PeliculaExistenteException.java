package platzi.play.excepcion;

public class PeliculaExistenteException extends RuntimeException {
    public PeliculaExistenteException(String tipo) {
        super("El contenido llamado " + tipo + ", ya existe.");
    }
}
