import com.typesafe.config.{Config, ConfigFactory}
import org.apache.spark.sql.SparkSession


trait sparkConfiguration {


  val config:Config=ConfigFactory.load("MongoDB.conf")
  val mongo_host =config.getString("mongo_host")
  val appName =config.getString("appName")
  val master =config.getString("master")
  val inputUrl =config.getString("inputUrl")
  val outputUrl =config.getString("outputUrl")
  val dbConnection =config.getString("dbConnection")
  val odbConnection =config.getString("odbConnection")
  val dbName =config.getString("dbName")
  val collectionConnection =config.getString("collectionConnection")
  val ocollectionConnection =config.getString("ocollectionConnection")
  val collectionName =config.getString("collectionName")
  val pathResult =config.getString("pathResult")
  val resultPrefix = config.getString("resultPrefix")
  val pathHdfs = config.getString("pathHdfs")


  val spark = SparkSession.builder()
    .master(master)
    .appName(appName)
    .config(inputUrl, mongo_host)
    .config(dbConnection, dbName)
    .config(collectionConnection, collectionName)
    .config(outputUrl, mongo_host)
    .config(odbConnection, dbName)
    .config(ocollectionConnection, collectionName)
    .getOrCreate()

}
