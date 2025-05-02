import java.util.*;

// Clase Player
class Player {
    private String name;
    private int health;
    private List<GameObject> inventory;
    private int maxHealth;

    public Player(String name, int initialHealth) {
        this.name = name;
        this.health = initialHealth;
        this.maxHealth = initialHealth;
        this.inventory = new ArrayList<>();
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) this.health = 0;
        System.out.println(name + " recibe " + damage + " de daño. Vida actual: " + health);
    }

    public void heal(int amount) {
        this.health += amount;
        if (this.health > maxHealth) this.health = maxHealth;
        System.out.println(name + " se ha curado. Vida actual: " + health);
    }

    public void addToInventory(GameObject item) {
        inventory.add(item);
        System.out.println(name + " recoge: " + item.getName());
    }

    public void useItem(String itemName) {
        for (GameObject item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                item.use(this);
                inventory.remove(item);
                return;
            }
        }
        System.out.println("No tienes ese objeto en tu inventario.");
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void displayStatus() {
        System.out.println("Nombre: " + name + " | Vida: " + health + "/" + maxHealth);
        System.out.println("Inventario: " + (inventory.isEmpty() ? "Vacío" : inventory.size() + " objetos"));
    }

    public String getName() { return name; }
}

// Clases de Objetos (GameObject)
class Treasure implements GameObject {
    private String name;
    public Treasure(String name) { this.name = name; }
    @Override public String getName() { return name; }
    @Override public void use(Player player) { System.out.println("No puedes usar un tesoro directamente."); }
}

class HealthPotion implements GameObject {
    private String name;
    private int healingAmount;
    public HealthPotion(String name, int healingAmount) {
        this.name = name;
        this.healingAmount = healingAmount;
    }
    @Override public String getName() { return name; }
    @Override public void use(Player player) {
        System.out.println("Usaste " + name + " y recuperaste " + healingAmount + " de vida.");
        player.heal(healingAmount);
    }
}

// Clase Enemy
class Enemy {
    private String name;
    private int damage;
    public Enemy(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    public void attack(Player player) {
        System.out.println(name + " ataca a " + player.getName() + "!");
        player.receiveDamage(damage);
    }
    public String getName() { return name; }
}

// Clases de Salas
class EmptyRoom implements Room {
    @Override public void interact(Player player) {
        System.out.println("Esta sala está vacía. No ocurre nada.");
    }
    @Override public String getDescription() { return "Una sala vacía y silenciosa."; }
}

class TreasureRoom implements Room {
    private GameObject treasure;
    public TreasureRoom(GameObject treasure) { this.treasure = treasure; }
    @Override public void interact(Player player) {
        System.out.println("¡Encontraste un tesoro!");
        player.addToInventory(treasure);
    }
    @Override public String getDescription() { return "Una sala con un tesoro: " + treasure.getName(); }
}

class EnemyRoom implements Room {
    private Enemy enemy;
    public EnemyRoom(Enemy enemy) { this.enemy = enemy; }
    @Override public void interact(Player player) {
        System.out.println("¡Un enemigo aparece! Es " + enemy.getName());
        enemy.attack(player);
    }
    @Override public String getDescription() { return "Una sala oscura con " + enemy.getName(); }
}

// Clase principal del juego
