package com.sevvalkocmar.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sevvalkocmar.dao.NoteDAO;
import com.sevvalkocmar.dao.NoteRepository;
import com.sevvalkocmar.entity.Note;
import com.sevvalkocmar.service.NoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NoteController.class)
public class NoteControllerTest {


    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private NoteService noteService;

    @MockBean
    private NoteDAO noteDAO;

    @MockBean
    private NoteRepository noteRepository;


    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testGetNoteById() throws Exception {

        Note note = new Note();
        note.setId("test");
        note.setTitle("Work");
        note.setDescription("meeting");


        when(noteService.getNoteById(eq("svvl"))).thenReturn(Optional.of(note));

        mockMvc.perform(MockMvcRequestBuilders.get("/notes?id=svvl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(note.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(note.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(note.getDescription()))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetNotes() throws Exception {
        Note note = new Note();
        note.setId("test");
        note.setTitle("Work");
        note.setDescription("meeting");

        Note note1 = new Note();
        note1.setId("test");
        note1.setTitle("Work");
        note1.setDescription("meeting");


        when(noteService.getNotes()).thenReturn(Arrays.asList(note, note1));


        mockMvc.perform(MockMvcRequestBuilders.get("/notes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(note.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(note.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(note.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(note1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value(note1.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(note1.getDescription()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNotesNoteIdIsEmpty() throws Exception {
        Note note = new Note();
        note.setId("test1");
        note.setTitle("Plan");
        note.setDescription("planing");

        when(noteService.getNoteById(note.getId())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/notes?id=test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty())
                .andExpect(status().isOk());


    }

    @Test
    public void testPostNote() throws Exception {

        Note note = new Note();
        note.setId("test1");
        note.setTitle("Plan");
        note.setDescription("planing");

        when(noteService.createNote(any())).thenReturn(note);


        String json = mapper.writeValueAsString(note);

        mockMvc.perform(MockMvcRequestBuilders.post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json).characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(note.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(note.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(note.getDescription()))
                .andReturn();


    }


    @Test
    public void testDeleteByIdNoteFound() throws Exception {

        Note note = new Note();
        note.setId("test2");
        note.setTitle("Plan");
        note.setDescription("planing");

        when(noteService.deleteNoteById(eq("test2"))).thenReturn(Optional.of(note));

        mockMvc.perform(MockMvcRequestBuilders.delete("/notes?id=test2", note.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Note %s deleted successful", note.getId())));

    }

    @Test
    public void testDeleteByIdNoteIsNotFound() throws Exception {

        when(noteService.deleteNoteById(eq("test2"))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/notes?id=test2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string((("Note not found."))));


    }

    @Test
    public void testUpdateById() throws Exception {
        Note note = new Note();
        note.setId("test2");
        note.setTitle("Plan");
        note.setDescription("planing");


        when(noteService.updateNoteById(eq("test2"), any(Note.class))).thenReturn(Optional.of(note));


        mockMvc.perform(MockMvcRequestBuilders.put("/notes?id=test2").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(note)).characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(note.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(note.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(note.getDescription()))
                .andReturn();


    }


}
