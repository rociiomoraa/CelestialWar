public class Jefe extends Enemigo {
    private int fase; // Fase de combate (cambia a Fase 2 con vida <50%)
    private boolean enFuria; // Activa cuando la vida es muy baja
    private boolean yaSeRegenero; // Evita que el jefe se regenere varias veces
    private boolean enDefensa; // El jefe puede entrar en modo defensa

    public Jefe(String nombre, int vida, int energia, int ataque, int defensa, int velocidad, String tipo) {
        super(nombre, vida, energia, ataque, defensa, velocidad, tipo, true);
        this.fase = 1;
        this.enFuria = false;
        this.yaSeRegenero = false;
        this.enDefensa = false;
    }

    @Override
    public void decidirAccion(Personaje objetivo) {
        int decision = random.nextInt(100);

        if (fase == 1) {
            if (decision < 40) {
                atacar(objetivo);
            } else if (decision < 65) {
                ataqueFuerte(objetivo);
            } else if (decision < 80) {
                esquivar();
            } else {
                recuperarEnergia();
            }
        } else if (fase == 2) {
            if (decision < 30) {
                atacar(objetivo);
            } else if (decision < 55) {
                ataqueFuerte(objetivo);
            } else if (decision < 70) {
                habilidadEspecial(objetivo);
            } else if (decision < 85) {
                esquivar();
            } else {
                regenerarse();
            }
        } else if (enFuria) {
            if (decision < 50) {
                ataqueFuerte(objetivo);
            } else if (decision < 80) {
                habilidadEspecial(objetivo);
            } else {
                habilidadFinal(objetivo);
            }
        }
    }

    @Override
    public void atacar(Personaje objetivo) {
        if (estaAturdido) {
            System.out.println("💫 " + getNombre() + " está aturdido y no puede atacar.");
            recuperarTurno();
            return;
        }

        int ataqueFinal = this.ataque;

        if (fase == 2) {
            ataqueFinal += 5;
            System.out.println("🔥 " + getNombre() + " está en Fase 2 y su ataque es más poderoso!");
        }

        if (enFuria) {
            ataqueFinal *= 2;
            System.out.println("🌋 " + getNombre() + " ha entrado en FURIA y desata un ataque devastador!");
        }

        System.out.println("⚔ " + getNombre() + " ataca a " + objetivo.getNombre() + " causando " + ataqueFinal + " de daño.");
        objetivo.recibirDanio(ataqueFinal);
        actualizarEstado();
    }

    public void habilidadEspecial(Personaje objetivo) {
        System.out.println("✨ " + getNombre() + " usa una **HABILIDAD ESPECIAL** en " + objetivo.getNombre() + "!");
        objetivo.recibirDanio(this.ataque * 2);
        this.energia -= 10;
    }

    public void ataqueFuerte(Personaje objetivo) {
        System.out.println(nombre + " lanza un ataque devastador contra " + objetivo.getNombre() + "!");
        objetivo.recibirDanio(ataque * 2);
        reducirEnergia(10);
    }

    public void usarHabilidadEspecial(Personaje objetivo) {
        switch (tipo) {
            case "Tempestron":
                System.out.println(nombre + " invoca \"Tormenta Galáctica\" contra " + objetivo.getNombre() + "!");
                objetivo.recibirDanio(ataque + 15);
                reducirEnergia(10);
                break;
            case "Emperador de Aurion":
                System.out.println(nombre + " usa \"Juicio Imperial\" y aumenta su ataque!");
                ataque += 5;
                break;
            case "Titán de la Oscuridad":
                System.out.println(nombre + " activa \"Sombra Devastadora\", drenando vida de " + objetivo.getNombre() + "!");
                int daño = ataque + 10;
                objetivo.recibirDanio(daño);
                vida += daño / 2; // Se cura con parte del daño infligido
                break;
            case "Nyxar":
                System.out.println(nombre + " desata \"Colapso Cósmico\", causando un daño masivo!");
                objetivo.recibirDanio(ataque * 2);
                reducirEnergia(15);
                break;
            default:
                System.out.println(nombre + " no tiene habilidades especiales.");
        }
    }

    public void habilidadFinal(Personaje objetivo) {
        System.out.println("🌌 " + getNombre() + " usa su **HABILIDAD FINAL** sobre " + objetivo.getNombre() + "!");
        objetivo.recibirDanio(this.ataque * 3);

        if (tipo.equals("Nyxar, Dios Cósmico")) {
            System.out.println("⚠ Nyxar distorsiona la realidad y absorbe parte del alma de Astra...");
            objetivo.recibirDanio(10);
        }
    }

    public void regenerarse() {
        if (!yaSeRegenero && getVida() > 0) {
            int vidaRecuperada = getVida() / 4;
            curarse(vidaRecuperada);
            yaSeRegenero = true;
            System.out.println("🌀 " + getNombre() + " regenera " + vidaRecuperada + " puntos de vida.");
        }
    }

    private void actualizarEstado() {
        if (getVida() < 50 && fase == 1) {
            fase = 2;
            System.out.println("⚠ " + getNombre() + " ha entrado en Fase 2. Sus ataques son más letales!");
        }

        if (getVida() < 20 && !enFuria) {
            enFuria = true;
            System.out.println("🌋 " + getNombre() + " ha entrado en modo FURIA. Su poder se duplica!");
        }
    }

    public void esquivar() {
        System.out.println("💨 " + getNombre() + " intenta esquivar el próximo ataque.");
        if (random.nextInt(100) < 30) {
            System.out.println("⚡ " + getNombre() + " **esquivó con éxito** el ataque entrante!");
            enDefensa = true;
        } else {
            System.out.println("❌ " + getNombre() + " intentó esquivar, pero falló.");
        }
    }

    public void defenderse() {
        enDefensa = true;
        System.out.println("🛡 " + getNombre() + " **se pone en defensa**, reduciendo el daño del próximo ataque.");
    }

    @Override
    public void recibirDanio(int cantidad) {
        if (enDefensa) {
            cantidad /= 2;
            System.out.println("🛡 " + getNombre() + " **reduce el daño a la mitad** al estar en defensa.");
            enDefensa = false;
        }
        super.recibirDanio(cantidad);
    }

    public int getFase() {
        return fase;
    }

    public boolean estaEnFuria() {
        return enFuria;
    }
}
