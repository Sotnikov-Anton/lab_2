package mephi.lab2;

import java.util.ArrayList;

public class DataStorage {
    private String source;
    private ArrayList<Reactor> reactors = new ArrayList<>();

    public String getSource() {
        return source;
    }
    public ArrayList<Reactor> getReactors() {
        return reactors;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setReactors(ArrayList<Reactor> reactors) {
        this.reactors = reactors;
    }
}