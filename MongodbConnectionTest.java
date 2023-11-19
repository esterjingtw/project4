package ds.project4;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Scanner;

public class MongodbConnectionTest {

    public static void main(String[] args) {
        // Connection URI string with credentials
        final String uri = "mongodb+srv://esterjingtw:Jtw001231@project4.6cphvt3.mongodb.net/?retryWrites=true&w=majority";

        // Try-with-resources block to ensure that the MongoClient is closed after use
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("myFirstDatabase");
            MongoCollection<Document> collection = database.getCollection("myFirstCollection");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a string to store in the database: ");
            String input = scanner.nextLine();

            Document doc = new Document("name", "User Input")
                    .append("value", input);
            collection.insertOne(doc);
            System.out.println("Stored in the database successfully!");

            System.out.println("Reading all documents:");
            for (Document document : collection.find()) {
                System.out.println(document.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while attempting to connect to MongoDB Atlas: " + e.getMessage());
        }
    }
}