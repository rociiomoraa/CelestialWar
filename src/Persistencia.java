import java.io.*;
import java.util.Scanner;

public class Persistencia {
    private static final String CARPETA_GUARDADOS = "guardados/";

    public static void guardarPartida(Jugador jugador, int nivelActual, int dificultad, String[][] mapa) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("📝 Ingresa un nombre para la partida: ");
        String nombrePartida = scanner.nextLine().trim().replace(" ", "_");
        String archivoGuardado = CARPETA_GUARDADOS + "partida_" + nombrePartida + ".dar";

        // Asegurar que la carpeta de guardados exista
        new File(CARPETA_GUARDADOS).mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoGuardado))) {
            oos.writeObject(jugador); // Guardar objeto Jugador
            oos.writeInt(nivelActual);
            oos.writeInt(dificultad);
            oos.writeObject(mapa); // Guardar el mapa completo

            System.out.println("💾 Partida guardada exitosamente como: " + archivoGuardado);
        } catch (IOException e) {
            System.out.println("⚠ Error al guardar la partida: " + e.getMessage());
        }
    }

    public static boolean cargarPartida(Jugador jugador) {
        File carpeta = new File(CARPETA_GUARDADOS);
        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".dar"));

        if (archivos == null || archivos.length == 0) {
            System.out.println("⚠ No hay partidas guardadas.");
            return false;
        }

        System.out.println("\n📂 Partidas disponibles:");
        for (int i = 0; i < archivos.length; i++) {
            System.out.println((i + 1) + ". " + archivos[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("📜 Elige el número de la partida a cargar: ");
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine()) - 1;
            if (opcion < 0 || opcion >= archivos.length) {
                System.out.println("❌ Opción inválida.");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida.");
            return false;
        }

        File archivoSeleccionado = archivos[opcion];

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoSeleccionado))) {
            Jugador jugadorCargado = (Jugador) ois.readObject(); // Cargar objeto Jugador
            int nivelGuardado = ois.readInt();
            int dificultadGuardada = ois.readInt();
            String[][] mapaCargado = (String[][]) ois.readObject();

            // Aplicar los datos cargados al jugador actual
            jugador.setDatos(jugadorCargado);

            // Cargar el mapa en la clase Mapa
            Mapa.cargarMapa(mapaCargado);

            // Establecer la dificultad en Juego
            Juego.setDificultad(dificultadGuardada);
            Juego.setNivelActual(nivelGuardado);

            System.out.println("✅ Partida " + archivoSeleccionado.getName() + " cargada. Nivel: " + nivelGuardado);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("⚠ Error al cargar la partida.");
            return false;
        }
    }
}
