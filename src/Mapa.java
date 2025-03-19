import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Mapa {
    private static final int MAX_TAMANO = 50;
    private static int tamanoMapa;
    private static String[][] mapa;
    private static int posX, posY;
    private static int nivelActual = 1;
    private static int dificultad;
    private static Random random = new Random();
    private static Jugador jugador;
    private static boolean juegoEnCurso = true;
    private static int fragmentosNivel = 0;
    private static int totalFragmentosNivel;

    // 🔹 **Aliados con emojis únicos y diálogos personalizados**
    private static final Map<String, Aliado> aliados = new HashMap<>();
    static {
        aliados.put("🔧", Aliado.crearAliado("Orin"));
        aliados.put("⚒", Aliado.crearAliado("Liora"));
        aliados.put("🌀", Aliado.crearAliado("Nix"));
        aliados.put("⏳", Aliado.crearAliado("Eira"));
        aliados.put("🌠", Aliado.crearAliado("Sygma"));
    }

    private static final Map<String, int[]> posicionesAliados = new HashMap<>();

    private static final List<String> ayudas = Arrays.asList(
            "🛡 Escudo Protector",
            "💫 Energía Cósmica",
            "🔮 Visión del Destino",
            "🌟 Bendición de las Estrellas"
    );

    public static void iniciarExploracion(int dificultadSeleccionada, int nivel) {
        dificultad = dificultadSeleccionada;
        nivelActual = nivel;
        tamanoMapa = calcularTamanoMapa();
        mapa = new String[tamanoMapa][tamanoMapa];
        inicializarMapa();

        Scanner scanner = new Scanner(System.in);
        juegoEnCurso = true;

        while (juegoEnCurso) {
            imprimirMapa();
            imprimirLeyenda();
            System.out.print("🔼 Movimiento (WASD) | E (Salir): ");
            char movimiento = scanner.next().toLowerCase().charAt(0);

            if (movimiento == 'e') {
                System.out.println("🚀 Astra deja de explorar y regresa al menú principal...");
                juegoEnCurso = false;
            } else {
                moverJugador(movimiento);
                verificarEvento();
                jugador.actualizarTurnos(); // 🔹 Reducimos los turnos de habilidades temporales
            }
        }
    }

    private static int calcularTamanoMapa() {
        switch (dificultad) {
            case 1: return 15 + random.nextInt(6);
            case 2: return 25 + random.nextInt(6);
            case 3: return 40 + random.nextInt(11);
            default: return 25;
        }
    }

    private static void inicializarMapa() {
        for (int i = 0; i < tamanoMapa; i++) {
            for (int j = 0; j < tamanoMapa; j++) {
                mapa[i][j] = "·";
            }
        }
        posX = tamanoMapa / 2;
        posY = tamanoMapa / 2;
        mapa[posX][posY] = "👸";
        generarEventos();

        generarPosicionesAliados();

    }

    private static void generarPosicionesAliados() {
        for (Map.Entry<String, Aliado> entry : aliados.entrySet()) {
            String emoji = entry.getKey();
            Aliado aliado = entry.getValue();

            int x, y;
            do {
                x = random.nextInt(tamanoMapa);
                y = random.nextInt(tamanoMapa);
            } while (!mapa[x][y].equals("·")); // Asegura que sea una casilla vacía

            mapa[x][y] = emoji;
            posicionesAliados.put(emoji, new int[]{x, y});
        }
    }

    private static void generarEventos() {
        Nivel nivel = Nivel.obtenerNivel(nivelActual);
        List<String> enemigos = nivel.getEnemigos();
        List<String> eventos = nivel.getEventos();

        int cantidadEventos = calcularCantidadEventos();
        totalFragmentosNivel = 0;

        for (int i = 0; i < cantidadEventos; i++) {
            int x = random.nextInt(tamanoMapa);
            int y = random.nextInt(tamanoMapa);
            if (mapa[x][y].equals("·")) {
                String evento = obtenerEventoAleatorio(enemigos, eventos);
                mapa[x][y] = evento;

                if (evento.equals("✨")) {
                    totalFragmentosNivel++;
                }
            }
        }

        // 🔹 **Generar aliados en posiciones aleatorias**
        for (Map.Entry<String, Aliado> entry : aliados.entrySet()) {
            String emoji = entry.getKey();
            Aliado aliado = entry.getValue();

            int x, y;
            do {
                x = random.nextInt(tamanoMapa);
                y = random.nextInt(tamanoMapa);
            } while (!mapa[x][y].equals("·"));

            mapa[x][y] = emoji; // Representamos al aliado con su emoji en el mapa
            posicionesAliados.put(emoji, new int[]{x, y});
        }


        // 🔹 **Generar ayudas en posiciones aleatorias**
        for (String ayuda : ayudas) {
            int x, y;
            do {
                x = random.nextInt(tamanoMapa);
                y = random.nextInt(tamanoMapa);
            } while (!mapa[x][y].equals("·"));
            mapa[x][y] = ayuda.split(" ")[0];
        }

        fragmentosNivel = 0;
    }

    private static int calcularCantidadEventos() {
        switch (dificultad) {
            case 1: return (tamanoMapa * tamanoMapa) / 20;
            case 2: return (tamanoMapa * tamanoMapa) / 15;
            case 3: return (tamanoMapa * tamanoMapa) / 10;
            default: return (tamanoMapa * tamanoMapa) / 15;
        }
    }

    private static String obtenerEventoAleatorio(List<String> enemigos, List<String> eventos) {
        int totalOpciones = enemigos.size() + eventos.size();
        int index = random.nextInt(totalOpciones);

        if (index < enemigos.size()) {
            return enemigos.get(index).split(" ")[0]; // Solo tomamos el emoji del enemigo
        } else {
            return eventos.get(index - enemigos.size()).split(" ")[0]; // Solo tomamos el emoji del evento
        }
    }

    private static void imprimirMapa() {
        System.out.println("\n🌌 Galaxia " + nivelActual + " - Exploración");
        for (int i = 0; i < tamanoMapa; i++) {
            for (int j = 0; j < tamanoMapa; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void imprimirLeyenda() {
        StringBuilder leyenda = new StringBuilder("\n📜 **Leyenda:** ");
        leyenda.append("👸 Astra | ");

        for (Map.Entry<String, Aliado> entry : aliados.entrySet()) {
            if (entry.getValue() != null) {
                leyenda.append(entry.getKey()).append(" ").append(entry.getValue().getNombre()).append(" | ");
            }
        }

        for (String ayuda : ayudas) {
            leyenda.append(ayuda).append(" | ");
        }
        leyenda.append("· Espacio vacío");

        System.out.println(leyenda);
        System.out.println("\n🎮 **Controles:** W (Arriba) | S (Abajo) | A (Izquierda) | D (Derecha) | E (Salir)");
    }

    private static void moverJugador(char direccion) {
        int nuevaX = posX, nuevaY = posY;

        switch (direccion) {
            case 'w': nuevaX--; break;
            case 's': nuevaX++; break;
            case 'a': nuevaY--; break;
            case 'd': nuevaY++; break;
            default:
                System.out.println("❌ Movimiento inválido. Usa WASD.");
                return;
        }

        if (nuevaX >= 0 && nuevaX < tamanoMapa && nuevaY >= 0 && nuevaY < tamanoMapa) {
            mapa[posX][posY] = "·"; // Limpiar posición anterior
            posX = nuevaX;
            posY = nuevaY;
            mapa[posX][posY] = "👸"; // Nueva posición de Astra
        } else {
            System.out.println("❌ No puedes salir del área explorada.");
        }
    }

    private static void verificarEvento() {
        String evento = mapa[posX][posY];

        // 📌 Detectar si el jugador pisa un fragmento de estrella
        if (evento.equals("✨")) {
            fragmentosNivel++;
            System.out.println("🌟 Astra ha encontrado un Fragmento de Estrella. (" + fragmentosNivel + "/" + totalFragmentosNivel + ")");
            if (fragmentosNivel == totalFragmentosNivel) {
                System.out.println("✨ ¡Has recolectado todos los fragmentos de esta galaxia! Ahora puedes avanzar.");
            }
        }

        // 📌 Detectar si el jugador pisa una ayuda
        else if (ayudas.stream().anyMatch(a -> a.startsWith(evento))) {
            System.out.println("✨ Astra ha activado una ayuda: " + buscarNombre(ayudas, evento));
        }

        // 📌 Detectar si el jugador encuentra un aliado
        else if (aliados.containsKey(evento)) {
            Aliado aliado = aliados.get(evento);
            if (aliado != null) {
                aliado.interactuar(jugador);
                aliado.usarHabilidad(jugador);
            } else {
                System.out.println("⚠ Error: El aliado no está correctamente asignado.");
            }
        }

        // 📌 Detectar si el jugador pisa un enemigo
        else {
            Nivel nivel = Nivel.obtenerNivel(nivelActual);
            List<String> enemigos = nivel.getEnemigos();

            if (enemigos.stream().anyMatch(e -> e.startsWith(evento))) {
                System.out.println("⚔ ¡Un " + buscarNombre(enemigos, evento) + " aparece! Prepárate para la batalla.");
            }
            // 📌 Detectar si el jugador encuentra un aliado
            // 📌 Detectar si el jugador encuentra un aliado y activar su habilidad
            else if (aliados.containsKey(evento)) {
                System.out.println("\n💬 " + aliados.get(evento));
                aplicarHabilidadAliado(evento);
            }
        }
    }

    // 📌 Método para aplicar las habilidades de los aliados a Astra
    private static void aplicarHabilidadAliado(String aliado) {
        if (jugador == null) {
            System.out.println("⚠ Error: No se ha inicializado el jugador.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n💬 " + aliados.get(aliado));
        System.out.print("¿Quieres aceptar la ayuda de este aliado? (S/N): ");
        char decision = scanner.next().toLowerCase().charAt(0);

        if (jugador.getTurnosDesdeUltimaHabilidad() < Jugador.COOLDOWN_HABILIDAD) {
            System.out.println("⏳ Debes esperar más turnos antes de recibir ayuda nuevamente.");
            return;
        }
        jugador.resetTurnosHabilidad();

        if (decision == 's') {
            switch (aliado) {
                case "🔧": jugador.aumentarAtaqueTemporal(10); break;
                case "⚒": jugador.aumentarAtaque(5); break;
                case "🌀": jugador.aumentarEnergia(10); break;
                case "⏳": jugador.activarEsquivaAutomatica(); break;
                case "🌠": jugador.recuperarEnergia(); break;
                default: System.out.println("✨ Un misterioso poder rodea a Astra...");
            }
            System.out.println("✨ Has aceptado la ayuda de " + aliados.get(aliado).getNombre() + "!");
        } else {
            System.out.println("❌ Has rechazado la ayuda de " + aliados.get(aliado).getNombre() + ".");
        }
    }

    private static String buscarNombre(List<String> lista, String emoji) {
        return lista.stream()
                .filter(e -> e.startsWith(emoji))
                .findFirst()
                .orElse("evento desconocido")
                .substring(2);
    }

    public static int getTamanoMapa() {
        return tamanoMapa;
    }

    public static void cargarMapa(String[][] mapaCargado) {
        if (mapaCargado != null) {
            mapa = mapaCargado;
            tamanoMapa = mapa.length;
            juegoEnCurso = true; // Restaurar estado del juego
            System.out.println("🌌 Mapa restaurado correctamente.");
        } else {
            System.out.println("⚠ No se pudo restaurar el mapa.");
        }
    }

    private static void mostrarDialogoAliado(String aliado) {
        int karma = Decision.getKarma();

        switch (aliado) {
            case "🔧":
                System.out.println(karma >= 1 ? "🔧 Orin: 'Astra, tu luz brilla con fuerza. No pierdas el camino.'" : "🔧 Orin: 'Temo que la oscuridad esté invadiendo tu alma, Astra.'");
                break;
            case "⚒":
                System.out.println(karma >= 1 ? "⚒ Liora: 'Si sigues luchando, puedo mejorar tus armas con fragmentos de luz.'" : "⚒ Liora: '¿Seguirás en este camino oscuro? Espero que no me obligues a luchar contra ti.'");
                break;
            case "🌀":
                System.out.println(karma < 0 ? "🌀 Nix: 'Parece que nuestras intenciones ahora coinciden, Astra...'" : "🌀 Nix: 'No sé si puedo confiar en ti, pero el destino nos une.'");
                break;
            case "⏳":
                System.out.println(karma >= 1 ? "⏳ Eira: 'Astra, he visto futuros oscuros, pero el tuyo sigue siendo incierto.'" : "⏳ Eira: 'El tiempo me dice que estás cambiando... para bien o para mal.'");
                break;
            case "🌠":
                System.out.println(karma >= 1 ? "🌠 Sygma: 'Las estrellas te observan, Astra. Sigues el camino correcto.'" : "🌠 Sygma: 'Algo en ti ha cambiado. ¿Todavía sigues buscando la verdad?'");
                break;
            default:
                System.out.println("✨ Un misterioso poder rodea a Astra...");
        }
    }

    // 🔹 Obtiene el nombre de la ayuda a partir de su emoji
    private static String obtenerNombreAyuda(String emoji) {
        for (String ayuda : ayudas) {
            if (ayuda.startsWith(emoji)) {
                return ayuda.substring(2); // Extrae el nombre eliminando el emoji
            }
        }
        return "ayuda desconocida";
    }

    // 🔹 Genera un diálogo dependiendo del tipo de ayuda encontrada
    private static String obtenerDialogoAyuda(String emoji) {
        switch (emoji) {
            case "🛡":
                return "**Escudo Protector:** 'Astra, las sombras acechan. Usa este escudo para resistir mejor sus ataques.'";
            case "💫":
                return "**Energía Cósmica:** 'Tu espíritu brilla como una estrella. Absorbe esta energía para seguir adelante.'";
            case "🔮":
                return "**Visión del Destino:** 'El futuro es incierto... pero puedo mostrarte una posibilidad. Escucha con atención...'";
            case "🌟":
                return "**Bendición de las Estrellas:** 'Astra, tu luz es inquebrantable. Las estrellas te protegerán en esta batalla.'";
            default:
                return "✨ Un misterioso poder rodea a Astra...";
        }
    }

    public static String[][] getMapa() {
        return mapa;
    }
}
