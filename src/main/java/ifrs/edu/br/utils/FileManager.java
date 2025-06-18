package ifrs.edu.br.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FileManager
 */
public class FileManager {
    private static final String AUTH_PATH = System.getProperty("user.home") + "/.config/.reviewauth.pass";

    public static void create(List<String> fileData) {
        try (FileWriter writer = new FileWriter(AUTH_PATH)) {
            for (String line : fileData)
                writer.write(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create the file");
        }
    }

    public static List<String> get() throws FileNotFoundException {
        ArrayList<String> fileData = new ArrayList<>();

        File file = new File(AUTH_PATH);
        if (!file.exists())
            return null;

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (!line.isBlank())
                fileData.add(line);
        }

        scanner.close();

        return fileData;
    }

    public static void delete() {
        File file = new File(AUTH_PATH);

        if (!file.exists())
            throw new RuntimeException("Not logged in");

        if (!file.delete())
            throw new RuntimeException("Logout Failed");
    }

    public static boolean exists() {
        return new File(AUTH_PATH).exists();
    }
}
