package mephi.lab2;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class Reactor {
    private String type;
    private double burnup;
    private double kpd;
    private double enrichment;
    private double thermal_capacity;
    private double electrical_capacity;
    private int life_time;
    private double first_load;

    public Reactor() {
    }
    public Reactor(String type, double burnup, double kpd, double enrichment, double thermal_capacity, double electrical_capacity, int life_time, double first_load) {
        this.type = type;
        this.burnup = burnup;
        this.kpd = kpd;
        this.enrichment = enrichment;
        this.thermal_capacity = thermal_capacity;
        this.electrical_capacity = electrical_capacity;
        this.life_time = life_time;
        this.first_load = first_load;
    }
    public String getType() {
        return type;
    }
    public double getBurnup() {
        return burnup;
    }
    public double getKpd() {
        return kpd;
    }
    public double getEnrichment() {
        return enrichment;
    }
    public double getThermal_capacity() {
        return thermal_capacity;
    }
    public double getElectrical_capacity() {
        return electrical_capacity;
    }
    public int getLife_time() {
        return life_time;
    }
    public double getFirst_load() {
        return first_load;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setBurnup(double burnup) {
        this.burnup = burnup;
    }
    public void setKpd(double kpd) {
        this.kpd = kpd;
    }
    public void setEnrichment(double enrichment) {
        this.enrichment = enrichment;
    }
    public void setThermal_capacity(double thermal_capacity) {
        this.thermal_capacity = thermal_capacity;
    }
    public void setElectrical_capacity(double electrical_capacity) {
        this.electrical_capacity = electrical_capacity;
    }
    public void setLife_time(int life_time) {
        this.life_time = life_time;
    }
    public void setFirst_load(double first_load) {
        this.first_load = first_load;
    }

    @Override
    public String toString() {
        return "Reactor{" + "type=" + type + ", burnup=" + burnup + ", kpd=" + kpd + ", enrichment=" + enrichment + ", thermal_capacity=" + thermal_capacity + ", electrical_capacity=" + electrical_capacity + ", life_time=" + life_time + ", first_load=" + first_load + '}';
    }
    public MutableTreeNode getNode() {
        DefaultMutableTreeNode rNode = new DefaultMutableTreeNode(this.type);
        rNode.add(new DefaultMutableTreeNode("burnup: ".concat(Double.toString(this.burnup))));
        rNode.add(new DefaultMutableTreeNode("kpd: ".concat(Double.toString(this.kpd))));
        rNode.add(new DefaultMutableTreeNode("enrichment: ".concat(Double.toString(this.enrichment))));
        rNode.add(new DefaultMutableTreeNode("thermal_capacity: ".concat(Double.toString(this.thermal_capacity))));
        rNode.add(new DefaultMutableTreeNode("electrical_capacity: ".concat(Double.toString(this.electrical_capacity))));
        rNode.add(new DefaultMutableTreeNode("life_time: ".concat(Integer.toString(this.life_time))));
        rNode.add(new DefaultMutableTreeNode("first_load: ".concat(Double.toString(this.first_load))));
        return rNode;
    }
}