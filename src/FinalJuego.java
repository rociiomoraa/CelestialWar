public class FinalJuego {
    public static void determinarFinal() {
        int karma = Decision.getKarma();

        System.out.println("\n🌠 El destino de Astra está decidido...");

        if (karma >= 2) {
            System.out.println("🌟 **Final Bueno:** Astra ha restaurado el Trono Celestial y ha devuelto la luz al universo.");
        } else if (karma == 0 || karma == 1) {
            System.out.println("✨ **Final Neutral:** Astra se convierte en la Diosa de las Estrellas, sacrificando su humanidad.");
        } else {
            System.out.println("🌑 **Final Malo:** Astra ha abrazado la oscuridad y se ha unido a Nyxar.");
        }
    }
}
