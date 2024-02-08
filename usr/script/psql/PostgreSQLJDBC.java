import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class PostgreSQLJDBC {
   public static void main(String[] args) {
      Connection connection = null;
      try {
         // 注册 PostgreSQL JDBC 驱动
         Class.forName("org.postgresql.Driver");

         // 打开一个连接
         System.out.println("连接数据库...");
         connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/walnsdb", "walns", "holyshit");

         // 执行 SQL 查询
         System.out.println("查询表中数据...");
         String sql = "SELECT * FROM walns";
         Statement statement = connection.createStatement();
         ResultSet resultSet = statement.executeQuery(sql);

         // 输出结果
         while(resultSet.next()){
            int id  = resultSet.getInt("id");
            String name = resultSet.getString("name");
            System.out.println("ID: " + id + ", Name: " + name);
         }
         resultSet.close();
         statement.close();

         // 关闭连接
         connection.close();
         System.out.println("连接已关闭。");
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
}
