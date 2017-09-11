package com.app.marvel.comics.comicslist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.app.marvel.comics.domain.ISchedulersFactory;
import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.interactor.FetchComicsInteractor;

import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import polanski.option.Option;

public class ComicsListViewModel extends ViewModel {

    @Nonnull private final FetchComicsInteractor interactor;
    @Nonnull private final ISchedulersFactory schedulersFactory;

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Comic>> comicsLiveData = new MutableLiveData<>();

    @Inject
    public ComicsListViewModel(@Nonnull final FetchComicsInteractor interactor,
                               @Nonnull final ISchedulersFactory schedulersFactory) {
        this.interactor = interactor;
        this.schedulersFactory = schedulersFactory;
        disposable.add(bindToComicsStream());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

    @Nonnull
    public LiveData<List<Comic>> getComicsLiveData() {
        return comicsLiveData;
    }

    @Nonnull
    private Disposable bindToComicsStream() {
        return interactor.getResponseStream(Option.none())
                .observeOn(schedulersFactory.main())
                .subscribe(comicsLiveData::postValue, error -> Log.e(ComicsListViewModel.class.getSimpleName(), "Error getting comics: ",  error));
    }
}
