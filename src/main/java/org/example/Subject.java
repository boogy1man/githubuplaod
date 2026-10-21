package org.example;


public class Subject {

    private String name;
    private int attended;
    private int total;

    public Subject(String name, int attended, int total) {
        this.name = name;
        this.attended = attended;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public int getAttended() {
        return attended;
    }

    public int getTotal() {
        return total;
    }

    public int getPercentage() {
        if (total == 0) {
            return 0;
        }

        return attended * 100 / total;
    }
}
