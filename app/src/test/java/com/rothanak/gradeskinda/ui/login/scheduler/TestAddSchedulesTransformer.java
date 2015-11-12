package com.rothanak.gradeskinda.ui.login.scheduler;

import com.rothanak.gradeskinda.interactor.scheduler.AddSchedulesTransformer;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class TestAddSchedulesTransformer {

    private static Scheduler scheduler = Schedulers.immediate();
    private static AddSchedulesTransformer instance = new AddSchedulesTransformer(scheduler, scheduler);

    private TestAddSchedulesTransformer() {
        //
    }

    public static AddSchedulesTransformer get() {
        return instance;
    }

}