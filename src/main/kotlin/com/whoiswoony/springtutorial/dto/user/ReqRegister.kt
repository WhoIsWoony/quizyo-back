package com.whoiswoony.springtutorial.dto.user

import com.whoiswoony.springtutorial.domain.user.User

data class ReqRegister (var uid:String, var upw:String){
    fun toUser() = User(
        uid = this.uid,
        upw = this.upw
    )
}