package repository;

import model.ClothingItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingItemRepository {
  private final String url;
  private final String user;
  private final String password;

  public ClothingItemRepository(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  public void createTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS clothing_items (" +
        "  id INT AUTO_INCREMENT PRIMARY KEY," +
        "  name VARCHAR(67) NOT NULL," +
        "  size VARCHAR(67) NOT NULL," +
        "  price DOUBLE NOT NULL," +
        "  category_id INT NOT NULL," +
        "  CONSTRAINT fk_clothing_category FOREIGN KEY (category_id) " +
        "      REFERENCES categories(id) ON DELETE CASCADE" +
        ");";

    try (Connection con = getConnection();
        Statement stmt = con.createStatement()) {
      stmt.executeUpdate(sql);
    }
  }

  public ClothingItem create(ClothingItem item) throws SQLException {
    String sql = "INSERT INTO clothing_items (name, size, price, category_id) VALUES (?, ?, ?, ?);";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, item.getName());
      ps.setString(2, item.getSize());
      ps.setDouble(3, item.getPrice());
      ps.setInt(4, item.getCategoryId());

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          item.setId(rs.getInt(1));
        }
      }
    }
    return item;
  }

  public List<ClothingItem> findAll() throws SQLException {
    List<ClothingItem> list = new ArrayList<>();
    String sql = "SELECT id, name, size, price, category_id FROM clothing_items;";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String size = rs.getString("size");
        double price = rs.getDouble("price");
        int categoryId = rs.getInt("category_id");

        list.add(new ClothingItem(id, name, size, price, categoryId));
      }
    }
    return list;
  }

  public boolean update(ClothingItem item) throws SQLException {
    String sql = "UPDATE clothing_items SET name = ?, size = ?, price = ?, category_id = ? WHERE id = ?;";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setString(1, item.getName());
      ps.setString(2, item.getSize());
      ps.setDouble(3, item.getPrice());
      ps.setInt(4, item.getCategoryId());
      ps.setInt(5, item.getId());

      return ps.executeUpdate() > 0;
    }
  }

  public boolean deleteById(int id) throws SQLException {
    String sql = "DELETE FROM clothing_items WHERE id = ?;";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, id);

      return ps.executeUpdate() > 0;
    }
  }
}