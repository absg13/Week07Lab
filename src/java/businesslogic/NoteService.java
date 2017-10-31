/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businesslogic;

import dataaccess.*;
import domainmodel.*;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 738377
 */
public class NoteService {

    private NotesDB noteDB;

    public NoteService() {
        noteDB = new NotesDB();
    }

    public Note get(int noteId) throws Exception {
        return noteDB.getNote(noteId);
    }

    public List<Note> getAll() throws Exception {
        return noteDB.getAll();
    }

    public int update(int noteId, Date dateCreated, String contents) throws Exception {
        Note note = new Note(noteId, dateCreated, contents);
        return noteDB.update(note);
    }

    public int delete(int noteId) throws Exception {
        Note deleteNote = noteDB.getNote(noteId);
        return noteDB.delete(deleteNote);
    }

    public int insert(int noteId, Date dateCreated, String contents) throws Exception {
        Note note = new Note(noteId, dateCreated, contents);
        return noteDB.insert(note);
    }
}
