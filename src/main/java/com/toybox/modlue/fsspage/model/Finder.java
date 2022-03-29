package com.toybox.modlue.fsspage.model;

import java.lang.reflect.Field;
import java.util.List;

public abstract class Finder {
    abstract public <T> List<T> find(List<T> list);
    abstract protected <T> boolean exist(T t, Field f) throws IllegalAccessException;

}
