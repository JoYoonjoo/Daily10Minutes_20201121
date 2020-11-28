package kr.co.tjoeun.daily10minutes_20201121

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_project_detail.*
import kr.co.tjoeun.daily10minutes_20201121.datas.Project
import kr.co.tjoeun.daily10minutes_20201121.utils.ServerUtil
import org.json.JSONObject

class ViewProjectDetailActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        applyBtn.setOnClickListener {

            ServerUtil.postRequestApplyProject(mContext, mProject.id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val dataObj = json.getJSONObject("data")
                    val projectObj = dataObj.getJSONObject("project")

//                    참여신청을 하면 => 최신상태로 변경된 프로젝트 정보를 서버가 다시 알려줌.
//                    지금 몇명이 신청하고 있는지 등.

//                    그 모든 정보가 담겨있는 새 프로젝트 JSON : projectObj

//                    JSON재료(projectObj)로 => Project로 변환하는 기능을 활용.

                    val newProject = Project.getProjectFromJSON(projectObj)

//                    화면에는 mProject가 뿌려짐.
//                    mProject를 새 프로젝트로 교체.
                    mProject = newProject

//                    새 프로젝트 데이터를 다시 뿌려주자. UI 변경
                    runOnUiThread {
                        setProjectDataToUI()
                    }

                }

            })

        }

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

        setProjectDataToUI()

    }

//    mProject로 => 화면에 뿌려주는 기능을 별도 함수로 분리

    fun setProjectDataToUI() {
        //        mProject 활용 => 실제 데이터 화면에 뿌려주기

        projectTitleTxt.text = mProject.title
        Glide.with(mContext).load(mProject.imageURL).into(projectImg)
        projectDescriptionTxt.text = mProject.description
        projectProofMethodTxt.text = mProject.proofMethod

//        서버가 주는 데이터 : 5 등의 숫자. => 지금 n명 참여중! => String 가공
        onGoingUsersCountTxt.text = "지금 ${mProject.onGoingUsersCount}명 참여중!"

//        내 프로젝트인지 아닌지에 따라 버튼 보여주고 숨기는 부분
        if (mProject.isMyProject) {
//            내가 진행중인 프로젝트가 맞다!
//            도전하기 숨기고, 포기하기 보여주자.

            applyBtn.visibility = View.GONE
            giveUpBtn.visibility = View.VISIBLE

        }
        else {
//            아직 신청하지 않은 프로젝트다!
//            도전하기 보여주고, 포기하기 숨기자.

            applyBtn.visibility = View.VISIBLE
            giveUpBtn.visibility = View.GONE

        }

    }

}