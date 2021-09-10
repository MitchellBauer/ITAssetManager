package com.bauerperception.itassetmanager.model;

public class TaskEntity implements Entity{
    private int toDoID;
    private String toDoName;
    private String toDoPriority;
    private String toDoDescription;

    @Override
    public int getID() {
        return toDoID;
    }
}
