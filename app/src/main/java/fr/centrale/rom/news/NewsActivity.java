package fr.centrale.rom.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.centrale.rom.news.dummy.DummyContent;
import fr.centrale.rom.news.models.NewsArticle;

public class NewsActivity extends AppCompatActivity implements NewsArticleFragment.OnListFragmentInteractionListener {

    //String feedSource = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    String feedSource = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=google-news-fr";

    private ArrayList<NewsArticle> articles;
    private NewsArticleFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(feedSource, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Monlog", "request done");
                        frag.clearArticles();
                        if(response.has("articles")){
                            try {
                                JSONArray receivedArticles = response.getJSONArray("articles");

                                for(int i = 0; i < receivedArticles.length(); i++){
                                    JSONObject article = receivedArticles.getJSONObject(i);
                                    frag.addArticle(new NewsArticle(article.getString("title"), "lol"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        getSupportFragmentManager().beginTransaction().detach(frag).attach(frag).commit();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Monlog", "request err");

                    }
                });

        queue.add(req);

        frag = new NewsArticleFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, frag)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(NewsArticle item) {
        Log.d("Monlog", "coucou");
    }
}
