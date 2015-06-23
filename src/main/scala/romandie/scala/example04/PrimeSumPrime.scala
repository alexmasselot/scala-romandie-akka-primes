package romandie.scala.example04

import scala.annotation.tailrec

/**
 *
 * a PrimeSumPrime is a prime number where the sum of the digit is also a prime
 * Alexandre Masselot
 */

object PrimeSumPrime {
  /**
   * return true if is Prime
   * @param i
   * @return
   */
  def isPrime(i: Int): Boolean = {
    (2 to Math.sqrt(i).toInt).forall(i % _ != 0)
  }

  /**
   * sum the digit of an integer
   * @param i
   * @return
   */
  def sumDigit(i: Int): Int = {
    @tailrec
    def sumDigiHandler (acc:Int, i:Int):Int = i match{
      case x if x < 10 => acc+x
      case x => sumDigiHandler(acc+ (x % 10), x /10)
    }
    sumDigiHandler(0, i)
  }

  /**
   * is prime and sum of digit is Prime
   * @param i
   * @return
   */
  def isPSP(i:Int) = isPrime(i) && isPrime(sumDigit(i))

  class OutOfBoundException(i:Int) extends Exception(s"no PSP number found >= $i")

  /**
   * find the next PSP number >= i
   * @param i
   * @return
   */
  def nextPSP(i:Int):Int = (i until Int.MaxValue).find(isPSP) match {
    case Some(j)=> j
    case None => throw new OutOfBoundException(i)
  }

  /**
   * find all the PSP number between start and end
   * @param start
   * @param end
   * @return
   */
  def allPSP(start:Int, end:Int):Seq[Int] = (start until end).filter(isPSP)

  /**
   * find all the PSP number between start and end
   * @param start
   * @param end
   * @return
   */
  def streamPSP(start:Int, end:Int):Stream[Int] = (start until end).toStream.filter(isPSP)
}