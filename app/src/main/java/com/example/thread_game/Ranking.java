package com.example.thread_game;

public class Ranking implements Comparable<Ranking> {
    private String name;
    private int score;

    public Ranking(String name, int score){
        this.name=name;
        this.score=score;
    }
    String getName(){
        return name;
    }
    int getScore(){
        return score;
    }

    @Override
    public int compareTo(Ranking o) {
        if(score>o.score)
            return -1;
        else if (score ==o.score)
            return 0;
        else
            return 1;
    }
}
