package com.sevvalkocmar.dao;

import com.sevvalkocmar.entity.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class NoteDAO {


    private final NoteRepository repository;

    @Autowired
    public NoteDAO(NoteRepository repository) {

        this.repository = repository;
    }

    public Collection<Note> getNotes() {

        return repository.findAll();
    }

    public Note createNote(Note note) {

        return repository.insert(note);
    }

    public Optional<Note> getNoteByID(String id) {

        return repository.findById(id);
    }

    public Optional<Note> deleteNoteById(String id) {
        Optional<Note> note = repository.findById(id);
        note.ifPresent(t -> repository.delete(t));
        return note;
    }


    public Optional<Note> updateNoteById(String id, Note noteUpdate) {
        Optional<Note> noteOptional = repository.findById(id);
        if (noteOptional.isEmpty()) {
            return Optional.empty();
        }
        Note note = noteOptional.get();
        note.setTitle(noteUpdate.getTitle());
        note.setDescription(noteUpdate.getDescription());
        repository.save(note);
        return Optional.of(note);

    }
}
