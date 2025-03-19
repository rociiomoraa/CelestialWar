import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class Nivel {
    private int numero;
    private String nombre;
    private String historia;
    private String desafioEspecial;
    private List<String> enemigos; // Lista de enemigos únicos por nivel
    private List<String> eventos;  // Lista de eventos/trampas únicas por nivel
    private static final Map<Integer, Nivel> niveles = new HashMap<>();

    public Nivel(int numero, String nombre, String historia, String desafioEspecial, List<String> enemigos, List<String> eventos) {
        this.numero = numero;
        this.nombre = nombre;
        this.historia = historia;
        this.desafioEspecial = desafioEspecial;
        this.enemigos = enemigos;
        this.eventos = eventos;
    }

    public static void inicializarNiveles() {
        niveles.put(1, new Nivel(1, "La Galaxia Crepuscular",
                "Astra llega a una galaxia en penumbra, donde los Espíritus de las Estrellas la guían.",
                "Derrota a los Centinelas del Vacío para avanzar.",
                Arrays.asList("👁‍🗨 Centinela del Vacío"),
                Arrays.asList("✨ Fragmento de Estrella", "⚠ Neblina Oscura")));

        niveles.put(2, new Nivel(2, "La Nebulosa de Cristal",
                "Los fragmentos del Trono están atrapados en asteroides de cristal. Astra debe esquivar meteoritos y resolver acertijos.",
                "Rompe los asteroides sin ser golpeada por los meteoritos.",
                Arrays.asList("🪨 Gólem de Cristal"),
                Arrays.asList("✨ Fragmento de Estrella", "☄ Lluvia de Meteoritos")));

        niveles.put(3, new Nivel(3, "El Cinturón de Tormentas",
                "Un océano de energía intergaláctica bloquea el camino. Astra obtiene un artefacto de control gravitacional.",
                "Enfrenta a Tempestron, el coloso de plasma.",
                Arrays.asList("⚡ Espíritu de Tormenta"),
                Arrays.asList("✨ Fragmento de Estrella", "🌩 Descarga Eléctrica")));

        niveles.put(4, new Nivel(4, "La Ciudad de los Astros Caídos",
                "Astra encuentra un reino de estrellas olvidadas, algunas corrompidas por la sombra de Nyxar.",
                "Purifica las estrellas y derrota a las sombras corruptas.",
                Arrays.asList("👤 Sombra Estelar"),
                Arrays.asList("✨ Fragmento de Estrella", "💀 Presencia Maligna")));

        niveles.put(5, new Nivel(5, "El Pozo de la Singularidad",
                "Un agujero negro amenaza con devorarlo todo. Astra debe encontrar un camino seguro.",
                "Evita ser absorbida por la gravedad del agujero negro.",
                Arrays.asList("🌑 Guardián de la Singularidad"),
                Arrays.asList("✨ Fragmento de Estrella", "⚫ Atracción Gravitacional")));

        niveles.put(6, new Nivel(6, "El Reino Perdido de Aurion",
                "Un antiguo imperio espacial en ruinas. Sus habitantes la confunden con una enemiga.",
                "Gana la confianza de los habitantes para obtener el fragmento.",
                Arrays.asList("🏰 Centinela de Aurion"),
                Arrays.asList("✨ Fragmento de Estrella", "⚔ Prueba del Guerrero")));

        niveles.put(7, new Nivel(7, "La Galaxia Fantasma",
                "Estrellas muertas siguen brillando en esta galaxia ilusoria.",
                "Rompe las ilusiones y enfrenta a los espectros cósmicos.",
                Arrays.asList("👻 Espectro Cósmico"),
                Arrays.asList("✨ Fragmento de Estrella", "🌀 Distorsión Espacial")));

        niveles.put(8, new Nivel(8, "El Laberinto de Cometas",
                "Un caos de cometas en constante movimiento, donde cazadores oscuros persiguen a Astra.",
                "Esquiva los cometas y huye de los Cazadores de Sombras.",
                Arrays.asList("🏹 Cazador de Sombras"),
                Arrays.asList("✨ Fragmento de Estrella", "💫 Trampa de Cometas")));

        niveles.put(9, new Nivel(9, "La Frontera del Caos",
                "Un campo de batalla estelar donde los restos de antiguas guerras flotan en el vacío.",
                "Derrota al Titán de la Oscuridad para obtener el fragmento.",
                Arrays.asList("🛡 Guerrero del Caos"),
                Arrays.asList("✨ Fragmento de Estrella", "🔥 Llamas del Conflicto")));

        niveles.put(10, new Nivel(10, "El Corazón del Cosmos",
                "Astra descubre que el Trono Celestial es más que un objeto, es parte de su propio destino.",
                "Decide si renunciar a su humanidad o continuar su misión.",
                Arrays.asList("💫 Guardián del Destino"),
                Arrays.asList("✨ Fragmento de Estrella", "🌠 Visión Celestial")));

        niveles.put(11, new Nivel(11, "El Último Umbral",
                "Nyxar detecta su presencia y la desafía en un duelo en el borde del universo.",
                "Usa todo lo aprendido para enfrentarlo.",
                Arrays.asList("🌀 Eco de Nyxar"),
                Arrays.asList("✨ Fragmento de Estrella", "🔮 Ruptura Dimensional")));

        niveles.put(12, new Nivel(12, "El Abismo de Nyxar",
                "La batalla final ha llegado. Astra entra en el dominio de Nyxar, donde la realidad se distorsiona.",
                "Decide el destino del universo: Restaurar la luz, convertirse en diosa o abrazar el caos.",
                Arrays.asList("🛑 Nyxar, el Dios Cósmico"),
                Arrays.asList("✨ Último Fragmento", "⚠ Colapso de la Realidad")));
    }

    public static Nivel obtenerNivel(int numero) {
        return niveles.get(numero);
    }

    public void mostrarHistoria() {
        System.out.println("\n🌌 **" + nombre + "**");
        System.out.println(historia);
        System.out.println("🔥 **Desafío del Nivel:** " + desafioEspecial);
        System.out.println("\n⚔ **Enemigos en esta galaxia:** " + String.join(", ", enemigos));
        System.out.println("\n🌠 **Eventos y trampas:** " + String.join(", ", eventos));
        System.out.println();
    }

    public List<String> getEnemigos() {
        return enemigos;
    }

    public List<String> getEventos() {
        return eventos;
    }
}
