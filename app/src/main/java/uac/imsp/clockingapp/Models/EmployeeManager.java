package uac.imsp.clockingapp.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class EmployeeManager {
    public final static int   CAN_NOT_LOGIN=15;
    private SQLiteDatabase Database=null;
    private EmployeeSQLite employeeSQLite;
    //private Context context;


    public EmployeeManager(Context context) {
        employeeSQLite= new EmployeeSQLite(context);
    }
    public SQLiteDatabase open() {
        if(Database==null)
         Database=employeeSQLite.getWritableDatabase();
        return Database;
    }

    public void close(){
        if (Database!=null && Database.isOpen())
            Database.close();
        }

public int connectUser(Employee employee,String password){

        open();

        String query="SELECT matricule,password FROM employe WHERE username=?";

        String [] selectArgs={employee.getUsername()};
        Cursor cursor = Database.rawQuery(query,selectArgs);
                 cursor.moveToFirst();
        if(cursor.getCount()==1 && cursor.getString(1).equals(password))
            return 0;
        return CAN_NOT_LOGIN;

}



    //create,delete,update

    public void create (Employee employee){
       open();
        SQLiteStatement statement;
        String query = "INSERT INTO employe (matricule,nom,prenom,sexe,birthdate,couriel,photo,username,password) VALUES(?,?,?,?,?,?,?,?,?) ";
                statement=Database.compileStatement(query);
        statement.bindLong(1,employee.getRegistrationNumber());

        statement.bindString(2,employee.getLastname());
        statement.bindString(3,employee.getFirstname());
        statement.bindString (4, Character.toString((char) employee.getGender()));
        statement.bindString(5,employee.getBirthdate());
        statement.bindString(6,employee.getMailAddress());
        statement.bindBlob(7, employee.getPicture());
         statement.bindBlob(8,employee.getQRCode());
        statement.bindString(9,employee.getUsername());
        statement.bindString(10,employee.getPassword());

        statement.executeInsert();

    }
//On peut modifier le courier ou la photo de l'employé


    //Pour modifier le courier de l'employé
    public boolean update (Employee employee, String mailAddress){

        String query="UPDATE employe SET couriel =? WHERE matricule=?";
        SQLiteStatement statement  ;
                statement=Database.compileStatement(query);
        statement.bindString(1, mailAddress);
        statement.bindLong(2, employee.RegistrationNumber);
        return true;
    }

    //Pour modifier la photo de l'employé
    public boolean update (Employee employee, byte[] picture){
        String query="UPDATE employe SET photo =? WHERE matricule=?";
        SQLiteStatement statement ;
        statement=Database.compileStatement(query);
        statement.bindBlob(1, picture);
        statement.bindLong(2, employee.RegistrationNumber);
        statement.executeUpdateDelete();
        return true;
    }
    //Enregistrer le code QR
    public void storeQRCode(Employee employee ){

        String query="UPDATE employe SET qr_code =? WHERE matricule=?";
        SQLiteStatement statement ;
        statement=Database.compileStatement(query);
        statement.bindBlob(1, employee.getQRCode());
        statement.bindLong(2, employee.RegistrationNumber);
        statement.executeUpdateDelete();

    }
    public Planning getPlanning(Employee employee){
        Planning planning ;
        String query="SELECT heure_debut_offficielle,heure_fin_officielle " +
                "FROM employe JOIN planning ON id_planning=id_planning_ref " +
                "WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query,selectArgs);
        planning = new Planning(cursor.getString(0),
                                cursor.getString(1));
        return planning;
    }
    public Service getService(Employee employee){
        Service service ;
        String query="SELECT service.nom  " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query,selectArgs);
        service = new Service(cursor.getString(0));
        return service;
    }


    public boolean delete(Employee employee){
        String query = "DELETE FROM employe WHERE matricule=?";
        SQLiteStatement statement ;

        statement = Database.compileStatement(query);
        statement.bindLong(1,employee.RegistrationNumber);
        statement.executeUpdateDelete();
        return true;
    }


//Pour rechercher un employé

/*On peut rechercher par matricule,nom,prénom,sexe,date de naissance ou mail
//Cette méthode prend la donnée à rechercher et retourne
un tableau contenant les emplyés vérifiant le motif de recherche*/

    //c'est une méthode de classe
    public  static Employee[] search(String data){

        return null;
    }

    public boolean exists(@NonNull Employee employee){

       String query ="SELECT matricule FROM employe WHERE matricule=?";
       String [] selectArg={Integer.valueOf(employee.getRegistrationNumber()).toString()};
        Cursor cursor = Database.rawQuery(query,selectArg);
        return cursor.getColumnCount()==1;

    }


    public void setInformations(Employee employee){

        String query="SELECT nom,prenom,sexe,photo FROM employe WHERE matricule=?";
        String [] selectArgs={
                Integer.valueOf(employee.getRegistrationNumber()).toString()
        };
        Cursor cursor =Database.rawQuery(query,selectArgs);
        cursor.moveToFirst();
         employee.setLastname(cursor.getString(0));
        employee.setFirstname(cursor.getString(1));
        employee.setGender(cursor.getString(2).charAt(0));
        employee.setPicture(cursor.getBlob(3));

    }
   /* public void update(Employee employee){

    }*/
    public void update(@NonNull Employee employee, @NonNull Service service){
        String query="UPDATE employe SET id_service_ref=? WHERE matricule=?";
        SQLiteStatement statement=Database.compileStatement(query);
               statement.bindLong(1,service.getId());
               statement.bindLong(2,employee.getRegistrationNumber());
               statement.executeUpdateDelete();

    }
    public void update(@NonNull Employee employee, Planning planning){
        String query="UPDATE employe SET id_planning_ref=? WHERE matricule=?";
        SQLiteStatement statement=Database.compileStatement(query);
       statement.bindLong(1,planning.getId());

        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    //Retourne un tableau conteneant le matricule de tous les employés
    public String[] getAllEmployees(){
        ArrayList<String> employee= new ArrayList<>();

        String query="SELECT matricule FROM employee ORDER BY matricule ";
        Cursor cursor=Database.rawQuery(query,null);
        while (cursor.moveToNext())
            employee.add(cursor.getString(0));

        return employee.toArray(new String[employee.size()]);


    }


}

