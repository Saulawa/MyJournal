package com.saulawa.anas.myjournal;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<JournalEntryModel>> entries;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        entries = database.entryDao().loadAllEntries();
    }


    public LiveData<List<JournalEntryModel>> getEntries() {
        return entries;
    }
}
