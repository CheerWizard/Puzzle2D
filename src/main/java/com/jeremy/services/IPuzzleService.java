package com.jeremy.services;

import com.jeremy.models.PuzzleLevel;

public interface IPuzzleService<T> extends IService {
    T[] split(String levelName);
    boolean check(T[] t);
    T[] resolve(PuzzleLevel puzzleLevel);
    T[] get();
    T[] update(T[] ts);
}
