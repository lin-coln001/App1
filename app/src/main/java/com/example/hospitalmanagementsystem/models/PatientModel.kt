package com.example.hospitalmanagementsystem.models

data class PatientModel(
    var id: String?= null,
    var name: String?= null,
    var age: String?= null,
    var phone: String?= null,
    var illness: String?= null,
    var imageUrl: String?= null,
    var gender: String?= null,
    var date_of_visit: String?=null
)