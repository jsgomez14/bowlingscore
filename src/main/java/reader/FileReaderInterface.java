package reader;

import java.io.IOException;
import java.util.List;

public interface FileReaderInterface {
    List<String> read(String path) throws IOException;
}
