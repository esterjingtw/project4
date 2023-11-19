package ds.project4;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongodbConnection {

    // Method for inserting documents. This can be called from the servlet.
    private static MongoClient mongoClient = MongoClients.create(System.getenv("MONGO_DB_URI"));
    private static MongoDatabase database = mongoClient.getDatabase(System.getenv("MONGO_DB_NAME"));

    public static void insertDocument(Document document, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertOne(document);
    }
}