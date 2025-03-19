import java.util.Scanner;

public class Introduccion {
    private static int dificultad = 2; // Dificultad por defecto: Normal

    public static void main(String[] args) {
        mostrarIntroduccion();
        mostrarMenu();
    }

    public static void mostrarIntroduccion() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("══════════════════════════════════════════");
        System.out.println("             ✦ CELESTIAL WAR ✦           ");
        System.out.println("══════════════════════════════════════════");

        esperar(1000);
        mostrarTextoLento("Hace eones, más allá de las galaxias conocidas...");
        mostrarTextoLento("Existía un lugar donde la luz y la oscuridad coexistían en armonía: el Reino Celestial de Luminara.");
        mostrarTextoLento("Gobernado por la familia Astralis, este reino protegía el balance del universo, guiando el destino de las estrellas.");
        System.out.println();

        mostrarTextoLento("Pero la armonía no dura para siempre...");
        esperar(1000);

        mostrarTextoLento("En la noche de su coronación, la princesa Astra vio cómo los cielos se desgarraban.");
        mostrarTextoLento("Desde la Nebulosa del Olvido, emergió una presencia ancestral...");
        esperar(1000);

        System.out.println();
        System.out.println("⚠ ¡ALARMA! ⚠");
        System.out.println();

        mostrarTextoLento("«El devorador de mundos... ha despertado...»");
        mostrarTextoLento("Nyxar, el Dios Cósmico, descendió sobre Luminara como una sombra infinita.");
        mostrarTextoLento("El Trono Celestial fue destruido en un solo instante, y sus fragmentos fueron esparcidos por el cosmos.");
        System.out.println();

        mostrarTextoLento("Astra despertó en la inmensidad del vacío. Su hogar... reducido a cenizas.");
        mostrarTextoLento("Ahora, solo queda una última esperanza...");
        esperar(1000);

        System.out.println();
        mostrarTextoLento("«Recolecta los 12 Fragmentos del Trono Celestial...»");
        mostrarTextoLento("«Derrota a los guardianes de la oscuridad...»");
        mostrarTextoLento("«Y enfréntate a Nyxar antes de que el universo se consuma en sombras...»");

        esperar(2000);
        System.out.println();
        System.out.println("══════════════════════════════════════════");
        System.out.println();

        System.out.println("   Presiona ENTER para continuar...");
        scanner.nextLine(); // Espera a que el usuario presione ENTER antes de avanzar
    }

    public static void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        while (true) { // Repetimos hasta que el jugador inicie la partida o salga
            System.out.println("\n✦ MENÚ PRINCIPAL ✦");
            System.out.println("1. Iniciar Nueva Partida");
            System.out.println("2. Cargar Partida");
            System.out.println("3. Seleccionar Dificultad");
            System.out.println("4. Ver Reglas");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    iniciarNuevaPartida();
                    return; // Salimos del menú después de iniciar la partida
                case 2:
                    cargarPartida();
                    return;
                case 3:
                    seleccionarDificultad();
                    break;
                case 4:
                    mostrarReglas();
                    break;
                case 5:
                    System.out.println("¡Gracias por jugar a Celestial War! ¡Nos vemos en otra batalla!");
                    return; // Cierra el programa correctamente
                default:
                    System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        }
    }

    public static void iniciarNuevaPartida() {
        System.out.println("\n🌟 Iniciando una nueva aventura...");
        System.out.println("Dificultad seleccionada: " + obtenerNombreDificultad());
        System.out.println("Cargando galaxias... Prepara tu viaje, Astra.");
        esperar(2000);

        // Iniciar el juego con la dificultad seleccionada
        Juego.iniciarNuevaPartida(dificultad);
    }

    public static void cargarPartida() {
        System.out.println("\n💾 Buscando partidas guardadas...");

        Jugador jugadorCargado = new Jugador();
        boolean partidaCargada = Persistencia.cargarPartida(jugadorCargado);

        if (partidaCargada) {
            System.out.println("✅ Partida cargada con éxito.");
            Juego.setJugador(jugadorCargado);
            Juego.jugar(); // Iniciar la partida cargada
        } else {
            System.out.println("❌ No se encontraron partidas guardadas. Iniciando una nueva aventura.");
            iniciarNuevaPartida();
        }
    }


    public static void seleccionarDificultad() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n✦ SELECCIÓN DE DIFICULTAD ✦");
        System.out.println("1. Fácil (Para una experiencia más relajada)");
        System.out.println("2. Normal (Equilibrio entre desafío y aventura)");
        System.out.println("3. Difícil (Para los valientes que desafían el cosmos)");
        System.out.print("Elige tu nivel de dificultad: ");

        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                dificultad = 1;
                System.out.println("🔵 Has seleccionado la dificultad FÁCIL.");
                break;
            case 2:
                dificultad = 2;
                System.out.println("🟢 Has seleccionado la dificultad NORMAL.");
                break;
            case 3:
                dificultad = 3;
                System.out.println("🔴 Has seleccionado la dificultad DIFÍCIL.");
                break;
            default:
                System.out.println("⚠ Opción inválida. Seleccionando dificultad NORMAL por defecto.");
                dificultad = 2;
        }
    }

    public static void mostrarReglas() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("✦ REGLAS DE CELESTIAL WAR ✦");
        System.out.println("1. Explora las 12 galaxias en busca de los fragmentos del Trono Celestial.");
        System.out.println("2. Derrota a los enemigos y jefes intermedios en los niveles 3, 6 y 9.");
        System.out.println("3. Los fragmentos de estrella te permitirán enfrentarte a Nyxar en el nivel 12.");
        System.out.println("4. Usa sabiamente a tus aliados, cada uno tiene un rol específico.");
        System.out.println("5. Si caes en combate, puedes intentarlo de nuevo, pero el universo depende de ti.");
        System.out.println("══════════════════════════════════════════");
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

    public static void mostrarTextoLento(String texto) {
        for (char c : texto.toCharArray()) {
            System.out.print(c);
            esperar(30);
        }
        System.out.println();
    }
}
