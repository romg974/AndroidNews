package fr.centrale.rom.news;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.centrale.rom.news.models.NewsArticle;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsShowFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsShowFragment extends Fragment {
    private NewsArticle news;

    private OnFragmentInteractionListener mListener;

    public NewsShowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewsShowFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsShowFragment newInstance(NewsArticle newsArticle) {
        NewsShowFragment fragment = new NewsShowFragment();

        fragment.setNews(newsArticle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Monlog", "Entering news show fragment");

    }

    public void setNews(NewsArticle news) {
        this.news = news;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_show, container, false);
        // Inflate the layout for this fragment
        TextView titre = (TextView) view.findViewById(R.id.titre);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView auteur = (TextView) view.findViewById(R.id.auteur);
        TextView source = (TextView) view.findViewById(R.id.source);
        TextView description = (TextView) view.findViewById(R.id.description);

        titre.setText(news.getTitle());
        date.setText(news.getPublishedAt());
        auteur.setText(news.getAuthor());
        description.setText(news.getDescription());
        source.setText(news.getSource().getName());

        Button btnClose = (Button) view.findViewById(R.id.close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentClose();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void onFragmentClose();
    }
}
