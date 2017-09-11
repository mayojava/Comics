package com.app.marvel.comics.comicdetails;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.marvel.comics.R;
import com.app.marvel.comics.domain.entity.Comic;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ComicDetailsActivity extends LifecycleActivity implements AppCompatCallback {
    @Inject ViewModelProvider.Factory viewModelFactory;

    private ImageView image;
    private TextView title;
    private TextView description;
    private TextView pageCount;
    private TextView price;
    private TextView authors;

    private AppCompatDelegate delegate;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_details);

        setupToolbar();
        setupView();

        final ComicDetailsViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(ComicDetailsViewModel.class);
        viewModel.getSelectedComic().observe(this, this::bindToViews);
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        delegate.setSupportActionBar(toolbar);
        final ActionBar actionBar = delegate.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupView() {
        image = findViewById(R.id.image_view_comic);
        title = findViewById(R.id.text_view_title);
        description = findViewById(R.id.text_view_description);
        pageCount = findViewById(R.id.text_view_page_count);
        price = findViewById(R.id.text_view_price);
        authors = findViewById(R.id.text_authors);
    }

    private void bindToViews(Comic comic) {
        title.setText(comic.title());
        description.setText(comic.description());
        pageCount.setText(String.valueOf(comic.pageCount()));
        authors.setText(comic.authors());
        price.setText(String.valueOf(comic.price()));
        Picasso.with(this)
                .load(comic.thumbnail())
                .into(image);
        toolbar.setTitle(comic.title());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {}

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {}

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
