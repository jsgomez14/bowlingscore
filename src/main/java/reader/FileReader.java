package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader implements FileReaderInterface {
    @Override
    public List<String> read(String path) throws IOException {
        return Files.lines(Paths.get(path)).collect(Collectors.toList());
    }
}
