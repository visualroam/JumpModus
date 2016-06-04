package me.dominik.Jumper.mysql;

import lombok.Getter;
import me.dominik.Jumper.Jumper;

import java.sql.*;
import java.time.temporal.JulianFields;

public class MySQL {

    private String HOST = "";
    private String DATABASE = "";
    private String USER = "";
    private String PASSWORD = "";

    public MySQL(String host, String database, String user, String password){
        this.HOST = host;
        this.DATABASE = database;
        this.USER = user;
        this.PASSWORD = password;



        connect();
    }

    private void connect() {
        try {
            Jumper.getInstance().setCon(DriverManager.getConnection("jdbc:mysql://" + HOST + ":3306/" + DATABASE + "?autoReconnect=true", USER, PASSWORD));
            System.out.println(Jumper.getPREFIX() + " Die Verbindund zur Datenbank wurde erstellt.");
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
    }

    public void close() {
        try {
            if(Jumper.getInstance().getCon() != null){
                Jumper.getInstance().getCon().close();
                System.out.println(Jumper.getPREFIX() + " Die Verbindund zur Datenbank wurde geschlossen.");
            }
        } catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
    }

    public void update(String query){
        try {
            Statement st = Jumper.getInstance().getCon().createStatement();
            st.executeUpdate(query);
            st.close();
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }

    }

    public ResultSet query(String query){
        ResultSet rs = null;
        try {
            Statement st = Jumper.getInstance().getCon().createStatement();
            rs = st.executeQuery(query);
        }catch (SQLException sqlex){
            sqlex.printStackTrace();
        }
        return rs;
    }

}
