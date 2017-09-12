package com.app.marvel.comics.domain.interactor;

import com.app.marvel.comics.domain.repository.IComicsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.observers.TestObserver;
import polanski.option.Option;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FetchComicsInteractorTest {

    @Mock IComicsRepository comicsRepository;

    private FetchComicsInteractor interactor;

    @Before
    public void setup() {
        interactor = new FetchComicsInteractor(comicsRepository);
    }

    @Test
    public void refreshData_shouldFetchFromRepository() {
        //Arrange
        when(comicsRepository.fetchComics()).thenReturn(Completable.complete());

        //Act
        TestObserver<Void> observer = interactor.refreshData().test();

        //Asset
        observer.assertTerminated();
        observer.assertComplete();
        observer.assertNoErrors();
    }

    @Test
    public void getResponseStream_shouldGetFromRepository() {
        //Arrange

        //Act
        interactor.getResponseStream(Option.none());

        //Assert
        verify(comicsRepository).getAllComics();
    }

}