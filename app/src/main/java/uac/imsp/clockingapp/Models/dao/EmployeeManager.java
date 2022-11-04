package uac.imsp.clockingapp.Models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.Models.entity.Planning;
import uac.imsp.clockingapp.Models.entity.Service;

public class EmployeeManager {
    public final static int   CAN_NOT_LOGIN=15;
    private SQLiteDatabase Database=null;
    private final EmployeeSQLite employeeSQLite;
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

public int connectUser(Employee employee, String password){
        int n;
        String str;
        int number;

        String query="SELECT matricule,password FROM employe WHERE username=?";

        String [] selectArgs={employee.getUsername()};

        Cursor cursor = Database.rawQuery(query,selectArgs);
                 cursor.moveToFirst();
                 n=cursor.getCount();
                 number=cursor.getInt(0);
                 str=cursor.getString(1);
                 cursor.close();
        if(n==1 && str.equals(password)) {
            employee.setRegistrationNumber(number);

            //employee=new Employee(number);
            setInformations(employee);
            return 0;
        }

        return CAN_NOT_LOGIN;

}




    //create,delete,update

    public void create (Employee employee){
      // open();
        SQLiteStatement statement;
        String query = "INSERT INTO employe (matricule,nom,prenom,sexe,birthdate," +
                "couriel,username,password,type) VALUES(?,?,?,?,?,?,?,?,?) ";



                statement=Database.compileStatement(query);

        statement.bindLong(1,employee.getRegistrationNumber());

        statement.bindString(2,employee.getLastname());
        statement.bindString(3,employee.getFirstname());
        statement.bindString (4, Character.toString((char) employee.getGender()));
        statement.bindString(5,employee.getBirthdate());
        statement.bindString(6,employee.getMailAddress());
        statement.bindString(7,employee.getUsername());
        statement.bindString(8,employee.getPassword());
        statement.bindString(9,employee.getType());

        statement.executeInsert();

    }
    
   public void setAccount(Employee employee){

    String query="SELECT username, password FROM employe WHERE matricule=?";
String [] selectArgs={String.valueOf(employee.getRegistrationNumber())};
Cursor cursor=Database.rawQuery(query,selectArgs);
if(cursor.moveToFirst()) {
    employee.setUsername(cursor.getString(0));
    employee.setPassword(cursor.getString(1));
}
cursor.close();


}
public void changePassword(Employee employee,String newPassword){

String query="UPDATE employe SET password=? WHERE matricule=?";

SQLiteStatement statement =Database.compileStatement(query);
statement.bindString(1,newPassword);
statement.bindLong(2,employee.getRegistrationNumber());

statement.executeUpdateDelete();




}
//On peut modifier le courier ou la photo de l'employé


    //update the email of employee
    public void update (Employee employee, String mailAddress){

        String query="UPDATE employe SET couriel =? WHERE matricule=?";
        SQLiteStatement statement  ;
                statement=Database.compileStatement(query);
        statement.bindString(1, mailAddress);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }


