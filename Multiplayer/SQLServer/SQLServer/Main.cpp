#include <mysql_connection.h>
#include <cppconn\driver.h>
#include <cppconn\prepared_statement.h>
#include <cppconn\resultset.h>
#include <cppconn\exception.h>
#include <iostream>

int main()
{
	std::string login;
	std::string password;

	std::cout << "Login: " << std::endl;
	std::getline(std::cin, login);

	std::cout << "Password: " << std::endl;
	std::getline(std::cin, password);

	try
	{
		sql::Driver* driver;
		driver = get_driver_instance();

		sql::Connection* connection;
		connection = driver->connect("localhost", "root", "");

		connection->close();
	}
	catch (sql::SQLException &ex)
	{
		std::cout << "An error occurried. Error code: " << ex.what() << std::endl;

	}
	return 0;
}