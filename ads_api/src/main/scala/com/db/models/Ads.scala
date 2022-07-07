package com.db.models

import java.sql.Timestamp

case class Ad(id: Long, text: String, product_id: Long)
//product type could be a number if there are huge number of variation or enum, to save time it is string for now
case class Product(id: Long, name: String, product_type: String)


//This model could have been ina no-sql db like cassandra but just to save time, doing it with postgres
case class AdImpression(id: Long, customer_id: String, ad_id: Long, impressionTime: Timestamp)
case class AdImpressionClicks(id: Long, impressionId: Long, clickTime: Timestamp)