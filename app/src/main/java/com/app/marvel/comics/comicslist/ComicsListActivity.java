package com.app.marvel.comics.comicslist;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.marvel.comics.R;
import com.app.marvel.comics.domain.entity.Comic;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class ComicsListActivity extends LifecycleActivity implements ComicsRecyclerAdapter.OnComicClickListener{

    @Inject ComicsRecyclerAdapter recyclerAdapter;
    @Inject ViewModelProvider.Factory viewModelFactory;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar_indefinite);
        setupRecyclerView();

        final ComicsListViewModel viewModel =
                ViewModelProviders.of(this, viewModelFactory).get(ComicsListViewModel.class);
        viewModel.getComicsLiveData().observe(this, this::updateList);
    }

    @Override
    public void onComicClick(Comic comic) {
        Toast.makeText(getBaseContext(), comic.title(), Toast.LENGTH_LONG).show();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_movies);
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
}
