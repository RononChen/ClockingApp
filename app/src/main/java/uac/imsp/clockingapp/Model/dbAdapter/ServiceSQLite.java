package dbAdapter;




import static dbAdapter.ClockingSQLite.CREATE_CLOCKING;
import static dbAdapter.ClockingSQLite.DROP_CLOCKING;
import static dbAdapter.DaySQLite.CREATE_DAY;
import static dbAdapter.DaySQLite.DROP_DAY;
import static dbAdapter.EmployeeSQLite.CREATE_EMPLOYEE;
import static dbAdapter.EmployeeSQLite.CREATE_TEMP;
import static dbAdapter.EmployeeSQLite.DROP_EMPLOYEE;
import static dbAdapter.EmployeeSQLite.DROP_TEMP;
import static dbAdapter.EmployeeSQLite.super_user;
import static dbAdapter.PlanningSQLite.CREATE_PLANNING;
import static dbAdapter.PlanningSQLite.DROP_PLANNING;
import static dbAdapter.PlanningSQLite.planning;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceSQLite  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clocking_database.db";
    private static final int DATABASE_VERSION = 1;
        private static final String TABLE_SERVICE = "service";
    private static final String COL_ID_SERVICE = "id_service";
    private static final String COL_NOM_SERVICE="nom";


    public static final String CREATE_SERVICE = "CREATE TABLE IF NOT EXISTS  " +
            TABLE_SERVICE + " (" +
            COL_ID_SERVICE + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
            COL_NOM_SERVICE + " TEXT UNIQUE NOT NULL  "+")" ;
    public static final  String DROP_SERVICE="DROP TABLE IF EXISTS "+TABLE_SERVICE;


    @Override
    public void onDowngrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    public ServiceSQLite(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            createDatabase(db);

    }
    public void createDatabase(@NonNull SQLiteDatabase db){
        db.execSQL(CREATE_SERVICE);
        db.execSQL(CREATE_PLANNING);
        db.execSQL(CREATE_DAY);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(CREATE_CLOCKING);
        db.execSQL(CREATE_TEMP);
        SQLiteStatement statement= db.compileStatement(super_user);
        statement.bindLong(1,1);
        statement.bindString(2,"User10");
        statement.bindString(3,md5("Aab10%"));
        statement.bindString(4,"Directeur");
        statement.bindString(5,"M");
        statement.bindString(6,"super@gmail.com");
        statement.bindLong(7,1);
        statement.bindLong(8,1);
        statement.bindString(9,"AKOBA");
        statement.bindString(10,"Patrick");
        statement.bindString(11,"1970-01-01");
        statement.bindString(12,"true");
       // statement.bindString(12,"DATE('NOW','LOCALTIME'))");
        statement.executeInsert();
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
        statement.executeInsert();
        statement.bindBlob(3,workDays);


        //08-18

        statement.bindString(1,"08:00");
        statement.bindString(2,"18:00");
        statement.bindBlob(3,workDays);

        statement.executeInsert();


    }
    public String md5(@NonNull String password) {
        MessageDigest digest;
        byte[] messageDigest;
        StringBuilder hexString;
        try {

            digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            messageDigest = digest.digest();
            hexString = new StringBuilder();
            for (byte element : messageDigest) {
                hexString.append(Integer.toHexString(0xFF & element));
                return hexString.toString();

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }





    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       upgradeDatabase(db);



    }
    public void upgradeDatabase(@NonNull SQLiteDatabase db){
        db.execSQL(DROP_EMPLOYEE);
        db.execSQL(DROP_SERVICE);
        db.execSQL(DROP_PLANNING);
        db.execSQL(DROP_DAY);
        db.execSQL(DROP_CLOCKING);
        db.execSQL(DROP_TEMP);
        onCreate(db);

    }
}
