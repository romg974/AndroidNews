package fr.centrale.rom.news;

import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.centrale.rom.news.NewsArticleFragment.OnListFragmentInteractionListener;
import fr.centrale.rom.news.dummy.DummyContent.DummyItem;
import fr.centrale.rom.news.models.NewsArticle;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNewsArticleRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsArticleRecyclerViewAdapter.ViewHolder> {

    private final List<NewsArticle> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyNewsArticleRecyclerViewAdapter(List<NewsArticle> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newsarticle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mAuteurView.setText(mValues.get(position).getAuthor());
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mDateView.setText(mValues.get(position).getPublishedAt());

        if(position % 2 == 0){
            holder.mImgDroite.setVisibility(View.GONE);
            holder.mImgGauche.setVisibility(View.VISIBLE);
        }
        else{
            holder.mImgGauche.setVisibility(View.GONE);
            holder.mImgDroite.setVisibility(View.VISIBLE);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAuteurView;
        public final TextView mTitleView;
        public final TextView mDateView;
        public final ImageView mImgGauche;
        public final ImageView mImgDroite;
        public NewsArticle mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuteurView = (TextView) view.findViewById(R.id.newsauteur);
            mTitleView = (TextView) view.findViewById(R.id.newstitre);
            mDateView = (TextView) view.findViewById(R.id.newsdate);
            mImgGauche = (ImageView) view.findViewById(R.id.imggauche);
            mImgDroite = (ImageView) view.findViewById(R.id.imgdroite);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
