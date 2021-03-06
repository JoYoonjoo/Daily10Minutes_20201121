package kr.co.tjoeun.daily10minutes_20201121.datas

import org.json.JSONObject

class User {

    var id = 0 // id가 Int라고 명시
    var email = "" // email은 String
    var nickName = ""

//    응용
//    1. 가입일시 (created_at) : 서버는 String으로 알려줌 -> 앱에서는 Calendar 형태로 변환해서 사용.
//    2. 프로필 사진"들" : 한명의 User가 -> 여러장의 사진URL (String) 을 갖고 있음을 어떻게 표현?

//    한명의 사용자 : 프사 URL 목록을 갖는다고 명시. (ArrayList를 갖는다)
    val profileImgList = ArrayList<String>()

//    JSON 넣으면 => User로 변환해주는 기능 제작

    companion object {

        fun getUserFromJSON(json: JSONObject) : User {
            
            val u = User()
            
//            u의 변수들을 => json이 가진 값을 활용해서 변경

            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")

//            프사 목록 파싱 => u가 가진 profileImgList에 add 해주자.

            val profileImagesArr = json.getJSONArray("profile_images")

            for (i in   0 until profileImagesArr.length()) {
                val pf = profileImagesArr.getJSONObject(i)
                val imgURL = pf.getString("img_url")
                u.profileImgList.add(imgURL)
            }

            return u
        }

    }

}