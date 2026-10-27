package com.epam.training.todolist.dataaccess.documents;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository  extends CrudRepository<Todo, String> {
}
