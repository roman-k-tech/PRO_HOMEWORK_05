package TASK_2;

public class Flat
{
    @Id
    private int id_flat;

    private String district;
    private String address;
    private int square;
    private int rooms;
    private int price;

    public Flat() {}

    public Flat(int id_flat) {
        this.id_flat = id_flat;
    }

    public Flat(String district, String address, int square, int rooms, int price) {
        this.district = district;
        this.address = address;
        this.square = square;
        this.rooms = rooms;
        this.price = price;
    }

    @Override
    public String toString(){
        return "Flat " + id_flat + ": " + district + ", " + address + ", " + square + ", " + rooms + ", " + price;
    }
}
