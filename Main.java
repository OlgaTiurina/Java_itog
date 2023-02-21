import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> menu = new ArrayList<>();
        menu.add("Общий список");
        menu.add("Добавить игрушку");
        menu.add("Изменить игрушку");
        menu.add("Игра");
        menu.add("Список разыгранных игрушек");
        menu.add("Выход");

        while (true) {
            System.out.println("\nМеню: \n");
            for (int i = 0; i < menu.size(); i++) {
                System.out.println((i + 1) + ". " + menu.get(i));
            }

            int choice = 0;
            boolean correctInput = false;
            while (!correctInput) {
                System.out.print("\nВведите число от 1 до " + menu.size() + ": ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    correctInput = (choice >= 1 && choice <= menu.size());
                } else {
                    scanner.next();
                }
                if (!correctInput) {
                    System.out.println("\nНеверный ввод. Пожалуйста, введите целое число от 1 до " + menu.size());
                }
            }
            scanner.nextLine();

            String menuItem = menu.get(choice - 1);
            System.out.println("\n" + menuItem + ": ");

            if (menuItem.equals("Выход")) {
                System.out.println("Розыгрыш окончен!");
                System.exit(0);
            }

            switch (menuItem) {
                case "Общий список":
                    DataOutput.getAllToys();
                    break;
                case "Добавить игрушку":
                    Adding.addToys();
                    break;
                case "Изменить игрушку":
                    try {
                        EditingToys.changingToy();
                    } catch (IOException e) {
                        System.out.println("Ошибка при изменении: " + e.getMessage());
                    }
                    break;
                case "Игра":
                    ChoosingToy.start();
                    break;
                case "Список разыгранных игрушек":
                    DataOutput.getDroppedToys();
                    break;
            }
        }
    }
}
