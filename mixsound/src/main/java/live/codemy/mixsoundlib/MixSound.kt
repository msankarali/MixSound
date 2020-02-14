package live.codemy.mixsoundlib

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import androidx.appcompat.app.AppCompatActivity
import java.util.*


/**     Code with ❤
╔════════════════════════════╗
║  Created by Gökhan ÖZTÜRK  ║
╠════════════════════════════╣
║ GokhanOzturk@AndroidEdu.IO ║
╠════════════════════════════╣
║     08/02/2020 - 22:21     ║
╚════════════════════════════╝
 */

class MixSound : TextToSpeech.OnInitListener {
    val textToSpeech by lazy { TextToSpeech(appCompatActivity, this) }
    private val promptText = "Kulağıma Fısılda.."
    private val recognizerIntent: Intent by lazy { Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) }

    fun changeSound(soundType: SoundType) {
        when (soundType) {
            SoundType.Fast -> textToSpeech.setSpeechRate(5.0f)
            SoundType.Slow -> textToSpeech.setSpeechRate(0.1f)
            SoundType.Chipmunk -> textToSpeech.setPitch(2f)
            SoundType.DarthVader -> textToSpeech.setPitch(0.1f)
            SoundType.Echo -> {
            }
            SoundType.Reverb -> {
            }
        }
        textToSpeech.speak()
    }

    override fun onInit(status: Int) {
        when (status) {
            TextToSpeech.SUCCESS -> {
                textToSpeech.setLanguage(Locale.getDefault()).let { _result ->
                    when (_result) {
                        TextToSpeech.LANG_MISSING_DATA,
                        TextToSpeech.LANG_NOT_SUPPORTED -> "Dil Desteklenmiyor" toast appCompatActivity
                        else -> textToSpeech.speak()
                    }
                }
            }
            else -> "Dil Desteklenmiyor" toast appCompatActivity
        }
    }

    fun recordSound() {
        with(recognizerIntent) {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, promptText)
            appCompatActivity?.startActivityForResult(this, CODE_SPEECH_RECOGNIZER)
        }
    }

    companion object {
        lateinit var recordSound: String
        const val CODE_SPEECH_RECOGNIZER = 100

        @Volatile
        private var INSTANCE: MixSound? = null
        private var appCompatActivity: AppCompatActivity? = null

        fun getInstance(mAppCompatActivity: AppCompatActivity): MixSound {
            return INSTANCE ?: synchronized(this) {
                appCompatActivity = mAppCompatActivity
                INSTANCE ?: MixSound()
            }
        }
    }
}