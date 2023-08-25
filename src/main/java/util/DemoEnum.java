package util;

public enum DemoEnum {
    Otp_Send("Otp: Send Otp Message"),
    Project_Created("Project Created: Project Created Message"),
    Project_Deleted("Project Deleted: Project Deleted Message"),
    Task_Created("Task Created: Task Created Message"),
    Task_Deleted("Task Deleted: Task Deleted Message");

     private String label;
    DemoEnum(String label)
    {
        this.label=label;
    }

    public String getLabel()
    {
        return label;
    }

}
