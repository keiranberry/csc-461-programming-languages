package berry_keiran
import scala.xml.{Elem, Node}

class Insurance extends RDP{
  private var zips: List[Zip] = List.empty

  def addElement(data: Any): Unit = {
    data match {
      case zip: Zip =>
        zips = zips :+ zip

      case _ => println("Invalid data type.")
    }
  }

  override def addData(indent: Int): String = {
    var output = ""
    for (zip <- zips) {
      val add = zip.addData(indent)
      output = output + add
    }
    output
  }

  override def writeXML(): Elem = {
    XMLHelper.makeNode("InsuranceData", null, zips.map(_.writeXML()))
  }

  override def readXML(node: Node): Unit = {
    val zipCodes = node.child
    for (code <- zipCodes) {
      if (code.label != "#PCDATA") {
        val newCode = code.attribute("code").get.toString()
        val zip = Zip(newCode.toInt)
        zip.readXML(code)
        addElement(zip)
      }
    }
  }

  def removeZip(zipCode: Int): Unit = {
    zips = zips.filterNot(zip => zip.zip == zipCode)
  }

  def zipExists(zipCode: Int): Boolean = {
    zips.exists(zip => zip.zip == zipCode)
  }

  def getVehiclesOfMake(makeSearch: String, zipCode: String): String = {
    var output = ""
    for (zip <- zips){
      if(zip.zip == zipCode.toInt){
        output = output + zip.getVehiclesOfMake(makeSearch)
      }
    }
    output
  }

  def serviceFound(serviceCode: Int): String = {
    for (zip <- zips){
      if(zip.serviceFound(serviceCode)) {
        return s"$serviceCode found in ${zip.zip}"
      }
    }
    s"$serviceCode not found"
  }

  def getValueOfVehicles(zipCode: Int): Int = {
    var total: Int = 0
    for (zip <- zips){
      if(zip.zip == zipCode){
        total = total + zip.getValueOfVehicles()
      }
    }
    total
  }

  def getInsuranceForOwner(zipCode: Int, ownerName: String): Double = {
    for (zip <- zips){
      if(zip.zip == zipCode){
        return zip.getInsuranceForOwner(ownerName)
      }
    }
    0
  }
}
