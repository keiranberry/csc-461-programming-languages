/*Every Line with a * has its grading tag (if reached): __X___

0. Got it running	6			__X___

1.	Add + Display*	36
Prompts correct 				___X__
Adds each item 					___X__
Above displays correctly formatted 		__X___


2A) Remove + Display*	10
Prompts correct					___X__
Removes and displays correctly 			__X___


2B) Add + XML save*	14
Console added items saved correctly 		___X__
Console added multiples is saved correctly 	__X___


2C) XML load + XML save*	14
1 XML file loaded and saved correctly 		___X__
2+ XML file loaded and saved correctly		___X__


2D) XML load + Display*	12
1 XML file loaded and displays correctly 	___X__
2+ XML file loaded and displays correctly	___X__


2E) XML+ Display with bad file handing	10
All errors handled 				__X___


3.	Stress test for above*	12
Loads in file, adds data, and displays/saves correctly		__X___
Appends a file and displays/saves correctly 			__X___
Removes elements after edits, and displays/saves correctly 	__X___


4. Find cars*	9
RDP format at least there				___X__
Lists cars						___X__
Formatting						___X__
Handles “not found case”				__X___


5. Find service*	14
CoR format at least there				___X__
First item found and output formatted correctly		__X___
Handles “not found case”				___X__


6a.  Total Insured 	7				___X__
Correct with no claims					__X___
Correct with claims 					___X__
Parallelized* 						__X___


6a.  Insured For 	9				___X__
Correct with no claims					__X___
Correct with claims 					___X__
Parallelized* 						__X___
* */
package berry_keiran

import java.io.{FileNotFoundException, FileWriter}
import org.xml.sax.SAXParseException
import java.text.DecimalFormat
import scala.io.StdIn
import scala.xml.XML

/**
 * author: Keiran Berry
 * description: a program which takes in insurance information
 * from the console or an XML input, and can display to the
 * console, write XML, and has various functionality with
 * the insurance information itself.
 */
object MainStarter {
    def main(args: Array[String]) = {
        val menu: String =
            """
              |1) Add Data
              |2) Display Data
              |3) Remove Zip
              |4) Load XML
              |5) Write XML
              |6) Find a Cars of Make in Zip
              |7) Find a Service
              |8) Total Value Insured
              |9) Insurance For
              |0) Quit
              |
              |Choice:> """.stripMargin
        var choice : Any = -1
        var insurance = Insurance()
        var temp = ""
        var formatter = new DecimalFormat("#,##0.00")

        while (choice != "0") {
            print(menu)
            //something to strip out empty lines
            temp = StdIn.readLine()
            while (temp.isEmpty)
                temp = StdIn.readLine()

            choice = temp

            if(choice == "1"){
                print("What Zip Code:> ")
                var tempZip = ""
                var addMore = "y"
                tempZip = StdIn.readLine()
                if (!insurance.zipExists(tempZip.toInt)) {
                    val newZip = Zip(tempZip.toInt)
                    while(addMore == "y"){
                        addZipElement(newZip)
                        print("Add another Zip Code element (y/n):> ")
                        addMore = StdIn.readLine().toLowerCase
                    }
                    //GRADING: ADD
                    insurance.addElement(newZip)
                } else {
                    println(s"$tempZip is already in the database")
                }
            } else if (choice == "2"){
                //GRADING: PRINT
                print(insurance.addData(0))
            } else if (choice == "3"){
                print("What Zip Code:> ")
                val zipToDelete = StdIn.readLine().toInt
                if(insurance.zipExists(zipToDelete)){
                    insurance.removeZip(zipToDelete)
                    println(s"Removed $zipToDelete")
                } else{
                    println("Zip Code not found")
                }
            } else if (choice == "4"){

                try {
                    print("File name:> ")
                    val fileName = StdIn.readLine()
                    val nodeIn = XML.loadFile(fileName)
                    if (nodeIn.label != "InsuranceData")
                        throw new SAXParseException("Invalid File", null)

                    //GRADING: READ
                    insurance.readXML(nodeIn)
                } catch {
                    case e: FileNotFoundException =>
                        println(s"Could not open file: ${e.getMessage}")

                    case e: SAXParseException =>
                        println("Invalid XML file. Needs to be an InsuranceData XML file")
                }

            } else if (choice == "5"){
                print("File name:> ")
                val fileName = StdIn.readLine()
                //GRADING: WRITE
                val xmlTree = insurance.writeXML()
                val prettyPrinter = new scala.xml.PrettyPrinter(80, 2)
                val prettyXml = prettyPrinter.format(xmlTree)
                val write = new FileWriter(fileName)
                write.write(prettyXml)
                write.close()
            } else if (choice == "6"){
                print("Zip Code:> ")
                val zipToSearch = StdIn.readLine()
                print("Vehicle:> ")
                val vehicleToSearch = StdIn.readLine()
                //GRADING: VEHICLE
                println(insurance.getVehiclesOfMake(vehicleToSearch, zipToSearch))
            } else if (choice == "7"){
                print("Car Service:> ")
                val codeToSearch = StdIn.readLine()
                //GRADING: SERVICE
                println(insurance.serviceFound(codeToSearch.toInt))
            } else if (choice == "8"){
                print("What Zip Code:> ")
                val zipToSearch = StdIn.readLine().toInt
                val totalValue = insurance.getValueOfVehicles(zipToSearch)
                val output = "Value: $" + formatter.format(totalValue)
                println(output)
            } else if (choice == "9"){
                print("What Zip Code:> ")
                val zipToSearch = StdIn.readLine().toInt
                print("What Owner:> ")
                val ownerName = StdIn.readLine()
                val totalPayment = insurance.getInsuranceForOwner(zipToSearch, ownerName)
                val output = "Monthly payment: $" + formatter.format(totalPayment)
                println(output)
            }


        }
    }

