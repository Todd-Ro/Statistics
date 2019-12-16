package com.company;

import javafx.util.Pair;

import java.util.Comparator;

public class SortByIntegerInPair implements Comparator<Pair<Integer, Double>> {
    public int compare(Pair<Integer, Double> a, Pair<Integer, Double> b) {
        return (a.getKey() - b.getKey());
    }
}
