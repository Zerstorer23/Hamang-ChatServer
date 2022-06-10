package Logins

data class LoginInfo(var isAnonymous :Boolean,
                     var id : String,
                     var password : String,
                     var delay:Int
                     );