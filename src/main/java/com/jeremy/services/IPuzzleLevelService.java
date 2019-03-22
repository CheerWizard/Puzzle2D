package com.jeremy.services;

public interface IPuzzleLevelService<T> extends IService{
    T update(T t);
    T get(String levelName);
    T get(int id , String levelName);
    void delete(int id);
    void delete(String name);
}
