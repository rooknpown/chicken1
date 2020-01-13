package com.example.chicken;

import java.io.Serializable;

public class Second_order_info implements Serializable {
    private String location;
    private String menu;
    private String pay;

    public Second_order_info(String location, String menu, String pay){
        super();
        this.location = location;
        this.menu = menu;
        this.pay = pay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
