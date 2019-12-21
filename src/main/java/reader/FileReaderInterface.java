package reader;

import java.io.IOException;
import java.util.List;

public interface FileReaderInterface {
    /**
     * It reads a file given a path, and collect each line into a List
     * @param path represents the path where the file is located
     * @return List containing each line of the specified file.
     * @throws IOException
     */
    List<String> read(String path) throws IOException;
}
