package repository;

import model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
  private final String url;
  private final String user;
  private final String password;

  public CategoryRepository(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  public void createTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS categories (" +
        "  id INT AUTO_INCREMENT PRIMARY KEY," +
        "  name VARCHAR(100) NOT NULL" +
        ");";

    try (Connection con = getConnection();
        Statement stmt = con.createStatement()) {
      stmt.executeUpdate(sql);
    }
  }

  public Category create(Category category) throws SQLException {
    String sql = "INSERT INTO categories (name) VALUES (?);";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, category.getName());
      ps.executeUpdate();

      try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          category.setId(generatedKeys.getInt(1));
        }
      }
    }
    return category;
  }

  public List<Category> findAll() throws SQLException {
    List<Category> list = new ArrayList<>();
    String sql = "SELECT id, name FROM categories;";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        list.add(new Category(id, name));
      }
    }
    return list;
  }

  public boolean update(Category category) throws SQLException {
    String sql = "UPDATE categories SET name = ? WHERE id = ?;";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, category.getName());
      ps.setInt(2, category.getId());

      return ps.executeUpdate() > 0;
    }
  }

  public boolean deleteById(int id) throws SQLException {
    String sql = "DELETE FROM categories WHERE id = ?;";

    try (Connection con = getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);

      return ps.executeUpdate() > 0;
    }
  }
}