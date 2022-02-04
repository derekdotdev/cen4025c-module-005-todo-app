package com.derekdileo;

public class Main {
	
	public static TodoList list;

	public static void main(String[] args) {

		// Instantiate TodoList Singleton (which has SessionFactory) and print Instance
		// ID
		list = TodoList.getInstance();
		System.out.println("Instance ID: " + System.identityHashCode(list));
		
		// Welcome user
		System.out.println(WelcomeToApp.welcomeMessage);
		
		try {

			// Enter looped menu program
			list.toDoMenu(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		


	}

}


//
////create session factory
//		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TodoItem.class)
//				.buildSessionFactory();
//
//		// create session
//		Session session = factory.getCurrentSession();
//
//		// use the session object to save Java object
//		try {
//
//			// create a todoItem object
//			System.out.println("Creating a new todoItem object...");
//			TodoItem item = new TodoItem("Cook Dinner");
//
//			// start a transaction
//			session.beginTransaction();
//
//			// save the todoItem object
//			System.out.println("Saving the todoItem...");
//			session.save(item);
//
//			// commit transaction
//			session.getTransaction().commit();
//			
//			System.out.println("Done!");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		} finally {
//			factory.close();
//
//		}