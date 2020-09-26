package com.sevvalkocmar.service;

import com.sevvalkocmar.dao.NoteDAO;
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



@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {


    @InjectMocks
    private NoteService noteService;


    @Mock
    private NoteDAO noteDAO;

    @Test
    public void testCreateNote() {
        //given
        Note note = new Note();
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteDAO.createNote(note)).thenReturn(note);

        //when
        Note result = noteService.createNote(note);

        //then
        assertThat(result.getTitle()).isEqualTo(note.getTitle());
        assertThat(result.getDescription()).isEqualTo(note.getDescription());
        verify(noteDAO, times(1)).createNote(note);
        verifyNoMoreInteractions(noteDAO);
    }

    @Test
    public void testGetNotes() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        Note note2 = new Note();
        note2.setId("Test2");
        note2.setTitle("Test2-Title");
        note2.setDescription("Test2-Description");


        when(noteDAO.getNotes()).thenReturn(Arrays.asList(note, note2));
        Collection<Note> result = noteService.getNotes();

        assertThat(result).hasSize(2).containsExactly(note, note2);
        verify(noteDAO, times(1)).getNotes();
        verifyNoMoreInteractions(noteDAO);


    }

    @Test
    void testGetNoteById() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");
        when(noteDAO.getNoteByID(note.getId())).thenReturn(Optional.of(note));

        Optional<Note> result = noteService.getNoteById(note.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(note.getId());
        assertThat(result.get().getTitle()).isEqualTo(note.getTitle());
        assertThat(result.get().getDescription()).isEqualTo(note.getDescription());
        verify(noteDAO, times(1)).getNoteByID(note.getId());
        verifyNoMoreInteractions(noteDAO);

    }

    @Test
    public void testDeleteById() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteDAO.deleteNoteById(note.getId())).thenReturn(Optional.of(note));

        Optional<Note> result = noteService.deleteNoteById(note.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(note.getId());
        assertThat(result.get().getTitle()).isEqualTo(note.getTitle());
        assertThat(result.get().getDescription()).isEqualTo(note.getDescription());
        verify(noteDAO, times(1)).deleteNoteById(note.getId());
        verifyNoMoreInteractions(noteDAO);

    }

    @Test
    public void testDeleteNoteByIdNoteNotFound()  {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteDAO.deleteNoteById(note.getId())).thenReturn(Optional.empty());

        Optional<Note> optionalNote = noteService.deleteNoteById(note.getId());

        assertThat(optionalNote.isPresent()).isFalse();
        verify(noteDAO, times(1)).deleteNoteById(note.getId());
        verifyNoMoreInteractions(noteDAO);
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

        when(noteDAO.updateNoteById(note.getId(), noteToUpdate)).thenReturn(Optional.of(updated));

        Optional<Note> result = noteService.updateNoteById(note.getId(), noteToUpdate);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(updated.getId());
        assertThat(result.get().getTitle()).isEqualTo(updated.getTitle());
        assertThat(result.get().getDescription()).isEqualTo(updated.getDescription());
        verify(noteDAO, times(1)).updateNoteById(note.getId(), noteToUpdate);
        verifyNoMoreInteractions(noteDAO);

    }

    @Test
    public void testUpdateNoteByIdWhenNoteIsNotFound() {
        Note note = new Note();
        note.setId("Test");
        note.setTitle("Test-Title");
        note.setDescription("Test-Description");

        when(noteDAO.updateNoteById(note.getId(), note)).thenReturn(Optional.empty());

        Optional<Note> optionalNote = noteService.updateNoteById(note.getId(), note);

        assertThat(optionalNote.isPresent()).isFalse();
        verify(noteDAO, times(1)).updateNoteById(note.getId(), note);
        verifyNoMoreInteractions(noteDAO);
    }
}
