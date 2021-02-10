package com.mayank.mytimetable;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mayank.mytimetable.DataClass.Note;

import java.util.List;

@androidx.room.Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

    @Query("select * from note_table where day = :d order by setStartTime")
    List<Note> select(String d);

    @Query("delete from note_table where day = :d")
    void deleteAll(String d);
    @Query("update note_table set endEndTime=-1 , endStartTime=-1 , breakAmt = 0 where day = :d")
    void updateEntireDay(String d);


    @Insert
    void insertAnEntireDay(List<Note> list);








}
