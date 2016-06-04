package me.dominik.Jumper.methoden;

public class Formatierung {

    public static String formatSecondsToMMSS(int timer) {
        return twoDigitString(timer % 3600 / 60) + ":" + twoDigitString(timer % 60);
    }


    public static String twoDigitString(int number) {
        return number == 0 ? "00" : (number / 10 == 0 ? "0" + number : Integer.toString(number));
    }

    public static int Random(int von, int bis){
        int random = (int) Math.random() * bis + von;
        return random;
    }

    public static String getPlatz(int p){
        return "Platz " + p;
    }

}
