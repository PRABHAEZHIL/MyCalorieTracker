package com.kvep.core.domain.model

import com.kvep.core.domain.model.Gender.Male.name

sealed class Gender(val name:String){
    object Male:Gender("male")
    object Female:Gender("female")
    companion object{
        fun fromString(name: String):Gender{
            return when(name){
                "male"->Male
                "female"->Female
                else->Female
            }
        }
    }
}
