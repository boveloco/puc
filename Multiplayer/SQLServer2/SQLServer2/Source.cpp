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

		connection->setSchema("Jogobd");


		sql::PreparedStatement * ps;
		ps = connection->prepareStatement("SELECT * FROM usuarios WHERE login = ? AND password = ?");

		ps->setString(1, login.c_str());
		ps->setString(2, password.c_str());

		sql::ResultSet* rs;
		rs = ps->executeQuery();

		if (rs->next())
			std::cout << "User valid!" << std::endl;
		else
			std::cout << "User or Password invalid" << std::endl;


		connection->close();
	}
	catch (sql::SQLException &ex)
	{
		std::cout << "An error occurried. Error code: " << ex.what() << std::endl;

	}
	return 0;
}