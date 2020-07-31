package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DBConnection;

public class CrudUtil {

  public static boolean executeUpdate(){
    return false;
  }

  public static ResultSet executeQuery(String sql , List<String> params) throws SQLException {
    Connection connection = DBConnection.getInstance().getConnection();
    PreparedStatement pstm = connection.prepareStatement(sql);
    int i = 0;
    for (String param : params) {
      i++;
      pstm.setObject(i, param);
    }
    return pstm.executeQuery();
  }

}
