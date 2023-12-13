package berry_keiran
import scala.collection.mutable
import scala.xml.{Elem, Node}

class Vehicle(private var make: String, private var model: String, private var year: Int, var value: Int) extends RDP{
  override def addData(indent: Int): String = {
    val indentation = " " * indent
    val valueString = "$" + value.toString
    val formattedMake = f"${make}%-10s"
    val formattedModel = f"${model}%-10s"
    val formattedYear = f"${year}%-10s"
    val formattedValue = f"${valueString}%-10s"
    var output = "\n"
    output = output + indentation + s"Vehicle: Make: ${formattedMake}Model: ${formattedModel}Year: ${formattedYear}Value: ${formattedValue}"
    output
  }

  override def writeXML(): Elem = {
    val attributes = mutable.HashMap(
      "make" -> make,
      "model" -> model,
      "year" -> year.toString,
      "value" -> value.toString
    )

    XMLHelper.makeNode("Vehicle", attributes)
  }

  override def readXML(node: Node): Unit = {
    make = node.attribute("make").getOrElse("").toString()
    model = node.attribute("model").getOrElse("").toString()
    year = node.attribute("year").getOrElse(0).toString().toInt
    value = node.attribute("value").getOrElse(0).toString().toInt
  }

  def vehicleIsOfMake(makeSearch: String): Boolean = {
    if(make == makeSearch){
      return true
    }
    false
  }
}
