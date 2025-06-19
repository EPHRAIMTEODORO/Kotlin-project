import kotlinx.browser.document
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val container = document.body ?: return
    ComposeViewport(container) {
        ChatBotApp()
    }
}
