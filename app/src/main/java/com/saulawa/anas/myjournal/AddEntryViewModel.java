package com.saulawa.anas.myjournal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

class AddEntryViewModel extends ViewModel {

    private LiveData<JournalEntryModel> entry;

    public AddEntryViewModel(AppDatabase mDb, int mEntryId) {
        entry = mDb.entryDao().loadEntryById(mEntryId);
    }


    public LiveData<JournalEntryModel> getEntry() {
        return entry;
    }



}
