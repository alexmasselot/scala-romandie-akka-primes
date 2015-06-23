package romandie.scala.example03

/**
 * create a MasterActor, that will
 * * launch a PingActor that will
 * * send 3 names to this PingActor
 *
 */
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

case class Name(value: String)

case class Greetings(value: String)

object Tcho

object GoForIt

class PingActor extends Actor with ActorLogging {
  def receive = {
    case Name(name) =>
      log.info(s"received [$name]")
      sender ! Greetings(s"hello, my name is $name")
    case Tcho => sender ! Tcho
  }
}

class MasterActor extends Actor with ActorLogging {
  val pingActor = context.actorOf(Props[PingActor], "pingActor")

  override def receive: Receive = {
    case GoForIt =>
      List(Name("Bond"),Name("Paf"), Name("Pif")).foreach(pingActor !)
      pingActor ! Tcho
    case Greetings(message) =>
      log.info(s"received greeting [$message]")
    case Tcho => context.system.shutdown()
  }
}

object HelloMultiActorsApp extends App {
  val system = ActorSystem("MyActorSystem")

  val masterActor = system.actorOf(Props[MasterActor], "master")
  masterActor ! GoForIt

  system.awaitTermination()
}