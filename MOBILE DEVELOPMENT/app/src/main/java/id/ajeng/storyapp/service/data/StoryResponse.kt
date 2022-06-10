package id.ajeng.storyapp.service.data

data class StoryResponse(
    val error: Boolean? = null,
    val message: String = "",
    val listStory: List<StoryResults>? = null
)
