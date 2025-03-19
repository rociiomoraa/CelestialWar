import java.util.Random;

public class Enemigo extends Personaje {
    protected String tipo;
    private boolean esJefe;
    private boolean enDefensa;
    protected static final Random random = new Random();

    public Enemigo(String nombre, int vida, int energia, int ataque, int defensa, int velocidad, String tipo, boolean esJefe) {
        super(nombre, vida, energia, ataque, defensa, velocidad);
        this.tipo = tipo;
        this.esJefe = esJefe;
        this.enDefensa = false;
    }

    public void decidirAccion(Personaje objetivo) {
        int decision = random.nextInt(100); // Probabilidad de acción

        if (decision < 40) {
            atacar(objetivo);
        } else if (decision < 65 && energia >= 10) {
            ataqueFuerte(objetivo);
        } else if (decision < 80) {
            usarHabilidadEspecial(objetivo);
        } else if (decision < 85) {
                defenderse();
            } else if (decision < 95) {
                esquivar();
            } else {
                recuperarEnergia();
            }
        }

    @Override
    public void atacar(Personaje objetivo) {
        if (estaAturdido) {
            System.out.println("💫 " + nombre + " está aturdido y no puede atacar.");
            recuperarTurno();
            return;
        }

        int daño = ataque;
        if (random.nextInt(100) < 20) { // 20% de probabilidad de golpe crítico
            daño *= 2;
            System.out.println("💥 ¡" + nombre + " ejecuta un **golpe crítico** causando " + daño + " de daño!");
        } else {
            System.out.println("⚔ " + nombre + " ataca a " + objetivo.getNombre() + " causando " + daño + " de daño.");
        }

        objetivo.recibirDanio(daño);
    }

    public void ataqueFuerte(Personaje objetivo) {
        if (energia < 10) {
            System.out.println("⚠ " + nombre + " intentó hacer un ataque fuerte, pero no tiene energía suficiente.");
            return;
        }

        energia -= 10;
        int daño = ataque * 2;
        System.out.println("💥 " + nombre + " usa un **ATAQUE FUERTE** sobre " + objetivo.getNombre() + " causando " + daño + " de daño!");
        objetivo.recibirDanio(daño);
    }

    public void usarHabilidadEspecial(Personaje objetivo) {
        switch (tipo) {
            case "Sombra Estelar":
                System.out.println(nombre + " usa \"Llamas de Oscuridad\" contra " + objetivo.getNombre() + "!");
                objetivo.recibirDanio(ataque + 10);
                break;
            case "Espectro Cósmico":
                System.out.println(nombre + " usa \"Eco Astral\" y drena energía de " + objetivo.getNombre() + "!");
                objetivo.reducirEnergia(15);
                energia += 10; // Absorbe parte de la energía drenada
                break;
            case "Guardián del Vacío":
                System.out.println(nombre + " activa \"Muro Antimateria\", reduciendo el daño recibido!");
                defensa += 5; // Aumenta temporalmente su defensa
                break;
            default:
                System.out.println(nombre + " no tiene habilidades especiales.");
        }
    }


    public void defenderse() {
        enDefensa = true;
        System.out.println("🛡 " + nombre + " **se pone en defensa**, reduciendo el daño del próximo ataque.");
    }

    public void esquivar() {
        System.out.println("💨 " + nombre + " **intenta esquivar el próximo ataque**.");
        if (random.nextInt(100) < 40) { // 40% de probabilidad de esquivar
            System.out.println("⚡ " + nombre + " esquivó con éxito el ataque entrante!");
            enDefensa = true; // Reduce el daño del próximo golpe
        } else {
            System.out.println("❌ " + nombre + " intentó esquivar, pero falló.");
        }
    }

    public void recuperarEnergia() {
        System.out.println("🔋 " + nombre + " **recupera energía**, recargando 15 puntos.");
        energia += 15;
    }

    @Override
    public void recibirDanio(int cantidad) {
        if (enDefensa) {
            cantidad /= 2;
            System.out.println("🛡 " + nombre + " **reduce el daño a la mitad** al estar en defensa.");
            enDefensa = false;
        }
        super.recibirDanio(cantidad);
    }

    public boolean esJefe() {
        return esJefe;
    }

    public String getTipo() {
        return tipo;
    }
}
