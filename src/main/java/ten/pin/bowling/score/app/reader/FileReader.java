package ten.pin.bowling.score.app.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileReader implements FileReaderInterface {
    @Override
    public List<String> read(String path) throws IOException, InvalidTSVFormatException {
        List<String> lines = Files.lines(Paths.get(path)).collect(Collectors.toList());
        if(!validTSVFormat(lines)) throw new InvalidTSVFormatException();
        return lines;
    }

    @Override
    public boolean validTSVFormat(List<String> lines) {
        return lines.stream().allMatch(line -> line.split("\\t").length==2);
    }
}
