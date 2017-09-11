package com.app.marvel.comics.comicslist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.app.marvel.comics.domain.ISchedulersFactory;
import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.interactor.FetchComicsInteractor;
import com.app.marvel.comics.domain.repository.ISelectedComicRepository;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;

public class ComicsListViewModel extends ViewModel {
    private static final String TAG = ComicsListViewModel.class.getSimpleName();

    @Nonnull private final FetchComicsInteractor interactor;
    @Nonnull private final ISchedulersFactory schedulersFactory;
    @Nonnull private final ISelectedComicRepository selectedComicRepository;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Comic>> comicsLiveData = new MutableLiveData<>();

    @Inject
    ComicsListViewModel(@Nonnull final FetchComicsInteractor interactor,
                        @Nonnull final ISchedulersFactory schedulersFactory,
                        @Nonnull final ISelectedComicRepository selectedComicRepository) {
        this.interactor = interactor;
        this.schedulersFactory = schedulersFactory;
        this.selectedComicRepository = selectedComicRepository;
        disposable.add(bindToComicsStream());
        refreshData();
    }

    private void refreshData() {
        disposable.add(interactor.refreshData()
                .subscribeOn(schedulersFactory.io())
                .observeOn(schedulersFactory.main())
                .subscribe(() -> {}, error -> Log.e(TAG, "Could not update data", error)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    @Nonnull
    LiveData<List<Comic>> getComicsLiveData() {
        return comicsLiveData;
    }

    void putSelectedComic(@Nonnull final Comic comic) {
        selectedComicRepository.putComic(comic);
    }

    @Nonnull
    private Disposable bindToComicsStream() {
        return interactor.getResponseStream(Option.none())
                .observeOn(schedulersFactory.main())
                .subscribe(comicsLiveData::postValue, error -> Log.e(TAG, "Error getting comics: ",  error));
    }
}
