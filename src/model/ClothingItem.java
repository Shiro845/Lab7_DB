package model;

public class ClothingItem {
  private int id;
  private String name;
  private String size;
  private double price;
  private int categoryId;

  public ClothingItem(String name, String size, double price, int categoryId) {
    this.name = name;
    this.size = size;
    this.price = price;
    this.categoryId = categoryId;
  }

  public ClothingItem(int id, String name, String size, double price, int categoryId) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.price = price;
    this.categoryId = categoryId;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getSize() { return size; }
  public void setSize(String size) { this.size = size; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  public int getCategoryId() { return categoryId; }
  public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

  @Override
  public String toString() {
    return "ClothingItem{id=" + id + ", name='" + name + "', size='" + size + "', price=" + price + ", categoryId=" + categoryId + "}";
  }
}