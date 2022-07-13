package com.pranjut.services

import com.pranjut.db.models.Ad
import com.pranjut.modules.CoreModules

class AdService(dbModules: CoreModules) {

  def insert(ad: Ad) = dbModules.adRepo.insert(ad)
  def delete(id: Long) = dbModules.adRepo.delete(id)
  def get(id: Long) = dbModules.adRepo.get(id)

}
