package com.example.dmerjimirror.library.model.response

import java.io.Serializable
import java.util.*

data class TodoElement(val id: Int, var name: String, var deadline: Date, var done: Boolean, val todoid: Int): Serializable
