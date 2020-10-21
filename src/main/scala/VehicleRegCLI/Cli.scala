package VehicleRegCLI

import java.io.FileNotFoundException

import VehicleRegCLI.VehicleDAO
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}

import scala.io.StdIn
import scala.util.matching.Regex

class Cli {

  val currentClient = MongoClient()
  val currentDAO = new VehicleDAO(currentClient)

  // Regular Expressions are a very handy tool that lets us pattern match strings
  // and extract pieces of strings that match patterns.
  // For this app, I'll give us some regex to use.  Check regex cheatsheets / google
  // to find others.

  /** commandArgPattern is regex that we get us a command and arguments to that
   * command from user input */
  val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r

  def printWelcome() : Unit = {
    println("Welcome to G.A.S.I.F. Vehicle Registry\n")
  }

  def printOptions() : Unit = {
    println("\n")
    println("All : Displays all registrations")
    println("New : Register new vehicles")
    println("Edit : Change a registration")
    println("Find : Find a registration")
    println("Delete : Remove a registration")
    println("Exit : Close G.A.S.I.F. VR")
    println("Please enter an option.")
  }

  /** Runs the menu prompting + listening for user input */
  def menu():Unit = {
    printWelcome()
    var continueMenuLoop = true

    // This loop here will repeatedly prompt, listen, run code, and repeat
    while(continueMenuLoop) {
      printOptions()
      // get user input with StdIn.readLine, read directly from StdIn
      StdIn.readLine() match {
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("All") => {
          println("Here are all currently registered vehicles: \n")
          println(currentDAO.getAll())
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("New") => {
          println("Please enter a file name. File must contain vehicle data with " +
            "values separated by commas and one vehicle per line: \n")
          var fileName = StdIn.readLine().toString
          FileUtil.processText(fileName,currentDAO.createVehicle,currentDAO.addVehicle)
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("Edit") => {
          println("Please enter a License Plate Number: \n")
          StdIn.readLine()
          println("Thank you for using G.A.S.I.F. Vehicle Registry. We are currently" +
            "working on this feature, so please come again later. :)")
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("Find") => {
          println("Please enter a License Plate Number: \n")
          var userLP = StdIn.readLine().toString
          currentDAO.viewVehicle(userLP)
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("Delete") => {
          println("Please enter a License Plate Number: \n")
          var userLP = StdIn.readLine().toString
          currentDAO.licenseDelete(userLP)
        }
        case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("Exit") => {
          println("Thanks for using G.A.S.I.F. Vehicle Registry! Goodbye :)")
          continueMenuLoop = false
        }
        case notRecognized => println(s"$notRecognized is not a recognized command. Please try " +
          s"one of the listed commands.")
      }
    }
  }

}
