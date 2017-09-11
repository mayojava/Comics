package com.app.marvel.comics.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class ViewModelUtil {
    @Inject public ViewModelUtil() {}

    public <T extends ViewModel>ViewModelProvider.Factory createViewModelFor(@Nonnull final T viewModel) {
        return new ViewModelProvider.Factory() {
            @Override
            public <T extends ViewModel> T create(Class<T> modelClass) {
                if (modelClass.isAssignableFrom(viewModel.getClass())) {
                    return (T) viewModel;
                }
                throw new IllegalStateException("Class should be an instance of view model");
            }
        };
    }
}
