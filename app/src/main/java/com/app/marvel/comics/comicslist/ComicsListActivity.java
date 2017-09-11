package com.app.marvel.comics.comicslist;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.app.marvel.comics.R;
import com.app.marvel.comics.comicdetails.ComicDetailsActivity;
import com.app.marvel.comics.domain.entity.Comic;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ComicsListActivity extends LifecycleActivity
        implements ComicsRecyclerAdapter.OnComicClickListener, AppCompatCallback {

    @Inject ComicsRecyclerAdapter recyclerAdapter;
    @Inject ViewModelProvider.Factory viewModelFactory;

    private ProgressBar progressBar;
    private AppCompatDelegate delegate;
    private ComicsListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        delegate = AppCompatDelegate.create(this, this);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_main);

        setupToolbar();
        setupRecyclerView();

        progressBar = findViewById(R.id.progress_bar_indefinite);

        viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(ComicsListViewModel.class);
        viewModel.getComicsLiveData().observe(this, this::updateList);
    }

    private void setupToolbar() {
        final android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        delegate.setSupportActionBar(toolbar);
    }

    @Override
    public void onComicClick(@Nonnull final Comic comic) {
        viewModel.putSelectedComic(comic);
        final Intent intent = new Intent(this, ComicDetailsActivity.class);
        startActivity(intent);
    }

    private void setupRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void updateList(@Nonnull List<Comic> comics) {
        if (comics.isEmpty() && recyclerAdapter.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }

            recyclerAdapter.setItems(comics);
        }
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
