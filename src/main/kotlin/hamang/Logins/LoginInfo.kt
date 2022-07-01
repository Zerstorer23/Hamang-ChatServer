package hamang.Logins

data class LoginInfo(
    var isWeb: Boolean,
    var isAnonymous: Boolean,
    var id: String,
    var password: String,
    var delay: Int,
    var tagLink:String,
    var gallId:String,
);