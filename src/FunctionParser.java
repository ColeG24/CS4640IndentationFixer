import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class FunctionParser {

  private final String rawInput;
  private int index = 0;
  private Optional<String> formattedText = Optional.empty();
  private boolean hasNextFunction = true;

  private Set<String> noIndentLinesSet = createNoIndentLinesSet();

  public FunctionParser(String rawInput) {
    this.rawInput = rawInput;

    hasNextFunction = rawInput.indexOf("function", index) != -1;
  }

  public String getFormattedText() {
    return formattedText.orElse(createFormattedText());
  }

  private String createFormattedText() {
    StringBuilder formattedTextBuilder = new StringBuilder();

    while (hasNextFunction) {
      formattedTextBuilder.append(formatFunction(getNextFunction()));
      formattedTextBuilder.append("\n\n");
    }

    formattedText = Optional.of(formattedTextBuilder.toString());
    return formattedText.get();
  }

  private String formatFunction(String function) {
    Scanner scanner = new Scanner(function);
    StringBuilder formattedFunctionBuilder = new StringBuilder();

    int indexAmount = 0;
    boolean firstLine = true;
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.startsWith("%")) {
        formattedFunctionBuilder.append("%");
        String restOfLine = line.substring(1);
        if (restOfLine.length() > 1) {
          if (noIndentLinesSet.contains(restOfLine)) {
            indexAmount = 1;
          } else {
            formattedFunctionBuilder.append(indentBy(indexAmount));
            if (restOfLine.endsWith(":")) {
              indexAmount++;
            }
          }
        }
        formattedFunctionBuilder.append(restOfLine);
        formattedFunctionBuilder.append('\n');
      }
      if (firstLine) {
        formattedFunctionBuilder.append(line);
        formattedFunctionBuilder.append('\n');

        firstLine = false;
      }
    }
    return formattedFunctionBuilder.toString();
  }

  private String indentBy(int amount) {
    String indents = "";
    for (int i = 0; i < amount; i++) {
      indents += '\t';
    }

    return indents;
  }

  private String getNextFunction() {
    if (hasNextFunction) {
      int functionStartIndex = rawInput.indexOf("function", index);

      String endFunctionString = "% Spring 2018\n%";
      int functionEndIndex = rawInput.indexOf(endFunctionString, functionStartIndex) + endFunctionString.length();

      String function = rawInput.substring(functionStartIndex, functionEndIndex);

      index = functionEndIndex;
      hasNextFunction = rawInput.indexOf("function", index) != -1;

      return function;
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  private Set<String> createNoIndentLinesSet() {
    Set<String> noIndentLines = new HashSet<>();

    noIndentLines.add(" On input:");
    noIndentLines.add(" On output:");
    noIndentLines.add(" Call:");
    noIndentLines.add(" Author:");

    return noIndentLines;
  }
}
