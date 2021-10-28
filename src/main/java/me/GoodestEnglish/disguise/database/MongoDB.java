package me.GoodestEnglish.disguise.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import me.GoodestEnglish.disguise.GoodestDisguise;
import me.GoodestEnglish.disguise.util.Log;
import org.bson.Document;

import java.util.Collections;
import java.util.UUID;

public class MongoDB {

    @Getter private MongoClient mongoClient;
    @Getter private MongoDatabase mongoDatabase;
    @Getter private MongoCollection<Document> statsCollection;

    public MongoDB() {
        connect();
    }

    public void removePlayerData(UUID uuid) {
        statsCollection.findOneAndDelete(Filters.eq("uuid", uuid.toString()));
    }

    public void replaceResult(UUID uuid, Object document) {
        statsCollection.replaceOne(Filters.eq("uuid", uuid.toString()), (Document) document, new ReplaceOptions().upsert(true));
    }

    public void connect() {
        try {
            if (GoodestDisguise.INSTANCE.getConfigFile().getBoolean("MONGO.AUTHENTICATION.ENABLED")) {
                final MongoCredential credential = MongoCredential.createCredential(
                        GoodestDisguise.INSTANCE.getConfigFile().getString("MONGO.AUTHENTICATION.USERNAME"),
                        GoodestDisguise.INSTANCE.getConfigFile().getString("MONGO.AUTHENTICATION.DATABASE"),
                        GoodestDisguise.INSTANCE.getConfigFile().getString("MONGO.AUTHENTICATION.PASSWORD").toCharArray()
                );
                mongoClient = new MongoClient(
                        new ServerAddress(GoodestDisguise.INSTANCE.getConfigFile().getString("MONGO.HOST")), Collections.singletonList(credential)
                );
            } else {
                mongoClient = new MongoClient(
                        new ServerAddress(GoodestDisguise.INSTANCE.getConfigFile().getString("MONGO.HOST"))
                );
            }
            mongoDatabase = mongoClient.getDatabase(GoodestDisguise.INSTANCE.getConfigFile().getString("MONGO.DATABASE"));
            statsCollection = mongoDatabase.getCollection("data");
        } catch (Exception e) {
            Log.show(Log.LogLevel.EXTREME,"無法連接到 MongoDB");
        }
    }

    public <T> T getPlayerData(UUID uuid, Class<T> clazz) {
        return clazz.cast(statsCollection.find(Filters.eq("uuid", uuid.toString())).first());
    }

    public <T> T getPlayerData(String username, Class<T> clazz) {
        return clazz.cast(statsCollection.find(Filters.eq("username", username)).first());
    }
}
