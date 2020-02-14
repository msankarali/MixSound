package live.codemy.mixsound

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import live.codemy.mixsoundlib.MixSound
import live.codemy.mixsoundlib.SoundType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) { // , persistentState: PersistableBundle?
        super.onCreate(savedInstanceState) //, persistentState

        setContentView(R.layout.activity_main)

        val imgMic = findViewById<ImageView>(R.id.imageView)
        val imgbFast = findViewById<ImageView>(R.id.imageView3)
        val imgbSlow = findViewById<ImageView>(R.id.imageView2)
        val imgbThin = findViewById<ImageView>(R.id.imageView4)
        val imgbPowerful = findViewById<ImageView>(R.id.imageView5)

        imgMic.setOnClickListener {
            "imgMic clicked!!" extShowToast this
            MixSound.getInstance(this).recordSound()
        }
        imgbFast.setOnClickListener {
            "imgbFast clicked!!" extShowToast this
            MixSound.getInstance(this).changeSound(SoundType.Fast)
        }
        imgbSlow.setOnClickListener {
            "imgbSlow clicked!!" extShowToast this
            MixSound.getInstance(this).changeSound(SoundType.Slow)
        }
        imgbThin.setOnClickListener {
            "imgbThin clicked!!" extShowToast this
            MixSound.getInstance(this).changeSound(SoundType.Chipmunk)
        }
        imgbPowerful.setOnClickListener {
            "imgbPowerful clicked!!" extShowToast this
            MixSound.getInstance(this).changeSound(SoundType.DarthVader)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            MixSound.CODE_SPEECH_RECOGNIZER -> {
                data?.let {
                    val result = it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    //MixSound.recordSound = result.first()
                    MixSound.recordSound = result.first()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        MixSound.getInstance(this).textToSpeech.shutdown()
    }
}

infix fun String.extShowToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}