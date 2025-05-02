public interface Room {

// Interfaces
    void interact(Player player);
    String getDescription();
}

interface GameObject {
    String getName();
    void use(Player player);
}