import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.User;


public class Main {
	public static void main(String[] args){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gameserver");
		EntityManager em = factory.createEntityManager();
		
		/*User user = new User();
		user.setName("fronchetti");
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		*/
	}

}
