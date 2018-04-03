import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Entry {
  public static void main(String[] args) throws IOException {

    if (args.length != 3) {
      System.out.println("Wrong number of inputs");
      return;
    }

    String filePath = args[0];
    String writePath = args[1];
    String name = args[2];

    byte[] encoded = Files.readAllBytes(Paths.get(filePath));
    String rawInput = new String(encoded, "UTF-8");

    FunctionParser fp = new FunctionParser(rawInput.replaceAll("\r", ""));

    try (PrintWriter out = new PrintWriter(writePath)) {
      String withName = fp.getFormattedText().replaceAll("<Your name>", name);
      out.println(withName);
    }
  }

}
