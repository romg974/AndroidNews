package fr.centrale.rom.news;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
import fr.centrale.rom.news.models.NewsSource;
import fr.centrale.rom.news.models.SourceList;

public class NewsActivity extends AppCompatActivity implements NewsArticleFragment.OnListFragmentInteractionListener, NewsShowFragment.OnFragmentInteractionListener {

    //String feedSource = "https://newsapi.org/v2/sources?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr";
    private String feedSource = "https://newsapi.org/v2/everything?apiKey=d31f5fa5f03443dd8a1b9e3fde92ec34&language=fr&sources=";
    private NewsSource actualSource;
    private int actualPage;
    private int loadedPage;

    private ArrayList<NewsArticle> articles;
    private NewsArticleFragment frag;

    private SourceList sl;

    private RequestQueue queue;

    private NewsShowFragment nsf;

    AlertDialog.Builder boite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        /////////////////////////////
        /// GESTION LISTE SOURCES ///
        /////////////////////////////
        Spinner spinner = (Spinner) findViewById(R.id.sourceSpinner);

        ArrayList<CharSequence> sources = new ArrayList<>();
        sl = SourceList.getInstance();

        for(int i = 0; i < sl.size(); i++){
            sources.add(sl.get(i).getName());
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sources);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                actualSource = sl.get(position);
                actualPage = 1;
                loadedPage = 0;
                frag.clearArticles();
                fetchNews();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(actualSource == null){
            actualSource = sl.get(0);
        }


        ////////////////////////////
        /// CREATION BOITE RETRY ///
        ////////////////////////////
        boite = new AlertDialog.Builder(this);
        boite.setTitle("Erreur de connexion");
        boite.setMessage("Les informations n'ont pas pu être récupérées. Assurez vous d'être bien connecté à l'internet mondial puis réessayez.");

        boite.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fetchNews();
            }
        });


        //////////////////////////////
        /// GESTION QUEUE REQUETES ///
        //////////////////////////////
        queue = Volley.newRequestQueue(this);


        ///////////////////////////////
        /// AJOUT FRAGMENT NEWSLIST ///
        ///////////////////////////////
        frag = new NewsArticleFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, frag)
                .commit();


        ///////////////////////////
        /// RECUP INITIALE NEWS ///
        ///////////////////////////
        actualPage = 1;
        fetchNews();
    }

    private void fetchNews(){
        JsonObjectRequest req = new JsonObjectRequest(feedSource+actualSource.getId()+"&page="+actualPage, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Monlog", "request done");
                        if(response.has("articles")){
                            try {
                                JSONArray receivedArticles = response.getJSONArray("articles");

                                for(int i = 0; i < receivedArticles.length(); i++){
                                    JSONObject article = receivedArticles.getJSONObject(i);
                                    NewsArticle na = new NewsArticle();
                                    na.setTitle(article.getString("title"));
                                    na.setAuthor(article.getString("author"));
                                    na.setPublishedAt(article.getString("publishedAt"));
                                    na.setDescription(article.getString("description"));
                                    na.setSource(actualSource);
                                    frag.addArticle(na);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        loadedPage = actualPage;
                        getSupportFragmentManager().beginTransaction().detach(frag).attach(frag).commit();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Monlog", "request err");
                        boite.show();
                    }
                });

        queue.add(req);

    }

    @Override
    public void onListFragmentInteraction(NewsArticle item) {
        if(nsf != null){
            getSupportFragmentManager().beginTransaction().remove(nsf).commit();
            nsf = null;
        }

        nsf = NewsShowFragment.newInstance(item);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, nsf)
                .commit();
    }

    @Override
    public void onBottom() {
        if(loadedPage == 5){
            Toast.makeText(this, "La dernière page a été atteinte", Toast.LENGTH_SHORT).show();
            return;
        }
        if(actualPage == loadedPage){
            actualPage++;
            fetchNews();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentClose() {
        if(nsf != null){
            getSupportFragmentManager().beginTransaction().remove(nsf).commit();
            nsf = null;
        }
    }
}
