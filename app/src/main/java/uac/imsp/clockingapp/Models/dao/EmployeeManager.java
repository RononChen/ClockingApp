package uac.imsp.clockingapp.Models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

import uac.imsp.clockingapp.Models.dbAdapter.EmployeeSQLite;
import uac.imsp.clockingapp.Models.entity.Day;
import uac.imsp.clockingapp.Models.entity.Employee;
import uac.imsp.clockingapp.Models.entity.Planning;
import uac.imsp.clockingapp.Models.entity.Service;

public class EmployeeManager {
    public final static int CAN_NOT_LOGIN = 15;
    private SQLiteDatabase Database = null;
    private final EmployeeSQLite employeeSQLite;

    //private Context context;
    public EmployeeManager(Context context) {
        employeeSQLite = new EmployeeSQLite(context);
    }

    public SQLiteDatabase open() {
        if (Database == null)
            Database = employeeSQLite.getWritableDatabase();
        return Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public int connectUser(Employee employee) {

        int nb_employee;
        String correctPassword , givenPassword = employee.getPassword();
        int regNumber;

        String query = "SELECT matricule,password FROM employe WHERE username=?";


        String[] selectArgs = {employee.getUsername()};

        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        nb_employee = cursor.getCount();
        regNumber = cursor.getInt(0);
        correctPassword = cursor.getString(1);//get  encrypted password
        cursor.close();
        if (nb_employee == 1 && correctPassword.equals(md5(givenPassword))) {
            employee.setRegistrationNumber(regNumber);
            setInformations(employee);
            return 0;
        }

        return CAN_NOT_LOGIN;

    }


    //create,delete,update

    public void create(Employee employee) {

        // open();
        SQLiteStatement statement;
        String query = "INSERT INTO employe (matricule,nom,prenom,sexe," +
                "couriel,username,password,type,date_ajout) VALUES(?,?,?,?,?,?,?,?,?) ";
        statement = Database.compileStatement(query);

        statement.bindLong(1, employee.getRegistrationNumber());

        statement.bindString(2, employee.getLastname());
        statement.bindString(3, employee.getFirstname());
        statement.bindString(4, Character.toString((char) employee.getGender()));
        //statement.bindString(5,employee.getBirthdate());
        statement.bindString(5, employee.getMailAddress());
        statement.bindString(6, employee.getUsername());
        statement.bindString(7, md5(employee.getPassword()));
        statement.bindString(8, employee.getType());
        statement.bindString(8, "DATE('NOW','LOCALTIME'");
        statement.executeInsert();
        //if the birthdate is given
        if (employee.getBirthdate()!=null) {
            query = "UPDATE employe SET birthdate=? WHERE matricule=?";
            statement = Database.compileStatement(query);
            statement.bindString(1, employee.getBirthdate());
            statement.bindLong(2, employee.getRegistrationNumber());
            statement.executeUpdateDelete();
        }

    }
    public void retrieveAddDate(Employee employee){
        String querry="SELECT date_ajout FROM employe WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor=Database.rawQuery(querry,selectArgs);
        if(cursor.moveToNext())
            employee.setAdddDate(cursor.getString(0));

        cursor.close();
    }

    public void setAccount(Employee employee) {

        String query = "SELECT username, password FROM employe WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst()) {
            employee.setUsername(cursor.getString(0));
            employee.setPassword(cursor.getString(1));
        }
        cursor.close();


    }

    public void changePassword(Employee employee, String newPassword) {

        String query = "UPDATE employe SET password=? WHERE matricule=?";

        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindString(1, md5(newPassword));

        statement.bindLong(2, employee.getRegistrationNumber());

        statement.executeUpdateDelete();


    }
//On peut modifier le courier ou la photo de l'employé


