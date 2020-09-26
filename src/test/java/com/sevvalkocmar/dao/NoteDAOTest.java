package com.sevvalkocmar.dao;

import com.sevvalkocmar.entity.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
public class NoteDAOTest {

    @InjectMocks
    private NoteDAO noteDAO;

    @Mock
    private NoteRepository noteRepository;

    @Test
    public void testGetNotes() {

        //given
        Note note1 = new Note();
        note1.setId("Test");
        note1.setTitle("Test-Title");
        note1.setDescription("Test-Description");

        Note note2 = new Note();
        note2.setId("Test2");
        note2.setTitle("Test2-Title");
        note2.setDescription("Test2-Description");


        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));
        //when
        Collection<Note> notes = noteDAO.getNotes();

        //then
        assertThat(notes).hasSize(2).containsExactly(note1, note2);
        verify(noteRepository, times(1)).findAll();
        verifyNoMoreInteractions(noteRepository);
        assertEquals("Toplamda 2 not kayıtlı bulunmalı.", 2, notes.size());


    }

    @Test
    public void testCreateNote() {

        Note note1 = new Note();
        note1.setId("Test");
        note1.setTitle("Test-Title");
        note1.setDescription("Test-Description");

        when(noteRepository.insert(note1)).thenReturn(note1);
        Note note = noteDAO.createNote(note1);

        assertThat(note.getId()).isEqualTo(note1.getId());
        assertThat(note.getTitle()).isEqualTo(note1.getTitle());
        assertThat(note1.getDescription()).isEqualTo(note1.getDescription());
        verify(noteRepository, times(1)).insert(note1);
        verifyNoMoreInteractions(noteRepository);

    }

    @Test
    public void testGetNotesById() {
        Note note1 = new Note();
        note1.setId("Test");
        note1.setTitle("Test-Title");
        note1.setDescription("Test-Description");


        when(noteRepository.findById(note1.getId())).thenReturn(Optional.of(note1));
        Optional<Note> optinalnoteByID = noteDAO.getNoteByID(note1.getId());

        assertThat(optinalnoteByID.isPresent()).isTrue();
        assertThat(optinalnoteByID.get().getId()).isEqualTo(note1.getId());
        assertThat(optinalnoteByID.get().getTitle()).isEqualTo(note1.getTitle());
        assertThat(optinalnoteByID.get().getDescription()).isEqualTo(note1.getDescription());
        verify(noteRepository, times(1)).findById(note1.getId());
        verifyNoMoreInteractions(noteRepository);
    }

    @Test
    public void testDeleteByIdNoteFound() {

        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        Optional<Note> noteDeleted = noteDAO.deleteNoteById(note.getId());

        assertThat(noteDeleted.isPresent()).isTrue();
        assertThat(noteDeleted.get().getId()).isEqualTo(note.getId());
        assertThat(noteDeleted.get().getTitle()).isEqualTo(note.getTitle());
        assertThat(noteDeleted.get().getDescription()).isEqualTo(note.getDescription());
        verify(noteRepository, times(1)).delete(note);
        verifyNoMoreInteractions(noteRepository);
    }

    @Test
    public void testDeleteByIdNoteNotFound() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());

        Optional<Note> optionalNote = noteDAO.deleteNoteById(note.getId());

        assertThat(optionalNote.isPresent()).isFalse();
        verify(noteRepository, times(1)).findById(note.getId());//???
        verifyNoMoreInteractions(noteRepository);
    }

    @Test
    public void testUpdateNoteById() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        Note noteToUpdate = new Note();
        noteToUpdate.setTitle("Test2-Title");
        noteToUpdate.setDescription("Test2-Description");

        Note updated = new Note();
        updated.setId(note.getId());
        updated.setTitle(noteToUpdate.getTitle());
        updated.setTitle(noteToUpdate.getDescription());

        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(updated));
        Optional<Note> result = noteDAO.updateNoteById(note.getId(), updated);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(updated.getId());
        assertThat(result.get().getTitle()).isEqualTo(updated.getTitle());
        assertThat(result.get().getDescription()).isEqualTo(updated.getDescription());
        verify(noteRepository, times(1)).save(updated);
        verifyNoMoreInteractions(noteRepository);
    }

    @Test
    public void testUpdateNoteByIdNoteIsNotFound() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());

        Optional<Note> optionalNote = noteDAO.updateNoteById(note.getId(), note);

        assertThat(optionalNote.isPresent()).isFalse();
        verify(noteRepository, times(1)).findById(note.getId());
        verifyNoMoreInteractions(noteRepository);
    }
}
