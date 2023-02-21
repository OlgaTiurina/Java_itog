import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataOutput {
    private static final String ALL_TOYS = "allToys.csv";
    private static final String DROPPED_TOYS = "droppedToys.csv";

    public static void getAllToys() {
        try (BufferedReader  read = new BufferedReader(new FileReader(ALL_TOYS, StandardCharsets.UTF_8))) {
            String line;
            while ((line =  read.readLine()) != null) {
                String[] toyData = line.split(",");
                System.out.println(toyData[0] + ". " + toyData[1] + ": " + toyData[2] + " шт. " + toyData[3]);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }
    
    public static void getDroppedToys() {
        try (BufferedReader  read = new BufferedReader(new FileReader(DROPPED_TOYS, StandardCharsets.UTF_8))) {
            String line;
            while ((line =  read.readLine()) != null) {
                String[] toyData = line.split(",");
                System.out.println(toyData[0] + ". " + toyData[1] + ": " + toyData[2] + " шт. ");
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }   
}
