package romandie.scala.example07

/**
 * create same has 04, but configuration is loaded from file
 * "router1" is defined in the configuration file, allowing for different architecture in dev/prod etc.
 */

import java.io.File

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import romandie.scala.psp.PSPActor
import romandie.scala.psp.PSPActor._


object PSProuterWithConfigApp extends App {

  class MasterActor extends Actor with ActorLogging {
    val pspActor = context.actorOf(FromConfig.props(Props[PSPActor]), "router1")

    pspActor ! FindListPSP(2000000000, 2000100000)
    pspActor ! FindListPSP(2000100000, 2000200000)
    pspActor ! FindListPSP(2000200000, 2000300000)
    pspActor ! FindListPSP(2000300000, 2000400000)

    override def receive: Receive = {

      case al: PSPList =>
        log.info(s"received PSP $al")
    }
  }
  val config = ConfigFactory.parseFile(new File("conf/akka_01.conf"))
  val system = ActorSystem("MyActorSystem", config)
  val masterActor = system.actorOf(Props[MasterActor], "master")

  system.awaitTermination()
}