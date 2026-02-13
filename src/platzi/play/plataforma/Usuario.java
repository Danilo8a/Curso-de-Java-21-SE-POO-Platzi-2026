package platzi.play.plataforma;

import platzi.play.contenido.Contenido;

import java.time.LocalDateTime;

public class Usuario {
    public String nombre;
    public String apellido;
    public LocalDateTime fechaRegistro;
    public int edad;
    public String correo;

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.correo = email;
        this.fechaRegistro = LocalDateTime.now();
    }

    public void ver(Contenido miContenido){
        System.out.println(this.nombre + " esta viendo: ");
        miContenido.reproducir();
    }
}
