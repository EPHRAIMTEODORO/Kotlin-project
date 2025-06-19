import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



@Composable
fun ChatBotApp() {
    var input by remember { mutableStateOf("") }
    var chatLog by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    val ruleList = listOf(
        "hello" to "Hi there! How can I help?",
        "how are you" to "Great! Thanks for asking.",
        "bye" to "Goodbye — see you later!"
    )

    val rules = ruleList.map { (keyword, response) ->
        { msg: String -> if (msg.contains(keyword, ignoreCase = true)) response else null }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())
        ) {
            chatLog.forEach { (u, b) ->
                Text("You: $u")
                Text("Bot: $b", color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(8.dp))
            }
        }

        Row(Modifier.fillMaxWidth()) {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val msg = input.lowercase().trim()
                if (msg.isNotEmpty()) {
                    val res = rules.asSequence()
                        .mapNotNull { predicate -> predicate(msg) }
                        .firstOrNull()
                        ?: "Sorry, I didn't understand that."

                    chatLog = chatLog + (msg to res)
                    input = ""
                }
            }) {
                Text("Send")
            }
        }
    }
}
