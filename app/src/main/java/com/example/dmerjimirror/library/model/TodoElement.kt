package com.example.dmerjimirror.library.model

import java.io.Serializable
import java.util.*

data class TodoElement(val id: Int, val name: String, val deadline: Date, val done: Boolean): Serializable
