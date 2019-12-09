package fr.centrale.rom.news.models;

import java.util.ArrayList;

public class SourceList {
    private ArrayList<NewsSource> sources;

    public SourceList() {
        sources = new ArrayList<>();
    }

    public ArrayList<NewsSource> getSources() {
        return sources;
    }

    public void addSource(NewsSource source){
        sources.add(source);
    }

    public void clearSources(){
        sources.clear();
    }

    public NewsSource get(int i){
        return sources.get(i);
    }

    public int size(){
        return sources.size();
    }

    private static final SourceList list = new SourceList();
    public static SourceList getInstance(){
        return list;
    }
}
