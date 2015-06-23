#Scala-romandie-akka-primes

These are a few very basic examples on how to use actors, for a [Scakla Romandie meetup on Akka](http://www.meetup.com/Scala-Romandie/events/222812513/), on June 24th 2015.

##What?

As an introduction to akka, these are some simple example of how to exchange messages between actors with [akka](http://akka.io) in Scala.
After the classic greeting messages, we'll see various communication patterns and setup, producing PSP number. Prime/Sum/Prime is an invention of the day (I doubt) to find prime number where the sum of the digit is also a prime.
Well, that only a simple way to simulate to some "CPU consuming" tasks without exchanging too much data.

We'll try to cover:

 * actor creation
 * simple message exchange with pattern matching
 * stream
 * ask pattern
 * router
 * some configuration

And the main goal of the show is to demonstrate how akka is cool and powerful and, together with Scala, make distributed computing a breeze.

The slides can be found [here](slides/20150624_scala_romandie_meetup_akka.pdf)

##How?

    ./activator run
And then select one of the app 

##who?
alexandre.masselot@gmail.com

http://alexandre-masselot.blogspot.ch/