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
        "bye" to "Goodbye — see you later!",
        "are we going to pass this class" to "Of course you will! As a matter of fact you will get an A+",
        "who is the best teacher in BYUH?" to "This is a tricky question, but I would say its Chris Slade because he will give you an A after this.",
        "how many koreans are there in class?" to "There's four outstanding Korean students taking CS420 right now. Do you want me to state their name?",
        "yes, please" to "They are Hyun Kim, Jeseop Lee, Kyeonglyeol Seung, and Dain Yoon.",
        "what is the best place on earth" to "The best place on earth could be found at Brigham Young University - Hawaii, in front of Heber Grant Building, Portable 2.",
        "who will fail this class" to "I don't have something specifically in mind, but I would those students who spend less time studying.",
        "between yaz, ted, and ephraim who will be the most successfull" to "All of you will be successfull except for Yaz.",
        "is CS490R hard on spring semester?" to "If you are motivated to spend hours a day to create a project then you could take this during spring semester. The only thing you need to master is how you manage your time efficiently.",
        "thanks" to "You’re welcome! Happy to help 😊",
        "thank you" to "Anytime—it’s my pleasure!",
        "sorry" to "No worries at all! How can I help?",
        "tell me a joke" to "Why do programmers prefer dark mode? Because light attracts bugs!",
        "can you tell me more" to "Your life",
        "tell me a fun fact" to "Did you know the very first computer bug was an actual moth stuck in a relay?",
        "how’s the weather" to "I’m a bot—my weather reports are a bit “static.” How’s it look out your window?",
        "what time is it" to "I don’t have wrists, but your device should show the current time!",
        "is this project worth it" to "yes, it will help you get a job"


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
