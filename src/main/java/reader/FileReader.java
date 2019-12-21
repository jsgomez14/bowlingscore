package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader implements FileReaderInterface {

    public static void main(String[] args) {
        FileReader n = new FileReader();
        String filename="../input.txt";
        Path pathToFile = Paths.get(filename);
        System.out.println(pathToFile.toAbsolutePath().toString());
        try {
            n.read("../input.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> read(String path) throws IOException {
        List<String> resp =  Files.lines(Paths.get(path)).collect(Collectors.toList());
        for (String i:
             resp) {
            System.out.println(i);
        }
        return null;
    }
}
