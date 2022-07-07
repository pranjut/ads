package com.services

import com.db.models.Ad
import com.modules.DBModules

class AdService(implicit dbModules: DBModules) {

  def insert(ad: Ad) = dbModules.adRepo.insert(ad)
  def delete(id: Long) = dbModules.adRepo.delete(id)
  def get(id: Long) = dbModules.adRepo.get(id)

}
