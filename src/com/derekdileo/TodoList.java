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

	private SessionFactory getFactory() {
		return factory;
	}

	private void setFactory(SessionFactory factory) {
		TodoList.factory = factory;
	}

	private void closeFactory() {
		firstInstance.getFactory().close();
	}

	private Session getSession() {
		SessionFactory sessionFactory = firstInstance.getFactory();
		return sessionFactory.openSession();

	}

	// Check if db is empty
	@SuppressWarnings("unchecked")
	private boolean dbEmpty() {

		Session session = firstInstance.getSession();
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
	private void createTodoItem(String description) {

		if (description != null && !description.isBlank()) {

			TodoItem t1 = new TodoItem();
			t1.setDescription(description);

			Session session = firstInstance.getSession();
			session.beginTransaction();
			session.save(t1);
			session.getTransaction().commit();
			session.close();

			System.out.println("\nTodo Item: [ " + description + " ] created!");

		} else {

			System.out.println("Invalid entry. Please try again.");
		}

	}

	// Read all the saved to do items
	@SuppressWarnings("unchecked")
	private List<TodoItem> getAllTodoItemDetails() {

		if (!dbEmpty()) {

			Session session = firstInstance.getSession();
			session.beginTransaction();

			List<TodoItem> todoItemList = (List<TodoItem>) session.createQuery(

					"FROM TodoItem").list();

			session.getTransaction().commit();
			session.close();
			return todoItemList;

		} else {
			System.out.println("\nThe TDL is currently empty. Please add some items and get to work!");
			return null;
		}

	}

	// Update to do item by Id
	private void updateTodoItemById(int id, String description) {

		Session session = firstInstance.getSession();
		session.beginTransaction();

		try {
			TodoItem item = (TodoItem) session.get(TodoItem.class, id);
			String tempDesc = item.getDescription();
			item.setDescription(description);

			session.update(item);
			session.getTransaction().commit();
			session.close();
			System.out.println("Todo Item Updated from: [ " + tempDesc + " ] to: [ " + description + " ]");

		} catch (Exception e) {
			System.out.println("No Todo Item found with ID: " + id);
			session.close();
		}

	}

	// Delete to do item by Id
	private void deleteTodoItemById(int id) {

		if (!dbEmpty()) {
			Session session = firstInstance.getSession();
			session.beginTransaction();

			try {

				TodoItem item = (TodoItem) session.get(TodoItem.class, id);
				String desc = item.getDescription();

				session.delete(item);
				session.getTransaction().commit();
				session.close();
				System.out.println("Todo Item: [ " + desc + " ] Deleted!");

			} catch (Exception e) {
				System.out.println("No Todo Item found with ID: " + id);
				session.close();
			}

		}

	}

	// Delete all to do items
	private void deleteAllTodoItems() {

		if (!dbEmpty()) {
			List<TodoItem> todoItems = firstInstance.getAllTodoItemDetails();
			
			for (TodoItem item : todoItems) {
				int id = item.getId();
				firstInstance.deleteTodoItemById(id);
			}
		}
	}

	// Method called from Main to run TDL App
	@SuppressWarnings("resource")
	public void toDoMenu(int choice) {
		while (choice != 6) {
			Scanner scanner = new Scanner(System.in);

			System.out.println("\n1 - View Current Todo List");
			System.out.println("2 - Add Todo Item ");
			System.out.println("3 - Update Todo Item By ID");
			System.out.println("4 - Delete Todo Item By ID");
			System.out.println("5 - Delete ALL Todo Items");
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
				List<TodoItem> items = firstInstance.getAllTodoItemDetails();
				if (items != null) {
					for (TodoItem item : items) {
						System.out.println(
								"\nTodo Item ID: " + item.getId() + "    Description: " + item.getDescription());
					}
				}
				break;
			case 2:
				System.out.print("\nPlease type in the Todo item to be added: ");
				scanner.nextLine();
				String item = scanner.nextLine();
				firstInstance.createTodoItem(item);
				break;
			case 3:
				System.out.print("\nPlease type in the ID of the Todo item to be updated: ");
				int toUpdate = scanner.nextInt();
				System.out.print("\nPlease type in the new Todo item description: ");
				scanner.nextLine();
				String update = scanner.nextLine();
				firstInstance.updateTodoItemById(toUpdate, update);
				break;
			case 4:
				System.out.print("\nPlease type in the ID of the Todo item to be deleted: ");
				int toDelete = scanner.nextInt();
				firstInstance.deleteTodoItemById(toDelete);
				break;
			case 5:
				System.out.println("Are you sure you want to delete everything? (This action cannot be undone)");
				System.out.println("Enter Y or N");
				scanner.nextLine();
				String reply = scanner.nextLine();

				if (reply.toUpperCase().equals("Y")) {
					firstInstance.deleteAllTodoItems();
				} else {
					System.out.println("Todo items NOT deleted.");
				}

				break;
			default:
				System.out.println("Invalid entry...How did you even get in here??");
				break;
			}

		}

	}


}

//These are the methods which were used when TodoList Singleton contained an ArrayList<TodoItems>
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
