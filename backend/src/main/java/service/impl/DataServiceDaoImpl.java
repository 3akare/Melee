package service.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.Data;
import org.bson.Document;
import service.iDataServiceDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataServiceDaoImpl implements iDataServiceDao {
    private final MongoCollection<Document> dataCollection;

    public DataServiceDaoImpl(MongoClient mongoClient, String databaseName) {
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        dataCollection = database.getCollection("raw_data");
    }

    @Override
    public void save(Data data) {
        Document document = new Document("_id", data.getId())
            .append("data", data.getData())
            .append("time", data.getDataTime())
            .append("from", data.getSenderEmail())
            .append("read", false);
        dataCollection.insertOne(document);
    }

    @Override
    public List<Data> findAll() {
        List<Data> data = new ArrayList<>();
        for (Document document : dataCollection.find()) {
            data.add(new Data(
                document.getString("_id"),
                document.getString("data"),
                    document.getString("senderEmail"),
                    (Date) document.get("dateTime"),
                    document.getBoolean("read")
            ));
        }
        return data;
    }
}
