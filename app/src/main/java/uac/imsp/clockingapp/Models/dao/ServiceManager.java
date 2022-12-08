package uac.imsp.clockingapp.Models.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import uac.imsp.clockingapp.Models.dbAdapter.ServiceSQLite;
import uac.imsp.clockingapp.Models.entity.Service;

public class ServiceManager {


    public static SQLiteDatabase Database;


    private final ServiceSQLite serviceSQLite;

    public ServiceManager(Context context) {
        serviceSQLite = new ServiceSQLite(context);
    }
    public SQLiteDatabase  open(){

        if (Database==null)
            Database=serviceSQLite.getWritableDatabase();
        else if (!Database.isOpen())
        {
            Database=null;
            Database = serviceSQLite.getWritableDatabase();
        }
        return Database;

    }
    public void close(){
        if (Database!=null && Database.isOpen())
            Database.close();
    }



    public void create (Service service){

        SQLiteStatement statement ;

        String query = "INSERT INTO service (nom) VALUES(?) ";





        if(!searchService(service)) {// if not exists
            statement=Database.compileStatement(query);
            statement.bindString(1,service.getName());

            statement.executeInsert();
        }
        searchService(service); //to set Id

    }

        public static void delete(Service service){
        String query = "DELETE FROM service WHERE id_service=?";
        SQLiteStatement statement ;

        statement = Database.compileStatement(query);
        statement.bindLong(1,service.getId());
        statement.executeUpdateDelete();

    }
    public void update(Service service,String newName){
        String query = "UPDATE service SET nom=? WHERE id_service=?";
        SQLiteStatement statement;

        statement = Database.compileStatement(query);
        statement.bindString(1, newName);
        statement.bindLong(2,service.getId());
        statement.executeUpdateDelete();

    }
    public String[] getAllServices(){
        ArrayList <String> service= new ArrayList<>();
        String query="SELECT nom FROM service";
        Cursor cursor=Database.rawQuery(query,null);

        while (cursor.moveToNext())

            service.add(cursor.getString(0));
        cursor.close();
        return service.toArray(new String[service.size()]);


    }




    public Service[] getAllService(){
        ArrayList <Service> serviceSet= new ArrayList<>();
        Service service;

        String query="SELECT * FROM service";
        Cursor cursor=Database.rawQuery(query,null);
        //cursor.moveToFirst();
        while (cursor.moveToNext())

        {
            service = new Service(cursor.getInt(0));
            service.setName(cursor.getString(1));
            serviceSet.add(service);
        }
        cursor.close();
        return serviceSet.toArray(new Service[serviceSet.size()]);

    }
    public boolean searchService(Service service){
        boolean test;
        String query="SELECT id_service FROM service WHERE nom=? ";

        String [] selectArg={
                service.getName()
        };
        Cursor cursor=Database.rawQuery(query,selectArg);
        cursor.moveToFirst();
        test=cursor.getCount()==1;


        if(test) {
            service.setId(Integer.parseInt(cursor.getString(0)));
            cursor.close();
            return true;

        }
        cursor.close();
        return false;

    }






}
