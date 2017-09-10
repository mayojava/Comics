package com.app.marvel.comics.domain;

import io.reactivex.Scheduler;

public interface ISchedulersFactory {
    Scheduler main();

    Scheduler io();

    Scheduler computation();

    Scheduler trampoline();
}