    //update the email of employee
    public void update(Employee employee, String mailAddress) {

        String query = "UPDATE employe SET couriel =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1, mailAddress);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }


    public void changeGrade(Employee employee, String type) {

        String query = "UPDATE employe SET type =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1, type);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

    //To update the picture of the employee
    public void update(Employee employee, byte[] picture) {
        String query = "UPDATE employe SET photo =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindBlob(1, picture);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

    public Planning getPlanning(Employee employee) {
        Planning planning = null;
        String query = "SELECT heure_debut_officielle,heure_fin_officielle," +
                "jours_de_travail FROM planning  JOIN employe ON " +
                "id_planning=id_planning_ref WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst())


            planning = new Planning(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getBlob(2)
            );
        cursor.close();
        return planning;
    }

    public Service getService(Employee employee) {
        Service service;
        String query = "SELECT service.nom  " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        service = new Service(cursor.getString(0));

        cursor.close();
        return service;
    }

    public void delete(Employee employee) {
        String query = "DELETE FROM employe WHERE matricule=?";
        SQLiteStatement statement;

        statement = Database.compileStatement(query);
        statement.bindLong(1, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
//Pour rechercher un employé
/*On peut rechercher par matricule,nom,prénom,ou service
//Cette méthode prend la donnée à rechercher et retourne
un tableau contenant les emplyés vérifiant le motif de recherche*/

    public Employee[] search(String data) {
        String query, queryAll;

        queryAll = "SELECT matricule, employe.nom,prenom,photo " +
                "FROM employe JOIN service ON id_service=id_service_ref ";

        query = "SELECT matricule, employe.nom,prenom,photo " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE  matricule||employe.nom||prenom||service.nom " +
                "LIKE '%'||?||'%'";

        String[] selectArg = {data};
        ArrayList<Employee> employeeSet = new ArrayList<>();
        Employee employee;
        Cursor cursor;
        if (Objects.equals(data, "*"))
            cursor = Database.rawQuery(queryAll, null);
        else
            cursor = Database.rawQuery(query, selectArg);

        while (cursor.moveToNext()) {
            employee = new Employee(cursor.getInt(0));
            employee.setLastname(cursor.getString(1));
            employee.setFirstname(cursor.getString(2));
            employee.setPicture(cursor.getBlob(3));
            setStatus(employee);
            employeeSet.add(employee);
        }
        cursor.close();
        return employeeSet.toArray(new Employee[employeeSet.size()]);

    }


    public boolean exists(@NonNull Employee employee) {

        return registrationNumberExists(employee);


    }

    public boolean registrationNumberExists(@NonNull Employee employee) {
        boolean test;
        String query = "SELECT matricule FROM employe WHERE matricule=?";
        String[] selectArg = {Integer.valueOf(employee.getRegistrationNumber()).toString()
        };
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();

        cursor.close();

        return test;
    }

    public boolean usernameExists(@NonNull Employee employee) {

        boolean test;
        String query = "SELECT username FROM employe WHERE username =?";
        String[] selectArg = {employee.getUsername()};
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();

        cursor.close();
        return test;
    }

    public boolean emailExists(@NonNull Employee employee) {

        boolean test;
        String query = "SELECT couriel FROM employe WHERE couriel =?";
        String[] selectArg = {employee.getMailAddress()};
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();
        cursor.close();
        return test;
    }


    public void setInformations(Employee employee) {
        String query;
        String[] selectArgs;
        Cursor cursor;


        query = "SELECT nom,prenom,sexe,photo,type,couriel,username,birthdate,jours_de_travail" +
                " FROM employe JOIN planning ON id_planning=id_planning_ref WHERE matricule=?";
        selectArgs = new String[]{
                Integer.valueOf(employee.getRegistrationNumber()).toString()
        };

        cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst()) {
            employee.setLastname(cursor.getString(0));
            employee.setFirstname(cursor.getString(1));
            employee.setGender(cursor.getString(2).charAt(0));
            employee.setPicture(cursor.getBlob(3));
            employee.setType(cursor.getString(4));
            employee.setMailAddress(cursor.getString(5));
            employee.setUsername(cursor.getString(6));
            employee.setBirthdate(cursor.getString(7));
            employee.setWorkdays(cursor.getBlob(8));

        }
        cursor.close();


    }


    public void setInformationsWithoutPiture(Employee employee) {

        String query;
        String[] selectArgs;
        Cursor cursor;


        query = "SELECT nom,prenom,sexe,type,couriel," +
                "username,birthdate FROM employe WHERE matricule=?";
        selectArgs = new String[]{
                Integer.valueOf(employee.getRegistrationNumber()).toString()
        };

        cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst()) {
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


    public void update(@NonNull Employee employee, @NonNull Service service) {
        String query = "UPDATE employe SET id_service_ref=? WHERE matricule=?";
        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindLong(1, service.getId());
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }

    public void update(@NonNull Employee employee, Planning planning) {
        String query = "UPDATE employe SET id_planning_ref=? WHERE matricule=?";
        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindLong(1, planning.getId());
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    public Hashtable<String, Integer> getStatisticsByService(int month,int year) {
Day day=new Day(year,month,1);
Day d;
int length=day.getLenthOfMonth();
d=new Day(year,month,length-1);
        String service;
        int count;
        String start,end;
        start=day.getDate();
        end=d.getDate();


        Hashtable<String, Integer> row = new Hashtable<>();
        String query = "SELECT id_service_ref, COUNT(*) FROM" +
                "( SELECT id_service_ref FROM employe JOIN   service AS Relation   ON id_service=id_service_ref " +
                " JOIN pointage AS Final  ON matricule =Final.matricule_ref " +
                "WHERE Final.statut=? AND date_jour BETWEEN ? AND ?) GROUP BY id_service_ref " ;
        String[] selectArgs = {start, end};
        Cursor cursor = Database.rawQuery(query, selectArgs);

        while (cursor.moveToNext()) {
            service = cursor.getString(0);
            count = cursor.getInt(1);
            row.put(service, count);
        }
        cursor.close();
        return row;
    }

    // presence report in a month for an employee (satursday and sunday aren't concerned)
    public String[] getPresenceReportForEmployee(
            Employee employee, int month, int year)  {
        //int nb;
        Hashtable<String,String> status=new Hashtable<>();

        Day day, d;
        String date,state;
        String[] table;
        Cursor cursor;
        String []selectArgs;
        int i;
        day = new Day(month,year);
        d=day;
        table = new String[day.getLenthOfMonth()];

        String query="SELECT date_jour,statut FROM pointage WHERE matricule_ref=? " +
                "AND date_jour BETWEEN ? AND ?  ";
        day=new Day(day.getYear(),month,1);
        d=new Day(d.getYear(),month,d.getLenthOfMonth());
        selectArgs=new String[]
                {
                String.valueOf(employee.getRegistrationNumber()),
                        day.getDate(),
                        d.getDate()
        };
         cursor=Database.rawQuery(query,selectArgs );
        while ((cursor.moveToNext()))
        {
            date=cursor.getString(0);
            state=cursor.getString(1);
            status.put(date,state);
        }
   cursor.close();
      day=new Day(day.getYear(),day.getMonth(),1);
        for (i = 0; i < table.length; i++) {
            //to browse the calendar especially the concerned month
            day = new Day(day.getYear(), month, i + 1);
            table[i] = status.getOrDefault(day.getDate(), "");

        }



        return table;
    }

    public int getDayNumberInWeek(String date) {
        String str;
        String query = "SELECT STRF('%w',?)";
        Cursor cursor = Database.rawQuery(query,
                new String[]{date}
        );
        cursor.moveToFirst();
        str = cursor.getString(0);
        cursor.close();
        return Integer.parseInt(str);

    }

    public boolean isNotSuperUser(Employee employee) {
        boolean test;
        String query = "SELECT type FROM employe WHERE matricule=?";
        String[] selectArgs = new String[]{String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        test = cursor.moveToFirst() && !Objects.equals(cursor.getString(0), "Simple");
        cursor.close();
        return !test;


    }
/*** This function take an employee and a day as paramater and
  returns the matching presence state***/
    public char compute(Employee employee, Day day)  {
        char state;
        String normalStartTime;
        String entryTime;
        String query = "SELECT heure_entree FROM(SELECT * FROM pointage AS Relation" +
                " JOIN jour ON Relation.id_jour_ref=id_jour  WHERE matricule_ref=?" +
                " AND id_jour=?)";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber()),
                String.valueOf(day.getId())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        if (day.getId() == 0)

            entryTime = "";
        else
            entryTime = cursor.getString(0);
        cursor.close();


        if (day.isWeekEnd())
            state = 'W';
        else if (entryTime == null)
            state = 'A';
        else if (entryTime.equals(""))
            state = 'F';
        else {
            normalStartTime = getPlanning(employee).getStartTime();
            if (normalStartTime.split("-")[0].compareTo(entryTime.split("-")[0]) < 0)
                state = 'R';
            else
                state = 'P';

        }
        return state;


    }

    public String md5(String password) {
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



    public boolean shouldNotWorkToday(Employee employee) {
        Cursor cursor;
        byte[] workDays;
        //byte
        Day day=new Day();
        int dayOfWeek=day.getDayOfWeek();
        String query = "SELECT jours_de_travail FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        workDays=cursor.getBlob(0);
        cursor.close();
        return workDays[dayOfWeek - 1] != 'T';
    }
    public void updateCurrentAttendance(Employee employee, String status){
        SQLiteStatement statement;
        employee.setCurrentStatus(status);
        String query="UPDATE employe SET statut=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    public void setDayAttendance(Employee employee,String status){
        SQLiteStatement statement;
        String query;
        Day day=new Day();
        employee.setCurrentStatus(status);
        updateCurrentAttendance(employee,status);
         query="INSERT INTO pointage (matricule_ref,date_jour,statut)" +
                " VALUES(?,?,?)";
        statement=Database.compileStatement(query);
        statement.bindLong(1,employee.getRegistrationNumber());
        statement.bindString(2,day.getDate());
        statement.bindString(3,status);
        statement.executeInsert();

    }
public void updateVariable(){

        Day day=new Day();
String query="UPDATE variable SET last_update=?";
SQLiteStatement statement=Database.compileStatement(query);
statement.bindString(1,day.getDate());
statement.executeUpdateDelete();


}
public String selectVariable(){
        String date="";
    String query="SELECT last_update FROM variable";
    Cursor cursor=Database.rawQuery(query,null);
    if(cursor.moveToFirst())
        date=cursor.getString(0);

    cursor.close();
        return date;
}
    public void setStatus(Employee employee){
        Cursor cursor;
        String status;
        String query = "SELECT statut FROM employe WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        status=cursor.getString(0);
        employee.setCurrentStatus(status);
        cursor.close();


    }

}