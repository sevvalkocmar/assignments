package com.sevvalkocmar.controller;


import com.sevvalkocmar.entity.Note;
import com.sevvalkocmar.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;


    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping
    public Note postNote(@RequestBody Note note) {
        note.setDate(System.currentTimeMillis());
        return noteService.createNote(note);
    }

    @GetMapping
    public Collection<Note> getNoteById(@RequestParam(value = "id", required = false) String id) {
        if (StringUtils.isEmpty(id)) {
            return noteService.getNotes();
        }
        Optional<Note> noteById = noteService.getNoteById(id);
        return noteById.isEmpty() ? Collections.emptyList() : Collections.singletonList(noteById.get());


    }

    @DeleteMapping
    public String deleteNoteById(@RequestParam("id") String id) {
        Optional<Note> note = noteService.deleteNoteById(id);
        return note.isEmpty() ? "Note not found." : String.format("Note %s deleted successful", id);
    }

    @PutMapping
    public Optional<Note> updateNoteById(@RequestParam("id") String id, @RequestBody Note noteToUpdate) {
        return noteService.updateNoteById(id, noteToUpdate);
    }

}
