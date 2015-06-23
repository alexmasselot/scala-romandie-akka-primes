package romandie.scala.example02

import akka.actor.Actor.Receive
import akka.actor.{Props, ActorSystem, ActorLogging, Actor}

case class Name(value:String) extends AnyVal
object Tcho

class PingActor extends Actor with ActorLogging {
  def receive = {
    case Name(name) =>
      log.info(s"hello, my name is $name")
    case tcho =>
      log.info("shutting down")
      context.system.shutdown()
  }
}

object HelloMultiMessagesApp extends App{
  val system = ActorSystem("MyActorSystem")
  val pingActor = system.actorOf(Props[PingActor], "pingActor")

  pingActor ! Name("Bond")
  pingActor ! Name("Paf")
  pingActor ! Name("Pif")
  pingActor ! Tcho

  system.awaitTermination()
}