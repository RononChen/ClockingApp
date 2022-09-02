package uac.imsp.clockingapp.View.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import uac.imsp.clockingapp.R;
import uac.imsp.clockingapp.View.util.ToastMessage;
public class FileHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_handler);
        /*FileOutputStream textFile = null;
        try {
            textFile = openFileOutput ("file.txt", Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        checkFileExists(new File("file.txt"));*/
        boolean b;
        File directory=new File(getFilesDir(),"ClockingApp");
        if(!directory.exists())
        {
            new ToastMessage(this,"Le répertoire n'existe pas\n" +
                    "Création :");
            b=directory.mkdir();
            if(b) {
                new ToastMessage(this, "Répertoire créé avec succès");
            new  ToastMessage(this,"Chemin:"+directory.getPath());
            }
             else
                new ToastMessage(this,"Echec de création");


        }
        else
        {
            new ToastMessage(this,"Le répertoire existe");
            new  ToastMessage(this,"Chemin:"+directory.getPath());
        }




        
    }
    public void checkFileExists(File file)
    {
boolean test;
        if(file.exists())
            new ToastMessage(this,"Le fichier existe") ;
        else

            new ToastMessage(this,"Le fichier n'existe pas\n" +
                    "Création :");
        //try {
            //test=file.createNewFile();
           // if(test)

               // new ToastMessage(this,"Fichier créé avec succès");
            //else
               // new ToastMessage(this,"Echec de création");

        //} catch (IOException e) {
           // e.printStackTrace();
        //}
    }

        
}
