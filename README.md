# JavaPersistanceAPI
/// This are just the Basic guidelines which shows that how to make this project run by making several changes
-open XAMPP control panel and change the password to 'password' in C/xampp/phpMyadmin/php_inc.php
-make database named sample and product table
open admin console from glassfish server
	-change JDBC resources
	- JDBC connection Pools
	
	Change the connection pools first and then the JDBC resources....
	-select pool_name from persistence.xml first to JDBC connection Pool by adding the new one...!
		-Edit url, URL, password thing here....!
		-url is the big URL in persistance.xml
		-password is the password
	
	-now come to JDBC resources
		-click new....
		-write mysql in JNDI Name
		-select our POOL name
		-and then just press okay
		
-using POSTMAN in Chrome
	-PUT for Update
	-DELETE for delete // just add URL with the id at the end..and press enter..it will get deleted.
	-POST for add... select post and add all the data there in content and press enter
