package com.saulawa.anas.myjournal;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface EntryDao {

    @Query("SELECT * FROM entry")
    LiveData<List<JournalEntryModel>> loadAllEntries();

    @Insert
    void insertEntry(JournalEntryModel journalEntryModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEntry(JournalEntryModel journalEntryModel);

    @Delete
    void deleteEntry(JournalEntryModel journalEntryModel);

    @Query("SELECT * FROM entry WHERE id = :id")
   LiveData<JournalEntryModel> loadEntryById(int id);

}
