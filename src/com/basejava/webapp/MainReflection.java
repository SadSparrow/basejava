package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.AbstractArrayStorage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        Resume r = new Resume("uuid123");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        // TODO : invoke r.toString via reflection
        System.out.println(Resume.class.getMethod("toString", null).invoke(r, null));

        //IllegalAccessException: class com.basejava.webapp.MainReflection cannot access a member of class com.basejava.webapp.storage.AbstractArrayStorage with modifiers "protected static final"
        Field f = AbstractArrayStorage.class.getDeclaredField("STORAGE_LIMIT");
        f.setAccessible(true);
        System.out.println(AbstractArrayStorage.class.getDeclaredField("STORAGE_LIMIT").get(null));



    }
}
