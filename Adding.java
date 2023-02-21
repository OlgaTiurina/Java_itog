import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Adding {
  public static void addToys() {
    Scanner scanner = new Scanner(System.in, "CP866");
    File file = new File("allToys.csv");

    int finalID = 0;
    double sumPercent = 0; 

    try (Scanner fileScanner = new Scanner(file)) {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] toyFields = line.split(",");
            finalID = Integer.parseInt(toyFields[0]);
            sumPercent += Double.parseDouble(toyFields[3].replace("%", ""));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    int newID = finalID + 1;

    System.out.print("\nНаименование игрушки: ");
    String name = scanner.nextLine();

    int count = 0;
    boolean validcount = false;
    while (!validcount) {
        System.out.print("Количество игрушек: ");
        if (scanner.hasNextInt()) {
            count = scanner.nextInt();
            validcount = true;
        } else {
            scanner.next();
            System.out.println("\nОшибка при вводе числа!\n");
        }
    }
    
    double percent = 0;
    boolean validPercent = false;
    while (!validPercent) {
        System.out.print("Процент выпадания игрушки (разделитель ','): ");
        if (scanner.hasNextDouble()) {
            percent = scanner.nextDouble();
            if (percent >= 0.01 && percent <= 100 - sumPercent) {
                validPercent = true;
            } else {
                System.out.println("Общий процент выпадения игрушек превышает 100%, выберите 0,01 до " + (100 - sumPercent) + "!");
                return;
            }
        } else {
            scanner.next();
            System.out.println("\nОшибка при вводе числа!\n");
        }
    }

    try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, true)) {
        writer.write(String.format("%d,%s,%d,%s%n", newID, name, count, percent + "%"));
				
        System.out.println("\nИгрушка добавлена!");
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
