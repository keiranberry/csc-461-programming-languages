package berry_keiran
import scala.collection.mutable
import scala.xml.{Elem, Node}

class CarShop(private val name: String) extends RDP with CoR{
  private var services: List[Service] = List.empty

  def addElement(data: Any): Unit = {
    data match {
      case service: Service =>
        services = services :+ service

      case _ => println("Invalid data type.")
    }
  }

  override def addData(indent: Int): String = {
    val indentation = " " * indent
    var output = "\n"
    output = output + s"$indentation....................................................."
    output = output + "\n" + indentation +  "Car Shop: " + name

    for (service <- services) {
      output = output + service.addData(indent + 2)
    }
    output + "\n" + indentation + "....................................................."
  }

  override def writeXML(): Elem = {
    val attributes = mutable.HashMap("name" -> name)
    val s = services.map(_.writeXML())

    XMLHelper.makeNode("CarShop", attributes, s)

  }

  override def readXML(node: Node): Unit = {
    val children = node.child
    for (child <- children) {
      if (child.label != "#PCDATA") {
        val newService = Service(0, "default description")
        newService.readXML(child)
        addElement(newService)
      }
    }
  }
  
  override def findService(serviceCode: Int): Boolean = {
    for (service <- services){
      if(service.findService(serviceCode))
        return true
    }
    
    false
  }
}