package VehicleRegCLI

import org.bson.types.ObjectId


case class Vehicle(_id : ObjectId, LPNumber : String, Color : String, Make : String, Model : Option[String], Year : Option[Int], DriverName : String){}


object Vehicle {
  def apply(_id: ObjectId, LPNumber: String, Color: String, Make: String, Model: Option[String], Year: Option[Int], DriverName : String): Vehicle = new Vehicle(_id, LPNumber, Color, Make, Model, Year, DriverName)
}