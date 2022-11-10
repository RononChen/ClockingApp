package uac.imsp.clockingapp.Controller.control;

import uac.imsp.clockingapp.Controller.util.IStartScreenController;
import uac.imsp.clockingapp.View.util.IStartScreenView;

public class StartScreenController  implements IStartScreenController {
    private IStartScreenView startScreenView;
    public StartScreenController(IStartScreenView startScreenView){
        this.startScreenView=startScreenView;

    }


    @Override
    public void onLogin() {
        startScreenView.onLogin();


    }

    @Override
    public void onClocking() {
        startScreenView.onClocking();

    }
}
