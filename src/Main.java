import model.Category;
import model.ClothingItem;
import repository.CategoryRepository;
import repository.ClothingItemRepository;

import java.sql.SQLException;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    String url = "jdbc:h2:./Lab7;DB_CLOSE_DELAY=-1";
    String user = "Shiro_845";
    String password = "";

    CategoryRepository categoryRepo = new CategoryRepository(url, user, password);
    ClothingItemRepository itemRepo = new ClothingItemRepository(url, user, password);

    try {
      System.out.println("1. Створення таблиць");
      categoryRepo.createTable();
      itemRepo.createTable();
      System.out.println("Таблиці готові до роботи.\n");

      System.out.println("2. Додавання даних (CREATE)");
      Category jackets = categoryRepo.create(new Category("Верхній одяг"));
      Category jeans = categoryRepo.create(new Category("Джинси"));
      System.out.println("Створено категорії в БД: ");
      System.out.println("  " + jackets);
      System.out.println("  " + jeans);

      ClothingItem winterParka = itemRepo.create(new ClothingItem("Зимова парка", "L", 3200.0, jackets.getId()));
      ClothingItem windbreaker = itemRepo.create(new ClothingItem("Вітровка", "M", 1500.0, jackets.getId()));
      ClothingItem slimJeans = itemRepo.create(new ClothingItem("Джинси Slim", "32/34", 1850.0, jeans.getId()));

      System.out.println("Створено товари в БД: ");
      System.out.println("  " + winterParka);
      System.out.println("  " + windbreaker);
      System.out.println("  " + slimJeans);

      System.out.println("\n3. Читання всіх записів (READ)");
      System.out.println("Список усіх категорій:");
      List<Category> allCategories = categoryRepo.findAll();
      for (Category c : allCategories) {
        System.out.println("  " + c);
      }

      System.out.println("Список усіх товарів:");
      List<ClothingItem> allItems = itemRepo.findAll();
      for (ClothingItem item : allItems) {
        System.out.println("  " + item);
      }

      System.out.println("\n4. Оновлення даних (UPDATE)");
      winterParka.setPrice(2799.0);
      winterParka.setName("Зимова парка (Знижка)");
      itemRepo.update(winterParka);
      System.out.println("Оновлено товар: " + winterParka);

      jackets.setName("Куртки");
      categoryRepo.update(jackets);
      System.out.println("Оновлено категорію: " + jackets);

      System.out.println("\n5. Видалення запису (DELETE)");
      itemRepo.deleteById(windbreaker.getId());
      System.out.println("Товар 'Вітровка' (ID=" + windbreaker.getId() + ") видалено.");

      System.out.println("\nАктуальні товари в магазині після видалення:");
      for (ClothingItem item : itemRepo.findAll()) {
        System.out.println("  " + item);
      }

    } catch (SQLException e) {
      System.err.println("Помилка під час роботи з БД: " + e.getMessage());
      e.printStackTrace();
    }
  }
}