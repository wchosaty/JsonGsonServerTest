package aln.ktversion.jsongsonservertest;

public class Menu {
    private String name;
    private String kind;
    private double price;
    private double workTime;

    public Menu(String name, String kind, double price, double workTime) {
        this.name = name;
        this.kind = kind;
        this.price = price;
        this.workTime = workTime;
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

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }
}
