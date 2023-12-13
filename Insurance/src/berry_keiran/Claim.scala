package berry_keiran
import scala.xml._

class Claim(private var date: String) extends RDP{
  override def addData(indent: Int): String = {
    val indentation = " " * indent
    "\n" + indentation + "Claim: " + date
  }

  override def writeXML(): Elem = {
    Elem(null, "Claim", Null, TopScope, true, Elem(null, "date", Null, TopScope, true, Text(date.strip())))
  }

  override def readXML(node: Node): Unit = {
    date = node.text.strip().split(" ")(0)
  }
}
