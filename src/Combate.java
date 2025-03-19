import java.util.Scanner;

public class Combate {
    private Jugador jugador;
    private Enemigo enemigo;
    private Scanner scanner;

    public Combate(Jugador jugador, Enemigo enemigo) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("\n⚔ ¡Combate contra " + enemigo.getNombre() + " ha comenzado! ⚔");

        while (jugador.estaVivo() && enemigo.estaVivo()) {
            mostrarEstado();
            if (jugador.getVelocidad() >= enemigo.getVelocidad()) {
                turnoJugador();
                if (!enemigo.estaVivo()) break;
                turnoEnemigo();
            } else {
                turnoEnemigo();
                if (!jugador.estaVivo()) break;
                turnoJugador();
            }
        }

        finalizarCombate();
    }

    private void mostrarEstado() {
        System.out.println("\n📊 Estado actual:");
        System.out.println("🔹 " + jugador.getNombre() + " | Vida: " + jugador.getVida() + " | Energía: " + jugador.getEnergia());
        System.out.println("🔻 " + enemigo.getNombre() + " | Vida: " + enemigo.getVida() + " | Nivel: "
                + (enemigo instanceof Jefe ? ((Jefe) enemigo).getFase() : "Normal"));
    }

    public void turnoJugador() {
        System.out.println("\n🎮 Tu turno:");
        System.out.println("1️⃣ Atacar");
        System.out.println("2️⃣ Usar habilidad");
        System.out.println("3️⃣ Defenderse");
        System.out.println("4️⃣ Esquivar");
        System.out.println("5️⃣ Descansar");
        System.out.println("6️⃣ Huir");
        System.out.print("Elige una acción: ");

        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                jugador.atacar(enemigo);
                break;
            case 2:
                System.out.println("\n🔮 Habilidades disponibles:");
                for (String habilidad : jugador.habilidades) {
                    System.out.println("✨ " + habilidad);
                }
                System.out.print("Elige una habilidad: ");
                String habilidad = scanner.next();
                jugador.usarHabilidad(habilidad, enemigo);
                break;
            case 3:
                jugador.defenderse();
                break;
            case 4:
                jugador.esquivar();
                break;
            case 5:
                jugador.descansar();
                break;
            case 6:
                System.out.println("🏃‍♀️ Astra escapa del combate.");
                return;
            default:
                System.out.println("❌ Opción inválida.");
                turnoJugador();
        }
    }

    private void usarHabilidad() {
        if (jugador.habilidades.isEmpty()) {
            System.out.println("⚠ No tienes habilidades disponibles.");
            return;
        }

        System.out.println("\n🔮 Habilidades disponibles:");
        for (String habilidad : jugador.habilidades) {
            System.out.println("✨ " + habilidad);
        }
        System.out.print("Elige una habilidad: ");
        String habilidad = scanner.next();
        jugador.usarHabilidad(habilidad, enemigo);
    }

    private void turnoEnemigo() {
        System.out.println("\n⚠ Turno de " + enemigo.getNombre() + "...");

        if (enemigo instanceof Jefe) {
            Jefe jefe = (Jefe) enemigo;

            if (jefe.estaEnFuria()) {
                System.out.println("🔥 " + jefe.getNombre() + " está en MODO FURIA y ataca con furia incontrolable!");
                jefe.atacar(jugador);
                return;
            }

            if (jefe.getVida() < 50 && jefe.getFase() == 1) {
                jefe.regenerarse();
            }

            if (jefe.getEnergia() > 10 && Math.random() < 0.3) {
                jefe.habilidadFinal(jugador);
            } else {
                jefe.atacar(jugador);
            }

        } else {
            enemigoNormalAtacar();
        }
    }

    private void enemigoNormalAtacar() {
        int decision = (int) (Math.random() * 100);

        if (decision < 50) {
            enemigo.atacar(jugador);
        } else if (decision < 75 && enemigo.getEnergia() >= 10) {
            enemigo.ataqueFuerte(jugador);
        } else if (decision < 90) {
            enemigo.defenderse();
        } else {
            enemigo.recuperarEnergia();
        }
    }

    private void finalizarCombate() {
        if (jugador.estaVivo()) {
            System.out.println("🏆 ¡Astra ha vencido a " + enemigo.getNombre() + "!");
            jugador.ganarExperiencia(20);

            if (enemigo.esJefe()) {
                System.out.println("🌟 ¡Has derrotado a un jefe! Obtienes un fragmento del Trono Celestial.");
                jugador.recolectarFragmento();
            }
        } else {
            System.out.println("💀 Astra ha sido derrotada...");
        }
    }
}
