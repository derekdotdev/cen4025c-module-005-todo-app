package com.derekdileo;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TodoList {

	// Used with lazy instantiation via getInstance()
	private static TodoList firstInstance = null;
	private static SessionFactory factory = null;


	// Constructor is private because TodoList is a Singleton
	// only getInstance() can instantiate
	private TodoList() {
		setFactory(new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(TodoItem.class)
				.buildSessionFactory());
	}

	// TodoList is a Singleton Class uses getInstance and a private constructor
	public static TodoList getInstance() {
		// Lazy instantiation - only instantiated if needed
		if (firstInstance == null) {
			synchronized (TodoList.class) {
				if (firstInstance == null) {
					firstInstance = new TodoList();
				}
			}
		}
		return firstInstance;
	}

	// Get Session (use this later when refactoring)
	public Session getSession() {
		SessionFactory sessionFactory = firstInstance.getFactory();
		return sessionFactory.openSession();

	}

	// Check if empty
	@SuppressWarnings("unchecked")
	private boolean dbEmpty() {

		SessionFactory sessionFactory = firstInstance.getFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		List<TodoItem> todoItemList = (List<TodoItem>) session.createQuery(

				"FROM TodoItem").list();

		if (todoItemList.isEmpty()) {
			session.close();
			return true;
		} else {
			session.close();
			return false;
		}

	}

	// Create and save a to do item
	public void createTodoItem(String description) {

		if (description != null && !description.isBlank()) {

			TodoItem t1 = new TodoItem();
			t1.setDescription(description);

			SessionFactory sessionFactory = firstInstance.getFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(t1);
			session.getTransaction().commit();
			session.close();

			System.out.println("Todo Item: " + description + " created!");

		} else {

			System.out.println("Invalid entry. Please try again.");
		}

	}

	// Read all the saved to do items
	@SuppressWarnings("unchecked")
	public void readAllTodoItemDetails() {

		if (!dbEmpty()) {
			SessionFactory sessionFactory = firstInstance.getFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			
			List<TodoItem> todoItemList = (List<TodoItem>) session.createQuery(
					
					"FROM TodoItem").list();
			
			System.out.println("\n*** Todo Item Details ***");
			
			for (TodoItem item : todoItemList) {

				System.out.println("\nTodo ID: " + item.getId() + " Todo Desc.   : " + item.getDescription());
			}
			
			session.getTransaction().commit();
			session.close();

		} else {
			System.out.println("\nThe TDL is currently empty. Please add some items and get to work!");
		}

	}

	// Update Todo Item by Id
	public void updateTodoItemById(int id, String description) {
		SessionFactory sessionFactory = firstInstance.getFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		TodoItem item = (TodoItem) session.get(TodoItem.class, id);
		String tempDesc = item.getDescription();
		item.setDescription(description);

		session.update(item);
		session.getTransaction().commit();
		session.close();
		System.out.println("Todo Item Updated from: " + tempDesc + " to: " + description);

	}

	// Delete Todo Item by Id
	public void deleteTodoItemById(int id) {

		if (!dbEmpty()) {

			// ********************* Need input validation for ID!! ************************

			SessionFactory sessionFactory = firstInstance.getFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			TodoItem item = (TodoItem) session.get(TodoItem.class, id);
			String desc = item.getDescription();

			session.delete(item);
			session.getTransaction().commit();
			session.close();
			System.out.println("Todo Item: " + desc + " Deleted!");

		}
	}



	// Method called from Main to run TDL App
	@SuppressWarnings("resource")
	public void toDoMenu(int choice) {
		while (choice != 6) {
			Scanner scanner = new Scanner(System.in);

			System.out.println("\n1 - View Current Todo List");
			System.out.println("2 - Add Todo Item ");
			System.out.println("3 - Remove Todo Item By ID");
			System.out.println("4 - Remove Last Todo Item");
			System.out.println("5 - Remove Particular Todo Item");
			System.out.println("6 - Exit");
			System.out.print("\nChoice: ");
			choice = scanner.nextInt();

			// Validate user input
			if (choice > 0 && choice <= 6) {

				if (choice != 6) {
					performOperationsUsingSwitch(choice, scanner);

				} else {
					System.out.println("\nExiting TDL. Goodbye");
					closeFactory();
					return;
				}

			} else {
				System.out.println("Invalid entry! Reloading options...");
			}

		}

	}

	// Method which uses switch statement to handle user choices
	private static void performOperationsUsingSwitch(int choice, Scanner scanner) {
		if (firstInstance.getFactory() != null) {
			switch (choice) {
			case 1:
				firstInstance.readAllTodoItemDetails();
				break;
			case 2:
				System.out.print("\nPlease type in the Todo item to be added: ");
				scanner.nextLine();
				String item = scanner.nextLine();
				firstInstance.createTodoItem(item);
				break;
			case 3:
				System.out.print("\nPlease type in the number of the Todo item to be deleted: ");
				int toDelete = scanner.nextInt();
				firstInstance.deleteTodoItemById(toDelete);
//				firstInstance.deleteFirstTodoItem();
				break;
			case 4:
//				firstInstance.deleteLastTodoItem();
				break;
			case 5:
//				System.out.print("\nPlease type in the number of the Todo item to be deleted: ");
//				int toDelete = scanner.nextInt();
//				firstInstance.deleteTodoItemAtIndex(toDelete);
				break;
			default:
				System.out.println("Invalid entry...How did you even get in here??");
				break;
			}

		}

	}

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		TodoList.factory = factory;
	}

	public void closeFactory() {
		firstInstance.getFactory().close();
	}

}

//
//
//
//private ArrayList<TodoItem> getTodoList() {
//	return firstInstance.todoList;
//}
//
//// Used in other methods as conditional
//private boolean listEmpty() {
//	if (firstInstance.todoList.isEmpty()) {
//		System.out.println("Error: TDL is empty!");
//		return true;
//	} else {
//		return false;
//	}
//}
//
//private void addTodoItem(String description) {
//	// Validate user input
//	if (description != null && !description.isBlank()) {
//		TodoItem todoItem = new TodoItem(description);
//		firstInstance.todoList.add(todoItem);
//	} else {
//		System.out.println("Invalid entry. Please try again.");
//	}
//}
//
//private void deleteFirstTodoItem() {
//	if (!listEmpty()) {
//		System.out.println("Deleting item: " + firstInstance.todoList.get(0).toString());
//		firstInstance.todoList.remove(0);
//	} else {
//		return;
//	}
//}
//
//private void deleteLastTodoItem() {
//	if (!listEmpty()) {
//		int index = firstInstance.todoList.size() - 1;
//		System.out.println("Deleting item: " + firstInstance.todoList.get(index).toString());
//		firstInstance.todoList.remove(index);
//	} else {
//		return;
//	}
//}
//
//private void deleteTodoItemAtIndex(int index) {
//	if (!listEmpty()) {
//		if ((index) <= firstInstance.todoList.size()) {
//			System.out.println("Deleting item: " + firstInstance.todoList.get(index).toString());
//			firstInstance.todoList.remove(index);
//		} else {
//			System.out.println("Error. No item at that location!");
//			return;
//		}
//	} else {
//		return;
//	}
//}
//
//private void showTodoList() {
//	int counter = 0;
//	if (firstInstance.todoList.isEmpty()) {
//		System.out.println("\nThe TDL is currently empty. Please add some items and get to work!");
//	} else {
//		System.out.println("\n - - TO DO LIST - - ");
//		for (TodoItem item : firstInstance.todoList) {
//			System.out.println("Item " + counter++ + "): " + item.toString());
//		}
//	}
//}
