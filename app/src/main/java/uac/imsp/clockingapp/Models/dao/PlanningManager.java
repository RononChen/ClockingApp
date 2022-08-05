package uac.imsp.clockingapp.Models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import uac.imsp.clockingapp.Models.entity.Planning;
import uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite;


public class PlanningManager {
    private SQLiteDatabase Database = null;
    private PlanningSQLite planningSQLite;

    public PlanningManager(Context context) {
        planningSQLite = new PlanningSQLite(context);
    }

    public SQLiteDatabase open() {

        if (Database == null)
            Database = planningSQLite.getWritableDatabase();
        return  Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public boolean delete(Planning planning) {
  String query="DELETE FROM planning WHERE id_planning=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindLong(1,planning.getId());
        statement.executeUpdateDelete();
        return true;
    }


    public void create(Planning planning) {
        String query;
        SQLiteStatement statement;
        if(!searchPlanning(planning))
        {
       query="INSERT INTO planning(heure_debut_officielle,heure_fin_officielle) VALUES (?,?)";

      statement=Database.compileStatement(query);
      statement.bindString(1,planning.getStartTime());
      statement.bindString(2,planning.getEndTime());
      statement.executeInsert();
      //The planning exists now
            //We'll search it so as to setId

            searchPlanning(planning);

        }
    }
    //setl'id du planning  puis retourne true s'il existe et false sinon
    public boolean searchPlanning(Planning planning){
        boolean test;

        String query="SELECT id_planning FROM planning WHERE heure_debut_officielle=? " +
                "AND heure_fin_officielle=?" ;
                String [] selectArgs={
                planning.getStartTime(),planning.getEndTime()
        };
        Cursor cursor=Database.rawQuery(query,selectArgs);
        cursor.moveToFirst();
        test=cursor.getCount()==1;
        if(test) {
            planning.setId(Integer.parseInt(cursor.getString(0)));
            cursor.close();
            return true;

        }
        cursor.close();
        return false;

        }
    /*public Planning getPlanning(Employee employee){
           Planning planning;

    }*/
    /*public void setId(Planning planning){


    }*/
}
