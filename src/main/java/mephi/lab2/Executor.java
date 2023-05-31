package mephi.lab2;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Executor {
    String dbName  = "lab_3";
    String user = "antony";
    String password = "HajOp0LyqBJxwsrmgN9DqpFwkBraq72e";
    Connection conn;

    {
        try {
            conn = getConn();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConn() throws SQLException {
        DBConnector db = new DBConnector();
        System.out.println("Successful connection with " + this.user + "'s database");
        return db.connection(this.dbName, this.user, this.password);
    }

    public HashMap<String, ArrayList<Object>> getDataFromDB(String sql, String file) throws SQLException, IOException, XMLStreamException {
        String[] column_names = new String[]{"region_name", "country_name", "companies_name", "class", "thermal_capacity", "enrichment", "load_factor", "commercial_operation"};
        HashMap<String, ArrayList<Object>> active_reactors = new HashMap<>();
        for (String column : column_names) {
            active_reactors.put(column, new ArrayList<>());
        }
        Statement stmt = conn.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (rs.next()) {
            active_reactors.get(column_names[0]).add(rs.getString(column_names[0]));
            active_reactors.get(column_names[1]).add(rs.getString(column_names[1]));
            active_reactors.get(column_names[2]).add(rs.getString(column_names[2]));
            active_reactors.get(column_names[3]).add(rs.getString(column_names[3]));
            active_reactors.get(column_names[4]).add(rs.getInt(column_names[4]));
            active_reactors.get(column_names[5]).add(rs.getInt(column_names[5]));
            active_reactors.get(column_names[6]).add(rs.getInt(column_names[6]));
            active_reactors.get(column_names[7]).add(rs.getDate(column_names[7]));
        }
        addLoadFactor(active_reactors.get(column_names[6]));
        create_burnup_collumn(active_reactors, file);
        return active_reactors;
    }
    
    public HashMap<String,ArrayList<Object>> collect_data(String file) throws SQLException, IOException, XMLStreamException {
        String sql ="SELECT region_name, country_name, companies_name, class,thermal_capacity , enrichment, load_factor, commercial_operation\n" +
                "FROM regions r \n" +
                "LEFT JOIN countries c ON r.id = c.region_id \n" +
                "LEFT JOIN sites s ON s.place = c.id \n" +
                "LEFT JOIN units u ON s.id = u.site  \n" +
                "LEFT JOIN companies c2 ON u.\"operator\" = c2.id\n" +
                "WHERE (date_shutdown > '2024-01-01' OR date_shutdown is null)  and commercial_operation <= '2023-01-01'";
        return getDataFromDB(sql, file);
    }

    public void addLoadFactor(ArrayList<Object> factors) {
        for (int i = 0; i < factors.size(); i++) {
            Object b = factors.get(i);
            if (b instanceof Integer && ((Integer) b) == 0) {
                factors.set(i, 90);
            }
        }
    }

    public HashMap<String, Double> ArrayListToHashMap(ArrayList<Object> entities) {
        HashMap<String, Double> map = new HashMap<>();
        entities.forEach(b -> {
            if (!(map.containsKey((String) b))) {
                map.put((String) b, (double) 0);
            }
        });
        return map;
    }

    public HashMap<String, Double> сompute(String column_name, String file) throws SQLException, IOException, XMLStreamException {
        HashMap<String, ArrayList<Object>> reactors = collect_data(file);
        HashMap<String, Double> entities = ArrayListToHashMap(reactors.get(column_name));
        for (int i = 0; i < reactors.get("class").size(); i++) {
            double val = (Double) reactors.get("burnup").get(i);
            entities.put((String) reactors.get(column_name).get(i), (Double)((Integer)reactors.get("thermal_capacity").get(i) / val));
        }
        return entities;
    }

    public HashMap<String, Double> reactor_types(ArrayList<Reactor> reactors) {
        HashMap<String, Double> reactor_info = new HashMap<>();
        reactors.forEach(b->{
            reactor_info.put(b.getType(), (b.getBurnup()));
        });
        return reactor_info;
    }

    //in this method we select file to read data from using chain pattern
    public void create_burnup_collumn(HashMap<String, ArrayList<Object>> active_reactors, String file) throws IOException, XMLStreamException {
        XMLReader xmlr = new XMLReader();
        JSONReader jsonr = new JSONReader();
        YAMLReader yamlr = new YAMLReader();
        NULLReader nullr = new NULLReader();
        xmlr.setNeighbour(jsonr);
        jsonr.setNeighbour(yamlr);
        yamlr.setNeighbour(nullr);
        ArrayList<Reactor> reactors_type_data = xmlr.readFile(file);
        HashMap<String, Double> reactors_burnup = reactor_types(reactors_type_data);
        create_burnup(active_reactors, reactors_burnup);
    }

    public void create_burnup(HashMap<String, ArrayList<Object>> active_reactors, HashMap<String, Double> reactor_info){
        ArrayList<Object> burnup = new ArrayList<>();
        ArrayList<Object> reactors = active_reactors.get("class");
        for (Object reactor : reactors) {
            String a = (String) reactor;
            int lastNonSpaceIndex = a.trim().lastIndexOf(""); //finding index of last non-space character
            a = a.substring(0, lastNonSpaceIndex);
            if (a.equals("PWR-900") | a.equals("PWR-1300") | a.equals("PWR-1500")) {
                a = "PWR";
            }
            if (a.equals("VVER-1000") | a.equals("VVER-1200") | a.equals("VVER-440")) {
                a = "VVER";
            }
            burnup.add(reactor_info.getOrDefault(a, 40.0));
            active_reactors.put("burnup", burnup);
        }
    }
    
    public void connection_close() throws SQLException {
        this.conn.close();
    }
}