package dao;

import java.util.List;

import entity.SuperEntity;

public interface SuperDAO <T extends SuperEntity,ID> {

  List<T> findAll();

  T find(ID key);

  boolean save(T entity);

  boolean update(T entity);

  boolean delete(ID key);

}
