package VehicleRegCLI


import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.bson.types.ObjectId
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Projections._

/** A Data Access Object for Vehicles.  The goal of this class is to encapsulate
 * all the mongoDB related parts of retrieving Vehicle-Registry, so the rest of our
 * application doesn't have to concern itself with mongo. */
class VehicleDAO(mongoClient : MongoClient) {

  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[Vehicle]), MongoClient.DEFAULT_CODEC_REGISTRY)
  val db: MongoDatabase = mongoClient.getDatabase("Vehicle-Registry").withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Vehicle] = db.getCollection("Vehicles")


  // we make getResults private, since it's not a functionality anyone should use
  // VehicleDao for.
  private def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def getAll() : Seq[Vehicle] = getResults(collection.find())

  def licenseDelete(inputLP: String) = {
    try {
      getResults(collection.deleteOne(equal("LPNumber", inputLP)))(0)
        .getDeletedCount > 0
    } catch {
      case e: Exception => {
        e.printStackTrace() //could be better
        false
      }
    }
  }

  def licenseEdit(inputLP: String, vehicleLine:String) = {
    val vehicleData = vehicleLine.split(",")
    getResults(collection.updateOne(equal("LPNumber", inputLP),combine(set("LPNumber",vehicleData(0)), set("Color", vehicleData(1)), set("Make",vehicleData(2)),
      set("Model",vehicleData(3)),set("Year",vehicleData(4)), set("DriverName",vehicleData(5)))))
  }

  def createVehicle(vehicleLine:String) = {
    val vehicleData = vehicleLine.split(",")
    val newVID = new ObjectId
    val newVehicle = new Vehicle(newVID, vehicleData(0),vehicleData(1),vehicleData(2),Some(vehicleData(3)),
      Some(vehicleData(4).toInt),vehicleData(5))
    newVehicle
  }
  def addVehicle(summ:Vehicle) ={
    getResults(collection.insertOne(summ))
  }

  def viewVehicle(inputLP: String) = {
    println(getResults(collection.find(equal("LPNumber",inputLP))))
  }

}