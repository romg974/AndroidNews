package fr.centrale.rom.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.centrale.rom.news.models.NewsArticle;
import fr.centrale.rom.news.models.NewsSource;
import fr.centrale.rom.news.models.SourceList;

public class MainActivity extends AppCompatActivity {

    String feedSource = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(feedSource, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Monlog", "request done");
                        SourceList sl = SourceList.getInstance();
                        sl.clearSources();

                        if(response.has("sources")){
                            try {
                                JSONArray receivedArticles = response.getJSONArray("sources");

                                for(int i = 0; i < receivedArticles.length(); i++){
                                    JSONObject article = receivedArticles.getJSONObject(i);
                                    NewsSource na = new NewsSource();
                                    na.setId(article.getString("id"));
                                    na.setName(article.getString("name"));
                                    na.setUrl(article.getString("url"));

                                    sl.addSource(na);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        Intent i = new Intent(MainActivity.this, NewsActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Monlog", "request err");

                    }
                });

        queue.add(req);

        Button shownews = (Button)findViewById(R.id.shownews);
        shownews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewsActivity.class);
                startActivity(i);
            }
        });
    }
}
