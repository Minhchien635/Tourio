package com.tourio.dao;

import com.tourio.jdbc.DataHelper;

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
