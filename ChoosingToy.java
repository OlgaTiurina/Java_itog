import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoosingToy {
    private static final String ALL_TOYS = "allToys.csv";
    private static final String DROPPED_TOYS = "droppedToys.csv";

    public static void start() {
        List<String> toys = viewingFile();
        if (toys.isEmpty()) {
            System.out.println("\nСписок игрушек пуст!\n");
            return;
        }

        String receivedToy = randomToy(toys);
        System.out.println("Выбрана игрушка: " + receivedToy);

        writingToFile(receivedToy);
    }

    private static List<String> viewingFile() {
      List<String> toys = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(ALL_TOYS))) {
          String line;
          while ((line = reader.readLine()) != null) {
              String[] toyData = line.split(",");
              int count = Integer.parseInt(toyData[2]);
              if (count > 0) {
                  toys.add(line);
              }
          }
      } catch (IOException e) {
          System.out.println("Ошибка при чтении файла " + ALL_TOYS + "\n");
      }
      return toys;
    }

    private static String randomToy(List<String> toys) {
        Random random = new Random();
        double totalChance = 0.0;
        for (String toy : toys) {
            totalChance += Double.parseDouble(toy.split(",")[3].replaceAll("%", "")) / 100.0;
        }
        double randomValue = random.nextDouble() * totalChance;
        double accumulatedChance = 0.0;
        for (String toy : toys) {
            double toyChance = Double.parseDouble(toy.split(",")[3].replaceAll("%", "")) / 100.0;
            accumulatedChance += toyChance;
            if (accumulatedChance >= randomValue) {
                return toy.split(",")[1];
            }
        }
        return null;
    }

    private static void writingToFile(String toy) {
      List<String> toys = viewingFile();
      boolean toyFound = false;
      for (int i = 0; i < toys.size(); i++) {
          String[] toyData = toys.get(i).split(",");
          if (toyData[1].equals(toy.split(",")[0])) {
              int count = Integer.parseInt(toyData[2]) - 1;
              toys.set(i, toyData[0] + "," + toyData[1] + "," + count + "," + toyData[3]);
              toyFound = true;
              break;
          }
      }

      if (!toyFound) {
          System.out.println("\nИгрушка не найдена\n");
          return;
      }

      try (FileWriter writer = new FileWriter(ALL_TOYS)) {
          for (String toyData : toys) {
              writer.write(toyData + "\n");
          }
      } catch (IOException e) {
          System.out.println("\nОшибка при записи файла " + ALL_TOYS + "\n");
          return;
      }

      try (BufferedReader reader = new BufferedReader(new FileReader(DROPPED_TOYS))) {
          List<String> playedToys = new ArrayList<>();
          String line;
          boolean toyFound2 = false;
          while ((line = reader.readLine()) != null) {
              String[] playedToyData = line.split(",");
              if (playedToyData[1].equals(toy.split(",")[0])) {
                  int count = Integer.parseInt(playedToyData[2]) + 1;
                  line = playedToyData[0] + "," + playedToyData[1] + "," + count;
                  toyFound2 = true;
              }
              playedToys.add(line);
          }

          if (!toyFound2) {
              int id = 0;
              for (String playedToy : playedToys) {
                  String[] playedToyData = playedToy.split(",");
                  id = Math.max(id, Integer.parseInt(playedToyData[0]));
              }
              int count = 1;
              String name = toy.split(",")[0];
              String playedToyEntry = (id + 1) + "," + name + "," + count;
              playedToys.add(playedToyEntry);
          }

          try (FileWriter writer = new FileWriter(DROPPED_TOYS)) {
              for (String playedToy : playedToys) {
                  writer.write(playedToy + "\n");
              }
          } catch (IOException e) {
              System.out.println("Ошибка при записи файла " + DROPPED_TOYS + "\n");
              return;
          }
      } catch (IOException e) {
          System.out.println("\nОшибка при чтении файла " + DROPPED_TOYS + "\n");
      }
    }
}
