package kr.co.tjoeun.daily10minutes_20201121

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.tjoeun.daily10minutes_20201121.utils.ContextUtil

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        토큰 검사는 2.5초 후에 실행 : 지연처리

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            //        토큰값이 어떻게 저장되어 있는지 체크.
//        "" : 아직 저장된 토큰이 없다. => 로그인이 필요한 상태. => 로그인화면으로 이동
//        그 외의 값 : 뭔가 저장된 토큰이 있다. => 자동로그인 처리 => 메인화면으로 이동

            if (ContextUtil.getLoginUserToken(mContext) == "") {
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
            }
            else {
                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
            }

//        이동 후에는 로딩화면 종료
            finish()

        }, 2500)


    }

}