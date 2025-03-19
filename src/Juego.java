import java.util.Random;
import java.util.Scanner;

public class Juego {
    private static int dificultad;
    private static int nivelActual = 1;
    private static boolean juegoEnCurso = true;
    private static Random random = new Random();
    private static Jugador astra = new Jugador();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("══════════════════════════════════════════");
        System.out.println("           ✦ CELESTIAL WAR ✦              ");
        System.out.println("══════════════════════════════════════════");
        System.out.println("1️⃣ Iniciar nueva partida");
        System.out.println("2️⃣ Cargar partida guardada");
        System.out.print("Elige una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (opcion == 2) {
            if (!Persistencia.cargarPartida(astra)) {
                System.out.println("⚠ No se encontró ninguna partida guardada. Iniciando una nueva...");
                Introduccion.seleccionarDificultad();
            } else {
                System.out.println("✅ Partida cargada con éxito.");
                jugar();
            }
        } else {
            Introduccion.seleccionarDificultad();
        }
    }

    public static void iniciarNuevaPartida(int dificultadSeleccionada) {
        dificultad = dificultadSeleccionada; // Guardamos la dificultad elegida
        Nivel.inicializarNiveles();
        System.out.println("══════════════════════════════════════════");
        System.out.println("           ✦ INICIO DE LA AVENTURA ✦      ");
        System.out.println("══════════════════════════════════════════");
        System.out.println("Dificultad seleccionada: " + obtenerNombreDificultad());
        System.out.println("Astra despierta en la inmensidad del cosmos...");
        System.out.println("Solo hay un camino: Recuperar los Fragmentos del Trono Celestial.");
        esperar(1500);
        jugar();
    }


    public static void jugar() {
        Scanner scanner = new Scanner(System.in);

        while (juegoEnCurso) {
            System.out.println("\n══════════════════════════════════════════");
            System.out.println("✦ Nivel " + nivelActual + " - Explorando la galaxia ✦");
            System.out.println("Fragmentos recolectados: " + astra.getFragmentosRecolectados() + "/12");
            System.out.println("══════════════════════════════════════════");

            Nivel nivel = Nivel.obtenerNivel(nivelActual);
            nivel.mostrarHistoria();

            System.out.println("1. Explorar");
            System.out.println("2. Consultar estado");
            System.out.println("3. Guardar y salir");
            System.out.print("Selecciona una opción: ");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    explorarNivel();
                    break;
                case 2:
                    mostrarEstado();
                    break;
                case 3:
                    guardarYSalir();
                    break;
                default:
                    System.out.println("Opción inválida. Inténtalo de nuevo.");
            }
        }
    }

    public static void explorarNivel() {
        System.out.println("🚀 Viajando a la galaxia del nivel " + nivelActual + "...");
        esperar(2000);

        Mapa.iniciarExploracion(dificultad, nivelActual);

        if (nivelActual == 3 || nivelActual == 6 || nivelActual == 9 || nivelActual == 12) {
            enfrentarJefe();
        } else {
            avanzarNivel();
        }
    }

    public static void enfrentarJefe() {
        System.out.println("🔥 ¡Un jefe bloquea el camino de Astra! 🔥");
        Enemigo jefe = obtenerJefeDelNivel(nivelActual);
        Combate combate = new Combate(astra, jefe);
        combate.iniciar();

        if (astra.estaVivo()) {
            System.out.println("✨ Astra ha superado el desafío y puede avanzar.");
            avanzarNivel();
        } else {
            System.out.println("💀 Astra ha sido derrotada... Fin de la partida.");
            juegoEnCurso = false;
        }
    }

    public static Enemigo obtenerJefeDelNivel(int nivel) {
        switch (nivel) {
            case 3:
                return new Jefe("Tempestron, el Coloso de Plasma", 120, 50, 18, 12, 10, "Coloso de Plasma");
            case 6:
                return new Jefe("Emperador de Aurion", 150, 60, 20, 15, 12, "Centinela de Aurion");
            case 9:
                return new Jefe("Titán de la Oscuridad", 180, 70, 22, 18, 15, "Guerrero del Caos");
            case 12:
                return new Jefe("Nyxar, el Dios Cósmico", 250, 100, 30, 20, 18, "Nyxar, Dios Cósmico");
            default:
                return null;
        }
    }

    public static void avanzarNivel() {
        if (nivelActual < 12) {
            nivelActual++;
            System.out.println("🌌 Astra se prepara para la siguiente galaxia...");
            esperar(1000);
        } else {
            System.out.println("⚠ Todos los fragmentos han sido reunidos.");
            System.out.println("🛑 El enfrentamiento final con Nyxar está cerca...");

            // Aquí agregamos la decisión final
            tomarDecisionFinal();

            juegoEnCurso = false;
        }
    }

    public static void mostrarEstado() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("✦ Estado actual de Astra ✦");
        System.out.println("Nivel: " + nivelActual);
        System.out.println("Fragmentos de estrella: " + astra.getFragmentosRecolectados() + "/12");
        System.out.println("Dificultad: " + obtenerNombreDificultad());
        System.out.println("Vida: " + astra.getVida() + " | Energía: " + astra.getEnergia());
        System.out.println("══════════════════════════════════════════");
    }

    public static void guardarYSalir() {
        System.out.println("💾 Guardando progreso...");
        Persistencia.guardarPartida(astra, nivelActual, dificultad, Mapa.getMapa());
        System.out.println("✅ Juego guardado. ¡Nos vemos pronto, Astra!");
        juegoEnCurso = false;
    }

    public static String obtenerNombreDificultad() {
        switch (dificultad) {
            case 1:
                return "Fácil";
            case 2:
                return "Normal";
            case 3:
                return "Difícil";
            default:
                return "Desconocida";
        }
    }

    public static void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setDificultad(int nuevaDificultad) {
        dificultad = nuevaDificultad;
    }

    public static void setNivelActual(int nuevoNivel) {
        nivelActual = nuevoNivel;
    }

    public static void eventoImportante() {
        System.out.println("\n✨ Astra se encuentra en una encrucijada cósmica...");
        Decision.tomarDecision(
                "Un fragmento del Trono Celestial está siendo corrompido por la oscuridad. ¿Qué debería hacer Astra?",
                "🌟 Purificar el fragmento con su luz.",
                "✨ Dejar el fragmento en su estado actual.",
                "🌑 Absorber la oscuridad y usar su poder."
        );
    }

    public static void tomarDecisionFinal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n💫 **Decisión Final** 💫");
        System.out.println("Astra se encuentra frente a Nyxar... La batalla ha terminado, pero una última elección definirá el destino del universo.");

        System.out.println("1️⃣ Restaurar el Trono Celestial y devolver la luz al universo. (Final Bueno 🌟)");
        System.out.println("2️⃣ Absorber el poder del trono y convertirse en la Diosa de las Estrellas. (Final Neutral ✨)");
        System.out.println("3️⃣ Unirse a Nyxar y sumergir el universo en oscuridad. (Final Malo 🌑)");

        System.out.print("🌀 ¿Qué decisión tomará Astra? ");

        int eleccionFinal;
        while (true) {
            try {
                eleccionFinal = Integer.parseInt(scanner.nextLine().trim());
                if (eleccionFinal >= 1 && eleccionFinal <= 3) {
                    break;
                } else {
                    System.out.print("❌ Opción inválida. Elige 1, 2 o 3: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("❌ Entrada no válida. Introduce un número: ");
            }
        }

        Finales.mostrarFinal(eleccionFinal);
    }

    public static void setJugador(Jugador nuevoJugador) {
        astra = nuevoJugador;
    }

}
