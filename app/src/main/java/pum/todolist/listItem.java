package pum.todolist;


import java.util.Date;

public class listItem
{
    public String priorytet;
    public String header;
    public String desc;
    public boolean isChecked;
    public Date data_deadline;
    public Date data_create;
    public boolean isExpanded;

    public listItem()
    {

        this.priorytet = "A";
        this.header = "#Header";
        this.desc = "#Example desc";
        this.isChecked = false;
        this.data_deadline = new Date();
        this.data_create = new Date();
        this.isExpanded = false;
    }

    public listItem(String priorytet, String header, String desc, boolean isChecked, Date data, boolean isExpanded)
    {

        this.priorytet = priorytet;
        this.header = header;
        this.desc = desc;
        this.isChecked = isChecked;
        this.data_deadline = data;
        this.data_create = data;
        this.isExpanded = isExpanded;
    }
}
