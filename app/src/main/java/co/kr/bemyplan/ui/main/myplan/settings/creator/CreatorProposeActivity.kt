package co.kr.bemyplan.ui.main.myplan.settings.creator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.kr.bemyplan.databinding.ActivityCreatorProposeBinding

class CreatorProposeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatorProposeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatorProposeBinding.inflate(layoutInflater)

        binding.ivBack.setOnClickListener { finish() }
        binding.tvProposeButton.setOnClickListener { onPropose() }

        setContentView(binding.root)
    }

    private fun onPropose() {
        val email = Intent(Intent.ACTION_SEND)
        email.type = "plain/text"
        val address = arrayOf("teambemyplan@gmail.com")
        val title = "비마이플랜 크리에이터 신청"
        val text = "업로드 신청 입력 양식 \n" +
                "- 이름 :\n" +
                "- 닉네임 : \n" +
                "- 여행 일정 컨텐츠 제목 : \n" +
                "- 장소명 : \n" +
                "- 전화번호 : \n" +
                "- 사진 : \n" +
                "- 정보 (솔직 후기, 꿀팁) :"
        email.putExtra(Intent.EXTRA_EMAIL, address)
        email.putExtra(Intent.EXTRA_SUBJECT, title)
        email.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(email)
    }
}