package uac.imsp.clockingapp.Controller.util;

public class Result {

    private int Number;
    private String Name;
    private  String Service;
    private byte[] Picture;
    private String Status;
    public Result(int number,String lastName,String firstname,String service,String status,
                 byte[] picture){
        Number=number;
        Name = firstname+"  "+lastName;
        Service=service;
        Status=status;
        Picture=picture;
    }
    public Result(int number,String name,String service){
        Number=number;
        Name = name;
        Service=service;
    }



    //public Result(ArrayList)


    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public String getStatus() {
        return Status;

    }

    public void setStatus(String status) {
        Status = status;

    }

    public byte[] getPicture() {
        return Picture;
    }

    public void setPicture(byte[] picture) {
        Picture = picture;
    }
}


