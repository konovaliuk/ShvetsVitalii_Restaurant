package org.application.dao;

import java.util.List;

public interface DAO <T> {
    T get(int id);
    List<T> getAll();
    T create(T t);
    void update(T t);
    void delete(int id);
}
