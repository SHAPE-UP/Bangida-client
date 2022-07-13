package com.example.shape_up_2022

data class LocationPageModel(val item: DocumentList)

data class DocumentList(val documents: MutableList<LocationDataModel>)

data class LocationDataModel(val x: String, val y: String, val address_name: String)