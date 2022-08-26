package uac.imsp.clockingapp.Models.entity;

public interface IEmployee {
     int getRegistrationNumber() ;

     String getFirstname();

     String getLastname() ;

     int getGender() ;

     String getBirthdate() ;String getMailAddress() ;
      byte[] getPicture() ;

     byte[] getQRCode();
     String getUsername();
     String getPassword();

     int isValid();

    void setPassword(String string);
}
