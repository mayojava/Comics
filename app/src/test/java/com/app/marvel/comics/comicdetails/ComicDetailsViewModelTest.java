package com.app.marvel.comics.comicdetails;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;

import com.app.marvel.comics.domain.entity.Comic;
import com.app.marvel.comics.domain.repository.ISelectedComicRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComicDetailsViewModelTest {
    @Mock ISelectedComicRepository selectedComicRepository;

    @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ComicDetailsViewModel comicDetailsViewModel;

    @Before
    public void setup() {
        when(selectedComicRepository.getSelectedComic()).thenReturn(getComic());
        comicDetailsViewModel = new ComicDetailsViewModel(selectedComicRepository);
    }

    @Test
    public void shouldGetSelectedComicFromRepository() {
        //Arrange

        //Act

        //Assert
        verify(selectedComicRepository).getSelectedComic();
    }

    @Test
    public void shouldPostSelectedComicInLiveData() {
        //Arrange

        //Act
        final LiveData<Comic> liveData = comicDetailsViewModel.getSelectedComic();

        //Assert
        assertNotNull(liveData);
        final Comic comic = liveData.getValue();
        assertNotNull(comic);
        assertThat(comic.authors(), is("Author"));
    }

    private Comic getComic() {
        return Comic
                .builder()
                .authors("Author")
                .price(100.f)
                .priceType("Print price")
                .pageCount(10)
                .description("Description")
                .thumbnail("thumbnail")
                .title("Iron man")
                .build();
    }
}