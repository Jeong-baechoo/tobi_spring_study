package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public class UserDao {

//    private final SimpleConnectionMaker simpleConnectionMaker;
    private final ConnectionMaker connectionMaker;

    public UserDao() {
        connectionMaker = new DConnectionMaker(); // 생성자에서 객체를 생성 -> DConnectionMaker 클래스에 대한 의존성이 생김
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();

        User user = new User();
        user.setId("whiteship");
        user.setName("백기선");
        user.setPassword("married");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?"); // 쿼리 준비
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery(); // 쿼리 실행
        rs.next(); // 첫 번째 로우 가리킴
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();
        return user;
    }

//    // 템플릿 메소드 패턴을 적용한 getConnection() 메소드
//    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
//
//    public class NUserDao extends UserDao {
//        public Connection getConnection() throws ClassNotFoundException, SQLException {
//            // N사 DB connection 생성코드
//            return null;
//        }
//    }
//
//    public class DUserDao extends UserDao {
//        public Connection getConnection() throws ClassNotFoundException, SQLException {
//            // D사 DB connection 생성코드
//            return null;
//        }
//    }
}
