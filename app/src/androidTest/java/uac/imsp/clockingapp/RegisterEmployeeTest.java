package uac.imsp.clockingapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import uac.imsp.clockingapp.Controller.control.RegisterEmployeeController;
import uac.imsp.clockingapp.View.util.IRegisterEmployeeView;

@RunWith(AndroidJUnit4.class)
public class RegisterEmployeeTest  implements IRegisterEmployeeView {
    private final Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private  RegisterEmployeeController registerEmployeePresenter;
    public RegisterEmployeeTest(){
        registerEmployeePresenter=new RegisterEmployeeController(this,appContext);
        assertNotNull(registerEmployeePresenter);
    }
   @Test
    public void testRegisterEmployee(){
        String [] services;
        new RegisterEmployeeTest();
        assertNotNull(registerEmployeePresenter);
        services= registerEmployeePresenter.onLoad();
        assertNotNull(registerEmployeePresenter.getServiceManager());
        assertNotNull(services);
        assertNotEquals(0,services.length);
        /* registerEmployeePresenter.onRegisterEmployee("","",
                "","M","","","",
                "","","",0,
                0,null,"");*/
       /*registerEmployeePresenter.onRegisterEmployee("10","",
               "","M","","","",
               "","","",0,
               0,null,"");*/
      /* registerEmployeePresenter.onRegisterEmployee("10","q",
               "","M","","","",
               "","","",0,
               0,null,"");*/
       /*registerEmployeePresenter.onRegisterEmployee("10","No",
               "","M","","","",
               "","","",0,
               0,null,"");*/
       /*registerEmployeePresenter.onRegisterEmployee("10","No",
               "1","M","","","",
               "","","",0,
               0,null,"");*/
       /*registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22","","",
               "","","",0,
               0,null,"");*/
       /*registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22","mhgjhii","",
               "","","",0,
               0,null,"");*/
      /* registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","",
               "","","",0,
               0,null,"");
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","km",
               "","","",0,
               0,null,"");
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","User100",
               "","","",0,
               0,null,"");
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","User100",
               "pass","","",0,
               0,null,"");
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","User100",
               "password","","",0,
               0,null,"");*/
       registerEmployeePresenter.onRegisterEmployee("10","No",
               "Jean","M","2022-08-22",
               "adedeezechiel@gmail.com","User100",
               "password","password","Aucun",8,
               18,null,"");




    }
    @Override
    public void onRegisterEmployeeSuccess(String message) {


    }
    @Override
    public void onRegisterEmployeeError(String message) {
        // assertEquals("Matricule requis !",message);
       // assertEquals("Nom requis !",message);
        //assertEquals("Nom invalide !",message);
        //assertEquals("Prénom requis !",message);
       // assertEquals("Prénom(s) invalide(s) !",message);
        //assertEquals("Email requis !",message);
        //assertEquals("Username requis !",message);
        //assertEquals("Username invalide !",message);
        //assertEquals("Mot de passe requis !",message);
         //assertEquals("Mot de passe invalide !",message);
         assertEquals("Vérifier le mot de passe et resssayer !",message);


    }


}

