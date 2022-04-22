package com.example.dmerjimirror.library.test

import com.example.dmerjimirror.library.controller.UserController

class UserTest {
    companion object {

        fun testAll() {
            getComponents()
        }

        private fun getComponents() {
            UserController.getComponents(14) { components, throwable ->
                print(components)
            }
        }
    }
}