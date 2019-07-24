import com.typesafe.config.{ConfigFactory, Config}

lazy val appProperties = settingKey[Config]("The application properties")
lazy val fileproperties = ConfigFactory.parseFile(new File("src/main/resources/application.conf"))
lazy val properties = ConfigFactory.load(fileproperties)
lazy val sparkVersion = properties.getString("spark.spark-sql-version")
lazy val mongoConnectorVersion = properties.getString("spark.mongo-spark-connector")
lazy val appName = properties.getString("scala.name")
lazy val appVersion = properties.getString("scala.version")
lazy val appscalaVersion = properties.getString("scala.scalaVersion")


name := appName

version := appVersion

scalaVersion := appscalaVersion

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion % "provided"

libraryDependencies += "org.mongodb.spark" %% "mongo-spark-connector" % mongoConnectorVersion

libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies += "com.google.code.gson" % "gson" % "2.8.1"
libraryDependencies += "net.virtual-void" %%  "json-lenses" % "0.6.2"


test in assembly := {}