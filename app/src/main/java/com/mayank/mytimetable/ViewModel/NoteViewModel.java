package com.mayank.mytimetable.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.DataClass.SavedNotes;
import com.mayank.mytimetable.Repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends ViewModel {

    NoteRepository mNoteRepository;
    LiveData<List<Note>> allNotes;

    public void selectQuery() {
        mNoteRepository.selectQuery();
    }

    public NoteViewModel() {
        mNoteRepository = NoteRepository.getRepository();
    }

    public void insert(Note note) {
        mNoteRepository.insert(note);
    }

    public void deleteAll(String d) {
        mNoteRepository.deleteAll(d);
    }

    public void delete(Note note) {
        mNoteRepository.delete(note);
    }

    public void update(Note note) {
        mNoteRepository.update(note);
    }

    public void insertSavedNote(List<SavedNotes> list,String day)
    {
        mNoteRepository.insertSavedNotes(list,day);
    }
  public   MutableLiveData<List<SavedNotes>> getDayProgress()
    {
        return mNoteRepository.getDayProgress();
    }
    public void selectTheDaysProgress(String day)
    {
        mNoteRepository.selectTheDaysProgress(day);
    }

    public void selectAllNotesOfADay(Integer i) {
        mNoteRepository.selectAllNotesOfADay(i);
    }
    public LiveData<List<Note>> getAllNotesOfDay()
    {
        return mNoteRepository.getAllNotesOfDay();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mNoteRepository.getAllNotes();
    }

    public void insertEntireDayNote(List<Note> list)
    {
        mNoteRepository.insertEntireDayNote(list);
    }

    public LiveData<Integer> getIsUpdateDone()
    {
        return mNoteRepository.getIsUpdateDone();
    }

    public void updateEntireDay(String d)
    {
        mNoteRepository.updateEntireDay(d);
    }


}
