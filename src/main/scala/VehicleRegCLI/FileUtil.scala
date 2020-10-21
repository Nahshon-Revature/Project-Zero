package VehicleRegCLI

import org.mongodb.scala.Completed
import org.mongodb.scala.bson.ObjectId

import scala.io.{BufferedSource, Source}

object FileUtil {
  // would like to make this a higher order function that can take insertMany and deleteMany
  // functions, then apply to each line. Tried map, which led to "processText" below.
  def getTextContent(filename: String) = {
    // the way we open files is using Source.fromFile.
    // you can write short version of opening + reading from a file,
    // ours will be a little longer so we can properly close the file
    // We'll use a try finally for this

    // just to declare outside of the try block
    var openedFile : BufferedSource = null
    try {
      openedFile = Source.fromFile(filename)
      // return this:
      for (line <- openedFile.getLines()) {println(line.toString)}
    }finally {
      if (openedFile != null) openedFile.close
    }
  }

  def processText(filename: String, func: String => Vehicle, func2: Vehicle => Seq[Completed]) = {
    // the way we open files is using Source.fromFile.
    // you can write short version of opening + reading from a file,
    // ours will be a little longer so we can properly close the file
    // We'll use a try finally for this

    // just to declare outside of the try block
    var openedFile : BufferedSource = null
    try {
      openedFile = Source.fromFile(filename)
      // return this:
      for (line <- openedFile.getLines()) {func2(func(line.toString))}
    }finally {
      if (openedFile != null) openedFile.close
    }
  }
}
