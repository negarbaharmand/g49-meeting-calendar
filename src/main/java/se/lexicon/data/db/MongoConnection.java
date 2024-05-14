package se.lexicon.data.db;

import com.mongodb.ConnectionString;
import com.mongodb.*;
import com.mongodb.client.*;

public class MongoConnection {
    private static final String DB_NAME = "meeting_calendar";
    private static final String CONNECTION_STRING = "mongodb+srv://thomassjovy:6mQvmXtQu6CgnkN@sjovy0.bmgchmw.mongodb.net/?retryWrites=true&w=majority&appName=Sjovy0";
    private static MongoClient mongoClient = null;

    public static MongoDatabase getConnection() {
        if (mongoClient == null) {
            ConnectionString uri = new ConnectionString(CONNECTION_STRING);
            mongoClient = MongoClients.create(uri);
            addShutdownHook();
        }
        return mongoClient.getDatabase(DB_NAME);
    }

    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }));
    }
}