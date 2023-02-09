# GlobalMentoring_Task3_File Sharing
## Implement the next use cases of File Share application:
1.	Save file to the database.
2.	Retrieve file from the database.
3.	Optional: file expiration.
Large files should be supported (size up to 200 MB).
Acceptance criteria:
1.	File Share database schema is developed: 
o	DB schema diagram is provided (5 points);
o	stored procedures for saving and retrieving files from DB are created (5 points).
2.	DAO on JDBC is implemented: 
o	DAO methods that are not used in proposed use cases can throw UnsupportedOperationException (2 points);
o	CallableStatement is used to call DB stored procedures (3 points);
o	large binary files are retrievable from DB (5 points).
Think about pros and cons of stored procedures usage comparing to SQL statement stored in Java code. Describe what difficulties you’ve faced when working with large binary files. Make demo via console interface or via special main method.
