package com.example.tictactoe;

public class UserModel {
    String userName;
    int id;
    int wins;
    int loses;

    public UserModel() {
    }

    public UserModel(int id, String userName, int wins, int loses) {
        this.userName = userName;
        this.id = id;
        this.wins = wins;
        this.loses = loses;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", id=" + id +
                ", wins=" + wins +
                ", loses=" + loses +
                '}';
    }
}
