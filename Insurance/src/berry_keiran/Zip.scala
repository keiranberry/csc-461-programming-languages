package berry_keiran
import scala.collection.mutable
import scala.xml.{Elem, Node}
import collection.parallel.CollectionsHaveToParArray

class Zip(val zip: Int) extends RDP{
  private var owners: List[Owner] = List.empty
  private var shops: List[CarShop] = List.empty

  def addElement(data: Any): Unit = {
    data match {
      case owner: Owner =>
        owners = owners :+ owner

      case shop: CarShop =>
        shops = shops :+ shop

      case _ => println("Invalid data type.")
    }
  }

  override def addData(indent: Int): String = {
    val indentation = " " * indent
    val nextIndentation = " " * (indent + 2)
    var output = "\n"
    output = output + s"Zip Code: ${zip}"
    output = output + "\n" + "======================================================"
    for (owner <- owners) {
      output = output + owner.addData(indent + 2)
    }

    for (shop <- shops){
      output = output + shop.addData(indent + 2)
    }

    output
  }

  override def writeXML(): Elem = {
    val attributes = mutable.HashMap("code" -> zip.toString)
    val o = owners.map(_.writeXML())
    val c = shops.map(_.writeXML())

    XMLHelper.makeNode("ZipCode", attributes, o ++ c)
  }

  override def readXML(node: Node): Unit = {
    val children = node.child
    for (child <- children) {
      if (child.label != "#PCDATA") {
        if(child.label == "Owner") {
          val newOwner = Owner(child.attribute("name").getOrElse("").toString())
          newOwner.readXML(child)
          addElement(newOwner)
        } else if (child.label == "CarShop"){
          val newShop = CarShop(child.attribute("name").getOrElse("").toString())
          newShop.readXML(child)
          addElement(newShop)
        }
      }
    }
  }

  def getVehiclesOfMake(makeSearch: String): String = {
    var output = ""
    for (owner <- owners){
      output = output + owner.getVehiclesOfMake(makeSearch)
    }
    output
  }

  def serviceFound(serviceCode: Int): Boolean = {
    for (shop <- shops){
      if(shop.findService(serviceCode)){
        return true
      }
    }
    false
  }

  def getValueOfVehicles(): Int = {
    var total: Int = 0
    //GRADING: PARALLEL
    val ownersPar: collection.parallel.ParSeq[Owner] = owners.toParArray
    total = ownersPar.flatMap(_.vehicles).map(_.value).sum
    total
  }

  def getInsuranceForOwner(ownerName: String): Double = {
    //GRADING: INSURANCE
    val ownersPar: collection.parallel.ParSeq[Owner] = owners.toParArray

    val foundOwner: Option[Owner] = ownersPar.find(_.name.toLowerCase() == ownerName.toLowerCase())

    val owner: Owner = foundOwner.getOrElse(Owner(""))

    val carsTotalValue = owner.vehicles.map(_.value).sum.toDouble
    val vehicleCount = owner.vehicles.length
    val claimCount = owner.claims.length

    carsTotalValue * 0.001 + vehicleCount * 25 + claimCount * carsTotalValue * 0.002
  }
}
