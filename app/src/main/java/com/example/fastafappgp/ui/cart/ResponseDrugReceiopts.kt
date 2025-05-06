package com.example.fastafappgp.ui.cart

data class ResponseDrugReceiopts(
    val id: Int,
    val drug: Drug,
    val stock: Int,
    val price: Double,
    var inputunits: Int? = null,
    var inputpacks: Int? = null,
)

data class Drug(
    val id: Int,
    val name: String,
    val form: String,
    val units: Int,
    val fullPrice: Double
)