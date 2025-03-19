import java.util.Scanner;

public class Decision {
    private static int karma = 0; // Afecta el final del juego y la actitud de los aliados
    private static Scanner scanner = new Scanner(System.in);

    public static void tomarDecision(String situacion, String opcion1, String opcion2, String opcion3) {
        System.out.println("\n🔮 **Decisión Importante** 🔮");
        System.out.println(situacion);
        System.out.println("1️⃣ " + opcion1);
        System.out.println("2️⃣ " + opcion2);
        System.out.println("3️⃣ " + opcion3);
        System.out.print("👉 Elige una opción: ");

        int eleccion = scanner.nextInt();

        switch (eleccion) {
            case 1:
                karma += 1; // Buen karma
                System.out.println("🌟 Astra ha tomado una decisión noble.");
                break;
            case 2:
                karma += 0; // Neutral
                System.out.println("✨ Astra ha tomado una decisión equilibrada.");
                break;
            case 3:
                karma -= 1; // Mal karma
                System.out.println("🌑 Astra ha tomado un camino más oscuro...");
                break;
            default:
                System.out.println("❌ Opción inválida.");
                tomarDecision(situacion, opcion1, opcion2, opcion3);
        }
    }

    public static int getKarma() {
        return karma;
    }
}
