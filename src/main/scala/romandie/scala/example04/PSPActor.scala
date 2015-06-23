package romandie.scala.example04

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import romandie.scala.example04.PSPActor._

/**
 * Created by amasselo on 6/22/15.
 */
class PSPActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case FindNextPSP(i) =>
      log.info(s"FindNextPSP($i)")
      sender ! PSPSingle(PrimeSumPrime.nextPSP(i))
    case FindStreamPSP(start, end) =>
      log.info(s"FindStreamPSP($start, $end)")
      PrimeSumPrime.streamPSP(start, end).foreach({
        x =>
          log.info(s"sending back from stream $x")
          sender ! PSPSingle(x)
      })
    case FindListPSP(start, end) =>
      log.info(s"FindListPSP($start, $end)")
      sender ! PSPList(PrimeSumPrime.allPSP(start, end))
  }
}

object PSPActor{
  val props = Props[PSPActor]

  case class FindNextPSP(i:Int)
  case class FindListPSP(start:Int, end:Int)
  case class FindStreamPSP(start:Int, end:Int)
  case class PSPSingle(i:Int)
  case class PSPList(l:Seq[Int]){
    override def toString = s"len=${l.size} (${l.head}..${l.last})"
  }

}