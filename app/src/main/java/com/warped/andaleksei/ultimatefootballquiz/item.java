package com.warped.andaleksei.ultimatefootballquiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDaleksei on 26.08.17.
 */

public class item {
    private String name;
    private int id;
    private int access;
    private int completed;
    // clubs and years are needed for transfer
    private String[] clubs;
    private Integer[] years;

    item() {
        name = "";
        id = -1;
        access = -1;
        completed = -1;
    }

    item(int id, String name, int access, int completed) {
        this.name = name;
        this.id = id;
        this.access = access;
        this.completed = completed;
    }

    item(item object) {
        this.name = object.getName();
        this.id = object.getId();
        this.access = object.getAccess();
        this.completed = object.getCompleted();
    }

    // divide String int footballer name, clubs and years (transfer mode)
    public String divideString() {
        String tempName = name;

        int index = tempName.indexOf(":");

        String footballerName = tempName.substring(0, index);

        tempName = tempName.substring(index + 1);

        List<String> clubsList = new ArrayList<String>();
        List<Integer> yearsList = new ArrayList<Integer>();

        boolean end = false;

        do {
            clubsList.add(tempName.substring(0, tempName.indexOf("(")));
            int beginIndex = tempName.indexOf("(") + 1;
            int endIndex = beginIndex + 4;
            yearsList.add(Integer.parseInt(tempName.substring(beginIndex, endIndex)));

            tempName = tempName.substring(endIndex + 1);

            // if we reach end of string
            if (tempName.length() < 4) {
                yearsList.add(0);
                end = true;
            } else {
                yearsList.add(Integer.parseInt(tempName.substring(0, 4)));

                tempName = tempName.substring(6);
            }

        } while (!end);

        clubs = clubsList.toArray(new String[clubsList.size()]);
        years = yearsList.toArray(new Integer[yearsList.size()]);

        return footballerName;
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

    public int getCompleted() { return completed; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public void setCompleted(int completed) { this.completed = completed; }

    public int getNumberOfClubs() {
        return clubs.length;
    }

    public String getClubName(int position) {
        return clubs[position];
    }

    public String getYears(int pos) {
        String yearsString = Integer.toString(years[2 * pos]) + "-";
        if (2 * pos != years.length - 2) {
            yearsString += Integer.toString(years[2 * pos + 1]);
        }
        return yearsString;
    }

}
