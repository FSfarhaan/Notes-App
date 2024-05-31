package com.example.notes;

import java.util.Comparator;

public class NotesModelTimestampComparator implements Comparator<NotesModel> {
    @Override
    public int compare(NotesModel n1, NotesModel n2) {
        return Long.compare(n2.getModifiedOn(), n1.getModifiedOn());
    }
}