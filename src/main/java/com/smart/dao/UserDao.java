package com.smart.dao;

import com.smart.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {
  private  JdbcTemplate  jdbcTemplate;

  private  final static String MATCH_COUNT_SQL="SELECT COUNT(*) FROM" +"T_USER WHERE USER_NAME = ? AND PASSWORD=? ";

  private  final  static  String UPDATE_LOGIN_INFO_SQL="UPDATE T_USER SET "+"LAST_VISIT=?,LAST_IP=?,CREDITS=? WHERE USER_ID=?";

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }
  @Autowired
  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public int getMatchCount(String userName,String password){
     String sql= "select count(*) from t_user" + "where user_name=? and password=? ";
     return  jdbcTemplate.queryForInt(sql,new Object[]{userName,password});
   }


   public User findUserByUserName(final  String userName){
     final User user = new User();
     jdbcTemplate.query(MATCH_COUNT_SQL, new Object[]{userName},
             new RowCallbackHandler() {
               public void processRow(ResultSet rs) throws SQLException {
                  user.setUserId(rs.getInt("user_id"));
                  user.setUserName(userName);
                  user.setCredits(rs.getInt("credits"));
               }
             });
         return user;
   }
  public void updateLoginfo(User user){
     jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL,new Object[]{
     user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});


  }
}
