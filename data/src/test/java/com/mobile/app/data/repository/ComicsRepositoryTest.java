package com.mobile.app.data.repository;

import com.app.marvel.comics.domain.entity.Comic;
import com.mobile.app.data.RxJavaSchedulerRule;
import com.mobile.app.data.db.entities.ComicsEntity;
import com.mobile.app.data.mappers.Mapper;
import com.mobile.app.data.models.ApiResponse;
import com.mobile.app.data.models.CreatorList;
import com.mobile.app.data.models.CreatorSummary;
import com.mobile.app.data.models.Data;
import com.mobile.app.data.models.Price;
import com.mobile.app.data.models.Result;
import com.mobile.app.data.remote.ComicsService;
import com.mobile.app.data.store.ReactiveStore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.processors.BehaviorProcessor;
import io.reactivex.subscribers.TestSubscriber;
import polanski.option.Option;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ComicsRepositoryTest {
    @Mock ComicsService comicsService;
    @Mock ReactiveStore<ComicsEntity> reactiveStore;
    @Mock Mapper mapper;

    public @Rule
    RxJavaSchedulerRule rxJavaSchedulerRule = new RxJavaSchedulerRule();

    private ComicsRepository comicsRepository;

    @Before
    public void setup() {
        comicsRepository = new ComicsRepository(reactiveStore,
                comicsService,
                rxJavaSchedulerRule.getSchedulersFactory(), mapper);
    }

    @Test
    public void testFetchComics_getsFromServiceAndSave() {
        //Arrange
        final ApiResponse apiResponse = mock(ApiResponse.class);
        when(comicsService.getComics(anyString(), anyString(), anyLong())).
                thenReturn(Single.just(apiResponse));
        when(apiResponse.getData()).thenReturn(data());
        when(mapper.mapToDbEntity(any(Result.class))).thenReturn(mock(ComicsEntity.class));

        //Act
        final TestObserver<Void> observer = comicsRepository.fetchComics().test();
        rxJavaSchedulerRule.triggerIoScheduler();
        rxJavaSchedulerRule.triggerComputationScheduler();

        //Assert
        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertTerminated();

        verify(mapper).mapToDbEntity(any(Result.class));
        verify(reactiveStore).replaceAll(any(List.class));
    }

    @Test
    public void testGetAllComics() {
        //Arrange
        final List<ComicsEntity> result = comicsEntities();
        final BehaviorProcessor<Option<List<ComicsEntity>>> processor = BehaviorProcessor.create();
        when(reactiveStore.getAll()).thenReturn(processor);
        processor.onNext(Option.ofObj(result));

        //Act
        final TestSubscriber<List<Comic>> subscriber = comicsRepository.getAllComics().test();

        rxJavaSchedulerRule.triggerComputationScheduler();

        //Assert
        subscriber.assertNoErrors();
        subscriber.assertValueCount(1);
        subscriber.assertNotTerminated();
    }

    private List<ComicsEntity> comicsEntities() {
        final ComicsEntity entity1 = new ComicsEntity(1, "thumbnail",
                "Spider man", "description", 20,
                200.0f, "Print price", "Larry Page");
        final ComicsEntity entity2 = new ComicsEntity(2, "yhumbnail 2",
                "Ant man", "Description 2", 400, 100.0f,
                "Print Price", "Fen Mikel");

        final List<ComicsEntity> entities = new ArrayList<>();
        entities.add(entity1);
        entities.add(entity2);
        return entities;
    }

    private Data data() {
        final CreatorSummary summary = new CreatorSummary();
        summary.setName("Tyler Man");
        final List<CreatorSummary> summaryList = new ArrayList<>();
        summaryList.add(summary);
        final CreatorList creatorList = new CreatorList();
        creatorList.setAvailable(1);
        creatorList.setItems(summaryList);

        final Price price = new Price();
        price.setPrice(100.0f);
        price.setType("Print price");
        final List<Price> prices = new ArrayList<>();
        prices.add(price);

        final Result result = new Result();
        result.setTitle("Spider man");
        result.setDescription("Spider man comic");
        result.setPageCount(10);
        result.setPrices(Collections.emptyList());
        result.setCreator(creatorList);
        result.setPrices(prices);

        final List<Result> results = new ArrayList<>();
        results.add(result);

        final Data data = new Data();
        data.setResults(results);

        return data;
    }
}