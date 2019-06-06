# fitnessfanaticsHTTPhelper
This is a library to be used in conjuction with my server. It provdes CRUD absraction, setting persistince, and a user template to extend.
# Guide
To create the server, all one must do is use: `HTTPService service = new HTTPService(ApplicationContext.initialize());`
This creates a service and loads settings from the configs.txt file.
Note that while this is technicly all you need, Authorizatins management, and options setting must be implemented in application. 
Methoids have been written to aid in this, such as:`HTTPservice.createConfigsFile()`,`service.setAuths()`, and `service.testAuths()`.
These all handle wrights to the disk and memory concurrently to prevent state loss.

An importent conponent of the API is the `User` abstract class. This is simply an abstract class that implements a jackson annotation, and should
be extended to match the Domain model of the server.

