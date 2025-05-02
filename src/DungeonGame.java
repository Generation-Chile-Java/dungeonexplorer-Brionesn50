import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DungeonGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Bienvenido al Juego de Mazmorras. Ingresa tu nombre: ");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName, 100);
        List<Room> dungeon = Arrays.asList(
                new EmptyRoom(),
                new TreasureRoom(new Treasure("Espada Legendaria")),
                new EnemyRoom(new Enemy("Goblin", 20)),
                new TreasureRoom(new HealthPotion("Poción de Curación", 30)),
                new EnemyRoom(new Enemy("Esqueleto", 15)),
                new EmptyRoom()
        );

        for (int i = 0; i < dungeon.size(); i++) {
            if (!player.isAlive()) {
                System.out.println("¡Juego Terminado! " + player.getName() + " ha muerto.");
                break;
            }
            System.out.println("\n--- Sala " + (i + 1) + " ---");
            System.out.println(dungeon.get(i).getDescription());
            player.displayStatus();

            System.out.print("Presiona Enter para interactuar con la sala...");
            scanner.nextLine();
            dungeon.get(i).interact(player);
        }

        if (player.isAlive()) {
            System.out.println("¡Felicidades! " + player.getName() + " ha explorado toda la mazmorra.");
        }

        scanner.close();
    }
}
