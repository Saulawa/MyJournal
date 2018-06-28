package com.saulawa.anas.myjournal;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AddEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
            private final int mEntryId;


    public AddEntryViewModelFactory(AppDatabase mDb, int mEntryId) {
        this.mDb = mDb;
        this.mEntryId = mEntryId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new AddEntryViewModel(mDb,mEntryId);
    }
}
