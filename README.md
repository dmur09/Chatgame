# chatgame
A copy of the mtchat repo.  

This repo contains programs to implement a multi-threaded TCP chat server and client. To play this game, the Host asks the connected users a true or false question. The first user to answer correctly is given a point. Repeat until there is a winner.

* MtClient.java handles keyboard input from the user.
* ClientListener.java receives responses from the server and displays them
* MtServer.java listens for client connections and creates a ClientHandler for each new client
* ClientHandler.java receives messages from a client and relays it to the other clients.
* Client.java creates a client class that stores a socket number and a username for each client. 


## Identifying Information

* Name: Jason Kim, Diego Murillo, Zack Dell
* Student ID: #2413661, #2403501, #2423498
* Email: younkim@chapman.edu, dmurillo@chapman.edu, zdell@chapman.edu
* Course: CPSC 353-01
* Assignment: PA04 - Chatgame

## Source Files

* ClientHandler.java
* ClientListener.java
* MtClient.java
* MtServer.java
* Client.java

## Contributions

* Diego: Forked the repo, formed branches, and added teammates for collaboration. Then implemented Client.java class. Helped implement switch case for submission 2. Also finished the required for submission 3.
* Zack: Wrote the server side and client handler to handle username. Helped error handle a socket error found in submission 2 and implemented client handling.
* Jason: Edited messages to communicate username between server and clients and removed errors. Coded a main part of the switch case statments for submission 2.

## References

*

## Known Errors

*

## Build Insructions

* javac MtServer.java
* javac MtClient.java

## Execution Instructions

* java MtServer
* java MtClient
