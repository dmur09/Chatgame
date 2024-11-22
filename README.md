# Chatgame
This repo contains programs to implement a multi-threaded TCP chat server and client. To play this game, the Host asks the connected users a true or false question. The first user to answer correctly is given a point. Repeat until there is a winner.

* MtClient.java handles keyboard input from the user.
* ClientListener.java receives responses from the server and displays them
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java receives messages from a client and relays it to the other clients.
* Client.java creates a client class that stores a socket number and a username for each client. 


## Identifying Information

* Name: Jason Kim, Diego Murillo, Zack Dell
* Email: younkim@chapman.edu, dmurillo@chapman.edu, zdell@chapman.edu
* Course: CPSC 353-01 - Data Communications and Computer Networks 
* Assignment: PA04 - Chatgame

## Source Files

* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java
* Client.java

## References

* N/A

## Known Errors

* N/A

## Build Insructions

* javac MtServer.java
* javac MtClient.java

## Execution Instructions

* java MtServer
* java MtClient
