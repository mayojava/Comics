package com.app.marvel.comics;

import com.app.marvel.comics.domain.ISchedulersFactory;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersFactory implements ISchedulersFactory {
    @Override
    public Scheduler main() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }
}
