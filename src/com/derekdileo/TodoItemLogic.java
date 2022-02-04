package com.derekdileo;

public class TodoItemLogic {

//	public static void main(String[] args) {
//
//		TodoItemLogic tdil = new TodoItemLogic();
//		tdil.createTodoItems();
////		tdil.readAllTodoItemDetails();
////		tdil.updateTodoItemById();
////		tdil.deleteTodoItemById();
//
//	}
//
//	// Create and save a to do item
//	public void createTodoItem(String description) {
//
//		TodoItem t1 = new TodoItem();
//		t1.setDescription(description);
//
//		SessionFactory sessionFactory = TodoList.getFactory();
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		session.save(t1);
//		session.getTransaction().commit();
//
//		System.out.println("Todo Item: " + description + " created!");
//
//	}
//
//	// Create and save to do items
//	public void createTodoItems() {
//
//		TodoItem t1 = new TodoItem();
////		t1.setId(1);
//		t1.setDescription("Fold Laundry");
//
//		TodoItem t2 = new TodoItem();
////		t2.setId(2);
//		t2.setDescription("Cook Dinner");
//
//		TodoItem t3 = new TodoItem();
//		t3.setId(1);
//		t3.setDescription("Clean Garage");
//
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//
//		Session session = sessionFactory.openSession();
//
//		session.beginTransaction();
//		session.save(t1);
//		session.save(t2);
//		session.save(t3);
//
//		session.getTransaction().commit();
//		System.out.println("Todo Items Created!");
//
//	}
//
//	// Read all the saved to do items
//	@SuppressWarnings("unchecked")
//	public void readAllTodoItemDetails() {
//
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//
//		Session session = sessionFactory.openSession();
//
//		session.beginTransaction();
//
//		List<TodoItem> todoItemList = (List<TodoItem>) session.createQuery(
//
//				"FROM TodoItem").list();
//
//		System.out.println("*** Todo Item Details ***");
//
//		for (TodoItem item : todoItemList) {
//
//			System.out.println("Todo ID     : " + item.getId());
//			System.out.println("Todo Desc.  : " + item.getDescription());
//
//		}
//
//		session.getTransaction().commit();
//
//	}
//	
//	
//	// Update Todo Item by Id
//    public void updateTodoItemById(int id,String description)
//    {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        
//        TodoItem item = (TodoItem)session.get(TodoItem.class, id);
//		String tempDesc = item.getDescription();
//        item.setDescription(description);
//        
//        session.update(item);
//        session.getTransaction().commit();
//		System.out.println("Todo Item Updated from: " + tempDesc + " to: " + description);
//    }
//
//    // Delete odo Item by Id
//    public void deleteTodoItemById(int id)
//    {
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        
//        TodoItem item = (TodoItem)session.get(TodoItem.class, id);
//		String desc = item.getDescription();
//        
//        session.delete(item);
//        session.getTransaction().commit();
//		System.out.println("Todo Item: " + desc + " Deleted!");
//    }
}
	
