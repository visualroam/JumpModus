package me.dominik.Jumper.methoden;

public class Formatting {

    public static String formatSecondsToMMSS(int timer) {
        return twoDigitString(timer % 3600 / 60) + ":" + twoDigitString(timer % 60);
    }


    public static String twoDigitString(int number) {
        return number == 0 ? "00" : (number / 10 == 0 ? "0" + number : Integer.toString(number));
    }

    public static int Random(int von, int bis){
        return (int) Math.random() * bis + von;
    }

    public static String getPlatz(int p){
        return "§4§nPlatz " + p;
    }

}

