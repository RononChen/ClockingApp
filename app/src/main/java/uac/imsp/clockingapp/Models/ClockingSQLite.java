package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ClockingSQLite  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clocking_database.db";


        private static final int DATABASE_VERSION = 1;

    private static final String TABLE_POINTAGE = "pointage";
    private static final String COL_ID_POINTAGE = "id_pointage";
    private static final String COL_DATE_JOUR= "date_jour";
    private static final String COL_DATE_JOUR_REF= "id_jour_ref";
    private static final String COL_HEURE_ENTREE = "heure_entree";
    private static final String COL_HEURE_SORTIE= "heure_sortie";
    private static final String COL_MATRICULE="matricule";
    private static  final String COL_MATRICULE_REF="matricule_ref";
       private static final String CREATE_CLOCKING = "CREATE TABLE " +
            TABLE_POINTAGE + " (" +
            COL_ID_POINTAGE + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +

            COL_DATE_JOUR_REF+ " TEXT    ," +
            COL_HEURE_ENTREE + "   TEXT ," +
            COL_HEURE_SORTIE + " TEXT ," +
            COL_MATRICULE_REF+ " INTEGER NOT NULL ," +

               " FOREIGN KEY(" + COL_MATRICULE_REF +
               " ) REFERENCES employe(" + COL_MATRICULE+" ),"+
        "  FOREIGN KEY(" + COL_DATE_JOUR_REF +
            " ) REFERENCES jour(" + COL_DATE_JOUR+" ))" ;




    public ClockingSQLite(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CLOCKING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS "+TABLE_POINTAGE);

        onCreate(db);

    }
}
