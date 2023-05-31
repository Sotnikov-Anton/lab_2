package mephi.lab2;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLTable {
    private String tableName;
    private String[] collumnNames;
    private HashMap<String, ArrayList<Object>> data;

    public SQLTable(String tableName, String[] collumnNames, HashMap<String, ArrayList<Object>> data){
        this.tableName = tableName;
        this.collumnNames =collumnNames;
        this.data = data;
    }
    public String getTableName() {
        return tableName;
    }
    public String[] getCollumnNames() {
        return collumnNames;
    }
    public HashMap<String, ArrayList<Object>> getData() {
        return data;
    }
    public ArrayList<Object> getListByColumnNumber(int i){
        return data.get(collumnNames[i]);
    }
    public Object getFirstCellOfColumn(String column){
        return data.get(column).get(0);
    }
}