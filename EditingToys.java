import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditingToys {
    public static void changingToy() throws IOException {
        Scanner scanner = new Scanner(System.in, "CP866");
        File file = new File("allToys.csv");
        boolean discovered = false;

        System.out.print("\nВведите ID игрушки: ");
        String id = scanner.nextLine().trim();

        double amountPercentage = 0.0;
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");
                amountPercentage += Double.parseDouble(fields[3].replace("%", ""));
            }
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] fields = line.split(",");

                if (fields[0].equals(id)) {
                    discovered = true;
                    System.out.print("\nНаименование игрушки: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        name = fields[1];
                    }

                    String count = "";
                    while (true) {
                        System.out.print("\nКоличество игрушек: ");
                        count = scanner.nextLine().trim();
                        if (count.isEmpty()) {
                            count = fields[2];
                            break;
                        }
                        try {
                            int countInt = Integer.parseInt(count);
                            if (countInt < 1) {
                                System.out.println("\nОшибка при вводе. Количество должно быть больше 0!\n");
                            } else {
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("\nОшибка при вводе!\n");
                        }
                    }

                    String percent = "";
                    while (true) {
                        System.out.print("\nПроцент выпадания игрушки: ");
                        percent = scanner.nextLine().trim();
                        if (percent.isEmpty()) {
                            percent = fields[3];
                            break;
                        }
                        try {
                            double percentDouble = Double.parseDouble(percent);
                            if (percentDouble <= 0.0) {
                                System.out.println("\nОшибка при вводе. Процент должен быть больше 0!");

                            } else if (percentDouble > 100.0 - amountPercentage + Double.parseDouble(fields[3].replace("%", ""))) {
                                System.out.println("\nОшибка! Cумма всех процентов больше 100!\n");

                            } else {
                                break;
                        }
                        } catch (NumberFormatException e) {
                            System.out.println("\nОшибка при вводе!\n");
                        }
                    }

                    String modifiedLine = id + "," + name + "," + count + "," + percent + "%";
                    replacingString(file, line, modifiedLine);
                    System.out.println("\nИгрушка успешно изменена!\n");
                    break;
              }
            }
        }

        if (!discovered) {
            System.out.println("\nИгрушка не найдена!\n");
        }
    }

    private static void replacingString(File file, String pastData, String newLine) throws IOException {
        String fileData = "";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(pastData)) {
                    fileData += newLine + System.lineSeparator();
                } else {
                    fileData += line + System.lineSeparator();
                }
            }
        }
        FileWriter writer = new FileWriter(file);
        writer.write(fileData);
        writer.close();
    }
}

