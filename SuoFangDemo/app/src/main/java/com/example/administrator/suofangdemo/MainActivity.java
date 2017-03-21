package com.example.administrator.suofangdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<String> list =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid);
        getServerData();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(MainActivity.this,NextActivity.class);
                intent.putStringArrayListExtra("url",list);
                startActivity(intent);
            }
        });


    }

    private void getServerData() {
        AsyncHttpClient client =new AsyncHttpClient();
        String url ="https://mock.eolinker.com/success/gIbgeNycc15SNh5mrSNTGC2Tr5WTUsCM";
        client.get(MainActivity.this, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject job =new JSONObject(responseString);
                    JSONArray data = job.optJSONArray("data");
                    for(int i =0;i<data.length();i++){
                        String image_url = data.getJSONObject(i).getString("image_url");
                        list.add(image_url);
                    }
                    MyGrid my =new MyGrid(MainActivity.this,data);
                    gridView.setAdapter(my);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
