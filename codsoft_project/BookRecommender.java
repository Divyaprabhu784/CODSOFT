import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.tensorflow.lite.Interpreter;

public class BookRecommender {

  private static Map<Integer, String> bookTitles;

  public static void main(String[] args) throws IOException {
    // Load data (replace with actual logic)
    bookTitles = loadBookTitles("books.txt"); // Assuming book titles are stored in a text file

    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter a book title:");
    String inputTitle = scanner.nextLine();

    Integer bookId = getBookId(inputTitle);
    if (bookId == null) {
      System.out.println("Book not found.");
    } else {
      recommendBooks(bookId, "model.tflite"); 
    }
    scanner.close();
  }

  private static Map<Integer, String> loadBookTitles(String filePath) throws IOException {
    Map<Integer, String> titles = new HashMap<>();
    int id = 0;
    for (String line : Files.readAllLines(Paths.get(filePath))) {
      titles.put(id++, line.trim());
    }
    return titles;
  }

  private static Integer getBookId(String title) {
    for (Map.Entry<Integer, String> entry : bookTitles.entrySet()) {
      if (entry.getValue().equalsIgnoreCase(title)) {
        return entry.getKey();
      }
    }
    return null;
  }

  private static void recommendBooks(int bookId, String modelPath) throws IOException {
    // Load the TensorFlow Lite model
    Interpreter tflite = new Interpreter(ByteBuffer.wrap(Files.readAllBytes(Paths.get(modelPath))));

    
    int[] inputShape = tflite.getInputTensorShape(0); // Assuming one input tensor
    int numInputs = inputShape[0]; // Get the number of input elements
    Object[] input = new Object[numInputs]; // Create an array to hold input data
    Object[] output = new Object[1]; // Assuming one output tensor

    // ... (code to prepare input data for the model, e.g., converting book titles to numerical IDs)
   

    double[][] similarityMatrix = new double[bookTitles.size()][bookTitles.size()];
    for (int i = 0; i < bookTitles.size(); i++) {
      if (i != bookId) {  // Avoid predicting similarity for the same book
        // Prepare input data for the model (replace with your specific logic)
        // - Convert book title (i) and target title (j) to numerical representation
        // - Set input data based on the model's input format and required elements

        // Run inference
        tflite.run(input, output);

        // Extract the similarity score from the output (assuming first element)
        double similarityScore = (double) output[0];
        similarityMatrix[i][bookId] = similarityScore;
      }
    }

    recommendBooksBasedOnMatrix(bookId, similarityMatrix);
  }

  private static void recommendBooksBasedOnMatrix(int bookId, double[][] similarityMatrix) {
    List<Integer> recommendedBookIds = new ArrayList<>();
    for (int i = 0; i < similarityMatrix.length; i++) {
      if (i != bookId) {
        recommendedBookIds.add(i);
      }
    }

    // Sort recommendations by similarity score (highest to lowest)
    recommendedBookIds.sort((i1, i2) -> Double.compare(similarityMatrix[i2][bookId], similarityMatrix[i1][bookId]));

    System.out.println("Recommended books:");
    for (int i = 0; i < Math.min(5, recommendedBookIds.size()); i++) {
      String recommendedTitle = bookTitles.get(recommendedBookIds.get(i));
      System.out.println("\"" + recommendedTitle + "\"");
    }
  }
}
