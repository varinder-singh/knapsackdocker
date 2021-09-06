package com.example.model;

import java.util.List;

public class KnapsackSolution {

    private List<Integer> packedItems;
    private int totalValue;

    public List<Integer> getPackedItems() {
        return packedItems;
    }

    public void setPackedItems(List<Integer> packedItems) {
        this.packedItems = packedItems;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }
}
