package com.example.todolistproject

class userRegister (username:String, name: String, email:String, password:String){
    private var username: String
    private var name: String
    private var email: String
    private var password: String


    init {
        this.username = username
        this.name = name
        this.email = email
        this.password = password
    }
    fun getUserName(): String {
        return this.username
    }
    fun getName(): String {
        return this.name
    }
    fun getEmail(): String {
        return this.email
    }
    fun getPassword(): String {
        return this.password
    }

    fun serUserName(username: String) {
        this.username = username
    }
    fun setName(name:String) {
        this.name = name
    }
    fun setEmail(email:String) {
        this.email = email
    }
    fun setPassword(password: String) {
        this.password = password
    }


}
