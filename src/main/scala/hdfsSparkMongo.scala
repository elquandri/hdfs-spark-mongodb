import java.io.BufferedOutputStream
import com.mongodb.spark._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.DataFrame
import resultcheck._

object hdfsSparkMongo extends App with sparkConfiguration {


  val dfWrite = readCsv(config.getString("csvFile"))

  initifile()
  mongoWrite(dfWrite)
  checkresult()



  def readCsv(path:String) : DataFrame =
  {
    val df = spark.read.format("com.databricks.spark.csv")
      .option("delimiter", ";")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(path)
    return df
  }

  def mongoWrite (df:DataFrame) {

    df.write.format("com.mongodb.spark.sql.DefaultSource").mode("Overwrite").save()

  }
  def checkresult(): Unit =
  {

    var c=""

    val dfRead = MongoSpark.load(spark)


    if (dfRead.count()==dfWrite.count())
    {
      c = generate("passed").toString
    }
    else if(dfRead.count()!=dfWrite.count()) {
      c = generate("failed").toString
    }
    else {
      c = generate("bloqued").toString
    }


    saveResultFile(c)

  }

  def initifile(): Unit =
  {
    val c =generate("failed").toString
    saveResultFile(c)
  }

  def saveResultFile(content: String) = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", pathHdfs)

    val date = java.time.LocalDate.now.toString
    val filePath = pathResult + resultPrefix + "_" + date + ".json"

    val fs = FileSystem.get(conf)
    val output = fs.create(new Path(filePath), true)

    val writer = new BufferedOutputStream(output)

    try {
      writer.write(content.getBytes("UTF-8"))
    }
    finally {
      writer.close()
    }
  }


}
