package com.project.csc301.shoppinglist.Models;

public class Separator implements ListItem {
    int title;
    String textitle;
    int details;
    ListItemType type;

    public Separator(int title, int details, ListItemType type) {
        this.title = title;
        this.details = details;
        this.type = type;
    }

    public void setActualTitle(String title){
        this.textitle = title;
    }
    public String getTextitle(){
        return this.textitle;
    }
    public int getTitle() {
        return this.title;
    }

    public int getDetails() {
        return this.details;
    }

    @Override
    public ListItemType getViewType() {
        return this.type;
    }
}
