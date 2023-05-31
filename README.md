# AlicisindanClient

This classes are used to create the library for Android application Alicisindan to work with the server.
The library needs to be imported by the main application project for it to work. (The released .jar files are used for dependency imports.)

Required classes like User, Listing, Password, Review and Verification are implemented with this library.
Objects of such classes can be created either by the library itself or the application that is using the library.

Additionally, this library handles the connection between client and server.
Response, Request and Connection classes are implemented within this library for this purpose.
Request objects carry the message of the client. Static methods of the Connection class are responsible with sending the Request objects to the server and receiving the Response objects.
Response objects carry the answer of the server. It may carry some data taken from the database, an exception message, result of a computation or just a message stating whatever client requested was done succesfully.

Those classes are packaged inside a library only so the serialized Request and Response classes can be sent between client and server through network sockets. Library makes sure the packages of such classes are identical both on client and server.
When Request and Response objects are implemented directly without a library, the packages of classes will differ and there will be exceptions like "NoSuchClass" during connections.
 
