package VehicleRegCLI

import com.mongodb.client.model.Sorts
import org.mongodb.scala.model.Updates
import org.mongodb.scala.model.Filters
import org.mongodb.scala.{MongoClient, MongoCollection, Observable}

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}
import org.mongodb.scala.bson.codecs.Macros._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

object Runner extends App {
  //Mongo scala boilerplate for localhost:
  // include classOf[T] for all of your classes
//  val codecRegistry = fromRegistries(fromProviders(classOf[Vehicle]), MongoClient.DEFAULT_CODEC_REGISTRY)
//  val client = MongoClient()
//  val db = client.getDatabase("Vehicle-Registry").withCodecRegistry(codecRegistry)
//  val collection : MongoCollection[Vehicle] = db.getCollection("Vehicles")

  //helper functions for access and printing, to get us started + skip the Observable data type
  def getResults[T](obs: Observable[T]): Seq[T] = {
    Await.result(obs.toFuture(), Duration(10, SECONDS))
  }

  def printResults[T](obs: Observable[T]): Unit = {
    getResults(obs).foreach(println(_))
  }

  new Cli().menu()
}
