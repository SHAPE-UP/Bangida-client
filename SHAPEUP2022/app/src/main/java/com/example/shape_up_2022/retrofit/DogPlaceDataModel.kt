package com.example.shape_up_2022.retrofit

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class responseInfo(
    @Element
    val header: Header,
    @Element
    val body : myBody
)

@Xml(name="header")
data class Header(
    @PropertyElement
    val resultCode: String,
    @PropertyElement
    val resultMsg: String
)

@Xml(name="body")
data class myBody(
    @Element
    val items : myItems,
    @PropertyElement
    val numOfRows: Int,
    @PropertyElement
    val pageNo: Int,
    @PropertyElement
    val totalCount: String
)

@Xml(name="items")
data class myItems(
    @Element(name="item")
    val item: MutableList<myItem>
)

@Xml
data class myItem(
    @PropertyElement
    val subjectCategory: String?,
    @PropertyElement
    val title: String?,
    @PropertyElement
    val venue: String?,
    @PropertyElement
    val affiliation: String?,
    @PropertyElement
    val state: String?,
    @PropertyElement
    val reference: String?,

){
    constructor() : this( null,null, null, null, null, null)
}