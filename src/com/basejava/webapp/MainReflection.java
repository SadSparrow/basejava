package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SortedArrayStorage;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        Resume r = new Resume("uuid123", "Ivanov Ivan");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        // TODO : invoke r.toString via reflection
        System.out.println(Resume.class.getMethod("toString").invoke(r));

        //for fun
        SortedArrayStorage s = new SortedArrayStorage();
        Class victimClass = s.getClass().getSuperclass();
        Field[] fields = victimClass.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getName() + ", " + f.getType());
        }
        fields[0].setAccessible(true); //fields[0].trySetAccessible();
        System.out.println(fields[0].getInt(s));
    }
}
