package aln.ktversion.jsongsonservertest;

public class Menu {
    private String name;
    private String kind;
    private double price;
    private double workLifeTime;

    public Menu(String name, String kind, double price, double workLifeTime) {
        this.name = name;
        this.kind = kind;
        this.price = price;
        this.workLifeTime = workLifeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWorkLifeTime() {
        return workLifeTime;
    }

    public void setWorkLifeTime(double workLifeTime) {
        this.workLifeTime = workLifeTime;
    }
}
