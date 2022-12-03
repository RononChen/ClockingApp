package uac.imsp.clockingapp.Models.dbAdapter;

import static uac.imsp.clockingapp.Models.dbAdapter.ClockingSQLite.CREATE_CLOCKING;
import static uac.imsp.clockingapp.Models.dbAdapter.ClockingSQLite.DROP_CLOCKING;
import static uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite.CREATE_EMPLOYEE;
import static uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite.CREATE_TEMP;
import static uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite.DROP_EMPLOYEE;
import static uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite.DROP_TEMP;
import static uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite.super_user;
import static uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite.CREATE_PLANNING;
import static uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite.DROP_PLANNING;
import static uac.imsp.clockingapp.Models.dbAdapter.PlanningSQLite.planning;
import static uac.imsp.clockingapp.Models.dbAdapter.ServiceSQLite.CREATE_SERVICE;
import static uac.imsp.clockingapp.Models.dbAdapter.ServiceSQLite.DROP_SERVICE;
import static uac.imsp.clockingapp.Models.entity.Employee.HEAD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DaySQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_JOUR = "jour";
    public static final String COL_DATE_JOUR= "date_jour";
    public static final String COL_ID_JOUR="id_jour";




    public static final String CREATE_DAY = "CREATE TABLE IF NOT EXISTS " + TABLE_JOUR + " (" +

            COL_ID_JOUR + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT , " +
            COL_DATE_JOUR+" TEXT )" ;
    public static final String DROP_DAY="DROP TABLE IF EXISTS "+TABLE_JOUR;




    public DaySQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void createDatabase(SQLiteDatabase db){
        db.execSQL(CREATE_SERVICE);
        db.execSQL(CREATE_PLANNING);
        db.execSQL(CREATE_DAY);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(CREATE_CLOCKING);
        db.execSQL(CREATE_TEMP);
        SQLiteStatement statement= db.compileStatement(super_user);
        statement.bindLong(1,1);
        statement.bindString(2,"User10");
        statement.bindString(3,"password");
        statement.bindString(4,HEAD);
        statement.bindString(5,"M");
        statement.bindString(6,"super@gmail.com");
        statement.bindLong(7,1);
        statement.bindLong(8,1);
        statement.bindString(9,"AKOBA");
        statement.bindString(10,"Patrick");
        statement.bindString(11,"1970-01-01");
        statement.bindString(12,"DATE('NOW','LOCALTIME'))");
        statement.execute();
        String service = "INSERT INTO service(nom)  VALUES (?)";
        statement= db.compileStatement(service);
        //Direction
        statement.bindString(1,"Direction");
        statement.executeInsert();

        //Service scolarité
        statement.bindString(1,"Service scolarité");
        statement.executeInsert();

        //Secrétariat administratif
        statement.bindString(1,"Secrétariat administratif");
        statement.executeInsert();
        //Comptabilité
        statement.bindString(1,"Comptabilité");
        statement.executeInsert();
        //Service de coopération
        statement.bindString(1,"Service de coopération");
        statement.executeInsert();



        statement= db.compileStatement(planning);
        byte[] workDays= new byte[]{'T','T','T','T','T','F','F'};


        //08-17
        statement.bindString(1,"08:00");
        statement.bindString(2,"17:00");
        statement.bindBlob(3,workDays);


        statement.executeInsert();

        //08-18

        statement.bindString(1,"08:00");
        statement.bindString(2,"18:00");
        statement.bindBlob(3,workDays);

        statement.executeInsert();


    }
    public void upgradeDatabase(SQLiteDatabase db){
        db.execSQL(DROP_EMPLOYEE);
        db.execSQL(DROP_SERVICE);
        db.execSQL(DROP_PLANNING);
        db.execSQL(DROP_DAY);
        db.execSQL(DROP_CLOCKING);
        db.execSQL( DROP_TEMP);
        onCreate(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(CREATE_DAY);
        createDatabase(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL(DROP_DAY);
       // onCreate(db);
        upgradeDatabase(db);

    }
}

