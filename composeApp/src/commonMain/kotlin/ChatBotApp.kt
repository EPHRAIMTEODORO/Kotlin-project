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
        "Hello" to "Hi there! How can I help?",
        "How are you" to "Great! Thanks for asking.",
        "Bye" to "Goodbye — see you later!",
        "Are we going to pass this class" to "Of course you will! As a matter of fact you will get an A+",
        "Who is the best teacher in BYUH" to "This is a tricky question, but I would say its Chris Slade because he will give you an A after this.",
        "How many koreans are there in class" to "There's four outstanding Korean students taking CS420 right now. Do you want me to state their name?",
        "Yes, please" to "They are Hyun Kim, Jeseop Lee, Kyeonglyeol Seung, and Dain Yoon.",
        "What is the best place on earth" to "The best place on earth could be found at Brigham Young University - Hawaii, in front of Heber Grant Building, Portable 2.",
        "Who will fail this class" to "I don't have specifically someone in mind, but I would say that it will be those students who spend less time studying.",
        "Between Yaz, Ted, and Ephraim, who will be the most successful" to "All of you will be successful except for Yaz.",
        "Is CS490R hard on spring semester" to "If you are motivated to spend hours a day to create a project, then you could take this during spring semester. The only thing you need to master is how you manage your time efficiently.",
        "Thanks" to "You’re welcome! Happy to help 😊",
        "Thank you" to "Anytime—it’s my pleasure!",
        "Sorry" to "No worries at all! How can I help?",
        "Tell me a joke" to "Why do programmers prefer dark mode? Because light attracts bugs!",
        "Can you tell me more" to "Your life",
        "Tell me a fun fact" to "Did you know the very first computer bug was an actual moth stuck in a relay?",
        "Wow’s the weather" to "I’m a bot—my weather reports are a bit “static.” How’s it look out your window?",
        "What time is it" to "I don’t have wrists, but your device should show the current time!",
        "Is this project worth it" to "Yes, it will help you get a job",
        "Is Kotlin compiled, interpreted, or both" to "Kotlin is a compiled language. It compiles to Java bytecode (for the JVM) or JavaScript (for front-end), or native code (via Kotlin/Native) depending on the target platform.",
        "What are the primitives" to "Primitives\n" +
                "- Kotlin types like Int, Double, and Boolean are class-based in the language.\n" +
                "- On the JVM, non-nullable basics compile to Java primitives (int, double, boolean) for performance.\n" +
                "- Nullable types (Int?) and generics (List<Int>) are boxed as Java objects (java.lang.Integer).\n" +
                "- Specialized arrays (IntArray, DoubleArray) compile to primitive JVM arrays (int[], double[]).",
        "What is the Abstraction Mechanisms" to "Abstraction Mechanisms\n" +
                "- Interfaces: Define contracts; may include default implementations\n" +
                "- Abstract classes: Combine abstract and concrete members; cannot be instantiated\n" +
                "- Sealed classes/interfaces: Restrict subclassing to a known set, enabling exhaustive when expressions\n" +
                "- Generics: Type-parameterized classes and functions\n" +
                "- Extension functions: Add methods to existing types without inheritance\n",
        "What are the means of combination" to "Means of Combination\n" +
                "- Inheritance (is-a): Extend one class or implement interfaces\n" +
                "- Composition (has-a): Hold instances of other classes as properties\n" +
                "- Delegation (by keyword): Forward interface implementation to a delegate object\n" +
                "- Traits (interfaces with default methods): Mix in behavior without class inheritance\n",
        "How about Variable Declarations" to "Variable Declarations\n" +
                "- val: Immutable variable\n" +
                "- var: Mutable variable\n" +
                "- Type inference: Omit explicit type when initialized\n" +
                "- const val: Compile-time constants (top-level or object)\n" +
                "- lateinit var: Deferred initialization for non-nullable properties\n" +
                "- Nullability: Append ? (e.g., String?) to allow null",
        "What are the uses of Kotlin" to "Uses of Kotlin\n" +
                "- Android App Development (officially supported by Google)\n" +
                "- Server-Side Development (JVM back-end alongside Java)\n" +
                "- Full-Stack Web (compile to JavaScript for front-end)\n" +
                "- Multiplatform Mobile (shared code for Android, iOS, watchOS, Linux)\n" +
                "- Data Science & ML (Jupyter, Zeppelin support; data pipelines, model deployment)\n" +
                "- Cloud Infrastructure (tooling and scripts for resource management)\n",
        "What are the Advantages and Disadvantage in Kotlin" to "Advantages\n" +
                "- Concise and readable syntax\n" +
                "- Null safety reduces runtime errors\n" +
                "- Full Java interoperability\n" +
                "- Multiplatform support\n" +
                "- Modern features (coroutines, higher-order functions)\n" +
                "- Strong tooling (JetBrains & Google support)\n" +
                "\nDisadvantages\n" +
                "- Smaller community and fewer learning resources than Java\n" +
                "- Slower full-build compile times at scale\n" +
                "- Key differences from Java require a learning curve\n" +
                "- Relative shortage of experienced Kotlin developers\n"
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
