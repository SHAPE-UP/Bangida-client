package com.example.shape_up_2022.data

data class LocationPageModel(val documents: MutableList<LocationDataModel>)

data class LocationDataModel(val x: String, val y: String)