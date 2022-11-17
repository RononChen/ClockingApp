package uac.imsp.clockingapp.Models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite;
import uac.imsp.clockingapp.Models.entity.Planning;


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
       query="INSERT INTO planning(heure_debut_officielle,heure_fin_officielle," +
               "jours_de_travail) VALUES (?,?,?)";
      statement=Database.compileStatement(query);
      statement.bindString(1,planning.getStartTime());
      statement.bindString(2,planning.getEndTime());
      statement.bindBlob(3, planning.getWorkDays());
      statement.executeInsert();
      //The planning exists now
            //We'll search it so as to setId

            searchPlanning(planning);

        }
    }
    //setl'id du planning  puis retourne true s'il existe et false sinon
    public boolean searchPlanning(Planning planning){
        boolean test;
        byte[] workdays=planning.getWorkDays();
        //Blob blob = new javax.sql.rowset.serial.;
       //// Bitmap bm = BitmapFactory.decodeByteArray(workdays, 0 ,workdays.length);
       //// Blob b=workdays;

        //SerialBlob blob;
       // Blob blob = new SerialBlob(hashValue);
        String query="SELECT id_planning FROM planning WHERE heure_debut_officielle=? " +
                "AND heure_fin_officielle=? AND jours_de_travail=?" ;
                String [] selectArgs={
                planning.getStartTime(),planning.getEndTime(),
                        new String(planning.getWorkDays())
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

}
