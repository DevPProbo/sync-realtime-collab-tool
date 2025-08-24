package com.devajit.sync_realtimecontentwriting.data.remote.dto

import com.devajit.sync_realtimecontentwriting.domain.model.UserModel
import javax.annotation.concurrent.Immutable

@Immutable
data class UserModelDto(
    var id : String? = "",
    var userName : String? = "",
    var fullName : String? = "",
    var userEmail: String?="",
    var password : String? = "",
    var userPicture:String?=""
) {

    fun toUserModel() : UserModel {
        return UserModel(id, userName, fullName,userEmail,userPicture)
    }

    companion object {
        fun fromUserModel(user: UserModel): UserModelDto {
            return UserModelDto(
                user.id, user.userName, user.fullName,user.userEmail,user.userPicture
            )
        }
    }
}