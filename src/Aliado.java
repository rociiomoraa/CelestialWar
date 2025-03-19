public class Aliado extends Personaje {
    private String rol; // Función del aliado en la historia
    private String habilidadEspecial; // Nombre de la habilidad especial

    public Aliado(String nombre, int vida, int energia, String rol, String habilidadEspecial) {
        super(nombre, vida, energia, 0, 0, 0); // No tienen stats de combate
        this.rol = rol;
        this.habilidadEspecial = habilidadEspecial;
    }
    // Método estático para crear los aliados con sus roles y habilidades
    public static Aliado crearAliado(String nombre) {
        switch (nombre) {
            case "Orin":
                return new Aliado("Orin, el Sabio Estelar", 100, 50, "Mentor", "Consejo Estelar");
            case "Liora":
                return new Aliado("Liora, la Forjadora de Estrellas", 120, 40, "Herrera", "Forja Celestial");
            case "Nix":
                return new Aliado("Nix, el Príncipe Errante", 110, 60, "Viajero", "Destino Sombrío");
            case "Eira":
                return new Aliado("Eira, la Guardiana del Tiempo", 130, 70, "Protectora", "Bendición del Tiempo");
            case "Sygma":
                return new Aliado("Sygma, la Estrella Rebelde", 90, 80, "Guía", "Luz Rebelde");
            default:
                return null; // Si no coincide, no existe el aliado
        }
    }

    public void interactuar(Jugador jugador) {
        System.out.println("🔹 " + nombre + " (" + rol + ") dice: 'Hola, Astra. ¿Necesitas ayuda?'");
    }

    public void usarHabilidad(Jugador jugador) {
        switch (habilidadEspecial) {
            case "Consejo Estelar":
                System.out.println(nombre + " comparte sabiduría con Astra. Su próximo ataque hará el doble de daño.");
                jugador.aumentarAtaqueTemporal(10);
                break;
            case "Forja Celestial":
                System.out.println(nombre + " mejora el arma de Astra, aumentando su ataque permanentemente.");
                jugador.aumentarAtaque(5);
                break;
            case "Destino Sombrío":
                System.out.println(nombre + " revela un secreto oscuro... Astra obtiene +10 de energía.");
                jugador.aumentarEnergia(10);
                break;
            case "Bendición del Tiempo":
                System.out.println(nombre + " ralentiza el tiempo, permitiendo a Astra esquivar automáticamente el próximo ataque.");
                jugador.activarEsquivaAutomatica();
                break;
            case "Luz Rebelde":
                System.out.println(nombre + " inspira a Astra, restaurando su energía completamente.");
                jugador.recuperarEnergia();
                break;
            default:
                System.out.println(nombre + " no tiene habilidades activas.");
        }
    }

    @Override
    public void atacar(Personaje objetivo) {
        System.out.println(nombre + " es un aliado y no puede atacar.");
    }

    public String getRol() {
        return rol;
    }

    public String getHabilidadEspecial() {
        return habilidadEspecial;
    }
}
