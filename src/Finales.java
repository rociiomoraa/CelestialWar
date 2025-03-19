import java.util.Scanner;

public class Finales {
    public static void mostrarFinal(int eleccionFinal) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🌌 **Decisión Final** 🌌");
        System.out.println("Astra se encuentra frente a Nyxar... La batalla ha terminado, pero una última elección definirá el destino del universo.");

        switch (eleccionFinal) {
            case 1:
                finalBueno();
                break;
            case 2:
                finalNeutral();
                break;
            case 3:
                finalMalo();
                break;
            default:
                System.out.println("❌ Elección inválida.");
        }

        System.out.println("\n🎬 Presiona ENTER para continuar...");
        scanner.nextLine();
        System.out.println("✨ Fin del juego. ¡Gracias por jugar *Celestial War*! ✨");
    }

    private static void finalBueno() {
        System.out.println("\n🌟 **Final Bueno: El Renacer del Trono Celestial** 🌟");
        System.out.println("Astra, con el último fragmento en su poder, se arrodilla ante el Trono Celestial.");
        System.out.println("La luz comienza a envolver la galaxia... La oscuridad retrocede...");
        System.out.println("Nyxar, debilitado, lanza un último rugido antes de desvanecerse en el vacío.");
        System.out.println("Las estrellas vuelven a brillar y los aliados de Astra aparecen a su lado.");
        System.out.println("\n🔮 **Eira, la Guardiana del Tiempo:** 'El ciclo ha sido restaurado, Astra...'");
        System.out.println("👑 Astra toma su lugar como la nueva regente del Trono Celestial, guiando al universo hacia una era de paz y luz eterna.");
    }

    private static void finalNeutral() {
        System.out.println("\n✨ **Final Neutral: La Diosa de las Estrellas** ✨");
        System.out.println("Astra se eleva sobre el Trono Celestial, pero en lugar de restaurarlo, absorbe su poder.");
        System.out.println("Las estrellas comienzan a girar en torno a ella, su cuerpo se disuelve en luz.");
        System.out.println("\n🌀 **Nix, el Príncipe Errante:** '¡Astra, no puedes hacer esto...!' ");
        System.out.println("Con una última sonrisa, Astra se convierte en la Diosa de las Estrellas, abandonando su humanidad para guiar el cosmos.");
        System.out.println("El universo sigue su curso... pero sin una soberana real. Las galaxias vagan sin dirección, esperando su destino.");
    }

    private static void finalMalo() {
        System.out.println("\n🌑 **Final Malo: La Era de la Oscuridad** 🌑");
        System.out.println("Astra observa el Trono Celestial... y se vuelve hacia Nyxar.");
        System.out.println("Con una voz tranquila, dice: 'Tienes razón, la oscuridad no es el fin... es el comienzo.'");
        System.out.println("\n🔥 **Liora, la Forjadora de Estrellas:** '¡No, Astra, por favor!'");
        System.out.println("Astra extiende su mano y la galaxia es consumida por las sombras.");
        System.out.println("Los aliados caen uno por uno... El universo es envuelto en un eterno eclipse.");
        System.out.println("\n🌓 **Nyxar, el Dios Cósmico:** 'Bienvenida, Astra. Ahora gobernaremos la oscuridad juntos...'");
    }
}
