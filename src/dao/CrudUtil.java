package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class CrudUtil {

  public static boolean executeUpdate(String sql , Object... params) throws SQLException {
    Connection connection = DBConnection.getInstance().getConnection();
    PreparedStatement pstm = connection.prepareStatement(sql);
    int i = 0;
    for (Object param : params) {
      i++;
      pstm.setObject(i, param);
    }
    return pstm.executeUpdate() > 0;
  }

  public static ResultSet executeQuery(String sql , Object... params) throws SQLException {
    Connection connection = DBConnection.getInstance().getConnection();
    PreparedStatement pstm = connection.prepareStatement(sql);
    int i = 0;
    for (Object param : params) {
      i++;
      pstm.setObject(i, param);
    }
    return pstm.executeQuery();
  }

}