    public void changeGrade (Employee employee, String type){

        String query="UPDATE employe SET type =? WHERE matricule=?";
        SQLiteStatement statement  ;
        statement=Database.compileStatement(query);
        statement.bindString(1, type);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

    //To update the picture of the employee
    public void update (Employee employee, byte[] picture){
        String query="UPDATE employe SET photo =? WHERE matricule=?";
        SQLiteStatement statement ;
        statement=Database.compileStatement(query);
        statement.bindBlob(1, picture);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

    public Planning getPlanning(Employee employee){
        Planning planning=null ;
        String query="SELECT heure_debut_officielle,heure_fin_officielle " +
                "FROM planning  JOIN employe ON id_planning=id_planning_ref " +
                "WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query,selectArgs);
        if(cursor.moveToFirst())

        planning = new Planning(cursor.getString(0),
                                cursor.getString(1));
        cursor.close();
        return planning;
    }
    public Service getService(Employee employee){
        Service service ;
        String query="SELECT service.nom  " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query,selectArgs);
        cursor.moveToFirst();
        service = new Service(cursor.getString(0));

        cursor.close();
        return service;
    }
    public void delete(Employee employee){
        String query = "DELETE FROM employe WHERE matricule=?";
        SQLiteStatement statement ;

        statement = Database.compileStatement(query);
        statement.bindLong(1,employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
//Pour rechercher un employé
/*On peut rechercher par matricule,nom,prénom,ou service
//Cette méthode prend la donnée à rechercher et retourne
un tableau contenant les emplyés vérifiant le motif de recherche*/

    public  Employee[] search(String data){
        String query,queryAll;

        queryAll="SELECT matricule, employe.nom,prenom,photo " +
                "FROM employe JOIN service ON id_service=id_service_ref " ;

                 query="SELECT matricule, employe.nom,prenom,photo " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE  matricule||employe.nom||prenom||service.nom " +
                "LIKE '%'||?||'%'";

                String [] selectArg={data};
        ArrayList <Employee> employeeSet= new ArrayList<>();
        Employee employee;
        Cursor cursor;
        if(Objects.equals(data, "*"))
         cursor=Database.rawQuery(queryAll,null);
        else
            cursor=Database.rawQuery(query,selectArg);
        //cursor.moveToFirst();

        while (cursor.moveToNext())
        {
            employee = new Employee(cursor.getInt(0));
            employee.setLastname(cursor.getString(1));
            employee.setFirstname(cursor.getString(2));
            employee.setPicture(cursor.getBlob(3));
            employeeSet.add(employee);
        }
        cursor.close();
        return employeeSet.toArray(new Employee[employeeSet.size()]);

    }


    public boolean exists(@NonNull Employee employee){

     return registrationNumberExists(employee);


    }
    public boolean registrationNumberExists(@NonNull Employee employee){
        boolean test;
        String query ="SELECT matricule FROM employe WHERE matricule=?";
        String [] selectArg={Integer.valueOf(employee.getRegistrationNumber()).toString()
        };
        Cursor cursor = Database.rawQuery(query,selectArg);
 test=cursor.moveToFirst();

        cursor.close();

        return test;
    }

    public boolean usernameExists(@NonNull Employee employee){

        boolean test;
        String query ="SELECT username FROM employe WHERE username =?";
        String [] selectArg={employee.getUsername()};
        Cursor cursor = Database.rawQuery(query,selectArg);
test=cursor.moveToFirst();

        cursor.close();
        return test;
    }
    public boolean emailExists(@NonNull Employee employee){

        boolean test;
        String query ="SELECT couriel FROM employe WHERE couriel =?";
        String [] selectArg={employee.getMailAddress()};
        Cursor cursor = Database.rawQuery(query,selectArg);
        test=cursor.moveToFirst();
        cursor.close();
        return test;
    }


    public void setInformations(Employee employee){
        String query;
        String [] selectArgs;
        Cursor cursor;


            query = "SELECT nom,prenom,sexe,photo,type,couriel," +
                    "username,birthdate FROM employe WHERE matricule=?";
            selectArgs= new String[]{
                    Integer.valueOf(employee.getRegistrationNumber()).toString()
            };

         cursor =Database.rawQuery(query,selectArgs);
       if( cursor.moveToFirst()) {
           //employee.setRegistrationNumber();
           employee.setLastname(cursor.getString(0));
           employee.setFirstname(cursor.getString(1));
           employee.setGender(cursor.getString(2).charAt(0));
           employee.setPicture(cursor.getBlob(3));
           employee.setType(cursor.getString(4));
           employee.setMailAddress(cursor.getString(5));
           employee.setUsername(cursor.getString(6));
           employee.setBirthdate(cursor.getString(7));
          // employee.setRegistrationNumber(8);

       }
       cursor.close();


    }
    public void setInformationsWithoutPiture(Employee employee){

        String query;
        String [] selectArgs;
        Cursor cursor;


        query = "SELECT nom,prenom,sexe,type,couriel," +
                "username,birthdate FROM employe WHERE matricule=?";
        selectArgs= new String[]{
                Integer.valueOf(employee.getRegistrationNumber()).toString()
        };

        cursor =Database.rawQuery(query,selectArgs);
        if( cursor.moveToFirst()) {
            //employee.setRegistrationNumber();
            employee.setLastname(cursor.getString(0));
            employee.setFirstname(cursor.getString(1));
            employee.setGender(cursor.getString(2).charAt(0));
            employee.setType(cursor.getString(3));
            employee.setMailAddress(cursor.getString(4));
            employee.setUsername(cursor.getString(5));
            employee.setBirthdate(cursor.getString(6));

        }
        cursor.close();

    }





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
    //return a table that contains the l
    public String[] getAllEmployees(){
        ArrayList<String> employee= new ArrayList<>();

        String query="SELECT matricule FROM employe ORDER BY matricule ";
        Cursor cursor=Database.rawQuery(query,null);
        while (cursor.moveToNext())
            employee.add(cursor.getString(0));
        cursor.close();

        return employee.toArray(new String[employee.size()]);



    }
    public Hashtable<String,Integer> getStatisticsByService(String start,String end){

        String service;
        int count;

        Hashtable<String,Integer> row = new Hashtable<>();
        String query="SELECT service.nom, COUNT(*) FROM" +
                " (SELECT * FROM employe " +
                "JOIN pointage AS relation ON matricule = matricule_ref " +
                " JOIN jour AS second  ON relation.id_jour_ref= id_jour " +
                "JOIN service ON employe.id_service_ref=id_service " +
                "WHERE heure_entree IS NOT NULL AND date_jour BETWEEN ? AND ? "+
                "GROUP BY service.nom) AS final " +
                "JOIN service ON final.id_service_ref=service.id_service ";
        String [] selectArgs={start,end};
        Cursor cursor=Database.rawQuery(query,selectArgs);

            while (cursor.moveToNext()) {
                service = cursor.getString(0);
                count = cursor.getInt(1);
                row.put(service, count);
            }
        cursor.close();
        return  row;
    }
   // presence report in a month for an employee (satursday and sunday aren't concerned)
public Character[] getPresenceReportForEmployee(
        Employee employee,int month) throws ParseException {
    Day day,d;
    Character[] table ;
    int i;
    day=new Day();
    table=new Character[day.getLenthOfMonth()];

    for (i=0;i<table.length;i++)

    {
      d=new Day(day.getYear(),month,i+1) ;

      table[i]= compute(employee,d);

    }


return  table;
}

    public int  getDayNumberInWeek(String date){
        String str;
        String query="SELECT STRF('%w',?)";
        Cursor cursor = Database.rawQuery(query,
                new String[]{date}
        );
        cursor.moveToFirst();
        str=cursor.getString(0);
        cursor.close();
        return Integer.parseInt(str);

    }

    public boolean isNotSuperUser(Employee employee){
        boolean test;
        String query="SELECT type FROM employe WHERE matricule=?";
        String [] selectArgs= new String[]{String.valueOf(employee.getRegistrationNumber())};
       Cursor cursor=Database.rawQuery(query,selectArgs);
       test=cursor.moveToFirst() && !Objects.equals(cursor.getString(0), "Simple");
       cursor.close();
       return !test;


    }

    public char compute(Employee  employee, Day day) throws ParseException {
        char state;
        String normalStartTime;
        String entryTime = null;
        String query="SELECT heure_entree FROM(SELECT * FROM pointage AS Relation JOIN jour" +

                " ON Relation.id_jour_ref=id_jour  WHERE matricule_ref=?" +
                " AND id_jour=?)";
        String [] selectArgs={String.valueOf(employee.getRegistrationNumber()),
        String.valueOf(day.getId())};
        Cursor cursor =Database.rawQuery(query,selectArgs);
        if(day.getId()==0)
            entryTime="";
        else
        entryTime=cursor.getString(0);
        cursor.close();


        if(day.isWeekEnd())
            state='W';
       else  if(entryTime==null)
            state='A';
       else if(entryTime.equals(""))
           state='F';
       else {
           normalStartTime=getPlanning(employee).getStartTime();
           if(normalStartTime.split("-")[0].compareTo(entryTime.split("-")[0])<0)
               state='R';
           else
               state='P';

        }
        return state;


    }


}

