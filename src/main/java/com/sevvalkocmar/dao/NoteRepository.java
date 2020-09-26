package com.sevvalkocmar.dao;


import com.sevvalkocmar.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface NoteRepository extends MongoRepository<Note, String> {


}
