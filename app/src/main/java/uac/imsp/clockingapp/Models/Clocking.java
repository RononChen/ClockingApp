package uac.imsp.clockingapp.Models;

public class Clocking  implements IClocking{

    private int Id;
    private String   EntryTime;
        private String ExitTime;
    // POur un pointage non existant
    public Clocking() {

    }

    // Pour un pointage déjà
    public Clocking(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getEntryTime() {
        return EntryTime;
    }

    public String getExitTime() {
        return ExitTime;
    }

    public void setEntryTime(String entryDateTime) {
        EntryTime = entryDateTime;
    }


    public void setExitTime(String exitTime) {
        ExitTime = exitTime;

    }

    public String getDate() {
        return null;
    }
}