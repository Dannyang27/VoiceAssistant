package com.example.voiceassistant

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import com.example.voiceassistant.Retrofit.RetrofitClient
import com.example.voiceassistant.Util.VoiceFilterUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        voiceButton.setOnClickListener {
            //startVoiceSpeech()
            RetrofitClient.getWeatherByName("london")
        }
    }

    private fun startVoiceSpeech(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        if(intent.resolveActivity(packageManager) != null ){
            startActivityForResult(intent, 200)

        }else{
            Toast.makeText(this, "Voice Recognition not supported", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            200 -> {
                    if (resultCode == Activity.RESULT_OK && data != null) {
                        val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        voiceText.text = result[0].toString()
                        val cityId = VoiceFilterUtils.getIdByCity(result[0].split(' '))
                        if(cityId != -1){
                            RetrofitClient.getWeatherById(cityId)
                        }
                    }
            }
        }
    }
}

//package com.example.voiceassistant
//
//import android.app.Activity
//import android.content.Intent
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.speech.RecognitionListener
//import android.speech.RecognizerIntent
//import android.speech.SpeechRecognizer
//import android.util.Log
//import android.widget.Toast
//import com.example.voiceassistant.Retrofit.RetrofitClient
//import com.example.voiceassistant.Util.VoiceFilterUtils
//import kotlinx.android.synthetic.main.activity_main.*
//import java.util.*
//
//class MainActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val mSpeechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
//        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
//        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
//
//        mSpeechRecognizer.startListening(speechIntent)
//        mSpeechRecognizer.setRecognitionListener(object: RecognitionListener{
//            override fun onReadyForSpeech(params: Bundle?) {
//                Log.d("CurrentWeather", "onReadyForSpeech")
//            }
//
//            override fun onRmsChanged(rmsdB: Float) {
//                Log.d("CurrentWeather", "onRmsChanged")
//            }
//
//            override fun onBufferReceived(buffer: ByteArray?) {
//                Log.d("CurrentWeather", "onBufferReceived")
//            }
//
//            override fun onPartialResults(partialResults: Bundle?) {
//                Log.d("CurrentWeather", "onPartialResults")
//            }
//
//            override fun onEvent(eventType: Int, params: Bundle?) {
//                Log.d("CurrentWeather", "onEvent")
//            }
//
//            override fun onBeginningOfSpeech() {
//                Log.d("CurrentWeather", "onBeginningOfSpeech")
//            }
//
//            override fun onEndOfSpeech() {
//                Log.d("CurrentWeather", "onEndOfSpeech")
//            }
//
//            override fun onError(error: Int) {
//                Log.d("CurrentWeather", "onError")
//            }
//
//            override fun onResults(results: Bundle?) {
//                Log.d("CurrentWeather", "onResults")
//            }
//        })
//    }
//}
