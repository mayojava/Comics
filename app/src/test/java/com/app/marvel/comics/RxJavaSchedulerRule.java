package com.app.marvel.comics;

import com.app.marvel.comics.domain.ISchedulersFactory;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.schedulers.TestScheduler;

public class RxJavaSchedulerRule implements TestRule {
    private final TestScheduler io = new TestScheduler();
    private final TestScheduler computation = new TestScheduler();
    private final TestScheduler trampoline = new TestScheduler();
    private final ISchedulersFactory schedulersFactory = createMockSchedulerFactory();
    private final Scheduler main = new Scheduler(){

        @Override
        public Worker createWorker() {
            return new ExecutorScheduler.ExecutorWorker(Runnable::run);
        }

        @Override
        public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
            return super.scheduleDirect(run, 0, unit);
        }
    };

    @Override
    public Statement apply(Statement base, Description description) {
        return base;
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
