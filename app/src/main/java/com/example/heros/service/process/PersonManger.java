package com.example.heros.service.process;

import android.os.IInterface;

import java.util.List;

public interface PersonManger extends IInterface {

    void addPerson(Person mPerson);
    List<Person> getPersonList();
}
