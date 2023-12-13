package berry_keiran
import scala.xml._

trait RDP {
  def addData(indent: Int): String
  def writeXML(): Elem
  def readXML(node: Node): Unit
}
