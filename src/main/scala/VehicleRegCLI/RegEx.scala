package VehicleRegCLI

import scala.util.matching.Regex

class RegEx {
  // Regular Expressions are a very handy tool that lets us pattern match strings
  // and extract pieces of strings that match patterns.
  // For this app, I'll give us some regex to use.  Check regex cheatsheets / google
  // to find others.

  /** commandArgPattern is regex that we get us a command and arguments to that
   * command from user input */
  val commandArgPattern : Regex = "(\\w+)\\s*(.*)".r

}
