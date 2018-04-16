package com.kg.spaceminer.model;

import com.kg.spaceminer.SpaceMiner;

import java.text.NumberFormat;

public class Player {
    SpaceMiner game = SpaceMiner.getInstance();
    private static final Player ourInstance = new Player();
    private static final int id = 1;
    private static final String name = "Marina";
    private int level = 0;
    private int fuel = 10;
    private float credits = 0.0F;

    public static Player getInstance() {
        return ourInstance;
    }

    private Player() {
        //nope
    }

    //getters and setters
    public static String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public float getCredits() {
        return credits;
    }

    public String getCreditsFormatted(){
        String formattedCredits = null;
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        formattedCredits = currencyFormatter.format(this.credits);
        return formattedCredits;
    }

    public void setCredits(float credits) {
        this.credits = credits;
    }

    //commerce related
    public boolean addCredits(float newCredits){
        boolean additionSuccessful = false;

        if(this.credits < game.getMaxAllowableCredits()) {
            this.credits = this.credits + newCredits;
            additionSuccessful = true;
        }

        return additionSuccessful;
    }

    public boolean deductCredits(float deduction){
        boolean deductionSuccessful = false;

        if(deduction <= this.credits){//if the deduction is less then the player's total credits
            this.credits = this.credits - deduction;//deduct from credits
            deductionSuccessful = true; //mark successful
        }
        return deductionSuccessful;
    }
}
