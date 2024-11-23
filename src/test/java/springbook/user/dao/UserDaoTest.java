package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

class UserDaoTest {

    /*
     * DAO를 사용하는 클라이언트인 main() 메소드가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.
     * UserDao는 ConnectionMaker 타입의 오브젝트에만 의존하도록 만들어졌으므로, UserDao는 어떤 방법으로든 ConnectionMaker 구현 클래스를 결정하고 생성해서 사용할 수 있다.
     * UserDAo를 테스트할 책임,
     * */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // 전략 패턴을 사용하여 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만든다.
        // 그러나 이 방법은 UserDao의 클라이언트인 main() 메소드가 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 만드는 책임을 짊어진다.
//        ConnectionMaker connectionMaker = new DConnectionMaker();
//        UserDao userDao = new UserDao(connectionMaker);

//        UserDao userDao = new DaoFactory().userDao(); // DaoFactory를 사용하여 UserDao 오브젝트를 생성한다.


        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);// DaoFactory를 사용하여 UserDao 오브젝트를 생성한다.
        UserDao userDao = context.getBean("userDao", UserDao.class);

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
}
