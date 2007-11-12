package src.edu.gatech.cs6300;

public class Student {
    private String name;
    private String gtid;
    private int attendance;
    
    public Student() {
        name = "";
        gtid = "";
        attendance = 0;
        // TODO Auto-generated constructor stub
    }

    public String getName(){
        return this.name;
    }
    
    public String getGtid(){        
        return this.gtid;
    }
    
    public int getAttendance(){ 
        return this.attendance;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setGTID(String gtid) {
        this.gtid = gtid;
    }
    
    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    
    
}