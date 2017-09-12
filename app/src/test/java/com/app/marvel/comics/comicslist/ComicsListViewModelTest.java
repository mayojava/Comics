package com.app.marvel.comics.comicslist;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.app.marvel.comics.RxJavaSchedulerRule;
import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.interactor.FetchComicsInteractor;
import com.app.marvel.comics.domain.repository.ISelectedComicRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.processors.BehaviorProcessor;
import polanski.option.Option;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComicsListViewModelTest {
    @Mock ISelectedComicRepository selectedComicRepository;
    @Mock FetchComicsInteractor fetchComicsInteractor;

    @Rule public RxJavaSchedulerRule rxJavaSchedulerRule = new RxJavaSchedulerRule();
    @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ComicsListViewModel comicsListViewModel;
    private BehaviorProcessor<List<Comic>> processor = BehaviorProcessor.create();

    @Before
    public void setup() {
        when(fetchComicsInteractor.refreshData()).thenReturn(Completable.complete());
        when(fetchComicsInteractor.getResponseStream(any(Option.class))).thenReturn(processor);

        comicsListViewModel = new ComicsListViewModel(fetchComicsInteractor,
                rxJavaSchedulerRule.getSchedulersFactory(),
                selectedComicRepository);
    }

    @Test
    public void testViewModel_refreshDataOnCreate() {
        //Arrange

        //Act
        processor.onNext(Collections.emptyList());

        //Assert
        verify(fetchComicsInteractor).refreshData();
    }

    @Test
    public void testViewModelBindsToStreamOnCreate() {
        //Arrange

        //Act

        //Assert
        verify(fetchComicsInteractor).getResponseStream(argThat(argument -> argument.equals(Option.none())));
    }

    @Test
    public void testViewModelPostResult_whenThereIsNewEmission() {
        //Arrange

        //Act
        processor.onNext(getComicList());

        rxJavaSchedulerRule.triggerIoScheduler();

        final LiveData<List<Comic>> data = comicsListViewModel.getComicsLiveData();

        //Assert
       assertNotNull(data);
        final List<Comic> comics = data.getValue();
        assertThat(comics.size(), is(2));
    }

    @Test
    public void testPutComic_savedInRepository() {
        //Arrange
        final Comic comic = mock(Comic.class);

        //Act
        comicsListViewModel.putSelectedComic(comic);

        //Assert
        verify(selectedComicRepository).putComic(comic);
    }

    private List<Comic> getComicList() {
        final Comic comic1 = Comic
                .builder()
                .priceType("Print price").price(1.0f)
                .authors("David Doe").description("Description 1")
                .thumbnail("Thumbnail 1").pageCount(10)
                .title("Spider Man").build();

        final Comic comic2 = Comic
                .builder()
                .priceType("Print price")
                .pageCount(120)
                .price(5.6f)
                .thumbnail("Thumbnail 2")
                .description("Description")
                .authors("Fell Mike")
                .title("Ant Man").build();

        final List<Comic> result = new ArrayList<>();
        result.add(comic1);
        result.add(comic2);
        return result;
    }
}