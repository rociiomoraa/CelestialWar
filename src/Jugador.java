import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jugador extends Personaje implements Serializable {
    private static final long serialVersionUID = 1L;

    protected List<String> habilidades;
    private int fragmentosRecolectados;
    private int experiencia;
    private int nivel;
    private boolean enDefensa;
    private boolean esquivando;
    private transient static final Random random = new Random();
    private int decisionesOscuras = 0;
    private int vidaMaxima;
    public static final int COOLDOWN_HABILIDAD = 3;
    private int turnosDesdeUltimaHabilidad = 0;
    private int turnosAtaqueBuff = 0;
    private boolean esquivaAutomatica = false;
    private int turnosEsquiva = 0;


    public Jugador() {
        super("Astra", 100, 50, 15, 10, 12);
        this.habilidades = new ArrayList<>();
        this.fragmentosRecolectados = 0;
        this.experiencia = 0;
        this.nivel = 1;
        this.enDefensa = false;
        this.esquivando = false;
        this.vidaMaxima = 100; // Define la vida máxima inicial
    }

    @Override
    public void atacar(Personaje objetivo) {
        if (estaAturdido) {
            System.out.println("💫 Astra está aturdida y no puede atacar.");
            recuperarTurno();
            return;
        }

        int ataqueFinal = ataque;
        if (random.nextInt(100) < 20) {
            ataqueFinal *= 2;
            System.out.println("💥 ¡Golpe crítico de Astra!");
        }

        System.out.println("⚔ Astra ataca a " + objetivo.getNombre() + " causando " + ataqueFinal + " de daño.");
        objetivo.recibirDanio(ataqueFinal);
        ganarExperiencia(5);
    }

    public void usarHabilidad(String habilidad, Personaje objetivo) {
        switch (habilidad) {
            case "Golpe Celestial":
                System.out.println(nombre + " usa Golpe Celestial contra " + objetivo.getNombre() + "!");
                objetivo.recibirDanio(ataque * 2); // Doble de daño físico
                break;
            case "Destello Estelar":
                System.out.println(nombre + " desata un Destello Estelar!");
                objetivo.recibirDanio(energia * 2); // Usa magia para atacar
                break;
            case "Esquiva Lunar":
                System.out.println(nombre + " activa Esquiva Lunar, aumentando su evasión!");
                esquivando = true; // Activar evasión en el próximo turno
                break;
            case "Bendición de Luminara":
                System.out.println(nombre + " se cura con la Bendición de Luminara!");
                vida += 20; // Recupera 20 de vida
                if (vida > vidaMaxima) vida = vidaMaxima;
                break;
            default:
                System.out.println(nombre + " intenta usar una habilidad desconocida...");
        }
    }

    public void ataqueCargado(Personaje objetivo) {
        if (energia < 10) {
            System.out.println("⚠ No tienes suficiente energía para un ataque cargado.");
            return;
        }
        energia -= 10;
        System.out.println("🌠 Astra concentra su energía y lanza un ataque cargado sobre " + objetivo.getNombre() + "!");
        objetivo.recibirDanio(ataque * 2);
        ganarExperiencia(10);
    }

    public void aprenderHabilidad(String habilidad) {
        if (!habilidades.contains(habilidad)) {
            habilidades.add(habilidad);
            System.out.println("✨ Astra ha aprendido una nueva habilidad: " + habilidad);
        } else {
            System.out.println("⚠ Astra ya conoce esta habilidad.");
        }
    }

    public void defenderse() {
        enDefensa = true;
        System.out.println("🛡 Astra se prepara para bloquear el próximo ataque.");
    }

    public void esquivar() {
        esquivando = true;
        System.out.println("⚡ Astra intenta esquivar el próximo ataque.");
    }

    @Override
    public void recibirDanio(int cantidad) {
        if (esquivando) {
            if (random.nextInt(100) < 50) {
                System.out.println("💨 Astra esquivó el ataque!");
                esquivando = false;
                return;
            } else {
                System.out.println("⚠ Astra intentó esquivar, pero no lo logró.");
            }
        }

        if (enDefensa) {
            cantidad /= 2;
            System.out.println("🛡 Astra bloquea parte del daño. Daño reducido.");
            enDefensa = false;
        }

        super.recibirDanio(cantidad);
    }

    public void recolectarFragmento() {
        fragmentosRecolectados++;
        System.out.println("✨ Astra ha recolectado un fragmento del Trono Celestial. Total: " + fragmentosRecolectados + "/12.");

        if (fragmentosRecolectados % 3 == 0) {
            System.out.println("⭐ Astra siente un aumento en su poder tras recolectar fragmentos.");
            aumentarNivel();
        }
    }
    public void aumentarAtaqueTemporal(int cantidad) {
        System.out.println("🔥 Tu ataque aumenta en " + cantidad + " por 3 turnos!");
        ataque += cantidad;
        turnosAtaqueBuff = 3; // Se activa por 3 turnos
    }

    public void aumentarAtaque(int cantidad) {
        System.out.println(nombre + " ha mejorado su fuerza! Ataque + " + cantidad + ".");
        ataque += cantidad;
    }

    public void aumentarEnergia(int cantidad) {
        System.out.println(nombre + " recupera " + cantidad + " puntos de energía.");
        energia += cantidad;
        if (energia > 100) energia = 100; // Límite máximo de energía
    }

    public void activarEsquivaAutomatica() {
        System.out.println("⚡ Astra está en estado de alerta! Esquiva automática activada por 3 turnos.");
        esquivaAutomatica = true;
        turnosEsquiva = 3;  // La esquiva durará 3 turnos
    }

    public void recuperarEnergia() {
        System.out.println(nombre + " siente un flujo de energía! Energía restaurada.");
        energia = 100; // Restaura la energía completamente
    }

    public void actualizarTurnos() {
        if (turnosAtaqueBuff > 0) {
            turnosAtaqueBuff--;
            if (turnosAtaqueBuff == 0) {
                System.out.println("⚠️ Tu ataque vuelve a la normalidad.");
                ataque -= 10; // Restaurar el ataque original
            }
        }
        if (esquivaAutomatica) {
            turnosAtaqueBuff--;
            if (turnosAtaqueBuff == 0) {
                System.out.println("⏳ La esquiva automática ha expirado.");
                esquivaAutomatica = false;
            }
        }
    }

    public void ganarExperiencia(int cantidad) {
        experiencia += cantidad;
        System.out.println("📈 Astra gana " + cantidad + " de experiencia. Total: " + experiencia + "/" + (nivel * 10));

        while (experiencia >= nivel * 10) {
            experiencia -= nivel * 10; // La experiencia sobrante se mantiene en la siguiente subida
            aumentarNivel();
        }
    }

    public void aumentarNivel() {
        nivel++;
        vida += 20;
        ataque += 3;
        defensa += 2;
        velocidad += 1;
        energia += 10;
        System.out.println("✨ ¡Astra ha subido al nivel " + nivel + "! Sus estadísticas han mejorado.");
    }

    public void recibirAyuda(String aliado) {
        switch (aliado) {
            case "Orin, el Sabio Estelar":
                System.out.println("🔮 Orin comparte su sabiduría. Astra gana +5 defensa en este nivel.");
                this.defensa += 5;
                break;
            case "Liora, la Forjadora de Estrellas":
                System.out.println("⚒ Liora refuerza tu arma. Astra gana +3 ataque.");
                this.ataque += 3;
                break;
            case "Nix, el Príncipe Errante":
                System.out.println("🌀 Nix susurra un secreto... pero, ¿se puede confiar en él?");
                if (this.decisionesOscuras >= 2) {
                    System.out.println("⚠ Nix se aleja. 'No podemos seguir juntos, Astra...'");
                }
                break;
            case "Eira, la Guardiana del Tiempo":
                System.out.println("⏳ Eira muestra un destello del futuro. Astra siente un deja-vu.");
                break;
            case "Sygma, la Estrella Rebelde":
                System.out.println("🌠 Sygma deja caer un fragmento de estrella. Astra recupera 20 energía.");
                this.energia += 20;
                break;
            default:
                System.out.println("✨ Un poder desconocido rodea a Astra...");
        }
    }

    public void descansar() {
        energia += 15;
        vida += 10;
        System.out.println("💤 Astra descansa y recupera 15 de energía y 10 de vida.");
    }

    public void tomarDecision(String decision) {
        if (decision.equals("usar oscuridad")) {
            decisionesOscuras++;
        } else if (decision.equals("purificar")) {
            if (decisionesOscuras > 0) {
                decisionesOscuras--; // Reducción si Astra decide el camino de la luz
            }
        }
    }

    public int getTurnosDesdeUltimaHabilidad() {
        return turnosDesdeUltimaHabilidad;
    }

    public void resetTurnosHabilidad() {
        turnosDesdeUltimaHabilidad = 0;
    }

    public void incrementarTurno() {
        turnosDesdeUltimaHabilidad++;
    }


    // Métodos GET
    public int getFragmentosRecolectados() {
        return fragmentosRecolectados;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public int getDecisionesOscuras() {
        return decisionesOscuras;
    }

    // Métodos SET para Persistencia.java
    public void setVida(int nuevaVida) {
        this.vida = nuevaVida;
    }

    public void setEnergia(int nuevaEnergia) {
        this.energia = nuevaEnergia;
    }

    public void setNivel(int nuevoNivel) {
        this.nivel = nuevoNivel;
    }

    public void setFragmentosRecolectados(int nuevosFragmentos) {
        this.fragmentosRecolectados = nuevosFragmentos;
    }

    // Método para cargar datos desde otro objeto Jugador
    public void setDatos(Jugador otro) {
        this.fragmentosRecolectados = otro.fragmentosRecolectados;
        this.experiencia = otro.experiencia;
        this.nivel = otro.nivel;
        this.vida = otro.vida;
        this.energia = otro.energia;
        this.decisionesOscuras = otro.decisionesOscuras;
        this.habilidades = new ArrayList<>(otro.habilidades);
        this.habilidades.add("Golpe Celestial");  // Ataque físico
        this.habilidades.add("Rayo Estelar");     // Ataque mágico
        this.habilidades.add("Escudo Lumínico");  // Defensa mágica
        this.habilidades.add("Evasión Sombría");  // Movimiento ágil para esquivar

    }
}
