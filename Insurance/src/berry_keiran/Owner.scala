package berry_keiran
import scala.collection.mutable
import scala.xml.{Elem, Node}

class Owner(val name: String) extends  RDP{
  var vehicles: List[Vehicle] = List.empty
  var claims: List[Claim] = List.empty

  def addElement(data: Any): Unit = {
    data match {
      case vehicle: Vehicle =>
        vehicles = vehicles :+ vehicle

      case claim: Claim =>
        claims = claims :+ claim

      case _ => println("Invalid data type.")
    }
  }

  override def addData(indent: Int): String = {
    val indentation = " " * indent
    var output = "\n"
    output = output + indentation + "*****************************************************"
    output = output + "\n" + indentation + name
    output = output + "\n" + indentation + "Vehicle(s)"

    for (vehicle <- vehicles) {
      output = output + vehicle.addData(indent + 2)
    }

    output = output + "\n" + indentation + "Claim(s)"

    for (claim <- claims) {
      output = output + claim.addData(indent + 2)
    }
    output + "\n" + indentation + "*****************************************************"
  }

  override def writeXML(): Elem = {
    val attributes = mutable.HashMap("name" -> name)
    val v = vehicles.map(_.writeXML())
    val c = claims.map(_.writeXML())

    XMLHelper.makeNode("Owner", attributes, v ++ c)

  }

  override def readXML(node: Node): Unit = {
    val children = node.child
    for (child <- children) {
      if (child.label != "#PCDATA") {
        if (child.label == "Vehicle") {
          val newVehicle = Vehicle("", "", 0, 0)
          newVehicle.readXML(child)
          addElement(newVehicle)
        } else if (child.label == "Claim") {
          val newClaim = Claim("")
          newClaim.readXML(child)
          addElement(newClaim)
        }
      }
    }
  }

  def getVehiclesOfMake(makeSearch: String): String = {
    var output = ""
    for (vehicle <- vehicles) {
      if(vehicle.vehicleIsOfMake(makeSearch)){
        output = output + vehicle.addData(0)
      }
    }
    output
  }
}
