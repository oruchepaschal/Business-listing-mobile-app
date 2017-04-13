package com.ecoach.cosapp.DataBase;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Model;

/**
 * Created by apple on 4/12/17.
 */

public abstract class TruncatableModel extends Model {
    public static void truncate(Class<? extends Model> type){
        final String tableName = Cache.getTableInfo(type).getTableName();

        // Delete all rows from table
        ActiveAndroid.execSQL(String.format("DELETE FROM %s;", tableName));

        // Reset ids
        ActiveAndroid.execSQL(String.format("DELETE FROM sqlite_sequence WHERE name='%s';", tableName));
    }
}
