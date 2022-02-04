package com.derekdileo;

public class Main {
	
	public static TodoList list;

	public static void main(String[] args) {

		// Instantiate TodoList Singleton (which has SessionFactory)
		// And Print Instance ID
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
