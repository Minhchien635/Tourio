package com.example.tourio.Dao;

import com.example.tourio.Jdbc.DataHelper;

public class DAO {

    public static boolean suaNguyeLieu(int n) {
        try {
            String sql = "";
            DataHelper.execAction(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
