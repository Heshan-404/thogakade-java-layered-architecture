package edu.iuhs.crm.repository;

import javafx.collections.ObservableList;

import java.util.List;

public interface CrudRepository<T, ID> extends SuperRepository {
    boolean permit(T entity);

    boolean update(T entity);

    List<T> getAll();

    boolean delete(ID id);

    T search(ID id);

    ObservableList<String> getAllId();
}
