public abstract class Personaje {
    protected String nombre;
    protected int vida;
    protected int energia;
    protected int ataque;
    protected int defensa;
    protected int velocidad;
    protected boolean estaVivo;
    protected boolean tieneEscudo;
    protected boolean estaAturdido;
    protected int nivel;

    public Personaje(String nombre, int vida, int energia, int ataque, int defensa, int velocidad) {
        this.nombre = nombre;
        this.vida = vida;
        this.energia = energia;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.estaVivo = true;
        this.tieneEscudo = false;
        this.estaAturdido = false;
        this.nivel = 1;
    }

    public abstract void atacar(Personaje objetivo); // Polimorfismo: Diferentes formas de ataque según el personaje

    public void recibirDanio(int cantidad) {
        if (tieneEscudo) {
            System.out.println("🛡 " + nombre + " bloqueó el ataque con su escudo.");
            tieneEscudo = false; // El escudo se rompe tras un golpe
        } else {
            int danioFinal = Math.max(cantidad - defensa, 0);
            vida -= danioFinal;
            if (vida <= 0) {
                vida = 0;
                estaVivo = false;
                System.out.println("💀 " + nombre + " ha sido derrotado.");
            } else {
                System.out.println("⚠ " + nombre + " ha recibido " + danioFinal + " de daño. Vida restante: " + vida);
            }
        }
    }

    public void reducirEnergia(int cantidad) {
        this.energia -= cantidad;
        if (this.energia < 0) {
            this.energia = 0; // Evita que la energía sea negativa
        }
    }

    public void restaurarEnergia(int cantidad) {
        energia += cantidad;
        System.out.println("🔋 " + nombre + " ha recuperado " + cantidad + " de energía. Energía actual: " + energia);
    }

    public void curarse(int cantidad) {
        if (estaVivo) {
            vida += cantidad;
            System.out.println("❤️ " + nombre + " ha recuperado " + cantidad + " de vida. Vida actual: " + vida);
        } else {
            System.out.println("⚰ No se puede curar a un personaje derrotado.");
        }
    }

    public void aumentarNivel() {
        nivel++;
        vida += 10;
        energia += 5;
        ataque += 2;
        defensa += 2;
        velocidad += 1;
        System.out.println("✨ " + nombre + " ha subido al nivel " + nivel + "!");
    }

    public void activarEscudo() {
        tieneEscudo = true;
        System.out.println("🛡 " + nombre + " activó un escudo protector.");
    }

    public void aturdir() {
        estaAturdido = true;
        System.out.println("💫 " + nombre + " ha sido aturdido y perderá el siguiente turno.");
    }

    public void recuperarTurno() {
        estaAturdido = false;
        System.out.println("✅ " + nombre + " ha recuperado el control.");
    }

    public boolean estaVivo() {
        return estaVivo;
    }

    public boolean estaAturdido() {
        return estaAturdido;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getEnergia() {
        return energia;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getNivel() {
        return nivel;
    }
}
