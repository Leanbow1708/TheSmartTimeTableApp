package com.mayank.mytimetable.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mayank.mytimetable.DataClass.Note;
import com.mayank.mytimetable.DataClass.SavedNotes;
import com.mayank.mytimetable.Database.NoteDatabase;
import com.mayank.mytimetable.NoteDao;
import com.mayank.mytimetable.SavedNotesDao;
import com.mayank.mytimetable.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private static final String TAG = "NoteRepository";


    MutableLiveData<Integer> isUpdateDone = new MutableLiveData<>();
    MutableLiveData<List<SavedNotes>> listSavedLiveData = new MutableLiveData<>();
    List<SavedNotes> savedNotesList = new ArrayList<>();
    private NoteDao mNoteDao;
    private SavedNotesDao savedNotesDao;
    private MutableLiveData<List<Note>> allNotes = new MutableLiveData<>();
    private static NoteRepository mRepository=null;
    private MutableLiveData<List<Note>> allNotesOfDay = new MutableLiveData<>();



    public static NoteRepository getRepository(){


        return mRepository;}

    public static synchronized void getInstance(Application application){

        if(mRepository == null)
        {
            Log.d(TAG, "getInstance: "+"repo crested");

            mRepository = new NoteRepository(application);
        }


    }
    private NoteRepository(Application application)
    {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        mNoteDao = noteDatabase.noteDao();
        savedNotesDao = noteDatabase.savedNotesDao();

        isUpdateDone.setValue(0);
    //    selectQuery();
//        allNotes = mNoteDao.select(Constants.dayOfWeek[Constants.day-1]);

    }
    public void selectQuery()
    {
        Log.d(TAG, "selectQuery: "+"select query fired");


        try {
            new SelectNoteAyncTask(mNoteDao,mRepository).execute();

        }
        catch (Exception e)
        {

        }
//        allNotes.postValue(mNoteDao.select(Constants.dayOfWeek[Constants.day-1]));


    }

    public void insert(Note note){

                try {
                    new InsertNoteAyncTask(mNoteDao).execute(note);
                    }
            catch (Exception e)
                {

                }
    }
    public void update(Note note)
    {
        try {
            new UpdateNoteAyncTask(mNoteDao).execute(note);
        }
        catch (Exception e)
        {

        }
    }
    public void delete(Note note)
    {
       try {
           new DeleteNoteAyncTask(mNoteDao).execute(note);
       }
       catch (Exception e)
       {

       }
    }
    public void deleteAll(String s){

        try {
            new DeleteAllNoteAyncTask(mNoteDao).execute(s);
        }
        catch (Exception e)
        {

        }
    }




    public void insertSavedNotes(List<SavedNotes> list,String day)
    {
        savedNotesList = list;
        new DeleteTheDayProgressAsynceTask(mRepository).execute(day);


    }

    public void insertTodaysProgress()
    {
        new InsertSavedNotesTask(mRepository).execute(savedNotesList);

    }


    private static class InsertSavedNotesTask extends AsyncTask<List<SavedNotes>,Void,Void>{

        private WeakReference<NoteRepository> noteRepositoryWeakReference;


        public InsertSavedNotesTask(NoteRepository repository) {

            noteRepositoryWeakReference = new WeakReference<>(repository);

        }

        @Override
        protected Void doInBackground(List<SavedNotes>... lists) {

            NoteRepository repository = noteRepositoryWeakReference.get();
            repository.savedNotesDao.insert(lists[0]);


            return null;
        }
    }
    public void selectTheDaysProgress(String day)
    {
        new SelectTheDayProgressAsyncTask(mRepository).execute(day);
    }

    public MutableLiveData<List<SavedNotes>> getDayProgress()
    {
        return listSavedLiveData;
    }


    private static class SelectTheDayProgressAsyncTask extends AsyncTask<String,Void,Void>
    {

        WeakReference<NoteRepository> noteRepositoryWeakReference;
        NoteRepository repository;

        SelectTheDayProgressAsyncTask(NoteRepository repository)
        {
            noteRepositoryWeakReference = new WeakReference<>(repository);
            this.repository = noteRepositoryWeakReference.get();

        }

        @Override
        protected Void doInBackground(String... strings) {

           repository.listSavedLiveData.postValue(repository.savedNotesDao.selectSavedNotesOfADay(strings[0]));


            return null;
        }



    }



    private static class DeleteTheDayProgressAsynceTask extends AsyncTask<String,Void,Void>{

        WeakReference<NoteRepository> noteRepositoryWeakReference;
        NoteRepository repository;

        public DeleteTheDayProgressAsynceTask(NoteRepository repository) {
            noteRepositoryWeakReference = new WeakReference<>(repository);
            this.repository = noteRepositoryWeakReference.get();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            repository.insertTodaysProgress();


        }

        @Override
        protected Void doInBackground(String... strings) {

           repository.savedNotesDao.deleteAllSavedNotes(strings[0]);

            return null;
        }
    }


    public void selectAllNotesOfADay(Integer i)
    {
        new SelectNoteOfDayAyncTask(mNoteDao, mRepository).execute(i);
    }
    public LiveData<List<Note>> getAllNotesOfDay()
    {
        return allNotesOfDay;
    }




    public LiveData<List<Note>> getAllNotes()
    {
        return allNotes;
    }

    public void updateEntireDay(String d)
    {
        new UpdateEntireDayTask(mNoteDao, mRepository, savedNotesDao).execute(d);


    }

    public LiveData<Integer> getIsUpdateDone()
    {
        return isUpdateDone;
    }


    private static class UpdateEntireDayTask extends AsyncTask<String,Void,Void>{

        WeakReference<NoteRepository> weakReference;
        private NoteDao noteDao;
        private NoteRepository repository;
        private SavedNotesDao savedNotesDao;

        private UpdateEntireDayTask(NoteDao noteDao,NoteRepository repository,SavedNotesDao savedNotesDao){


            this.savedNotesDao = savedNotesDao;
            this.noteDao = noteDao;
            weakReference = new WeakReference<>(repository);
            this.repository = weakReference.get();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            repository.isUpdateDone.setValue(1);

        }

        @Override
        protected Void doInBackground(String... strings) {

          noteDao.updateEntireDay(strings[0]);
          savedNotesDao.deleteAllSavedNotes(strings[0]);

            return null;
        }
    }


    private static class SelectNoteAyncTask extends AsyncTask<Void,Void,List<Note>>
    {
        private WeakReference<NoteRepository> repositoryWeakReference;



        private NoteDao noteDao;
        private SelectNoteAyncTask(NoteDao note,NoteRepository repository)
        {
            repositoryWeakReference = new WeakReference<>(repository);
            this.noteDao = note;
        }



        @Override
        protected List<Note> doInBackground(Void... voids) {



            return noteDao.select(Constants.dayOfWeek[Constants.day-1]);
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);

            NoteRepository repository = repositoryWeakReference.get();
            repository.allNotes.setValue(notes);


        }
    }


    private static class DeleteAllNoteAyncTask extends AsyncTask<String,Void,Void>
    {
        String day;



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


//            if(Constants.dayOfWeek[CurrDayProgressFragment.todayDay-1].equals(day))
//            {
//                mRepository.allNotes.setValue(null);
//            }
            mRepository.allNotes.setValue(null);
//            mRepository.allNotesOfDay.setValue(null);


        }

        private NoteDao noteDao;
        private DeleteAllNoteAyncTask(NoteDao note)
        {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(String... notes) {

            day = notes[0];

            noteDao.deleteAll(notes[0]);
            return null;
        }
    }

    private static class SelectNoteOfDayAyncTask extends AsyncTask<Integer,Void,List<Note>>
    {
        private WeakReference<NoteRepository> repositoryWeakReference;



        private NoteDao noteDao;
        private SelectNoteOfDayAyncTask(NoteDao note,NoteRepository repository)
        {
            repositoryWeakReference = new WeakReference<>(repository);
            this.noteDao = note;
        }



        @Override
        protected List<Note> doInBackground(Integer... i) {



            return noteDao.select(Constants.dayOfWeek[i[0]]);
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            super.onPostExecute(notes);

            NoteRepository repository = repositoryWeakReference.get();
            repository.allNotesOfDay.setValue(notes);



        }
    }

    public void insertEntireDayNote(List<Note> list)
    {
        new InsertEntireDayAyncTask(mNoteDao,mRepository).execute(list);
    }


    private static class InsertEntireDayAyncTask extends AsyncTask<List<Note>,Void,Void>
    {
        private WeakReference<NoteRepository> weakReference;
        private NoteRepository repository;

        private NoteDao noteDao;
        private InsertEntireDayAyncTask(NoteDao note,NoteRepository repository)
        {
            weakReference = new WeakReference<>(repository);
            this.repository = weakReference.get();

            this.noteDao = note;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            repository.selectQuery();


        }

        @Override
        protected Void doInBackground(List<Note>... notes) {

            noteDao.insertAnEntireDay(notes[0]);
            Log.d(TAG, "doInBackground: "+"inserted");
            return null;
        }
    }

    private static class InsertNoteAyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private InsertNoteAyncTask(NoteDao note)
        {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            Log.d(TAG, "doInBackground: "+"inserted");
            return null;
        }
    }
    private static class UpdateNoteAyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            List<Note> list = mRepository.allNotesOfDay.getValue();
//            mRepository.allNotesOfDay.setValue(list);


        }

        private UpdateNoteAyncTask(NoteDao note)
        {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private DeleteNoteAyncTask(NoteDao note)
        {
            this.noteDao = note;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }











}
