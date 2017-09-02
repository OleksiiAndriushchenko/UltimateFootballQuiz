package com.example.andaleksei.ultimatefootballquiz;

import static android.R.attr.id;

/**
 * Created by ANDaleksei on 26.08.17.
 */

public class item {
    private String name;
    private int id;
    private int access;

    item() {
        name = "";
        id = -1;
        access = -1;
    }

    item(int id, String name, int access) {
        this.name = name;
        this.id = id;
        this.access = access;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAccess() {
        return access;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
