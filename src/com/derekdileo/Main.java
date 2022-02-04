package com.derekdileo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {

		// create session factory
		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TodoItem.class)
				.buildSessionFactory();

		// create session
		Session session = factory.getCurrentSession();

		// use the session object to save Java object
		try {

			// create a todoItem object
			System.out.println("Creating a new todoItem object...");
			TodoItem item = new TodoItem("Cook Dinner");

			// start a transaction
			session.beginTransaction();

			// save the todoItem object
			System.out.println("Saving the todoItem...");
			session.save(item);

			// commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			factory.close();

		}

	}

}

