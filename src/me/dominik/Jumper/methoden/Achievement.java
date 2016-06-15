package me.dominik.Jumper.methoden;

public class Achievement {

    private String name;
    private String description;
    private String databaseString;

    public Achievement(String name , String description){
        this.name = name;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }


}
