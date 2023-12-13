package berry_keiran
import scala.xml._

class Service(private var code: Int, private var description: String) extends RDP with CoR{
  override def addData(indent: Int): String = {
    val indentation = " " * indent
    var output = "\n"
    output = output + s"$indentation(${code}) ${description}"
    output
  }

  override def writeXML(): Elem = {
    val attribute =  new UnprefixedAttribute("code", code.toString, Null)
    Elem(null, "CarService", attribute, TopScope, true, Text(description))
  }

  override def readXML(node: Node): Unit = {
    code = node.attribute("code").get.toString().toInt
    description = node.text
  }

  override def findService(serviceCode: Int): Boolean = {
    if(code == serviceCode){
      return true
    }

    false
  }

}
