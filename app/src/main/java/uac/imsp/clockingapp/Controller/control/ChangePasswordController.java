public class ChangePasswordController implements IChangePasswordController{

private IChangePasswordView changePasswordView;
private Employee employee;
    public ChangePasswordController(IChangePasswordView changePasswordView){

this.changePasswordView=changePasswordView;
    }
    void onStart(int number){
        employee=new Employee(number);
        EmployeeManager employeeManager;
        employeeManager=new EmployeeManager(changePasswordView);
        employeeManager.setAccount(employee);
        changePasswordView.onStart(employee.getUsername());
        void onStart(String oldPassword,String newPassword){
            if(!oldPassword.equals(employee.getPassword()))
            changePasswordView.onWrongPassword("Mot de passe incorrect");
            else if()
            changePasswordView.onWrongPassword("Mot de passe invalide");
            else {
                employeeManager.changePassword(employee,newPassword);
                changePasswordView.onSuccess("Mot de passe changé avec succès");

            }
        
        }


    }
}