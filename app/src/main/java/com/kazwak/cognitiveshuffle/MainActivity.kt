package com.kazwak.cognitiveshuffle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kazwak.cognitiveshuffle.ui.theme.CognitiveShuffleTheme
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.kazwak.cognitiveshuffle.voice.VoiceFilePlayer

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CognitiveShuffleTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Content(this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CognitiveShuffleTheme {
        Greeting("Android")
    }
}


@Composable
fun Content(activity: MainActivity) {
    var voiceId by remember { mutableStateOf( 0) }
    val asset = LocalContext.current.assets
    val vfp = VoiceFilePlayer()

    Column(horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center
        , modifier = Modifier.background(Color.Gray)) {
        Row(horizontalArrangement = Arrangement.Absolute.Center) {
            ControlButtons(voiceId, activity, vfp)
        }
        // キャラクターを増やす場合は切り替えられるUIを追加する
//  ex)
//        Row(horizontalArrangement = Arrangement.Absolute.Center){
//            Button(enabled = VoiceFilePlayer.isPlaying.value.not()
//                , onClick = {
//                voiceId = 0
//            }) {
//                Text("ずんだもん")
//            }
//            Button(enabled = VoiceFilePlayer.isPlaying.value.not()
//                , onClick = {
//                voiceId = 1
//            }) {
//                Text("四国メタン")
//            }
//        }
        Row(horizontalArrangement = Arrangement.Absolute.Center) {
            StandImage(id = voiceId)
        }
        Row(horizontalArrangement = Arrangement.Absolute.Right) {
            Text("VoiceBox：ずんだもん")
        }
    }
}

//@Preview
//@Composable
//fun ContentPreview () {
//    Content()
//}

@Composable
fun StandImage(id: Int) {
    when (id) {
        0 -> Image(
            painter = painterResource(id = R.drawable.zunda), contentDescription = null
        )
    }

}

@Preview
@Composable
fun StandImagePreview() {
    StandImage(0)
}
