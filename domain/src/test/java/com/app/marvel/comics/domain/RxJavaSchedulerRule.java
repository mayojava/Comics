package com.app.marvel.comics.domain;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class RxJavaSchedulerRule implements TestRule {
    private TestScheduler io = new TestScheduler();
    private TestScheduler main = new TestScheduler();
    private TestScheduler computation = new TestScheduler();
    private TestScheduler trampoline = new TestScheduler();
    private ISchedulersFactory schedulersFactory = createMockSchedulerFactory();

    @Override
    public Statement apply(Statement base, Description description) {
        return base;
    }

    public void triggerMainScheduler() {
        main.triggerActions();
    }

    public void triggerIoScheduler() {
        io.triggerActions();
    }

    public void triggerComputationScheduler() {
        computation.triggerActions();
    }

    public ISchedulersFactory getSchedulersFactory() {
        return schedulersFactory;
    }

    private ISchedulersFactory createMockSchedulerFactory() {
        return new ISchedulersFactory() {
            @Override
            public Scheduler main() {
                return main;
            }

            @Override
            public Scheduler io() {
                return io;
            }

            @Override
            public Scheduler computation() {
                return computation;
            }

            @Override
            public Scheduler trampoline() {
                return trampoline;
            }
        };
    }
}
