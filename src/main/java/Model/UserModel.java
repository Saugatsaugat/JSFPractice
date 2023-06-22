package Model;

import Entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author saugat
 */
public class UserModel {

    EntityManagerFactory emf;
    EntityManager em;

    public UserModel() {
        emf = Persistence.createEntityManagerFactory("futsal");
        em = emf.createEntityManager();
    }

    public void getAllData() {
        TypedQuery<User> query = em.createQuery("Select e from User e", User.class);
        List<User> userList = query.getResultList();
        for (User user : userList) {
            System.out.println("\nName: " + user.getFirstname() + user.getMidname() + user.getLastname() + "\tEmail: " + user.getEmail());

        }

    }
    
    public void deleteDataById(){
        Long id = 6L;   
        User user = em.find(User.class, id);
        System.out.println(user);
        
        if (user != null) {
            em.remove(user);
        
    }
    }

    public static void main(String[] args) {
       new UserModel().getAllData();

    }
}
