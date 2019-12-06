package com.inc;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String name = "ООО Шуба", country = "Россия", city = "Чебоксары",
                address = "ул. Петрова, д.4", telephone = "237-89-08", email = "mex@mail.ru";

        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.insert_data(name, country, city, address, telephone, email);

        Scanner scan = new Scanner(System.in);

        System.out.println("Input contact you want to add to base. Name: ");
        name = scan.nextLine();
		System.out.println("Country: ");
		country = scan.nextLine();
		System.out.println("City: ");
		city = scan.nextLine();
		System.out.println("Address: ");
		address = scan.nextLine();
		System.out.println("Telephone: ");
		telephone = scan.nextLine();
		System.out.println("Email: ");
		email = scan.nextLine();

		dbHandler.insert_data(name, country, city, address, telephone, email);
		scan.close();

		String contact_name = "Институт ядерных технологий", contact_telephone = "511-11-11";
		dbHandler.change_contact_name_telephone(contact_name, contact_telephone);

		String contact_id = "5";
		dbHandler.delete_by_id(contact_id);
    }
}
