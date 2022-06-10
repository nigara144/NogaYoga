//package com.example.nogayoga.DB;
//
//import android.util.Log;
//
//import org.bson.Document;
//
//import com.mongodb.ConnectionString;
////import com.mongodb.MongoClient;
//import com.mongodb.MongoClientSettings;
////import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//
//public class Mongo {
//
//	MongoCollection<Document> meditationCollection;
//
//	public void connect()
//	{
//		ConnectionString connectionString = new ConnectionString("mongodb+srv://hodg300:Hg19581819@cluster0.cjbzazi.mongodb.net/?retryWrites=true&w=majority");
//		MongoClientSettings settings = MongoClientSettings.builder()
//				.applyConnectionString(connectionString)
//				.build();
//		MongoClient mongoClient = MongoClients.create(settings);
//		MongoDatabase database = mongoClient.getDatabase("nogayogadb");
//
////		MongoClientURI uri = new MongoClientURI(
////			"mongodb+srv://nigara:nigar140498@cluster0.apvxx.mongodb.net/?retryWrites=true&w=majority");
////		MongoClient mongoClient = new MongoClient(uri);
////		MongoDatabase database = mongoClient.getDatabase("nogayogadb");
//
//		meditationCollection = database.getCollection("meditation");
//
//		Log.d("MONGO", "success connect!!");
//
//	}
//	public void insert(String name , String email , String phone)
//	{
//		Document emp1 = new Document();
//		emp1.put("name", name);
//		emp1.put("email", email);
//		emp1.put("phone", phone);
//
//		Log.d("MONGO", "before!!");
//		try {
//			meditationCollection.insertOne(emp1);
//		}catch (Exception e){
//			Log.d("MONGO-error", e.toString());
//		}
//
//		Log.d("MONGO", "success insert!!");
//	}
//
//}
