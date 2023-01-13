package com.example.healthcare.DataModel

class HospitalsData(
    val address:String,
    val hospital:String,
    val mail:String,
    val number:String,
    val pincode:String
) {
    constructor() : this("","","","","",)
}