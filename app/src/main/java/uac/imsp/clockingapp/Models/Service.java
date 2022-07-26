package uac.imsp.clockingapp.Models;

public class Service implements IService{

    //attributs
    private int Id;
    private String Name;



    //constructeurs

    //un service non existant
    public Service(String name){
        Name = name;
    }
    //un service existant
    public Service(int id){
        Id=id;
    }
    ///getters
    public int getId(){
        return Id;
    }
    public String getName(){
        return Name;
    }

    //setters
    public void setId(int id){
        Id = id;
    }
    public void setName(String name){
        Name = name;
    }


}
