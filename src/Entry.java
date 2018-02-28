import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Entry {
  public static void main(String[] args) throws IOException {

    if (args.length != 2) {
      System.out.println("Wrong number of inputs");
      return;
    }
    String filePath = args[0];
    String writePath = args[1];

    byte[] encoded = Files.readAllBytes(Paths.get(filePath));
    String rawInput = new String(encoded, "UTF-8");

    FunctionParser fp = new FunctionParser(rawInput.replaceAll("\r", ""));

    try (PrintWriter out = new PrintWriter(writePath)) {
      out.println(fp.getFormattedText());
    }
  }

}
