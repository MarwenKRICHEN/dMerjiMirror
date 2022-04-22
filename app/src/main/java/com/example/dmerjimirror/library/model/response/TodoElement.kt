package com.example.dmerjimirror.library.model.response

import java.io.Serializable
import java.util.*

data class TodoElement(val id: Int, val name: String, val deadline: Date, val done: Boolean, val todoid: Int): Serializable
