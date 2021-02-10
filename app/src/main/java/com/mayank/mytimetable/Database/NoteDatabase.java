package com.mayank.mytimetable.Database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.DataClass.SavedNotes;
import com.mayank.mytimetable.NoteDao;
import com.mayank.mytimetable.SavedNotesDao;

@Database(entities = {Note.class, SavedNotes.class}, version = 2)

public abstract class NoteDatabase extends RoomDatabase {

        private static NoteDatabase instance;
        public abstract NoteDao noteDao();
        public abstract SavedNotesDao savedNotesDao();

        public static synchronized NoteDatabase getInstance(Application application)
        {
            if(instance == null)
            {
                instance = Room.databaseBuilder(application.getApplicationContext(), NoteDatabase.class,"note_database" )
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }



}
