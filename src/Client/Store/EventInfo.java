package Client.Store;

public class EventInfo {
    private String evnetName=null;
    private String start_date=null;
    private String end_date=null;
    private String contents=null;

    public EventInfo(String evnetName, String start_date, String end_date, String contents) {
        this.evnetName = evnetName;
        this.start_date = start_date;
        this.end_date = end_date;
        this.contents = contents;
    }

    public String getEvnetName() {
        return evnetName;
    }

    public void setEvnetName(String evnetName) {
        this.evnetName = evnetName;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
