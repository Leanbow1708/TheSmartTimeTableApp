package com.mayank.mytimetable;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mayank.mytimetable.DataClass.SavedNotes;


import java.util.List;
@Dao
public interface SavedNotesDao {
    @Insert
    void insert(List<SavedNotes> n);

    @Query("Delete from saved_note_table where day = :d")
    void deleteAllSavedNotes(String d);

    @Query("Select * from saved_note_table where day = :d")
    List<SavedNotes> selectSavedNotesOfADay(String d);

    @Query("update saved_note_table set endStartTime=-1 and endEndTime=-1 and breakAmt=0 where day = :d")
    void updateEntireDay(String d);



}
