package com.sc.couchbase_client;

import java.util.concurrent.TimeUnit;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

public class CouchApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
                .connectTimeout(10000) // 10000ms = 10s, default is 5s
                .queryEnabled(true).build();
		
		CouchbaseCluster cluster =  CouchbaseCluster.create (env, "52.90.10.148:8091");
		Bucket bucket = cluster.openBucket();

		JsonObject user =  JsonObject.empty().put("name", "premanand")
				.put("lastname","chandrasekaran")
				.put("job", "developer")
				.put("age", 40);
		
		JsonDocument doc = JsonDocument.create("senthil",user);
		JsonDocument response = bucket.upsert(doc);
		
		System.out.println("Output:"+bucket.get("senthil"));
		//System.out.println("Output:"+bucket.get("senthil"));
		cluster.disconnect();
		
	}

}
