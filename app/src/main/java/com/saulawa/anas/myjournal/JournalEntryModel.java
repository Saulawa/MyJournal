package com.saulawa.anas.myjournal;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "entry")
public class JournalEntryModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private byte[] _icon;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public JournalEntryModel(String title, String description, byte[] _icon, Date updatedAt) {

        this.title = title;
        this.description = description;
        this._icon = _icon;
        this.updatedAt = updatedAt;
    }


    public JournalEntryModel(int id, String title, String description, byte[] _icon, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this._icon = _icon;
        this.updatedAt = updatedAt;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] get_icon() {
        return _icon;
    }

    public void set_icon(byte[] _icon) {
        this._icon = _icon;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
