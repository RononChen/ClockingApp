package uac.imsp.clockingapp.Controller.util;

import java.util.List;

public interface ISearchEmployeeController {
    //Take the data with which  employee will be searched
    List <Result> onSearch(String data);

}
