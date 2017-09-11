package com.app.marvel.comics.comicslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.marvel.comics.R;
import com.app.marvel.comics.domain.entity.Comic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class ComicsRecyclerAdapter extends RecyclerView.Adapter<ComicsRecyclerAdapter.ComicsViewHolder> {
    private List<Comic> items = new ArrayList<>();

    @Nonnull private final OnComicClickListener comicClickListener;

    public ComicsRecyclerAdapter(@Nonnull final OnComicClickListener onComicClickListener) {
        comicClickListener = onComicClickListener;
    }

    @Override
    public ComicsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,
                parent, false);
        return new ComicsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicsViewHolder holder, int position) {
        holder.bind(items.get(position), comicClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(@Nonnull final List<Comic> comics) {
        items.clear();
        items.addAll(comics);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    static class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView comicImage;

        ComicsViewHolder(final View view) {
            super(view);
            title = view.findViewById(R.id.text_view_comic_title);
            comicImage = view.findViewById(R.id.comic_thumbnail);
        }

        void bind(final Comic comic, final OnComicClickListener listener) {
            itemView.setOnClickListener(view -> listener.onComicClick(comic));

            title.setText(comic.title());
            Picasso.with(itemView.getContext())
                    .load(comic.thumbnail())
                    .into(comicImage);
        }
    }

    interface OnComicClickListener {
        void onComicClick(Comic comic);
    }
}
