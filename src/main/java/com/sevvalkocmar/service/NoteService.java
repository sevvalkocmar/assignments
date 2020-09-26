package com.sevvalkocmar.service;

import com.sevvalkocmar.dao.NoteDAO;
import com.sevvalkocmar.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteDAO noteDAO;

    public Collection<Note> getNotes() {
        return noteDAO.getNotes();
    }

    public Note createNote(Note note) {

        return noteDAO.createNote(note);
    }

    public Optional<Note> getNoteById(String id) {

        return noteDAO.getNoteByID(id);
    }

    public Optional<Note> deleteNoteById(String id) {

        return noteDAO.deleteNoteById(id);
    }

    public Optional<Note> updateNoteById(String id, Note noteToUpdate) {
        return noteDAO.updateNoteById(id, noteToUpdate);
    }
}