    def addZipElement(zip: Zip): Unit = {
        print("What Element (Owner, Car Shop ):> ")
        val elementType = StdIn.readLine().toLowerCase

        elementType match {
            case "o" | "owner" =>
                addOwner(zip)

            case "c" | "car shop" =>
                addCarShop(zip)

            case _ =>
                println("Invalid element type.")
        }
    }

    def addOwner(zip: Zip): Unit = {
        print("Name:> ")
        val ownerName = StdIn.readLine()
        var addAnother = "y"
        val owner = Owner(ownerName)

        while (addAnother == "y") {
            addOwnerElement(owner)

            print("Add another Owner element (y/n):> ")
            addAnother = StdIn.readLine().toLowerCase
        }
        zip.addElement(owner)
        println("Added Owner")
    }

    def addCarShop(zip: Zip): Unit = {
        print("Name:> ")
        val shopName = StdIn.readLine()
        var addAnother = "y"
        val shop = CarShop(shopName)

        while (addAnother == "y") {
            addShopElement(shop)

            print("Add another element (y/n):> ")
            addAnother = StdIn.readLine().toLowerCase
        }

        zip.addElement(shop)
        println("Added Car Shop")
    }

    def addOwnerElement(owner: Owner): Unit = {
        print("What Element (Vehicle, Claim):> ")
        val elementType = StdIn.readLine().toLowerCase

        elementType match {
            case "v" | "vehicle" =>
                print("Make:> ")
                val make = StdIn.readLine()
                print("Model:> ")
                val model = StdIn.readLine()
                print("Year:> ")
                val year = StdIn.readInt()
                print("Value:> ")
                val value = StdIn.readInt()

                val vehicle = Vehicle(make, model, year, value)
                owner.addElement(vehicle)
                println("Added Vehicle")

            case "c" | "claim" =>
                print("Date:> ");
                val date = StdIn.readLine()

                val claim = Claim(date)
                owner.addElement(claim)
                println("Added Claim")

            case _ =>
                println("Invalid element type.")
        }
    }

    def addShopElement(shop: CarShop): Unit = {
        print("Code:> ")
        val code = StdIn.readInt()
        print("Description:> ")
        val description = StdIn.readLine()
        val service = Service(code, description)
        shop.addElement(service)
    }

}

