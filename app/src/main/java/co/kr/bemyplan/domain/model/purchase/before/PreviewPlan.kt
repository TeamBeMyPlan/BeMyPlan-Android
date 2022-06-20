package co.kr.bemyplan.domain.model.purchase.before

data class PreviewPlan(
    val createdAt: String,
    val updatedAt: String,
    val previewInfo: PreviewInfo,
    val previewContents: List<PreviewContents>
) {
    fun translate(): PreviewPlan {
        return this.apply {
            previewContents.map {
                if (it.type == "IMAGE") {
                    it.value = it.value.replace(" ", "")
                }
            }

            previewInfo.theme = Theme.koreanOf(previewInfo.theme)
            previewInfo.partner = Partner.koreanOf(previewInfo.partner)
            previewInfo.mobility = Mobility.koreanOf(previewInfo.mobility)
        }
    }

    enum class Theme(val themeEnglish: String, val themeKorean: String) {
        HEALING("HEALING", "힐링"),
        EATING("EATING", "맛집"),
        HOTPLACE("HOTPLACE", "핫플"),
        LIFESHOT("LIFESHOT", "인생샷"),
        LOCAL("LOCAL", "로컬"),
        ACTIVITY("ACTIVITY", "액티비티"),
        CAMPING("CAMPING", "캠핑");

        companion object {
            fun koreanOf(english: String): String {
                return values().find { it.themeEnglish == english }?.themeKorean ?: throw IllegalArgumentException()
            }
        }
    }

    enum class Partner(val partnerEnglish: String, val partnerKorean: String) {
        FAMILY("FAMILY", "가족"),
        FRIEND("FRIEND", "친구"),
        COUPLE("COUPLE", "연인"),
        SOLO("SOLO", "혼자");

        companion object {
            fun koreanOf(english: String): String {
                return values().find { it.partnerEnglish == english }?.partnerKorean ?: throw IllegalArgumentException()
            }
        }
    }

    enum class Mobility(val mobilityEnglish: String, val mobilityKorean: String) {
        CAR("CAR", "승용차"),
        PUBLIC("PUBLIC", "대중교통"),
        WALK("WALK", "도보"),
        BICYCLE("BICYCLE", "자전거");

        companion object {
            fun koreanOf(english: String): String {
                return values().find {it.mobilityEnglish == english}?.mobilityKorean ?: throw IllegalArgumentException()
            }
        }
    }
}