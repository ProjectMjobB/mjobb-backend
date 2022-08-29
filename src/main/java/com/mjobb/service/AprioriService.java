package com.mjobb.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface AprioriService {

    void firstFrequentItemSet(int noOfTransactions, List<String> transactions);
    void display(int noOfTransactions, List<String> transactions);
    void setData(Long jobId);
    void aprioriStart(int noOfTransactions,List<String> transactions);
    void generateCombinations(int itr);
    void checkFrequentItems(int noOfTransactions,List<String> transactions);
    void generateAssociationRules(int noOfTransactions,List<String> transactions);

}
