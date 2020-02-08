package com.example.taskapp.viewModels.addReminder

import com.example.taskapp.viewmodels.addReminder.DurationModel
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

 class DurationModelTest{
    private lateinit var model : DurationModel
     @Before
    fun setup(){
         model = DurationModel()
     }

     @Test
     fun beginningDate_isYesterday_error(){
         //todo
     }
}