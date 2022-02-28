
import org.apache.spark.SparkContext;

import org.apache.spark.SparkConf;

object Product_broadcast {

  def main(args:Array[String]) {

    val sparkConf = new SparkConf().setAppName("Broadcast").setMaster("local")

    val sc = new SparkContext(sparkConf)



//Build the map collection in scala to store the different product id and the corresponding product descriptions

val prod_codes = Map("P101"->"cosmetics","P102"->"Furtniture","P103"->"Handmade")

//The original data(sequence/file) ,which can be converted into RDD . This RDD is in need of the previous map

val orders = Seq(("Customer1","O0901","P101"),("Customer2","O0902","P102"),("Customer3","O0903","P103"))

val ordersRDD = sc.parallelize(orders)

//converting the map into the broadcast variable

val prod_broadcast = sc.broadcast(prod_codes)

//using the broadcast variable 

val res_rdd = ordersRDD.map(x=>{
  val product_id=x._3
  val product_desc = prod_broadcast.value.get(product_id).get
  (x._1,x._2,x._3,product_desc)
})

    //Change the file name according to yout path
res_rdd.saveAsTextFile("/home/hadoop/Learnbay/Spark/broadcast_scala")

  }
}
